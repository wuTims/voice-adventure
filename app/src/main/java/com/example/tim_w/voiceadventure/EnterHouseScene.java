package com.example.tim_w.voiceadventure;

import android.widget.TextView;

import java.util.HashSet;

/**
 * Created by tim_w on 4/24/2017.
 */

public class EnterHouseScene implements Scene {
    private Inventory _inventory;
    private String _desc;
    private HashSet<Item> sceneItems;
    private TextView tView;
    private boolean lanternOn = false;
    private Item book, pokeballs, mag_glass;
    private boolean scribbleRead = false;
    private boolean deciphered = false;

    public EnterHouseScene() {
        if (lanternOn) {
            this._desc = "You stand in a large room with a door to the north. There is a table.";
        } else {
            this._desc = "They key jams in the door and you enter the house. It is too dark to see anything.";
        }

        this.book = new Item("book", "An old ancient cipher");
        this.pokeballs = new Item("pokeballs", "Basic balls used to catch Poakamawn");
        this.mag_glass = new Item("magnifier", "A piece of glass used to magnify things");

        this.sceneItems = new HashSet<Item>();

        this.sceneItems.add(this.book);
        this.sceneItems.add(this.pokeballs);
        this.sceneItems.add(this.mag_glass);

    }

    @Override
    public void load(TextView v) {
        v.setText(this._desc);
        if (this.tView == null) this.tView = v;
    }

    @Override
    public String performAction(String keyword, String command) {
        if (!lanternOn) {
            switch (keyword) {
                case "USE":
                case "USED":
                    if (command.contains("LANTERN") && this._inventory.checkItem("lantern")) {
                        lanternOn = true;
                        this._desc = "You stand in a large room with a door to the north and exit to the south. There is a table.";
                        return "You turn the lantern on. " + this._desc;
                    } else if (lanternOn) {
                        return "The lantern is already on.";
                    } else {
                        return "You can't use that right now.";
                    }
                case "TAKE":
                case "LOOK":
                case "OPEN":
                case "READ":
                    return "It's too dark to do anything. Maybe if you had some light...";
            }
        } else {
            switch (keyword) {
                case "CHECK":
                case "LOOK":
                case "EXAMINE":
                    if (command.contains("TABLE")) {
                        int resultID = checkItemsInScene(this.sceneItems);
                        switch (resultID) {
                            case 0:
                                return "There is a book, magnifier, and four poakaballs.";
                            case 1:
                                return "There is a book and a four poakaballs.";
                            case 2:
                                return "There is a book and a magnifier";
                            case 3:
                                return "There is a magnifier and four poakaballs.";
                            case 4:
                                return "There is a book.";
                            case 5:
                                return "There is a magnifier.";
                            case 6:
                                return "There are four poakaballs.";
                            case 7:
                                return "It is an old dusty table.";
                        }
                    }
                    if (command.equals("")) {
                        return this._desc;
                    }
                case "READ":
                    if (command.contains("BOOK") && this._inventory.checkItem("book") && !scribbleRead) {
                        return "A list of translations for Unknown symbols.";
                    } else if (command.contains("BOOK") && this._inventory.checkItem("book") && scribbleRead) {
                        return "A precious stone, as clear as diamond.\n" +
                                "Seek it out whilst the sun’s near the horizon.\n" +
                                "Though you can walk on water with its power,\n" +
                                "Try to keep it, and it’ll vanish ere an hour.\n" +
                                "Who am I?";
                    }

                    break;
                case "USE":
                case "USED":
                    if (command.contains("BOOK") && this._inventory.checkItem("book") && scribbleRead) {
                        return "A precious stone, as clear as diamond.\n" +
                                "Seek it out whilst the sun’s near the horizon.\n" +
                                "Though you can walk on water with its power,\n" +
                                "Try to keep it, and it’ll vanish ere an hour.\n" +
                                "Who am I?";
                    } else if (command.contains("BOOK") && this._inventory.checkItem("book") && !scribbleRead) {
                        return "This isn't the time to use that.";
                    }
                    break;
                case "TAKE":
                    if (command.contains("BOOK") && this.sceneItems.contains(book)) {
                        addItem(this.book);
                        return "You put the book in your bag.";
                    } else if (command.contains("MAGNIFIER") && this.sceneItems.contains(mag_glass)) {
                        addItem(this.mag_glass);
                        return "You put the magnifier in your bag.";
                    } else if (command.contains("POKEBALLS") || command.contains("POKEBALL") && this.sceneItems.contains(pokeballs)) {
                        addItem(this.pokeballs);
                        return "You put the pokeballs in your bag.";
                    } else if (command.contains("ALL") && this.sceneItems.contains(book) && this.sceneItems.contains(mag_glass) && this.sceneItems.contains(pokeballs)) {
                        addItem(this.book);
                        addItem(this.mag_glass);
                        addItem(this.pokeballs);
                        return "You put all the items in your bag.";
                    }
                    break;
                case "ICE":
                    if (command.equals("")) {
                        deciphered = true;
                        return "The door shatters and opens up a room to the north.";
                    }
                    break;
            }
        }


        return null;
    }

    private int checkItemsInScene(HashSet<Item> sceneItems) {
        if (sceneItems.contains(this.book) && sceneItems.contains(this.pokeballs) && sceneItems.contains(this.mag_glass)) {
            return 0;
        } else if (sceneItems.contains(this.book) && sceneItems.contains(this.pokeballs)) {
            return 1;
        } else if (sceneItems.contains(this.book) && sceneItems.contains(this.mag_glass)) {
            return 2;
        } else if (sceneItems.contains(this.pokeballs) && sceneItems.contains(this.mag_glass)) {
            return 3;
        } else if (sceneItems.contains(this.book)) {
            return 4;
        } else if (sceneItems.contains(this.mag_glass)) {
            return 5;
        } else if (sceneItems.contains(this.pokeballs)) {
            return 6;
        }
        return 7;
    }

    private void addItem(Item item) {
        this.sceneItems.remove(item);
        this._inventory.addItem(item);
    }

    @Override
    public Scene navigate(String direction, AdventureMap map) {
        Position currPos = map.getCurrPos();

        switch (direction) {
            case "NORTH":
            case "FORWARD":
            case "FRONT":
                if (!deciphered) {
                    scribbleRead = true;
                    this.tView.setText("The door won't budge. There are Unknown symbols on the door that you can't decipher.");
                } else if (deciphered) {
                    Scene nextScene = map.getSceneAtPosition(currPos.getX(), currPos.getY() - 1);
                    map.setCurrPos(currPos.getX(), currPos.getY() - 1);
                    nextScene.setInventory(this._inventory);
                    return nextScene;
                }
                break;
            case "SOUTH":
            case "BACKWARD":
            case "BACK":
                Scene nextScene = map.getSceneAtPosition(currPos.getX() - 1, currPos.getY());
                map.setCurrPos(currPos.getX() - 1, currPos.getY());
                nextScene.setInventory(this._inventory);
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

    public void setInventory(Inventory inventory) {
        this._inventory = inventory;
    }
}
