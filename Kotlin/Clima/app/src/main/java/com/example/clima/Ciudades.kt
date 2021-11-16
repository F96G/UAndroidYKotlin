package com.example.clima

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class Ciudades : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ciudades)
        val TAG = "com.RK.Clima.ciudades.CIUDAD"

        val bCordoba = findViewById<Button>(R.id.bt_Cordoba)
        val bVilla = findViewById<Button>(R.id.bt_villaDelRosario)
        val bShanghai = findViewById<Button>(R.id.bt_Shanghai)

        bCordoba.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            intent.putExtra(TAG,"3860259")//Es el valor de la ciudad para hacer la peticion HTTP de OpenWheater
            startActivity(intent)
        })

        bVilla.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            intent.putExtra(TAG,"3427454")
            startActivity(intent)
        })

        bShanghai.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(TAG,"1796236")
            startActivity(intent)
        })

    }
}