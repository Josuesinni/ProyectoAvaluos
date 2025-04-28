package mx.edu.itesca.proyectoavaluos

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import mx.edu.itesca.proyectoavaluos.databinding.ActivityRegistroBinding
import java.util.UUID

class Registro : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    private lateinit var binding: ActivityRegistroBinding
    private lateinit var viewModel: RegistroViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[RegistroViewModel::class.java]

        auth = Firebase.auth

        val usuario: EditText = binding.etUsuario
        val password: EditText = binding.etPassword
        val confPass: EditText = binding.etConfirmar
        val tel: EditText = binding.etTel
        val email: EditText = binding.etEmail
        val errorTv: TextView = binding.tvrError
        val btnRegistro: Button =binding.btnRegistrar

        errorTv.visibility = View.INVISIBLE

        btnRegistro.setOnClickListener {

            if(email.text.isEmpty() || password.text.isEmpty() || confPass.text.isEmpty() || usuario.text.isEmpty() || tel.text.isEmpty()){
                errorTv.text = "Todos los campos deben de ser llenados"
                errorTv.visibility = View.VISIBLE
            } else {
                errorTv.visibility = View.INVISIBLE

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnSuccessListener { authResult ->
                        val uid = authResult.user?.uid
                        if (uid != null) {
                            val registro = Registros(
                                id = UUID.randomUUID().toString(),
                                email = email.text.toString(),
                                contraseña = password.text.toString(),
                                telefono = tel.text.toString(),
                                usuario = usuario.text.toString()
                            )
                            viewModel.agregarRegistro(uid, registro)

                            email.setText("")
                            password.setText("")
                            confPass.setText("")
                            tel.setText("")
                            usuario.setText("")

                            Toast.makeText(this, "Registro realizado", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, Menu::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, "Error: UID nulo", Toast.LENGTH_LONG).show()
                        }
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Error al registrar usuario: ${e.message}", Toast.LENGTH_LONG).show()
                    }
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
}
}