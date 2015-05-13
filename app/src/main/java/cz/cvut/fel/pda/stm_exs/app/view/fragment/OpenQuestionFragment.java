package cz.cvut.fel.pda.stm_exs.app.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
 * Activities that contain this fragment must implement the
 * Use the {@link OpenQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@EFragment(R.layout.fragment_open_question)
public class OpenQuestionFragment extends Fragment {

    @Bean
    protected DataModel dataModel;

    @ViewById(R.id.question_text)
    protected TextView questionTextView;

    @ViewById(R.id.question_answer)
    protected EditText answerEditText;

    private Question question;




    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment OpenQuestionFragment.
     * @param question
     */
    public static OpenQuestionFragment newInstance(Question question) {
        OpenQuestionFragment fragment = new OpenQuestionFragment_();
        Bundle args = new Bundle();
        args.putString("id", question.getId());
        fragment.setArguments(args);

        return fragment;
    }

    public OpenQuestionFragment() {
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
        return inflater.inflate(R.layout.fragment_open_question, container, false);
    }

    @AfterViews
    protected void initViews() {
        questionTextView.setText(question.getText());
        if (question.getSelectedAnswers() != null && !question.getSelectedAnswers().isEmpty()) {
            answerEditText.setText(question.getSelectedAnswers().get(0).getText());
        }
        Log.i("Activity", "Fragment: Open question views rendered");
    }

    @AfterTextChange(R.id.question_answer)
    protected void afterTextChangedOnHelloTextView() {
        if (question.getSelectedAnswers() == null) {
            question.setSelectedAnswers(new ArrayList<Answer>());
        }
        String answerText = answerEditText.getText().toString();
        if (!answerText.isEmpty()) {
            question.getSelectedAnswers().clear();
            Answer answer = new Answer();
            answer.setText(answerText);
            answer.setType(QaType.OPEN);
            answer.setId("id_answer1");
            question.getSelectedAnswers().add(answer);
        }
    }


}
