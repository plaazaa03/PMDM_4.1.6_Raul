package com.example.pmdm_416_raul;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class AdaptadorArticulos extends RecyclerView.Adapter<AdaptadorArticulos.ArticuloViewHolder> implements View.OnClickListener{
    private ArrayList<Articulo> datos;
    private View.OnClickListener listener;
    MainActivity actividadprincipal;
    AdaptadorArticulos adaptador = this;

    AdaptadorArticulos(ArrayList<Articulo> datos){
        this.datos = datos;
    }

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }
    }

    @NonNull
    @Override
    public ArticuloViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.articulo, parent, false);

        itemView.setOnClickListener(this);

        ArticuloViewHolder ovh = new ArticuloViewHolder(itemView);

        return ovh;
    }

    @Override
    public void onBindViewHolder(@NonNull ArticuloViewHolder holder, int position) {
        Articulo item = datos.get(position);

        holder.bindArticulo(item);
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public void setActividadprincipal(MainActivity actividadprincipal){
        this.actividadprincipal = actividadprincipal;
    }

    public void setAdaptador(AdaptadorArticulos adaptador){
        this.adaptador = adaptador;
    }
    class ArticuloViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        private TextView nombre;
        private boolean comprado;


        public ArticuloViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.articulo_lista);

            nombre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!comprado){
                        nombre.setPaintFlags(nombre.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        nombre.setTextColor(Color.GREEN);
                        comprado = true;
                    } else {
                        nombre.setPaintFlags(nombre.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                        nombre.setTextColor(Color.RED);
                        comprado = false;
                    }
                }
            });

            itemView.setOnCreateContextMenuListener(this);
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
            MenuItem editar = menu.add(0, v.getId(), 0, "Editar");
            MenuItem borrar = menu.add(0, v.getId(), 0, "Borrar");


            editar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(@NonNull MenuItem item) {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(actividadprincipal);
                    alerta.setTitle("Editar artículo");

                    final EditText entradaTexto = new EditText(actividadprincipal.getBaseContext());
                    entradaTexto.setInputType(InputType.TYPE_CLASS_TEXT);
                    alerta.setView(entradaTexto);

                    alerta.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(Pattern.matches(".*\\w.*", entradaTexto.getText().toString())) {
                                int posicion = getAdapterPosition();

                                datos.get(posicion).setNombre(entradaTexto.getText().toString());

                                adaptador.notifyItemChanged(posicion);
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
                    return true;
                }
            });

            borrar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(@NonNull MenuItem item) {
                    int posicion = getAdapterPosition();

                    datos.get(posicion).setComprado(false);

                    datos.remove(posicion);

                    notifyItemRemoved(posicion);

                    nombre.setPaintFlags(nombre.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                    nombre.setTextColor(Color.RED);

                    return true;
                }
            });
        }


        public void bindArticulo(Articulo p){
            nombre.setText(p.getNombre());
            comprado = p.isComprado();
        }
    }
}
