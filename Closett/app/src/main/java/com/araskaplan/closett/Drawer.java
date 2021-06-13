package com.araskaplan.closett;

import java.util.ArrayList;

public class Drawer {
    int id;
    String name;
    ArrayList<Clothing> clothes;

    public Drawer( String name) {
        this.name = name;
        clothes=new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Clothing> getClothes() {
        return clothes;
    }

    public void setClothes(ArrayList<Clothing> clothes) {
        this.clothes = clothes;
    }
}
