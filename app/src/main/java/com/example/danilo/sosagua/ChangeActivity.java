package com.example.danilo.sosagua;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danilo.sosagua.BD.DAO.denunciadb;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChangeActivity extends AppCompatActivity {
    int codigo;
    String etaria,categoria,imovel,descricao,situacao,data,data2;
    double latitude,longitude;
    boolean sucesso = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        Bundle args = getIntent().getBundleExtra("changeComplaint");

        if ( args != null){
            latitude = args.getDouble("lat");
            longitude = args.getDouble("lon");
            codigo = args.getInt("cod");
            etaria = args.getString("eta");
            categoria = args.getString("cat");
            imovel = args.getString("imo");
            descricao = args.getString("desc");
            situacao = args.getString("sit");
            data = args.getString("dat");
            data2 = args.getString("dat2");
        }


        TextView Tcat = (TextView) findViewById(R.id.Tcat);
        Tcat.setText("Categoria: " + categoria);
        TextView Teta = (TextView) findViewById(R.id.Teta);
        Teta.setText("Faixa Etária: "+ etaria);
        TextView Tsit = (TextView) findViewById(R.id.Tsit);
        Tsit.setText("Status: " + situacao);
        TextView Timo = (TextView) findViewById(R.id.Timo);
        Timo.setText("Tipo do Imóvel: " + imovel);
        TextView Tdesc = (TextView) findViewById(R.id.Tdesc);
        Tdesc.setText(descricao);
        Tdesc.setMovementMethod(new ScrollingMovementMethod());
        TextView Tdata = (TextView) findViewById(R.id.Tdata);
        Tdata.setText("Data: " + data);


        botoes();
    }
    public void botoes(){
        Button NOT = (Button) findViewById(R.id.NOT);
        NOT.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangeActivity.this, MarksActivity.class);
                startActivity(intent);
                Toast.makeText(ChangeActivity.this, "Você foi redirecionado para a área de denúncias pendentes.", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        Button YES = (Button) findViewById(R.id.YES);
        YES.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Date date = new Date();
                SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                data2 = f.format(date);
                denunciadb db = new denunciadb(getBaseContext());


                situacao = "Resolvido";
                sucesso = db.update(codigo, etaria, categoria, imovel, situacao, descricao, latitude, longitude, data, data2);


                Toast.makeText(ChangeActivity.this, "Status alterado com sucesso!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ChangeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
