package mx.edu.itesca.proyectoavaluos

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.FirebaseApp

class AcabadosInmueble : Fragment() {
    var pisos = ArrayList<Acabado>()
    var muros = ArrayList<Acabado>()
    var plafones = ArrayList<Acabado>()
    lateinit var pisosAdapter: AcabadosAdapter
    lateinit var murosAdapter: AcabadosAdapter
    lateinit var plafonesAdapter: AcabadosAdapter
    private lateinit var viewModel: AcabadoViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_acabados_inmueble, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val imagen = view.findViewById<ImageView>(R.id.imagenAcabados)
        val titulo = view.findViewById<TextView>(R.id.tituloAcabados)
        val argumentos = getArguments()

        var idAvaluo: String = ""
        var idInmueble: String = ""
        if (argumentos != null) {
            imagen.setImageResource(argumentos.getInt("imagen"))
            titulo.setText("Acabados " + argumentos.getString("titulo"))
            idAvaluo = argumentos.getString("idAvaluo")!!
            idInmueble = argumentos.getString("idInmueble")!!

            //Registrar avaluo_inmuble
        }

        viewModel = ViewModelProvider(this)[AcabadoViewModel::class.java]
        viewModel.listaAcabados.observe(viewLifecycleOwner, { acabados ->
            Toast.makeText(context, "Cargando...", Toast.LENGTH_SHORT).show()
            pisos.clear()
            pisos.addAll(acabados.filter { it.idTipoOpcion == "1" })

            muros.clear()
            muros.addAll(acabados.filter { it.idTipoOpcion == "2" })

            plafones.clear()
            plafones.addAll(acabados.filter { it.idTipoOpcion == "3" })

            pisosAdapter.notifyDataSetChanged()
            murosAdapter.notifyDataSetChanged()
            plafonesAdapter.notifyDataSetChanged()
        })
        val lista: ListView = view.findViewById(R.id.listaAcabados)


        pisosAdapter = AcabadosAdapter(this, pisos)
        murosAdapter = AcabadosAdapter(this, muros)
        plafonesAdapter = AcabadosAdapter(this, plafones)

        lista.adapter = pisosAdapter


        lista.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val dataModel: Acabado = pisos!![position]
            dataModel.checked = !dataModel.checked
            pisosAdapter.notifyDataSetChanged()
        }

        val btnGuardarAcabados = view.findViewById<Button>(R.id.btnGuardarAcabados)
        btnGuardarAcabados.setOnClickListener {
            val pisos = pisosAdapter.acabados.filter { it.checked }
            val muros = murosAdapter.acabados.filter { it.checked }
            val plafones = plafonesAdapter.acabados.filter { it.checked }
            val opcionSeleccionada = ArrayList<OpcionSeleccionada>()
            for (piso in pisos) {
                opcionSeleccionada.add(OpcionSeleccionada("",idInmueble,piso.id))
                Log.d(
                    "PISO",
                    idInmueble + " | " + piso.id + " | " + piso.descripcion + " | " + piso.precio
                )
            }
            for (piso in muros) {
                opcionSeleccionada.add(OpcionSeleccionada("",idInmueble,piso.id))
                Log.d(
                    "MURO",
                    idInmueble + " " + piso.id + " " + piso.descripcion + " " + piso.precio
                )
            }
            for (piso in plafones) {
                opcionSeleccionada.add(OpcionSeleccionada("",idInmueble,piso.id))
                Log.d(
                    "PLAFÓN",
                    idInmueble + " " + piso.id + " " + piso.descripcion + " " + piso.precio
                )
            }
            viewModel.agregarOpcionSeleccionada(opcionSeleccionada)
            fragmentManager?.beginTransaction()
                ?.replace(R.id.frame_container, CaracteristicasInmueble())?.commit()
        }
        val btnRegresar = view.findViewById<Button>(R.id.btnRegresarCaracteristicasInmueble)

        btnRegresar.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.frame_container, CaracteristicasInmueble())?.commit()
        }

        val btnOpcionesAcabados =
            view.findViewById<BottomNavigationView>(R.id.bottom_opciones_acabado)
        btnOpcionesAcabados.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottom_pisos -> {
                    lista.adapter = pisosAdapter
                    lista.onItemClickListener =
                        AdapterView.OnItemClickListener { _, _, position, _ ->
                            val dataModel: Acabado = pisos!![position]
                            dataModel.checked = !dataModel.checked
                            pisosAdapter.notifyDataSetChanged()
                        }
                    true
                }

                R.id.bottom_muros -> {
                    lista.adapter = murosAdapter
                    lista.onItemClickListener =
                        AdapterView.OnItemClickListener { _, _, position, _ ->
                            val dataModel: Acabado = muros!![position]
                            dataModel.checked = !dataModel.checked
                            murosAdapter.notifyDataSetChanged()
                        }
                    true
                }

                R.id.bottom_plafones -> {
                    lista.adapter = plafonesAdapter
                    lista.onItemClickListener =
                        AdapterView.OnItemClickListener { _, _, position, _ ->
                            val dataModel: Acabado = plafones!![position]
                            dataModel.checked = !dataModel.checked
                            plafonesAdapter.notifyDataSetChanged()
                        }
                    true
                }

                else -> {
                    false
                }
            }
        }
    }

}