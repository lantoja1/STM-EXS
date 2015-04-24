package cz.cvut.fel.pda.stm_exs.app.domain;

public class TimeWindow {
    private Time start;
    private Time end;
    private boolean[] days;

    public TimeWindow(){
        start = new Time(0, 0);
        end = new Time(0, 0);
        days = new boolean[]{true, true, true, true, true, false, false};
    }

    public Time getStart() {
        return start;
    }

    public void setStart(Time start) {
        this.start = start;
    }

    public Time getEnd() {
        return end;
    }

    public void setEnd(Time end) {
        this.end = end;
    }

    public boolean[] getDays() {
        return days;
    }

    public void setDays(boolean[] days) {
        this.days = days;
    }

    public void setDay(int index, boolean b){
        this.days[index] = b;
    }
}