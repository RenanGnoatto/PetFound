package com.example.petfound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText edtEmailLogin;
    private EditText edtSenhaLogin;
    private CheckBox cbSenhaLoginMostrar;
    private Button btLogar;
    private Button btCriarConta;
    private Button btSair;
    private SQLiteDatabase db = null;
    private Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseManager(this, "BancoDados", null, 7).getWritableDatabase();

        edtEmailLogin = (EditText) findViewById(R.id.edt_email_login);
        edtSenhaLogin = (EditText) findViewById(R.id.edt_senha_login);
        cbSenhaLoginMostrar = (CheckBox) findViewById(R.id.cb_senha_login_mostrar);
        btLogar = (Button) findViewById(R.id.bt_logar);
        btCriarConta = (Button) findViewById(R.id.bt_criar_conta);

        btLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = edtEmailLogin.getText().toString();
                String senha = edtSenhaLogin.getText().toString();
                validaLogin(usuario, senha);
            }
        });

        btCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RegistroUsuario.class);
                startActivity(intent);
            }
        });

        cbSenhaLoginMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbSenhaLoginMostrar.isChecked()) {
                    edtSenhaLogin.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else {
                    edtSenhaLogin.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

    }

    public void validaLogin(String usuario, String senha) {
        Cursor cur = db.rawQuery("select id, nome, email, senha from usuario " +
                "where email like '" + usuario + "' and senha = '" + senha + "'", null);
        System.out.println("USUARIO: " + edtEmailLogin.getText());
        System.out.println("SENHA: " + edtSenhaLogin.getText());
        if (edtEmailLogin.getText().length() > 0 ||
                edtSenhaLogin.getText().length() > 0) {
            if (cur.getCount() > 0) {
                while (cur.moveToNext()) {
                    bundle.putString("idUsuario", cur.getString(0));
                    bundle.putString("nomeUsuario", cur.getString(1));
                    bundle.putString("emailUsuario", cur.getString(2));
                    Intent intent = new Intent(MainActivity.this, TelaPrincipal.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            } else {
                Snackbar sbLoginInvalido = Snackbar.make(findViewById(R.id.CoordinatorLayout), "E-mail ou Senha inválidos!", Snackbar.LENGTH_SHORT);
                sbLoginInvalido.show();
            }
        }
        else {
            Snackbar sbLoginInvalido = Snackbar.make(findViewById(R.id.CoordinatorLayout),"Informe e-mail e senha!",Snackbar.LENGTH_SHORT);
            sbLoginInvalido.show();
        }
    }
}