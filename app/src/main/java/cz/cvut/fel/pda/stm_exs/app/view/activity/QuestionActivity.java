/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cz.cvut.fel.pda.stm_exs.app.view.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import cz.cvut.fel.pda.stm_exs.app.R;
import cz.cvut.fel.pda.stm_exs.app.data.DataModel;
import cz.cvut.fel.pda.stm_exs.app.domain.QaType;
import cz.cvut.fel.pda.stm_exs.app.domain.Question;
import cz.cvut.fel.pda.stm_exs.app.domain.Sampling;
import cz.cvut.fel.pda.stm_exs.app.view.adapter.QuestionsPagerAdapter;
import cz.cvut.fel.pda.stm_exs.app.view.fragment.*;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import java.util.ArrayList;
import java.util.List;

@EActivity
public class QuestionActivity extends FragmentActivity {

    @Bean
    protected DataModel dataModel;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide fragments representing
     * each object in a collection. We use a {@link android.support.v4.app.FragmentStatePagerAdapter}
     * derivative, which will destroy and re-create fragments as needed, saving and restoring their
     * state in the process. This is important to conserve memory and is a best practice when
     * allowing navigation between objects in a potentially large collection.
     */
    QuestionsPagerAdapter mDemoCollectionPagerAdapter;

    /**
     * The {@link android.support.v4.view.ViewPager} that will display the object collection.
     */
    ViewPager mViewPager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        // Create an adapter that when requested, will return a fragment representing an object in
        // the collection.
        // 
        // ViewPager and its adapters use support library fragments, so we must use
        // getSupportFragmentManager.
        mDemoCollectionPagerAdapter = new QuestionsPagerAdapter(getSupportFragmentManager(), buildFragments());

        // Set up action bar.
        final ActionBar actionBar = getActionBar();

        // Specify that the Home button should show an "Up" caret, indicating that touching the
        // button will take the user one step up in the application's hierarchy.
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Set up the ViewPager, attaching the adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This is called when the Home (Up) button is pressed in the action bar.
                // Create a simple intent that starts the hierarchical parent activity and
                // use NavUtils in the Support Package to ensure proper handling of Up.
                Intent upIntent = new Intent(this, MainActivity_.class);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is not part of the application's task, so create a new task
                    // with a synthesized back stack.
                    TaskStackBuilder.from(this)
                            // If there are ancestor activities, they should be added her
                            .addNextIntent(upIntent)
                            .startActivities();
                    finish();
                } else {
                    // This activity is part of the application's task, so simply
                    // navigate up to the hierarchical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<Fragment> buildFragments() {


        List<Fragment> fragments = new ArrayList<Fragment>();
        Sampling sampling = dataModel.getSampling();
        for (Question question : sampling.getQuestions()) {
            if (question.getType() == QaType.CLOSED) {
                fragments.add(ClosedQuestionFragment.newInstance(question));
            } else if (question.getType() == QaType.MEASURE) {
                fragments.add(MeasureQuestionFragment.newInstance(question));
            } else if (question.getType() == QaType.OPEN) {
                fragments.add(OpenQuestionFragment.newInstance(question));
            } else if (question.getType() == QaType.MULTICLOSED) {
                fragments.add(MultiClosedQuestionFragment.newInstance(question));
            }
        }
        fragments.add(EndSamplingFragment.newInstance());

        return fragments;
    }

    @Click(R.id.button_next)
    public void MoveNext() {
        //it doesn't matter if you're already in the last item
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
    }

    @Click(R.id.button_prev)
    public void MovePrevious() {
        //it doesn't matter if you're already in the first item
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
    }

}
