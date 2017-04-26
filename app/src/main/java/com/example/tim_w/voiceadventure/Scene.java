package com.example.tim_w.voiceadventure;

import android.widget.TextView;

/**
 * Created by tim_w on 3/23/2017.
 */

public interface Scene {
    public void load(TextView v); //Read scene description, change scene text to correct scene text
    public String performAction(String keyword, String command); //have defined list of possible actions in current scene
    public Scene navigate(String direction, AdventureMap map); //should be Map map // get scene at correct index, then call Scene.load()
    public Scene navigate(String keyword, String object, AdventureMap map);
    public Inventory getInventory();
    public void setInventory(Inventory inventory);
}
