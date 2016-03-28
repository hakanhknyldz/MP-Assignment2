package com.example.dilkom_hak.assingment2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "HAKKE";
    TextView tvfirst, tvsecond;
    public EditText etInfo;
    private final int END_POINT = 99;
    public int currentFirst = 0;
    public int currentSecond = 0;
    Thread thread1, thread2;
    Button btnStart, btnPause, btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvfirst = (TextView) findViewById(R.id.tvFirstDigit);
        tvsecond = (TextView) findViewById(R.id.tvSecondDigit);
        etInfo = (EditText) findViewById(R.id.etInfo);


        btnStart = (Button) findViewById(R.id.btnStart);
        btnPause = (Button) findViewById(R.id.btnPause);
        btnClear = (Button) findViewById(R.id.btnClear);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnStartMethod();
            }
        });
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPauseMethod();
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnClearMethod();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void btnClearMethod() {
        tvfirst.setText("0");
        tvsecond.setText("0");
        etInfo.setText("Press Start to Go!");

    }

    private void btnStartMethod() {


        Log.d(TAG, "Start Method başladı.");
        //thread2 = new Thread(secondRunnable);
        //thread2.start();

        thread2 = new Thread() {
            public void run() {
                runOnUiThread(secondRunnable);
            }
        };
        thread2.start();


    }

    private void btnPauseMethod() {
        Log.d(TAG, "Pause Method başladı.");

        if (thread2 != null || thread1 != null) {
            try {
                thread2.wait();
                thread1.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    private Runnable firstRunnable = new Runnable() {
        @Override
        public void run() {
            Log.d(TAG, "Thread 1 START");
            String info = "First Thread is now Working, Second Thread is set to Zero!";
            etInfo.setText(info);

            currentFirst = Integer.parseInt(tvfirst.getText().toString());
            Log.d("TAG", "tvFirst get Text = " + tvfirst.getText().toString());
            try {
                if (currentFirst == 9) {
                    thread2.interrupt();
                    etInfo.setText("Counter is stopped!");
                }
                Thread.sleep(1000);

                currentFirst = currentFirst + 1;
                tvfirst.setText(currentFirst + "");
                tvfirst.invalidate();
                thread2.join();


                Log.d("HAKKE", "Thread 1 FINISH");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    };

    private Runnable secondRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                Log.d(TAG, "Thread 2 START");

                String info = "First Thread Waits, Second Thread is Working!";

                etInfo.setText(info);

                currentSecond = Integer.parseInt(tvsecond.getText().toString());
                Log.d("TAG", "tvSecond get Text = " + tvsecond.getText().toString());
                while (currentSecond < 9) {
                    Thread.sleep(1000);

                    currentSecond = currentSecond + 1;
                    tvsecond.setText(currentSecond + "");
                    tvsecond.invalidate();

                }
                if (currentSecond == 9) {
                    currentSecond = 0;

                    if (thread1 == null) {
                        thread1 = new Thread() {
                            @Override
                            public void run() {
                                runOnUiThread(firstRunnable);
                            }
                        };
                        thread1.start();
                    } else {
                        thread1.join();
                    }

                }
                Log.d("HAKKE", "Thread 2 FINISH");


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };


}
