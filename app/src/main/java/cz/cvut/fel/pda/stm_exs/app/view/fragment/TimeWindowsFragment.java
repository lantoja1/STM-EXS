package cz.cvut.fel.pda.stm_exs.app.view.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.androidannotations.annotations.EFragment;

import cz.cvut.fel.pda.stm_exs.app.R;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_time_windows)
public class TimeWindowsFragment extends Fragment {


    public TimeWindowsFragment() {
        // Required empty public constructor
    }


    public static TimeWindowsFragment newInstance(int index) {
        TimeWindowsFragment f = new TimeWindowsFragment();

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
    public void onCreate(Bundle state) {
        super.onCreate(state);

        //  setContentView(R.layout.a_example_list);

        View fab = getActivity().findViewById(R.id.add_interval);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "add interval", Toast.LENGTH_SHORT).show();
            }
        });

//        ListView listView = (ListView) findViewById(android.R.id.list);
//        listView.setAdapter(new ExampleAdapter());
//        listView.setOnTouchListener(new ShowHideOnScroll(fab));
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(view.getContext(), "on item click", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

}
