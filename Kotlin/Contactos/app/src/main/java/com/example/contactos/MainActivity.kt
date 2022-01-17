package com.example.contactos

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.SearchView
import android.widget.Switch
import android.widget.ViewSwitcher

class MainActivity : AppCompatActivity() {

    var lista:ListView? = null
    var viewSwitcher:ViewSwitcher? = null

    //Lo agrgado aqui no se elimina nunca
    companion object{
        var adaptador:AdaptadorCustom? = null
        var inicio:Boolean = true
        var contactos:ArrayList<Contacto>? = null

        fun agregarContacto(contacto:Contacto){
            adaptador?.addItem(contacto)
        }
        fun obtenerContacto(index:Int):Contacto{
            return adaptador?.getItem(index) as Contacto
        }

        fun eliminarContacto(index: Int){
            adaptador?.removeItem(index)
        }

        fun editarContacto(index: Int, nuevoContacto: Contacto){
            adaptador?.updateItem(index, nuevoContacto)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (inicio) {
            contactos = ArrayList()
            contactos?.add(Contacto("Julieta", "Oliva", "Fachera", 20, 70.5F,
                    "Tucuman 123", "3573-455567", "julituwachiturra@hotmail.com", R.drawable.foto_05))
            contactos?.add(Contacto("Fabio", "Gazzoni", "Soltero", 25, 82.4F,
                "Rio Negro 1530", "3573-430932", "fabin@hotmail.com", R.drawable.foto_02))
            contactos?.add(Contacto("Silvia", "Strumia", "Soltera", 53, 70.5F,
                "Rio Negro 1530", "3573-424456", "silvia@hotmail.com", R.drawable.foto_06))
            inicio = false
        }

        lista = findViewById<ListView>(R.id.lista)
        adaptador = AdaptadorCustom(this, contactos!!)
        viewSwitcher = findViewById(R.id.viewSwitcher)

        lista?.adapter = adaptador
        //Determina una accion al seleccionar un item de la lista
        lista?.setOnItemClickListener { parent, view, position, id ->
                val intent = Intent(this, Detalles::class.java)
            intent.putExtra("ID", position.toString())
            startActivity(intent)
        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        //Elementos de busqueda busqueda
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val itemBusqueda = menu?.findItem(R.id.iBuscar)
        val searchView = itemBusqueda?.actionView as SearchView
        //Elementos de swich
        //val itemSwich = menu.findItem(R.id.viewSwitcher)
        //Contengo el elemento XML
        //itemSwich.setActionView(R.layout.switch_item)  <------- Falta solucionar este problema, buscar en internet
        //val switchView = itemSwich?.actionView?.findViewById<Switch>(R.id.sCambiaVista)

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Buscar contacto..."
        //Preparamos los datos
        searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->

        }
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            //Filtrar datos
            override fun onQueryTextChange(newText: String?): Boolean {
                adaptador?.filtar(newText!!)
                return true
            }
            //Filtar datos
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
        })


        //switchView?.setOnCheckedChangeListener { buttonView, isChecked ->
          //  viewSwitcher?.showNext()
        //}

            return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.iNuevo ->{
                val intent = Intent(this, Nuevo::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        //Refresca la informacion de lista de contactos
        adaptador?.notifyDataSetChanged()
    }
}