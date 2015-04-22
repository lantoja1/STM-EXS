package cz.cvut.fel.pda.stm_exs.app.domain;

/**
 * @author jan.lantora
 */
public class Answer {
    private String id;
    private QaType type;
    private String text;

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
}
