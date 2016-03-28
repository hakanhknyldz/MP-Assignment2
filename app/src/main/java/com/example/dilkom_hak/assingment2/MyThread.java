package com.example.dilkom_hak.assingment2;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;

/**
 * Created by dilkom-hak on 28.03.2016.
 */
public class MyThread implements Runnable {

    int currentFirst = 0, currentSecond = 0;
    private Context context;
    public MyThread(Context c)
    {
        context =c;
    }

    public void secondThreadMethod()
    {
        for(int i = 1 ; i<= 9 ; i++)
        {
            String status = "First Thread Waits \n";
            status += "Second Thread is Working!";
            currentSecond = i;
            try
            {
                Thread.sleep(1000);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }

            Message msg = new Message();

            msg.what = 1;
            Bundle bundle = new Bundle();
            bundle.putString("status",status);
            bundle.putInt("first",currentFirst);
            bundle.putInt("second",currentSecond);
            msg.setData(bundle);

            ((MainActivity)context).getHandler().sendMessage(msg);
        }
    }




    @Override
    public void run() {
        secondThreadMethod();
    }
}
