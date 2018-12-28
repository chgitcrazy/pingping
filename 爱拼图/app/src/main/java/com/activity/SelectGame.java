package com.activity;

import com.activity.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class SelectGame extends Activity {
	
	Gallery gallery;
	public static int iamges [] = {R.drawable.b0,R.drawable.b1,R.drawable.b2,R.drawable.b3,R.drawable.b4,R.drawable.b5,R.drawable.b6,R.drawable.b7,R.drawable.b8,R.drawable.b9};	
	public static int IMAGEINDEX ;
	public static int levelIndex ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setFullScreen();
		setContentView(R.layout.select);	
		gallery = (Gallery) findViewById(R.id.gallery1);
		myAdapter adapter = new myAdapter();
		gallery.setAdapter(adapter);
		gallery.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
				// TODO Auto-generated method stub
				IMAGEINDEX = position;
				new AlertDialog.Builder(SelectGame.this).setTitle("难度模式").setIcon(R.drawable.about).
				setPositiveButton("困难",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						levelIndex = 5;
						startGame();
					}
				} ).setNegativeButton("简单", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						levelIndex = 3;
						startGame();
					}
				}).setNeutralButton("普通", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						levelIndex = 4;
						startGame();
					}
				}).create().show();
			}
		});
	}
	//设置全屏
	public void setFullScreen(){
		//去掉标题栏
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    	//去除信息栏
    	Window window = this.getWindow();
    	window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
	}
	public void startGame(){
		Intent intent = new Intent(SelectGame.this,GameActivity.class);
	
		startActivity(intent);
		
		
	}
	
	public class myAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return iamges.length;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View view, ViewGroup viewGroup) {
			// TODO Auto-generated method stub
			ImageView imageView = new ImageView(SelectGame.this);
			imageView.setImageResource(iamges[position]);
			return imageView;
			
		}
		
	}
	
	
	
	

}
