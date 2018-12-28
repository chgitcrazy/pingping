package com.activity;

import com.activity.R;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


public class HelpActivity extends ListActivity{
	
	private String[] mTitles = { "游戏操作", "游戏系统" };
	private String[] mDialogue = {
			"\n一、基本操作\n" + "点击欲移动格子进行移动.\n"+ "点击缩略图查看原图。\n"+"点击播放图标重放游戏步骤。\n"
					+ "游戏中按手机返回键:弹出菜单中选择返回选关界面;\n\n"
					+ "二、难度设定\n" + "游戏根据简单，普通，困难三个难度将拼图分割为3*3,4*4,5*5三种样式。\n\n",				
			"\n在主菜单点击积分排名按钮可查看每个难度下过关的最短时间。\n",

	};	
	//定义布尔型数组mExpanded
	private boolean[] mExpanded = { false, false};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//设置全屏
		setFullScreen();
		//设置背景
		//设置背景图片
		Drawable drawable = getResources().getDrawable(R.drawable.helpback);
		this.getWindow().setBackgroundDrawable(drawable);
		this.getListView().setCacheColorHint(Color.TRANSPARENT);
		MyAdapter adapter = new MyAdapter();
		this.setListAdapter(adapter);
    	
    }
	public  void setFullScreen(){
    	//去掉标题栏
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    	//去除信息栏
    	Window window = this.getWindow();
    	window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	
	}
	
	
	
	public class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mTitles.length;
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
			HelpView hv = null;
			if (view==null) {
				hv= new HelpView(HelpActivity.this, mTitles[position], mDialogue[position], mExpanded[position]);
				
			}else {
				hv = (HelpView) view;
				hv.setTitle(mTitles[position]);
				hv.setDialog(mDialogue[position]);
				hv.setExpand(mExpanded[position]);
			}
			
			return hv;
		}
		public void toggle(int position) {
			mExpanded[position]=!mExpanded[position];
			//刷新listview界面
			notifyDataSetChanged();
			
		}
		
	}
	
		
	public class HelpView extends LinearLayout{
		TextView title;
		TextView dialog;
		public HelpView(Context context,String mtitle,String mdialog,Boolean mexpand) {
			super(context);
			//设置listview的显示方式为纵向
			this.setOrientation(VERTICAL);
			// TODO Auto-generated constructor stub
		title = new TextView(context);
		title.setTextColor(Color.BLACK);
		title.setTextSize(30);
		title.setText(mtitle);
		//将TextView添加到LinearLayout
		this.addView(title, new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.FILL_PARENT));
		
		dialog = new TextView(context);
		dialog.setTextColor(Color.RED);
		dialog.setTextSize(16);
		dialog.setText(mdialog);	
		this.addView(dialog, new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.FILL_PARENT));
		dialog.setVisibility(mexpand?VISIBLE:GONE);
		
	}
	
	public void setTitle(String title1){
		title.setText(title1);
	}
	public void setDialog(String dialog1){
		dialog.setText(dialog1);
	}
	public void setExpand(Boolean expand){
		dialog.setVisibility(expand?VISIBLE:GONE);
	}
}
	 @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		((MyAdapter)this.getListAdapter()).toggle(position);
		
	}
	
	 public boolean onKeyDown(int keyCode,KeyEvent event) {
			if (keyCode ==KeyEvent.KEYCODE_BACK) {
				finish();	
			return true;
			}
			return false;
		}
	
}
