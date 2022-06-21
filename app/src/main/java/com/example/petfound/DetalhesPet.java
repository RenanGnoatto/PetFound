package com.example.petfound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class DetalhesPet extends AppCompatActivity {

    private ImageView ivFoto;
    private TextView tvNomePet;
    private TextView tvCidade;
    private TextView tvDetalhesPet;
    private TextView tvDetalhesSumico;
    private TextView tvDono;
    private TextView tvTelefone;
    private ImageButton ibTelefone;
    private TextView tvEmail;
    private ImageButton ibEmail;
    private Bundle bundle;
    private SQLiteDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_pet);
        db = new DatabaseManager(this, "BancoDados", null, 7).getWritableDatabase();

        ivFoto = (ImageView) findViewById(R.id.iv_foto_det);
        tvNomePet = (TextView) findViewById(R.id.tv_nome_pet_det);
        tvCidade = (TextView) findViewById(R.id.tv_cidade_det);
        tvDetalhesPet = (TextView) findViewById(R.id.tv_detalhes_pet_det);
        tvDetalhesSumico = (TextView) findViewById(R.id.tv_detalhes_sumico_det);
        tvDono = (TextView) findViewById(R.id.tv_nome_dono_det);
        tvTelefone = (TextView) findViewById(R.id.tv_telefone_det);
        ibTelefone = (ImageButton) findViewById(R.id.ib_telefone_det);
        tvEmail = (TextView) findViewById(R.id.tv_email_det);
        ibEmail = (ImageButton) findViewById(R.id.ib_email_det);


        bundle = getIntent().getExtras();
        carregaDados(bundle.getInt("idPet"));

        ibTelefone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvTelefone.getText().toString().equals("")) {
                    Snackbar donoSemTelefone = Snackbar.make(findViewById(R.id.CoordinatorLayoutUsuario),"Usuário não informou telefone!",Snackbar.LENGTH_SHORT);
                    donoSemTelefone.show();
                }
                else {
                String fone = tvTelefone.getText().toString().trim();
                try {
                    Intent intentFone = new Intent(Intent.ACTION_DIAL);
                    intentFone.setData(Uri.parse("tel:"+fone));
                    startActivity(intentFone);
                }
                catch (ActivityNotFoundException e){
                    Log.e("Sample call in android", "Erro ao efetuar ligação", e);
                }
            }
            }
        });

        ibEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String destinatario = tvEmail.getText().toString().trim();
                Intent intentEmail = new Intent(Intent.ACTION_SEND);
                intentEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{destinatario});
                intentEmail.putExtra(Intent.EXTRA_SUBJECT, "Ei, tenho notícias sobre seu pet!!!");
                intentEmail.setType("message/rfc822");
                startActivity(intentEmail);
            }
        });
    }

    public void carregaDados(int idPet){
        Cursor cur = db.rawQuery("select p.foto1, p.nome, c.nome, p.detalhes_pet, p.detalhes_sumico," +
                " u.nome, u.telefone, u.email from pet p, usuario u, cidade c" +
                " where p.id_cidade = c.id and p.id_usuario = u.id" +
                " and p.id = " + idPet + "", null);
        while (cur.moveToNext()) {
            byte[] imagemPetBytes = Base64.decode(cur.getBlob(0),Base64.DEFAULT);
            Bitmap foto = BitmapFactory.decodeByteArray(imagemPetBytes,0,imagemPetBytes.length);
            ivFoto.setImageBitmap(foto);
            tvNomePet.setText("Nome do pet: " + cur.getString(1));
            tvCidade.setText("Cidade: " + cur.getString(2));
            tvDetalhesPet.setText("Detalhes do pet: " + cur.getString(3));
            tvDetalhesSumico.setText("Detalhes do sumiço: " + cur.getString(4));
            tvDono.setText("Nome do(a) dono(a): " + cur.getString(5));
            tvTelefone.setText(cur.getString(6));
            tvEmail.setText(cur.getString(7));

        }
    }
}