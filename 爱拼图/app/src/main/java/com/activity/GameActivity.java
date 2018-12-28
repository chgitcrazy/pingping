package com.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import app.rank.Rank;
import app.view.GameView;
import mobi.oneway.sdk.OWRewardedAd;
import mobi.oneway.sdk.OWRewardedAdListener;
import mobi.oneway.sdk.OnewayAdCloseType;
import mobi.oneway.sdk.OnewaySdk;
import mobi.oneway.sdk.OnewaySdkError;

public class GameActivity extends Activity {
	GameView gameView;
	public static int SCREENWIDTH;
	public static int SCREENHEIGH;
	public static GameActivity gameActivity;
	public static int currentScore;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setFullScreen();
		gameActivity = this;
		//拉伸图片
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
		SCREENWIDTH = metrics.widthPixels;
		SCREENHEIGH = metrics.heightPixels;
		 gameView = new GameView(this);
		setContentView(gameView);

		OnewaySdk.configure(this, "9c87042bac3a4d29");
		OWRewardedAd.init(new OWRewardedAdListener() {
			@Override
			public void onAdReady() {
				// 广告已经准备就绪，可以调用 show() 方法播放广告
				if(OWRewardedAd.isReady()){
					OWRewardedAd.show(GameActivity.this, "tag");
				}
			}

			@Override
			public void onAdShow(String tag) {
				// 广告已经开始播放
			}

			@Override
			public void onAdClick(String tag) {
				// 广告点击事件
			}

			@Override
			public void onAdClose(String tag, OnewayAdCloseType type) {
				// 广告页面被关闭， type 枚举说明了关闭的类型:
				//  ERROR(视频播放失败), SKIPPED(只对插屏视频，用户点击了跳过按钮), COMPLETED(视频正常播放完成)
			}

			@Override
			public void onAdFinish(String s, OnewayAdCloseType onewayAdCloseType) {

			}

			@Override
			public void onSdkError(OnewaySdkError error, String message) {
				// SDK 初始化或者广告播放过程中出现错误，可以在此保存错误日志，以便排查错误原因
			}
		});

	}
	public  void finishGame(int score) {
		if (score < Rank.scores[SelectGame.levelIndex - 3][9]) {

			currentScore = score;
			Intent intent = new Intent(GameActivity.this, InputName.class);
			startActivity(intent);

		}
		finish();
	}
	
	public  void setFullScreen() {
		//去除标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去除信息栏
		Window window = getWindow();
		window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
	}
	public boolean onKeyDown(int keyCode,KeyEvent event) {
		if (keyCode ==KeyEvent.KEYCODE_BACK) {
//			gameView.isFinish = true;
			finish();	
		}
		return false;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
//		return super.onCreateOptionsMenu(menu);
		menu.add(0, 0, 0, "继续游戏");
		menu.add(0, 1, 0, "返回退出");
		menu.add(0, 2, 0, "游戏帮助");
		menu.add(0, 3, 0, "游戏设置");
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
//		return super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case 0:
			return true;
		case 1:
			new AlertDialog.Builder(this).setTitle("返回选关or退出游戏").
			setMessage("是返回选关还是退出游戏").
			setPositiveButton("返回选关", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(GameActivity.this,SelectGame.class);
					startActivity(intent);
				}
			} ).setNegativeButton("退出游戏", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					finish();
				}
			}).create().show();
			return true;
		case 2:
			Intent intent1 = new Intent();
	       intent1.setClass(GameActivity.this, HelpActivity.class);
	       startActivity(intent1);
	       return true;
		case 3:
			Intent intent2 = new Intent(GameActivity.this,OptionActivity.class);
			startActivity(intent2);
			return true;
		}
		return false;

	}
	
	
	
	

}
