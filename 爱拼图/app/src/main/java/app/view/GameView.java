package app.view;

import incision.Block;
import incision.BlockGroup;

import com.activity.GameActivity;
import com.activity.HelpActivity;
import com.activity.SelectGame;
import com.activity.R;

import android.R.color;
import android.R.menu;
import android.R.string;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.ImageView;

public class GameView extends SurfaceView implements Runnable{
	
	//������Ϸ״̬
	public  int gamestart;
	public  final int GAMESTATR_READY=0;//���ҽ׶�
	public  final int GAMESTATR_RUN=1;//���н׶�
	public  final int GAMESTATR_WIN=2;//ʤ���׶�
	public  final int GAMESTATR_SHOW=3;//��ʾԭͼ�׶�
	public  final int GAMESTATR_REPLAY=4;//�طý׶�
	long startTime;
	Bitmap currentBitmap;
	Bitmap bigbBitmap;
	Bitmap smallBitmap;
	Bitmap clockBitmap;
	Bitmap replaybBitmap;
	RectF smallBitmapF;
	RectF backBitmaprRectF;
	BlockGroup blockGroup;
	
	int replayStart[];//��¼���Һ�ÿ������id
	int replayState;//�ط�״̬
	int replayCount;//�ط��ܲ���
	int repalyCurrentStep;//��ǰ����
	BlockGroup rBlockGroup =null;//���ƻطŸ���

	public GameView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub	

		//����ͼƬ
		currentBitmap =BitmapFactory.decodeResource(getResources(), SelectGame.iamges[SelectGame.IMAGEINDEX]);
		clockBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.k);
		replaybBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.up);
		//��ͼƬ��������
		bigbBitmap = Bitmap.createScaledBitmap(currentBitmap, GameActivity.SCREENWIDTH, (GameActivity.SCREENHEIGH-100), true);
		smallBitmap = Bitmap.createScaledBitmap(currentBitmap, 100, 100, true);
		smallBitmapF=new RectF(0, 0, 100, 100);
		backBitmaprRectF =new RectF(210,50,replaybBitmap.getWidth()+200,replaybBitmap.getHeight()+50);
		blockGroup = new BlockGroup(bigbBitmap, SelectGame.levelIndex);
		rBlockGroup = new BlockGroup(bigbBitmap, SelectGame.levelIndex);
		Thread thread = new Thread(this);
		thread.start();
	}
	//����״̬
	Paint paint = new Paint();
	public void readerReady(Canvas canvas) {
		//��ȡ��ʼʱ��
		
		paint.setColor(Color.YELLOW);
		paint.setTextSize(26);		
		canvas.drawText(renderText, 20, 70, paint);	
		blockGroup.paint(canvas, 0, 100, null);
	}
	//��ʼʱ����
	public void startGame(Canvas canvas) {
				
	    canvas.drawBitmap(smallBitmap, 0, 0,null);
		canvas.drawBitmap(clockBitmap, 210, 0,null);
		canvas.drawBitmap(replaybBitmap, 210, 50,null);
		paint.setColor(Color.YELLOW);
		paint.setTextSize(26);		
		canvas.drawText(currentTime(), 270, 32, paint);
		blockGroup.paint(canvas, 0, 100, null);		
	}
	//��Ϸʤ�����淽����
	String winString = "��Ϸ��ɣ���ϲ����ʤ��������";
	public void gameWin(Canvas canvas){
	
		canvas.drawBitmap(bigbBitmap, 0, 0,null);
		canvas.drawText(winString, 50, bigbBitmap.getHeight()+40, paint);		
	}
	//���Сͼ����ִ�ͼ��ʾ
	public void gameShow(Canvas canvas) {
		canvas.drawBitmap(bigbBitmap, 0,10, null);
	}
	//���ƻط�ͼƬ
	public void gameReplay(Canvas canvas) {
		rBlockGroup.paint(canvas, 0, 100, null);
	}
	//ˢ�½���
	int score;
	int start =0;
	int end = 30;
	String normal = "���ڴ�����";
	String renderText ;
	int count;
	private void update() {
		if (start<end) {
			renderText =normal;
			for (int i=0;i<=start%3;i++) {
			renderText +=".";					
			}	
			start++;
			blockGroup.flushBlicks(SelectGame.levelIndex);	
		}else {	
			switch (gamestart) {
			case GAMESTATR_READY:
				
				gamestart = GAMESTATR_RUN;
		//		start=0;
				startTime = System.currentTimeMillis();
				replayStart = blockGroup.getmap();
				
				break;
			case GAMESTATR_WIN:
				count++;
				if (count>60) {	
				isFinish = true ;
				GameActivity.gameActivity.finishGame(score);
//				count=0;
//					gamestart=GAMESTATR_READY;
				}
				break;
			case GAMESTATR_REPLAY:
				replayCount++;
				if (replayCount>9) {
					if (replayCount%2 == 0&&repalyCurrentStep<blockGroup.steps.length) {
						rBlockGroup.doMove(blockGroup.steps[repalyCurrentStep][0], blockGroup.steps[repalyCurrentStep][1]);
						repalyCurrentStep++;					
					}
						else if (repalyCurrentStep>=blockGroup.steps.length) {
						if (replayState == GAMESTATR_RUN) {
							gamestart = GAMESTATR_RUN;
						}else {
							count =0;
							score = (int)((System.currentTimeMillis() - startTime)/1000);
							gamestart = GAMESTATR_WIN;
						}				
					} 										
					//gamestart=GAMESTATR_READY;
				}
				break;
			}
		}
	}
	
	
	public void render(){				
		//surfaceholder����surfaceview
		SurfaceHolder holder = this.getHolder();
		//��������
		Canvas canvas = holder.lockCanvas();
		if (canvas == null) {
			return;
		}
		canvas.drawColor(Color.BLACK);
		switch (gamestart) {
		case GAMESTATR_READY:			
			readerReady(canvas);
			break;
		case GAMESTATR_RUN:
			startGame(canvas);	
			break;
		case GAMESTATR_WIN:
			gameWin(canvas);
			break;
		case GAMESTATR_SHOW:
			gameShow(canvas);
			break;
		case GAMESTATR_REPLAY:
	        gameReplay(canvas);
			break;
	
		}	
		//��������
		holder.unlockCanvasAndPost(canvas);			
		
	}
	
	//��ȡʱ��
	public String currentTime(){
		//System.out.println(System.currentTimeMillis()+"--"+startTime);
		long useTime =(System.currentTimeMillis()-startTime)/1000;
		long millis = useTime%60;
		long seconds = (useTime/60)%60;
		long hour = useTime/3600;
		return (hour<10?"0"+hour:hour)+":"+(seconds<10?"0"+seconds:seconds)+":"+(millis<10?"0"+millis:millis);
		
	}
	
	//������Ϸ���¼�����
	int eventType;
	public static final int EVENT_NON = 0;
	public static final int EVENT_TOUCHDOWN = 1;
	public static final int EVENT_TOUCHUP = 2;
	
	public void event() {
		//�ж��¼�����
		if (eventType == EVENT_NON) {
			return;
		}
		switch (eventType) {
		case EVENT_TOUCHDOWN:
			//ִ�е���¼�
			if(blockGroup.onClick(touchX, touchY-100)){			
				if (blockGroup.steps != null) {
					rBlockGroup.setmap(replayStart);
					gamestart = GAMESTATR_REPLAY;
					replayCount = 0;
					repalyCurrentStep = 0;
//					replayState = GAMESTATR_WIN;	
				}		
			}
			eventType = EVENT_NON;
			
			break;
		case EVENT_TOUCHUP:
			switch (gamestart) {
			case GAMESTATR_RUN:
				if (smallBitmapF.contains(touchX, touchY)) {
					gamestart =GAMESTATR_SHOW;
				}else if (backBitmaprRectF.contains(touchX, touchY)) {
					//���ûص����Һ��״̬
//					rBlockGroup.setmap(replayStart);
//					replayState =GAMESTATR_RUN;
//					gamestart = GAMESTATR_REPLAY;
					if (blockGroup.steps != null) {
						rBlockGroup.setmap(replayStart);
						gamestart = GAMESTATR_REPLAY;
//						replayCount = 0;
//						repalyCurrentStep = 0;
						replayState =GAMESTATR_RUN;
					}
				}
				break;
			case GAMESTATR_SHOW:
				gamestart = GAMESTATR_RUN;
				break;
//			case GAMESTATR_REPLAY:
//				
//				break;
//
			}
			eventType = EVENT_NON;
			break;


		default:
			break;
		}
		
		
		
	}
	
	//�ص��ӿڣ������¼������������� ̧�� �ƶ�
	//����true��
	//����false��
	float touchX,touchY;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		//�жϴ�������
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			eventType = EVENT_TOUCHDOWN;
			//��ȡ��ǰX��Y����
			touchX = event.getX();
			touchY = event.getY();
			return true;
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			eventType = EVENT_TOUCHUP;
			//��ȡ��ǰX��Y����
			touchX = event.getX();
			touchY = event.getY();
			return true;
		}
	
		return super.onTouchEvent(event);
	}
	
	
	public  boolean isFinish;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!isFinish){
			//�¼�����
			event();
			//ˢ�½���
			update();
			//����ͼƬ
			render();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


}
