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
 * Use the {@link MeasureQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@EFragment(R.layout.fragment_measure_question)
public class MeasureQuestionFragment extends Fragment {


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MeasureQuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MeasureQuestionFragment newInstance() {
        MeasureQuestionFragment fragment = new MeasureQuestionFragment_();
        return fragment;
    }

    public MeasureQuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_measure_question, container, false);
    }


}
