package com.example.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockActivity;
import com.example.movieapp.R;

public class LoadingScreenActivity extends SherlockActivity {
	private int count = 5;
	private int[] imgIDs = {R.id.widget0,R.id.widget1,R.id.widget2,R.id.widget3,R.id.widget4};
	private long time = 4*1000;
	boolean isStop = false;
	
	private static final int TYPE_SELECTED = 1;     
	private static final int TYPE_NO_SELECTED = 2;  
	private static final int TYPE_STOP = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
       
        for(int id : imgIDs)
        	((ImageView)findViewById(id)).setBackgroundResource(R.drawable.progress_bg_small);
        
        final IndexThread showThread = new IndexThread();
        final InitialThread workingThread = new InitialThread();
        showThread.start();
        workingThread.start();
	}
	
	 public Handler myHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch(msg.what)
				{
				case TYPE_STOP:
					Intent intent = new Intent(LoadingScreenActivity.this, MainActivity.class);
					startActivity(intent);
					isStop = true;
					finish();
					break;
				case TYPE_SELECTED:
					((ImageView)findViewById(msg.arg1)).setBackgroundResource(R.drawable.progress_go_small);
					break;
				case TYPE_NO_SELECTED:
					((ImageView)findViewById(msg.arg1)).setBackgroundResource(R.drawable.progress_bg_small);
					break;
				}
			}
	    };
	    
	    class InitialThread extends Thread{
	    	@Override
		     public void run()
		     {
	    		
	    		try {
					Thread.sleep(time);//替换为初始化代码....
					
					Message msg;
		    		msg = new Message();
					msg.what = TYPE_STOP;
					myHandler.sendMessage(msg);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Message msg;
		    		msg = new Message();
					msg.what = TYPE_STOP;
					myHandler.sendMessage(msg);
				}
		     }
	    }
	    
	    class IndexThread extends Thread
	    {
	    	@Override
		     public void run()
		     {
	    		Message msg;
	    		while(!isStop)
	    		{    			
	    			for(int i= 0 ; i < count ; i++)
	    			{    				
	    				msg = new Message();
	    				msg.what = TYPE_SELECTED;
	    				msg.arg1 = imgIDs[i];
	    				myHandler.sendMessage(msg);
	    				msg = new Message();
	    				if(i==0)
	    				{
	    					msg.what = TYPE_NO_SELECTED;
	    					msg.arg1 = imgIDs[count-1];
	    					myHandler.sendMessage(msg);
	    					}
	    				else
	    				{
	    					msg.what = TYPE_NO_SELECTED;
	    					msg.arg1 = imgIDs[i-1];
	    					myHandler.sendMessage(msg);
	    					}
	    				SystemClock.sleep(500);
	    			}    			
	    		}    		
		     }
	    }
}
