package com.example.petfound;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
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
    private String ivFotoPetString = "";
    private Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_pet);
        db = new DatabaseManager(this, "BancoDados", null, 7).getWritableDatabase();
        bundle = getIntent().getExtras();

        ivFotoPet = (ImageView) findViewById(R.id.iv_foto_pet);
        btIncluirFoto = (Button) findViewById(R.id.bt_incluir_foto);
        edtNomePet = (EditText) findViewById(R.id.edt_nome_pet);
        sCidadePet = (Spinner) findViewById(R.id.s_cidade_pet);
        edtDetalhesPet = (EditText) findViewById(R.id.edt_detalhes_pet);
        edtDetalhesSumico = (EditText) findViewById(R.id.edt_detalhes_sumico);
        btRegistrarPet = (Button) findViewById(R.id.bt_registrar_pet);
        btCancelarRegistroPet = (Button) findViewById(R.id.bt_cancelar_registro_pet);

        populaCidade(sCidadePet);

        btIncluirFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        btRegistrarPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ivFotoPetString.length() > 0 &&
                        edtNomePet.getText() != null &&
                        sCidadePet.getSelectedItemId() > 0) {
                    db.execSQL("insert into pet " +
                            "(nome, id_cidade, id_usuario, detalhes_pet, detalhes_sumico, foto1) " +
                            "values ('" + edtNomePet.getText() + "', " +
                            sCidadePet.getSelectedItemId() + ", " +
                            bundle.getString("idUsuario") + ", " +
                            "'" + edtDetalhesPet.getText() + "', " +
                            "'" + edtDetalhesSumico.getText() + "', " +
                            "'" + ivFotoPetString + "')");
                    edtNomePet.setText("");
                    populaCidade(sCidadePet);
                    edtDetalhesPet.setText("");
                    edtDetalhesSumico.setText("");
                    ivFotoPet.setImageResource(R.drawable.gato);

                    Snackbar sbCadastroRealizado = Snackbar.make(findViewById(R.id.CoordinatorLayoutRegistroPet),"Pet cadastrado com sucesso. Boa sorte nas buscas!",Snackbar.LENGTH_SHORT);
                    sbCadastroRealizado.show();
                }
                else {
                    Snackbar sbCadastroRealizado = Snackbar.make(findViewById(R.id.CoordinatorLayoutRegistroPet),"É necessário informar uma Foto, Nome e Cidade!",Snackbar.LENGTH_SHORT);
                    sbCadastroRealizado.show();
                }
            }
        });

        btCancelarRegistroPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

    public void abrirCamera(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent dados) {
        super.onActivityResult(requestCode, resultCode, dados);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                try {
                    Uri imageUri = dados.getData();
                    Bitmap fotoBuscada = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);
                    Bitmap fotoRedimensionada = Bitmap.createBitmap(fotoBuscada,0,0,fotoBuscada.getWidth(),fotoBuscada.getHeight(),matrix,true);
                    fotoRedimensionada = Bitmap.createScaledBitmap(fotoRedimensionada,256,256,true);
                    ivFotoPet.setImageBitmap(fotoRedimensionada);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    fotoRedimensionada.compress(Bitmap.CompressFormat.PNG, 0, stream);
                    byte[] fotoEmBytes = stream.toByteArray();
                    ivFotoPetString = Base64.encodeToString(fotoEmBytes, Base64.DEFAULT);
                } catch (Exception e){

                }
            }
        }
    }
}