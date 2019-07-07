package com.example.danilo.sosagua;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danilo.sosagua.BD.DAO.denunciadb;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

    String categoria,etaria,imovel,situacao,descricao,dataStr,dataStr2;
    Spinner spinnerCat; //spinner categoria
    Spinner spinnerET; //spinner Faixa etaria
    Spinner spinnerIM; //spinner IMOVEL
    //Spinner spinnerSitu; //spinner situacao
    boolean sucesso = false;

    double latitude;
    double longitude;
    private Location mLastLocation;

    public Location getmLastLocation() {
        return mLastLocation;
    }
    public void setmLastLocation(Location mLastLocation) {
        this.mLastLocation = mLastLocation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        Bundle args = getIntent().getBundleExtra("locationMapActivity");//pega a latitude e longitude enviadas do MapActivity e coloca dentro
                                                                                // Bundle args novamente, assim como no MapActivity
        if ( args != null){
            //No MapActivity é feito um "empacotamento" da latitute e longitude, agora vamos "desempacotar" as informacoes
            latitude = args.getDouble("lat");
            longitude = args.getDouble("long");

        }

        spinnerCat = (Spinner) findViewById(R.id.spinnerCat);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categoria, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCat.setAdapter(adapter);
        spinnerCat.setOnItemSelectedListener(this);

        spinnerET = (Spinner) findViewById(R.id.spinnerEtaria);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.etaria, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerET.setAdapter(adapter1);
        spinnerET.setOnItemSelectedListener(this);

        spinnerIM = (Spinner) findViewById(R.id.spinnerImovel);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.imovel, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIM.setAdapter(adapter2);
        spinnerIM.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(final AdapterView<?> parent, View view, int position, long id) {
        //String text = parent.getItemAtPosition(position).toString();
        //Toast.makeText(parent.getContext(), text,Toast.LENGTH_LONG).show();

        categoria = spinnerCat.getSelectedItem().toString();
        etaria = spinnerET.getSelectedItem().toString();
        imovel = spinnerIM.getSelectedItem().toString();
        situacao = "Pendente";

        Button btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        TextView Textdescricao = findViewById(R.id.descricaoID);
        Textdescricao.setMovementMethod(new ScrollingMovementMethod());
        descricao = Textdescricao.getText().toString();


            if (descricao != null && !descricao.isEmpty()) {
                Date date = new Date();
                SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                dataStr = f.format(date);

                dataStr2 = null;
                denunciadb db = new denunciadb(getBaseContext());
                sucesso = db.salvar(etaria, categoria, imovel, situacao, descricao, latitude, longitude, dataStr);

                /*
                Toast.makeText(parent.getContext(), categoria, Toast.LENGTH_LONG).show();
                Toast.makeText(parent.getContext(), etaria, Toast.LENGTH_LONG).show();
                Toast.makeText(parent.getContext(), imovel, Toast.LENGTH_LONG).show();
                Toast.makeText(parent.getContext(), situacao, Toast.LENGTH_LONG).show();
                Toast.makeText(parent.getContext(), descricao, Toast.LENGTH_LONG).show();
                //Toast.makeText(parent.getContext(), latitude, Toast.LENGTH_LONG).show();
                //Toast.makeText(parent.getContext(), longitude, Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), "lat: " + latitude,                Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "long:" + longitude,                Toast.LENGTH_SHORT).show();
                Toast.makeText(parent.getContext(), dataStr, Toast.LENGTH_LONG).show();
                    */
                if (sucesso) {
                    Toast.makeText(getApplicationContext(), "Denúncia cadastrada!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DataActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } else
                    Toast.makeText(getApplicationContext(), "Ocorreu um erro.", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(),"Favor preencher o campo descrição com até 1000 caracteres.",Toast.LENGTH_LONG).show();
            }
            }

        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
