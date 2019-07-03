package com.example.danilo.sosagua;

import android.Manifest;
import android.app.Activity;

import android.app.AlertDialog;
import android.app.DialogFragment;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danilo.sosagua.BD.DAO.denunciadb;
import com.example.danilo.sosagua.BD.Denuncia;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.List;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;

public class MarksActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "MarksActivity";
    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;

    //vars
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Context context;
    private String codigo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_marks);


        getLocationPermission();


    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

        }
    }

    @SuppressWarnings("unchecked")
    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(mLocationPermissionsGranted){
                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: found location!");

                            Location currentLocation = (Location) task.getResult();

                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM);
                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(MarksActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
        salvar();
    }

    private void moveCamera(LatLng latLng, float zoom){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    @SuppressWarnings("unchecked")
    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MarksActivity.this);

    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                initMap();
            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //inicializa o mapa
                    initMap();
                }
            }
        }
    }


    public void salvar() {

        final denunciadb denuncia = new denunciadb(getBaseContext());
        int tam = denuncia.verificaCodigoPendente();
        List<Denuncia> lista = denuncia.denuncias();
        final List<Denuncia> lista2 = denuncia.denuncias();


        if (tam > 0) {
            for (Denuncia d : lista) {
                LatLng mark = new LatLng(d.getLatitude(), d.getLongitude());
                if (mMap != null) {
                   if(d.getSituacao().equals("Pendente")) {

                       mMap.addMarker(new MarkerOptions().position(mark).title("Clique para alterar o status da denúncia").snippet("Codigo da denúncia:"+ d.getCodigo()).draggable(false)
                               .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                       Intent intent = new Intent(MarksActivity.this, UserInfoWindowAdapter.class);
                       mMap.setInfoWindowAdapter(new UserInfoWindowAdapter(getLayoutInflater(),intent,this));
                   }
                }
            }
        }else
            Toast.makeText(MarksActivity.this, "Não existem denúncias cadastradas atualmente", Toast.LENGTH_SHORT).show();

            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener()
            {
                @Override
                public void onInfoWindowClick(Marker arg0) {

                    for(Denuncia d : lista2) {
                        String id = arg0.getId().substring(1);
                        int id1 = Integer.parseInt(id)+1;
                        id = Integer.toString(id1);

                        if(id.equals(Integer.toString(d.getCodigo()))) {

                            Bundle args = new Bundle();
                            args.putInt("cod", d.getCodigo());
                            args.putString("eta",d.getEtaria());
                            args.putString("cat",d.getCategoria());
                            args.putString("imo",d.getImovel());
                            args.putString("desc",d.getDescricao());
                            args.putString("sit",d.getSituacao());
                            args.putString("dat",d.getData());
                            args.putDouble("lat",d.getLatitude());
                            args.putDouble("lon",d.getLongitude());
                            //args.putString("dat2", d.getData2());

                            Intent intent = new Intent(MarksActivity.this, ChangeActivity.class);
                            intent.putExtra("changeComplaint",args);
                            startActivity(intent);
                        }
                    }
                }
            });
        }
    }
