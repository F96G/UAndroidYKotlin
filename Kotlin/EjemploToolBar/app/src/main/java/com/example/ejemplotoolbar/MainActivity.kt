package com.example.ejemplotoolbar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.widget.ShareActionProvider
import androidx.core.view.MenuItemCompat

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

        //Se implementa la busqueda IMPORTANTE VER QUE NO ESTA FUNCIONANDO; la solucion esta en ToolBar actualizado
        val itemBusqueda = menu?.findItem(R.id.busqueda)
        val vistaBusqueda = itemBusqueda?.actionView as SearchView

        //Lo que sale cuando apretamos en search
        vistaBusqueda.queryHint = "Escribe tu nombre"

        //Realizo un listenert para detectar cuando estamos usando el search y cuando no
        vistaBusqueda.setOnQueryTextFocusChangeListener { v, hasFocus ->
        Log.d("LISTENERFOCUS", hasFocus.toString() )}

        //ejecuta alguna accion respecto al texto introducido
        vistaBusqueda.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            //Se va a ejecutar siempre que el texto cambie
            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("OnQueryTextChange", newText.toString())
                return true
            }

            //Se va a ejecutar cuando damos enter
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("OnQueryTextChange", query.toString())
                return true
            }
        })

        //Se implementa el boton de compartir IMPORTANTE VER QUE NO ESTA FUNCIONANDO; la solucion esta en ToolBar actualizado
        val itemShare = menu?.findItem(R.id.share)
        val shareActionProvider = MenuItemCompat.getActionProvider(itemShare) as ShareActionProvider
        //Creo un intent para compartir
        compartirIntent(shareActionProvider)

    }


    private fun compartirIntent(shareActionProvider: ShareActionProvider) {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, "Este es un mensaje compartido")
            shareActionProvider.setShareIntent(intent)
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