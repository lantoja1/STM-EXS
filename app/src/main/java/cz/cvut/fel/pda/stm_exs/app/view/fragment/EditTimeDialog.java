package cz.cvut.fel.pda.stm_exs.app.view.fragment;


import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import java.util.Calendar;

import cz.cvut.fel.pda.stm_exs.app.R;
import cz.cvut.fel.pda.stm_exs.app.domain.TimeWindow;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditTimeDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        Bundle b = getArguments();
        int hour = b.getInt("hour");
        int minute =  b.getInt("minute");

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // TODO Do something with the time chosen by the user

    }
}