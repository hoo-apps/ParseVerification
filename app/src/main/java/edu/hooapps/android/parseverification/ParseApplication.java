package edu.hooapps.android.parseverification;

import android.app.Application;

import com.parse.Parse;

// Custom Application class to initialize Parse
// This ensures that Parse is only initialized once during the lifecycle of the app
// NOTE: This structure is commonly used for apps with analytics, etc. that are app-wide
public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Initialize Parse to the app
        Parse.initialize(this,                                      // Context (application-wide)
                "FPIl00Ze9bbzIjjHP92kj77SfIkRoMHLhZapRHDn",         // Application ID
                "THi2ZZrgYehCyrEAlX4UX9HGZtidRiaBBfZbrZTK");        // Client Key

        /*==========================================================================================
            iOS Code Equivalent
        --------------------------------------------------------------------------------------------
        Must:   #import <Parse/Parse.h>
                Add code to AppDelegate.m

        // OBJECTIVE-C & SWIFT
        // Enable Local Datastore
        [Parse enableLocalDatastore];

        // Initialize Parse to the app
        [Parse setApplicationId:@"FPIl00Ze9bbzIjjHP92kj77SfIkRoMHLhZapRHDn"
              clientKey:@"THi2ZZrgYehCyrEAlX4UX9HGZtidRiaBBfZbrZTK"];
        ==========================================================================================*/

    }
}
