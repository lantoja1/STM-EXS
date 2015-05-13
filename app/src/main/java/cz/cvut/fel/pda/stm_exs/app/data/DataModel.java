package cz.cvut.fel.pda.stm_exs.app.data;

import cz.cvut.fel.pda.stm_exs.app.domain.Answer;
import cz.cvut.fel.pda.stm_exs.app.domain.Question;
import cz.cvut.fel.pda.stm_exs.app.domain.Sampling;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.json.JSONException;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jan.lantora
 */
@EBean
public class DataModel {
    Map<String, Question> questionMap;
    Map<String, Sampling> samplingMap;

    public Question getQuestion(String questionId) {
        return questionMap.get(questionId);
    }

    public List<Sampling> getSamplings(String theme) {
        List<Sampling> samplings = new ArrayList<Sampling>();
        for (Sampling sampling : samplingMap.values()) {
            if (sampling.getTheme().equals(theme)) {
                samplings.add(sampling);
            }
        }
        return samplings;
    }

    @AfterInject
    public void init() {
        questionMap = new ConcurrentHashMap<String, Question>();
        samplingMap = new ConcurrentHashMap<String, Sampling>();

//###########################-question1-####################################
        Question question1 = new Question();
        question1.setId("id_question1");
        question1.setText("How much money did you spend this week?");

        Answer q1Answer = new Answer();
        q1Answer.setText("less then 50");
        Answer q1Answer2 = new Answer();
        q1Answer2.setText("between 50 and 100");
        Answer q1Answer3 = new Answer();
        q1Answer3.setText("between 100 and 500");
        Answer q1Answer4 = new Answer();
        q1Answer4.setText("more then 500");

        List<Answer> q1Answers = new ArrayList<Answer>();
        q1Answers.add(q1Answer);
        q1Answers.add(q1Answer2);
        q1Answers.add(q1Answer3);
        q1Answers.add(q1Answer4);

        question1.setAnswers(q1Answers);

        questionMap.put(question1.getId(), question1);

//###########################-question2-####################################

        Question question2 = new Question();
        question2.setId("id_question2");
        question2.setText("What's on your mind?");

        questionMap.put(question2.getId(), question2);

//###########################-question3-####################################
        Question question3 = new Question();
        question3.setId("id_question3");
        question3.setText("Which of the following types of payment have you used?");

        Answer q3Answer = new Answer();
        q3Answer.setText("Credit card");
        Answer q3Answer2 = new Answer();
        q3Answer2.setText("Cash");
        Answer q3Answer3 = new Answer();
        q3Answer3.setText("Mobile Payment");
        Answer q3Answer4 = new Answer();
        q3Answer4.setText("E-Payment");

        List<Answer> q3Answers = new ArrayList<Answer>();
        q3Answers.add(q3Answer);
        q3Answers.add(q3Answer2);
        q3Answers.add(q3Answer3);
        q3Answers.add(q3Answer4);

        question3.setAnswers(q3Answers);

        questionMap.put(question3.getId(), question3);

        //###########################-question4-####################################

        Question question4 = new Question();
        question4.setId("id_question4");
        question4.setText("Currently I feel ... happy?");

        questionMap.put(question4.getId(), question4);

        //###########################-sampling1-####################################

        Sampling sampling = new Sampling();
        sampling.setId("id_sampling1");
        sampling.setTitle("Morning Shopping");
        sampling.setDate(new Date());
        sampling.setTheme("shopping");

        samplingMap.put(sampling.getId(), sampling);

        //###########################-sampling2-####################################

        Sampling sampling2 = new Sampling();
        sampling2.setId("id_sampling2");
        sampling2.setTitle("Afternoon Shopping");
        sampling2.setTheme("shopping");
        sampling2.setDate(new Date());

        samplingMap.put(sampling2.getId(), sampling2);

        //###########################-sampling3-####################################

        Sampling sampling3 = new Sampling();
        sampling3.setId("id_sampling3");
        sampling3.setTitle("All Day Shopping");
        sampling3.setDate(new Date());
        sampling3.setTheme("shopping");


        samplingMap.put(sampling3.getId(), sampling3);



        JSONStringer stringer= new JSONStringer();
        try {
            stringer.value(sampling);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
