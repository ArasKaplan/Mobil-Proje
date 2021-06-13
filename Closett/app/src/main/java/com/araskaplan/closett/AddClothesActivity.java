package com.araskaplan.closett;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class AddClothesActivity extends AppCompatActivity {
    Button done;
    EditText name,color,pattern,date,price;
    Spinner type;
    ImageView imageView;
    Bitmap selectedimage;
    ArrayList<String> types;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clothes);

        Intent intent=getIntent();
        int mode=intent.getIntExtra("edit",0);
        int pos=intent.getIntExtra("drawerpos",-1);
        int cpos=intent.getIntExtra("clothespos",-1);

        SQLiteHelper database=new SQLiteHelper(getApplicationContext());
        ArrayList<Clothing> clothes=database.getDrawers().get(pos).getClothes();

        done=findViewById(R.id.add_clothing_done);
        name=findViewById(R.id.add_clothing_name);
        color=findViewById(R.id.add_clothing_color);
        type=findViewById(R.id.add_clothing_spinner);
        pattern=findViewById(R.id.add_clothing_pattern);
        date=findViewById(R.id.add_clothing_date);
        price=findViewById(R.id.add_clothing_price);
        imageView=findViewById(R.id.add_clothing_img);

        types=new ArrayList<>();
        types.add("Overhead");
        types.add("Face");
        types.add("Torso");
        types.add("Legs");
        types.add("Shoes");

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item,types);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(arrayAdapter);

        SQLiteHelper sqLiteHelper=new SQLiteHelper(getApplicationContext());

        if(mode==1){
            fillBoxes();
        }


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedimage!=null){
                    Clothing temp=new Clothing(name.getText().toString(),pattern.getText().toString(),color.getText().toString(),types.get(type.getSelectedItemPosition()),price.getText().toString(),date.getText().toString());
                    temp.setDrawerid(database.getDrawers().get(pos).getId());
                    temp.setImg(selectedimage);
                    if(mode==1){
                        temp.setId(clothes.get(cpos).getId());
                        clothes.set(cpos,temp);
                        sqLiteHelper.updateClothing(clothes.get(cpos));
                        Toast.makeText(v.getContext(),"Clothing Updated",Toast.LENGTH_LONG).show();
                    }
                    else{
                        sqLiteHelper.addClothing(temp);
                        clothes.add(temp);
                        Toast.makeText(v.getContext(),"Clothing Added",Toast.LENGTH_LONG).show();
                    }

                    Intent intent1=new Intent(v.getContext(),ClothesActivity.class);
                    intent1.putExtra("drawerpos",pos);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                }
                else {
                    Toast.makeText(v.getContext(),"Select an image first",Toast.LENGTH_LONG).show();
                }
            }
        });
    }




    public void getImageFromGallery(View view){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }else {
            Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,2);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,2);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==2 && resultCode==RESULT_OK && data!=null){
            Uri imageData=data.getData();
            try {
                if(Build.VERSION.SDK_INT>=28){
                    ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(),imageData);
                    selectedimage= ImageDecoder.decodeBitmap(source);
                    imageView.setImageBitmap(selectedimage);

                }
                else{
                    selectedimage= MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageData);
                    imageView.setImageBitmap(selectedimage);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void fillBoxes(){
        SQLiteHelper database=new SQLiteHelper(getApplicationContext());
        Intent intent=getIntent();
        int drawerpos=intent.getIntExtra("drawerpos",-1);
        int clothespos=intent.getIntExtra("clothespos",-1);
        Clothing current=database.getDrawers().get(drawerpos).getClothes().get(clothespos);
        imageView.setImageBitmap(current.getImg());
        selectedimage=current.getImg();
        name.setText(current.getName());
        color.setText(current.getColor());
       // type.setText(current.getType());
        pattern.setText(current.getPattern());
        price.setText(current.getPrice());
        date.setText(current.getDate_of_purchase());

        int i=0;
        for (String string:types){
            if (string.equals(current.getType())){
                type.setSelection(i);
                break;
            }
            i++;
        }
    }
}