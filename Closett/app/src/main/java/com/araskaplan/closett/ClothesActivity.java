package com.araskaplan.closett;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ClothesActivity extends AppCompatActivity {
    FloatingActionButton fab;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes);

        recyclerView=findViewById(R.id.clothes_act_recylerview);
        fab=findViewById(R.id.clothes_act_fab);

        SQLiteHelper database=new SQLiteHelper(getApplicationContext());

        Intent intent=getIntent();
        int pos=intent.getIntExtra("drawerpos",0);
        System.out.println("Drawerpos:"+pos);

        //ArrayList<Clothing>clothes=database.getClothesofDrawer(pos);
        ArrayList<Clothing>clothes=database.getDrawers().get(pos).getClothes();
        ClothesAdapter clothesAdapter=new ClothesAdapter(clothes,pos);


        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(clothesAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(v.getContext(),AddClothesActivity.class);
                intent1.putExtra("drawerpos",pos);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
            }
        });
    }
}