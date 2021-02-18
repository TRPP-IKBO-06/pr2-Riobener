package riobener.ponggametest;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

public class Player implements GameObjects {
    private RectF rectangle;
    private Paint p;
    private int color;



    public Player(Rect rectangle ,int color){
        this.rectangle = new RectF(rectangle);
        this.color = color;
        p = new Paint();
        p.setColor(color);

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(rectangle,p);
    }

    @Override
    public void update(PointF point) {
        rectangle.set(point.x-rectangle.width()/2, point.y-rectangle.height()/2,point.x + rectangle.width()/2,point.y + rectangle.height()/2 );


    }
    public void gitTest(){//someChanger1111}
    public RectF getRectangle(){
        return rectangle;
    }

    public float getRightX(){
        return rectangle.centerX()+rectangle.width()/2;
    }

    public float getHigherY(){
     return rectangle.centerY()-rectangle.height()/2;
    }

    public float getLowerY(){
        return rectangle.centerY()+rectangle.height()/2;
    }







}
