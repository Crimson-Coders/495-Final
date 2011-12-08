package task.tree.activity;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;

public class Break extends Activity{

	private static Integer oneDuration = 0;
	private static Integer twoDuration = 0;
	private static Integer threeDuration = 0;
	private static int counter;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		/*This function is the main function 
		 * that android calls when the activity is created.
		 * It sets up all of the buttons in order for
		 * the program to work as expected.*/
		super.onCreate(savedInstanceState);
        setContentView(R.layout.breakfile);
        
        Button breakOne = (Button)findViewById(R.id.breakOne);
        Button breakTwo = (Button)findViewById(R.id.breakTwo);
        Button breakThree = (Button)findViewById(R.id.breakThree);
        Button exitButton = (Button)findViewById(R.id.exitButton);
        getButtonInfo(breakOne, breakTwo, breakThree);
        
        Bundle extraInput = getIntent().getExtras();
        counter = extraInput.getInt("counter");
        
        BroadcastReceiver breakAlarmReceiver = new BroadcastReceiver(){
        	/*This function is the receiver for the alarm. It
        	 * will receive the alarm, and trigger the main activity.*/
			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				Intent mainActivity = new Intent();
				mainActivity.putExtra("counter", counter);
				mainActivity.setClass(Break.this, Task_Tree.class);
				startActivity(mainActivity);
				finish();
				onDestroy();
			}
        	
        };
        
        registerReceiver(breakAlarmReceiver, new IntentFilter("task.tree.breakAlarm"));
        
        //Get info for all buttons
        
        breakOne.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Get Info for button
				setAlarm(oneDuration);
				
			}
        });
        
        breakTwo.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				//get info for button and start alarm
				setAlarm(twoDuration);
			}     	
        });
        
        breakThree.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				//get info for the button and start alarm
				setAlarm(threeDuration);
			}
        	
        	
        });
        
        exitButton.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent mainActivity = new Intent();
				mainActivity.setClass(Break.this, Task_Tree.class);
				startActivity(mainActivity);
				finish();
				onDestroy();
			}
        });
	}
	
	public void getButtonInfo(Button one, Button two, Button three){
		/*This function is called by the main function to obtain
		 * the button information and set it so that 
		 * the buttons correspond to what the user set
		 * in the desktop application*/
		//get the button information 
		XmlReaderActivity reader = new XmlReaderActivity(Break.this);
		String[] buffer = reader.getBreakNames();
		if(buffer[0] != null){
			one.setText(buffer[0]);
			oneDuration = Integer.parseInt(buffer[1]);
		}
		if(buffer[2] != null){
			two.setText(buffer[2]);
			twoDuration = Integer.parseInt(buffer[3]);
		}
		if(buffer[4] !=null){
			three.setText(buffer[4]);
			threeDuration = Integer.parseInt(buffer[5]);
		}
	}
	
	public void setAlarm(int time){
		/*This funciton sets the alarm so that the break will actually be triggered and restart the main
		 * activity*/
		Toast ourToast;
    	Intent broadCastIntent = new Intent("task.tree.breakAlarm");
    	PendingIntent sendBroadcast = PendingIntent.getBroadcast(Break.this, 0, broadCastIntent, 0);
    	
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTimeInMillis(System.currentTimeMillis());
    	calendar.add(Calendar.SECOND, time);
    	
    	AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
    	am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sendBroadcast);
    	
    	 ourToast = Toast.makeText(Break.this, "Set the alarm",
                 Toast.LENGTH_LONG);
         ourToast.show();
	}
}
