package com.mobile.sextus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONTokener;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class Main extends Activity {

	private static final 	String	BAD_CONNECTION	= "Connection Error";
	private static final 	String	BAD_URL					= "URL error";
	private static final	String	JSONLoc					= "http://tetonsoftware.com/bikes/bikes.json";
	private static final	String	ERR_TITLE				= "Error";
	private 							String	errString;
	private 							String	bikesJSON;
	private 		          View		listView;
	private 							AlertDialog.Builder	errAlert;
	
	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );
		
		errAlert = new AlertDialog.Builder( this );
    errAlert.setTitle( ERR_TITLE );
		
		listView = findViewById( R.id.listView );
		
		new AsyncJSONGetter().execute( JSONLoc );
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu( Menu menu ) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate( R.menu.activity_main, menu );
		return true;
	}

	
private class AsyncJSONGetter extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... arg0) {
			BufferedReader 		in 					= null;
			URL 							url 				= null;
			HttpURLConnection connection 	= null;
			errString = "";
			
			try {
				  url = new URL( arg0[0] );
		      try {
						connection = (HttpURLConnection) url.openConnection();
						connection.setDoInput(true);
				    connection.connect();
				    in = new BufferedReader( new InputStreamReader( connection.getInputStream() ) );
				    
				    StringBuffer sb = new StringBuffer("");
		        String line = "";
	
		        while ( (line = in.readLine()) != null ) {
		            sb.append( line );
		        }
		        in.close();
		        String page = sb.toString();
		        //System.out.println(page);
		        return page;
					} catch (IOException e) {
						e.printStackTrace();
						//System.err.println("IO Exception on HTTP connection");
						errString = BAD_CONNECTION;
					} 
			} catch (MalformedURLException e) {
				e.printStackTrace();
				//System.err.println(BAD_URL);
				errString = BAD_URL;
			} 
			return "";
		}

		protected void onPostExecute(String result) {
			JSONTokener jToken = null;
			
			bikesJSON = result;
			jToken = new JSONTokener( bikesJSON );
			
			if(!errString.isEmpty()) {
				errAlert.setMessage( errString );
				errAlert.create().show();
			}
		}
	}
}
