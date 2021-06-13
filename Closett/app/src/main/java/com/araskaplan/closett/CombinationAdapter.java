package com.araskaplan.closett;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class CombinationAdapter extends RecyclerView.Adapter<CombinationAdapter.MyViewHolder> {

    ArrayList<Combination>combinations;

    public CombinationAdapter(ArrayList<Combination> combinations) {
        this.combinations = combinations;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.combination_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.overhead.setText("Overhead");
        holder.face.setText("Face");
        holder.torso.setText("Torso");
        holder.legs.setText("Legs");
        holder.shoes.setText("Shoes");

        holder.overheadimg.setImageBitmap(combinations.get(position).getOverhead().getImg());
        holder.faceimg.setImageBitmap(combinations.get(position).getFace().getImg());
        holder.torsoimg.setImageBitmap(combinations.get(position).getTorso().getImg());
        holder.legsimg.setImageBitmap(combinations.get(position).getLegs().getImg());
        holder.shoesimg.setImageBitmap(combinations.get(position).getFeet().getImg());
        holder.name.setText("Combination Name:"+combinations.get(position).getName());
        holder.share.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   OutputStream outputStream=null;
                Bitmap[] selectedimages=new Bitmap[5];
                selectedimages[0]=combinations.get(position).getOverhead().getImg();
                File filepath= Environment.getExternalStorageDirectory();
                File dir=new File(filepath.getAbsolutePath()+"/Demo/");
                dir.mkdir();
                File file=new File(dir,"overhead.jpg");
                try {
                    outputStream=new FileOutputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                selectedimages[0].compress(Bitmap.CompressFormat.JPEG,100,outputStream);
                Toast.makeText(v.getContext(), "Image Saved",Toast.LENGTH_LONG);
                try {
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }


              */
            }
        });

        holder.edit.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=combinations.get(position).getId();
                Intent intent=new Intent(v.getContext(),AddCombinationsActivity.class);
                intent.putExtra("mode",1);
                intent.putExtra("id",id);
                v.getContext().startActivity(intent);
            }
        });
        holder.delete.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                builder.setTitle("Delete Combination");
                builder.setMessage("Are you sure to delete this combination");
                builder.setNegativeButton("No",null);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        holder.sqLiteHelper.deleteCombination(combinations.get(position));
                        combinations.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(v.getContext(),"Combination Deleted",Toast.LENGTH_LONG).show();
                    }
                });
                builder.show();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return combinations.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView overhead,face,torso,legs,shoes,name;
        ImageView overheadimg,faceimg,torsoimg,legsimg,shoesimg;
        Button delete,edit,share;
        SQLiteHelper sqLiteHelper;
        public MyViewHolder(View itemView){
            super(itemView);
            overhead=itemView.findViewById(R.id.combination_row_overhead);
            face=itemView.findViewById(R.id.combination_row_face);
            torso=itemView.findViewById(R.id.combination_row_torso);
            legs=itemView.findViewById(R.id.combination_row_legs);
            shoes=itemView.findViewById(R.id.combination_row_shoes);
            name=itemView.findViewById(R.id.combination_row_comb_name);
            overheadimg=itemView.findViewById(R.id.combination_row_overhead_img);
            faceimg=itemView.findViewById(R.id.combination_row_face_img);
            torsoimg=itemView.findViewById(R.id.combination_row_torso_img);
            legsimg=itemView.findViewById(R.id.combination_row_legs_img);
            shoesimg=itemView.findViewById(R.id.combination_row_shoes_img);
            delete=itemView.findViewById(R.id.combination_row_delete);
            edit=itemView.findViewById(R.id.comb_row_edit);
            sqLiteHelper=new SQLiteHelper(itemView.getContext());
            share=itemView.findViewById(R.id.comb_row_share);
        }
    }
}
