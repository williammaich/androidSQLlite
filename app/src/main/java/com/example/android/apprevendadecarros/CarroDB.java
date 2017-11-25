package com.example.android.apprevendadecarros;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by android on 06/11/2017.
 */

public class CarroDB extends SQLiteOpenHelper {


    public CarroDB(Context context) {
        super(context, "revenda.sqlite", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table if not exists carros(" +
                "_id integer primary key autoincrement," +
                "modelo text, ano integer, preco real );");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public long inclui(Carros carro) {

        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues dados = new ContentValues();
            dados.put("modelo", carro.getModelo());
            dados.put("ano", carro.getAno());
            dados.put("preco", carro.getPreco());

            long id = db.insert("carros", "", dados);

            return id;
        } finally {
            db.close();
        }

    }


    public Carros busca(long id) {
        SQLiteDatabase db = getReadableDatabase();

        try {
            Cursor c = db.query("carros", null, "_id=?",
                    new String[]{String.valueOf(id)},
                    null, null, null);

            if (c.getCount() > 0) {
                c.moveToFirst();
                String modelo = c.getString(1);
                int ano = c.getInt(2);
                double preco = c.getDouble(3);
                return new Carros(id, modelo, ano, preco);
            } else {
                return new Carros(0, "", 0, 0);
            }

        } finally {
            db.close();
        }

    }

    public long altera(Carros carro){

        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues dados = new ContentValues();
            dados.put("modelo", carro.getModelo());
            dados.put("ano", carro.getAno());
            dados.put("preco", carro.getPreco());

            long id = db.update("carros", dados, "_id=?", new String[] {String.valueOf(carro.getId())});

            return id;
        } finally {
            db.close();
        }

    }

    public long exclui (long id){

        SQLiteDatabase db = getWritableDatabase();
        try{

            return db.delete("carros", "_id=?", new String[]{String.valueOf(id)});

        }finally {
            db.close();
        }

    }

}
