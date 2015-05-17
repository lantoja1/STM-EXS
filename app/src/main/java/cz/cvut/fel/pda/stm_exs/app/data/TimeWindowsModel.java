package cz.cvut.fel.pda.stm_exs.app.data;

import cz.cvut.fel.pda.stm_exs.app.domain.Time;
import cz.cvut.fel.pda.stm_exs.app.domain.TimeWindow;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@EBean(scope = EBean.Scope.Singleton)
public class TimeWindowsModel {
    public Map<String, TreeMap<String, TimeWindow>> themesMap;

    public TimeWindowsModel(){}

    @AfterInject
    public void init() {
        themesMap = new ConcurrentHashMap<String, TreeMap<String, TimeWindow>>();

        TreeMap<String, TimeWindow> theme1 = new TreeMap<String, TimeWindow>();

        TimeWindow tw1 = new TimeWindow(1);
        tw1.setStart(new Time(8, 0));
        tw1.setEnd(new Time(16, 30));

        theme1.put(tw1.toString(), tw1);
        themesMap.put("Work", theme1);

        TreeMap<String, TimeWindow> theme2 = new TreeMap<String, TimeWindow>();

        TimeWindow tw2 = new TimeWindow(1);
        tw2.setStart(new Time(16, 30));
        tw2.setEnd(new Time(21, 30));

        TimeWindow tw3 = new TimeWindow(2);
        tw3.setStart(new Time(10, 0));
        tw3.setEnd(new Time(20, 0));
        tw3.setDays(new boolean[]{false, false, false, false, false, true, true});

        theme2.put(tw2.toString(), tw2);
        theme2.put(tw3.toString(), tw3);
        themesMap.put("Health", theme2);

        TreeMap<String, TimeWindow> theme3 = new TreeMap<String, TimeWindow>();

        themesMap.put("shopping", theme3);
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
        int id = getNextId(themesMap.get(theme));
        TimeWindow tw = new TimeWindow(id);
        themesMap.get(theme).put(tw.toString(), tw);
    }

    private int getNextId(TreeMap<String, TimeWindow> map) {
        if (map.isEmpty()){
            return 0;
        }
        int max = 0;
        for (Map.Entry<String, TimeWindow> e : map.entrySet()){
            if (e.getValue().getId() > max){
                max = e.getValue().getId();
            }
        }
        return max + 1;
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

    public void removeTimeWindow(String theme, TimeWindow selecteditem) {
        TreeMap<String, TimeWindow> tws = themesMap.get(theme);
        String toDelete = "";
        for (Map.Entry<String, TimeWindow> e : tws.entrySet()){
            if (selecteditem.getId() == e.getValue().getId()){
                toDelete = e.getKey();
            }
        }
        tws.remove(toDelete);
        themesMap.put(theme, tws);
    }

    public TimeWindow getTimeWindow(String theme, int twID) {
        TreeMap<String, TimeWindow> tws = themesMap.get(theme);
        for (Map.Entry<String, TimeWindow> e : tws.entrySet()){
            if (twID == e.getValue().getId()){
                return e.getValue();
            }
        }
        return null;
    }
}
