package mx.edu.itesca.proyectoavaluos

import com.google.type.Date

data class Cliente(
    var titular: String = "",
    var tramite: String = "",
    var numero_contacto: String = "",
    var fecha: String = ""
){

}
data class Inmueble(

    var calle: String = "",
    var num_exterior: Long = 0L,
    var colonia: String = "",
    var cod_postal: Long = 0L,
    var imagen: String = ""
){

}

data class Cita(
    var id: String = "",
    val cliente: Cliente = Cliente(),
    var inmueble: Inmueble = Inmueble()
){

}