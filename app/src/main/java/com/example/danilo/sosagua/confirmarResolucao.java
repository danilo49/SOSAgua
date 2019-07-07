package com.example.danilo.sosagua;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danilo.sosagua.BD.denunciadb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class confirmarResolucao extends AppCompatActivity {
    private static final String TAG = "confirmarResolucao";
    int codigo,confiavel;
    String etaria,categoria,imovel,descricao,situacao,data,data2;
    double latitude,longitude;
    boolean sucesso = false;

    String dataText1,dataText2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_resolucao);

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
            confiavel = args.getInt("conf");
        }



        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formats = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            Date date1 = formatter.parse(data);
            Date date2 = formatter.parse(data2);
            dataText1 = formats.format(date1);
            dataText2 = formats.format(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        TextView Tcat = (TextView) findViewById(R.id.Tcat);
        Tcat.setText(categoria);
        TextView Tsit = (TextView) findViewById(R.id.Tsit);
        Tsit.setText(situacao);
        TextView Timo = (TextView) findViewById(R.id.Timo);
        Timo.setText(imovel);
        EditText Tdesc = (EditText) findViewById(R.id.Tdesc);
        Tdesc.setText(descricao);
        disableEditText(Tdesc);
        TextView Tdata1 = (TextView) findViewById(R.id.Tdata1);
        Tdata1.setText(dataText1);

        TextView Tdata2 = (TextView) findViewById(R.id.Tdata2);
        Tdata2.setText(dataText2);

        TextView Tconf = (TextView) findViewById(R.id.Tconf);
        Tconf.setText(String.valueOf(confiavel));

        botoes();
    }

    private void disableEditText(EditText editText) {
        editText.setFocusable(true);
        editText.setEnabled(true);
        editText.setCursorVisible(true);
        editText.setKeyListener(null);
        editText.setBackgroundColor(Color.GRAY);
        editText.setMovementMethod(new ScrollingMovementMethod());
    }
    public void botoes(){
        /*
        Button NOT = (Button) findViewById(R.id.NOT);
        NOT.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangeActivity.this, MarksActivity.class);
                startActivity(intent);
                Toast.makeText(.this, "Você foi redirecionado para a área de denúncias pendentes.", Toast.LENGTH_LONG).show();
                finish();
            }
        });*/

        Button Confirmar = (Button) findViewById(R.id.Confirm);
        Confirmar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                denunciadb db = new denunciadb(getBaseContext());

                confiavel++;

                sucesso = db.update(codigo, etaria, categoria, imovel, situacao, descricao, latitude, longitude, data, data2, confiavel);



                Toast.makeText(confirmarResolucao.this, "Obrigado por aumentar a confiabilidade de nossas informações!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(confirmarResolucao.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });





    }
}