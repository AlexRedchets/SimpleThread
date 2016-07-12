package com.example.azvk.simplethread;

import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            String message = (String)msg.obj;
            TextView textView_1 = (TextView)findViewById(R.id.textView_1);
            textView_1.setText(message);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButtonClicked(View view){

        //set future time +5 sec from current time
        final long futureTime = System.currentTimeMillis() + 5000;

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (System.currentTimeMillis() < futureTime){
                    Message msg = Message.obtain();
                    msg.obj = "Thread is working";
                    handler.sendMessage(msg);
                    //use synchronized method to protect data (if the object is already blocked, wait until unblocking to use it)
                    synchronized (this) {
                        try {
                            //wait system for 5 sec
                            wait(futureTime - System.currentTimeMillis());
                        } catch (Exception e) {
                            Log.e(TAG, "ERROR: ", e);
                        }
                    }
                }
                Message msg = Message.obtain();
                msg.obj = "Thread is done";
                handler.sendMessage(msg);
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

    }
}
