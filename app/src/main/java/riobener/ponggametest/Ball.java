package riobener.ponggametest;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.Random;

import static riobener.ponggametest.GameView.colorBG;

public class Ball implements GameObjects {
    Paint circlePaint;
    RectF ball;
    GameView gameView;
    public static float ballYForCollide;

    private float x;
    private float y;

    private final float speedX = 15f;
    private final float speedY = 15f;

    private float directionX = 1f;
    private float directionY = 0f;



   public Ball(Rect circle, GameView gameView){
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
    public void update(PointF point) {
        x = point.x;
        y = point.y;
        ballYForCollide = y;
        ball.set(point.x-ball.width()/2,point.y - ball.height()/2,point.x + ball.width()/2,point.y + ball.height()/2);
        x += directionX * speedX;
        y += directionY * speedY;

        gameView.setBallCordinates(x,y);
       }

    public void changeBGColor(){
        Random randomColor = new Random();
        colorBG = Color.argb(255, randomColor.nextInt(256),randomColor.nextInt(256), randomColor.nextInt(256));
    }

    public void detectBallCollision(int bottomBorder, int rightBorder, int topBorder, Player player){
            if(ball.bottom>=bottomBorder){
                directionY = -1f;
            }else if(ball.right>=rightBorder){
                directionX = -1f;
            }else if(ball.top<=topBorder){
                directionY = 1f;
            }else if((getLeftX()==player.getRightX()&&getLeftY()==player.getRectangle().centerY()+1)||(getLeftX()==player.getRightX()&&getLeftY()==player.getRectangle().centerY()-1)||
                    (getLeftX()==player.getRightX()&&getLeftY()==player.getRectangle().centerY())){
                directionX = 1f;
                directionY = 0f;
            }else if(getLeftX()==player.getRightX()&&eqCollide(getLeftY(),player.getHigherY(),player.getRectangle().centerY())==1){
                directionX = 1f;
                directionY = -1f;
            }else if(getLeftX()==player.getRightX()&&eqCollide(getLeftY(),player.getLowerY(),player.getRectangle().centerY())==0){
                directionX = 1f;
                directionY = 1f;
            }
     }

     public float getLeftX(){
       return ball.centerX()-ball.width()/2;
     }

     public float getLeftY(){
       return ball.centerY();
     }
     //0 - lowSide , 1 - highSide
     int eqCollide(float ballPoint, float sidePoint, float centerPoint){
       if(ballPoint>centerPoint&&ballPoint<sidePoint){
           return 0;
       }else if(ballPoint<centerPoint&&ballPoint>sidePoint){
           return 1;
       }else{
           return -1;
       }

     }
}
