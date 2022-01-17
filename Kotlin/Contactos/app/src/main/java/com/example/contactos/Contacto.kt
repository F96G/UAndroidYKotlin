package com.example.contactos

class Contacto(nombre:String,apellido:String, estado:String, edad:Int, peso:Float, direccion:String, telefono:String, email:String, foto:Int) {
    var nombre:String = ""
    var apellido:String = ""
    var estado:String = ""
    var edad:Int = 0
    var peso:Float = 0.0F
    var direccion:String = ""
    var telefono:String = ""
    var email:String = ""
    var foto:Int = 0

    init {
        this.nombre = nombre
        this.apellido = apellido
        this.direccion = direccion
        this.edad = edad
        this.estado = estado
        this.peso = peso
        this.direccion = direccion
        this.telefono = telefono
        this.email = email
        this.foto = foto
    }
}