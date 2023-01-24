package ru.pavlenty.surfacegame2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.preference.Preference;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class GameView extends SurfaceView implements Runnable {


    private Thread gameThread = null;
    private Timer timer;

    private Player player;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private Enemy enemy;
    private Boom boom;
    private Friend friend;
    private Bullet bullet;

    int screenX;
    int countMisses;
    int score;
    private int count_hp;
    private Boolean isCollisionEnemy = false;

    volatile boolean playing;
    private boolean isGameOver;
    private boolean isHpLost;
    private boolean isHpAdd;

    private ArrayList<Star> stars = new ArrayList<Star>();
    int highScore[] = new int[4];



    SharedPreferences sharedPreferences;

    static MediaPlayer gameOnsound;
    final MediaPlayer killedEnemysound;
    final MediaPlayer gameOversound;

    Context context;

    public GameView(Context context, int screenX, int screenY) {
        super(context);
        player = new Player(context, screenX, screenY);
        surfaceHolder = getHolder();
        paint = new Paint();
        bullet = new Bullet(context,screenX,screenY);


        int starNums = 100;
        for (int i = 0; i < starNums; i++) {
            Star s = new Star(screenX, screenY);
            stars.add(s);
        }
        Enemy e = new Enemy(context,screenX,screenY);
        enemy = e;
        Friend f = new Friend(context,screenX,screenY);
        friend = f;
        boom = new Boom(context);
        this.screenX = screenX;
        countMisses = 0;
        isGameOver = false;
        isHpLost = false;
        count_hp = 3;


        score = 0;
        sharedPreferences = context.getSharedPreferences("SHAR_PREF_NAME", Context.MODE_PRIVATE);


        highScore[0] = sharedPreferences.getInt("score1", 0);
        highScore[1] = sharedPreferences.getInt("score2", 0);
        highScore[2] = sharedPreferences.getInt("score3", 0);
        highScore[3] = sharedPreferences.getInt("score4", 0);
        this.context = context;


        gameOnsound = MediaPlayer.create(context,R.raw.gameon);
        killedEnemysound = MediaPlayer.create(context,R.raw.killedenemy);
        gameOversound = MediaPlayer.create(context,R.raw.gameover);


        gameOnsound.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                player.stopBoosting();
                break;
            case MotionEvent.ACTION_DOWN:
                player.setBoosting();
                break;


        }

        if(isGameOver){
            if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                context.startActivity(new Intent(context,MainActivity.class));
            }
        }
        return true;
    }

    @Override
    public void run() {
        while (playing) {
            update();
            draw();
            control();
        }
    }

    public void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.BLACK);


            paint.setColor(Color.WHITE);
            paint.setTextSize(20);

            for (Star s : stars) {
                paint.setStrokeWidth(s.getStarWidth());
                canvas.drawPoint(s.getX(), s.getY(), paint);
            }
            canvas.drawBitmap(enemy.getBitmap(),
                    enemy.getX(),
                    enemy.getY(),
                    paint);
            canvas.drawBitmap(
                    boom.getBitmap(),
                    boom.getX(),
                    boom.getY(),
                    paint);
            canvas.drawBitmap(
                    friend.getBitmap(),
                    friend.getX(),
                    friend.getY(),
                    paint);


            paint.setTextSize(50);
            canvas.drawText("Очки: "+score,100,50,paint);
            canvas.drawText("Жизни: " + count_hp, 800, 50,paint);

            canvas.drawBitmap(
                    player.getBitmap(),
                    player.getX(),
                    player.getY(),
                    paint);


            if(isGameOver){
                paint.setTextSize(150);
                paint.setTextAlign(Paint.Align.CENTER);

                int yPos=(int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));
                canvas.drawText("Конец игры",canvas.getWidth()/2,yPos,paint);

            }

            surfaceHolder.unlockCanvasAndPost(canvas);

        }
    }


    public static void stopMusic(){
        gameOnsound.stop();
    }

    private void update() {
        score++;

        player.update();


        for (Star s : stars) {
            s.update(player.getSpeed());
        }
        enemy.update(player.getSpeed());
        friend.update(player.getSpeed());
        isHpLost = Rect.intersects(enemy.getDetectCollision(),player.getDetectCollision());
        if (isHpLost) {
            timer = new Timer();
            boom.setX(enemy.getX());
            boom.setY(enemy.getY());
            count_hp-=1;
            Log.d("qqqq", Integer.toString(count_hp));
            enemy.restart();
            timer.schedule(new TimerTask() {
                public void run() {
                    timerTick();
                }
            }, 500);
        }
        isHpAdd = Rect.intersects(friend.getDetectCollision(),player.getDetectCollision());
        if (isHpAdd){
            count_hp+=1;
            friend.restart();
        }

        if (count_hp <= 0){
            isGameOver = true;
            playing = false;
        }
    }

    private void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void timerTick() {
        boom.setX(2000);
        boom.setY(2000);
        timer = null;
    }



}