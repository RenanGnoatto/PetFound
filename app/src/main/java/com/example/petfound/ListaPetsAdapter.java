package com.example.petfound;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.petfound.DAO.Pet;

import java.util.ArrayList;

public class ListaPetsAdapter extends ArrayAdapter<Pet> {

    private final Context context;
    private final ArrayList<Pet> listaPets;

    public ListaPetsAdapter(Context context, ArrayList<Pet> listaPets) {
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
        TextView tvNomeDono = (TextView) rowView.findViewById(R.id.tv_nome_dono_pet_lista);
        ImageView ivFotoPet = (ImageView) rowView.findViewById(R.id.iv_foto_pet_lista);

        tvNomePet.setText("Nome do Pet: " + listaPets.get(position).getNome());
        tvCidadePet.setText("Cidade: " + listaPets.get(position).getCidade());
        tvDetalhesPet.setText("Detalhes do Pet: " + listaPets.get(position).getDetalhesPet());
        tvNomeDono.setText("Nome do(a) dono(a): " + listaPets.get(position).getNomeDono());
        /*ByteArrayInputStream imageStream = new ByteArrayInputStream(listaPets.get(position).getFoto());
        Bitmap bitmap = BitmapFactory.decodeStream(imageStream);*/

        /*byte[] fotoEmBytes = Base64.decode(listaPets.get(position).getFoto(), Base64.DEFAULT);
        System.out.println("BYTES: " + listaPets.get(position).getFoto());
        Bitmap bitmap = BitmapFactory.decodeByteArray(fotoEmBytes, 0, fotoEmBytes.length);*/

        /*byte[] fotoEmBytes = Base64.decode(listaPets.get(position).getFoto(), Base64.DEFAULT);
        Drawable image = new BitmapDrawable(BitmapFactory.decodeByteArray(fotoEmBytes, 0, fotoEmBytes.length));*/

        /*byte [] fotoEmBytes = listaPets.get(position).getFoto();
        Bitmap bitmap = BitmapFactory.decodeByteArray(fotoEmBytes, 0, fotoEmBytes.length);*/

        //ivFotoPet.setImageDrawable(image);

        return rowView;
    }

}
