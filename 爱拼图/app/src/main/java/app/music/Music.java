package app.music;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


import android.content.Context;
import android.media.MediaPlayer;
   
//对音乐进行操作
public class Music {
	static MediaPlayer player = null;
	static Context mContext;//定义上下文对象
	public static void start(Context context,int musicid,boolean loop){
		if (mContext == null) {
			mContext = context;
		}
				
		if (player!=null&&player.isPlaying()) {
			player.reset();
			player = null;
		}
		player = MediaPlayer.create(context, musicid);	
		if (loop) {
			//设置循环播放
			player.setLooping(true);
		}
		player.start();
	}
	//判断音乐是否正在播放
	public static boolean playing(){
		if (player!=null&&player.isPlaying()) {
			return true;
		}
		return false;
		
	}
	//停止音乐
	public static void stop(){
		if (player.isPlaying()) {
			player.stop();
		}
	}
	public static boolean musicState;//音乐状态
	static int MyVolume;//音乐值
	//保存音乐状态，音乐值
	public static void saveMusic(int volume){
		FileOutputStream fos =null;
		DataOutputStream dos = null;
		try {
			fos = mContext.openFileOutput("zxsc.doc", Context.MODE_PRIVATE);
			dos = new DataOutputStream(fos);
			dos.writeBoolean(musicState);
			dos.writeInt(volume);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				
				dos.close();
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	//加载音乐数据
	public static void loadMusic(){
		FileInputStream fis = null;
		DataInputStream dis = null;
		try {
			if (mContext.openFileInput("zxsc.doc")!=null) {
				fis = mContext.openFileInput("zxsc.doc");
				dis = new DataInputStream(fis);
				musicState = dis.readBoolean();
				MyVolume = dis.readInt();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if (mContext.openFileInput("zxsc.doc")!=null) {
				dis.close();
				fis.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
