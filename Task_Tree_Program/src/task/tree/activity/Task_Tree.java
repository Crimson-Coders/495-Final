package task.tree.activity;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import java.util.ArrayList;


public class Task_Tree extends Activity {
    /** Called when the activity is first created. */

	  
	private static Integer alarmTime = 5;
	private static String textDescription;
	private static int counter=0;
    @Override
    
    public void onCreate(Bundle savedInstanceState) {
    	/*This is the main activity, the one
    	 * that the user sees upon startup.
    	 * it will initialize all of the main screen
    	 * buttons, the textview
    	 * receive all of the information regarding the tasks
    	 * set the alarm for when the task should finish
    	 * and trigger the next task. It will ahve 
    	 * a lot more functionality alter, but for now, it
    	 * has a lot going on in the background. */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    	final TextView mainText = (TextView)findViewById(R.id.mainTextView);
    	Button breakbutton = (Button)findViewById(R.id.breakButton);
        Button login = (Button)findViewById(R.id.loginButton);
        Button callButton = (Button)findViewById(R.id.callHelpButton);
        Button taskButton = (Button)findViewById(R.id.taskHelpButton);
        
        Bundle extraInput = getIntent().getExtras();
        if(extraInput != null){
        	int taskNumber = extraInput.getInt("counter");
        	counter = taskNumber;
        }
        setNextTask(mainText);
        
        BroadcastReceiver nextTaskReceiver = new BroadcastReceiver(){

			@Override
			public void onReceive(Context arg0, Intent arg1) {
				/*this is the broadcast receiver for this activity. 
				 * it will set the next task on the liust and update all 
				 * of the information through the use of a function call.*/
				// TODO Auto-generated method stub
				setNextTask(mainText);
			}
        
        };
        
        registerReceiver(nextTaskReceiver, new IntentFilter("task.tree.mainAlarm"));
        
        /*Button inits that spawn new activities and in
         * certain cases, destroys this one. */
        breakbutton.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent breakIntent = new Intent();
				breakIntent.putExtra("counter", counter);
				breakIntent.setClass(Task_Tree.this, Break.class);
				startActivity(breakIntent);
				finish();
				onDestroy();
			}
        		
        });
        
        login.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent loginIntent = new Intent();
				loginIntent.putExtra("counter", counter);
				loginIntent.setClass(Task_Tree.this, Login.class);
				startActivity(loginIntent);
				finish();
				onDestroy();
			}
        	
        	
        });
        
        callButton.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				XmlReaderActivity reader = new XmlReaderActivity(Task_Tree.this);
				String number;
				number = "tel:"+reader.getHelpNumber();
		        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number)); 
		        startActivity(callIntent);
		        onPause();
			}
        	
        });
        
        taskButton.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent helpIntent = new Intent();
				helpIntent.putExtra("description", textDescription);
				helpIntent.setClass(Task_Tree.this, TaskHelp.class);
				startActivity(helpIntent);
			}
        	
        });
        
    }
    
    public void setNextTask(TextView mainText){
    	/*This is the function that draws the information to the text
    	 * view and sets up everything. It uses the XmlReader to do 
    	 * so and will be modified to run more efficiently later. */
    	XmlReaderActivity reader = new XmlReaderActivity(Task_Tree.this);
    	ArrayList<String[]> tasks = new ArrayList<String[]>(100);
    	tasks = reader.getTaskData();
    	if(counter < tasks.size() &&  tasks.get(counter) != null){
    		mainText.setText(tasks.get(counter)[0]);
    		textDescription = tasks.get(counter)[1];
    		alarmTime = Integer.parseInt(tasks.get(counter)[2]);
    		counter += 1;
    		setAlarm(alarmTime);
    	}
    	else{
    		mainText.setText("THE CAKE IS A LIE!!!!!!");
    		textDescription = "THE CAKE IS A LIE!!!!!!";
    	}
    	
    }
    
   
    public void setAlarm(int time){
    	/*This is the set alarm function, this will set the alarm
    	 * to go off which will trigger the broadcast receiver to
    	 * spawn the next task at hand. Currently uses durations
    	 * but will eventually use time stamps ( in the works )*/
    	Toast ourToast;
    	Intent broadCastIntent = new Intent("task.tree.mainAlarm");
    	PendingIntent sendBroadcast = PendingIntent.getBroadcast(Task_Tree.this, 0, broadCastIntent, 0);
    	
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTimeInMillis(System.currentTimeMillis());
    	calendar.add(Calendar.SECOND, time);
    	
    	AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
    	am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sendBroadcast);
    	
    	 ourToast = Toast.makeText(Task_Tree.this, "Set the alarm",
                 Toast.LENGTH_LONG);
         ourToast.show();
    }
}