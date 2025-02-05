package com.example.starwarsapi;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolderElemento> {

    ArrayList<Elemento> listaElementos;

    public Adaptador(ArrayList<Elemento> listaElementos) {

        this.listaElementos = listaElementos;
    }

    @NonNull
    @Override
    public ViewHolderElemento onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_item, parent,false);
        return new ViewHolderElemento(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderElemento holder, int position) {

        final int posicion = position;

        //Agrega los datos a los elementos
        Elemento elemento = listaElementos.get(posicion);
        holder.txtDato1.setText(elemento.getDato1());
        holder.txtDato2.setText(elemento.getDato2());
        holder.txtDato3.setText(elemento.getDato3());
    }

    @Override
    public int getItemCount() {
        return listaElementos.size();
    }

    public class ViewHolderElemento extends RecyclerView.ViewHolder{

        TextView txtDato1;
        TextView txtDato2;
        TextView txtDato3;

        public ViewHolderElemento(@NonNull View itemView) {
            super(itemView);

            txtDato1 = itemView.findViewById(R.id.txtDato1);
            txtDato2 = itemView.findViewById(R.id.txtDato2);
            txtDato3 = itemView.findViewById(R.id.txtDato3);
        }
    }
}
