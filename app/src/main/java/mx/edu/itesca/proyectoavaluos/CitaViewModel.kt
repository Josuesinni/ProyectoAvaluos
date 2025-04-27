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

class CitaViewModel : ViewModel() {
    private val db = Firebase.firestore
    private var _listaCitas = MutableLiveData<List<Cita>>(emptyList())
    val listaCitas: LiveData<List<Cita>> = _listaCitas

    init {
        obtenerCitas()
    }

    fun obtenerCitas() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val resultado = db.collection("visita").get().await()
                val citas = resultado.documents
                    .mapNotNull { it.toObject(Cita::class.java) }
                _listaCitas.postValue(citas)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun agregarCita(cita: Cita) {
        val citaId = UUID.randomUUID().toString()
        cita.id = citaId
        viewModelScope.launch(Dispatchers.IO) {
            try {
                db.collection("visita").document(cita.id).set(cita).await()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}