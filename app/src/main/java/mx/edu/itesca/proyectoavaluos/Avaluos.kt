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
 * Use the [Avaluos.newInstance] factory method to
 * create an instance of this fragment.
 */
class Avaluos : Fragment() {
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
import android.widget.ListView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider


class Avaluos : Fragment() {
    var listAvaluos = ArrayList<Avaluo>()
    lateinit var avaluosAdapter: AvaluosAdapter
    private lateinit var viewModel: AvaluoViewModel
>>>>>>> origin/main

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_avaluos, container, false)
    }

<<<<<<< HEAD
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Avaluos.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Avaluos().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
=======
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[AvaluoViewModel::class.java]
        viewModel.listaAvaluos.observe(viewLifecycleOwner, { avaluos ->
            Toast.makeText(context, "Cargando...", Toast.LENGTH_SHORT).show()
            listAvaluos.clear()
            listAvaluos.addAll(avaluos)//avaluos.filter { it. == "1" }
            avaluosAdapter.notifyDataSetChanged()

        })
        val lista: ListView = view.findViewById(R.id.listaAvaluos)
        avaluosAdapter = AvaluosAdapter(this, listAvaluos)
        lista.adapter = avaluosAdapter

        val btnNuevoAvaluo: Button =view.findViewById(R.id.btnNewAvaluo)
        btnNuevoAvaluo.setOnClickListener {
            viewModel.agregarAvaluo()
            val bundle = Bundle()
            bundle.putString("idAvaluo",""+viewModel.lastIdAvaluo)
            bundle.putString("folio",""+viewModel.lastFolio)
            bundle.putBoolean("tipo",false)
            val fragmento = RegistroAvaluos()
            fragmento.arguments = bundle
            fragmentManager?.beginTransaction()?.replace(R.id.frame_container, fragmento)?.addToBackStack(null)?.commit()
        }
>>>>>>> origin/main
    }
}