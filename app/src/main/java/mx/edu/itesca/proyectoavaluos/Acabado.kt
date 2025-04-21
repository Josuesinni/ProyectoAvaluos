package mx.edu.itesca.proyectoavaluos

import java.io.Serializable

data class Acabado(var id:String="",var descripcion:String="",val idTipoOpcion:String="",var precio:Long=0L,var estado:Boolean=true,var checked:Boolean=false) {
    /*fun toMap(): Map<String, String> {
        return mapOf("descripcion" to descripcion, "idTipoOpcion" to idTipoOpcion)
    }*/
}

data class AcabadoInsert(var id:String="",var descripcion:String="",val idTipoOpcion:String="",var precio:Long=0L,var estado:Boolean=true){

}
data class AvaluoInmueble(var id:String="",var idAvaluo:String="",var idInmueble:String="",var subtotal:Long=0L){

}
data class OpcionSeleccionada(var id:String="",var idAvaluoInmueble:String="",var idOpcion:String=""){

}