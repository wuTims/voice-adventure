package com.example.tim_w.voiceadventure;

import android.widget.TextView;

/**
 * Created by gswu on 4/26/2017.
 */

public class PikachuScene implements Scene {
    private String _desc;
    private TextView tView;
    private Inventory _inventory;
    private Item Pikachu;
    private boolean readTag = false;
    private boolean lockDoor = false;


    public PikachuScene() {
        Pikachu = new Item("Pikachu","Pikachu, the electric mouse pokemon." +
                " It can use thunderbolt by generating electricity from the two " +
                "electric pouches on its cheeks.");
        this._desc = "As you enter the power plant the door behind you closes trapping you in " +
                "the room. Your Pikachu is trapped inside a glass box in front of you. There is " +
                "a tag on its ears. There is a control panel in front of the box.";
    }

    @Override
    public void load(TextView v) {
        v.setText(this._desc);
        if (this.tView == null) this.tView = v;
    }

    @Override
    public String performAction(String keyword, String command) {
        switch (keyword) {
            case "USED":
            case "USE":
                if (command.contains("CONTROL PANEL")) {
                    return "Access Unauthorized. Insert Password. Password hint is " +
                            "2, 3, 5, 9, 17, _ What is the next number in the sequence?";
                }
                if (command.contains("POKEBALL")) {
                    if (readTag) {
                        _inventory.addItem(Pikachu);
                        return "Pikachu returns to its pokeball. Pikachu, the electric mouse pokemon." +
                                " It can use thunderbolt by generating electricity from the two " +
                                "electric pouches on its cheeks.";
                    } else {
                        return "The tag looks pretty important. You might want to read it first.";
                    }
                }
                if (command.contains("THUNDERBOLT")) {
                    if(_inventory.checkItem("Pikachu")){
                        lockDoor = false;
                        return "Pikachu use thunderbolt at the door. The door opens.";
                    } else {
                        return "None of your Pokemons knows Thunderbolt";
                    }
                }
            case "THROW":
                if (command.contains("POKEBALL")) {
                    if (readTag) {
                        _inventory.addItem(Pikachu);
                        return "Pikachu returns to its pokeball. Pikachu, the electric mouse pokemon." +
                                " It can use thunderbolt by generating electricity from the two " +
                                "electric pouches on its cheeks.";
                    } else {
                        return "The tag looks pretty important. You might want to read it first.";
                    }
                }
            case "THIRTY THREE":
            case "33":
                return "Access Authorized. You use the control panel to open the glass box.";
            case "EXAMINE":
            case "LOOK":
            case "READ":
                if (command.contains("TAG")) {
                    readTag = true;
                    return "The number 3 is written on the tag.";
                } else {
                    return "Input unknown. Try something else.";
                }
            case "TAKE":
            case "CAPTURE":
                if (command.contains("PIKACHU")) {
                    if (readTag) {
                        _inventory.addItem(Pikachu);
                        return "Pikachu returns to its pokeball. Pikachu, the electric mouse pokemon." +
                                " It can use thunderbolt by generating electricity from the two " +
                                "electric pouches on its cheeks.";
                    } else {
                        return "The tag looks pretty important. You might want to read it first.";
                    }
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
            case "WEST":
                if (lockDoor) {
                    this.tView.setText("Door won’t budge. It seems to be powered by electricity.");
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
