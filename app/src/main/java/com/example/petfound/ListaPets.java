package com.example.petfound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListaPets extends AppCompatActivity {
    private ListView lvPets;
    private SQLiteDatabase db = null;
    private Bundle bundle;
    private Pets pets = new Pets();
    ArrayList<Pets> arrayPets = new ArrayList<Pets>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pets);
        db = new DatabaseManager(this, "BancoDados", null, 3).getWritableDatabase();

        lvPets = (ListView) findViewById(R.id.lv_pets);
        ArrayAdapter adapter = new ListaPetsAdapter(this, adicionaPets());
        lvPets.setAdapter(adapter);

        lvPets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //bundle.putInt("idPet", arrayPets.get(position).getId() );
                Intent intent = new Intent(ListaPets.this, DetalhesPet.class);
                //intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private ArrayList<Pets> adicionaPets() {
        Cursor cur = db.rawQuery("select p.id, p.nome, c.nome, p.detalhes_pet, p.detalhes_sumico, u.nome, p.foto1" +
                " from pet p, cidade c, usuario u where p.id_usuario = u.id and p.id_cidade = c.id",null);
        while (cur.moveToNext()){
            pets = new Pets(cur.getInt(0), cur.getString(1),cur.getString(2),
                    cur.getString(3), cur.getString(4), cur.getString(5),
                    cur.getBlob(6));
            arrayPets.add(pets);
        }
        return arrayPets;
    }
}