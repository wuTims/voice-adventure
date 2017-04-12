package com.example.tim_w.voiceadventure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by tim_w on 3/18/2017.
 */

public class Inventory {
    private Set<Item> items;

    public Inventory(){
        items = new HashSet<Item>();
    }

    public void addItem(Item item){
        items.add(item);
    }

    public void removeItem(String item){
        items.remove(item);
    }

    public boolean checkItem(String item){
        return items.contains(item);
    }

}
