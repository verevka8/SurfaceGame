package ru.pavlenty.surfacegame2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Bullet {
    private Bitmap bitmap;
    private int x;
    private int y;
    private int speed;

    private int maxX;
    private int maxY;

    private Rect detectCollision;

    public Bullet(Context context, int screenX, int screenY){
        maxX = screenX;
        maxY = screenY;
        bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.bullet);
        x = -100;
        y = - 100;
        speed = 20;
        detectCollision =  new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }
    public void update(int playerSpeed) {

        x += speed;
        if (x > (maxX)) {
            x = -200;
            y = -200;
        }

        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();
    }

    public Rect getDetectCollision() {
        return detectCollision;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }


}
