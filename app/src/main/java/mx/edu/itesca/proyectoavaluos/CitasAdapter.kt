package mx.edu.itesca.proyectoavaluos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView

class CitasAdapter: BaseAdapter {
    var citas = ArrayList<Cita>()
    var contexto: Citas? = null

    constructor(contexto: Citas, citas: ArrayList<Cita>) {
        this.citas = citas
        this.contexto = contexto
    }

    override fun getCount(): Int = citas.size

    override fun getItem(position: Int): Any = citas[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var inflador = LayoutInflater.from(contexto?.context)
        val vista = convertView ?: inflador.inflate(R.layout.lista_citas, parent, false)

        val cita = citas[position]

        val tvNombre = vista.findViewById<TextView>(R.id.tvNombre)
        val tvFecha = vista.findViewById<TextView>(R.id.tvFecha)
        val tvEstatus = vista.findViewById<TextView>(R.id.tvEstatus)
        val btnEditar = vista.findViewById<ImageButton>(R.id.btn_editar_cita)
        val btnEliminar = vista.findViewById<ImageButton>(R.id.btn_eliminar_cita)

        tvNombre.text = cita.titular
        tvFecha.text = cita.fecha
        val estadoTexto = if (cita.estatus) "Confirmada" else "Pendiente"
        tvEstatus.text = estadoTexto

        btnEditar.setOnClickListener {
            // Aquí pondrías el código para editar la cita
        }

        btnEliminar.setOnClickListener {
            // Aquí pondrías el código para eliminar la cita
        }

        return vista
    }
}