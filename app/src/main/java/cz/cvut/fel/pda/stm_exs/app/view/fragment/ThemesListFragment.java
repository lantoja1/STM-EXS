package cz.cvut.fel.pda.stm_exs.app.view.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;
import java.util.Collections;

import cz.cvut.fel.pda.stm_exs.app.R;

import cz.cvut.fel.pda.stm_exs.app.data.TimeWindowsModel;
import cz.cvut.fel.pda.stm_exs.app.view.activity.TimeWindowsActivity_;

/**
 * A fragment representing a list of Items.
 */
@EFragment(R.layout.fragment_settings)
public class ThemesListFragment extends ListFragment {

    boolean mDualPane;
    int mCurCheckPosition = 0;

    @Bean
    protected TimeWindowsModel timeWindowsModel;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ThemesListFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Populate list with theme names
        setListAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_activated_1, timeWindowsModel.getSortedThemesNames()));

        // Check to see if we have a frame in which to embed the details
        // fragment directly in the containing UI.
        View detailsFrame = getActivity().findViewById(R.id.time_windows_of_chosen_theme_frame);
        mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

        if (savedInstanceState != null) {
            // Restore last state for checked position.
            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
        }

        if (mDualPane) {
            // In dual-pane mode, the list view highlights the selected item.
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            // Make sure our UI is in the correct state.
            showDetails(mCurCheckPosition);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        showDetails(position);
    }


    /**
     * Helper function to show the details of a selected item, either by
     * displaying a fragment in-place in the current UI, or starting a
     * whole new activity in which it is displayed.
     */
    void showDetails(int index) {
        mCurCheckPosition = index;

        if (mDualPane) {
            // We can display everything in-place with fragments, so update
            // the list to highlight the selected item and show the data.
            getListView().setItemChecked(index, true);

            // Check what fragment is currently shown, replace if needed.
            Fragment details = getFragmentManager().findFragmentById(R.id.time_windows_of_chosen_theme_frame);
            if (details != null && details instanceof TimeWindowsFragment) {
                if (((TimeWindowsFragment) details).getShownIndex() != index) {
                    replaceFragment(index);
                }
            } else if (details != null && details instanceof TimeWindowSettingsFragment_) {
                replaceFragment(index);
            } else if (details == null) {
                replaceFragment(index);
            }
        } else {
            // Otherwise we need to launch a new activity to display
            // the dialog fragment with selected text.
            Intent intent = new Intent();
            intent.setClass(getActivity(), TimeWindowsActivity_.class);
            intent.putExtra("index", index);
            startActivity(intent);
        }
    }

    private void replaceFragment(int index){
        // Make new fragment to show this selection.
        TimeWindowsFragment details = TimeWindowsFragment.newInstance(index);
        // Execute a transaction, replacing any existing fragment
        // with this one inside the frame.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.time_windows_of_chosen_theme_frame, details);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
}