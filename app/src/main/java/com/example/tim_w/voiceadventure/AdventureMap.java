package com.example.tim_w.voiceadventure;

/**
 * Created by tim_w on 4/11/2017.
 */

public class AdventureMap {
    private Scene[][] map;
    private int height;
    private int width;

    public AdventureMap(int height, int width){
        this.height = height;
        this.width = width;
        this.map = new Scene[height][width];
    }

    public void setSceneAtPosition(Scene scene, int x, int y){
        this.map[y][x] = scene;
    }

    public Scene getSceneAtPosition(int x, int y){
        if(x >= this.width || y >= this.height || x < 0 || y < 0){
            return null;
        }
        return this.map[y][x];
    }

}
