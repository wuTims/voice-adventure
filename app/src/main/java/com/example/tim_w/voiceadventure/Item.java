package com.example.tim_w.voiceadventure;

/**
 * Created by tim_w on 3/23/2017.
 */

public class Item {
    private String name;
    private String desc;

    public Item(String name, String description){
        this.name = name;
        this.desc = description;
    }

    public String getName(){
        return this.name;
    }

    public String getDesc(){
        return this.desc;
    }

    public String toString(){
        return this.desc;
    }
}
