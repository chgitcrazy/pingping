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
		//����ͼƬ
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
				// ����Ѿ�׼�����������Ե��� show() �������Ź��
				if(OWRewardedAd.isReady()){
					OWRewardedAd.show(GameActivity.this, "tag");
				}
			}

			@Override
			public void onAdShow(String tag) {
				// ����Ѿ���ʼ����
			}

			@Override
			public void onAdClick(String tag) {
				// ������¼�
			}

			@Override
			public void onAdClose(String tag, OnewayAdCloseType type) {
				// ���ҳ�汻�رգ� type ö��˵���˹رյ�����:
				//  ERROR(��Ƶ����ʧ��), SKIPPED(ֻ�Բ�����Ƶ���û������������ť), COMPLETED(��Ƶ�����������)
			}

			@Override
			public void onAdFinish(String s, OnewayAdCloseType onewayAdCloseType) {

			}

			@Override
			public void onSdkError(OnewaySdkError error, String message) {
				// SDK ��ʼ�����߹�沥�Ź����г��ִ��󣬿����ڴ˱��������־���Ա��Ų����ԭ��
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
		//ȥ��������
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ����Ϣ��
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
		menu.add(0, 0, 0, "������Ϸ");
		menu.add(0, 1, 0, "�����˳�");
		menu.add(0, 2, 0, "��Ϸ����");
		menu.add(0, 3, 0, "��Ϸ����");
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
			new AlertDialog.Builder(this).setTitle("����ѡ��or�˳���Ϸ").
			setMessage("�Ƿ���ѡ�ػ����˳���Ϸ").
			setPositiveButton("����ѡ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(GameActivity.this,SelectGame.class);
					startActivity(intent);
				}
			} ).setNegativeButton("�˳���Ϸ", new DialogInterface.OnClickListener() {
				
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
