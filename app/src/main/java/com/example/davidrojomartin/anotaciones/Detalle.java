package com.example.davidrojomartin.anotaciones;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import entities.Nota;

/**
 * Created by David Rojo Martin on 30/11/2015.
 */
public class Detalle extends Activity{

    private EditText etTitulo;
    private TextView tvFecha;
    private EditTextLineas etTexto;

    private Nota n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle);

        //n = (Nota) getIntent().getSerializableExtra("nota");
        n =(Nota) getIntent().getExtras().get("nota");
        etTitulo = (EditText) findViewById(R.id.ettitulo);
        tvFecha = (TextView) findViewById(R.id.tvfecha);
        etTexto = (EditTextLineas) findViewById(R.id.body);


        etTitulo.setText(n.getTitulo());
        tvFecha.setText(n.getFecha());
        etTexto.setText(n.getTexto());
    }


    @Override
    public void onBackPressed() {
        // creamos un intent para mandar datos para el retorno

        n.setTitulo(etTitulo.getText().toString());
        n.setTexto(etTexto.getText().toString());

        Log.d("kk", "Detalle:  Nota enviada por Intent: "+ n.toString());

        Intent i = new Intent(this, Anotaciones.class );
        // colocamos el nombre que nos han teclado en el intent con putExtra
        i.putExtra("nota", n);
        // colocamos en intent en le result para que la activity principal lo pueda recoger
        setResult(Activity.RESULT_OK, i);
        super.onBackPressed();
    }



    private void tostada(String msg){
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    }

}
