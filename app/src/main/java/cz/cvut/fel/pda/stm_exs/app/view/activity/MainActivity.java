package cz.cvut.fel.pda.stm_exs.app.view.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import cz.cvut.fel.pda.stm_exs.app.R;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

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
        Log.wtf("Dashboard:", "Action My Profile pressed");


    }

    @Click(R.id.dashboard_settings)
    public void buttonSettings() {
        Intent intent = new Intent(this, SettingsActivity_.class);
        startActivity(intent);
        Log.wtf("Dashboard:", "button click");
    }
}
