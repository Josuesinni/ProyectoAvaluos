package mx.edu.itesca.proyectoavaluos

data class Cita( var id: String = "",
                 var calle: String = "",
                 var codigo_postal: Int = 0,
                 var colonia: String = "",
                 var fecha: String = "",
                 var idUsuario: String = "",
                 var imagen:String="",
                 var num_exterior:Int=0,
                 var numero_contacto:String="",
                 var titular:String="",
                 var tramite:String="",
                 var estatus: Boolean = true,
                 var estado: Boolean = true)
