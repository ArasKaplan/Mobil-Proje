package com.araskaplan.closett;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.ImageDecoder;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddEventsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {
    CalendarView calendarView;
    EditText name,type;
    ImageView overhead,face,torso,legs,shoes;
    Button done;
    Spinner spinner;
    int spinnerselects;

    private GoogleMap mMap;

    List<Address> addresses;
    String date;
    Combination selectedcomb;
    SQLiteHelper sqLiteHelper;
    int mode;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_events);

        Intent intent1=getIntent();
        mode=intent1.getIntExtra("mode",0);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.add_events_map);
        mapFragment.getMapAsync(this);
        overhead=findViewById(R.id.add_events_overhead);
        face=findViewById(R.id.add_events_face);
        torso=findViewById(R.id.add_events_torso);
        legs=findViewById(R.id.add_events_legs);
        shoes=findViewById(R.id.add_events_shoes);
        spinner=findViewById(R.id.add_events_spinner);
        done=findViewById(R.id.add_events_done);
        calendarView=findViewById(R.id.add_events_calendar);
        name=findViewById(R.id.add_events_name);
        type=findViewById(R.id.add_events_type);
        sqLiteHelper=new SQLiteHelper(getApplicationContext());

        if(mode==1){
            id=intent1.getIntExtra("id",-1);
            spinnerselects=0;
            fillComponents();
        }else {
            spinnerselects=1;
        }


        ArrayList<String> combnames=new ArrayList<>();
        for (Combination combination:sqLiteHelper.getCombinations()){
            combnames.add(combination.getName());
        }

        ArrayAdapter<String> adapter=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item,combnames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date=""+dayOfMonth+"."+month+"."+year;
            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerselects++;
                if (spinnerselects>1){
                    overhead.setImageBitmap(sqLiteHelper.getClothing(sqLiteHelper.getCombinations().get(position).getOverhead().getId()).getImg());
                    face.setImageBitmap(sqLiteHelper.getClothing(sqLiteHelper.getCombinations().get(position).getFace().getId()).getImg());
                    torso.setImageBitmap(sqLiteHelper.getClothing(sqLiteHelper.getCombinations().get(position).getTorso().getId()).getImg());
                    legs.setImageBitmap(sqLiteHelper.getClothing(sqLiteHelper.getCombinations().get(position).getLegs().getId()).getImg());
                    shoes.setImageBitmap(sqLiteHelper.getClothing(sqLiteHelper.getCombinations().get(position).getFeet().getId()).getImg());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addresses==null){
                    Toast.makeText(v.getContext(),"Select a location before adding an event",Toast.LENGTH_LONG).show();
                }else {
                    if (mode==1){
                        Event temp=new Event(name.getText().toString(),type.getText().toString(),date,addresses.get(0).getAddressLine(0),sqLiteHelper.getCombinations().get(spinner.getSelectedItemPosition()));
                        temp.setCombid(sqLiteHelper.getCombinations().get(spinner.getSelectedItemPosition()).getId());
                        temp.setEventid(id);
                        sqLiteHelper.updateEvent(temp);
                        Toast.makeText(v.getContext(),"Event Updated",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(v.getContext(),EventActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }else {
                        Event temp=new Event(name.getText().toString(),type.getText().toString(),date,addresses.get(0).getAddressLine(0),sqLiteHelper.getCombinations().get(spinner.getSelectedItemPosition()));
                        temp.setCombid(sqLiteHelper.getCombinations().get(spinner.getSelectedItemPosition()).getId());
                        sqLiteHelper.addEvent(temp);
                        Toast.makeText(v.getContext(),"Event Added",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(v.getContext(),EventActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }


            }
        });
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

        mMap.clear();
        String address="";
        Geocoder geocoder=new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            addresses=geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if(addresses!=null && addresses.size()>0){
                if (addresses.get(0).getThoroughfare()!=null){
                    address+=addresses.get(0).getThoroughfare();
                    System.out.println(addresses.get(0).getAddressLine(0));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        mMap.addMarker(new MarkerOptions().position(latLng).title(address));

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap= googleMap;
        mMap.setOnMapLongClickListener(this);

        LocationManager locationManager= (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener= new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {


            }
        };

        // 41.02648847105992, 28.8887230726066 ytu
        LatLng ytu = new LatLng(41.02648847105992, 28.8887230726066);
        mMap.addMarker(new MarkerOptions().position(ytu).title("Marker in Yildiz Teknik Universitesi"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ytu,15));
    }
    public void fillComponents(){
        if (id!=-1){
            Event temp=sqLiteHelper.getEvent(id);
            overhead.setImageBitmap(temp.getCombination().getOverhead().getImg());
            face.setImageBitmap(temp.getCombination().getFace().getImg());
            torso.setImageBitmap(temp.getCombination().getTorso().getImg());
            legs.setImageBitmap(temp.getCombination().getLegs().getImg());
            shoes.setImageBitmap(temp.getCombination().getFeet().getImg());
            name.setText(temp.getName());
            type.setText(temp.getType());

            int i=0;
            for (Combination combination:sqLiteHelper.getCombinations()){
                if (combination.getId()==temp.getCombid()){
                   spinner.setSelection(i);
                   break;
                }
                i++;
            }

        }
    }
}