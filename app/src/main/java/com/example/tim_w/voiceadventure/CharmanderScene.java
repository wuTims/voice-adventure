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
    private boolean articuno; //idk what to do

    public CharmanderScene(boolean articuno) {
        this.articuno = articuno;
        if (frozen) {
            this._desc = "You climb to the top of the volcano. You see your Charmander in the middle" +
                    " of a frozen pit of lava. It has a tag on its tail. It’s cool enough now.";
        } else {
            this._desc = "You climb to the top of the volcano. You see your Charmander in the " +
                    "middle of a pit of lava. It has a tag on its tail. It’s too hot to do anything.";
        }
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
                if (command.contains("BLIZZARD")) {
                    if (articuno) {
                        return "Articuno use blizzard. With a giant flap of its wings it freezes the lava around Charmander.";
                    } else {
                        return "None of your Pokemons knows Blizzard.";
                    }
                }
                if (command.contains("POKEBALL")) {
                    if (readTag) {
                        return "Charmander returns to its pokeball. Charmander, the fire pokemon. " +
                                "It’s tail is lit with fire. Using flamethrower from its mouth, " +
                                "it can burn through anything";
                    } else {
                        return "The tag looks pretty important. You might want to read it first.";
                    }
                }
            case "EXAMINE":
            case "LOOK":
            case "READ":
                if (command.contains("TAG")) {
                    readTag = true;
                    return "The number 2 is written on the tag.";
                } else {
                    return "Input unknown. Try something else.";
                }
            case "TAKE":
            case "CAPTURE":
                if (command.contains("CHARMANDER")) {
                    if (readTag) {
                        return "Charmander returns to its pokeball. Charmander, the fire pokemon. " +
                                "It’s tail is lit with fire. Using flamethrower from its mouth, " +
                                "it can burn through anything";
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
            case "SOUTH":
                nextScene = map.getSceneAtPosition(currPos.getX() + 1, currPos.getY());
                map.setCurrPos(currPos.getX() + 1, currPos.getY());
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
