package com.example.petfound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class RegistroUsuario extends AppCompatActivity {

    private EditText edtEmailRegistro;
    private Spinner sCidade;
    private EditText edtSenhaRegistro;
    private EditText edtSenhaRegistroConfirmar;
    private CheckBox cbSenhaRegistroMostrar;
    private Button btRegistrarUsuario;
    private Button btCancelarRegistroUsuario;
    private SQLiteDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        db = new DatabaseManager(this, "BancoDados", null, 1).getWritableDatabase();

        edtEmailRegistro = findViewById(R.id.edt_email_registro);
        sCidade = findViewById(R.id.s_cidade);
        edtSenhaRegistro = findViewById(R.id.edt_senha_registro);
        edtSenhaRegistroConfirmar = findViewById(R.id.edt_senha_registro_confirmar);
        cbSenhaRegistroMostrar = findViewById(R.id.cb_senha_registro_mostrar);
        btRegistrarUsuario = findViewById(R.id.bt_registrar_usuario);
        btCancelarRegistroUsuario = findViewById(R.id.bt_cancelar_registro_usuario);

        populaCidade(sCidade);

        btCancelarRegistroUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public Spinner populaCidade(Spinner sCidade) {
        ArrayList<String> listaCidades = new ArrayList<>();
        Cursor cur = db.rawQuery("select nome, estado from cidade", null);
        int contador = 0;
        while (cur.moveToNext()) {
            listaCidades.add(contador, cur.getString(0) + " - " + cur.getString(1) );
            contador ++;
        }
        ArrayAdapter<String> a = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, listaCidades);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sCidade.setAdapter(a);
        return sCidade;
    }
}