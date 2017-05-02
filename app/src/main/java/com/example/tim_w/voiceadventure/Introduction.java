package com.example.tim_w.voiceadventure;

import android.widget.TextView;

/**
 * Created by gswu on 4/30/2017.
 */

public class Introduction implements Scene{
    private String _desc;
    private TextView tView;

    public Introduction(){
        this._desc = "This is a voice adventure. " +
                "To give a command, shake the device. " +
                "To go in a direction, say GO direction, Like GO EAST. " +
                "To take an item in a scene, say GET or TAKE item, Like TAKE BOOK or GET BOOK. " +
                "To use an item, say USE item, Like USE KEY. " +
                "You may also READ or EXAMINE certain items. "+
                "If you want to repeat the scene description, say LOOK. " +
                "To start the game, say START";
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
        Position currPos = map.getCurrPos();
        switch(direction){
            case "START":
                Scene nextScene = map.getSceneAtPosition(currPos.getX(), currPos.getY() + 1);
                map.setCurrPos(currPos.getX(), currPos.getY() + 1);
                return nextScene;
        }

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
