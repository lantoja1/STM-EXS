package cz.cvut.fel.pda.stm_exs.app.data;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cz.cvut.fel.pda.stm_exs.app.domain.Time;
import cz.cvut.fel.pda.stm_exs.app.domain.TimeWindow;

@EBean
public class TimeWindowsModel {
    public Map<String, Map<Integer, TimeWindow>> themesMap;

    public Map<Integer, TimeWindow> getThemeSettings(String themeId) {
        return themesMap.get(themeId);
    }

    public TimeWindowsModel(){}

    @AfterInject
    public void init() {
        themesMap = new ConcurrentHashMap<String, Map<Integer, TimeWindow>>();

        Map<Integer, TimeWindow> theme1 = new ConcurrentHashMap<Integer, TimeWindow>();

        TimeWindow tw1 = new TimeWindow();
        tw1.setStart(new Time(8, 0));
        tw1.setEnd(new Time(16, 30));

        theme1.put(1, tw1);
        themesMap.put("Work", theme1);

        Map<Integer, TimeWindow> theme2 = new ConcurrentHashMap<Integer, TimeWindow>();

        TimeWindow tw2 = new TimeWindow();
        tw2.setStart(new Time(16, 30));
        tw2.setEnd(new Time(21, 30));

        TimeWindow tw3 = new TimeWindow();
        tw3.setStart(new Time(10, 0));
        tw3.setEnd(new Time(20, 0));
        tw3.setDays(new boolean[]{false, false, false, false, false, true, true});

        theme2.put(1, tw2);
        theme2.put(2, tw3);
        themesMap.put("Health", theme2);

        Map<Integer, TimeWindow> theme3 = new ConcurrentHashMap<Integer, TimeWindow>();

        themesMap.put("Shopping", theme3);
    }

}
