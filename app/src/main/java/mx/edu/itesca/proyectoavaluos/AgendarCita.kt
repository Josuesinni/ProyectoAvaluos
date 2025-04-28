package mx.edu.itesca.proyectoavaluos

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.core.os.bundleOf

class AgendarCita : Fragment() {

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_agendar_cita, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnSiguiente: Button = view.findViewById(R.id.btn_AgendaSiguiente)

        val nombreEditText: EditText = view.findViewById(R.id.editTextText2)
        val tramiteSpinner: Spinner = view.findViewById(R.id.spr_tramite)
        val numeroEditText: EditText = view.findViewById(R.id.editTextNumber)
        val fechaEditText: EditText = view.findViewById(R.id.editTextDate)

        btnSiguiente.setOnClickListener {
            val nombre = nombreEditText.text.toString().trim()
            val tramite = tramiteSpinner.selectedItem.toString()
            val numero = numeroEditText.text.toString().trim()
            val fecha = fechaEditText.text.toString().trim()

            // Validaciones
            if (nombre.isEmpty() || tramite.isEmpty() || numero.isEmpty() || fecha.isEmpty()) {
                Toast.makeText(requireContext(), "Por favor llena todos los campos.", Toast.LENGTH_SHORT).show()
            } else {
                // Si todo está bien, mandamos los datos al siguiente fragment
                val bundle = bundleOf(
                    "nombre" to nombre,
                    "tramite" to tramite,
                    "numero" to numero,
                    "fecha" to fecha
                )

                val fragment = AgendarCitaInmueble()
                fragment.arguments = bundle

                fragmentManager?.beginTransaction()
                    ?.replace(R.id.frame_container, fragment)
                    ?.addToBackStack(null)
                    ?.commit()
            }
        }
    }
}