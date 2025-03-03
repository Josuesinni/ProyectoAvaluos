package mx.edu.itesca.proyectoavaluos

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class AgendarCita : Fragment() {

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_agendar_cita, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnSiguiente:Button=view.findViewById(R.id.btn_AgendaSiguiente)
        btnSiguiente.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.frame_container,AgendarCitaInmueble())?.commit()
        }
    }
}