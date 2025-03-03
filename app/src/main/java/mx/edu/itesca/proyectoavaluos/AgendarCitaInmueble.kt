package mx.edu.itesca.proyectoavaluos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class AgendarCitaInmueble : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_agendar_cita_inmueble, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnRegresarAgenda:Button=view.findViewById(R.id.btnRegresarAgenda)
        btnRegresarAgenda.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.frame_container,AgendarCita())?.commit()
        }

        val btnSiguiente:Button=view.findViewById(R.id.btnConfirmarVisita)
        btnSiguiente.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.frame_container,ConfirmacionCita())?.commit()
        }
    }
}