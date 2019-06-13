package com.example.danilo.sosagua;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import static com.google.android.gms.plus.PlusOneDummyView.TAG;

public class UserInfoWindowAdapter implements GoogleMap.InfoWindowAdapter{
    LayoutInflater inflater = null;
    Intent intent;
    Context context;

    UserInfoWindowAdapter(LayoutInflater inflater, Intent intent,Context context){
        this.inflater = inflater;
        this.intent = intent;
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker arg0){
        return null;
    }

    @Override
    public View getInfoContents(Marker marker){
        View infoWindow = inflater.inflate(R.layout.user_info_windows, null);
        TextView title = (TextView)infoWindow.findViewById(R.id.title);
        TextView description = (TextView)infoWindow.findViewById(R.id.snippet);
        title.setText(marker.getTitle());
        description.setText(marker.getSnippet());



        Log.d(TAG, "onClick: TEEEEEEEEEEEEEEEEEEEEEEESTE 111111111111111111111111111");









        /*
        Button btnAltera = (Button) infoWindow.findViewById(R.id.btnAltera);
        btnAltera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Log.d(TAG, "TESTEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE22222222222222222222222");
                Toast.makeText(context, "TESTEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE22222222222222222222222", Toast.LENGTH_SHORT).show();


                Intent intent1 = new Intent(context, ChangeStatus.class);
                context.startActivity(intent1);
            }
        });
        Log.d(TAG, "TEEEEEEEEEESTEEEEEEEE 33333333333333333333333333333333333");
    */
        return(infoWindow);

    }

}
