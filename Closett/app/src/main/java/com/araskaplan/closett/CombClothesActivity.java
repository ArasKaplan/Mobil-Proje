package com.araskaplan.closett;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class CombClothesActivity extends AppCompatActivity {
    int mode;
    RecyclerView recyclerView;
    ArrayList<Clothing> clothes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comb_clothes);

        for (Drawer drawer:MainActivity.getDrawers()){
            clothes.addAll(drawer.getClothes());
        }

        recyclerView = findViewById(R.id.comb_clothes_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        Intent intent = getIntent();
        mode = intent.getIntExtra("mode", 0);
       /* if (mode == 0) {
            //    CombClothesAdapter clothesAdapter=new CombClothesAdapter(MainActivity.getoverheads,this);
        } else if (mode == 1) {
            //    CombClothesAdapter clothesAdapter=new CombClothesAdapter(MainActivity.getfaces,this);
        }else if (mode==2){
            //    CombClothesAdapter clothesAdapter=new CombClothesAdapter(MainActivity.gettorsos,this);
        }else if(mode==3){
            //    CombClothesAdapter clothesAdapter=new CombClothesAdapter(MainActivity.getlegs,this);
        }else if(mode==4){
            //    CombClothesAdapter clothesAdapter=new CombClothesAdapter(MainActivity.getshoes,this);
        }*/
        CombClothesAdapter combClothesAdapter=new CombClothesAdapter(clothes,this);

    }
}