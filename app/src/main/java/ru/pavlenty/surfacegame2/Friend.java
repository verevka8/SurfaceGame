package ru.pavlenty.surfacegame2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

import java.util.Random;

public class Friend {
    private Bitmap bitmap;
    private int x;
    private int y;
    private int speed = 0;

    private int maxY;
    private int maxX;
    private int minY;
    private int minX;

    private long present_time;
    private long previous_time = 0;
    private long delay_time;

    private Rect detectCollision;

    public Friend(Context context, int screenX, int screenY) {
        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;
        Random generator = new Random();
        speed = 15 + generator.nextInt(10);
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.friend);
        x = maxX;
        y = generator.nextInt(maxY);
        detectCollision =  new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }

    public void update(int player_speed) {

        x -= (speed);

        if (x < (-1) * bitmap.getWidth()) {
            Random generator = new Random();
            present_time = System.currentTimeMillis();
            delay_time = generator.nextInt(200) * 100 + 200;
            if (present_time - previous_time >= delay_time){
                previous_time = present_time;
                speed = 15 + generator.nextInt(15);
                y = generator.nextInt(maxY);
                x = maxX;
            }
            else{
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
}