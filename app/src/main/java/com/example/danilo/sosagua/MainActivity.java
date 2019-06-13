package com.example.danilo.sosagua;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.danilo.sosagua.BD.DAO.denunciadb;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity  {

    private static final String TAG = "MainActivity";

    private static final int ERROR_DIALOG_REQUEST = 9001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toast.makeText(this, "Bem vindo!", Toast.LENGTH_SHORT).show();
        if(isServicesOK()){
            init();
        }
        /*denunciadb dao = new denunciadb(getBaseContext());
        boolean verifica = dao.salvar("12","capitação irregular","Domiciliar","Pendente","qqoiweuoiqwueoi",-18.727514, -47.496114, "2018-11-13");
        if(verifica){
            Toast.makeText(this, "Denuncia Salva", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, " nao Salva", Toast.LENGTH_SHORT).show();
        */
    }

    private void init(){
        Button btnMap = (Button) findViewById(R.id.btnMap); // botao para abrir o mapa de denuncia
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
        Log.d(TAG, "init: ENTRANDO NA ACTIVITY DE DENUNCIAS PENDENTES");
        Button btnMarkMap = (Button) findViewById(R.id.btnMarkMap); // botao para abrir o mapa de denuncias pendentes
        btnMarkMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, MarksActivity.class);
                startActivity(intent1);
            }
        });

        Button btnResolvido = (Button) findViewById(R.id.btnResolvido);
        btnResolvido.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent2 = new Intent(MainActivity.this, MarkSortedOutActivity.class);
                startActivity(intent2);
            }
        });
    }

    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //está tudo bem e o usuário pode fazer solicitações de mapas
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //Ocorreu um erro, mas podemos resolvê-lo
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "Você não pode fazer solicitações de mapa", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

}
