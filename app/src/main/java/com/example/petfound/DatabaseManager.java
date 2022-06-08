    package com.example.petfound;

    import android.content.Context;
    import android.database.DatabaseErrorHandler;
    import android.database.sqlite.SQLiteDatabase;
    import android.database.sqlite.SQLiteOpenHelper;

    import androidx.annotation.Nullable;

    public class DatabaseManager extends SQLiteOpenHelper {
            public DatabaseManager(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
                super(context, name, factory, version);
            }

            public DatabaseManager(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
                super(context, name, factory, version, errorHandler);
            }

            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {
                sqLiteDatabase.execSQL("create table cidade(" +
                        "id integer primary key autoincrement, " +
                        "nome varchar(100) not null, " +
                        "estado char)");
                sqLiteDatabase.execSQL("create table usuario(" +
                        "id integer primary key autoincrement, " +
                        "nome varchar(200) not null, " +
                        "email varchar(100) not null, " +
                        "senha varchar(200) not null, " +
                        "telefone varchar(50), " +
                        "foto blob," +
                        "id_cidade int not null, " +
                        "constraint id_cidade foreign key (id_cidade) references cidade)");
                sqLiteDatabase.execSQL("insert into cidade " +
                        "(nome,estado) " +
                        "values ('Lajeado','RS'),('Encantado','RS')");
                sqLiteDatabase.execSQL("create table pet(" +
                        "id integer primary key autoincrement, " +
                        "nome varchar(100) not null, " +
                        "id_cidade integer not null, " +
                        "detalhes_pet text, " +
                        "detalhes_sumico text, "+
                        "foto1 blob not null, " +
                        "foto2 blob, " +
                        "foto3 blob, " +
                        "foto4 blob, " +
                        "foto5 blob, " +
                        "constraint id_cidade foreign key (id_cidade) references cidade)");
                sqLiteDatabase.execSQL("create table mensagem(" +
                        "id integer primary key autoincrement, " +
                        "mensagem text not null, " +
                        "fone text, " +
                        "data_envio datetime not null, " +
                        "data_leitura datetime, " +
                        "id_usuario_origem integer not null, " +
                        "id_usuario_destino integer not null, " +
                        "constraint id_usuario_origem foreign key (id_usuario_origem) references usuario," +
                        "constraint id_usuario_destino foreign key (id_usuario_destino) references usuario)");
            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
                sqLiteDatabase.execSQL("drop table cidade");
                sqLiteDatabase.execSQL("drop table usuario");
                sqLiteDatabase.execSQL("drop table pet");
                sqLiteDatabase.execSQL("drop table mensagem");
                onCreate(sqLiteDatabase);

            }


        }
