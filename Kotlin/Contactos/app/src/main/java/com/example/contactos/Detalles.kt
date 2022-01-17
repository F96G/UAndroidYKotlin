package com.example.contactos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView

class Detalles : AppCompatActivity() {
    var index:Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles)

        //Habilita el boton de regresar antes en Manifest debo hubicar android:parentActivityName=".MainActivity"
        var actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        //Recibo un intent con la posicion del elemento
        index = intent.getStringExtra("ID")?.toInt()

        mapearDatos()
    }

    fun mapearDatos(){
        val contacto = MainActivity.obtenerContacto(index!!)

        val tvNombre = findViewById<TextView>(R.id.tvNombreDetalle)
        val tvEstado = findViewById<TextView>(R.id.tvEstadoDetalle)
        val tvDireccion = findViewById<TextView>(R.id.tvDireccionDetalle)
        val tvEdad = findViewById<TextView>(R.id.tvEdadDetalle)
        val tvEmail = findViewById<TextView>(R.id.tvEmailDetalle)
        val tvPeso = findViewById<TextView>(R.id.tvPesoDetalle)
        val tvTelefono = findViewById<TextView>(R.id.tvTelefonoDetalle)
        val ivFoto = findViewById<ImageView>(R.id.ivFotoNuevo)

        tvNombre.text = contacto.nombre + " " + contacto.apellido
        tvEstado.text = contacto.estado
        tvDireccion.text = contacto.direccion
        tvEdad .text = contacto.edad.toString() + " Años"
        tvEmail.text = contacto.email
        tvPeso.text = contacto.peso.toString() + " Kg"
        tvTelefono.text = contacto.telefono
        ivFoto.setImageResource(contacto.foto)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalles, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            //Soluciona el problema de que se vuelve a crear la actividad main al volver atras
            R.id.home -> {
                finish()
                return true
            }

            R.id.iEliminarDetalle -> {
                MainActivity.eliminarContacto(index!!)
                finish()
                return true
            }

            R.id.iEditarDetalle -> {
                //Se reutiliza la actividad de nuevo
                val intent = Intent(this, Nuevo::class.java)
                //De esta forma puedo avisar que quiero editar un elemento y no elimnarlo
                intent.putExtra("ID", index.toString())
                startActivity(intent)
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()

        mapearDatos()
    }
}