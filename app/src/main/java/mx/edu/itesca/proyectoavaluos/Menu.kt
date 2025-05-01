package mx.edu.itesca.proyectoavaluos

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class Menu : AppCompatActivity() {
    private lateinit var btnNavegacion:BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)

        btnNavegacion=findViewById(R.id.bottom_navigation)
        btnNavegacion.setOnItemSelectedListener { menuItem->
            when(menuItem.itemId){
                R.id.bottom_home->{
                    replaceFragment(Inicio())
                    true
                }
                R.id.bottom_avaluos->{
                    replaceFragment(Avaluos())
                    true
                }
                R.id.bottom_agendar->{
                    replaceFragment(Citas())
                    true
                }
                R.id.bottom_informacion->{
                    replaceFragment(Informacion())
                    true
                }
                R.id.bottom_salir->{
                    borrar_sesion()
                    val intent= Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> {false}
            }
        }
        replaceFragment(Inicio())

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
    }
    //Función que remplaza el fragment del activity_menu
    private fun replaceFragment(fragment:Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frame_container,fragment).commit()
    }
    @SuppressLint("CoroutineCreationDuringComposition")
    fun borrar_sesion() {
        var borrar_sesion: SharedPreferences.Editor = this.getSharedPreferences(
            Login.Global.preferencias_compartidas,
            Context.MODE_PRIVATE
        ).edit()
        borrar_sesion.clear()
        borrar_sesion.apply()
        borrar_sesion.commit()

        Firebase.auth.signOut()
    }
}