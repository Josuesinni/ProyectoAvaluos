package mx.edu.itesca.proyectoavaluos

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegistroViewModel: ViewModel() {
    private val db = Firebase.firestore;
    private var database: DatabaseReference
    init {
        database = Firebase.database.reference
    }

    fun agregarRegistro(uid: String, registro: Registros) {
        FirebaseFirestore.getInstance().collection("usuario")
            .document(uid)
            .set(registro)
            .addOnSuccessListener {
                Log.d("RegistroViewModel", "Datos guardados correctamente")
            }
            .addOnFailureListener {
                Log.e("RegistroViewModel", "Error al guardar: ${it.message}")
            }
    }

}