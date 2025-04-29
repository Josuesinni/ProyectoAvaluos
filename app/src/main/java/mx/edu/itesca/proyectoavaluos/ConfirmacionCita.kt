package mx.edu.itesca.proyectoavaluos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class ConfirmacionCita : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_confirmacion_cita, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnInicioAgenda: Button =view.findViewById(R.id.btnInicioAgenda)
        btnInicioAgenda.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.frame_container,Citas())?.commit()
        }
    }
}