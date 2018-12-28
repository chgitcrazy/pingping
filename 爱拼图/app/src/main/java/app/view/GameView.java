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
	
	//定义游戏状态
	public  int gamestart;
	public  final int GAMESTATR_READY=0;//打乱阶段
	public  final int GAMESTATR_RUN=1;//运行阶段
	public  final int GAMESTATR_WIN=2;//胜利阶段
	public  final int GAMESTATR_SHOW=3;//显示原图阶段
	public  final int GAMESTATR_REPLAY=4;//回访阶段
	long startTime;
	Bitmap currentBitmap;
	Bitmap bigbBitmap;
	Bitmap smallBitmap;
	Bitmap clockBitmap;
	Bitmap replaybBitmap;
	RectF smallBitmapF;
	RectF backBitmaprRectF;
	BlockGroup blockGroup;
	
	int replayStart[];//记录打乱后每个格子id
	int replayState;//回放状态
	int replayCount;//回放总步数
	int repalyCurrentStep;//当前步骤
	BlockGroup rBlockGroup =null;//绘制回放格子

	public GameView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub	

		//绘制图片
		currentBitmap =BitmapFactory.decodeResource(getResources(), SelectGame.iamges[SelectGame.IMAGEINDEX]);
		clockBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.k);
		replaybBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.up);
		//对图片进行伸缩
		bigbBitmap = Bitmap.createScaledBitmap(currentBitmap, GameActivity.SCREENWIDTH, (GameActivity.SCREENHEIGH-100), true);
		smallBitmap = Bitmap.createScaledBitmap(currentBitmap, 100, 100, true);
		smallBitmapF=new RectF(0, 0, 100, 100);
		backBitmaprRectF =new RectF(210,50,replaybBitmap.getWidth()+200,replaybBitmap.getHeight()+50);
		blockGroup = new BlockGroup(bigbBitmap, SelectGame.levelIndex);
		rBlockGroup = new BlockGroup(bigbBitmap, SelectGame.levelIndex);
		Thread thread = new Thread(this);
		thread.start();
	}
	//打乱状态
	Paint paint = new Paint();
	public void readerReady(Canvas canvas) {
		//获取开始时间
		
		paint.setColor(Color.YELLOW);
		paint.setTextSize(26);		
		canvas.drawText(renderText, 20, 70, paint);	
		blockGroup.paint(canvas, 0, 100, null);
	}
	//开始时界面
	public void startGame(Canvas canvas) {
				
	    canvas.drawBitmap(smallBitmap, 0, 0,null);
		canvas.drawBitmap(clockBitmap, 210, 0,null);
		canvas.drawBitmap(replaybBitmap, 210, 50,null);
		paint.setColor(Color.YELLOW);
		paint.setTextSize(26);		
		canvas.drawText(currentTime(), 270, 32, paint);
		blockGroup.paint(canvas, 0, 100, null);		
	}
	//游戏胜利界面方法：
	String winString = "游戏完成！恭喜你获得胜利！！！";
	public void gameWin(Canvas canvas){
	
		canvas.drawBitmap(bigbBitmap, 0, 0,null);
		canvas.drawText(winString, 50, bigbBitmap.getHeight()+40, paint);		
	}
	//点击小图后出现大图提示
	public void gameShow(Canvas canvas) {
		canvas.drawBitmap(bigbBitmap, 0,10, null);
	}
	//绘制回放图片
	public void gameReplay(Canvas canvas) {
		rBlockGroup.paint(canvas, 0, 100, null);
	}
	//刷新界面
	int score;
	int start =0;
	int end = 30;
	String normal = "正在打乱中";
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
		//surfaceholder控制surfaceview
		SurfaceHolder holder = this.getHolder();
		//锁定画布
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
		//解锁画布
		holder.unlockCanvasAndPost(canvas);			
		
	}
	
	//获取时间
	public String currentTime(){
		//System.out.println(System.currentTimeMillis()+"--"+startTime);
		long useTime =(System.currentTimeMillis()-startTime)/1000;
		long millis = useTime%60;
		long seconds = (useTime/60)%60;
		long hour = useTime/3600;
		return (hour<10?"0"+hour:hour)+":"+(seconds<10?"0"+seconds:seconds)+":"+(millis<10?"0"+millis:millis);
		
	}
	
	//处理游戏中事件类型
	int eventType;
	public static final int EVENT_NON = 0;
	public static final int EVENT_TOUCHDOWN = 1;
	public static final int EVENT_TOUCHUP = 2;
	
	public void event() {
		//判断事件类型
		if (eventType == EVENT_NON) {
			return;
		}
		switch (eventType) {
		case EVENT_TOUCHDOWN:
			//执行点击事件
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
					//设置回到打乱后的状态
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
	
	//回调接口，触摸事件，动作：按下 抬起 移动
	//返回true：
	//返回false：
	float touchX,touchY;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		//判断触摸动作
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			eventType = EVENT_TOUCHDOWN;
			//获取当前X，Y坐标
			touchX = event.getX();
			touchY = event.getY();
			return true;
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			eventType = EVENT_TOUCHUP;
			//获取当前X，Y坐标
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
			//事件处理
			event();
			//刷新界面
			update();
			//绘制图片
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
