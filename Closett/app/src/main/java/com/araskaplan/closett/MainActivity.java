package com.araskaplan.closett;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button drawerButton,cabinbutton;

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

        /*combinations=new ArrayList<>();
        drawers=new ArrayList<>();

         */
        /*
        Drawer temp=new Drawer(0,"drawer#1");
        Drawer temp2=new Drawer(1,"drawer#2");
        Drawer temp3=new Drawer(2,"drawer#3");
        drawers.add(temp);
        drawers.add(temp2);
        drawers.add(temp3);
        */

        SQLiteHelper sqLiteHelper=new SQLiteHelper(getApplicationContext());
        /*ArrayList<Clothing>temp=new ArrayList<>();
        temp=sqLiteHelper.getAllClothing();
        for(Clothing clothing:temp){
            System.out.println("Name:"+clothing.getName()+"DrawerId:"+clothing.getDrawerid());
        }*/
        ArrayList<Drawer>temp2=new ArrayList<>();
        temp2=sqLiteHelper.getDrawers();
        /*System.out.println("Drawers");
        for(Drawer drawer:temp2){
            System.out.println("Name:"+drawer.getName()+"Drawerid"+drawer.getId());
        }*/
        for (Clothing clothing:temp2.get(0).getClothes()){
            System.out.println("Name:"+clothing.getName()+"Id:"+clothing.getId());
        }

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
}