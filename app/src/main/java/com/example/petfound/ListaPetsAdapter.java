package com.example.petfound;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Base64;

import androidx.core.graphics.drawable.BitmapDrawableKt;

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
        ImageView ivFotoPet = (ImageView) rowView.findViewById(R.id.iv_foto_pet_lista);

        tvNomePet.setText(listaPets.get(position).getNome());
        tvCidadePet.setText(listaPets.get(position).getCidade());
        tvDetalhesPet.setText(listaPets.get(position).getDetalhesPet());
        tvDetalhesSumico.setText(listaPets.get(position).getDetalhesSumico());
        tvNomeDono.setText(listaPets.get(position).getNomeDono());
        /*ByteArrayInputStream imageStream = new ByteArrayInputStream(listaPets.get(position).getFoto());
        Bitmap bitmap = BitmapFactory.decodeStream(imageStream);*/

        /*byte[] fotoEmBytes = Base64.decode(listaPets.get(position).getFoto(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(fotoEmBytes, 0, fotoEmBytes.length);*/

        /*byte[] fotoEmBytes = Base64.decode(listaPets.get(position).getFoto(), Base64.DEFAULT);
        Drawable image = new BitmapDrawable(BitmapFactory.decodeByteArray(fotoEmBytes, 0, fotoEmBytes.length));*/

        /*byte [] fotoEmBytes = listaPets.get(position).getFoto();
        Bitmap bitmap = BitmapFactory.decodeByteArray(fotoEmBytes, 0, fotoEmBytes.length);*/

        //ivFotoPet.setImageDrawable(image);

        return rowView;
    }

}
