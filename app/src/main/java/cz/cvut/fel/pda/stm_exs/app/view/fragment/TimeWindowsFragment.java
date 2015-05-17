package cz.cvut.fel.pda.stm_exs.app.view.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.*;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import cz.cvut.fel.pda.stm_exs.app.R;
import cz.cvut.fel.pda.stm_exs.app.data.TimeWindowsModel;
import cz.cvut.fel.pda.stm_exs.app.domain.TimeWindow;
import cz.cvut.fel.pda.stm_exs.app.view.adapter.TimeWindowArrayAdapter;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;
import java.util.List;

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
                    updateListView();
                    // show time windows settings fragment
                    int twID = timeWindowsModel.getTimeWindowByViewIndices(theme, 0).getId();
                    showTimeWindowSettingsFragment(theme, twID);
                }
            });
        }
        updateListView();

        // set title
        String theme = timeWindowsModel.getSortedThemesNames().get(getShownIndex());
        if (theme == null) {
            theme = "";
        } else {
            theme = " - " + theme;
        }
        getActivity().getActionBar().setTitle(
        getResources().getString(R.string.title_activity_settings_details) + theme);
    }

    // Declare Variables
    ListView list;
    TimeWindowArrayAdapter listviewadapter;
    List<TimeWindow> timeWindowsList = new ArrayList<TimeWindow>();

    private void updateListView() {
        // Locate the ListView in xml
        list = (ListView) getActivity().findViewById(R.id.lv);
        if (list != null) {
            timeWindowsList = timeWindowsModel.getThemeTimeWindows(timeWindowsModel.getSortedThemesNames().get(getShownIndex()));
            // Pass results to TimeWindowArrayAdapter Class
            listviewadapter = new TimeWindowArrayAdapter(getActivity(), R.layout.list_item, timeWindowsList);
            // Binds the Adapter to the ListView
            list.setAdapter(listviewadapter);
            list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
            // Capture ListView item click
            list.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

                @Override
                public void onItemCheckedStateChanged(ActionMode mode,
                                                      int position, long id, boolean checked) {
                    // Capture total checked items
                    final int checkedCount = list.getCheckedItemCount();
                    // Set the CAB title according to total checked items
                    mode.setTitle(checkedCount + " Selected");
                    // Calls toggleSelection method from TimeWindowArrayAdapter Class
                    listviewadapter.toggleSelection(position);
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_delete:
                            // Calls getSelectedIds method from TimeWindowArrayAdapter Class
                            SparseBooleanArray selected = listviewadapter.getSelectedIds();
                            // Captures all selected ids with a loop
                            for (int i = (selected.size() - 1); i >= 0; i--) {
                                if (selected.valueAt(i)) {
                                    TimeWindow selecteditem = listviewadapter
                                            .getItem(selected.keyAt(i));
                                    // Remove selected items following the ids
                                    listviewadapter.remove(selecteditem);
                                    timeWindowsModel.removeTimeWindow(timeWindowsModel.getSortedThemesNames().get(getShownIndex()), selecteditem);
                                }
                            }
                            // Close CAB
                            mode.finish();
                            return true;
                        default:
                            return false;
                    }
                }

                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    mode.getMenuInflater().inflate(R.menu.context_main, menu);
                    return true;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {
                    listviewadapter.removeSelection();
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }
            });


            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String theme = timeWindowsModel.getSortedThemesNames().get(getShownIndex());
                    int twID = timeWindowsModel.getTimeWindowByViewIndices(theme, position).getId();
                    showTimeWindowSettingsFragment(theme, twID);
                }
            });

        }
    }

    /**
     * Helper function to show the details of a selected item, either by
     * displaying a fragment in-place in the current UI, or starting a
     * whole new activity in which it is displayed.
     */
    void showTimeWindowSettingsFragment(String theme, int twID) {
        // Check what fragment is currently shown and replace.
        // Execute a transaction, replacing existing fragment with a new one inside the frame.
        FragmentTransaction ft;
        Fragment fragment = getFragmentManager().findFragmentById(R.id.time_windows_of_chosen_theme_frame);
        if (fragment != null) {
            // this is a dual pane layout and here is replaced the time windows fragment
            // with a detail of currently clicked time window
            ft = getFragmentManager().beginTransaction();
            ft.remove(this);
            ft.add(R.id.time_windows_of_chosen_theme_frame, TimeWindowSettingsFragment.newInstance(theme, twID));
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        } else {
            // single pane layout
            ft = getFragmentManager().beginTransaction();
            ft.remove(this);
            ft.add(android.R.id.content, TimeWindowSettingsFragment.newInstance(theme, twID));
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }
    }
}
