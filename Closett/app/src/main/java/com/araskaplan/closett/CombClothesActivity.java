package com.araskaplan.closett;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class CombClothesActivity extends AppCompatActivity {
    String type;
    RecyclerView recyclerView;
    ArrayList<Clothing> clothes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comb_clothes);
        Intent getterintent=getIntent();
        type=getterintent.getStringExtra("type");
        SQLiteHelper sqLiteHelper=new SQLiteHelper(getApplicationContext());
        clothes=sqLiteHelper.getClothesBytype(type);

        CombClothesAdapter combClothesAdapter=new CombClothesAdapter(clothes,this);

        recyclerView = findViewById(R.id.comb_clothes_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(combClothesAdapter);


    }
}