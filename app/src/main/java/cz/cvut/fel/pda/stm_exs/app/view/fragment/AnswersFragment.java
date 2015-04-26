package cz.cvut.fel.pda.stm_exs.app.view.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import cz.cvut.fel.pda.stm_exs.app.R;
import cz.cvut.fel.pda.stm_exs.app.data.DataModel;
import cz.cvut.fel.pda.stm_exs.app.view.activity.QuestionActivity_;
import cz.cvut.fel.pda.stm_exs.app.view.adapter.SamplingsArrayAdapter;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_answers)
public class AnswersFragment extends Fragment {

    @Bean
    protected DataModel dataModel;


    public AnswersFragment() {
        // Required empty public constructor
    }


    public static AnswersFragment newInstance(int index) {
        AnswersFragment f = new AnswersFragment_();

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

        return inflater.inflate(R.layout.fragment_answers, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        updateListView();
    }


    private void updateListView() {
        // Locate the ListView in xml
        ListView list = (ListView) getActivity().findViewById(R.id.lv);
        if (list != null) {
            // Pass results to TimeWindowArrayAdapter Class


            ListAdapter listviewadapter = new SamplingsArrayAdapter(getActivity(), R.layout.answers_list_item, dataModel.getSamplings("shopping"));
            // Binds the Adapter to the ListView
            list.setAdapter(listviewadapter);
            list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), QuestionActivity_.class);
                    startActivity(intent);
                }
            });

        }
    }

}
