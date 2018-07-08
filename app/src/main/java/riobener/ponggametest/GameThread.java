package riobener.ponggametest;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.widget.Toast;

import static riobener.ponggametest.GameView.canvas;


public class GameThread extends Thread {

    private boolean threadRun = false;

    SurfaceHolder holder;
    GameView gameView;


    GameThread(GameView gameView,SurfaceHolder holder){
        this.gameView = gameView;
        this.holder = holder;
}
    public void setRunning(boolean a) {
        threadRun = a;
    }

    @Override
    public void run() {
        while(threadRun){
            canvas = null;
            try{
                canvas = holder.lockCanvas();
                synchronized (holder) {
                    gameView.setupBorder();
                    gameView.update();
                    gameView.detectCollision();
                    gameView.draw(canvas);
                }
            }finally {
                if(canvas!=null)
                holder.unlockCanvasAndPost(canvas);

            }

        }

    }
}
