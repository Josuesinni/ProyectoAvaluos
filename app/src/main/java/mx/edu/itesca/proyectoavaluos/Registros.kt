package mx.edu.itesca.proyectoavaluos

data class Registros(var id:String="", var email:String="", var contraseña:String="", var telefono:String="",var usuario:String=""){
    fun toMap(): Map<String,String>{
        return mapOf(
            "email" to email  , "contraseña" to contraseña, "telefono" to telefono, "usuario" to usuario
        )
    }
}
