package ru.pavlenty.surfacegame2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class Enemy {
    private Bitmap bitmap;
    private int x;
    private int y;
    private int speed;

    private int maxX;
    private int maxY;
    private int minX;
    private int minY;
    private Rect detectCollision;


    public Enemy(Context context,int screenX, int screenY) {
        maxX = screenX;
        maxY = screenY;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy);
        x = 75;
        y = maxY;
        speed = 20;
        detectCollision =  new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());

    }

    public void update(int playerSpeed) {

        x -= speed;
        x -= playerSpeed;

        if (x < 0) {
            x = maxX;
            Random generator = new Random();
            y = generator.nextInt(maxY);
            speed = 20;
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }
}
