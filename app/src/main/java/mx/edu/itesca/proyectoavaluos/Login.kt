package mx.edu.itesca.proyectoavaluos

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings.Global
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

class Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    object Global{
        var preferencias_compartidas="sharedPreferences"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        auth = Firebase.auth

        val usuario: EditText = findViewById(R.id.et_usuario)
        val password: EditText = findViewById(R.id.et_password)
        val errorTv: TextView = findViewById(R.id.tvError)

        val buttonLoginAc: Button = findViewById(R.id.btnIniciarSesion)
        val buttonRegistrarAc: Button = findViewById(R.id.btnRegistrar)
        errorTv.visibility = View.INVISIBLE
        buttonRegistrarAc.setOnClickListener {
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }
        buttonLoginAc.setOnClickListener {
            var password = password.text.toString()
            var usuario = usuario.text.toString()
            if (password != "") {
                if (usuario != "") {
                    login(usuario, password)
                }
            } else {
                Toast.makeText(this, "Escribe la contraseña", Toast.LENGTH_LONG).show()
            }

        }
    }

    fun goToInicio(user: FirebaseUser){
        val intent=Intent(this,Menu::class.java)
        //intent.putExtra("user",user.email)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    fun showError(text:String="",visible:Boolean){
        val error: TextView =findViewById(R.id.tvError)
        error.text=text
        error.visibility = if(visible) View.VISIBLE else View.INVISIBLE
    }

    fun guardar_sesion(correo: String, proveedor: String) {
        var guardar_sesion: SharedPreferences.Editor = this.getSharedPreferences(
            Global.preferencias_compartidas,
            Context.MODE_PRIVATE
        ).edit()
        guardar_sesion.putString("Correo",correo);
        guardar_sesion.putString("Proveedor",proveedor);
        guardar_sesion.apply()
        guardar_sesion.commit()
    }

    fun login(email:String,password:String){
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this){
                task->
            if (task.isSuccessful){
                val user=auth.currentUser
                guardar_sesion(task.result.user?.email.toString(),"Usuario/Contraseña")
                showError(visible = false)
                goToInicio(user!!)
            }else{
                showError("Usuario y/o contraseña equivocados",true)
            }
        }
    }
}