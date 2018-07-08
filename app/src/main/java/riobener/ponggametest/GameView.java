package riobener.ponggametest;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{
    public static Canvas canvas;
    GameThread gameThread;
    Point rectPoint,ballPoint;
    Player player;
    Ball ball;
    Paint p;

    public static int colorBG = Color.BLUE;
    int yStartPos;
    int firstBorderY;
    int secondBorderY;
    int widthScreen;
    int heightScreen;

    public GameView(Context context) {
        super(context);

        getHolder().addCallback(this);
        yStartPos = this.getResources().getDisplayMetrics().heightPixels/2;
        widthScreen = this.getResources().getDisplayMetrics().widthPixels;
        heightScreen = this.getResources().getDisplayMetrics().heightPixels;
        gameThread = new GameThread(this,getHolder());
        p = new Paint();
        rectPoint = new Point(80,yStartPos);
        ballPoint = new Point(widthScreen/2,heightScreen/2);
        player = new Player(new Rect(0,0,40,400),Color.rgb(255,255,255));
        ball = new Ball(new Rect(0,0,80,80),ballPoint,this);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

        gameThread = new GameThread(this,getHolder());
        gameThread.setRunning(true);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

        boolean retry = true;
        while(retry){
            try {
                this.canvas = null;
                gameThread.setRunning(false);
                gameThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    public void saveBorders(int pointFirst,int pointSecond){
        firstBorderY = pointFirst;
        secondBorderY = pointSecond;
    }

    public int getSecondBorderY(){
        return secondBorderY;
    }
    public int getFirstBorderY(){
        return firstBorderY;
    }

    public void setBallCordinates(int x, int y){
        if(ballPoint!=null)
        ballPoint.set(x,y);
    }

    public void update() {
        player.update(rectPoint);
        ball.update(ballPoint);
    }

    public void detectCollision(){
        ball.detectBallCollision(getSecondBorderY()+180,widthScreen,getFirstBorderY()-80,player.getRectangle());
    }

    public void setupBorder(){
        saveBorders((yStartPos/4)+60,(yStartPos*2)-180);
    }

    @Override
    public void draw(Canvas canvas) {
        if(this.canvas!=null){
        super.draw(canvas);
        canvas.drawColor(colorBG);
        ball.draw(canvas);
        player.draw(canvas);

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch(event.getAction()){

            case MotionEvent.ACTION_MOVE:
                if(event.getY()<getSecondBorderY()&&event.getY()>getFirstBorderY()){
                    rectPoint.set(50,(int)event.getY());
                }
        }
        return true;
        //return super.onTouchEvent(event);
    }
}
