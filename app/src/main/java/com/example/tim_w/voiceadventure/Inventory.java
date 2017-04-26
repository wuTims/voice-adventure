package com.example.tim_w.voiceadventure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by tim_w on 3/18/2017.
 */

public class Inventory {
    private Set<Item> items;
    private Set<String> itemNames;

    public Inventory(){
        items = new HashSet<Item>();
        itemNames = new HashSet<String>();
    }

    public void addItem(Item item){
        items.add(item);
        itemNames.add(item.getName());
    }

    public void removeItem(Item item){
        items.remove(item);
        itemNames.remove(item.getName());
    }

    public boolean checkItem(String itemName){
        return itemNames.contains(itemName);
    }

}
