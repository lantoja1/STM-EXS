package cz.cvut.fel.pda.stm_exs.app.domain;

import java.util.Date;
import java.util.List;

/**
 * @author jan.lantora
 */
public class Sampling {
    private String id;
    private String theme;
    private List<Question> questions;
    private Date date;
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title + ": " + date;
    }
}
