package mx.edu.itesca.proyectoavaluos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView

class CitaAdapter(
    var  listaCitas: List<Cita>,
        var onBorrarClic: (String) -> Unit
    ): RecyclerView.Adapter<CitaAdapter.ViewHolder>(){
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val tvNombre: TextView = itemView.findViewById(R.id.txtNombre)
        val tvFecha: TextView = itemView.findViewById(R.id.txtFecha)
        val tvDireccion: TextView = itemView.findViewById(R.id.txtDireccion)
        val btnBorrar: Button = itemView.findViewById(R.id.ibtnButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_cita,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listaCitas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cita = listaCitas[position]
        val direccion = cita.inmueble.calle+ ", " +cita.inmueble.num_exterior+ ", " +cita.inmueble.colonia+ ", " + cita.inmueble.cod_postal
        holder.tvNombre.text = cita.cliente.titular
        holder.tvDireccion.text = direccion
        holder.tvFecha.text = cita.cliente.fecha

        holder.btnBorrar.setOnClickListener {
            onBorrarClic(cita.id)
        }

    }
}

