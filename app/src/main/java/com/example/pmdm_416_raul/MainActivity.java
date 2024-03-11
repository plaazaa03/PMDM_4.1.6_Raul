package com.example.pmdm_416_raul;

import android.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private String[] nombreArticulos;
    private ArrayList<Articulo> datos;
    private RecyclerView recView;
    private Button btAnadir;
    private MainActivity actividadPrincipal = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombreArticulos = getResources().getStringArray(R.array.articulos);

        recView = findViewById(R.id.recycler_articulos);

        btAnadir = findViewById(R.id.bt_anade_art);

        inicializa();
    }

    private void inicializa(){

        datos = new ArrayList<Articulo>();
        for(String s : nombreArticulos){
            datos.add(new Articulo(s));
        }

        final AdaptadorArticulos adaptador = new AdaptadorArticulos(datos);

        adaptador.setActividadprincipal(actividadPrincipal);
        recView.setAdapter(adaptador);

        recView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        recView.setItemAnimator(new DefaultItemAnimator());

        btAnadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(actividadPrincipal);
                alerta.setTitle("Añadir artículo");

                final EditText entradaTexto = new EditText(getBaseContext());
                entradaTexto.setInputType(InputType.TYPE_CLASS_TEXT);
                alerta.setView(entradaTexto);

                alerta.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(Pattern.matches(".*\\w.*", entradaTexto.getText().toString())) {
                            datos.add(0, new Articulo(entradaTexto.getText().toString()));

                            adaptador.notifyItemInserted(0);
                        } else {
                            dialog.cancel();
                        }
                    }
                });
                alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alerta.show();
            }
        });
    }
}