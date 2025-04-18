package mx.edu.itesca.proyectoavaluos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        val btnIniciar: Button =findViewById(R.id.btnIniciarSesion)
        val btnRegistro: Button =findViewById(R.id.btnRegistrar)

        btnIniciar.setOnClickListener {
            var intent: Intent = Intent(this,Menu::class.java)
            startActivity(intent)
        }

        btnRegistro.setOnClickListener {
            var intent: Intent = Intent(this,Registro::class.java)
            startActivity(intent)
        }

    }
}