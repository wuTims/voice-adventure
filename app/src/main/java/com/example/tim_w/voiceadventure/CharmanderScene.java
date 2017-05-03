package com.example.tim_w.voiceadventure;

import android.widget.TextView;

/**
 * Created by gswu on 4/26/2017.
 */

public class CharmanderScene implements Scene {
    private String _desc;
    private TextView tView;
    private boolean frozen = false;
    private boolean readTag = false;
    private Item Charmander;
    private Inventory _inventory;

    public CharmanderScene() {
        Charmander = new Item("charmander", "Charmander, the fire pokemon. " +
                "It’s tail is lit with fire. Using flamethrower from its mouth, " +
                        "it can burn through anything");
        this._desc = "You climb to the top of the volcano. You see your Charmander in the " +
                "middle of a pit of lava. It has a tag on its tail. It’s too hot to do anything.";
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
                if (command.contains("BLIZZARD")) {
                    if (_inventory.checkItem("articuno") && !frozen) {
                        frozen = true;
                        this._desc = "You climb to the top of the volcano. You see your Charmander in the middle" +
                                " of a frozen pit of lava. It has a tag on its tail. It’s cool enough now.";
                        return "Articuno use blizzard. With a giant flap of its wings it freezes the lava around Charmander.";
                    } else {
                        return "None of your Pokemons knows Blizzard.";
                    }
                }
                if (command.contains("POKEBALL")) {
                    if(frozen){
                        if (readTag && !_inventory.checkItem("charmander")) {
                            _inventory.addItem(Charmander);
                            this._desc = "You are on top of a volcano. There is a frozen pit of lava.";
                            return "Charmander returns to its pokeball. Charmander has the ability to use FLAMETHROWER.";
                        } else if(!readTag){
                            return "The tag looks pretty important. You might want to read it first.";
                        } else {
                            return "You already have Charmander";
                        }
                    } else if (!frozen) {
                        return "It's too hot.";
                    } else {
                        return "You already have Charmander";
                    }
                }
                if(command.contains("POKEDEX") && this._inventory.checkItem("pokedex")){
                    if(command.contains("PIKACHU")) {
                        return "Pikachu, the electric mouse pokemon. " +
                                "Pikachu has the ability to use THUNDERBOLT.";
                    }else if(command.contains("ABRA")) {
                        return "Abra, a psychic type Pokemon. It can use TELEPORT to transport to different locations.";
                    }else if(command.contains("ARTICUNO")) {
                        return "Articuno, the legendary flying ice Pokemon. Articuno has the ability to use BLIZZARD.";
                    }else if(command.contains("CHARMANDER")){
                        return "Charmander, the fire lizard Pokemon. Charmander has the ability to use FLAMETHROWER.";
                    }else{
                        return "Please specify a Pokemon to look up in the Pokedex.";
                    }
                }
            case "THROW":
                if (command.contains("POKEBALL")) {
                    if(frozen){
                        if (readTag && !_inventory.checkItem("charmander")) {
                            _inventory.addItem(Charmander);
                            this._desc = "You are on top of a volcano. There is a frozen pit of lava.";
                            return "Charmander returns to its pokeball. Charmander has the ability to use FLAMETHROWER.";
                        } else if(!readTag){
                            return "The tag looks pretty important. You might want to read it first.";
                        } else {
                            return "You already have Charmander";
                        }
                    } else if (!frozen) {
                        return "It's too hot.";
                    } else {
                        return "You already have Charmander";
                    }
                }
            case "ARTICUNO":
                if(command.contains("BLIZZARD") && this._inventory.checkItem("articuno") && !frozen){
                    frozen = true;
                    this._desc = "You climb to the top of the volcano. You see your Charmander in the middle" +
                            " of a frozen pit of lava. It has a tag on its tail. It’s cool enough now.";
                    return "Articuno use blizzard. With a giant flap of its wings it freezes the lava around Charmander.";
                }
            case "EXAMINE":
            case "CHECK":
            case "READ":
                if (command.contains("TAG")) {
                    readTag = true;
                    return "The number 2 is written on the tag.";
                }
            case "LOOK":
                if(command.equals("")){
                    return this._desc;
                } else if (command.contains("TAG")) {
                    readTag = true;
                    return "The number 2 is written on the tag.";
                }
            case "TAKE":
            case "CAPTURE":
            case "CATCH":
                if (command.contains("CHARMANDER")) {
                    if(frozen){
                        if (readTag && !_inventory.checkItem("charmander")) {
                            _inventory.addItem(Charmander);
                            this._desc = "You are on top of a volcano. There is a frozen pit of lava.";
                            return "Charmander returns to its pokeball. Charmander has the ability to use FLAMETHROWER.";
                        } else if(!readTag){
                            return "The tag looks pretty important. You might want to read it first.";
                        } else {
                            return "You already have Charmander";
                        }
                    } else if (!frozen) {
                        return "It's too hot.";
                    } else {
                        return "You already have Charmander";
                    }
                }
            case "HELP":
                String helpString = "";
                if(frozen){
                    helpString += "Try to CAPTURE CHARMANDER.\n\n";
                }
                if(!frozen && this._inventory.checkItem("articuno")){
                    helpString += "Try to freeze the lava with BLIZZARD.\n\n";
                }
                if(!this._inventory.checkItem("articuno")){
                    helpString += "You might need a Pokemon that can FREEZE the lava.\n\n";
                }

                return helpString;
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
            case "EAST":
                if(_inventory.checkItem("pikachu") && _inventory.checkItem("charmander") && _inventory.checkItem("articuno")){
                    //3,1 --> 5,1
                    nextScene = map.getSceneAtPosition(currPos.getX() + 2, currPos.getY());
                    map.setCurrPos(currPos.getX() + 2, currPos.getY());
                    nextScene.setInventory(this._inventory);
                    return nextScene;
                } else {
                    nextScene = map.getSceneAtPosition(currPos.getX() + 1, currPos.getY());
                    map.setCurrPos(currPos.getX() + 1, currPos.getY());
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
