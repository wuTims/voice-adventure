package com.example.tim_w.voiceadventure;

/**
 * Created by tim_w on 4/17/2017.
 */

public class Position {
    private int x, y;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void updatePos(int x, int y){
        this.x = x;
        this.y = y;
    }

}
