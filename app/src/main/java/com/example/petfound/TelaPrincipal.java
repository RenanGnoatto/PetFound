package com.example.petfound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class TelaPrincipal extends AppCompatActivity {

    private TextView tvBoasVindas;
    private Button btEditarPerfil;
    private Button btMeusPets;
    private Button btListaPets;
    private Button btRelatarSumico;
    private Bundle bundle = new Bundle();
    private Bundle bundle2 = new Bundle();
    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        tvBoasVindas = (TextView) findViewById(R.id.tv_boas_vindas);
        btEditarPerfil = (Button) findViewById(R.id.bt_editar_perfil);
        btMeusPets = (Button) findViewById(R.id.bt_meus_pets);
        btListaPets = (Button) findViewById(R.id.bt_lista_pets);
        btRelatarSumico = (Button) findViewById(R.id.bt_relatar_sumico);

        bundle = getIntent().getExtras();
        tvBoasVindas.setText("Olá " + bundle.getString("nomeUsuario") + ", seja bem-vindo(a)!" );

        btEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle2.putString("idUsuario", bundle.getString("idUsuario"));
                Intent intent = new Intent(TelaPrincipal.this, EditorUsuario.class);
                intent.putExtras(bundle2);
                startActivity(intent);
            }
        });

        btEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar = Snackbar.make(findViewById(R.id.CoordinatorLayoutTelaPrincipal),"Tela não disponível",Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });
        btMeusPets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle2.putString("idUsuario", bundle.getString("idUsuario"));
                Intent intent = new Intent(TelaPrincipal.this, MeusPets.class);
                intent.putExtras(bundle2);
                startActivity(intent);
            }
        });
        btListaPets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaPrincipal.this, ListaPets.class);
                startActivity(intent);
            }
        });
        btRelatarSumico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle2.putString("idUsuario", bundle.getString("idUsuario"));
                Intent intent = new Intent(TelaPrincipal.this,RegistroPet.class);
                intent.putExtras(bundle2);
                startActivity(intent);
            }
        });
    }
}