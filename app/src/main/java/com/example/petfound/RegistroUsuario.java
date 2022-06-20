package com.example.petfound;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.petfound.Util.MascaraCampos;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class RegistroUsuario extends AppCompatActivity {

    private EditText edtNomeUsuarioRegistro;
    private Spinner sCidade;
    private EditText edtTelefone;
    private EditText edtEmailRegistro;
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
        db = new DatabaseManager(this, "BancoDados", null, 6).getWritableDatabase();

        edtNomeUsuarioRegistro = (EditText) findViewById(R.id.edt_nome_usuario_registro);
        sCidade = (Spinner) findViewById(R.id.s_cidade);
        edtTelefone = (EditText) findViewById(R.id.edt_telefone_registro);
        edtEmailRegistro = (EditText) findViewById(R.id.edt_email_registro);
        edtSenhaRegistro = (EditText) findViewById(R.id.edt_senha_registro);
        edtSenhaRegistroConfirmar = (EditText) findViewById(R.id.edt_senha_registro_confirmar);
        cbSenhaRegistroMostrar = (CheckBox) findViewById(R.id.cb_senha_registro_mostrar);
        btRegistrarUsuario = (Button) findViewById(R.id.bt_registrar_usuario);
        btCancelarRegistroUsuario = (Button) findViewById(R.id.bt_cancelar_registro_usuario);
        populaCidade(sCidade);
        edtTelefone.addTextChangedListener(MascaraCampos.mask(edtTelefone,MascaraCampos.FORMAT_FONE));

        btCancelarRegistroUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btRegistrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtNomeUsuarioRegistro.getText().length() > 0 &&
                sCidade.getSelectedItemPosition() > 0 &&
                edtEmailRegistro.getText().length() > 0 &&
                edtSenhaRegistro.getText().length() > 0 &&
                edtSenhaRegistroConfirmar.getText().length() > 0) {
                    if (edtSenhaRegistro.getText().toString().equals(edtSenhaRegistroConfirmar.getText().toString())) {
                        db.execSQL("insert into usuario " +
                                "(nome, email, senha, telefone, id_cidade) " +
                                "values ('" + edtNomeUsuarioRegistro.getText() + "','" +
                                edtEmailRegistro.getText() + "','" +
                                edtSenhaRegistro.getText() + "','" +
                                edtTelefone.getText() + "'," +
                                (int) sCidade.getSelectedItemId() + ")");
                        edtNomeUsuarioRegistro.setText("");
                        sCidade.setId(0);
                        edtTelefone.setText("");
                        edtEmailRegistro.setText("");
                        edtSenhaRegistro.setText("");
                        edtSenhaRegistroConfirmar.setText("");
                        cbSenhaRegistroMostrar.setChecked(false);
                        /*Snackbar sbCadastroRealizado = Snackbar.make(findViewById(R.id.CoordinatorLayoutUsuario),"Registro realizado com sucesso!",Snackbar.LENGTH_SHORT);
                        sbCadastroRealizado.show();*/
                    }
                    else {
                        Snackbar sbSenhasDiferentes = Snackbar.make(findViewById(R.id.CoordinatorLayoutUsuario),"As senhas informadas estão diferentes!",Snackbar.LENGTH_SHORT);
                        sbSenhasDiferentes.show();
                    }
                }
                else {
                    Snackbar sbCamposObrigatorios = Snackbar.make(findViewById(R.id.CoordinatorLayoutUsuario),"Todos os campos são obrigatórios!",Snackbar.LENGTH_SHORT);
                    sbCamposObrigatorios.show();
                }
            }
        });

        cbSenhaRegistroMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cbSenhaRegistroMostrar.isChecked()) {
                    edtSenhaRegistro.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edtSenhaRegistroConfirmar.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else {
                    edtSenhaRegistro.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edtSenhaRegistroConfirmar.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
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
}