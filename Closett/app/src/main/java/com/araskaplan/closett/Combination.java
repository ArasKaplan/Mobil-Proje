package com.araskaplan.closett;

public class Combination {
    String name;

    Clothing overhead;
    Clothing face;
    Clothing torso;
    Clothing legs;
    Clothing feet;

    int id;

    public Combination(String name) {
        overhead=null;
        face=null;
        torso=null;
        legs=null;
        feet=null;
        this.name=name;
        id=0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Clothing getOverhead() {
        return overhead;
    }

    public void setOverhead(Clothing overhead) {
        this.overhead = overhead;
    }

    public Clothing getFace() {
        return face;
    }

    public void setFace(Clothing face) {
        this.face = face;
    }

    public Clothing getTorso() {
        return torso;
    }

    public void setTorso(Clothing torso) {
        this.torso = torso;
    }

    public Clothing getLegs() {
        return legs;
    }

    public void setLegs(Clothing legs) {
        this.legs = legs;
    }

    public Clothing getFeet() {
        return feet;
    }

    public void setFeet(Clothing feet) {
        this.feet = feet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
