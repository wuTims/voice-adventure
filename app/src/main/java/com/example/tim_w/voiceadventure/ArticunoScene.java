package com.example.tim_w.voiceadventure;

import android.widget.TextView;

/**
 * Created by gswu on 4/26/2017.
 */

public class ArticunoScene implements Scene {
    private Inventory _inventory;
    private String _desc;
    private TextView tView;
    private boolean readTag = false;
    Item Articuno;

    public ArticunoScene() {
        Articuno = new Item("Articuno", "Articuno, the legendary flying ice pokemon. " +
                "The flapping of its wings creates a blizzard that chills the air freezing everything");
        this._desc = "You climbed to the top of the icy mountain to see a door with scribbles on the door you can not decipher.";
    }

    @Override
    public void load(TextView v) {
        v.setText(this._desc);
        if (this.tView == null) this.tView = v;
    }

    @Override
    public String performAction(String keyword, String command) {
        switch (keyword) {
            case "USE":
                if (command.contains("BOOK")) {
                    return "Die without me. Never thank me. Walk right through me. Never feel me." +
                            "Always watching. Never speaking. Always lurking. Never seen. The answer is.";
                }
                if (command.contains("POKEBALL")) {
                    if(readTag && !_inventory.checkItem("articuno")){
                        _inventory.addItem(Articuno);
                        if (readTag) {
                            return "Articuno returns to its pokeball. Articuno, the legendary flying ice pokemon. " +
                                    "The flapping of its wings creates a blizzard that chills the air freezing everything";
                        } else {
                            return "The tag looks pretty important. You might want to read it first.";
                        }
                    } else if(!readTag){
                        return "The tag looks pretty important. You might want to read it first.";
                    } else {
                        return "You already have Articuno";
                    }
                }
            case "READ":
                if (command.contains("BOOK")) {
                    return "Die without me. Never thank me. Walk right through me. Never feel me." +
                            "Always watching. Never speaking. Always lurking. Never seen. The answer is.";
                }
                if (command.contains("TAG")) {
                    readTag = true;
                    return "The number 1 is written on the tag.";
                } else {
                    return "Input unknown. Try something else.";
                }
            case "AIR":
                if (command.equals("")) {
                    return "The door opens and you see Articuno sleeping. There is a tag on its wings.";
                }
            case "EXAMINE":
            case "LOOK":
                if (command.contains("TAG")) {
                    readTag = true;
                    return "The number 1 is written on the tag.";
                }
                if(command.equals("")){
                    return this._desc;
                }
            case "TAKE":
            case "CAPTURE":
                if(readTag && !_inventory.checkItem("articuno")){
                    _inventory.addItem(Articuno);
                    if (readTag) {
                        return "Articuno returns to its pokeball. Articuno, the legendary flying ice pokemon. " +
                                "The flapping of its wings creates a blizzard that chills the air freezing everything";
                    } else {
                        return "The tag looks pretty important. You might want to read it first.";
                    }
                } else if(!readTag){
                    return "The tag looks pretty important. You might want to read it first.";
                } else {
                    return "You already have Articuno";
                }
            default:
                return "Input unknown. Try something else.";
        }
    }

    @Override
    public Scene navigate(String direction, AdventureMap map) {
        Position currPos = map.getCurrPos();
        Scene nextScene;
        switch (direction) {
            case "BACK":
            case "SOUTH":
                if(_inventory.checkItem("pikachu") && _inventory.checkItem("charmander") && _inventory.checkItem("articuno")) {
                    nextScene = map.getSceneAtPosition(currPos.getX() + 1, currPos.getY() - 1);
                    map.setCurrPos(currPos.getX() + 1, currPos.getY() - 1);
                    nextScene.setInventory(this._inventory);
                    return nextScene;
                } else {
                    nextScene = map.getSceneAtPosition(currPos.getX(), currPos.getY() + 1);
                    map.setCurrPos(currPos.getX(), currPos.getY() + 1);
                    nextScene.setInventory(this._inventory);
                    return nextScene;
                }
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
        this._inventory = inventory;
    }
}
