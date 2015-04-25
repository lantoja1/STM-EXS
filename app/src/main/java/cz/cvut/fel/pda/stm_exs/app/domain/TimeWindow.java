package cz.cvut.fel.pda.stm_exs.app.domain;

import java.util.Arrays;

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

    @Override
    public String toString() {
        return start.getHour() + ":" + (start.getMinute() < 10 ? "0" : "") + start.getMinute() + "-"
                + end.getHour() + ":" + (end.getMinute() < 10 ? "0" : "") + end.getMinute()
                + " " + daysToString(days);
    }

    private String daysToString(boolean[] days) {
        String[] daysSt = new String[]{"Po", "Út", "St", "Čt", "Pá", "So", "Ne"};
        StringBuilder st = new StringBuilder("");
        for (int i = 0; i < 7; i++){
            if (days[i]){
                st.append(daysSt[i] + ", ");
            }
        }
        if (st.length() > 0){
            st.delete(st.length() - 2, st.length() - 1);
        }
        return st.toString();
    }

}