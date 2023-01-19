package ru.pavlenty.surfacegame2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Boom {

    private int x;
    private int y;
    private Bitmap bitmap;

    public Boom(Context context){
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.boom);
        x = 2000;
        y = 2000;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
