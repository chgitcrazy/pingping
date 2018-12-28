package incision;

import android.graphics.RectF;

/**
 * ���Ƹ���
 * @author Administrator
 *
 */
public class Block {
	public int x,y;//��������
	public int xline,yline ;//���ӱ�����ϵ
	RectF rectF = null;//�������ھ��η�Χ
	public int id;
/**
 * 
 * @param level ��Ϸ�Ѷ�
 * @param id  ����id
 */
	public  Block(int level,int id){
		xline = id%level;
		yline = id/level;
		
		x = (id%level)*(ImageRect.rectW+2);
		y = (id/level)*(ImageRect.rectH+2);
		this.id = id;
	}
	
//	  ����ÿ���������ڵľ��η�Χ
	 
	public void init() {
		rectF = new RectF(x, y, x+ImageRect.rectW, y+ImageRect.rectH);
	}
	
	public boolean onClick(float x,float y) {
		//	contains�жϵ�ǰ�����λ���Ƿ��ھ��η�Χ��
		return rectF.contains(x, y);
	}
}
