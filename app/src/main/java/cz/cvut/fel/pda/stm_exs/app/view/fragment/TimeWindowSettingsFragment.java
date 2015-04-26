package cz.cvut.fel.pda.stm_exs.app.view.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.EFragment;

import cz.cvut.fel.pda.stm_exs.app.R;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment
public class TimeWindowSettingsFragment extends Fragment {


    public TimeWindowSettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_time_window_settings, container, false);
    }


}
