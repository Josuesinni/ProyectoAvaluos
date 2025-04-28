package mx.edu.itesca.proyectoavaluos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

class AgendarCitaInmueble : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_agendar_cita_inmueble, container, false)
    }

    private lateinit var viewModel: CitaViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nombre = arguments?.getString("nombre")
        val tramite = arguments?.getString("tramite")
        val numero = arguments?.getString("numero")
        val fecha = arguments?.getString("fecha")
        val calle_et: EditText = view.findViewById(R.id.nombreCalle)
        val num_exterior_et: EditText = view.findViewById(R.id.numeroExt)
        val colonia_et: EditText = view.findViewById(R.id.Colonia)
        val cod_postal_et: EditText = view.findViewById(R.id.CodigoPostal)


        val btnRegresarAgenda:Button=view.findViewById(R.id.btnRegresarAgenda)
        btnRegresarAgenda.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.frame_container,AgendarCita())?.commit()
        }

        val btnConfirmarVisita:Button=view.findViewById(R.id.btnConfirmarVisita)
        viewModel = ViewModelProvider(requireActivity())[CitaViewModel::class.java]

        btnConfirmarVisita.setOnClickListener {

            val calle = calle_et.text.toString()
            val num_exterior = num_exterior_et.text.toString()
            val num_exerior_num = if(calle.isNotEmpty()) num_exterior.toInt() else 0
            val colonia = colonia_et.text.toString()
            val cod_postal = cod_postal_et.text.toString()
            val cod_postal_num = if(calle.isNotEmpty()) cod_postal.toInt() else 0

            if(calle.isEmpty() || num_exterior.isEmpty() || colonia.isEmpty() || cod_postal.isEmpty()){
                Toast.makeText(requireContext(), "Por favor, llene todos los campos.", Toast.LENGTH_SHORT).show()
            }else{

                val cita = Cita(
                    calle = calle,
                    codigo_postal = cod_postal_num,
                    colonia = colonia,
                    fecha = fecha.toString(),
                    idUsuario = "",
                    imagen = "",
                    num_exterior = num_exerior_num,
                    numero_contacto = numero.toString(),
                    titular = nombre.toString(),
                    tramite = tramite.toString(),
                    estatus = false,
                    estado = true
                )

                viewModel.agregarCita(cita)

                val bundle = Bundle()
                bundle.putString("nombre",""+nombre)
                bundle.putString("fecha",""+fecha)
                bundle.putBoolean("estado",true)
                val fragmento = ConfirmacionCita()
                fragmento.arguments = bundle
                fragmentManager?.beginTransaction()?.replace(R.id.frame_container,fragmento)?.commit()
            }

        }
    }
}