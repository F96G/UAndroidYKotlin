package com.example.solicitudeshttp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import okhttp3.Call
import okhttp3.OkHttpClient
import java.io.IOException

class MainActivity : AppCompatActivity(), CompletadoListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var btRed = findViewById<Button>(R.id.bt_Red)
        var btSolicitud = findViewById<Button>(R.id.bt_solicitudHTTP)
        var btVolley = findViewById<Button>(R.id.bt_volley)
        var btOk = findViewById<Button>(R.id.bt_solicitudHTTP)

        btRed.setOnClickListener {
            if (Network.hayRed(this))
                Toast.makeText(this, "Si hay red", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "No hay internet", Toast.LENGTH_SHORT).show()
        }

        btSolicitud.setOnClickListener{
            if (Network.hayRed(this))
                //Log.d("bSolicitudOnCLick",descargarDatos("http://www.google.com")
                    DescargaURL(this).execute("http://www.google.com")
            else
                Toast.makeText(this, "No hay internet", Toast.LENGTH_SHORT).show()
        }


        btVolley.setOnClickListener{
            if (Network.hayRed(this))
                solicitudesHTPPVolley("http://www.google.com")
            else
                Toast.makeText(this, "No hay internet", Toast.LENGTH_SHORT).show()
        }

        btOk.setOnClickListener{
            if (Network.hayRed(this))
                solicitudHTTPOkHTTP("http://www.google.com")
            else
                Toast.makeText(this, "No hay internet", Toast.LENGTH_SHORT).show()
        }
    }

    //Metodo para volley
    private fun solicitudesHTPPVolley(url:String){
        val cola = Volley.newRequestQueue(this)
        val solicitud = StringRequest(Request.Method.GET, url, {
            response ->
            try {
                Log.d("solicitudHTTPVolley", response)
            }catch (e:Exception){

            }
        }, {  })

        cola.add(solicitud)
    }

    //Metodo para OkHTTP
    private fun solicitudHTTPOkHTTP(url:String){
        val cliente = OkHttpClient()
        val solicitud = okhttp3.Request.Builder().url(url).build()

        cliente.newCall(solicitud).enqueue(object: okhttp3.Callback{

            override fun onFailure(call: Call, e: IOException?) {

            }

            override fun onResponse(call: Call, response: okhttp3.Response) {
                val resultado = response.body()!!.string()//No funciona el body()?

                this@MainActivity.runOnUiThread {
                    try {
                            Log.d("solicitudHTTPokHTTP", resultado)
                    }catch (e:Exception){}
                }
            }
        })
    }

    override fun descargaCompleta(resultado: String) {
        Log.d("descargaCompleta",resultado)
    }


}
