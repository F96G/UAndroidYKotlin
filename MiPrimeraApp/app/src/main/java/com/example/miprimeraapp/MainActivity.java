package com.example.miprimeraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button boton = findViewById(R.id.btBonton);
        boton.setOnClickListener(this);
    }

    protected boolean  validarDato(){
        EditText nombre = findViewById(R.id.etNombre);
        if(nombre.getText().toString().matches("")){
            return false;
        }else {
            return true;
        }
    }

    public void onClick(View v) {

        EditText nombre = findViewById(R.id.etNombre);
        CheckBox cbHombre = findViewById(R.id.cbHombre);

        if(validarDato()) {
            if(cbHombre.isChecked())
            Toast.makeText(this, nombre.getText() + " se la come" , Toast.LENGTH_LONG).show();
            else {
                Toast.makeText(this, nombre.getText() + " tijeretea" , Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this,"Escribi algo gorriado", Toast.LENGTH_LONG).show();
        }
    }
}

