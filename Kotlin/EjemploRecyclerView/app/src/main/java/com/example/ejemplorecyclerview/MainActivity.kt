package com.example.ejemplorecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainActivity : AppCompatActivity() {
    var lista:RecyclerView? = null
    var adaptador:AdaptadorCustom? = null
    //Sirve para determinar como se vera nuestro layout (vartical, horizontal, etc..)
    var layoutManager:RecyclerView.LayoutManager? = null
    //Se utilizara para saber si estamos en modo contextual, este modo permite utilizar una toolbar alternativa
    var isActionMode = false
    //Lo utilizo para saber la cantidad de elementos seleccionados
    var actionMode:ActionMode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val platillos = ArrayList<Platillo>()
        platillos.add(Platillo("platillo 1", 250.0, 3.5F, R.drawable.platillo01))
        platillos.add(Platillo("platillo 2", 250.0, 4.5F, R.drawable.platillo02))
        platillos.add(Platillo("platillo 3", 250.0, 3.0F, R.drawable.platillo03))
        platillos.add(Platillo("platillo 4", 250.0, 2.0F, R.drawable.platillo04))
        platillos.add(Platillo("platillo 5", 250.0, 5.0F, R.drawable.platillo05))
        platillos.add(Platillo("platillo 6", 250.0, 3.0F, R.drawable.platillo06))
        platillos.add(Platillo("platillo 7", 250.0, 4.0F, R.drawable.platillo07))
        platillos.add(Platillo("platillo 8", 250.0, 1.5F, R.drawable.platillo08))
        platillos.add(Platillo("platillo 9", 250.0, 0.5F, R.drawable.platillo09))
        platillos.add(Platillo("platillo 10", 250.0, 0.0F, R.drawable.platillo10))

        lista = findViewById(R.id.lista)
        //tiene un tamaño fijo pàra optimizar el uso de recursos
        lista?.setHasFixedSize(true)

        layoutManager = LinearLayoutManager(this)
        //Desde donde se dibuja ese layout
        lista?.layoutManager = layoutManager

        val callback = object: ActionMode.Callback{

            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                //Inicializa el action mode
                adaptador!!.iniciarActionMode()
                actionMode = mode
                //Cambio el menu por defecto al creado
                menuInflater.inflate(R.menu.menu_contextual, menu!!)
                return true
            }

            //Sirve para preparar el menu
            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                mode?.title = "0 seleccionados"
                return false
            }

            //Cuando damos click a la toolbar a algun elemento
            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                when (item?.itemId) {
                    R.id.eliminar -> {
                        adaptador?.eliminarSeleccionados()
                    }
                    else -> return true
                }
                adaptador?.terminarActionMode()
                mode?.finish()
                isActionMode = false

                return true
            }

            //Cuando se destruye el cation mode
            override fun onDestroyActionMode(mode: ActionMode?) {
                adaptador?.destruirActionMode()
                isActionMode = false
            }
        }

        //Aqui no solo aplico el adaptador sino que tambien determino las acciones al hacer click,
        //para que al hacer click se seleccione en el template debo agregar  android:clickable="true" android:background="?attr/selectableItemBackground"
        adaptador = AdaptadorCustom( platillos, object:ClickListener{
            override fun onClick(vista: View, index: Int) {
                Toast.makeText(applicationContext, platillos.get(index).nombre, Toast.LENGTH_SHORT).show()
            }
            //Aqui implemento para al hacer click sostenido ejecute una accion
        }, object: LongClickListener{
            override fun longClick(vista: View, index: Int) {
                //Al hacer la accion de mantener apretado el elemento entro en modo contextual
                if (!isActionMode){
                    startSupportActionMode(callback)
                    isActionMode = true
                    adaptador?.seleccionarItem(index)
                }else{
                    //Hacer selecciones o deselecciones, se pasa el indice del elemento
                    adaptador?.seleccionarItem(index)
                }
                actionMode?.title = adaptador?.obtenerNumeroElementosSeleccionados().toString() + " seleccionados"
            }

        })
        lista?.adapter = adaptador



        //Para utilizar el refresh debo implementar en gradle build 'androidx.swiperefreshlayout:swiperefreshlayout:1.0.0'
        val swipeToRefresh = findViewById<SwipeRefreshLayout>(R.id.swipeToRefresh)
        //Defino que hacer cuando hago refresh
        swipeToRefresh.setOnRefreshListener {
            for (i in 1..100000000){

            }
            //Apagar swipe
            swipeToRefresh.isRefreshing = false
            platillos.add(Platillo("Nugets",25.5,3.5F,R.drawable.platillo01))
            adaptador?.notifyDataSetChanged()
        }
    }
}