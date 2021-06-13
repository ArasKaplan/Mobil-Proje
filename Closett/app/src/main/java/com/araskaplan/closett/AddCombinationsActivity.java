package com.araskaplan.closett;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddCombinationsActivity extends AppCompatActivity {
    Button done;
    ImageView overhead,face,torso,legs,shoes;
    EditText name;
    int mode;
    Combination combination;
    SQLiteHelper sqLiteHelper;
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

        Intent intent=new Intent(getApplicationContext(),CombClothesActivity.class);


        sqLiteHelper=new SQLiteHelper(getApplicationContext());

        combination=new Combination("temp");
        Intent getterintent=getIntent();
        mode=getterintent.getIntExtra("mode",0);
        if(mode==1){
            fillComponents(getterintent.getIntExtra("id",0));
        }

        overhead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("type","Overhead");
                startActivityForResult(intent,1);
            }
        });
        face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("type","Face");
                startActivityForResult(intent,2);
            }
        });
        torso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("type","Torso");
                startActivityForResult(intent,3);
            }
        });
        legs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("type","Legs");
                startActivityForResult(intent,4);
            }
        });
        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("type","Shoes");
                startActivityForResult(intent,5);
            }
        });


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                combination.setName(name.getText().toString());
                if (mode==1){
                    sqLiteHelper.updateCombination(combination);
                    Toast.makeText(v.getContext(),"Combination Updated",Toast.LENGTH_LONG).show();
                }
                else{
                    sqLiteHelper.addCombination(combination);
                    Toast.makeText(v.getContext(),"Combination Added",Toast.LENGTH_LONG).show();
                }
                Intent intent=new Intent(v.getContext(),CabinActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if (resultCode==RESULT_OK){
                int result=data.getIntExtra("result",0);
                combination.setOverhead(sqLiteHelper.getClothing(result));
                overhead.setImageBitmap(sqLiteHelper.getClothing(result).getImg());
            }
        }
        if(requestCode==2){
            if (resultCode==RESULT_OK){
                int result=data.getIntExtra("result",0);
                combination.setFace(sqLiteHelper.getClothing(result));
                face.setImageBitmap(sqLiteHelper.getClothing(result).getImg());
            }
        }
        if(requestCode==3){
            if (resultCode==RESULT_OK){
                int result=data.getIntExtra("result",0);
                combination.setTorso(sqLiteHelper.getClothing(result));
                torso.setImageBitmap(sqLiteHelper.getClothing(result).getImg());
            }
        }
        if(requestCode==4){
            if (resultCode==RESULT_OK){
                int result=data.getIntExtra("result",0);
                combination.setLegs(sqLiteHelper.getClothing(result));
                legs.setImageBitmap(sqLiteHelper.getClothing(result).getImg());
            }
        }
        if(requestCode==5){
            if (resultCode==RESULT_OK){
                int result=data.getIntExtra("result",0);
                combination.setFeet(sqLiteHelper.getClothing(result));
                shoes.setImageBitmap(sqLiteHelper.getClothing(result).getImg());
            }
        }

    }
    public void fillComponents(int id){
        combination=sqLiteHelper.getCombination(id);
        overhead.setImageBitmap(sqLiteHelper.getClothing(combination.getOverhead().getId()).getImg());
        face.setImageBitmap(sqLiteHelper.getClothing(combination.getFace().getId()).getImg());
        torso.setImageBitmap(sqLiteHelper.getClothing(combination.getTorso().getId()).getImg());
        legs.setImageBitmap(sqLiteHelper.getClothing(combination.getLegs().getId()).getImg());
        shoes.setImageBitmap(sqLiteHelper.getClothing(combination.getFeet().getId()).getImg());
        name.setText(combination.getName());
    }


}