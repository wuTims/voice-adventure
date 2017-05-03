package com.example.tim_w.voiceadventure;

import android.widget.TextView;

import java.util.HashSet;

/**
 * Created by tim_w on 4/12/2017.
 */

public class IntroScene implements Scene {

    private String _desc;
    private Inventory _inventory;
    private TextView tView;

    public IntroScene(Inventory inventory){
        this._inventory = inventory;
        this._desc = "Team Rocket stole four of your Pokemon. You see them run down a path to the east.";
    }

    @Override
    public void load(TextView v) {
        v.setText(this._desc);
        if(this.tView == null) this.tView = v;
    }

    @Override
    public String performAction(String keyword, String command) {
        switch(keyword){
            case "LOOK":
                if(command.equals("")){
                    return this._desc;
                }
                break;

            case "HELP":
                return "Try to navigate EAST.";
        }


        return "Input unknown. Try something else.";
    }

    @Override
    public Scene navigate(String direction, AdventureMap map) {
        Position currPos = map.getCurrPos();
        switch(direction){
            case "FORWARD":
            case "FRONT":
            case "EAST":
                Scene nextScene = map.getSceneAtPosition(currPos.getX() + 1, currPos.getY());
                map.setCurrPos(currPos.getX() + 1, currPos.getY());
                nextScene.setInventory(this._inventory);
                return nextScene;
            default:
                this.tView.setText("Sorry, you can't go that way.");
                return null;
        }
    }

    @Override
    public Scene navigate(String keyword, String object, AdventureMap map) {
        return null;
    }

    @Override
    public Inventory getInventory() {
        return this._inventory;
    }

    @Override
    public void setInventory(Inventory inventory) {
        this._inventory = inventory;
    }
}
