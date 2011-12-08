package task.tree.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class TaskHelp extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState){
		/*This activity is very small for now, all it does is display the help description
		 * and sets the exit button with a small picture. */
		super.onCreate(savedInstanceState);
		setContentView(R.layout.taskhelp);
		
		final TextView taskHelp = (TextView)findViewById(R.id.taskHelpView);
		Button exitButton = (Button)findViewById(R.id.exitButton);
		Bundle extraInput = getIntent().getExtras();
		String inString = extraInput.getString("description");
		taskHelp.setText(inString);
		
		setTextView(taskHelp);
		
		exitButton.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				onDestroy();
			}
			
			
		});
	}
	
	public void setTextView(TextView helpText){
		
	}
}
