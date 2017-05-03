package com.example.tim_w.voiceadventure;

import android.widget.TextView;

/**
 * Created by gswu on 5/1/2017.
 */

public class VictoryScene implements Scene{
    private String _desc;
    private TextView tView;
    boolean firstCombo = false;
    boolean secondCombo = false;
    boolean thirdCombo = false;

    public VictoryScene() {
        this._desc = "Team Rocket challenges you to a battle. You see four numbers etched on the " +
                "front of the robot. 8-7-4-3. You remember what Professor Oak told you before " +
                "coming here: To make a strong attack use your pokemons' ability together.";
    }

    @Override
    public void load(TextView v) {
        v.setText(this._desc);
        if (this.tView == null) this.tView = v;
    }

    @Override
    public String performAction(String keyword, String command) {
        switch (keyword) {
            case "LOOK":
                if(command.equals("")){
                    return "Team Rocket challenges you to a battle. You see four numbers etched on the front of the robot. 8-7-4-3.";
                }
            case "USED":
            case "USE":
                if (command.contains("TELEPORT ") && command.contains("THUNDERBOLT")) {
                    firstCombo = true;
                    return "Abra uses teleport to teleport you to the robot’s blind spot. Pikachu uses thunderbolt " +
                            "to attack the robot. It’s a critical hit!";
                } else if (command.contains("TELEPORT ") && command.contains("FLAMETHROWER")) {
                    if (firstCombo) {
                        secondCombo = true;
                        return "Charmander uses flamethrower to set the robot on fire. It comes at you but you teleport " +
                                "away with Abra. It’s super effective!";
                    } else {
                        return "Your attacked missed";
                    }
                } else if (command.contains("FLAMETHROWER ") && command.contains("BLIZZARD") && secondCombo) {
                    if (firstCombo && secondCombo) {
                        thirdCombo = true;
                        return "Articuno and Charmander attack at the same time with flamethrower and blizzard. The sudden " +
                                "change from hot and cold is messing up the robot. It seems pretty beat up.";
                    } else {
                        return "Your attacked missed";
                    }
                } else if (command.contains("THUNDERBOLT") ){
                    if(firstCombo && secondCombo && thirdCombo){
                        return "Pikachu uses thunderbolt to blow up the robot. You hear as Team Rocket is blown away and disappear into the clouds:" +
                                "Team rocket is blasting off againnnn. Congratulations! You have beaten Team Rocket. Game Over.";
                    } else {
                        return "You remember what Professor Oak told you before coming here: “use your pokemon’s abilities in a combination with another pokemon to make a stronger attack.";
                    }
                 } else {
                    return "Remember what Professor Oak told you before coming here: To make a strong attack use your pokemons’ ability together.";
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
