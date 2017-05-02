package com.example.tim_w.voiceadventure;

import android.widget.TextView;

import java.util.HashSet;

/**
 * Created by tim_w on 4/17/2017.
 */


public class FrontHouseScene implements Scene {
    private Inventory _inventory;
    private String _desc;
    private HashSet<Item> sceneItems;
    private boolean mailboxOpen = false;
    private Item lantern, key, pokedex;
    private TextView tView;

    public FrontHouseScene(Inventory inventory) {
        this._inventory = inventory;
        this._desc = "You stand before a dark, broken down house. The front door is locked. There is a mailbox and a lantern on the ground.";

        this.lantern = new Item("lantern", "A battery powered lantern.");
        this.key = new Item("key", "A rusty old key");
        this.pokedex = new Item("pokedex", "A computer that contains information about pokemon.");

        sceneItems = new HashSet<Item>();

        sceneItems.add(lantern);
        sceneItems.add(key);
        sceneItems.add(pokedex);


    }

    @Override
    public void load(TextView v) {
        v.setText(this._desc);
        if (this.tView == null) this.tView = v;
    }

    @Override
    public String performAction(String keyword, String command) {
        switch (keyword) {
            case "OPEN":
            case "CHECK":
            case "LOOK":
                if(command.equals("")){
                    return this._desc;
                }
                if(command.contains("MAILBOX")){
                    this.mailboxOpen = true;
                    if(this.sceneItems.contains(key) && this.sceneItems.contains(pokedex)) {
                        return "There's a key and a pokedex in the mailbox.";
                    }else if(this.sceneItems.contains(key) && !this.sceneItems.contains(pokedex)) {
                        return "There's a key in the mailbox.";
                    }else if(this.sceneItems.contains(pokedex)) {
                        return "There's a pokedex in the mailbox.";
                    }else{

                        return "It's a mailbox with nothing inside";
                    }
                } else {
                    return "Input unknown. Try something else.";
                }
            case "TAKE":
                if(command.contains("KEY")){
                    if(this.mailboxOpen && this.sceneItems.contains(key)){
                        addItem(key);
                        return "You put the key in your bag.";
                    }
                }else if(command.contains("LANTERN") && this.sceneItems.contains(lantern)) {
                    addItem(lantern);
                    this._desc = "You stand before a dark, broken down house. The front door is locked. There is a mailbox.";
                    return "You take the lantern.";
                }else if(command.contains("POKEDEX") && this.sceneItems.contains(pokedex)){
                    addItem(pokedex);
                    return "You put the pokedex in your bag.";
                } else{
                    return "Input unknown. Try something else.";
                }
            case "TAKEE":
            case "TIKKI":
            case "TIKI":
                if(this.mailboxOpen && this.sceneItems.contains(key)){
                    addItem(key);
                    return "You put the key in your bag.";
                } else {
                    return "Input unknown. Try something else.";
                }
            default:
                return "Input unknown. Try something else.";
        }
    }

    @Override
    public Scene navigate(String direction, AdventureMap map) {
        return null;
    }

    @Override
    public Scene navigate(String keyword, String object, AdventureMap map) {
        Position currPos = map.getCurrPos();
        switch (keyword) {
            case "OPEN":
            case "ENTER":
            case "UNLOCK":
                if(object.contains("DOOR") || object.contains("HOUSE")){
                    if(this._inventory.checkItem("key")){
                        Scene nextScene = map.getSceneAtPosition(currPos.getX() + 1, currPos.getY());
                        map.setCurrPos(currPos.getX() + 1, currPos.getY());
                        nextScene.setInventory(this._inventory);
                        if(this._inventory.checkItem("lantern")){
                            this._desc = "You stand before a dark, broken down house. The front door is open. There is a mailbox.";
                        }else{
                            this._desc = "You stand before a dark, broken down house. The front door is open. There is a mailbox and a lantern on the ground.";
                        }
                        return nextScene;
                    } else {
                        this.tView.setText("The door is locked.");
                    }
                }else if(object.contains("MAILBOX")){
                    String result = this.performAction("OPEN", "MAILBOX");
                    this.tView.setText(result);
                }
        }

        return null;
    }

    private void addItem(Item item) {
        this.sceneItems.remove(item);
        this._inventory.addItem(item);
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
