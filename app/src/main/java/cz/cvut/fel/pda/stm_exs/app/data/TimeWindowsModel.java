package cz.cvut.fel.pda.stm_exs.app.data;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import cz.cvut.fel.pda.stm_exs.app.domain.Time;
import cz.cvut.fel.pda.stm_exs.app.domain.TimeWindow;

@EBean(scope = EBean.Scope.Singleton)
public class TimeWindowsModel {
    public Map<String, TreeMap<String, TimeWindow>> themesMap;

    public TimeWindowsModel(){}

    @AfterInject
    public void init() {
        themesMap = new ConcurrentHashMap<String, TreeMap<String, TimeWindow>>();

        TreeMap<String, TimeWindow> theme1 = new TreeMap<String, TimeWindow>();

        TimeWindow tw1 = new TimeWindow();
        tw1.setStart(new Time(8, 0));
        tw1.setEnd(new Time(16, 30));

        theme1.put(tw1.toString(), tw1);
        themesMap.put("Work", theme1);

        TreeMap<String, TimeWindow> theme2 = new TreeMap<String, TimeWindow>();

        TimeWindow tw2 = new TimeWindow();
        tw2.setStart(new Time(16, 30));
        tw2.setEnd(new Time(21, 30));

        TimeWindow tw3 = new TimeWindow();
        tw3.setStart(new Time(10, 0));
        tw3.setEnd(new Time(20, 0));
        tw3.setDays(new boolean[]{false, false, false, false, false, true, true});

        theme2.put(tw2.toString(), tw2);
        theme2.put(tw3.toString(), tw3);
        themesMap.put("Health", theme2);

        TreeMap<String, TimeWindow> theme3 = new TreeMap<String, TimeWindow>();

        themesMap.put("Shopping", theme3);
    }


    public List<TimeWindow> getThemeTimeWindows(String theme) {
        return new ArrayList(themesMap.get(theme).values());
    }

    public List<String> getListDataHeader(int index){

        ArrayList<String> themes = new ArrayList<String>(themesMap.keySet());
        Collections.sort(themes);
        String theme = themes.get(index);
        ArrayList<TimeWindow> tw = new ArrayList<TimeWindow>(themesMap.get(theme).values());
        List<String> list = new ArrayList<String>();
        for (TimeWindow t : tw){
            list.add(t.toString());
        }
        Collections.sort(list);
        return list;
    }

    public List<String> getSortedThemesNames() {
        ArrayList<String> themes = new ArrayList<String>(themesMap.keySet());
        Collections.sort(themes);
        return themes;
    }

    public void addTimeWindow(String theme) {
        TimeWindow tw = new TimeWindow();
        themesMap.get(theme).put(tw.toString(), tw);
    }

    /**
     * Returns the TimeWindow object according to selected view by groupPosition and childPosition.
     * @param theme
     * @param groupPosition
     * @return
     */
    public TimeWindow getTimeWindowByViewIndices(String theme, int groupPosition) {
        return getThemeTimeWindows(theme).get(groupPosition);
    }

    public void setTimeWindow(String theme, TimeWindow tw, String oldKey) {
        if (!oldKey.isEmpty()){
            themesMap.get(theme).remove(oldKey);
        }
        themesMap.get(theme).put(tw.toString(), tw);
    }
}
