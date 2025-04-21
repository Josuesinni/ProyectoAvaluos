package mx.edu.itesca.proyectoavaluos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

class AvaluoViewModel:ViewModel() {
    private val db = Firebase.firestore
    private var _listaAvaluos = MutableLiveData<List<Avaluo>>(emptyList())
    val listaAvaluos: LiveData<List<Avaluo>> = _listaAvaluos
    var lastIdAvaluo:String=""
    var lastFolio:Long=0L
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
    fun getUltimoFolio(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val resultado = db.collection("avaluo").get().await()
                val folio = resultado.documents
                    .mapNotNull { it.toObject(Avaluo::class.java) }
                    .sortedBy { it.folio }.first().folio
                lastFolio=folio
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun agregarAvaluo(){
        getUltimoFolio()
        var avaluo=Avaluo(UUID.randomUUID().toString(),"",lastFolio+1,0,false,true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                db.collection("avaluo").document(avaluo.id).set(avaluo).await()
                lastIdAvaluo= avaluo.id
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}