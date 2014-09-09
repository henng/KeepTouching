package com.henryluo.keeptouching;


import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.Math;
import java.util.HashMap;

public class MainActivity extends ActionBarActivity {

	private static final int RESULT_SETTINGS = 1;
	
	private Chronometer time_display;
	private Button startButton;
	
	private TextView userName, gameMode;
	
	int xFirst, yFirst = 0;
	
	private SoundPool sp;//声明一个SoundPool   
    private int music;//定义一个整型用load（）；来设置suondID    
    HashMap<Integer, Integer> soundPoolMap = new HashMap<Integer, Integer>();
    
	SharedPreferences sharedPrefs;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		time_display = (Chronometer) findViewById(R.id.time_display);
		startButton = (Button) findViewById(R.id.start);
		
		//startButton.setOnClickListener(new btnOnClickListener() );
		startButton.setOnTouchListener(new normalListener() );

		userName = (TextView) findViewById(R.id.game_username);
		gameMode = (TextView) findViewById(R.id.game_mode);
		
    	sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
    	userName.setText(sharedPrefs.getString("prefUserName", "无名大侠"));
    	
    	sp = new SoundPool(10, AudioManager.STREAM_MUSIC, 5);
    	//第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量

    	
    	soundPoolMap.put(1, sp.load(this, R.raw.start, 1));
    	soundPoolMap.put(2, sp.load(this, R.raw.stop, 2));
    	//把你的声音素材放到res/raw里，第2个参数即为资源文件，第3个为音乐的优先级 
    	
    	
	}
	
/*	private class btnOnClickListener implements OnClickListener {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Toast.makeText(MainActivity.this, "请长按来开始", Toast.LENGTH_SHORT).show();
		}
		
	}*/
	
	
	
	public void playSound(int sound, int loop) {

        AudioManager mgr = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);   

        float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);   

        float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);       

       float volume = streamVolumeCurrent/streamVolumeMax;   

       sp.play(soundPoolMap.get(sound), volume, volume, 1, loop, 1f);

       //参数：1、Map中取值   2、当前音量     3、最大音量  4、优先级   5、重播次数   6、播放速度

}   
	
	
	
	private class normalListener implements OnTouchListener {
		
        public boolean onTouch(View v, MotionEvent event) {
        	
        	if (v.getId() == R.id.start) {
        		
        		switch ( event.getAction() ) {
				
        		case MotionEvent.ACTION_DOWN:

        			if ( sharedPrefs.getBoolean("prefSoundToggle", true) ) {
        				playSound(1, 0);
        			}
        			
                	time_display.setBase(SystemClock.elapsedRealtime());
                	time_display.start();
                	startButton.setText("stop");
					break;

					
				case MotionEvent.ACTION_UP:
					
					if ( sharedPrefs.getBoolean("prefSoundToggle", true) ) {
						playSound(2, 0);
        			}
					
					new  AlertDialog.Builder(MainActivity.this).setIcon(R.drawable.ic_launcher).setTitle(
							"你的战绩").setMessage("呵呵呵呵，"+time_display.getText().toString() + "...")
							.setPositiveButton("分享战绩", new DialogInterface.OnClickListener() {
								
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									
									Intent intent = new Intent(Intent.ACTION_SEND); 
									intent.setType("text/plain"); 
									intent.putExtra(Intent.EXTRA_SUBJECT, "分享"); 
									intent.putExtra(Intent.EXTRA_TEXT, time_display.getText().toString() );  
									intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
									startActivity(Intent.createChooser(intent, getTitle())); 
									
								}
							}).setNegativeButton("再来一次", null).show();
					
        			time_display.stop();
					startButton.setText("start");
					break;
					
				default:
					break;
				}

                }
        	
        	return false;
        	
        }
        

		
	}
	
	
	
	private class difficultListener implements OnTouchListener {
		
/*        public void onClick(View v) {  
        	// Toast.makeText(MainActivity.this, "长按才是英雄", Toast.LENGTH_SHORT).show();
        }  */
  

        public boolean onTouch(View v, MotionEvent event) {
        	
        	if (v.getId() == R.id.start) {
        		
        		switch ( event.getAction() ) {
				
        		case MotionEvent.ACTION_DOWN:
        			
        			if ( sharedPrefs.getBoolean("prefSoundToggle", true) ) {
        				playSound(1, 0);
        			}
        			
                	xFirst = (int) event.getRawX();
                	yFirst = (int) event.getRawY();
                	
                	time_display.setBase(SystemClock.elapsedRealtime());
                	time_display.start();
                	startButton.setText("stop");

					break;

				case MotionEvent.ACTION_MOVE:
        			int xDis = (int) event.getRawX() - xFirst;
        			int yDis = (int) event.getRawY() - yFirst;
        			
        			if (Math.abs(xDis) > 10 || Math.abs(yDis) > 10) {
        				time_display.stop();
        				startButton.setText("start");
					}
					break;
					
				case MotionEvent.ACTION_UP:
					
					if ( sharedPrefs.getBoolean("prefSoundToggle", true) ) {
        				playSound(2, 0);
        			}
					
					new  AlertDialog.Builder(MainActivity.this).setIcon(R.drawable.ic_launcher).setTitle(
							"你的战绩").setMessage("呵呵呵呵，"+time_display.getText().toString() + "...")
							.setPositiveButton("分享战绩", new DialogInterface.OnClickListener() {
								
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									
									Intent intent = new Intent(Intent.ACTION_SEND); 
									intent.setType("text/plain"); 
									intent.putExtra(Intent.EXTRA_SUBJECT, "分享"); 
									intent.putExtra(Intent.EXTRA_TEXT, time_display.getText().toString() );  
									intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
									startActivity(Intent.createChooser(intent, getTitle())); 
									
								}
							}).setNegativeButton("再来一次", null).show();
					
        			time_display.stop();
					startButton.setText("start");
					break;
					
				default:
					break;
				}

                }
        	return false;
        	
        }
        
	}
	

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		
		switch (id) {
		
		case R.id.action_settings:
			
			Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
			startActivityForResult(intent, RESULT_SETTINGS);
			
			break;

		case R.id.normal_game:
			
			gameMode.setText("正常模式");
			
			new  AlertDialog.Builder(MainActivity.this).setIcon(R.drawable.ic_dialog_alert_holo_light).setTitle(
					"正常模式").setMessage("正常模式下，你可以移动手指，但必须保持按住状态")
					.setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
						
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
							startButton.setOnTouchListener(new normalListener() );
							
						}
					}).show();
			
			break;
			
		case R.id.difficult_game:
			
			gameMode.setText("变态模式");
			
			new  AlertDialog.Builder(MainActivity.this).setIcon(R.drawable.ic_dialog_alert_holo_light).setTitle(
					"变态模式").setMessage("变态模式下，你必须保持按住状态,同时不能做任何移动")
					.setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
						
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
							startButton.setOnTouchListener(new difficultListener() );
							
						}
					}).show();
			
			break;
			
		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
 
        switch (requestCode) {
        case RESULT_SETTINGS:

    		userName.setText(sharedPrefs.getString("prefUserName", "Henry"));
        	
            break;
 
        }
 
    }
	
	
	
}
