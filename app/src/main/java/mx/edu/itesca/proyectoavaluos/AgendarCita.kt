package mx.edu.itesca.proyectoavaluos

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

        val nombre = arguments?.getString("nombre")
        val tramite = arguments?.getString("tramite")
        val numero = arguments?.getString("numero")
        val fecha = arguments?.getString("fecha")
        val citaId = arguments?.getString("id")
        Log.d("Cita",citaId.toString())
        val tipo = arguments?.getBoolean("tipo")

        if (!nombre.isNullOrEmpty()) nombreEditText.setText(nombre)
        if (!numero.isNullOrEmpty()) numeroEditText.setText(numero)
        if (!fecha.isNullOrEmpty()) fechaEditText.setText(fecha)
        if (!tramite.isNullOrEmpty()) {
            val adapter = tramiteSpinner.adapter
            for (i in 0 until adapter.count) {
                if (adapter.getItem(i).toString() == tramite) {
                    tramiteSpinner.setSelection(i)
                    break
                }
            }
        }
        val btnRegresar:Button=view.findViewById(R.id.btnRegresarCita)
        btnRegresar.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
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
                    "id" to citaId,
                    "nombre" to nombre,
                    "tramite" to tramite,
                    "numero" to numero,
                    "fecha" to fecha,
                    "calle" to arguments?.getString("calle"),
                    "colonia" to arguments?.getString("colonia"),
                    "codigo_postal" to arguments?.getInt("codigo_postal"),
                    "num_exterior" to arguments?.getInt("num_exterior")
                )
                bundle.putBoolean("tipo", tipo == true)
                val fragment = AgendarCitaInmueble()
                fragment.arguments = bundle

                parentFragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
}