package com.araskaplan.closett;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class ClothesAdapter extends RecyclerView.Adapter<ClothesAdapter.MyViewHolder> {

    ArrayList<Clothing> clothes;
    int drawerpos;

    public ClothesAdapter(ArrayList<Clothing> clothes,int drawerpos) {
        this.clothes = clothes;
        this.drawerpos=drawerpos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.clothes_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(clothes.get(position).getName());
        holder.color.setText(clothes.get(position).getColor());
        holder.date.setText(clothes.get(position).getDate_of_purchase());
        holder.pattern.setText(clothes.get(position).getPattern());
        holder.price.setText(clothes.get(position).getPrice());
        holder.type.setText(clothes.get(position).getType());
        holder.imageView.setImageBitmap(clothes.get(position).getImg());


        holder.delete.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                builder.setTitle("Delete Clothing?");
                builder.setMessage("Are you sure to delete this clothing?");
                builder.setNegativeButton("No",null);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        holder.sqLiteHelper.deleteClothing(clothes.get(position));
                        clothes.remove(position);
                        notifyDataSetChanged();
                    }
                });
                builder.show();
            }
        });
        holder.edit.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),AddClothesActivity.class);
                intent.putExtra("drawerpos",drawerpos);
                intent.putExtra("clothespos",position);
                intent.putExtra("edit",1);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return clothes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name,color,price,pattern,type,date;
        Button delete,edit;
        Intent intent2;
        SQLiteHelper sqLiteHelper;

        public MyViewHolder(View itemView){
            super(itemView);
            imageView=itemView.findViewById(R.id.clothes_row_img);
            color=itemView.findViewById(R.id.clothe_row_color);
            price=itemView.findViewById(R.id.clothe_row_price);
            pattern=itemView.findViewById(R.id.clothe_row_pattern);
            type=itemView.findViewById(R.id.clothe_row_type);
            date=itemView.findViewById(R.id.clothe_row_date);
            name=itemView.findViewById(R.id.clothe_row_name);
            edit=itemView.findViewById(R.id.clothes_row_edit);
            delete=itemView.findViewById(R.id.clothes_row_delete);
            sqLiteHelper=new SQLiteHelper(itemView.getContext());
        }
    }
}
