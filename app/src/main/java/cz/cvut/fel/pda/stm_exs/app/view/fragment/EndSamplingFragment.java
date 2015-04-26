package cz.cvut.fel.pda.stm_exs.app.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cz.cvut.fel.pda.stm_exs.app.R;
import cz.cvut.fel.pda.stm_exs.app.view.activity.MainActivity_;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EndSamplingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@EFragment(R.layout.fragment_end_sampling)
public class EndSamplingFragment extends Fragment {


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EndSamplingFragment.
     */
    public static EndSamplingFragment newInstance() {
        EndSamplingFragment fragment = new EndSamplingFragment_();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public EndSamplingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_end_sampling, container, false);
    }

    @Click(R.id.action_end)
    public void endSampling() {
        Intent intent = new Intent(getActivity(), MainActivity_.class);
        startActivity(intent);
    }

}
