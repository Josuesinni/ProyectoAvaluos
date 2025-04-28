package mx.edu.itesca.proyectoavaluos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

class CitaViewModel:ViewModel() {

    //private val db = Firebase.firestore;
    private val db = FirebaseFirestore.getInstance()
    private val _listaCitas= MutableLiveData<List<Cita>>(emptyList())
    val listaCitas: LiveData<List<Cita>> get() = _listaCitas

    /*init{
        obtenerCitas()
    }*/

    fun obtenerCitas(){
        /*viewModelScope.launch(Dispatchers.IO){
            try{
                val resultado=db.collection("visita").get().await()

                val citas=resultado.documents.mapNotNull { it.toObject(Cita::class.java) }
                _listaCitas.postValue(citas)

            }catch (e:Exception){
                e.printStackTrace()
            }
        }*/
        db.collection("visita")
            .get()
            .addOnSuccessListener { result ->
                val citas = result.map { document ->
                    document.toObject(Cita::class.java).copy(id = document.id)
                }
                _listaCitas.value = citas
            }
            .addOnFailureListener { e ->
                Log.e("CitasViewModel", "Error al obtener citas: ${e.message}")
            }
    }

    fun agregarCitas(cita:Cita){
        /*cita.id= UUID.randomUUID().toString()
        viewModelScope.launch(Dispatchers.IO){
            try{
                db.collection("visita").document(cita.id).set(cita).await()
                _listaCitas.postValue(_listaCitas.value?.plus(cita))

            }catch (e:Exception){
                e.printStackTrace()
            }
        }*/
        db.collection("visita")
            .add(cita)
            .addOnSuccessListener {
                obtenerCitas() // Volver a cargar la lista después de agregar
            }
            .addOnFailureListener { e ->
                Log.e("CitasViewModel", "Error al agregar: ${e.message}")
            }
    }

    fun borrarCitas(id: String){
        /*viewModelScope.launch(Dispatchers.IO){
            try{
                db.collection("visita").document(id).delete().await()
                _listaCitas.postValue(_listaCitas.value?.filter {it.id != id})
            }catch (e: Exception){
                e.printStackTrace()
            }
        }*/
        db.collection("visita").document(id)
            .delete()
            .addOnSuccessListener {
                obtenerCitas() // Volver a cargar la lista después de borrar
            }
            .addOnFailureListener { e ->
                Log.e("CitasViewModel", "Error al eliminar: ${e.message}")
            }

    }
}