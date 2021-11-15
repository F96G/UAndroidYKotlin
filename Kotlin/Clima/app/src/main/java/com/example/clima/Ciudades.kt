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

        bCordoba.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            intent.putExtra(TAG,"ciudad-cordoba")
            startActivity(intent)
        })

        bVilla.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            intent.putExtra(TAG,"ciudad-villaDelRosario")
            startActivity(intent)
        })
    }
}