package riobener.ponggametest;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.Random;

import static riobener.ponggametest.GameView.colorBG;

public class Ball implements GameObjects {
    Paint circlePaint;
    RectF ball;
    GameView gameView;

    private int x;
    private int y;

    private final float speedX = 20f;
    private final float speedY = 20f;

    private int directionX = 1;
    private int directionY = 0;



   public Ball(Rect circle, Point point, GameView gameView){
       this.gameView = gameView;
       ball = new RectF(circle);
       circlePaint = new Paint();
       circlePaint.setStyle(Paint.Style.FILL);
       circlePaint.setColor(Color.WHITE);
    }


    @Override
    public void draw(Canvas canvas) {
        canvas.drawRoundRect(ball,200,200,circlePaint);
    }

    @Override
    public void update(Point point) {
        x = point.x;
        y = point.y;
        ball.set(point.x-ball.width()/2,point.y - ball.height()/2,point.x + ball.width()/2,point.y + ball.height()/2);
        x += directionX * speedX;
        y += directionY * speedY;
        System.out.println(x+" "+y);
        gameView.setBallCordinates(x,y);
       }

    public void changeBGColor(){
        Random randomColor = new Random();
        colorBG = Color.argb(255, randomColor.nextInt(256),randomColor.nextInt(256), randomColor.nextInt(256));
    }

     public void detectBallCollision(int bottomBorder, int rightBorder, int topBorder, RectF player){
            if(ball.bottom>=bottomBorder){
                directionY = -1;
            }else if(ball.right>=rightBorder){
                directionX = -1;
            }else if(ball.top<=topBorder){
                directionY = 1;
            }else if(RectF.intersects(ball,player)){
                directionX = 1;
                changeBGColor();


            }
     }
}
