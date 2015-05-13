package cz.cvut.fel.pda.stm_exs.app.view.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import cz.cvut.fel.pda.stm_exs.app.R;
import cz.cvut.fel.pda.stm_exs.app.data.DataModel;
import cz.cvut.fel.pda.stm_exs.app.domain.Answer;
import cz.cvut.fel.pda.stm_exs.app.domain.Question;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ClosedQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@EFragment(R.layout.fragment_closed_question)
public class ClosedQuestionFragment extends Fragment {

    @Bean
    protected DataModel dataModel;

    @ViewById(R.id.question_text)
    protected TextView questionTextView;

    @ViewById(R.id.question_answers)
    protected RadioGroup answersGroup;

    private Question question;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param question
     * @return A new instance of fragment ClosedQuestionFragment.
     */
    public static ClosedQuestionFragment newInstance(Question question) {
        ClosedQuestionFragment fragment = new ClosedQuestionFragment_();
        Bundle args = new Bundle();
        args.putString("id", question.getId());
        fragment.setArguments(args);
        return fragment;
    }

    public ClosedQuestionFragment() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_closed_question, container, false);
    }

    @AfterViews
    protected void initViews() {
        questionTextView.setText(question.getText());

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        for (int i = 0; i < question.getAnswers().size(); i++) {

            Answer answer = question.getAnswers().get(i);
            RadioButton button = new RadioButton(getActivity());
            button.setId(i);
            button.setLayoutParams(params);
            button.setPadding(15, 20, 0, 20);
            button.setTypeface(Typeface.DEFAULT_BOLD);
            button.setTextSize(19);
            button.setText(answer.getText());
            if (question.getSelectedAnswers() != null && !question.getSelectedAnswers().isEmpty() && question.getSelectedAnswers().contains(answer)) {
                button.setChecked(Boolean.TRUE);
            }

            answersGroup.addView(button);
        }


        answersGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (question.getSelectedAnswers() == null) {
                    question.setSelectedAnswers(new ArrayList<Answer>());
                }
                question.getSelectedAnswers().clear();

                question.getSelectedAnswers().add(question.getAnswers().get(checkedId));
            }
        });
        Log.i("Activity", "Fragment: Closed question views rendered");
    }
}
