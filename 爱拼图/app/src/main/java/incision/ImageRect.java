package incision;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * 切割图片
 * @author Administrator
 *
 */
	public class ImageRect {
		public int xline,yline;//图片的显示比例
		public static int rectW,rectH;//切割后图片的宽高
		Bitmap rectBitmap;//当前游戏的图片
/**
 * 
 * @param currentIMG 图片
 * @param size游戏难度3*3 
 * @param id对应格子id id= 0 1 2 3 4 5 6 7 8
 */
		public ImageRect(Bitmap currentIMG,int size,int id){
			xline = id%size;//0 1 2 0 1 2 0 1 2
			yline = id/size;//0 0 0 1 1 1 2 2 2 
			rectW = currentIMG.getWidth()/size;
			rectH = currentIMG.getHeight()/size;
			if (id ==size*size-1) {
				/**
				 * Config:存储颜色值，位数越高，图像越逼真
				 */
				rectBitmap= Bitmap.createBitmap(rectW, rectH, Bitmap.Config.ARGB_8888);
				Canvas canvas = new Canvas(rectBitmap);
				canvas.drawColor(Color.WHITE);
			}else {
				rectBitmap = Bitmap.createBitmap(currentIMG, xline*rectW, yline*rectH, rectW, rectH);
			}
	
		}
		public void paint(Canvas canvas,int x,int y,Paint paint) {
			
			canvas.drawBitmap(rectBitmap, x, y,null);
		}
		
		
}
