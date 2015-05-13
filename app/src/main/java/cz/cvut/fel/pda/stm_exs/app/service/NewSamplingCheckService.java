package cz.cvut.fel.pda.stm_exs.app.service;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import cz.cvut.fel.pda.stm_exs.app.R;
import cz.cvut.fel.pda.stm_exs.app.data.DataModel;
import cz.cvut.fel.pda.stm_exs.app.domain.Sampling;
import cz.cvut.fel.pda.stm_exs.app.service.rest.RESTClientErrorHandler;
import cz.cvut.fel.pda.stm_exs.app.service.rest.RESTClientService;
import cz.cvut.fel.pda.stm_exs.app.view.activity.MainActivity_;
import org.androidannotations.annotations.*;
import org.androidannotations.annotations.rest.RestService;

import java.text.MessageFormat;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * CheckingService
 * <p/>
 * This service repeatedly asks server for new data for currently logged in user
 * and if it gets new data, displays notification in ActionBar. When interval of
 * checking is changed in settings, this service is stopped and started again
 * from @SettingsFragment
 * <p/>
 * Copyright (c) 2014 AspectWorks, spol. s r.o.
 *
 * @author david.passler
 * @version $Revision$
 */
@EService
public class NewSamplingCheckService extends Service {

    @SystemService
    NotificationManager notificationManager;

    @RestService
    RESTClientService restClientService;

    @Bean
    DataModel dataModel;

    /**
     * Handle REST service exceptions
     */
    @Bean
    RESTClientErrorHandler errorHandler;

    /**
     * Time to check - every minutesToCheck minutes
     */
    private int minutesToCheck;

    /**
     * Provides recurrent checking
     */
    private Timer timer = new Timer();

    @AfterInject
    public void init() {
        restClientService.setRestErrorHandler(errorHandler);
    }

    @Override
    public void onCreate() {
        Log.d(getClass().getName(), "Checking service started");
        minutesToCheck = 1;

        startNewTimer();
    }

    /**
     * We don't provide binding of this service, so it returns null
     *
     * @return null
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Log.d(getClass().getName(), "Checking service stopped");
        stopTimer();
        timer = null;
    }

    /**
     * Gets list of records earlier than given date (obtained from
     * uiComponentModel)
     */
    @Background
    void downloadNewDataFromServer() {
        Sampling sampling = restClientService.getSampling("shopping", "1");

        if (sampling != null && !dataModel.hasSampling(sampling)) {
            dataModel.saveSampling(sampling);
            makeNotification(sampling);
        }
    }

    /**
     * Displays notification on Android device in Status bar
     *
     * @param sampling Sampling with new questions
     */
    @UiThread
    void makeNotification(Sampling sampling) {

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("New questionaire arrived")
                .setContentText("Hello World!")
                .setDefaults(Notification.DEFAULT_ALL);

        Intent intent = new Intent(this, MainActivity_.class);
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addParentStack(MainActivity_.class);
        taskStackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);
        notification.setTicker(MessageFormat.format("New samplings!!!", sampling.getTheme()));

        notificationManager.notify(0, notification.build());
    }

    /**
     * Stops timer (is used for restart when number of minutes is changed)
     */
    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = new Timer();
        }
    }

    private void startNewTimer() {
        int millisecondsInterval = minutesToCheck * 60 * 1000;

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();

                // Here we start from 1 because on position 0 there is currently
                // running application
                // and we want to download data from server only if our
                // application is in background
                for (int i = 1; i < runningAppProcesses.size(); i++) {
                    ActivityManager.RunningAppProcessInfo appProcessInfo = runningAppProcesses.get(i);

                    // if it is our application and is in background, then
                    // download data
                    if (appProcessInfo.processName.equals(getBaseContext().getPackageName())
                            && appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                        downloadNewDataFromServer();
                    }
                }
            }
        }, millisecondsInterval, millisecondsInterval);
    }
}
