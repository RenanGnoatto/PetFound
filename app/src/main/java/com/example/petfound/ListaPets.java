package com.example.petfound;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListaPets extends AppCompatActivity {
    private ListView lvPets;
    private SQLiteDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pets);
        db = new DatabaseManager(this, "BancoDados", null, 3).getWritableDatabase();

        lvPets = (ListView) findViewById(R.id.lv_pets);
        ArrayAdapter adapter = new ListaPetsAdapter(this, adicionaPets());
        lvPets.setAdapter(adapter);
    }

    private ArrayList<Pets> adicionaPets() {
        ArrayList<Pets> arrayPets = new ArrayList<Pets>();
        Pets pets;
        Cursor cur = db.rawQuery("select p.nome, c.nome, p.detalhes_pet, p.detalhes_sumico, u.nome" +
                " from pet p, cidade c, usuario u where p.id_usuario = u.id and p.id_cidade = c.id",null);
        while (cur.moveToNext()){
            pets = new Pets(cur.getString(0),cur.getString(1),
                    cur.getString(2), cur.getString(3), cur.getString(4));
            arrayPets.add(pets);
        }

        return arrayPets;
    }
}