package cz.cvut.fel.pda.stm_exs.app.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import cz.cvut.fel.pda.stm_exs.app.R;
import cz.cvut.fel.pda.stm_exs.app.data.DataModel;
import cz.cvut.fel.pda.stm_exs.app.data.TimeWindowsModel;
import cz.cvut.fel.pda.stm_exs.app.domain.Sampling;
import cz.cvut.fel.pda.stm_exs.app.domain.Time;
import cz.cvut.fel.pda.stm_exs.app.domain.TimeWindow;
import cz.cvut.fel.pda.stm_exs.app.service.rest.RESTClientErrorHandler;
import cz.cvut.fel.pda.stm_exs.app.service.rest.RESTClientService;
import cz.cvut.fel.pda.stm_exs.app.view.activity.MainActivity_;
import org.androidannotations.annotations.*;
import org.androidannotations.annotations.rest.RestService;

import java.text.MessageFormat;
import java.util.*;

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

    @Bean
    TimeWindowsModel timeWindowsModel;

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

    private boolean isTime(TimeWindow window) {

        Calendar c = Calendar.getInstance();
        Date now = c.getTime();
        int day = c.get(Calendar.DAY_OF_WEEK) - 1;
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        if (window.getDay(day)) {
            Time start = window.getStart();
            Time end = window.getEnd();
            if (start.getHour() == hour && hour == end.getHour()) {
                if (start.getMinute() <= minute && minute <= end.getMinute()) {
                    return true;
                }
            } else if (start.getHour() == hour && hour < end.getHour()) {
                if (start.getMinute() <= minute) {
                    return true;
                }
            } else if (start.getHour() < hour && hour == end.getHour()) {
                if (minute <= end.getMinute()) {
                    return true;
                }
            } else if (start.getHour() < hour && hour < end.getHour()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets list of records earlier than given date (obtained from
     * uiComponentModel)
     */
    @Background
    void downloadNewDataFromServer() {
        Map<String, TreeMap<String, TimeWindow>> themesMap = timeWindowsModel.getThemesMap();
        for (Map.Entry<String, TreeMap<String, TimeWindow>> mapEntry : themesMap.entrySet()) {
            TreeMap<String, TimeWindow> value = mapEntry.getValue();
            for (TimeWindow timeWindow : value.values()) {
                if (isTime(timeWindow)) {
                    Sampling sampling = restClientService.getSampling(mapEntry.getKey(), "1");
                    if (sampling != null && !dataModel.hasSampling(sampling)) {
                        dataModel.saveSampling(sampling);
                        makeNotification(sampling);
                        return;
                    }
                }
            }
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
                .setContentText("Theme: "
                        + sampling.getTheme() + " has " + sampling.getTitle())
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
                downloadNewDataFromServer();
            }
        }, millisecondsInterval, millisecondsInterval);
    }
}
