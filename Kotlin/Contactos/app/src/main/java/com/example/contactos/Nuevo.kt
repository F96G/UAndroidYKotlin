package com.example.contactos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AlertDialog


class Nuevo : AppCompatActivity() {
    //Variable de foto a seleccionar
    var fotoIndex:Int = 0
    //Lista de fotos
    val fotos = arrayOf(R.drawable.foto_01,R.drawable.foto_02,R.drawable.foto_03,R.drawable.foto_04,R.drawable.foto_05,R.drawable.foto_06)
    var foto:ImageView? = null
    //index lo utilizo para saber si estoy editando un elemento (el valor de index) o creando uno nuevo
    var index:Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo)

        var actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        //Creo un evento en foto
        foto = findViewById<ImageView>(R.id.ivFotoNuevo)
        foto?.setOnClickListener{
            seleccionarFoto()
        }

        //reconocer si estoy eliminando o editando
        if (intent.hasExtra("ID")){
            index = intent.getStringExtra("ID")?.toInt()!!
            rellenarDatos(index)
        }

    }

    private fun rellenarDatos(index: Int) {

        val contacto = MainActivity.obtenerContacto(index)

        val nombre = findViewById<EditText>(R.id.etNombreNuevo)
        val apellido = findViewById<EditText>(R.id.etApellidoNuevo)
        val estado = findViewById<EditText>(R.id.etEstadoNuevo)
        val direccion = findViewById<EditText>(R.id.etDireccionNuevo)
        val edad = findViewById<EditText>(R.id.etEdadNuevo)
        val email = findViewById<EditText>(R.id.etEmailNuevo)
        val peso = findViewById<EditText>(R.id.etPesoNuevo)
        val telefono = findViewById<EditText>(R.id.etTelefonoNuevo)
        val foto = findViewById<ImageView>(R.id.ivFotoNuevo)

        nombre.setText(contacto.nombre, TextView.BufferType.EDITABLE)
        apellido.setText(contacto.apellido, TextView.BufferType.EDITABLE)
        estado.setText(contacto.estado, TextView.BufferType.EDITABLE)
        direccion.setText(contacto.direccion, TextView.BufferType.EDITABLE)
        edad.setText(contacto.edad.toString(), TextView.BufferType.EDITABLE)
        email.setText(contacto.email, TextView.BufferType.EDITABLE)
        peso.setText(contacto.peso.toString(), TextView.BufferType.EDITABLE)
        telefono.setText(contacto.telefono, TextView.BufferType.EDITABLE)

        foto.setImageResource(contacto.foto)

        var posicion = 0
        for (foto in fotos){
            if (contacto.foto == foto){
                fotoIndex = posicion
            }
            posicion++
        }
    }

    fun seleccionarFoto(){
        val builder =AlertDialog.Builder(this)
        builder.setTitle("Selecciona imagen de perfil")
        //Boton de cancelar la seleccion
        builder.setNegativeButton("Cancelar"){
            dialog, which -> dialog.dismiss()
        }

        val adapadorDeDialogo = ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item)
        adapadorDeDialogo.add("Foto 01")
        adapadorDeDialogo.add("Foto 02")
        adapadorDeDialogo.add("Foto 03")
        adapadorDeDialogo.add("Foto 04")
        adapadorDeDialogo.add("Foto 05")
        adapadorDeDialogo.add("Foto 06")

        //una vez seleccionado de la lista que hacer (el elemento es which)
        builder.setAdapter(adapadorDeDialogo){
            dialog, which ->
            //Cambio fotoIndex para saber que foto se selecciono
            fotoIndex = which
            //Cambio la foto mostrada en Nuevo
            foto?.setImageResource(obtenerFoto(fotoIndex))
        }
        builder.show()
    }

    fun obtenerFoto(index: Int):Int{
        return fotos.get(index)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_nuevo, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            //Soluciona el problema de que se vuelve a crear la actividad main al volver atras
            R.id.home -> {
                finish()
                return true
            }

            R.id.iCrearNuevo ->{
                //Crear un nuevo elemento de tipo contacto
                val nombre = findViewById<EditText>(R.id.etNombreNuevo)
                val apellido = findViewById<EditText>(R.id.etApellidoNuevo)
                val estado = findViewById<EditText>(R.id.etEstadoNuevo)
                val direccion = findViewById<EditText>(R.id.etDireccionNuevo)
                val edad = findViewById<EditText>(R.id.etEdadNuevo)
                val email = findViewById<EditText>(R.id.etEmailNuevo)
                val peso = findViewById<EditText>(R.id.etPesoNuevo)
                val telefono = findViewById<EditText>(R.id.etTelefonoNuevo)

                //Validar campos(Valida que todos los campos esten llenos)
                var campos = ArrayList<String>()
                campos.add(nombre.text.toString())
                campos.add(apellido.text.toString())
                campos.add(estado.text.toString())
                campos.add(direccion.text.toString())
                campos.add(edad.text.toString())
                campos.add(email.text.toString())
                campos.add(peso.text.toString())
                campos.add(telefono.text.toString())

                var flag = 0
                for (campo in campos){
                    if (campo.isNullOrEmpty())
                        flag++
                }

                if (flag > 0)
                    Toast.makeText(this,"Rellena todos los campos",Toast.LENGTH_SHORT).show()
                else {
                    if (index > -1)
                        MainActivity.editarContacto(index , Contacto(nombre.text.toString(), apellido.text.toString(), estado.text.toString(),
                            edad.text.toString().toInt(), peso.text.toString().toFloat(), direccion.text.toString(),
                            telefono.text.toString(), email.text.toString(), obtenerFoto(fotoIndex)))
                    else
                        MainActivity.agregarContacto(Contacto(nombre.text.toString(), apellido.text.toString(), estado.text.toString(),
                                edad.text.toString().toInt(), peso.text.toString().toFloat(), direccion.text.toString(),
                             telefono.text.toString(), email.text.toString(), obtenerFoto(fotoIndex)))
                    finish()
                }
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }
}