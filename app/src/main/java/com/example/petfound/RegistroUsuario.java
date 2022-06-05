package com.example.petfound;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class RegistroUsuario extends AppCompatActivity {

    private EditText edtNomeUsuarioRegistro;
    private Spinner sCidade;
    private EditText edtEmailRegistro;
    private EditText edtSenhaRegistro;
    private EditText edtSenhaRegistroConfirmar;
    private CheckBox cbSenhaRegistroMostrar;
    private Button btRegistrarUsuario;
    private Button btCancelarRegistroUsuario;
    private SQLiteDatabase db = null;
    private AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        db = new DatabaseManager(this, "BancoDados", null, 1).getWritableDatabase();

        edtNomeUsuarioRegistro = (EditText) findViewById(R.id.edt_nome_usuario_registro);
        sCidade = (Spinner) findViewById(R.id.s_cidade);
        edtEmailRegistro = (EditText) findViewById(R.id.edt_email_registro);
        edtSenhaRegistro = (EditText) findViewById(R.id.edt_senha_registro);
        edtSenhaRegistroConfirmar = (EditText) findViewById(R.id.edt_senha_registro_confirmar);
        cbSenhaRegistroMostrar = (CheckBox) findViewById(R.id.cb_senha_registro_mostrar);
        btRegistrarUsuario = (Button) findViewById(R.id.bt_registrar_usuario);
        btCancelarRegistroUsuario = (Button) findViewById(R.id.bt_cancelar_registro_usuario);

        populaCidade(sCidade);

        btCancelarRegistroUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btRegistrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtNomeUsuarioRegistro.getText() != null &&
                sCidade.getSelectedItemPosition() > 0 &&
                edtEmailRegistro.getText() != null &&
                edtSenhaRegistro.getText() != null &&
                edtSenhaRegistroConfirmar.getText() != null) {
                    if (edtSenhaRegistro.getText().toString().equals(edtSenhaRegistroConfirmar.getText().toString())) {
                        db.execSQL("insert into usuario " +
                                "(nome, email, senha, id_cidade) " +
                                "values ('" + edtNomeUsuarioRegistro.getText() + "','" + edtEmailRegistro.getText() +
                                "','" + edtSenhaRegistro.getText() + "'," + (int) sCidade.getSelectedItemId() + ")");
                        edtNomeUsuarioRegistro.setText("");
                        sCidade.setId(0);
                        edtEmailRegistro.setText("");
                        edtSenhaRegistro.setText("");
                        edtSenhaRegistroConfirmar.setText("");
                        cbSenhaRegistroMostrar.setChecked(false);
                        /*Snackbar sbCadastroRealizado = Snackbar.make(findViewById(R.id.CoordinatorLayout),"Registro realizado com sucesso!",Snackbar.LENGTH_SHORT);
                        sbCadastroRealizado.show();*/
                    }
                    else {
                        Snackbar sbSenhasDiferentes = Snackbar.make(findViewById(R.id.CoordinatorLayout),"As senhas informadas estão diferentes!",Snackbar.LENGTH_SHORT);
                        sbSenhasDiferentes.show();
                    }
                }
                else {
                    Snackbar sbCamposObrigatorios = Snackbar.make(findViewById(R.id.CoordinatorLayout),"Todos os campos são obrigatórios!",Snackbar.LENGTH_SHORT);
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

    private void alertaUsuarioCadastrado() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cadastro de Usuário");
        builder.setMessage("Usuário cadastrado com sucesso!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(RegistroUsuario.this, "positivo=" + arg1, Toast.LENGTH_SHORT).show();
            }
        });
        alerta = builder.create();
        alerta.show();
    }
}