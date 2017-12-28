package com.time_bt.administrator.bt_timing;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

/**
 * @author iimT
 * @date 2017/12/21
 *
 */

public class time_service extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        int anMinute = 10 * 1000;
        long triggerAtTime = SystemClock.elapsedRealtime() + anMinute;

        Intent i = new Intent(this, AlarmReciver.class);

        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i ,0);
        manager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);

        return super.onStartCommand(intent, flags, startId);
    }
}
