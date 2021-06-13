package com.araskaplan.closett;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class EventActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton fab;


    SQLiteHelper sqLiteHelper;
    ArrayList<Event> events;
    int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        sqLiteHelper=new SQLiteHelper(getApplicationContext());
        events=sqLiteHelper.getEvents();

        recyclerView=findViewById(R.id.activity_event_recyclerview);
        fab=findViewById(R.id.activity_event_fab);

        EventAdapter eventAdapter=new EventAdapter(events);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(eventAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),AddEventsActivity.class);
                startActivity(intent);
            }
        });
    }
}