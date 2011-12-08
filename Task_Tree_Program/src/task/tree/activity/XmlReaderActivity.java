package task.tree.activity;

import java.io.IOException;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import java.util.ArrayList;

public class XmlReaderActivity{
	/*This class is what gets all of
	 * the information for my program to
	 * be able to run. It parses all 
	 * of the information and returns it as strings of
	 * what I need. */
	private static Activity contextActivity;
	private static String mainString;
	public XmlReaderActivity(Activity activity){
		contextActivity = activity;
		
		try{
		mainString = getEventsFromAnXML();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		catch(XmlPullParserException e){
			e.printStackTrace();
		}
	}
	
	public String getEventsFromAnXML()
	throws XmlPullParserException, IOException
		{
		/*this is the parsing function. Nathan wrote it
		 * and can tell you more about it, but it reads 
		 * the XML file and returns a string buffer to
		 * be used by later function calls.*/
		StringBuffer stringBuffer = new StringBuffer();
		Resources res = contextActivity.getResources();
		XmlResourceParser xpp = res.getXml(R.xml.myxml);
		xpp.next();
		int eventType = xpp.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT){
			
			if (eventType == XmlPullParser.START_DOCUMENT)
				stringBuffer.append("start");
			
			else if (eventType == XmlPullParser.START_TAG)
				stringBuffer.append(" "+xpp.getName());
			
			else if (eventType == XmlPullParser.END_TAG)
				stringBuffer.append("");
			
			else if (eventType == XmlPullParser.TEXT)
				stringBuffer.append(" "+xpp.getText());
			
			eventType = xpp.next();
		}
		
		return stringBuffer.toString();
	}


		public String[] getBreakNames(){
			/*This is the function that I call
			 * in the break activity, It gives me 
			 * the names to put on the buttons and 
			 * the durations to set for the buttons.*/
			String[] words = mainString.split(" ");
			int i = 0; int j = 0;
			String[] data = new String[6];
			String name = "";
			while (i < words.length){
				if (words[i].equals("break")){
					i+=2; //skipping name
					name = "";
					while (!words[i].equals("duration")){
						name = name.concat(words[i].trim()+" "); i++;
					}
					data[j] = name;
					j++; //duration slot be careful that j never gets above 6
					i++; //skipping duration
					data[j] = words[i].trim(); // on duration in words
					j++; // advancing array
				}
				i++;
			}
			return data;
		}
		
		public String getHelpNumber(){
			/*This is the function that will return the 
			 * telephone number for me to enter into 
			 * the program for the callhelp button*/
			String[] words = mainString.split(" ");
			int i = 0;
			
			while(i<words.length){
				if(words[i].equals("helpNumber")){
					return words[i+1].trim();
				}
				i++;
			}
			return ("111111111");
			
			
		}
		
		public ArrayList<String[]> getTaskData(){
			/*This is the main function. 
			 * It returns the task name, the duration
			 * the description and will eventually pass me
			 * a lot more information. This will determine what
			 * task the user currently has to do.*/
			ArrayList<String[]> data = new ArrayList<String[]>();
			String[] words = mainString.split(" ");
			int i = 0, j = 0;
			String name = "", description = "";
			while (i < words.length){
				String[] entry = new String[3];
				if (words[i].equals("task")){
					i+=2; //skipping task and taskname
					name = "";
					while (!words[i].equals("description")){
						name = name.concat(words[i].trim()+" "); i++;
					}
					entry[0] = name; //setting name
					i++;//moving to description
					description = "";
					while (!words[i].equals("duration")){
						description = description.concat(words[i].trim()+" "); i++;
					}
					entry[1] = description;
					i++; //skipping duration
					entry[2] = words[i].trim(); //setting duration
					data.add(j, entry); j++;
				}
				i++;
			}
			return data;
		}
}
//XmlReaderActivity reader = new XmlReaderActivity(Task_Tree.this);
/*Try{
 * 		String newString = reader.getEventsFromAnXml();
 * 		String[] breakArray = reader.getBreakNames(newString);
 * 		breakArray[i]
 * }*/
 