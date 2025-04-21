package mx.edu.itesca.proyectoavaluos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
<<<<<<< HEAD

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegistroAvaluos.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegistroAvaluos : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

=======
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible

class RegistroAvaluos : Fragment() {
>>>>>>> origin/main
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
<<<<<<< HEAD
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registro_avaluos, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegistroAvaluos.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegistroAvaluos().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
=======
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

>>>>>>> origin/main
    }
}