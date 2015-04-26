package cz.cvut.fel.pda.stm_exs.app.view.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

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
        int index =  b.getInt("index");
        boolean start = b.getBoolean("start");

        TimeWindowsModel timeWindowsModel = TimeWindowsModel_.getInstance_(getActivity());
        TimeWindow tw = timeWindowsModel.getTimeWindowByViewIndices(theme, index);
        String oldKey = tw.toString();
        if (start) {
            tw.setStart(new Time(hourOfDay, minute));
        } else {
            tw.setEnd(new Time(hourOfDay, minute));
        }
        timeWindowsModel.setTimeWindow(theme, tw, oldKey);
    }
}