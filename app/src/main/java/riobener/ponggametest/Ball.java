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

    private float x;
    private float y;

    private float speed = 10f;

    private double angle = Math.toRadians(280);
    private double direction = 1;


   public Ball(Rect circle, GameView gameView){
       this.gameView = gameView;
       ball = new RectF(circle);
       circlePaint = new Paint();
       circlePaint.setStyle(Paint.Style.FILL);
       circlePaint.setColor(Color.WHITE);
    }


    @Override
    public void draw(Canvas canvas) {
        canvas.drawRoundRect(ball,250,250,circlePaint);
    }

    @Override
    public void update(PointF point) {
        x = point.x;
        y = point.y;
        ball.set(point.x-ball.width()/2,point.y - ball.height()/2,point.x + ball.width()/2,point.y + ball.height()/2);
        x += speed * Math.cos(angle*direction);
        y += speed * Math.sin(angle*direction);

        gameView.setBallCordinates(x,y);
       }

    public void changeBGColor(){
        Random randomColor = new Random();
        colorBG = Color.argb(255, randomColor.nextInt(256),randomColor.nextInt(256), randomColor.nextInt(256));
    }

    public void detectBallCollision(int bottomBorder, int rightBorder, int topBorder, Player player){
            if(ball.bottom>=bottomBorder){
                direction = 1;


            }else if(ball.right>=rightBorder){
                    if(direction == 1){
                        angle = Math.toRadians(260);
                        direction = 1;
                    }else{
                        angle = Math.toRadians(260);
                        direction = -1;
                    }
            }else if(ball.top<=topBorder){

                direction = -1;

                //center of player
            }else if((getLeftX()==player.getRightX()&&getLeftY()==player.getRectangle().centerY()+1)||(getLeftX()==player.getRightX()&&getLeftY()==player.getRectangle().centerY()-1)||
                    (getLeftX()==player.getRightX()&&getLeftY()==player.getRectangle().centerY())){


                //higher than center of player
            }else if(getLeftX()==player.getRightX()&&eqCollide(getLeftY(),player.getHigherY(),player.getRectangle().centerY())==1){


                //lower that center of player
            }else if(getLeftX()==player.getRightX()&&eqCollide(getLeftY(),player.getLowerY(),player.getRectangle().centerY())==0){

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
