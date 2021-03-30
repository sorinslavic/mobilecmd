package com.sorin.mobilecmd.androidcmd;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Application;
import android.util.Log;

import com.sorin.mobilecmd.androidcmd.data.UserWS;

/**
 * Global application object singleton. Use the static method {@link #getAppObject()} to obtain
 * a reference to the singleton. Members of this class are volatile so that visibility is ensured
 * among threads. However it is recommended to access this object only inside the GUI thread.
 */
public class AppObject extends Application {
	private static final String TAG = AppObject.class.getSimpleName();

	// number of threads in the application thread pool.
	private static final int NTHREADS = 4;
	
	// global application executor
	private final ExecutorService executor = Executors.newFixedThreadPool(NTHREADS);
	
	// the user used for the last authentication
	private volatile UserWS user;
	
	/**
	 * @return the global application Executor. The executor
	 * 			returned by this method can be safely used in any thread (including the GUI thread).
	 */
	public final Executor getExecutor() {
		return executor;
	}

	// ---------------------------------- //
	
	// application singleton
	private volatile static AppObject appObject;
	
	/**
	 * @return the AppObject singleton
	 */
	public final static AppObject getAppObject() {
		return appObject;
	}
	
	// ---------------------------------- //	
	

	@SuppressWarnings("static-access")
	@Override
    public void onCreate() {
		Log.i(TAG, "onCreate - Called.");
		this.appObject = this;
    }

    @Override
    public void onTerminate() {
    	Log.i(TAG, "onTerminate - Called.");
    	executor.shutdownNow();
    	Log.i(TAG, "onTerminate - executor shutdown.");
    }

    /**
     * Set the user used for the last authentication to the server.
     * @param user
     */
	public void setUser(UserWS user) {
		this.user = user;
	}

	/**
	 * @return the user used for the last authentication to the server.
	 */
	public UserWS getUser() {
		return user;
	}
}
