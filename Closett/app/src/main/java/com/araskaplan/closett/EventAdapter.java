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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {
    ArrayList<Event> events;

    public EventAdapter(ArrayList<Event> events) {
        this.events = events;
    }

    @NonNull
    @Override
    public EventAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.event_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  MyViewHolder holder, int position) {
        holder.name.setText(events.get(position).getName());
        holder.date.setText(events.get(position).getDate());
        holder.type.setText(events.get(position).getType());
        holder.location.setText(events.get(position).getLocation());
        holder.overhead.setImageBitmap(events.get(position).getCombination().getOverhead().getImg());
        holder.face.setImageBitmap(events.get(position).getCombination().getFace().getImg());
        holder.torso.setImageBitmap(events.get(position).getCombination().getTorso().getImg());
        holder.legs.setImageBitmap(events.get(position).getCombination().getLegs().getImg());
        holder.shoes.setImageBitmap(events.get(position).getCombination().getFeet().getImg());
        holder.delete.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                builder.setTitle("Delete Event");
                builder.setMessage("Are you sure you want to delete this event");
                builder.setNegativeButton("No",null);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        holder.sqLiteHelper.deleteEvent(events.get(position).getEventid());
                        events.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(v.getContext(),"Event Deleted",Toast.LENGTH_LONG).show();
                    }
                });
                builder.show();
            }
        });
        holder.edit.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                builder.setTitle("Edit Event");
                builder.setMessage("Proceeding to edit the event may make you lose location and date values on said event. Are you sure?");
                builder.setNegativeButton("No",null);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(v.getContext(),AddEventsActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("mode",1);
                        intent.putExtra("id",events.get(position).getEventid());
                        v.getContext().startActivity(intent);
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Button edit,delete;
        TextView name,date,type,location;
        SQLiteHelper sqLiteHelper;
        ImageView overhead,face,torso,legs,shoes;

        public MyViewHolder(View itemView){
            super(itemView);
            edit=itemView.findViewById(R.id.event_row_edit);
            delete=itemView.findViewById(R.id.event_row_delete);
            name=itemView.findViewById(R.id.event_row_name);
            date=itemView.findViewById(R.id.event_row_date);
            type=itemView.findViewById(R.id.event_row_type);
            location=itemView.findViewById(R.id.event_row_location);
            overhead=itemView.findViewById(R.id.event_row_overhead);
            face=itemView.findViewById(R.id.event_row_face);
            torso=itemView.findViewById(R.id.event_row_torso);
            legs=itemView.findViewById(R.id.event_row_legs);
            shoes=itemView.findViewById(R.id.event_row_shoes);
            sqLiteHelper=new SQLiteHelper(itemView.getContext());
        }
    }
}
