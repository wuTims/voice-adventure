package com.example.tim_w.voiceadventure;

import android.widget.TextView;

import java.util.HashSet;

/**
 * Created by gswu on 4/26/2017.
 */

public class PikachuScene implements Scene {
    private String _desc;
    private TextView tView;
    private Inventory _inventory;
    private Item Pikachu;
    private boolean readTag = false;
    private boolean lockDoor = true;
    private boolean auth = false;
    private boolean openBox = false;
    private HashSet<Item> sceneItems;


    public PikachuScene() {
        this.sceneItems = new HashSet<Item>();

        Pikachu = new Item("pikachu","Pikachu, the electric mouse pokemon." +
                " It can use thunderbolt by generating electricity from the two " +
                "electric pouches on its cheeks.");
        this.sceneItems.add(Pikachu);
        this._desc = "As you enter the power plant the door behind you closes and locks you in. " +
                "Your Pikachu is trapped inside a glass box. There is " +
                "a tag on its ears. A control panel is next to the box.";
    }

    @Override
    public void load(TextView v) {
        if(!lockDoor){
            this._desc = "You enter the power plant. There's an open glass box and a control panel next to it.";
        }
        v.setText(this._desc);
        if (this.tView == null) this.tView = v;
    }

    @Override
    public String performAction(String keyword, String command) {
        switch (keyword) {
            case "USED":
            case "USE":
                if (command.contains("CONTROL PANEL")) {
                    if(auth){
                        openBox = true;
                        return "You press a switch on the panel and the glass box opens.";
                    }else{
                        return "Access Unauthorized. Insert Password. Password hint is " +
                                "2, 3, 5, 9, 17. What is the next number in the sequence?";
                    }
                }
                if (command.contains("POKEBALL")) {
                    if(sceneItems.contains(Pikachu)){
                        if (command.contains("POKEBALL")) {
                            if(openBox){
                                if (readTag) {
                                    addItem(Pikachu);
                                    return "Pikachu returns to its pokeball. Pikachu has the ability to use THUNDERBOLT";
                                } else {
                                    return "The tag looks pretty important. You might want to read it first.";
                                }
                            }else{
                                return "You can't reach Pikachu. The glass box is unbreakable.";
                            }
                        }
                    }else{
                        return "You already have Pikachu.";
                    }
                }
                if (command.contains("THUNDERBOLT")) {
                    if(_inventory.checkItem("pikachu")){
                        lockDoor = false;
                        return "Pikachu used thunderbolt at the door. The door opens to the east.";
                    } else {
                        return "None of your Pokemon know Thunderbolt";
                    }
                }
            case "THROW":
                    if (command.contains("POKEBALL")) {
                        if(sceneItems.contains(Pikachu)) {
                            if (openBox) {
                                if (readTag) {
                                    addItem(Pikachu);
                                    return "Pikachu returns to its pokeball. Pikachu has the ability to use THUNDERBOLT";
                                } else {
                                    return "The tag looks pretty important. You might want to read it first.";
                                }
                            } else {
                                return "You can't reach Pikachu. The glass box is unbreakable.";
                            }
                        }else{
                                return "You already have Pikachu.";
                        }
                    }else{
                        return "Input unknown. Try something else.";
                    }

            case "THIRTY THREE":
            case "33":
                if(lockDoor){
                    auth = true;
                    return "Access Authorized. You can now USE the control panel.";
                }else{
                    return "You already have access to the control panel.";
                }
            case "EXAMINE":
            case "READ":
                if(command.contains("CONTROL PANEL")){
                    return "A panel used to control the glass box.";
                }
                if (command.contains("TAG")) {
                    readTag = true;
                    return "The number 3 is written on the tag.";
                }
            case "LOOK":
                if (command.contains("TAG")) {
                    readTag = true;
                    return "The number 3 is written on the tag.";
                }
                if(command.equals("")){
                    return this._desc;
                }
            case "TAKE":
            case "CAPTURE":
                if (command.contains("PIKACHU")) {
                    if(sceneItems.contains(Pikachu)){
                        if(openBox){
                            if(this._inventory.checkItem("pokeballs")){
                                if (readTag) {
                                    addItem(Pikachu);
                                    return "Pikachu returns to its pokeball. Pikachu has the ability to use THUNDERBOLT";
                                } else {
                                    return "The tag looks pretty important. You might want to read it first.";
                                }
                            }else{
                                return "You don't have any pokeballs.";
                            }

                        }else{
                            return "You can't reach Pikachu. The glass box is unbreakable.";
                        }
                    }else{
                        return "You already have Pikachu.";
                    }
                }else{
                    return "Input unknown. Try something else.";
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
            case "NORTH":
                if (lockDoor) {
                    this.tView.setText("Door won’t budge. It seems to be powered by electricity.");
                } else {
                    if(_inventory.checkItem("pikachu") && _inventory.checkItem("charmander") && _inventory.checkItem("articuno")){
                        //4,2 --> 5,1
                        nextScene = map.getSceneAtPosition(currPos.getX() + 1, currPos.getY() - 1);
                        map.setCurrPos(currPos.getX() + 1, currPos.getY() - 1);
                        nextScene.setInventory(this._inventory);
                        return nextScene;
                    } else {
                        nextScene = map.getSceneAtPosition(currPos.getX(), currPos.getY() - 1);
                        map.setCurrPos(currPos.getX(), currPos.getY() - 1);
                        nextScene.setInventory(this._inventory);
                        return nextScene;
                    }
                }
        }
        return null;
    }

    private void addItem(Item item) {
        this.sceneItems.remove(item);
        this._inventory.addItem(item);
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
