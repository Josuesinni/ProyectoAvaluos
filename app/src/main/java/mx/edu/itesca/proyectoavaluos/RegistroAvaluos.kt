package mx.edu.itesca.proyectoavaluos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class RegistroAvaluos : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registro_avaluos, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnRegresar: Button =view.findViewById(R.id.btnRegresarAcabados)
        btnRegresar.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.frame_container,Avaluos())?.commit()
        }
        val btnCaracteristicasInmueble: Button =view.findViewById(R.id.btnCaracInm)
        btnCaracteristicasInmueble.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.frame_container,CaracteristicasInmueble())?.commit()
        }
    }
}