package ru.pavlenty.surfacegame2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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

    private long present_time;
    private long previous_time = 0;
    private long delay_time = 1000;


    public Enemy(Context context,int screenX, int screenY) {
        maxX = screenX;
        maxY = screenY;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy);
        x = maxX;
        y = maxY;
        speed = 20;
        detectCollision =  new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());

    }

    public void update(int playerSpeed) {

        x -= speed;
        x -= playerSpeed;

        if (x < (-1) * bitmap.getWidth()) {
            present_time = System.currentTimeMillis();
            if (present_time - previous_time >= delay_time) {
                Random generator = new Random();
                delay_time = ThreadLocalRandom.current().nextInt(2000, 4000);
                previous_time = present_time;
                x = maxX;
                y = generator.nextInt(maxY - bitmap.getHeight());
                speed = 20;
            }
            else {
                speed = 0;
            }
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
    public void restart() {
        x = maxX;
        y = maxY;
        speed = 20;
    }
}
