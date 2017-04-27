package com.example.tim_w.voiceadventure;

import android.widget.TextView;

/**
 * Created by gswu on 4/26/2017.
 */

public class ThreePathsScene implements Scene {
    private String _desc;
    private TextView tView;
    private Inventory _inventory;

    public ThreePathsScene(Inventory inventory) {
        this._inventory = inventory;
        this._desc = "You find yourself in a dimly lit cave. There are three paths. To the west there is a power plant. To the north is a volcano that looks like its about to erupt. To the east is a icy mountain.";
    }

    @Override
    public void load(TextView v) {
        v.setText(this._desc);
        if(this.tView == null) this.tView = v;
    }

    @Override
    public String performAction(String keyword, String command) {
        return "Input unknown. Try something else.";
    }

    @Override
    public Scene navigate(String direction, AdventureMap map) {
        Position currPos = map.getCurrPos();
        Scene nextScene;
        switch(direction){
            case "NORTH": //ice
                nextScene = map.getSceneAtPosition(currPos.getX(), currPos.getY() + 1);
                map.setCurrPos(currPos.getX(), currPos.getY() + 1);
                nextScene.setInventory(this._inventory);
                return nextScene;
            case "SOUTH": //electric
                nextScene = map.getSceneAtPosition(currPos.getX(), currPos.getY() - 1);
                map.setCurrPos(currPos.getX(), currPos.getY() - 1);
                nextScene.setInventory(this._inventory);
                return nextScene;
            case "WEST": //fire
                nextScene = map.getSceneAtPosition(currPos.getX() -1, currPos.getY());
                map.setCurrPos(currPos.getX() - 1, currPos.getY());
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
        return null;
    }

    @Override
    public void setInventory(Inventory inventory) {
        this._inventory = inventory;
    }
}
