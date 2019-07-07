package com.example.danilo.sosagua.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.example.danilo.sosagua.BD.DbGateway;
import com.example.danilo.sosagua.BD.Denuncia;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class denunciadb {

    private final String TABLE_DENUNCIA = "DenunciaBD";
    private DbGateway gw;

    public denunciadb (Context ctx) {
        gw = DbGateway.getInstance(ctx);
    }

    public boolean salvar(String etaria,String categoria,String imovel,String situacao,String descricao,double latitude,
                          double longitude,String data){
        return salvar(0,etaria,categoria,imovel,situacao,descricao,latitude,longitude,data);
    }


    public boolean salvar(int codigo,String etaria,String categoria,String imovel,String situacao,String descricao,
                          double latitude,double longitude,String data) {
        ContentValues cv = new ContentValues();

        cv.put("etaria", etaria);
        cv.put("categoria", categoria);
        cv.put("imovel", imovel);
        cv.put("situacao", situacao);
        cv.put("descricao", descricao);
        //cv.put("codigo", codigo);
        cv.put("latitude", latitude);
        cv.put("longitude", longitude);
        cv.put("data", data);
        //cv.put("data2", data2);

        if(codigo > 0)
            return gw.getDatabase().update(TABLE_DENUNCIA, cv, "ID=?", new String[]{ codigo + "" }) > 0;
        else
            return gw.getDatabase().insert(TABLE_DENUNCIA, null, cv) > 0;
    }
    public int verificaCodigoPendente(){
        Cursor cursor = gw.getDatabase().rawQuery("SELECT * FROM DenunciaBD",null);
        int flag=0;
        if(cursor.getCount()>0)
            return flag=1;
        return flag;
    }
    public int verificaCodigoResolvido(){
        Cursor cursor = gw.getDatabase().rawQuery("SELECT * FROM DenunciaBD ",null);
        int flag=0;
        if(cursor.getCount()>0)
            return flag=1;
        return flag;
    }





    public  boolean update(int codigo,String etaria,String categoria,String imovel,String situacao,String descricao,
                           double latitude,double longitude,String data,String data2,int confiavel){
        ContentValues cv = new ContentValues();
        cv.put("etaria", etaria);
        cv.put("categoria", categoria);
        cv.put("imovel", imovel);
        cv.put("situacao", situacao);
        cv.put("descricao", descricao);
        cv.put("codigo", codigo);
        cv.put("latitude", latitude);
        cv.put("longitude", longitude);
        cv.put("data", data);
        cv.put("data2", data2);
        cv.put("confiavel", confiavel);
        return gw.getDatabase().update(TABLE_DENUNCIA,cv ,"codigo ='" + codigo + "'", null) > 0;
    }

    public List<Denuncia> denuncias() {
        List<Denuncia> lista = new ArrayList<>();

        Cursor cursor = gw.getDatabase().rawQuery("SELECT * FROM DenunciaBD",null);

        while(cursor.moveToNext()){
            int codigo = cursor.getInt(cursor.getColumnIndex("codigo"));
            String etaria = cursor.getString(cursor.getColumnIndex("etaria"));
            String categoria = cursor.getString(cursor.getColumnIndex("categoria"));
            String imovel = cursor.getString(cursor.getColumnIndex("imovel"));
            String descricao = cursor.getString(cursor.getColumnIndex("descricao"));
            String situacao = cursor.getString(cursor.getColumnIndex("situacao"));
            double latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
            double longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
            String data = cursor.getString(cursor.getColumnIndex("data"));
            String data2 = cursor.getString(cursor.getColumnIndex("data2"));
            int confiavel = cursor.getInt(cursor.getColumnIndex("confiavel"));

            lista.add(new Denuncia(categoria,etaria,imovel,situacao,descricao,codigo,latitude,longitude,data,data2,confiavel));
        }
        cursor.close();
        return lista;
    }



}
