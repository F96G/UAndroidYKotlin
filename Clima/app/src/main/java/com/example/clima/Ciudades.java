package com.example.clima;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Ciudades extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ciudades);


        Button btVilla = findViewById(R.id.btVilla);
        Button btCordoba = findViewById(R.id.btCordoba);

       btCordoba.setOnClickListener(this);
        btVilla.setOnClickListener(this);

    }
    public void onClick(View v) {
        String TAG = "com.rungekutta.apclima.ciudades.CIUDAD";



        Intent intent = new Intent(this, MainActivity.class);

        switch (v.getId()){
            case R.id.btCordoba:
                intent.putExtra(TAG, "Cordoba");
                startActivity(intent);
                break;
            case R.id.btVilla:
                intent.putExtra(TAG, "Villa del Rosario");
                startActivity(intent);
                break;
        }

    }
}