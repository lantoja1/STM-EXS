package cz.cvut.fel.pda.stm_exs.app.data;

import cz.cvut.fel.pda.stm_exs.app.domain.Question;
import cz.cvut.fel.pda.stm_exs.app.domain.Sampling;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jan.lantora
 */
@EBean(scope = EBean.Scope.Singleton)
public class DataModel {
    Map<String, Question> questionMap;
    Map<String, Sampling> samplingMap;

    public Question getQuestion(String questionId) {
        return questionMap.get(questionId);
    }

    /**
     * finds convenient sampling according to time window and timestamp of the sampling
     *
     * @return the oldest Sampling with proper theme
     */
    public Sampling getSampling() {
        Collection<Sampling> samplings = samplingMap.values();
        if (samplings != null && !samplings.isEmpty()) {
            return samplings.iterator().next();
        }
        return null;
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

    public void saveSampling(Sampling sampling) {
        samplingMap.put(sampling.getId(), sampling);

        for (Question question : sampling.getQuestions()) {
            questionMap.put(question.getId(), question);
        }
    }

    public boolean hasSampling(Sampling sampling) {
        return samplingMap.containsKey(sampling.getId());
    }

    public void clear() {
        questionMap.clear();
        samplingMap.clear();
    }

    @AfterInject
    public void init() {
        questionMap = new ConcurrentHashMap<String, Question>();
        samplingMap = new ConcurrentHashMap<String, Sampling>();

//        GsonBuilder builder = new GsonBuilder();
//        builder.setPrettyPrinting().serializeNulls();
//
//        Gson gson = builder.create();
//
//        System.out.println("OBJECT SAMPLING TO JSON:");
//        System.out.println(gson.toJson(sampling));

        String sampl1 = "http://private-650dd-expsam.apiary-mock.com/samplings/shopping/1";
        String sampl2 = "http://private-650dd-expsam.apiary-mock.com/samplings/work/1";

    }

}
