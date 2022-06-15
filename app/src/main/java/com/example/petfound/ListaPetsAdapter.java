package com.example.petfound;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListaPetsAdapter extends ArrayAdapter<Pets> {

    private final Context context;
    private final ArrayList<Pets> listaPets;

    public ListaPetsAdapter(Context context, ArrayList<Pets> listaPets) {
        super(context,R.layout.lista_pets,listaPets);
        this.context = context;
        this.listaPets = listaPets;
    }

    public View getView (int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.lista_pets,parent,false);

        TextView tvNomePet = (TextView) rowView.findViewById(R.id.tv_nome_pet_lista);
        TextView tvCidadePet = (TextView) rowView.findViewById(R.id.tv_cidade_pet_lista);
        TextView tvDetalhesPet = (TextView) rowView.findViewById(R.id.tv_detalhes_pet_lista);
        TextView tvDetalhesSumico = (TextView) rowView.findViewById(R.id.tv_detalhes_sumico_lista);
        TextView tvNomeDono = (TextView) rowView.findViewById(R.id.tv_nome_dono_pet_lista);

        tvNomePet.setText(listaPets.get(position).getNome());
        tvCidadePet.setText(listaPets.get(position).getCidade());
        tvDetalhesPet.setText(listaPets.get(position).getDetalhesPet());
        tvDetalhesSumico.setText(listaPets.get(position).getDetalhesSumico());
        tvNomeDono.setText(listaPets.get(position).getNomeDono());

        return rowView;
    }

}
