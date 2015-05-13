package cz.cvut.fel.pda.stm_exs.app.view.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import cz.cvut.fel.pda.stm_exs.app.R;
import cz.cvut.fel.pda.stm_exs.app.data.DataModel;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

    @Bean
    protected DataModel dataModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        ActionBar actionBar = getActionBar();
        //actionBar.

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

    @Click(R.id.action_start)
    public void startSampling() {
        Intent intent = new Intent(this, QuestionActivity_.class);
        startActivity(intent);
        Log.i("Dashboard:", "Action Start sampling pressed");
    }

    @Click(R.id.dashboard_profile)
    public void buttonProfile() {
        Intent intent = new Intent(this, ProfileActivity_.class);
        startActivity(intent);
        Log.i("Dashboard:", "Action My Profile pressed");
    }

    @Click(R.id.dashboard_settings)
    public void buttonSettings() {
        Intent intent = new Intent(this, SettingsActivity_.class);
        startActivity(intent);
        Log.i("Dashboard:", "button click");
    }

    @Click(R.id.dashboard_answers)
    public void buttonAnswers() {
        Intent intent = new Intent(this, ThemesActivity_.class);
        startActivity(intent);
        Log.i("Dashboard:", "Action My Answers pressed");
    }

    @Click(R.id.dashboard_skip)
    public void buttonSkip() {

        notifyMe();

        Log.i("Dashboard:", "Button skip pressed");
    }

    private void notifyMe() {
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.notification_icon);
        builder.setContentTitle("New questionaire arrived");
        builder.setContentText("Hello World!");
        // play sound, vibrations and light diod
        builder.setDefaults(Notification.DEFAULT_ALL);

/*        Intent resultIntent = new Intent(this, QuestionActivity_.class);
        resultIntent.

        Intent postpone15intent = new Intent(this, OurService.class);
        postpone15intent.setAction(CommonConstants.ACTION_POSTPONE_15);
        PendingIntent postpone15 = PendingIntent.getService(this, 0, postpone15intent, 0);

        Intent postpone60intent = new Intent(this, OurService.class);
        postpone60intent.setAction(CommonConstants.ACTION_POSTPONE_60);
        PendingIntent postpone60 = PendingIntent.getService(this, 0, postpone60intent, 0);

        String msg = "There are few new questions for you.";
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(msg));
        builder.addAction(R.drawable.notification_postpone, "Postpone by 15 mins", postpone15);
        builder.addAction (R.drawable.notification_postpone, "Postpone by 60 mins", postpone60);*/

        nm.notify(1, builder.build());
    }
}
