package com.example.clima;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvCiudad = findViewById(R.id.tvCiudad);
        TextView tvGrados = findViewById(R.id.tvTemperatura);
        TextView tvEstado = findViewById(R.id.tvEstado);

        Ciudad ciudadCordoba = new Ciudad("Cordoba", 25, "Soleado");
        Ciudad ciudadVilla = new Ciudad("Villa del Rosario", 20, "Nublado");


        String ciudad = getIntent().getStringExtra("com.rungekutta.apclima.ciudades.CIUDAD");

        switch (ciudad){
            case "Cordoba":
                tvCiudad.setText(ciudadCordoba.ciudad);
                tvGrados.setText(ciudadCordoba.grados + "°");
                tvEstado.setText(ciudadCordoba.estado);
                break;
            case "Villa del Rosario":
                tvCiudad.setText(ciudadVilla.ciudad);
                tvGrados.setText(ciudadVilla.grados + "°");
                tvEstado.setText(ciudadVilla.estado);
                break;
        }

        }


}