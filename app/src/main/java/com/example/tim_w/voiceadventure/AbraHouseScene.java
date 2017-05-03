package com.example.tim_w.voiceadventure;

import android.widget.TextView;

import java.util.HashSet;

/**
 * Created by tim_w on 4/26/2017.
 */

public class AbraHouseScene implements Scene {
    private Inventory _inventory;
    private String _desc;
    private HashSet<Item> sceneItems;
    private TextView tView;
    private Item abra;
    private boolean tagExamined = false;

    public AbraHouseScene(){
        this._desc = "Your Abra is sitting peacefully in the middle of the room. There is a tag shining on its leg.";
        this.abra = new Item("abra", "A psychic type Poakamawn. It can use teleport to move around.");

        this.sceneItems = new HashSet<Item>();
        this.sceneItems.add(this.abra);
    }

    @Override
    public void load(TextView v) {
        v.setText(this._desc);
        if (this.tView == null) this.tView = v;
    }

    @Override
    public String performAction(String keyword, String command) {
        switch(keyword){
            case "USE":
            case "USED":
                if(command.contains("POKEBALLS") || command.contains("POKEBALL") && this._inventory.checkItem("pokeballs")){
                    if(tagExamined){
                        addItem(abra);
                        this._desc = "An empty room.";
                        return "Abra returns to its Pokeball. Abra has the ability to use TELEPORT.";
                    }else if(!tagExamined){
                        return "The tag looks important. You might want to EXAMINE it first.";
                    }
                } else if(command.contains("POKEBALLS") || command.contains("POKEBALL") && !this._inventory.checkItem("pokeballs")){
                    return  "You don't have any pokeballs.";
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
                if(command.contains("TELEPORT") && this._inventory.checkItem("abra") && tagExamined){
                    return "TELE";
                }else if(command.contains("TELEPORT") && this._inventory.checkItem("abra") && !tagExamined){
                    return "The tag looks important. You might want to EXAMINE it first.";
                }else{
                    return "Input unknown. Try something else.";
                }
            case "THROW":
                if(command.contains("POKEBALLS") || command.contains("POKEBALL") && this._inventory.checkItem("pokeballs")){
                    if(tagExamined){
                        addItem(abra);
                        this._desc = "An empty room.";
                        return "Abra returns to its Pokeball. Abra has the ability to use TELEPORT.";
                    }else if(!tagExamined){
                        return "The tag looks important. You might want to EXAMINE it first.";
                    }
                } else if(command.contains("POKEBALLS") || command.contains("POKEBALL") && !this._inventory.checkItem("pokeballs")){
                    return  "You don't have any pokeballs.";
                }
            case "TAKE":
            case "CAPTURE":
            case "GET":
                if (command.contains("ABRA")) {
                    if(sceneItems.contains(abra)){
                            if(this._inventory.checkItem("pokeballs")){
                                if(tagExamined){
                                    addItem(abra);
                                    this._desc = "An empty room.";
                                    return "Abra returns to its Pokeball. Abra has the ability to use TELEPORT.";
                                }else if(!tagExamined){
                                    return "The tag looks important. You might want to EXAMINE it first.";
                                }
                            }else{
                                return "You don't have any pokeballs.";
                            }

                    }else{
                        return "You already have Abra.";
                    }
                }else{
                    return "Input unknown. Try something else.";
                }
            case "ABRA":
                if(command.contains("TELEPORT") && this._inventory.checkItem("abra") && tagExamined){
                    return "TELE";
                }else if(command.contains("TELEPORT") && this._inventory.checkItem("abra") && !tagExamined){
                    return "The tag looks important. You might want to EXAMINE it first.";
                }else{
                    return "Input unknown. Try something else.";
                }
            case "READ":
            case "EXAMINE":
            case "CHECK":
            case "LOOK":
                if(command.contains("TAG")){
                    tagExamined = true;
                    return "The number 5 is written on the tag.";
                }
                if(command.equals("")){
                    return this._desc;
                }

            case "HELP":
                String helpString = "";
                if(this._inventory.checkItem("abra")){
                    helpString += "Abra can USE TELEPORT to transport you.\n\n";
                }
                if(this._inventory.checkItem("pokeballs")){
                    helpString += "Try to CAPTURE ABRA.\n\n";
                }
                if(!this._inventory.checkItem("pokeballs")){
                    helpString += "You might have forgotten POKEBALLS on the table.\n\n";
                }

                helpString += "The TAG looks important to EXAMINE.";

                return helpString;
            default:
                return "Input unknown. Try something else.";
        }
    }

    private void addItem(Item item) {
        this.sceneItems.remove(item);
        this._inventory.addItem(item);
    }

    @Override
    public Scene navigate(String direction, AdventureMap map) {
        Position currPos = map.getCurrPos();
        switch(direction){
            case "SOUTH":
            case "BACK":
                return loadNextScene(currPos.getX(), currPos.getY() + 1, map);
            case "TELE":
                return loadNextScene(currPos.getX() + 2, currPos.getY() + 1, map);
        }

        return null;
    }

    private Scene loadNextScene(int x, int y, AdventureMap map){
        Position currPos = map.getCurrPos();
        Scene nextScene = map.getSceneAtPosition(x, y);
        map.setCurrPos(x, y);
        nextScene.setInventory(this._inventory);

        return nextScene;
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
