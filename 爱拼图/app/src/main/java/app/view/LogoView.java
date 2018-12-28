package app.view;


import com.activity.LogoActivity;
import com.activity.R;

import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class LogoView extends View implements Runnable {


    int image[] = {/*R.drawable.mmlogo,R.drawable.and1,*/R.drawable.logo};
    Bitmap bitmap[] = null;

    public LogoView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
//		bitmap = new Bitmap[3];
//		for(int i =0;i<image.length;i++){
//		bitmap [i] = BitmapFactory.decodeResource(getResources(), image[i]);
//		bitmap [i] =Bitmap.createScaledBitmap(bitmap [i],LogoActivity.SCREEN_WIDTH , LogoActivity.SCREEN_HEIGH, true);
//		}
        bitmap = new Bitmap[1];
        bitmap[0] = BitmapFactory.decodeResource(getResources(), image[0]);
        bitmap[0] = Bitmap.createScaledBitmap(bitmap[0], LogoActivity.SCREEN_WIDTH, LogoActivity.SCREEN_HEIGH, true);

        new Thread(this).start();
    }

    int count;
    Boolean boolean1 = false;

    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        //	Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
//        canvas.drawBitmap(bitmap[count / 20], 0, 0, null);
        canvas.drawBitmap(bitmap[0], 0, 0, null);
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (!boolean1) {
            count++;
            postInvalidate();//子线程刷新界面
//			invalidate();主线程刷新界面
            if (count > 58) {
                boolean1 = true;
                LogoActivity.logoActivity.gomenu();
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
