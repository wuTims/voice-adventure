package com.example.tim_w.voiceadventure;

/**
 * Created by tim_w on 3/23/2017.
 */

public interface Scene {
    public void load();
    public String performAction(String command);
    public void navigate(String direction, String map); //should be Map map
}
