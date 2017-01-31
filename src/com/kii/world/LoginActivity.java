//
//
// Copyright 2017 Kii Corporation
// http://kii.com
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
//

package com.kii.world;

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
import com.kii.cloud.storage.exception.app.AppException;

public class LoginActivity extends Activity {
	
    private static final String TAG = "LoginActivity";

    // Define the UI elements.
    private TextView mUsernameField;
    private TextView mPasswordField;
    private ProgressDialog mProgress;
    
    // Called by the "Log In" button.
    public void handleLogin(View v) {

    	// Show a progress dialog.
    	mProgress = ProgressDialog.show(LoginActivity.this, "", "Signing in...", true);
    	
    	// Get the username and password from the UI.
    	String username = mUsernameField.getText().toString();
    	String password = mPasswordField.getText().toString();
    	Log.v(TAG, "Logging in: " + username + ":" + password);
    	
    	// Authenticate the user asynchronously.
    	KiiUser.logIn(new KiiUserCallBack() {
    		
    		// Catch the result from the callback method.
    		public void onLoginCompleted(int token, KiiUser user, Exception e) {

    			// Hide the progress dialog.
        		mProgress.cancel();

        		// Check for an exception. The request was successfully processed if e==null.
        		if(e == null) {

        			// Tell the console and the user that the login was successful.
        			Log.v(TAG, "Logged in: " + user.toString());
        			Toast.makeText(LoginActivity.this, "User authenticated!", Toast.LENGTH_SHORT).show();

            		// Go to the main screen.
            		Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
            		LoginActivity.this.startActivity(myIntent);
            		
        		} 
        		
        		// A failure occurred when processing the request.
        		else {
        			
        			// Tell the console and the user that the login failed.
        			Log.v(TAG, "Error authenticating: " + e.getLocalizedMessage());
        			Toast.makeText(LoginActivity.this, "Error authenticating: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        		}
        		        		
        	} 
        	
        }, username, password);
    }
    
    // Called by the "Sign Up" button.
    public void handleSignUp(View v) {

    	// Show a progress dialog.
    	mProgress = ProgressDialog.show(LoginActivity.this, "", "Signing up...", true);

    	// Get the username and password from the UI.
    	String username = mUsernameField.getText().toString();
    	String password = mPasswordField.getText().toString();
    	Log.v(TAG, "Registering: " + username + ":" + password);
    	
    	// Create a KiiUser object.
    	try {
        	KiiUser user = KiiUser.createWithUsername(username);
        	// Register the user asynchronously.
            user.register(new KiiUserCallBack() {
            	
        		// Catch the result from the callback method.
            	public void onRegisterCompleted(int token, KiiUser user, Exception e) {

        			// Hide the progress dialog.
            		mProgress.cancel();

            		// Check for an exception. The request was successfully processed if e==null.
            		if(e == null) {

            			// Tell the console and the user that the registration was successful.
                		Log.v(TAG, "Registered: " + user.toString());
            			Toast.makeText(LoginActivity.this, "User registered!", Toast.LENGTH_SHORT).show();

                		// Go to the main screen.
                		Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
                		LoginActivity.this.startActivity(myIntent);
                		
            		} 
            		
            		// A failure occurred when processing the request.
            		else {
            			
            			// Tell the console and the user that the registration failed.
            			Log.v(TAG, "Error registering: " + e.getLocalizedMessage());
            			Toast.makeText(LoginActivity.this, "Error Registering: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            		}
            		        		
            	}
            	
            }, password);

    	} catch(Exception e) {
    		mProgress.cancel();
    		Toast.makeText(this, "Error signing up: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    	}
    	
    }

    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.login);
        
        // Link the variables to the UI elements.
        mUsernameField = (TextView) findViewById(R.id.username_field);
        mPasswordField = (TextView) findViewById(R.id.password_field);
        
    }

}
