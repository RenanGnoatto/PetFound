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
import android.widget.Spinner;

import com.example.petfound.DAO.Cidade;
import com.example.petfound.DAO.Pets;

import java.util.ArrayList;

public class ListaPets extends AppCompatActivity {
    private ListView lvPets;
    private Spinner sCidadesLista;
    private SQLiteDatabase db = null;
    private Bundle bundle;
    private Pets pets = new Pets();
    ArrayList<Pets> arrayPets = new ArrayList<Pets>();
    private Cidade cidades;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pets);
        db = new DatabaseManager(this, "BancoDados", null, 3).getWritableDatabase();

        lvPets = (ListView) findViewById(R.id.lv_pets);
        sCidadesLista = (Spinner) findViewById(R.id.s_cidade_lista);
        adapter = new ListaPetsAdapter(this, adicionaPets());
        lvPets.setAdapter(adapter);

        populaCidade(sCidadesLista);

        sCidadesLista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int idCidade = ((Cidade)sCidadesLista.getSelectedItem()).getId();
                adapter.clear();
                adapter = new ListaPetsAdapter(ListaPets.this, atualizaLista(idCidade));
                lvPets.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

    private ArrayList<Pets> atualizaLista(int idCidade) {
        Cursor cur = db.rawQuery("select p.id, p.nome, c.nome, p.detalhes_pet, p.detalhes_sumico, u.nome, p.foto1" +
                " from pet p, cidade c, usuario u where p.id_usuario = u.id and p.id_cidade = c.id and" +
                " p.id_cidade = " + idCidade + "", null);
        while (cur.moveToNext()) {
            pets = new Pets(cur.getInt(0), cur.getString(1), cur.getString(2),
                    cur.getString(3), cur.getString(4), cur.getString(5),
                    cur.getBlob(6));
            arrayPets.add(pets);
        }
        return arrayPets;
    }

    private Spinner populaCidade(Spinner sCidade) {
        ArrayList<Cidade> arrayCidades = new ArrayList<Cidade>();
        Cursor cur = db.rawQuery("select id, nome from cidade order by id", null);
        while (cur.moveToNext()) {
            cidades = new Cidade(cur.getInt(0), cur.getString(1));
            arrayCidades.add(cidades);
        }
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, arrayCidades);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sCidade.setAdapter(adapter);
        return sCidade;
    }
}