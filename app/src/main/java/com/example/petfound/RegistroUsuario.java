package com.example.petfound;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class RegistroUsuario extends AppCompatActivity {

    private EditText edtEmailRegistro;
    private EditText edtSenhaRegistro;
    private EditText edtSenhaRegistroConfirmar;
    private CheckBox cbSenhaRegistroMostrar;
    private Button btRegistrarUsuario;
    private Button btCancelarRegistroUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        edtEmailRegistro = findViewById(R.id.edt_email_registro);
        edtSenhaRegistro = findViewById(R.id.edt_senha_registro);
        edtSenhaRegistroConfirmar = findViewById(R.id.edt_senha_registro_confirmar);
        cbSenhaRegistroMostrar = findViewById(R.id.cb_senha_registr_mostrar);
        btRegistrarUsuario = findViewById(R.id.bt_registrar_usuario);
        btCancelarRegistroUsuario = findViewById(R.id.bt_cancelar_registro_usuario);


    }
}