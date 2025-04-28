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
import java.util.UUID

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
        val citaId = arguments?.getString("id")

        val calle = arguments?.getString("calle")
        val colonia = arguments?.getString("colonia")
        val codPostal = arguments?.getInt("codigo_postal")
        val numExterior = arguments?.getInt("num_exterior")

        calle?.let { calle_et.setText(it) }
        colonia?.let { colonia_et.setText(it) }
        codPostal?.let { cod_postal_et.setText(it.toString()) }
        numExterior?.let { num_exterior_et.setText(it.toString()) }

        val btnRegresarAgenda:Button=view.findViewById(R.id.btnRegresarAgenda)
        btnRegresarAgenda.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.frame_container,AgendarCita())?.commit()
        }

        val btnConfirmarVisita:Button=view.findViewById(R.id.btnConfirmarVisita)
        viewModel = ViewModelProvider(requireActivity())[CitaViewModel::class.java]

        btnConfirmarVisita.setOnClickListener {

            val calle = calle_et.text.toString()
            val num_exterior = num_exterior_et.text.toString().toInt()
            val colonia = colonia_et.text.toString()
            val cod_postal = cod_postal_et.text.toString().toInt()

                if(calle.isEmpty() || num_exterior_et.text.isEmpty() || colonia.isEmpty() || cod_postal_et.text.isEmpty()){
                    Toast.makeText(requireContext(), "Por favor, llene todos los campos.", Toast.LENGTH_SHORT).show()
                }else{

                    val cita = Cita(
                        id = citaId?: UUID.randomUUID().toString(),
                        calle = calle,
                        codigo_postal = cod_postal,
                        colonia = colonia,
                        fecha = fecha?:"",
                        idUsuario = "",
                        imagen = "",
                        num_exterior = num_exterior,
                        numero_contacto = numero?:"",
                        titular = nombre?:"",
                        tramite = tramite?:"",
                        estatus = false,
                        estado = true
                    )

                    if(citaId != null){
                        viewModel.actualizarCita(cita)
                    }else{
                        viewModel.agregarCita(cita)
                    }

                    val bundle = Bundle()
                    bundle.putString("id",""+citaId)
                    bundle.putString("calle",""+calle)
                    bundle.putString("cod_postal",""+cod_postal)
                    bundle.putString("colonia",""+colonia)
                    bundle.putString("fecha",""+fecha)
                    bundle.putString("id_usuario","")
                    bundle.putString("imagen","")
                    bundle.putString("num_exterior",""+num_exterior)
                    bundle.putString("num_contacto",""+numero)
                    bundle.putString("nombre",""+nombre)
                    bundle.putString("tramite",""+tramite)
                    bundle.putBoolean("estatus",false)
                    bundle.putBoolean("estado",true)
                    val fragmento = ConfirmacionCita()
                    fragmento.arguments = bundle
                    fragmentManager?.beginTransaction()?.replace(R.id.frame_container,fragmento)?.commit()
                }

            }
    }
}