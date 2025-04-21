package mx.edu.itesca.proyectoavaluos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

class AcabadoViewModel : ViewModel() {

    private val db = Firebase.firestore
    private var _listaAcabados = MutableLiveData<List<Acabado>>(emptyList())
    val listaAcabados: LiveData<List<Acabado>> = _listaAcabados
    var lastIdAvaluoInmueble: String = ""
    init {
        obtenerAcabados()
    }

    fun obtenerAcabados() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val resultado = db.collection("opcion").get().await()
                val acabados = resultado.documents.mapNotNull { it.toObject(Acabado::class.java) }.sortedBy { it.descripcion }
                _listaAcabados.postValue(acabados)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun agregarAvaluoInmueble(avaluoInmueble: AvaluoInmueble) {
        avaluoInmueble.id = UUID.randomUUID().toString()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val querySnapshot = db.collection("avaluo_inmueble").whereEqualTo("idInmueble", avaluoInmueble.idInmueble).whereEqualTo("idAvaluo", avaluoInmueble.idAvaluo).get().await()
                if (querySnapshot.isEmpty) {
                    db.collection("avaluo_inmueble").document(avaluoInmueble.id).set(avaluoInmueble).await()
                    lastIdAvaluoInmueble = avaluoInmueble.id
                }else{
                    lastIdAvaluoInmueble=querySnapshot.documents.mapNotNull{ it.toObject(AvaluoInmueble::class.java) }.get(0).id
                    Log.d("ID_AVALUO_INMUBLE",lastIdAvaluoInmueble)
                    val result = db.collection("opciones_seleccionadas").whereEqualTo("idAvaluoInmueble", lastIdAvaluoInmueble).get().await()
                    val opcionesSeleccionadas=result.documents.mapNotNull {  it.toObject(OpcionSeleccionada::class.java)}
                    val acabadosActuales = _listaAcabados.value?.map { acabado ->
                        if (opcionesSeleccionadas.any { it.idOpcion == acabado.id }) {
                            acabado.copy(checked = true)
                        } else {
                            acabado
                        }
                    } ?: emptyList()

                    _listaAcabados.postValue(acabadosActuales)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun agregarOpcionSeleccionada(opciones_seleccionadas: ArrayList<OpcionSeleccionada>) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                for (opcion in opciones_seleccionadas) {
                    val snapshot = db.collection("opciones_seleccionadas")
                        .whereEqualTo("idAvaluoInmueble", lastIdAvaluoInmueble)
                        .get()
                        .await()

                    val opcionesSeleccionadasEnDB = snapshot.documents.mapNotNull { it.toObject(OpcionSeleccionada::class.java) }

                    val opcionesSeleccionadasNuevas = opciones_seleccionadas.filter { opcionSeleccionadaNueva ->
                        opcionesSeleccionadasEnDB.none { it.idOpcion == opcionSeleccionadaNueva.idOpcion }
                    }

                    val opcionesRemovidas = opcionesSeleccionadasEnDB.filter { opcionEnBD ->
                        opciones_seleccionadas.none { it.idOpcion == opcionEnBD.idOpcion }
                    }

                    for (opcionNueva in opcionesSeleccionadasNuevas) {
                        opcionNueva.id = UUID.randomUUID().toString()
                        opcionNueva.idAvaluoInmueble = lastIdAvaluoInmueble
                        db.collection("opciones_seleccionadas").document(opcionNueva.id).set(opcionNueva).await()
                        Log.d("INSERT_OPCION", "Insertada: ${opcionNueva.idOpcion}")
                    }
                    for (opcionRemovida in opcionesRemovidas) {
                        db.collection("opciones_seleccionadas").document(opcionRemovida.id).delete().await()
                        Log.d("DELETE_OPCION", "Eliminada: ${opcionRemovida.idOpcion}")
                    }
                    actualizarTotalAvaluoYSubtotalAvaluoInmueble()
                }
                Log.d("INSERT_OPCIONES_SELECCIONADAS","Todos las opciones seleccionadas se insertaron correctamente"
                )
            } catch (e: Exception) {
                Log.e("INSERT_OPCIONES_SELECCIONADAS", "Error al insertar: ${e.message}")
            }
        }
    }
    fun actualizarTotalAvaluoYSubtotalAvaluoInmueble(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val snapshot = db.collection("opciones_seleccionadas")
                    .whereEqualTo("idAvaluoInmueble", lastIdAvaluoInmueble)
                    .get()
                    .await()

                val opcionesSeleccionadas = snapshot.documents.mapNotNull { it.toObject(OpcionSeleccionada::class.java) }

                //En lugar de hacer otra consulta se toma la lista de opciones o de acabados y hacemos un filtrado con las opciones seleccionadas
                //para posteriormente sumarlas y tener el subtotal del avaluo_inmueble
                val acabados = _listaAcabados.value?: emptyList()
                var subtotal=acabados.filter{acabado->opcionesSeleccionadas.any{it.idOpcion==acabado.id}}.sumOf { it.precio }

                //Se busca al avaluo_inmueble que coincida con la id para actualizar su subtotal
                var resultado_avaluoInmueble=db.collection("avaluo_inmueble").document(lastIdAvaluoInmueble).get().await()
                val avaluo_inmueble = resultado_avaluoInmueble.toObject(AvaluoInmueble::class.java)
                val idAvaluo = avaluo_inmueble?.idAvaluo
                if (avaluo_inmueble != null && idAvaluo != null) {
                    db.collection("avaluo_inmueble").document(lastIdAvaluoInmueble)
                        .update("subtotal", subtotal)
                        .await()
                } else {
                    Log.e("ACTUALIZAR_SUBTOTAL", "AvaluoInmueble o idAvaluo es null")
                }

                //se busca ahora todos los avaluos-inmueble que coincidan con el idAvaluo de la anterior busqueda para actualizar
                //el total del documento avaluo
                val snapshotAvaluoInmueble = db.collection("avaluo_inmueble")
                    .whereEqualTo("idAvaluo", idAvaluo)
                    .get()
                    .await()

                val avaluosInmueble = snapshotAvaluoInmueble.documents.mapNotNull { it.toObject(AvaluoInmueble::class.java) }
                val total = avaluosInmueble.sumOf { it.subtotal }

                if (idAvaluo != null) {
                    db.collection("avaluo").document(idAvaluo)
                        .update("total", total)
                        .await()
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
/*
    fun insertarAcabadosPrueba() {
        val acabados = listOf(
            AcabadoInsert("1", "Loseta cerámica", "1", 1050),
            AcabadoInsert("2", "Loseta cerámica con zoclos", "1", 1850),
            AcabadoInsert("3", "Loseta cerámica rectificada con zoclos", "1", 1200),
            AcabadoInsert("4", "Loseta vinílica", "1", 1750),
            AcabadoInsert("5", "Mosaico cerámico", "1", 300),
            AcabadoInsert("6", "Laminado imitación duela", "1", 2100),
            AcabadoInsert("7", "Duela de cedro", "1", 300),
            AcabadoInsert("8", "Alfombra", "1", 2100),
            AcabadoInsert("9", "Loseta antiderrapante", "1", 200),
            AcabadoInsert("10", "Loseta cerámica y antiderrapante en zona húmeda", "1", 300),
            AcabadoInsert("11", "Concreto pulido y antiderrapante en zona húmeda", "1", 750),
            AcabadoInsert("12", "Loseta vinílica y antiderrapante en zona húmeda", "1", 1300),
            AcabadoInsert("13", "Mosaico cerámico y antiderrapante en zona húmeda", "1", 150),
            AcabadoInsert("14", "Firme de concreto y antiderrapante en zona húmeda", "1", 650),
            AcabadoInsert("15", "Loseta cerámica y firme de concreto en zona húmeda", "1", 1450),
            AcabadoInsert("16", "Mosaico cerámico y firme de concreto en zona húmeda", "1", 1250),
            AcabadoInsert("17", "Firme de concreto pulido", "1", 1900),
            AcabadoInsert("18", "Firme de concreto común", "1", 1550),
            AcabadoInsert("19", "Firme de concreto escobillado", "1", 400),
            AcabadoInsert("20", "Firme de concreto lavado", "1", 800),
            AcabadoInsert("21", "Huellas de concreto", "1", 2100),
            AcabadoInsert("22", "Adoquín", "1", 2500),
            AcabadoInsert("23", "Tierra y andadores de concreto", "1", 2450),
            AcabadoInsert("24", "Tierra", "1", 1400),
            AcabadoInsert("25", "Cantera", "1", 600),
            AcabadoInsert("26", "Pasto sintético", "1", 250),
            AcabadoInsert("27", "Pasto", "1", 650),
            AcabadoInsert("28", "Firme de concreto con tratamiento epóxico", "1", 1950),
            AcabadoInsert("29", "Firme de concreto sin reglear", "1", 1350),
            AcabadoInsert("30", "Empedrado", "1", 150),
            AcabadoInsert("31", "Se supone firme de concreto pulido", "1", 2150),
            AcabadoInsert("32", "Aplanado mortero cemento-arena y pintura", "2", 1700),
            AcabadoInsert("33", "Aplanado fino cemento-arena y pintura", "2", 800),
            AcabadoInsert("34", "Aplanado mortero cemento arena sin pintura", "2", 1650),
            AcabadoInsert("35", "Pasta texturizada", "2", 550),
            AcabadoInsert("36", "Aplanado de yeso y pintura", "2", 1900),
            AcabadoInsert("37", "Block aparente y pintura", "2", 2150),
            AcabadoInsert("38", "Azulejo", "2", 400),
            AcabadoInsert("39", "Aplanado de mezcla, pintura y azulejo en área húmeda", "2", 400),
            AcabadoInsert(
                "40",
                "Aplanado fino de mezcla, pintura y azulejo en área húmeda",
                "2",
                1550
            ),
            AcabadoInsert(
                "41",
                "Aplanado de mezcla sin pintura y azulejo en área húmeda",
                "2",
                1400
            ),
            AcabadoInsert("42", "Pasta texturizada y azulejo en área húmeda", "2", 650),
            AcabadoInsert("43", "Aplanado de yeso con pintura y azulejo en área húmeda", "2", 1150),
            AcabadoInsert("44", "Block aparente con pintura y azulejo en área húmeda", "2", 2500),
            AcabadoInsert("45", "Azulejo a 2.10m y aplanado de mezcla con pintura", "2", 2300),
            AcabadoInsert("46", "Azulejo a 0.90m y aplanado de mezcla con pintura", "2", 100),
            AcabadoInsert("47", "Tirol lanzado", "2", 1000),
            AcabadoInsert("48", "Tirol planchado", "2", 1750),
            AcabadoInsert("49", "Ladrillo aparente", "2", 900),
            AcabadoInsert("50", "Block aparente", "2", 300),
            AcabadoInsert("51", "Barda con enjarre sin reglear", "2", 2350),
            AcabadoInsert("52", "Mortero lanzado", "2", 850),
            AcabadoInsert("53", "Acabado cerroteado", "2", 450),
            AcabadoInsert("54", "Aplanado de mezcla y recubrimientos de granito", "2", 2450),
            AcabadoInsert("55", "Aplanado de mezcla y recubrimientos cerámicos", "2", 2100),
            AcabadoInsert(
                "56",
                "Aplanado de mezcla y recubrimientos de piedra cultivada",
                "2",
                1650
            ),
            AcabadoInsert("57", "Aplanado de mezcla y recubrimientos de cantera", "2", 2050),
            AcabadoInsert("58", "Aplanado de mezcla y azulejo en regular estado", "2", 1600),
            AcabadoInsert("59", "Aplanado de mezcla en regular estado", "2", 800),
            AcabadoInsert("60", "Aplanado de yeso en regular estado", "2", 1100),
            AcabadoInsert("61", "Aplanado de mezcla vandalizado en mal estado", "2", 1600),
            AcabadoInsert("62", "Yeso vandalizado en mal estado", "2", 600),
            AcabadoInsert("63", "Block aparente vandalizado en mal estado", "2", 1850),
            AcabadoInsert("64", "Azulejo vandalizado en mal estado", "2", 1200),
            AcabadoInsert("65", "Lámian acanalada", "2", 2250),
            AcabadoInsert("66", "Se supone aplanado de mezcla con pintura", "2", 300),
            AcabadoInsert("67", "Acabado tipo tirol", "3", 800),
            AcabadoInsert("68", "Tirol planchado", "3", 1950),
            AcabadoInsert("69", "Tirol y yeso con pintura", "3", 1550),
            AcabadoInsert("70", "Yeso acabado pintura", "3", 1700),
            AcabadoInsert("71", "Aplanado fino de yeso y pintura", "3", 2250),
            AcabadoInsert("72", "Yeso acabado esmalte", "3", 1750),
            AcabadoInsert("73", "Aplanado cemento-arena y pintura", "3", 1800),
            AcabadoInsert("74", "Pasta texturizada", "3", 1750),
            AcabadoInsert("75", "Aplanado cemento-arena sin pintura", "3", 300),
            AcabadoInsert("76", "Aplanado fino con molduras de yeso", "3", 1700),
            AcabadoInsert("77", "Adoquín y vigas de madera", "3", 500),
            AcabadoInsert("78", "Lámina y perfiles de madera", "3", 1800),
            AcabadoInsert("79", "Lámina y perfiles metálicos", "3", 500),
            AcabadoInsert("80", "Plafones de poliestireno expandido con cancelería", "3", 750),
            AcabadoInsert("81", "Fibra de vidrio y perfiles metálicos", "3", 2500),
            AcabadoInsert("82", "Teja y perfiles metálicos", "3", 550),
            AcabadoInsert("83", "Lona y perfiles metálicos", "3", 450),
            AcabadoInsert("84", "Mallasombra y perfiles metálicos", "3", 200),
            AcabadoInsert("85", "Insulpanes y perfiles metálicos", "3", 1050),
            AcabadoInsert("86", "Pergolado", "3", 1600),
            AcabadoInsert("87", "Losa aparente sin enjarre", "3", 1350),
            AcabadoInsert("88", "Aplanado de mezcla en regular estado", "3", 100),
            AcabadoInsert("89", "Yeso terminado pintura en regular estado", "3", 350),
            AcabadoInsert("90", "Losa en mal estado con acero expuesto", "3", 650),
            AcabadoInsert("91", "Losa quemada en mal estado", "3", 450),
            AcabadoInsert("92", "Se supone tirol", "3", 2300)
        )

        viewModelScope.launch(Dispatchers.IO) {
            val db = Firebase.firestore
            try {
                for (acabado in acabados) {
                    db.collection("opcion").document(acabado.id).set(acabado).await()
                }
                Log.d("INSERT_ACABADOS", "Todos los acabados se insertaron correctamente")
            } catch (e: Exception) {
                Log.e("INSERT_ACABADOS", "Error al insertar: ${e.message}")
            }
        }
    }*/
    /*
        fun agregarTareas(tarea: AcabadoData) {
            tarea.id = UUID.randomUUID().toString()
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    db.collection("tareas").document(tarea.id).set(tarea).await()
                    _listaTareas.postValue(_listaTareas.value?.plus(tarea))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        fun actualizarTareas(tarea: AcabadoData) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    // db.collection("tareas").document(tarea.id).update(tarea.toMap()).await()
                    _listaTareas.postValue(_listaTareas.value?.map { if (it.id == tarea.id) tarea else it })
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        fun borrarTareas(id: String) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    db.collection("tareas").document(id).delete().await()
                    _listaTareas.postValue(_listaTareas.value?.filter { it.id != id })
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    */
}