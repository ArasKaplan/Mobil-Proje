package com.araskaplan.closett;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.libraries.places.api.Places;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button drawerButton,cabinbutton,eventButton;

    public static ArrayList<Drawer> drawers;
    public static ArrayList<Combination> combinations;

    public static ArrayList<Combination> getCombinations() {
        return combinations;
    }
    public static ArrayList<Drawer> getDrawers() {
        return drawers;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        drawerButton=findViewById(R.id.main_act_drawers);
        drawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),DrawerActivity.class);
                startActivity(intent);
            }
        });
        cabinbutton=findViewById(R.id.main_act_cabin);
        cabinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),CabinActivity.class);
                startActivity(intent);
            }
        });
        eventButton=findViewById(R.id.main_act_events);
        eventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),EventActivity.class);
                startActivity(intent);
            }
        });

    }
    public static Bitmap getBitmapFromBytes(byte[] bytes) {
        if (bytes != null) {
            return BitmapFactory.decodeByteArray(bytes, 0 ,bytes.length);
        }
        return null;
    }

    public static byte[] getBytesFromBitmap(Bitmap bitmap) {
        if (bitmap!=null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
            return stream.toByteArray();
        }
        return null;
    }
    ChargeBroadcaster chargeBroadcaster;

    @Override
    protected void onResume() {
        super.onResume();

        chargeBroadcaster=new ChargeBroadcaster();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        registerReceiver(chargeBroadcaster,intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(chargeBroadcaster);
    }
}