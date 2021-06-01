package com.araskaplan.closett;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DrawerActivity extends AppCompatActivity {

    FloatingActionButton fab;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        fab=findViewById(R.id.drawer_act_fab);
        recyclerView=findViewById(R.id.drawer_act_recyclerview);

        SQLiteHelper database=new SQLiteHelper(getApplicationContext());
        ArrayList<Drawer>drawers=database.getDrawers();
        DrawerAdapter drawerAdapter=new DrawerAdapter(drawers);


        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(drawerAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                builder.setTitle("Add Drawer");
                builder.setMessage("Name of the new drawer:");
                final EditText input = new EditText(v.getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                builder.setView(input);
                builder.setNegativeButton("No",null);
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*Drawer temp=new Drawer(input.getText().toString());
                        drawers.add(temp);
                        database.addDrawer(temp);*/
                        drawers.add(database.addDrawer(input.getText().toString()));
                        Intent intent=new Intent(v.getContext(),DrawerActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
                builder.show();
            }
        });
    }
}