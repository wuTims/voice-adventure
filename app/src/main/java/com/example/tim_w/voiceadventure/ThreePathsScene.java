package com.example.tim_w.voiceadventure;

import android.media.MediaPlayer;
import android.widget.TextView;

/**
 * Created by gswu on 4/26/2017.
 */

public class ThreePathsScene implements Scene {
    private String _desc;
    private TextView tView;
    private Inventory _inventory;
    private AdventureMap _map;

    public ThreePathsScene() {
        this._desc = "You find yourself in a dimly lit cave. There are three paths. To the south there is a power plant. To the west is a volcano that looks like its about to erupt. To the north is a icy mountain.";
    }

    @Override
    public void load(TextView v) {
        if(_inventory.checkItem("articuno") && _inventory.checkItem("pikachu") && _inventory.checkItem("charmander")){
            v.setText("A giant robot breaks through the roof of the cave. You hear");
            Position currPos = _map.getCurrPos();
            Scene nextScene = _map.getSceneAtPosition(currPos.getX() + 1, currPos.getY());
            _map.setCurrPos(currPos.getX() + 1, currPos.getY());
            nextScene.setInventory(this._inventory);

        } else {
            v.setText(this._desc);
            if(this.tView == null) this.tView = v;
        }

    }

    @Override
    public String performAction(String keyword, String command) {
        switch(keyword){
            case "USE":
            case "ABRA":
                if(command.contains("TELEPORT") && this._inventory.checkItem("abra")) {
                    return "TELE";
                }else{
                    return "Input unknown. Try something else";
                }
            case "LOOK":
                if(command.equals("")){
                    return this._desc;
                }
        }

        return "Input unknown. Try something else.";
    }

    @Override
    public Scene navigate(String direction, AdventureMap map) {
        Position currPos = map.getCurrPos();
        Scene nextScene;
        switch(direction){
            case "NORTH": //ice
                nextScene = map.getSceneAtPosition(currPos.getX(), currPos.getY() - 1);
                map.setCurrPos(currPos.getX(), currPos.getY() - 1);
                nextScene.setInventory(this._inventory);
                return nextScene;
            case "SOUTH": //electric
                nextScene = map.getSceneAtPosition(currPos.getX(), currPos.getY() + 1);
                map.setCurrPos(currPos.getX(), currPos.getY() + 1);
                nextScene.setInventory(this._inventory);
                return nextScene;
            case "WEST": //fire
                nextScene = map.getSceneAtPosition(currPos.getX() - 1, currPos.getY());
                map.setCurrPos(currPos.getX() - 1, currPos.getY());
                nextScene.setInventory(this._inventory);
                return nextScene;
            case "TELE":
                nextScene = map.getSceneAtPosition(2, 1);
                map.setCurrPos(2, 1);
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

    public void setMap(AdventureMap map) {
        this._map = map;
    }

    private AdventureMap getMap(){
        return this._map;
    }
}
