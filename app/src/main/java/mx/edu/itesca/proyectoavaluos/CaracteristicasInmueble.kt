package mx.edu.itesca.proyectoavaluos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class CaracteristicasInmueble : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_caracteristicas_inmueble, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnSala: Button =view.findViewById(R.id.btnSala)
        val btnComedor: Button =view.findViewById(R.id.btnComedor)
        val btnCocina: Button =view.findViewById(R.id.btnCocina)
        val btnBanio: Button =view.findViewById(R.id.btnBanio)
        val btnRecamara: Button =view.findViewById(R.id.btnRecamara)
        val btnEstancia: Button =view.findViewById(R.id.btnEstancia)
        val btnPosterior: Button =view.findViewById(R.id.btnPosterior)
        val btnEstacionamiento: Button =view.findViewById(R.id.btnEstacionamiento)
        val btnTerraza: Button =view.findViewById(R.id.btnTerraza)

        val btnRegresar:Button=view.findViewById(R.id.btnRegresoRegistroAcabados)
        btnRegresar.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.frame_container,RegistroAvaluos())?.commit()
        }
        var idAvaluo:String=""
        val argumentos = getArguments()
        if (argumentos != null) {
            idAvaluo=(argumentos.getString("idAvaluo"))!!
        }
        val acabadosInmueble=AcabadosInmueble()
        val bundle = Bundle()
        bundle.putString("idAvaluo",idAvaluo)
        btnSala.setOnClickListener {
            bundle.putString("idInmueble","1")
            bundle.putString("titulo","Sala")
            bundle.putInt("imagen",R.drawable.sala)
            acabadosInmueble.setArguments(bundle)
            fragmentManager?.beginTransaction()?.replace(R.id.frame_container,acabadosInmueble)?.commit()
        }

        btnComedor.setOnClickListener {
            bundle.putString("idInmueble","2")
            bundle.putString("titulo","Comedor")
            bundle.putInt("imagen",R.drawable.comedor)
            acabadosInmueble.setArguments(bundle)
            fragmentManager?.beginTransaction()?.replace(R.id.frame_container,acabadosInmueble)?.commit()
        }

        btnCocina.setOnClickListener {
            bundle.putString("idInmueble","2")
            bundle.putString("titulo","Cocina")
            bundle.putInt("imagen",R.drawable.cocina)
            acabadosInmueble.setArguments(bundle)
            fragmentManager?.beginTransaction()?.replace(R.id.frame_container,acabadosInmueble)?.commit()
        }
        btnBanio.setOnClickListener {
            bundle.putString("idInmueble","4")
            bundle.putString("titulo","Baño")
            bundle.putInt("imagen",R.drawable.bathroom)
            acabadosInmueble.setArguments(bundle)
            fragmentManager?.beginTransaction()?.replace(R.id.frame_container,acabadosInmueble)?.commit()
        }
        btnRecamara.setOnClickListener {
            bundle.putString("idInmueble","5")
            bundle.putString("titulo","Recamara")
            bundle.putInt("imagen",R.drawable.recamara)
            acabadosInmueble.setArguments(bundle)
            fragmentManager?.beginTransaction()?.replace(R.id.frame_container,acabadosInmueble)?.commit()
        }
        btnEstancia.setOnClickListener {
            bundle.putString("idInmueble","6")
            bundle.putString("titulo","Estancia")
            bundle.putInt("imagen",R.drawable.estancia)
            acabadosInmueble.setArguments(bundle)
            fragmentManager?.beginTransaction()?.replace(R.id.frame_container,acabadosInmueble)?.commit()
        }
        btnPosterior.setOnClickListener {
            bundle.putString("idInmueble","7")
            bundle.putString("titulo","Patio Posterior")
            bundle.putInt("imagen",R.drawable.patio_posterior)
            acabadosInmueble.setArguments(bundle)
            fragmentManager?.beginTransaction()?.replace(R.id.frame_container,acabadosInmueble)?.commit()
        }
        btnEstacionamiento.setOnClickListener {
            bundle.putString("idInmueble","8")
            bundle.putString("titulo","Estacionamiento")
            bundle.putInt("imagen",R.drawable.estacionamiento)
            acabadosInmueble.setArguments(bundle)
            fragmentManager?.beginTransaction()?.replace(R.id.frame_container,acabadosInmueble)?.commit()
        }
        btnTerraza.setOnClickListener {
            bundle.putString("idInmueble","9")
            bundle.putString("titulo","Terraza")
            bundle.putInt("imagen",R.drawable.terraza)
            acabadosInmueble.setArguments(bundle)
            fragmentManager?.beginTransaction()?.replace(R.id.frame_container,acabadosInmueble)?.commit()
        }
    }
}