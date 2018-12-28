package incision;

import android.graphics.RectF;

/**
 * 绘制格子
 * @author Administrator
 *
 */
public class Block {
	public int x,y;//格子坐标
	public int xline,yline ;//格子比例关系
	RectF rectF = null;//格子所在矩形范围
	public int id;
/**
 * 
 * @param level 游戏难度
 * @param id  格子id
 */
	public  Block(int level,int id){
		xline = id%level;
		yline = id/level;
		
		x = (id%level)*(ImageRect.rectW+2);
		y = (id/level)*(ImageRect.rectH+2);
		this.id = id;
	}
	
//	  设置每个格子所在的矩形范围
	 
	public void init() {
		rectF = new RectF(x, y, x+ImageRect.rectW, y+ImageRect.rectH);
	}
	
	public boolean onClick(float x,float y) {
		//	contains判断当前点击的位置是否在矩形范围内
		return rectF.contains(x, y);
	}
}
