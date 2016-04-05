package com.example.davidrojomartin.anotaciones;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import adapters.AdaptadorBD;
import adapters.AdaptadorLVNotas;
import entities.Nota;

public class Anotaciones extends AppCompatActivity {
    private ArrayList<Nota> listaNotas;
    private AdaptadorBD bd;

    private Button btnNuevaNota;
    private ListView lv;
    private AdaptadorLVNotas adapter;

    private boolean edicion;


    protected static final int REQUEST_SEGUNDA = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        cargarDatos();




        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                abrirNota(position);
                edicion = true;
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                borrarNota(position);
                return false;
            }
        });

        btnNuevaNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edicion = false;
                nuevaNota();
            }
        });

    }

    public void nuevaNota(){
        Nota n = bd.crearNota();

        Intent intent = new Intent(Anotaciones.this, Detalle.class);
        intent.putExtra("nota", n);

        startActivityForResult(intent, REQUEST_SEGUNDA);
    }

    public void borrarNota(int position){
        Nota n = listaNotas.get(position);
        bd.borrarNota(n);
        listaNotas.remove(position);
        adapter.notifyDataSetChanged();
    }

    public void abrirNota(int position){
        Intent intent = new Intent(Anotaciones.this, Detalle.class);
        intent.putExtra("nota", listaNotas.get(position));
        startActivityForResult(intent, REQUEST_SEGUNDA);
    }


    public void cargarDatos(){
        listaNotas = new ArrayList<>();
        bd = new AdaptadorBD(this);

        listaNotas = bd.obtenerNotas();
        adapter = new AdaptadorLVNotas(Anotaciones.this, R.layout.fila, listaNotas);

        btnNuevaNota = (Button) findViewById(R.id.btnNuevaNota);

        lv = (ListView) findViewById(R.id.lvNotas);
        lv.setAdapter(adapter);

        Log.d("kk", "Contenido de la lista tras la carga: " + listaNotas.toString());
    }

    @Override
    // Este es el metodo que se encarga de recoger lo que otra activity nos
    // devuelve
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_SEGUNDA:
                    Nota n = (Nota) data.getExtras().get("nota");
                    Log.d("kk", "Anotaciones: Nota recibida por intent: " + n.toString());
                    if(edicion){ //si estábamos editando una nota existente
                        for(int i=0;i<listaNotas.size();i++){
                            if(listaNotas.get(i).getId().equals(n.getId())){
                                listaNotas.set(i,n);
                                adapter.notifyDataSetChanged();
                            }
                        }
                        bd.updateNota(n);
                    }else{ //si estábamos creando una nueva nota
                        listaNotas.add( bd.updateNota(n));
                        adapter.notifyDataSetChanged();
                    }
                    Log.d("kk" , "Contenido de la lista tras los cambios: "+ listaNotas.toString());


                    break;
                default:
                    break;
            }
        }
    }

}
