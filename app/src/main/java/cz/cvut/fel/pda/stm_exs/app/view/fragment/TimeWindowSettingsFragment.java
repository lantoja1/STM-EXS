package cz.cvut.fel.pda.stm_exs.app.view.fragment;


import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.cvut.fel.pda.stm_exs.app.R;
import cz.cvut.fel.pda.stm_exs.app.data.TimeWindowsModel;
import cz.cvut.fel.pda.stm_exs.app.data.TimeWindowsModel_;
import cz.cvut.fel.pda.stm_exs.app.domain.Time;
import cz.cvut.fel.pda.stm_exs.app.domain.TimeWindow;

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

    public static Fragment newInstance(String theme, int twID) {
        TimeWindowSettingsFragment f = new TimeWindowSettingsFragment_();
        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("TWindex", twID);
        args.putString("theme", theme);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        updateView();
    }

    private void updateView(){
        ListView timeListView = (ListView) getActivity().findViewById(R.id.start_end_time);

        if (timeListView != null) {

            Bundle b = getArguments();
            String theme = b.getString("theme");
            int twID = b.getInt("TWindex");
            TimeWindowsModel timeWindowsModel = TimeWindowsModel_.getInstance_(getActivity());
            TimeWindow tw = timeWindowsModel.getTimeWindow(theme, twID);

            List<Map<String, String>> data = new ArrayList<Map<String, String>>();
            Map<String, String> start = new HashMap<String, String>(2);
            start.put("title", "Začátek časového okna");
            start.put("value", tw.getStart().toString());
            data.add(start);
            Map<String, String> end = new HashMap<String, String>(2);
            end.put("title", "Konec časového okna");
            end.put("value", tw.getEnd().toString());
            data.add(end);

            SimpleAdapter adapter = new SimpleAdapter(getActivity(), data,
                    android.R.layout.simple_list_item_2,
                    new String[] {"title", "value"},
                    new int[] {android.R.id.text1,
                            android.R.id.text2});
            timeListView.setAdapter(adapter);

            // add time window button click

            timeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Bundle b = getArguments();
                    String theme = b.getString("theme");
                    int twID = b.getInt("TWindex");
                    TimeWindowsModel timeWindowsModel = TimeWindowsModel_.getInstance_(getActivity());

                    TimeWindow tw = timeWindowsModel.getTimeWindow(theme, twID);
                    switch (position) {
                        case 0:
                            DialogFragment newFragment = new EditTimeDialog();
                            Bundle bundle = new Bundle();
                            bundle.putInt("hour", tw.getStart().getHour());
                            bundle.putInt("minute", tw.getStart().getMinute());
                            bundle.putString("theme", theme);
                            bundle.putInt("index", twID);
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
                            bundle1.putInt("index", twID);
                            bundle1.putBoolean("start", false);
                            newFragment1.setArguments(bundle1);
                            newFragment1.show(getActivity().getFragmentManager(), "timePicker");
                            break;
                    }
                }
            });

            // add listeners and model values to days checkboxes
            CheckBox po = (CheckBox) getActivity().findViewById(R.id.po);
            po.setChecked(tw.getDay(0));
            po.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Bundle b = getArguments();
                    String theme = b.getString("theme");
                    int twID = b.getInt("TWindex");
                    TimeWindowsModel timeWindowsModel = TimeWindowsModel_.getInstance_(getActivity());
                    TimeWindow tw = timeWindowsModel.getTimeWindow(theme, twID);
                    setDayInModel(theme, timeWindowsModel, tw, 0, isChecked);
                }
            });

            CheckBox ut = (CheckBox) getActivity().findViewById(R.id.ut);
            ut.setChecked(tw.getDay(1));
            ut.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Bundle b = getArguments();
                    String theme = b.getString("theme");
                    int twID = b.getInt("TWindex");
                    TimeWindowsModel timeWindowsModel = TimeWindowsModel_.getInstance_(getActivity());
                    TimeWindow tw = timeWindowsModel.getTimeWindow(theme, twID);
                    setDayInModel(theme, timeWindowsModel, tw, 1, isChecked);
                }
            });

            CheckBox st = (CheckBox) getActivity().findViewById(R.id.st);
            st.setChecked(tw.getDay(2));
            st.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Bundle b = getArguments();
                    String theme = b.getString("theme");
                    int twID = b.getInt("TWindex");
                    TimeWindowsModel timeWindowsModel = TimeWindowsModel_.getInstance_(getActivity());
                    TimeWindow tw = timeWindowsModel.getTimeWindow(theme, twID);
                    setDayInModel(theme, timeWindowsModel, tw, 2, isChecked);
                }
            });

            CheckBox ct = (CheckBox) getActivity().findViewById(R.id.ct);
            ct.setChecked(tw.getDay(3));
            ct.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Bundle b = getArguments();
                    String theme = b.getString("theme");
                    int twID = b.getInt("TWindex");
                    TimeWindowsModel timeWindowsModel = TimeWindowsModel_.getInstance_(getActivity());
                    TimeWindow tw = timeWindowsModel.getTimeWindow(theme, twID);
                    setDayInModel(theme, timeWindowsModel, tw, 3, isChecked);
                }
            });

            CheckBox pa = (CheckBox) getActivity().findViewById(R.id.pa);
            pa.setChecked(tw.getDay(4));
            pa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Bundle b = getArguments();
                    String theme = b.getString("theme");
                    int twID = b.getInt("TWindex");
                    TimeWindowsModel timeWindowsModel = TimeWindowsModel_.getInstance_(getActivity());
                    TimeWindow tw = timeWindowsModel.getTimeWindow(theme, twID);
                    setDayInModel(theme, timeWindowsModel, tw, 4, isChecked);
                }
            });

            CheckBox so = (CheckBox) getActivity().findViewById(R.id.so);
            so.setChecked(tw.getDay(5));
            so.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Bundle b = getArguments();
                    String theme = b.getString("theme");
                    int twID = b.getInt("TWindex");
                    TimeWindowsModel timeWindowsModel = TimeWindowsModel_.getInstance_(getActivity());
                    TimeWindow tw = timeWindowsModel.getTimeWindow(theme, twID);
                    setDayInModel(theme, timeWindowsModel, tw, 5, isChecked);
                }
            });

            CheckBox ne = (CheckBox) getActivity().findViewById(R.id.ne);
            ne.setChecked(tw.getDay(6));
            ne.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Bundle b = getArguments();
                    String theme = b.getString("theme");
                    int twID = b.getInt("TWindex");
                    TimeWindowsModel timeWindowsModel = TimeWindowsModel_.getInstance_(getActivity());
                    TimeWindow tw = timeWindowsModel.getTimeWindow(theme, twID);
                    setDayInModel(theme, timeWindowsModel, tw, 6, isChecked);
                }
            });
        }
    }

    private void setDayInModel(String theme, TimeWindowsModel timeWindowsModel, TimeWindow tw, int index, boolean isChecked){
        String oldKey = tw.toString();
        tw.setDay(index, isChecked);
        timeWindowsModel.setTimeWindow(theme, tw, oldKey);
    }
}
