package com.activity;


import com.activity.R;

import android.R.menu;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData.Item;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import app.music.Music;

public class MenuActivity extends Activity{
	Button button1;
	Button button2;
	Button button3;
	Button button4;
	Button button5;
	Button button6;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setFullScreen();
	    setContentView(R.layout.menu);
	    Music.start(this, R.raw.love, true);
	    button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);
		button4 = (Button) findViewById(R.id.button4);
		button5 = (Button) findViewById(R.id.button5);
		button6 = (Button) findViewById(R.id.button6);

		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MenuActivity.this,SelectGame.class);
				startActivity(intent);
			}
		});
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent();
				intent.setClass(MenuActivity.this, RankActivity.class);
				startActivity(intent);
			}
		});


	button3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MenuActivity.this,OptionActivity.class);
				MenuActivity.this.startActivity(intent);
			}
		});

		button4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MenuActivity.this,HelpActivity.class);
				MenuActivity.this.startActivity(intent);
			}
		});

		button5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(MenuActivity.this).setTitle("游戏版权")
				.setMessage("版权所有，禁止侵权").setIcon(R.drawable.icon).
				setPositiveButton("返回", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub

					}
				}).create().show();
			}
		});

		button6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(MenuActivity.this).
				setTitle("退出游戏").setIcon(R.drawable.icon).
				setMessage("确定退出游戏吗!!").
				setPositiveButton("确定", new DialogInterface.OnClickListener() {


					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						finish();
					}
				}).
				setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub

					}
				}).create().show();
			}

		});
	}
	public  void setFullScreen(){
    	//去掉标题栏
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    	//去除信息栏
    	Window window = this.getWindow();
    	window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE)	;
		}
		else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
		}
	}

}
