package com.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import app.music.Music;
import app.rank.Rank;

public class InputName extends Activity{
	EditText name = null;
	Button finish = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setFullScreen();
		setContentView(R.layout.inputname);
		name = (EditText) findViewById(R.id.name);
		name.setTextKeepState("武鹏");
		finish = (Button) findViewById(R.id.finish);
		
		finish.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				System.out.println("SelectGame.levelIndex="+SelectGame.levelIndex);

				// TODO Auto-generated method stub
				if (name.getText().toString().equals("")) {
					Rank.playerName = "无名氏";
					Rank.names[SelectGame.levelIndex-3][9] = "无名氏";
				} else {
					Rank.playerName = name.getText().toString();
//					Toast.makeText(InputName.this, SelectGame.levelIndex+"", 0).show();
					Rank.names[SelectGame.levelIndex-3][9] = name.getText()
							.toString();
				}
				Rank.scores[SelectGame.levelIndex-3][9] = GameActivity.currentScore;
				Rank.sort(SelectGame.levelIndex-3);
				Rank.saveRank(InputName.this);
				Music.stop();
//				Intent intent = new Intent(InputName.this,MenuActivity.class);
//				startActivity(intent);
				finish();
			}
		});
	}
	public void setFullScreen(){
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Window window = this.getWindow();
		window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
