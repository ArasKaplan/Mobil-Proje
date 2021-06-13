package com.araskaplan.closett;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.MyViewHolder> {


    ArrayList<Drawer> drawers;

    public DrawerAdapter(ArrayList<Drawer> drawers) {
        this.drawers = drawers;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.drawername.setText(drawers.get(position).getName());
        holder.deleteDrawer.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                builder.setTitle("Delete Drawer?");
                builder.setMessage("Are you sure to delete this drawer?");
                builder.setNegativeButton("No",null);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        holder.sqLiteHelper.deleteDrawer(drawers.get(position));
                        drawers.remove(position);
                        Toast.makeText(v.getContext(),"Drawer Deleted",Toast.LENGTH_LONG).show();
                        notifyDataSetChanged();
                    }
                });
                builder.show();
            }
        });
        holder.drawername.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),ClothesActivity.class);
                intent.putExtra("drawerpos",position);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return drawers.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView drawername;
        Button deleteDrawer;
        SQLiteHelper sqLiteHelper;

        public MyViewHolder(View itemView){
            super(itemView);
            sqLiteHelper=new SQLiteHelper(itemView.getContext());
            deleteDrawer=itemView.findViewById(R.id.drawer_row_delete);
            drawername=itemView.findViewById(R.id.drawer_row_textView);
        }
    }

}
