package mx.edu.itesca.proyectoavaluos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

class Citas : Fragment() {
    var listCitas = ArrayList<Cita>()
    lateinit var citasAdapter: CitasAdapter
    private lateinit var viewModel: CitaViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_citas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[CitaViewModel::class.java]
        viewModel.listaCitas.observe(viewLifecycleOwner, { citas ->
            Toast.makeText(context, "Cargando citas...", Toast.LENGTH_SHORT).show()
            listCitas.clear()
            listCitas.addAll(citas)
            citasAdapter.notifyDataSetChanged()
        })

        val lista: ListView = view.findViewById(R.id.listaCitas)
        citasAdapter = CitasAdapter(this, listCitas)
        lista.adapter = citasAdapter

        val btnNuevaCita: Button = view.findViewById(R.id.btnNewCita)
        btnNuevaCita.setOnClickListener {
            val fragment = AgendarCita()
            fragmentManager?.beginTransaction()
                ?.replace(R.id.frame_container, fragment)
                ?.addToBackStack(null)
                ?.commit()
        }
    }
}