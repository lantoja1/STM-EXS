package cz.cvut.fel.pda.stm_exs.app.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import cz.cvut.fel.pda.stm_exs.app.R;
import cz.cvut.fel.pda.stm_exs.app.data.DataModel;
import cz.cvut.fel.pda.stm_exs.app.domain.Answer;
import cz.cvut.fel.pda.stm_exs.app.domain.QaType;
import cz.cvut.fel.pda.stm_exs.app.domain.Question;
import org.androidannotations.annotations.*;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeasureQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@EFragment(R.layout.fragment_measure_question)
public class MeasureQuestionFragment extends Fragment {
    @Bean
    protected DataModel dataModel;

    @ViewById(R.id.question_text)
    protected TextView questionTextView;

    @ViewById(R.id.question_answer)
    protected SeekBar answerSeekBar;

    private Question question;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MeasureQuestionFragment.
     */
    public static MeasureQuestionFragment newInstance(Question question) {
        MeasureQuestionFragment fragment = new MeasureQuestionFragment_();
        Bundle args = new Bundle();
        args.putString("id", question.getId());
        fragment.setArguments(args);
        return fragment;
    }

    public MeasureQuestionFragment() {
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
        return inflater.inflate(R.layout.fragment_measure_question, container, false);
    }

    @AfterViews
    protected void initViews() {
        questionTextView.setText(question.getText());
        if (question.getSelectedAnswers() != null && !question.getSelectedAnswers().isEmpty()) {
            answerSeekBar.setProgress(Integer.parseInt(question.getSelectedAnswers().get(0).getText()));
        }
        Log.i("Activity", "Fragment: Measure question views rendered");
    }

    @SeekBarProgressChange(R.id.question_answer)
    void onProgressChangeOnSeekBar(SeekBar seekBar, int progress) {
        if (question.getSelectedAnswers() == null) {
            question.setSelectedAnswers(new ArrayList<Answer>());
        }
        question.getSelectedAnswers().clear();
        Answer answer = new Answer();
        answer.setText(String.valueOf(progress));
        answer.setType(QaType.MEASURE);
        answer.setId("id_answer1");
        question.getSelectedAnswers().add(answer);
    }


}
