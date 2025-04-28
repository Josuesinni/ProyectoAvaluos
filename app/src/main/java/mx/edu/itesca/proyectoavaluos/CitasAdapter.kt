package mx.edu.itesca.proyectoavaluos

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentActivity

class CitasAdapter(
    private val context: Context,
    private var citas: ArrayList<Cita>,
    private val onEliminarCita: ((String) -> Unit)?
) : BaseAdapter() {

    override fun getCount(): Int = citas.size

    override fun getItem(position: Int): Any = citas[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflador = LayoutInflater.from(context)
        val vista = convertView ?: inflador.inflate(R.layout.lista_citas, parent, false)

        val cita = citas[position]

        val tvNombre = vista.findViewById<TextView>(R.id.tvNombre)
        val tvFecha = vista.findViewById<TextView>(R.id.tvFecha)
        val tvEstatus = vista.findViewById<TextView>(R.id.tvEstatus)
        val btnEditar = vista.findViewById<ImageButton>(R.id.btn_editar_cita)
        val btnEliminar = vista.findViewById<ImageButton>(R.id.btn_eliminar_cita)

        tvNombre.setText(""+cita.titular)
        tvFecha.setText(""+cita.fecha)
        val estatus = if (cita.estatus) "Confirmada" else "Pendiente"
        tvEstatus.setText(estatus)

        btnEditar.setOnClickListener {
            val bundle = Bundle().apply {
                /*putString("id",""+cita.id)
                putString("nombre",""+cita.titular)
                putString("fecha", ""+cita.fecha)*/
                putString("id", cita.id)
                putString("nombre", cita.titular)
                putString("tramite", cita.tramite)
                putString("numero", cita.numero_contacto)
                putString("fecha", cita.fecha)
                putString("calle", cita.calle)
                putString("colonia", cita.colonia)
                putInt("codigo_postal", cita.codigo_postal)
                putInt("num_exterior", cita.num_exterior)
                putBoolean("estado", true)
            }
            val fragmento = AgendarCita()
            fragmento.arguments = bundle
            if (context is FragmentActivity) {
                context.supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragmento)
                    .addToBackStack(null)
                    .commit()
            }
        }


        btnEliminar.setOnClickListener {
            onEliminarCita?.invoke(cita.id)
        }

        return vista
    }
}

