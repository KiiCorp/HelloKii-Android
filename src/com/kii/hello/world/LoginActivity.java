package com.kii.hello.world;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kii.cloud.storage.KiiUser;
import com.kii.cloud.storage.callback.KiiUserCallBack;
import com.kii.world.R;

public class LoginActivity extends Activity {
	
    private static final String TAG = "LoginActivity";

    // define our UI elements
    private TextView mUsernameField;
    private TextView mPasswordField;
    private ProgressDialog mProgress;
    
    // called by the 'Log In' button on the UI
    public void handleLogin(View v) {

    	// show a loading progress dialog
    	mProgress = ProgressDialog.show(LoginActivity.this, "", "Signing in...", true);
    	
    	// get the username/password combination from the UI
    	String username = mUsernameField.getText().toString();
    	String password = mPasswordField.getText().toString();
    	Log.v(TAG, "Logging in: " + username + ":" + password);
    	
    	// authenticate the user asynchronously
    	KiiUser.logIn(new KiiUserCallBack() {
    		
    		// catch the callback's "done" request
    		public void onLoginCompleted(int token, KiiUser user, Exception e) {

    			// hide our progress UI element
        		mProgress.cancel();

        		// check for an exception (successful request if e==null)
        		if(e == null) {

        			// tell the console and the user it was a success!
        			Log.v(TAG, "Logged in: " + user.toString());
        			Toast.makeText(LoginActivity.this, "User authenticated!", Toast.LENGTH_SHORT).show();

            		// go to the main screen
            		Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
            		LoginActivity.this.startActivity(myIntent);
            		
        		} 
        		
        		// otherwise, something bad happened in the request
        		else {
        			
        			// tell the console and the user there was a failure
        			Log.v(TAG, "Error registering: " + e.getLocalizedMessage());
        			Toast.makeText(LoginActivity.this, "Error registering: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        		}
        		        		
        	} 
        	
        }, username, password);
    }
    
    // called by the 'Sign Up' button on the UI
    public void handleSignUp(View v) {

    	// show a loading progress dialog
    	mProgress = ProgressDialog.show(LoginActivity.this, "", "Signing up...", true);

    	// get the username/password combination from the UI
    	String username = mUsernameField.getText().toString();
    	String password = mPasswordField.getText().toString();
    	Log.v(TAG, "Registering: " + username + ":" + password);
    	
    	// create a KiiUser object
    	KiiUser user = KiiUser.createWithUsername(username);
    	
    	// register the user asynchronously
        user.register(new KiiUserCallBack() {
        	
    		// catch the callback's "done" request
        	public void onRegisterCompleted(int token, KiiUser user, Exception e) {

    			// hide our progress UI element
        		mProgress.cancel();

        		// check for an exception (successful request if e==null)
        		if(e == null) {

        			// tell the console and the user it was a success!
            		Log.v(TAG, "Registered: " + user.toString());
        			Toast.makeText(LoginActivity.this, "User registered!", Toast.LENGTH_SHORT).show();

            		// go to the next screen
            		Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
            		LoginActivity.this.startActivity(myIntent);
            		
        		} 
        		
        		// otherwise, something bad happened in the request
        		else {
        			
        			// tell the console and the user there was a failure
        			Log.v(TAG, "Error registering: " + e.getLocalizedMessage());
        			Toast.makeText(LoginActivity.this, "Error Registering: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        		}
        		        		
        	}
        	
        }, password);

    }

    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.login);
        
        // link our variables to UI elements
        mUsernameField = (TextView) findViewById(R.id.username_field);
        mPasswordField = (TextView) findViewById(R.id.password_field);
        
    }

}
