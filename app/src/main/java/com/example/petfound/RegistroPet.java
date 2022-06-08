package com.example.petfound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;

public class RegistroPet extends AppCompatActivity {

    private ImageView ivFotoPet;
    private Button btIncluirFoto;
    private EditText edtNomePet;
    private Spinner sCidadePet;
    private EditText edtDetalhesPet;
    private EditText edtDetalhesSumico;
    private Button btRegistrarPet;
    private Button btCancelarRegistroPet;
    private SQLiteDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_pet);
        db = new DatabaseManager(this, "BancoDados", null, 2).getWritableDatabase();

        ivFotoPet = (ImageView) findViewById(R.id.iv_foto_pet);
        btIncluirFoto = (Button) findViewById(R.id.bt_incluir_foto);
        edtNomePet = (EditText) findViewById(R.id.edt_nome_pet);
        sCidadePet = (Spinner) findViewById(R.id.s_cidade_pet);
        edtDetalhesPet = (EditText) findViewById(R.id.edt_detalhes_pet);
        edtDetalhesSumico = (EditText) findViewById(R.id.edt_detalhes_sumico);
        btRegistrarPet = (Button) findViewById(R.id.bt_registrar_pet);
        btCancelarRegistroPet = (Button) findViewById(R.id.bt_cancelar_registro_pet);

        btIncluirFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, 1);
                //ivFotoPet.setImageBitmap(startActivityForResult(takePictureIntent, 1));
            }
        });
        /*btRegistrarPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ivFotoPet.is null &&
                        sCidade.getSelectedItemPosition() > 0 &&
                        edtEmailRegistro.getText() != null &&
                        edtSenhaRegistro.getText() != null &&
                        edtSenhaRegistroConfirmar.getText() != null)
            }
        });*/
    }

    private Spinner populaCidade(Spinner sCidade) {
        ArrayList<String> listaCidades = new ArrayList<>();
        Cursor cur = db.rawQuery("select nome, estado from cidade order by id", null);
        int contador = 1;
        listaCidades.add(0,"Selecione uma cidade");
        while (cur.moveToNext()) {
            listaCidades.add(contador, cur.getString(0) + " - " + cur.getString(1) );
            contador ++;
        }
        ArrayAdapter<String> a = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, listaCidades);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sCidade.setAdapter(a);
        return sCidade;
    }

    public void abrirCamera(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, 1);
    }
}