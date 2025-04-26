package mx.edu.itesca.proyectoavaluos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible

class RegistroAvaluos : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registro_avaluos, container, false)
    }
    var idAvaluo:String=""
    var folioAvaluo:String=""
    var tipo:Boolean=false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val argumentos = getArguments()
        if (argumentos != null) {
            tipo=(argumentos.getBoolean("tipo"))!!
            idAvaluo=(argumentos.getString("idAvaluo"))!!
            folioAvaluo=(argumentos.getString("folio"))!!
            val folio=view.findViewById<TextView>(R.id.folio1_av)
            folio.setText(folioAvaluo)

            val titulo=view.findViewById<TextView>(R.id.tv_Titulo)
            val descripcion=view.findViewById<TextView>(R.id.tv_Descripcion)
            if(tipo){
                titulo.setText("¡Edición de Avalúo!")
                descripcion.isVisible=false
            }

        }
        val btnRegresar: Button =view.findViewById(R.id.btnRegresarAcabados)
        btnRegresar.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        val btnCaracteristicasInmueble: Button =view.findViewById(R.id.btnCaracInm)


        btnCaracteristicasInmueble.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("idAvaluo",idAvaluo)
            bundle.putString("folio",folioAvaluo)
            bundle.putBoolean("tipo",tipo)
            val fragmento = CaracteristicasInmueble()
            fragmento.arguments = bundle
            fragmentManager?.beginTransaction()?.replace(R.id.frame_container, fragmento)?.addToBackStack(null)?.commit()
        }
        val btnCaracteristicasEntorno: Button =view.findViewById(R.id.btnCaracEnt)
        btnCaracteristicasEntorno.setOnClickListener {
            Toast.makeText(context,"Función en desarrollo...",Toast.LENGTH_LONG).show()
        }


    }
}