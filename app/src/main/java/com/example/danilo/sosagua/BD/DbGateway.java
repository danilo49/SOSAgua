package com.example.danilo.sosagua.BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DbGateway {
    private static DbGateway gw;
    private static SQLiteDatabase db;


    private DbGateway(Context ctx){
        DataBaseHelper helper = new DataBaseHelper(ctx);
        db = helper.getWritableDatabase(); //erro por fim aqui
    }

    public static DbGateway getInstance(Context ctx){
        if(gw == null)
            gw = new DbGateway(ctx); //passa por aqui
        return gw;
    }

    public SQLiteDatabase getDatabase(){
        return this.db;
    }

}
