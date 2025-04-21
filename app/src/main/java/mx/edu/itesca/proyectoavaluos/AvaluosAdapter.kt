package mx.edu.itesca.proyectoavaluos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView

class AvaluosAdapter: BaseAdapter {
    var avaluos = ArrayList<Avaluo>()
    var contexto: Avaluos? = null

    constructor(contexto: Avaluos, avaluos: ArrayList<Avaluo>) {
        this.avaluos = avaluos
        this.contexto = contexto
    }

    override fun getCount(): Int {
        return avaluos.size
    }

    override fun getItem(position: Int): Any {
        return avaluos[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var inflador = LayoutInflater.from(contexto?.context)
        val vista = convertView ?: inflador.inflate(R.layout.lista_avaluos, parent, false)

        var avaluo = avaluos[position]

        var folio = vista.findViewById<TextView>(R.id.tvFolio)
        var total = vista.findViewById<TextView>(R.id.tvTotal)
        var estado = vista.findViewById<TextView>(R.id.tvEstado)
        var editar = vista.findViewById<ImageButton>(R.id.btn_editar)
        var eliminar = vista.findViewById<ImageButton>(R.id.btn_eliminar)

        folio.setText(""+avaluo.folio)
        total.setText("$"+avaluo.total)
        val estatus = if (avaluo.estatus) "Aprobado" else "Sin aprobar"
        estado.setText(estatus)
        editar.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("idAvaluo",""+avaluo.id)
            bundle.putString("folio",""+avaluo.folio)
            bundle.putBoolean("tipo",true)
            val fragmento = RegistroAvaluos()
            fragmento.arguments = bundle
            contexto?.fragmentManager?.beginTransaction()?.replace(R.id.frame_container, fragmento)?.addToBackStack(null)?.commit()
        }
        return vista
    }

}