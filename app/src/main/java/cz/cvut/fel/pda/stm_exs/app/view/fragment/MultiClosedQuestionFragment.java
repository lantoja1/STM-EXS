package cz.cvut.fel.pda.stm_exs.app.view.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import cz.cvut.fel.pda.stm_exs.app.R;
import cz.cvut.fel.pda.stm_exs.app.data.DataModel;
import cz.cvut.fel.pda.stm_exs.app.domain.Answer;
import cz.cvut.fel.pda.stm_exs.app.domain.Question;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MultiClosedQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@EFragment(R.layout.fragment_multi_closed_question)
public class MultiClosedQuestionFragment extends Fragment {

    @Bean
    protected DataModel dataModel;

    @ViewById(R.id.question_text)
    protected TextView questionTextView;

    @ViewById(R.id.question_answers)
    protected ViewGroup answersGroup;

    private Question question;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MultiClosedQuestionFragment.
     */
    public static MultiClosedQuestionFragment newInstance(Question question) {
        MultiClosedQuestionFragment fragment = new MultiClosedQuestionFragment_();
        Bundle args = new Bundle();
        args.putString("id", question.getId());
        fragment.setArguments(args);
        return fragment;
    }

    public MultiClosedQuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (question == null) {
            String questionId = getArguments().getString("id");
            question = dataModel.getQuestion(questionId);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_multi_closed_question, container, false);
    }

    @AfterViews
    protected void initViews() {
        questionTextView.setText(question.getText());

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        for (Answer answer : question.getAnswers()) {
            CheckBox checkBox = new CheckBox(getActivity());
            checkBox.setLayoutParams(params);
            checkBox.setPadding(15, 20, 0, 20);
            checkBox.setTypeface(Typeface.DEFAULT_BOLD);
            checkBox.setTextSize(19);
            checkBox.setText(answer.getText());

            answersGroup.addView(checkBox);
        }
        Log.i("Activity", "Fragment: MultiClosed question views rendered");
    }


}
