package com.activity;

import com.activity.R;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import app.music.Music;

public class OptionActivity extends Activity implements OnClickListener {
	ImageView imageView;
	ImageView imageView1;
	ImageView imageView2;
	ImageView imageView3;
	ImageView imageView4;
	Button button;
	Button button2;
	SeekBar sb;
	AudioManager am = null;
	int MyVolume = 0;//音量值
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setFullScreen();
		setContentView(R.layout.option);
		
		imageView  = (ImageView) findViewById(R.id.iv1);
		imageView1 = (ImageView) findViewById(R.id.iv2);
		imageView2 = (ImageView) findViewById(R.id.iv3);
		imageView3 = (ImageView) findViewById(R.id.iv4);
		imageView4 = (ImageView) findViewById(R.id.iv5);
		button = (Button) findViewById(R.id.bu1);
		button2 = (Button) findViewById(R.id.bu2);
		sb = (SeekBar) findViewById(R.id.seekBar1);
		am = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
	    click();
	    //设置进度条最大值
	    sb.setMax(am.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
	    //设置进度条当前值
	    sb.setProgress(am.getStreamVolume(AudioManager.STREAM_MUSIC));
	    sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			//进度条停止拖动调用
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
			//进度条开始时改变
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
			//进度条进度改变时调用
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean arg2) {
				// TODO Auto-generated method stub
				//获取当前音量值
				am.setStreamVolume(AudioManager.STREAM_MUSIC, progress, AudioManager.FLAG_SHOW_UI);
				MyVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
				Music.saveMusic(MyVolume);
			}
		});
	}
	//设置全屏
    public  void setFullScreen(){
    	//去掉标题栏
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    	//去除信息栏
    	Window window = this.getWindow();
    	window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);	
    }
    
	public void click(){
		imageView1.setOnClickListener(this);
		imageView2.setOnClickListener(this);
		imageView3.setOnClickListener(this);
		imageView4.setOnClickListener(this);
		button.setOnClickListener(this);
		button2.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		//boolean singo = true;

		switch (view.getId()) {
		case R.id.iv2:
			setOnMusic();
			break;
		case R.id.iv3:
			setOffMusic();
			break;
		case R.id.iv4:	
			am.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);	
			sb.setProgress(am.getStreamVolume(AudioManager.STREAM_MUSIC));
			break;

		case R.id.iv5:
			am.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);				
			sb.setProgress(am.getStreamVolume(AudioManager.STREAM_MUSIC));
			break;			
			
		case R.id.bu2:
			finish();
			break;

		default:
			break;
		}
	}
	//设置音乐开启
	public void setOnMusic() {
		imageView.setImageResource(R.drawable.audio_on);
		//设置音乐状态，开启音乐
		Music.musicState = true;
		Music.start(this, R.raw.love, true);
	}
	//设置音乐关闭
	public void setOffMusic() {
		imageView.setImageResource(R.drawable.audio_off);
		Music.musicState =false;
		Music.stop();
	}
	public boolean onKeyDown(int keyCode,KeyEvent event) {
		if (keyCode ==KeyEvent.KEYCODE_BACK) {
			finish();	
		return true;
		}
		return false;
	}
	
}
