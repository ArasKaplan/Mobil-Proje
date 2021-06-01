package com.araskaplan.closett;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CombClothesAdapter extends RecyclerView.Adapter<CombClothesAdapter.MyViewHolder> {

    ArrayList<Clothing> clothes;
    Activity mactivity;

    public CombClothesAdapter(ArrayList<Clothing> clothes,Activity mactivity) {
        this.clothes = clothes;
        this.mactivity=mactivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.comb_clothes_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.imageView.setImageBitmap(clothes.get(position).getImg());
        holder.price.setText(clothes.get(position).getPrice());
        holder.date.setText(clothes.get(position).getDate_of_purchase());
        holder.pattern.setText(clothes.get(position).getPattern());
        holder.type.setText(clothes.get(position).getType());
        holder.color.setText(clothes.get(position).getColor());
        holder.name.setText(clothes.get(position).getName());
        holder.select.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result=position;
                Intent resultintent=new Intent();
                resultintent.putExtra("result",result);
                mactivity.setResult(Activity.RESULT_OK,resultintent);
                mactivity.finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return clothes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name,color,type,pattern,date,price;
        Button select;

        public MyViewHolder(View itemView){
            super(itemView);
            select=itemView.findViewById(R.id.comb_clothes_row_choose);
            name=itemView.findViewById(R.id.comb_clothes_row_name);
            color=itemView.findViewById(R.id.comb_clothes_row_color);
            type=itemView.findViewById(R.id.comb_clothes_row_type);
            pattern=itemView.findViewById(R.id.comb_clothes_row_pattern);
            date=itemView.findViewById(R.id.comb_clothes_row_date);
            price=itemView.findViewById(R.id.comb_clothes_row_price);
            imageView=itemView.findViewById(R.id.comb_clothes_row_img);

        }
    }
}
