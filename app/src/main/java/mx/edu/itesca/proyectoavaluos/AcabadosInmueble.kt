package mx.edu.itesca.proyectoavaluos

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.content.Intent
import android.widget.Button

class AcabadosInmueble : Fragment() {
    var acabados=ArrayList<Acabado>()
    var pisos=ArrayList<Acabado>()
    var muros=ArrayList<Acabado>()
    var plafones=ArrayList<Acabado>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_acabados_inmueble, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pisosAdapter = AcabadosAdapter(this,pisos!!)
        val murosAdapter = AcabadosAdapter(this,muros!!)
        val plafonesAdapter = AcabadosAdapter(this,plafones!!)
        val lista: ListView = view.findViewById(R.id.listaAcabados)

        cargarAcabadosPisos()
        cargarAcabadosMuros()
        cargarAcabadosPlafones()

        val imagen=view.findViewById<ImageView>(R.id.imagenAcabados)
        val titulo=view.findViewById<TextView>(R.id.tituloAcabados)

        lista.adapter = pisosAdapter
        val argumentos=getArguments()
        if (argumentos != null) {
            imagen.setImageResource(argumentos.getInt("imagen"))
            titulo.setText("Acabados "+argumentos.getString("titulo"))
        }
        lista.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val dataModel: Acabado = pisos!![position]
            dataModel.checked = !dataModel.checked
            pisosAdapter.notifyDataSetChanged()
        }

        val btnRegresar=view.findViewById<Button>(R.id.btnRegresarCaracteristicasInmueble)

        btnRegresar.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.frame_container,CaracteristicasInmueble())?.commit()
        }

        val btnOpcionesAcabados=view.findViewById<BottomNavigationView>(R.id.bottom_opciones_acabado)
        btnOpcionesAcabados.setOnItemSelectedListener { menuItem->
            when(menuItem.itemId){
                R.id.bottom_pisos->{
                    lista.adapter = pisosAdapter
                    lista.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                        val dataModel: Acabado = pisos!![position]
                        dataModel.checked = !dataModel.checked
                        pisosAdapter.notifyDataSetChanged()
                    }
                    true
                }
                R.id.bottom_muros->{
                    lista.adapter = murosAdapter
                    lista.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                        val dataModel: Acabado = muros!![position]
                        dataModel.checked = !dataModel.checked
                        murosAdapter.notifyDataSetChanged()
                    }
                    true
                }
                R.id.bottom_plafones->{
                    lista.adapter = plafonesAdapter
                    lista.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                        val dataModel: Acabado = plafones!![position]
                        dataModel.checked = !dataModel.checked
                        plafonesAdapter.notifyDataSetChanged()
                    }
                    true
                }
                else -> {false}
            }
        }
    }

    fun cargarAcabadosPisos(){
        pisos.add(Acabado("Loseta cerámica",false))
        pisos.add(Acabado("Loseta cerámica con zoclos",false))
        pisos.add(Acabado("Loseta cerámica rectificada con zoclos",false))
        pisos.add(Acabado("Loseta vinílica",false))
        pisos.add(Acabado("Mosaico cerámico",false))
        pisos.add(Acabado("Laminado imitación duela",false))
        pisos.add(Acabado("Duela de cedro",false))
        pisos.add(Acabado("Alfombra",false))
        pisos.add(Acabado("Loseta antiderrapante",false))
        pisos.add(Acabado("Loseta cerámica y antiderrapante en zona húmeda",false))
        pisos.add(Acabado("Concreto pulido y antiderrapante en zona húmeda",false))
        pisos.add(Acabado("Loseta vinílica y antiderrapante en zona húmeda",false))
        pisos.add(Acabado("Mosaico cerámico y antiderrapante en zona húmeda",false))
        pisos.add(Acabado("Firme de concreto y antiderrapante en zona húmeda",false))
        pisos.add(Acabado("Loseta cerámica y firme de concreto en zona húmeda",false))
        pisos.add(Acabado("Mosaico cerámico y firme de concreto en zona húmeda",false))
        pisos.add(Acabado("Firme de concreto pulido",false))
        pisos.add(Acabado("Firme de concreto común",false))
        pisos.add(Acabado("Firme de concreto escobillado",false))
        pisos.add(Acabado("Firme de concreto lavado",false))
        pisos.add(Acabado("Huellas de concreto",false))
        pisos.add(Acabado("Adoquín",false))
        pisos.add(Acabado("Tierra y andadores de concreto",false))
        pisos.add(Acabado("Tierra",false))
        pisos.add(Acabado("Cantera",false))
        pisos.add(Acabado("Pasto sintético",false))
        pisos.add(Acabado("Pasto",false))
        pisos.add(Acabado("Firme de concreto con tratamiento epóxico",false))
        pisos.add(Acabado("Firme de concreto sin reglear",false))
        pisos.add(Acabado("Empedrado",false))
        pisos.add(Acabado("Se supone firme de concreto pulido",false))
        pisos.add(Acabado("Alta de otro",false))
    }
    fun cargarAcabadosMuros(){
        muros.add(Acabado("Aplanado mortero cemento-arena y pintura",false))
        muros.add(Acabado("Aplanado fino cemento-arena y pintura",false))
        muros.add(Acabado("Aplanado mortero cemento arena sin pintura",false))
        muros.add(Acabado("Pasta texturizada",false))
        muros.add(Acabado("Aplanado de yeso y pintura",false))
        muros.add(Acabado("Block aparente y pintura",false))
        muros.add(Acabado("Azulejo",false))
        muros.add(Acabado("Aplanado de mezcla, pintura y azulejo en área húmeda",false))
        muros.add(Acabado("Aplanado fino de mezcla, pintura y azulejo en área húmeda",false))
        muros.add(Acabado("Aplanado de mezcla sin pintura y azulejo en área húmeda",false))
        muros.add(Acabado("Pasta texturizada y azulejo en área húmeda",false))
        muros.add(Acabado("Aplanado de yeso con pintura y azulejo en área húmeda",false))
        muros.add(Acabado("Block aparente con pintura y azulejo en área húmeda",false))
        muros.add(Acabado("Azulejo a 2.10m y aplanado de mezcla con pintura",false))
        muros.add(Acabado("Azulejo a 0.90m y aplanado de mezcla con pintura",false))
        muros.add(Acabado("Tirol lanzado",false))
        muros.add(Acabado("Tirol planchado",false))
        muros.add(Acabado("Ladrillo aparente",false))
        muros.add(Acabado("Block aparente",false))
        muros.add(Acabado("Barda con enjarre sin reglear",false))
        muros.add(Acabado("Mortero lanzado",false))
        muros.add(Acabado("Acabado cerroteado",false))
        muros.add(Acabado("Aplanado de mezcla y recubrimientos de granito",false))
        muros.add(Acabado("Aplanado de mezcla y recubrimientos cerámicos",false))
        muros.add(Acabado("Aplanado de mezcla y recubrimientos de piedra cultivada",false))
        muros.add(Acabado("Aplanado de mezcla y recubrimientos de cantera",false))
        muros.add(Acabado("Aplanado de mezcla y azulejo en regular estado",false))
        muros.add(Acabado("Aplanado de mezcla en regular estado",false))
        muros.add(Acabado("Aplanado de yeso en regular estado",false))
        muros.add(Acabado("Aplanado de mezcla vandalizado en mal estado",false))
        muros.add(Acabado("Yeso vandalizado en mal estado",false))
        muros.add(Acabado("Block aparente vandalizado en mal estado",false))
        muros.add(Acabado("Azulejo vandalizado en mal estado",false))
        muros.add(Acabado("Lámian acanalada",false))
        muros.add(Acabado("Se supone aplanado de mezcla con pintura",false))
    }
    fun cargarAcabadosPlafones(){
        plafones.add(Acabado("Acabado tipo tirol",false))
        plafones.add(Acabado("Tirol planchado",false))
        plafones.add(Acabado("Tirol y yeso con pintura",false))
        plafones.add(Acabado("Yeso acabado pintura",false))
        plafones.add(Acabado("Aplanado fino de yeso y pintura",false))
        plafones.add(Acabado("Yeso acabado esmalte",false))
        plafones.add(Acabado("Aplanado cemento-arena y pintura",false))
        plafones.add(Acabado("Pasta texturizada",false))
        plafones.add(Acabado("Aplanado cemento-arena sin pintura",false))
        plafones.add(Acabado("Aplanado fino con molduras de yeso",false))
        plafones.add(Acabado("Adoquín y vigas de madera",false))
        plafones.add(Acabado("Lámina y perfiles de madera",false))
        plafones.add(Acabado("Lámina y perfiles metálicos",false))
        plafones.add(Acabado("Plafones de poliestireno expandido con cancelería",false))
        plafones.add(Acabado("Fibra de vidrio y perfiles metálicos",false))
        plafones.add(Acabado("Teja y perfiles metálicos",false))
        plafones.add(Acabado("Lona y perfiles metálicos",false))
        plafones.add(Acabado("Mallasombra y perfiles metálicos",false))
        plafones.add(Acabado("Insulpanes y perfiles metálicos",false))
        plafones.add(Acabado("Pergolado",false))
        plafones.add(Acabado("Losa aparente sin enjarre",false))
        plafones.add(Acabado("Aplanado de mezcla en regular estado",false))
        plafones.add(Acabado("Yeso terminado pintura en regular estado",false))
        plafones.add(Acabado("Losa en mal estado con acero expuesto",false))
        plafones.add(Acabado("Losa quemada en mal estado",false))
        plafones.add(Acabado("Se supone tirol",false))
    }
}

    class AcabadosAdapter:BaseAdapter{
        var acabados=ArrayList<Acabado>()
        var contexto:AcabadosInmueble?=null
        constructor(contexto: AcabadosInmueble, acabados: ArrayList<Acabado>){
            this.acabados=acabados
            this.contexto=contexto
        }
        override fun getCount(): Int {
            return acabados.size
        }

        override fun getItem(position: Int): Acabado {
            return acabados[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var acabado=acabados[position]
            var inflador=LayoutInflater.from(contexto?.context)
            var vista = inflador.inflate(R.layout.lista_acabados,null)

            var opcion=vista.findViewById<TextView>(R.id.txtName)
            var check=vista.findViewById<CheckBox>(R.id.checkBox)

            opcion.setText(acabado.texto)
            check.isChecked=acabado.checked

            return vista
        }
    }