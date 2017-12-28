package com.time_bt.administrator.bt_timing;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    BluetoothAdapter bluetoothAdapter;
    EditText minutes_view;
    int minutes_num;
    int minutes;
    int seconds;
    Button set_button;
    boolean set_state = false;
    Timer timing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    //按钮按下时的事件
    public void set_time(View v) {
        minutes_view = (EditText) findViewById(R.id.input_minute);
        if(minutes_view.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "请先输入", Toast.LENGTH_SHORT).show();
            return;
        }
        set_button = (Button) findViewById(R.id.set);

        set_state = !set_state; //更改设置状态
        //获取用户输入时间
        if(set_state) {
            //设置倒计时
            timing = set_timing();
        } else {
            //停止倒计时
            cancle_timing();
        }
    }

    //设置倒计时
    public Timer set_timing() {
        Helper.makeToast(this,"设置成功，开始计时",true);
        minutes_num = Integer.parseInt(minutes_view.getText().toString());
        minutes_view.setText(((minutes_num > 10 ? minutes_num : "0" + minutes_num) + " : 00").toCharArray(), 0,
                ((minutes_num > 10 ? minutes_num : "0" + minutes_num) + " : 00").length());
        set_button.setText("取消");

        minutes = minutes_num - 1;
        seconds = 59;

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            String change_time = "";
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(seconds == 0){
                            if(minutes == 0 && seconds == 0) {
                                //关闭蓝牙
                                if(bluetoothAdapter != null) {
                                    bluetoothAdapter.disable(); //关闭蓝牙
                                    Helper.makeToast(getApplicationContext(),"蓝牙已关闭,2秒后程序退出", true);
                                    paddingToFinish(2000);
                                } else {
                                    Helper.makeToast(getApplicationContext(), "没有找到蓝牙模块",true);
                                }
                                stop_timing();
                            } else {
                                seconds = 59;
                                minutes--;
                            }
                        }

                        change_time += minutes > 9? minutes : "0" + minutes;
                        change_time += " : ";
                        change_time += seconds > 9? seconds : "0" + seconds;

                        minutes_view.setText(change_time.toCharArray(), 0, change_time.length());
                        change_time = "";
                        seconds = seconds == 0 ? 0 : seconds - 1;

                    }
                });
            }
        };
        timer.schedule(task ,1000, 1000);
        //禁用输入框
        Helper.banInput(minutes_view);
        return timer;
    }

    public void cancle_timing() {
        minutes = 0;
        seconds = 0;

        timing.cancel();
        Helper.emptyInput(minutes_view);
        Helper.activeInput(minutes_view);
        set_button.setText("设置");
        Helper.makeToast(this,"已取消，请重新设置",true);
    }

    public void stop_timing() {
        timing.cancel();
        Helper.activeInput(minutes_view);
        Helper.emptyInput(minutes_view);
        set_state = !set_state;
        set_button.setText("设置");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Helper.makeToast(this,"程序将在后台运行",true);
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //暂停一会之后结束
    public void paddingToFinish(final int msec) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(msec);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finish();
            }
        }).start();
    }
}
