package com.example.toolbaractualizado

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private var favorito:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    /*Recibe por argumento un menu item (favorito) y lo cambia
     */
    private fun setFavoriteIcon(menuItem:MenuItem){
        //La variable id almacena alguno de las dos imagenes
        val id = if (favorito) R.drawable.favorito
        else R.drawable.favorito_borde

        //setea la imagen al icono
        menuItem.icon = ContextCompat.getDrawable(this, id)
    }

    /*Se ejecutan todas las acciones en el MENU a la hora de crear la actividad
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //Cambia el menu por defecto por el nuevo
        menuInflater.inflate(R.menu.menu, menu)

        //Inicializa uno de los iconos de favorito
        setFavoriteIcon(menu?.findItem(R.id.favorito)!!)

        return super.onCreateOptionsMenu(menu)
    }

    /*Se ejecuta al hacer click en algun item
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            //Si hago click sobre favorito este llama a setFavoriteIcon para cambiar el icono
            R.id.favorito -> {
                favorito = !favorito
                setFavoriteIcon(item)
            }
            //Si hago click sobre share creo un intent que voy a enviar
            R.id.share ->{
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND//La accion del intent
                    putExtra(Intent.EXTRA_TEXT, "Esto se esta por enviar")//Lo que voy a enviar
                    type = "text/plain"
                }

                //Creo la actividad y la envio
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }
            //Si hago click en salir salgo
            R.id.salir -> finish()
        }

        return super.onOptionsItemSelected(item)
    }
}