package incision;

import java.util.Random;
import android.graphics.Bitmap;
import android.graphics.Paint;
//������Block   ImageRect����������Լ�ͼƬ
import android.graphics.Canvas;
import android.view.Window;
import app.view.GameView;

public class BlockGroup {
	
	Random r;
	public int[][] steps; 
	
	public int mylevel;//�Ѷ�ϵ��
	public int mylevel2;//�Ѷ�ϵ������
	public int whiteB;//�׸���id
	ImageRect[] imageRects = null;//ͼƬ����
	Block [][]block;//��������
/**
 * 
 * @param image
 * @param level
 */
 
	public BlockGroup(Bitmap image,int level) {
		r = new Random();
		mylevel = level;
		mylevel2 = level*level;
		whiteB = mylevel2-1;
		imageRects = new ImageRect[mylevel2];
		for (int i = 0; i < mylevel2; i++) {
			imageRects[i] = new ImageRect(image, level, i);
		}
		block = new Block[mylevel][mylevel];
		for (int i = 0; i < mylevel2; i++) {
			//��ά���鸳ֵ�·���
			block[i/mylevel][i%mylevel] = new Block(level,i);
			block[i/mylevel][i%mylevel].init();
		}
	}
	/**
	 * ����ͼƬ
	 * @param canvas
	 * @param x
	 * @param y
	 * @param paint
	 */
	public void paint(Canvas canvas,int x,int y,Paint paint) {
		for (int i = 0; i < mylevel2; i++) {
			//��ȡ���Ӷ���Block����
			Block tempB = block[i/mylevel][i%mylevel];
			imageRects[tempB.id].paint(canvas, x+tempB.x, y+tempB.y, null);
		}
	}
	
	public boolean onClick(float x,float y){
		for (int i = 0; i < mylevel2; i++) {
			Block tempB = block[i / mylevel][i % mylevel];
			if(tempB.onClick(x, y)){
				if(checkBlock(tempB)){
					return checkWin();
				}else return false;

			}
		}
		return false;
	}
	public boolean checkWin() {
		for (int i = 0; i < mylevel2; i++) {
			Block tempB = block[i / mylevel][i % mylevel];
			if(tempB.id!=i)return false;
		}	
		return true;
	}

	private boolean checkBlock(Block blog) {
		if(blog.xline>0 && (block[blog.yline][blog.xline-1].id == whiteB)) {
			int maopao = blog.id;
			blog.id = whiteB;
			block[blog.yline][blog.xline-1].id = maopao;
			addSteps(blog,block[blog.yline][blog.xline-1]);
			return true;
		}
		if(blog.xline<mylevel-1 && (block[blog.yline][blog.xline+1].id == whiteB)) {
			int maopao = blog.id;
			blog.id = whiteB;
			block[blog.yline][blog.xline+1].id = maopao;
			addSteps(blog,block[blog.yline][blog.xline+1]);
			return true;
		}
		if(blog.yline>0 && (block[blog.yline-1][blog.xline].id == whiteB)) {
			int maopao = blog.id;
			blog.id = whiteB;
			block[blog.yline-1][blog.xline].id = maopao;
			addSteps(blog,block[blog.yline-1][blog.xline]);
			return true;
		}
		if(blog.yline<mylevel-1 && (block[blog.yline+1][blog.xline].id == whiteB)) {
			int maopao = blog.id;
			blog.id = whiteB;
			block[blog.yline+1][blog.xline].id = maopao;
			addSteps(blog,block[blog.yline+1][blog.xline]);
			return true;
		}
		return false;
	}
	//������Ӽ�¼������ӺͰ�ɫ���ӵ�����
	private void addSteps(Block clickBlock, Block whiteBlock) {
		if(steps==null){
			steps = new int[1][2];
			steps[0][0] = clickBlock.xline+clickBlock.yline*mylevel;
			steps[0][1] = whiteBlock.xline+whiteBlock.yline*mylevel;
		}else{
			steps = addStep(clickBlock.xline+clickBlock.yline*mylevel,
					whiteBlock.xline+whiteBlock.yline*mylevel);
		}
		
	}
//��¼ÿһ���ƶ�����
	private int[][] addStep(int num1, int num2) {
		int temp[][] = new int[steps.length+1][];
		for(int i = 0;i<steps.length;i++){
			temp[i] = new int[steps[i].length];
			for(int j = 0;j<steps[i].length;j++){
				temp[i][j] = steps[i][j];
			}
		}
		temp[steps.length] = new int[2];
		temp[steps.length][0] = num1;
		temp[steps.length][1] = num2;
		return temp;
	}
	//ˢ�¸���
	public void flushBlicks(int num){
		for(int i = 0;i<num;i++){
			autoChange();
		}
	}

	
	//��������
		private void autoChange(){
			for (int i = 0; i < mylevel2; i++) {
				Block tempB = block[i / mylevel][i % mylevel];
				if(tempB.id == whiteB){
	//				while(true){
						int index = r.nextInt(4);
						switch (index) {
						case 0:
							if(tempB.xline>0){
								int tempNum = tempB.id;
								tempB.id = block[tempB.yline][tempB.xline-1].id;
								block[tempB.yline][tempB.xline-1].id = tempNum;
								return;
							}
							break;
						case 1:
							if(tempB.xline<mylevel-1){
								int tempNum = tempB.id;
								tempB.id = block[tempB.yline][tempB.xline+1].id;
								block[tempB.yline][tempB.xline+1].id = tempNum;
								return;
							}
							break;
						case 2:
							if(tempB.yline>0){
								int tempNum = tempB.id;
								tempB.id = block[tempB.yline - 1][tempB.xline].id;
								block[tempB.yline - 1][tempB.xline].id = tempNum;
								return;
							}
							break;
						case 3:
							if(tempB.yline<mylevel-1){
								int tempNum = tempB.id;
								tempB.id = block[tempB.yline+1][tempB.xline].id;
								block[tempB.yline+1][tempB.xline].id = tempNum;
								return;
							}
							break;
						}
					}
				}
//			}
			
		}
		//����Ϊ���Һ�Ľ���
		public void setmap(int[] map){
			for (int i = 0; i < mylevel2; i++) {
				Block tempB = block[i / mylevel][i % mylevel];
				tempB.id = map[i];
			}
		}
		//��ȡ���Һ���ӵ�id
		public int[] getmap(){
			int[] map = new int[mylevel2];
			for (int i = 0; i < mylevel2; i++) {
				Block tempB = block[i / mylevel][i % mylevel];
				map[i] = tempB.id;
			}
			return map;
		}

	//�ƶ�����
		/**
		 * 
		 * @param i:  clickBlock.xline+clickBlock.yline*mylevel;
		 * @param j:  whiteBlock.xline+whiteBlock.yline*mylevel;
		 */
	public void doMove(int i,int j) {
		int temp = block[i/mylevel][i%mylevel].id;
		block[i/mylevel][i%mylevel].id = block[j/mylevel][j%mylevel].id;
		block[j/mylevel][j%mylevel].id = temp;
	}
	
	
}
