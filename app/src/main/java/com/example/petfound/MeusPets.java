package com.example.petfound;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.petfound.DAO.Pet;

import java.util.ArrayList;

public class MeusPets extends AppCompatActivity {

    private ListView lvMeusPets;
    private SQLiteDatabase db = null;
    private Pet pet = new Pet();
    ArrayList<Pet> arrayPets = new ArrayList<Pet>();
    ArrayAdapter adapter;
    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_pets);
        db = new DatabaseManager(this, "BancoDados", null, 7).getWritableDatabase();

        lvMeusPets = (ListView) findViewById(R.id.lv_meus_pets);
        adapter = new ListaPetsAdapter(this, adicionaPets());
        lvMeusPets.setAdapter(adapter);

        lvMeusPets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bundle.putInt("idPet",arrayPets.get(position).getId());
                Intent intent = new Intent(MeusPets.this, EditorPet.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    private ArrayList<Pet> adicionaPets() {
        bundle = getIntent().getExtras();
        int idUsuario = Integer.parseInt(bundle.getString("idUsuario"));
        Cursor cur = db.rawQuery("select p.id, p.nome, c.nome, p.detalhes_pet, p.detalhes_sumico, u.nome, p.foto1" +
                " from pet p, cidade c, usuario u where p.id_usuario = u.id and p.id_cidade = c.id" +
                " and u.id = " + idUsuario,null);
        while (cur.moveToNext()){
            pet = new Pet(cur.getInt(0), cur.getString(1),cur.getString(2),
                    cur.getString(3), cur.getString(4), cur.getString(5),
                    cur.getBlob(6));
            arrayPets.add(pet);
        }
        return arrayPets;
    }
}