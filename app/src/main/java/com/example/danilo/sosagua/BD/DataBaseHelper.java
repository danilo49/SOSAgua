package com.example.danilo.sosagua.BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SOSAguaApp";
    private static final int DATABASE_VERSION = 1;

    private static final String TABELA_DENUNCIA = "DenunciaBD";

    private static final String CATEGORIA = "categoria";
    private static final String ETARIA = "etaria";
    private static final String IMOVEL = "imovel";
    private static final String SITUACAO = "situacao";
    private static final String DESCRICAO = "descricao";
    private static final String CODIGO = "codigo";
    private static final String DATA = "data";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String DATA2 = "data2";

    private static final String CREATE_TABLE_DENUNCIA = " CREATE TABLE "
        + TABELA_DENUNCIA
        + "(" + CODIGO + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
        + CATEGORIA + " VARCHAR(50) NOT NULL, "
        + ETARIA + " VARCHAR(30) NOT NULL, "
        + IMOVEL + " VARCHAR(30) NOT NULL, "
        + SITUACAO + " VARCHAR(30) NOT NULL, "
        + DESCRICAO + " VARCHAR(1000) NOT NULL,"
        + DATA + " VARCHAR(11) NOT NULL,"
        + LATITUDE + " DECIMAL(9,6) NOT NULL,"
        + LONGITUDE + " DECIMAL(9,6) NOT NULL, "
        + DATA2 + " VARCHAR(11))";




    public DataBaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static DataBaseHelper instance;

    private static synchronized DataBaseHelper getHelper(Context context){
        if (instance == null)
            instance = new DataBaseHelper(context);
        return instance;
    }

    @Override
    public void onOpen(SQLiteDatabase db){
        super.onOpen(db);
        if (!db.isReadOnly()){
            db.execSQL("PRAGMA foreign_key=ON");
        }

    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_DENUNCIA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
