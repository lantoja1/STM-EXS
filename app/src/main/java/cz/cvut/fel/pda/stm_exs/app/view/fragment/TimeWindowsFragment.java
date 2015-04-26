package cz.cvut.fel.pda.stm_exs.app.view.fragment;


import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import cz.cvut.fel.pda.stm_exs.app.R;
import cz.cvut.fel.pda.stm_exs.app.data.TimeWindowsModel;
import cz.cvut.fel.pda.stm_exs.app.domain.TimeWindow;
import cz.cvut.fel.pda.stm_exs.app.view.adapter.ExpandableListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_time_windows)
public class TimeWindowsFragment extends Fragment {

    @Bean
    protected TimeWindowsModel timeWindowsModel;

    public TimeWindowsFragment() {
        // Required empty public constructor
    }


    public static TimeWindowsFragment newInstance(int index) {
        TimeWindowsFragment f = new TimeWindowsFragment_();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);

        return f;
    }


    public int getShownIndex() {
        return getArguments().getInt("index", 0);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            // We have different layouts, and in one of them this
            // fragment's containing frame doesn't exist.  The fragment
            // may still be created from its saved state, but there is
            // no reason to try to create its view hierarchy because it
            // won't be displayed.  Note this is not needed -- we could
            // just run the code below, where we would create and return
            // the view hierarchy; it would just never be used.
            return null;
        }

        return inflater.inflate(R.layout.fragment_time_windows, container, false);
    }

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        // add time window button click
        View fab = getActivity().findViewById(R.id.add_interval);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(v.getContext(), timeWindowsModel.getSortedThemesNames().get(getShownIndex()), Toast.LENGTH_SHORT).show();
                    String theme = timeWindowsModel.getSortedThemesNames().get(getShownIndex());
                    // add a new TimeWindow to this Theme in model
                    timeWindowsModel.addTimeWindow(theme);
                    // update view
                    updateExpandableListView();
                }
            });
        }

        updateExpandableListView();
    }

    private void updateExpandableListView() {
        // get the listview
        expListView = (ExpandableListView) getActivity().findViewById(R.id.lvExp);
        if (expListView != null) {
            // preparing list data
            prepareListData();
            listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
            // setting list adapter
            expListView.setAdapter(listAdapter);
            // add listener on item click
            expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                   // Toast.makeText(v.getContext(), "on item" + groupPosition + " > " + childPosition + " click (id is " + id + ")", Toast.LENGTH_SHORT).show();
                    String theme = timeWindowsModel.getSortedThemesNames().get(getShownIndex());
                    TimeWindow tw = timeWindowsModel.getTimeWindowByViewIndices(theme, groupPosition);

                    switch (childPosition) {
                        case 0:
                            DialogFragment newFragment = new EditTimeDialog();
                            Bundle bundle = new Bundle();
                            bundle.putInt("hour", tw.getStart().getHour());
                            bundle.putInt("minute", tw.getStart().getMinute());
                            bundle.putString("theme", theme);
                            bundle.putInt("index", groupPosition);
                            bundle.putBoolean("start", true);
                            newFragment.setArguments(bundle);
                            newFragment.show(getActivity().getFragmentManager(), "timePicker");
                            break;

                        case 1:
                            DialogFragment newFragment1 = new EditTimeDialog();
                            Bundle bundle1 = new Bundle();
                            bundle1.putInt("hour", tw.getEnd().getHour());
                            bundle1.putInt("minute", tw.getEnd().getMinute());
                            bundle1.putString("theme", theme);
                            bundle1.putInt("index", groupPosition);
                            bundle1.putBoolean("start", false);
                            newFragment1.setArguments(bundle1);
                            newFragment1.show(getActivity().getFragmentManager(), "timePicker");
                            break;
                    }
                    return false;
                }
            });
        }
    }


    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = timeWindowsModel.getListDataHeader(getShownIndex());
        listDataChild = new HashMap<String, List<String>>();

        List<String> generalOptions = new ArrayList<String>();
        generalOptions.add("Začátek časového okna");
        generalOptions.add("Konec časového okna");
        generalOptions.add("Opakovat ve dny");

        for (int i = 0; i < listDataHeader.size(); i++){
            listDataChild.put(listDataHeader.get(i), generalOptions); // Header, Child data
        }
    }

}
