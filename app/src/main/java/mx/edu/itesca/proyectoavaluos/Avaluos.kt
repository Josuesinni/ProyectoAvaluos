package mx.edu.itesca.proyectoavaluos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class Avaluos : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_avaluos, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnNuevoAvaluo: Button =view.findViewById(R.id.btnNewAvaluo)
        btnNuevoAvaluo.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.frame_container,RegistroAvaluos())?.commit()
        }
    }
}