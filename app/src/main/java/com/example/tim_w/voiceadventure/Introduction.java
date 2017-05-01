package com.example.tim_w.voiceadventure;

import android.widget.TextView;

/**
 * Created by gswu on 4/30/2017.
 */

public class Introduction implements Scene{
    private String _desc;
    private TextView tView;

    public Introduction(){
        this._desc = "This is a voice adventure. \n" +
                "To give a command, shake the device. \n" +
                "To go in a direction, say GO direction. Like GO EAST. \n" +
                "To take an item in a scene, say TAKE item. Like TAKE BOOK. \n" +
                "To use an item, say USE item. Like USE KEY.\n" +
                "\n" +
                "At anytime if you want to repeat the scene description, say LOOK. ";
    }

    @Override
    public void load(TextView v) {
        v.setText(this._desc);
        if(this.tView == null) this.tView = v;
    }

    @Override
    public String performAction(String keyword, String command) {
        return null;
    }

    @Override
    public Scene navigate(String direction, AdventureMap map) {
        return null;
    }

    @Override
    public Scene navigate(String keyword, String object, AdventureMap map) {
        return null;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }

    @Override
    public void setInventory(Inventory inventory) {

    }
}
