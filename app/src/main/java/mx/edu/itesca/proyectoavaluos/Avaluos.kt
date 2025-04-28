package mx.edu.itesca.proyectoavaluos

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider


class Avaluos : Fragment() {
    var listAvaluos = ArrayList<Avaluo>()
    lateinit var avaluosAdapter: AvaluosAdapter
    private lateinit var viewModel: AvaluoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_avaluos, container, false)
    }


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
        avaluosAdapter = AvaluosAdapter(this, listAvaluos,{ idAvaluo ->
            viewModel.eliminarAvaluo(idAvaluo)
        })
        lista.adapter = avaluosAdapter

        val btnNuevoAvaluo: Button =view.findViewById(R.id.btnNewAvaluo)
        btnNuevoAvaluo.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("idAvaluo",viewModel.lastIdAvaluo)
            bundle.putString("folio",viewModel.lastFolio.toString())
            bundle.putBoolean("tipo",false)
            viewModel.agregarAvaluo()
            val fragmento = RegistroAvaluos()
            fragmento.arguments = bundle
            fragmentManager?.beginTransaction()?.replace(R.id.frame_container, fragmento)?.addToBackStack(null)?.commit()
        }

    }
}