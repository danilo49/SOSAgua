package com.example.danilo.sosagua;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danilo.sosagua.BD.denunciadb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChangeActivity extends AppCompatActivity {
    private static final String TAG = "ChangeActivity";
    int codigo,confiavel;
    String etaria,categoria,imovel,descricao,situacao,data,data2;
    double latitude,longitude;
    boolean sucesso = false;

    String dataText;
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
            confiavel = args.getInt("confiavel");
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //String data = "2019-07-16 13:30:23";
        SimpleDateFormat formats = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {

            Date date = formatter.parse(data);
            //System.out.println(date);
            //System.out.println(formatter.format(date));
            //System.out.println(formats.format(date));
            dataText = formats.format(date);
            //System.out.println(dataText);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        /*
           // String -> Date
        SimpleDateFormat.parse(String);

        // Date -> String
        SimpleDateFormat.format(date);
         */
        TextView Tcat = (TextView) findViewById(R.id.Tcat);
        Tcat.setText(categoria);
        TextView Tsit = (TextView) findViewById(R.id.Tsit);
        Tsit.setText(situacao);
        TextView Timo = (TextView) findViewById(R.id.Timo);
        Timo.setText(imovel);
        EditText Tdesc = (EditText) findViewById(R.id.Tdesc);
        Tdesc.setText(descricao);
        disableEditText(Tdesc);
        TextView Tdata = (TextView) findViewById(R.id.Tdata);
        Tdata.setText(dataText);

        TextView text = (TextView) findViewById(R.id.link);
        text.setMovementMethod(LinkMovementMethod.getInstance());

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

                confiavel = 0;
                situacao = "Resolvido";
                sucesso = db.update(codigo, etaria, categoria, imovel, situacao, descricao, latitude, longitude, data, data2, confiavel);


                //Toast.makeText(ChangeActivity.this, ""+data2, Toast.LENGTH_SHORT).show();
                Toast.makeText(ChangeActivity.this, "Status alterado com sucesso!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ChangeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}

