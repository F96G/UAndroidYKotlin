package com.example.ejemplotoolbar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    //Declaro una variable toolbar antes creado en MainActivity
    var toolbar: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var botonActividad2 = findViewById<Button>(R.id.bir)

        botonActividad2.setOnClickListener {
            val intent = Intent(this, Actividad2::class.java)
            startActivity(intent)
        }

        toolbar = findViewById(R.id.toolbar)
        //Le agrego un titulo directamente de los recursos string
        toolbar?.setTitle(R.string.app_name)
        //toolbar?.title = "Mi titulo"

        setSupportActionBar(toolbar)
    }

    //Asocia el template.xml a nuestro toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        //Se implementa el menu
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //Permite mapear que hacer respecto al item seleccionado
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.bStar -> {
                Toast.makeText(this, "Elemento agragado a favorito", Toast.LENGTH_SHORT).show()
                return true
            }

            else -> {return super.onOptionsItemSelected(item)}
        }
    }
}