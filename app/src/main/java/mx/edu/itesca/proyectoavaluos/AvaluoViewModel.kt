package mx.edu.itesca.proyectoavaluos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

class AvaluoViewModel : ViewModel() {
    private val db = Firebase.firestore
    private var _listaAvaluos = MutableLiveData<List<Avaluo>>(emptyList())
    val listaAvaluos: LiveData<List<Avaluo>> = _listaAvaluos
    var lastIdAvaluo: String = ""
    var lastFolio: Long = 0L

    init {
        obtenerAcabados()
    }

    fun obtenerAcabados() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val resultado = db.collection("avaluo").get().await()
                val avaluos = resultado.documents
                    .mapNotNull { it.toObject(Avaluo::class.java) }
                    .sortedBy { it.folio }
                _listaAvaluos.postValue(avaluos)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun agregarAvaluo(): Pair<String, Long>? {
        return try {
            val ultimoFolio = _listaAvaluos.value?.maxOfOrNull { it.folio } ?: 0L
            val nuevoFolio = ultimoFolio + 1
            lastFolio = nuevoFolio
            var avaluo = Avaluo(UUID.randomUUID().toString(), "", nuevoFolio, 0, false, true)

            db.collection("avaluo").document(avaluo.id).set(avaluo).await()
            lastIdAvaluo = avaluo.id
            val listaActual = _listaAvaluos.value?.toMutableList() ?: mutableListOf()
            listaActual.add(avaluo)
            _listaAvaluos.postValue(listaActual)
            Pair(avaluo.id,nuevoFolio)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

    }

    fun eliminarAvaluo(idAvaluo: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val datosAvaluoInmueble = db.collection("avaluo_inmueble")
                    .whereEqualTo("idAvaluo", idAvaluo)
                    .get()
                    .await()

                val avaluosInmueble = datosAvaluoInmueble.documents

                for (avaluoInmuebleDoc in avaluosInmueble) {
                    val idAvaluoInmueble = avaluoInmuebleDoc.id

                    val opcionesSeleccionadas = db.collection("opciones_seleccionadas")
                        .whereEqualTo("idAvaluoInmueble", idAvaluoInmueble)
                        .get()
                        .await()

                    for (opcionDoc in opcionesSeleccionadas.documents) {
                        db.collection("opciones_seleccionadas").document(opcionDoc.id).delete()
                            .await()
                    }

                    db.collection("avaluo_inmueble").document(idAvaluoInmueble).delete().await()
                }
                db.collection("avaluo").document(idAvaluo).delete().await()
                _listaAvaluos.postValue(_listaAvaluos.value?.filter { it.id != idAvaluo })
                Log.d("ELIMINAR_AVALUO", "Se ha eliminado el avalúo exitsoamente")
            } catch (e: Exception) {
                Log.e("ELIMINAR_AVALUO", "Error al eliminar: ${e.message}", e)
            }
        }
    }

}