package com.example.petfound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText edtEmailLogin;
    private EditText edtSenhaLogin;
    private CheckBox cbSenhaLoginMostrar;
    private Button btLogar;
    private Button btCriarConta;
    private Button btSair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEmailLogin = (EditText) findViewById(R.id.edt_email_login);
        edtSenhaLogin = (EditText) findViewById(R.id.edt_senha_login);
        cbSenhaLoginMostrar = (CheckBox) findViewById(R.id.cb_senha_login_mostrar);
        btLogar = (Button) findViewById(R.id.bt_logar);
        btCriarConta = (Button) findViewById(R.id.bt_criar_conta);
        btSair = (Button) findViewById(R.id.bt_sair);

        btLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,TelaPrincipal.class);
                startActivity(intent);
            }
        });
        btCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RegistroUsuario.class);
                startActivity(intent);
            }
        });

        btSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
            }
        });
    }
}