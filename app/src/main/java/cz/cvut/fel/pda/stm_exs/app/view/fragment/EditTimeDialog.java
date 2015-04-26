package cz.cvut.fel.pda.stm_exs.app.view.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.text.format.DateFormat;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TimePicker;

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
public class EditTimeDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle b = getArguments();
        int hour = b.getInt("hour");
        int minute =  b.getInt("minute");
        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Bundle b = getArguments();
        String theme = b.getString("theme");
        int twID =  b.getInt("index");
        boolean startOrEnd = b.getBoolean("start");

        TimeWindowsModel timeWindowsModel = TimeWindowsModel_.getInstance_(getActivity());
        TimeWindow tw = timeWindowsModel.getTimeWindow(theme, twID);
        String oldKey = tw.toString();
        if (startOrEnd) {
            tw.setStart(new Time(hourOfDay, minute));
        } else {
            tw.setEnd(new Time(hourOfDay, minute));
        }
        timeWindowsModel.setTimeWindow(theme, tw, oldKey);

        // change text in the fragment
        ListView timeListView = (ListView) getActivity().findViewById(R.id.start_end_time);

        if (timeListView != null) {
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
        }
    }
}