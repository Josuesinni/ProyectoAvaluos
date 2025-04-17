package mx.edu.itesca.proyectoavaluos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView

class AcabadosAdapter : BaseAdapter {
    var acabados = ArrayList<Acabado>()
    var contexto: AcabadosInmueble? = null

    constructor(contexto: AcabadosInmueble, acabados: ArrayList<Acabado>) {
        this.acabados = acabados
        this.contexto = contexto
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
        var inflador = LayoutInflater.from(contexto?.context)
        val vista = convertView ?: inflador.inflate(R.layout.lista_acabados, parent, false)

        var acabado = acabados[position]

        var opcion = vista.findViewById<TextView>(R.id.txtName)
        var check = vista.findViewById<CheckBox>(R.id.checkBox)

        opcion.setText(acabado.descripcion)
        check.isChecked = acabado.checked

        check.setOnCheckedChangeListener(null)

        check.setOnCheckedChangeListener { _, isChecked ->
            acabado.checked = isChecked
        }

        return vista
    }
}

