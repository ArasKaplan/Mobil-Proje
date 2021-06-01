package com.araskaplan.closett;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AddCombinationsActivity extends AppCompatActivity {
    Button done;
    ImageView overhead,face,torso,legs,shoes;
    EditText name;
    int result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_combinations);

        //ArrayList<Combination>combinations=MainActivity.getCombinations();

        done=findViewById(R.id.add_comb_act_done);
        overhead=findViewById(R.id.add_comb_act_overheadimg);
        face=findViewById(R.id.add_comb_act_faceimg);
        torso=findViewById(R.id.add_comb_act_torsoimg);
        legs=findViewById(R.id.add_comb_act_legsimg);
        shoes=findViewById(R.id.add_comb_act_shoesimg);

        name=findViewById(R.id.add_comb_act_name);


        overhead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),CombClothesActivity.class);
                intent.putExtra("mode",0);
                startActivityForResult(intent,1);

            }
        });


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if (resultCode==RESULT_OK){
                result=data.getIntExtra("result",-1);
            }
        }
    }
}