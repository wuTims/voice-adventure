package com.example.tim_w.voiceadventure;

import android.widget.TextView;

/**
 * Created by gswu on 4/30/2017.
 */

public class Introduction implements Scene{
    private String _desc;
    private TextView tView;

    public Introduction(){
        this._desc = "This is a voice adventure game. These are the main commands. There may be others that you can try.\n\n" +
                "GO: for NAVIGATION, like GO EAST.\n" +
                "TAKE or GET: for ITEMS, like TAKE BOOK or GET KEY.\n" +
                "USE: for ITEMS or MOVES, like USE LANTERN.\n"+
                "EXAMINE, LOOK, READ, or CHECK: for ITEMS, like EXAMINE TABLE or CHECK MAILBOX.\n" +
                "LOOK: for SCENES, only say LOOK to repeat the scene.\n" +
                "HELP: for HINTS, only use HELP if you are stuck.\n\n"+
                "Shake the device and say START to begin.";
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
