package task.tree.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity{
	private static int counter;
	@Override
	public void onCreate(Bundle savedInstanceState){
		/*This program is the one where the user will (eventually) be able to
		 * log in to the cloud account where the information is to be stored. 
		 * Right now though it is just a concept qand not able
		 * to log in. */
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		final EditText userText = (EditText)findViewById(R.id.usernameField);
		final EditText passText = (EditText)findViewById(R.id.passwordField);
		Button loginButton = (Button)findViewById(R.id.loginButton);
		Button cancelButton = (Button)findViewById(R.id.cancelButton);
		Bundle extraInput = getIntent().getExtras();
		counter = extraInput.getInt("counter");
		
		loginButton.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				//verify Credentials and Login
				if(userText.getText()==null){
					//make a toast and retry
				}
				else if(passText.getText()==null){
					//make a toast and retry
				}
				else{
					//verify credentials and attempt to login
				}
			}
		
			
		});
		
		cancelButton.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent mainActivity = new Intent();
				mainActivity.putExtra("counter", counter);
				mainActivity.setClass(Login.this, Task_Tree.class);
				startActivity(mainActivity);
				finish();
				onDestroy();
			}
			
			
		});
		
	}

}
