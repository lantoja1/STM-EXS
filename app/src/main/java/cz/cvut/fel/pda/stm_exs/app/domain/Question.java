package cz.cvut.fel.pda.stm_exs.app.domain;

import java.util.List;

/**
 * @author jan.lantora
 */
public class Question {
    private String id;
    private QaType type;
    private String text;
    private List<Answer> answers;
    private List<Answer> selectedAnswers;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public QaType getType() {
        return type;
    }

    public void setType(QaType type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public List<Answer> getSelectedAnswers() {
        return selectedAnswers;
    }

    public void setSelectedAnswers(List<Answer> selectedAnswers) {
        this.selectedAnswers = selectedAnswers;
    }
}
