package com.example.petfound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class EditorPet extends AppCompatActivity {

    private ImageView ivFotoPetEdit;
    private Button btAlterarFoto;
    private EditText edtNomePetEdit;
    private Spinner sCidadePetEdit;
    private EditText edtDetalhesPetEdit;
    private EditText edtDetalhesSumicoEdit;
    private Button btAtualizarPet;
    private Button btCancelarAtualizacaoPet;
    private SQLiteDatabase db = null;
    private Bundle bundle = new Bundle();
    private String ivFotoPetString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_pet);
        db = new DatabaseManager(this, "BancoDados", null, 6).getWritableDatabase();

        ivFotoPetEdit = (ImageView) findViewById(R.id.iv_foto_pet_edit);
        btAlterarFoto = (Button) findViewById(R.id.bt_alterar_foto);
        edtNomePetEdit = (EditText) findViewById(R.id.edt_nome_pet_edit);
        sCidadePetEdit = (Spinner) findViewById(R.id.s_cidade_pet_edit);
        edtDetalhesPetEdit = (EditText) findViewById(R.id.edt_detalhes_pet_edit);
        edtDetalhesSumicoEdit = (EditText) findViewById(R.id.edt_detalhes_sumico_edit);
        btAtualizarPet = (Button) findViewById(R.id.bt_atualizar_pet);
        btCancelarAtualizacaoPet = (Button) findViewById(R.id.bt_cancelar_atualizacao_pet);

        populaCidade(sCidadePetEdit);
        bundle = getIntent().getExtras();
        carregaDados(bundle.getInt("idPet"));

        btAlterarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        btCancelarAtualizacaoPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btAtualizarPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ivFotoPetEdit.getDrawable() != null &&
                        edtNomePetEdit.getText() != null &&
                        sCidadePetEdit.getSelectedItemId() > 0) {
                        db.execSQL("update pet set nome = '" + edtNomePetEdit.getText() +
                                        "', id_cidade = " + sCidadePetEdit.getSelectedItemId() +
                                ", detalhes_pet = '" + edtDetalhesPetEdit.getText() +
                                        "', detalhes_sumico = '" + edtDetalhesSumicoEdit.getText() +
                                "', foto1 = '" + ivFotoPetString + "' " +
                                        " where id = " + bundle.getInt("idPet"));
                        finish();
                        /*Snackbar sbCadastroAtualizado = Snackbar.make(findViewById(R.id.CoordinatorLayoutEditorPet),"Registro do Pet atualizado com sucesso. Boa sorte nas buscas!", Snackbar.LENGTH_SHORT);
                        sbCadastroAtualizado.show();*/
                }
                else {
                    Snackbar sbCadastroAtualizado = Snackbar.make(findViewById(R.id.CoordinatorLayoutEditorPet),"É necessário informar uma Foto, Nome, cidade e detalhes do seu Pet!", Snackbar.LENGTH_SHORT);
                    sbCadastroAtualizado.show();
                }
            }
        });
    }

    public void carregaDados(int idPet){
        Cursor cur = db.rawQuery("select foto1, nome, id_cidade, detalhes_pet, detalhes_sumico" +
                " from pet where id = " + idPet, null);
        while (cur.moveToNext()) {
            byte[] imagemPetBytes = Base64.decode(cur.getBlob(0),Base64.DEFAULT);
            Bitmap foto = BitmapFactory.decodeByteArray(imagemPetBytes,0,imagemPetBytes.length);
            ivFotoPetEdit.setImageBitmap(foto);
            edtNomePetEdit.setText(cur.getString(1));
            sCidadePetEdit.setSelection(cur.getInt(2));
            edtDetalhesPetEdit.setText(cur.getString(3));
            edtDetalhesSumicoEdit.setText(cur.getString(4));
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent dados) {
        super.onActivityResult(requestCode, resultCode, dados);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                try {
                    Uri imageUri = dados.getData();

                    Bitmap fotoBuscada = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    Bitmap fotoRedimensionada = Bitmap.createScaledBitmap(fotoBuscada,256,256,true);

                    ivFotoPetEdit.setImageBitmap(fotoRedimensionada);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    fotoRedimensionada.compress(Bitmap.CompressFormat.PNG, 70, stream);
                    byte[] fotoEmBytes = stream.toByteArray();
                    ivFotoPetString = Base64.encodeToString(fotoEmBytes, Base64.DEFAULT);
                } catch (Exception e){

                }
            }
        }
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