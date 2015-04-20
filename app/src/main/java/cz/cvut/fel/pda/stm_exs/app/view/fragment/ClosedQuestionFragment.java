package cz.cvut.fel.pda.stm_exs.app.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cz.cvut.fel.pda.stm_exs.app.R;
import org.androidannotations.annotations.EFragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ClosedQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@EFragment(R.layout.fragment_closed_question)
public class ClosedQuestionFragment extends Fragment {

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ClosedQuestionFragment.
     */
    public static ClosedQuestionFragment newInstance() {
        ClosedQuestionFragment fragment = new ClosedQuestionFragment_();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ClosedQuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_closed_question, container, false);
    }
}
