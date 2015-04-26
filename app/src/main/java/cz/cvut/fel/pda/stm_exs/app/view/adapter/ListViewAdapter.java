package cz.cvut.fel.pda.stm_exs.app.view.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cz.cvut.fel.pda.stm_exs.app.R;
import cz.cvut.fel.pda.stm_exs.app.domain.TimeWindow;

public class ListViewAdapter extends ArrayAdapter<TimeWindow> {

    Activity context;
    List<TimeWindow> timeWindows;
    private SparseBooleanArray mSelectedItemsIds;
    
    public ListViewAdapter(Activity context, int resId, List<TimeWindow> timeWindows) {
        super(context, resId, timeWindows);
        mSelectedItemsIds = new SparseBooleanArray();
        this.context = context;
        this.timeWindows = timeWindows;
    }

    private class ViewHolder {
        TextView twTxt;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.twTxt = (TextView) convertView
                    .findViewById(R.id.lblListItem);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        TimeWindow tw = getItem(position);
        holder.twTxt.setText(tw.toString());
        convertView
                .setBackgroundColor(mSelectedItemsIds.get(position) ? 0x9934B5E4
                        : Color.TRANSPARENT);

        return convertView;
    }

    @Override
    public void add(TimeWindow tw) {
        timeWindows.add(tw);
        notifyDataSetChanged();
        Toast.makeText(context, timeWindows.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void remove(TimeWindow object) {
        // super.remove(object);
        timeWindows.remove(object);
        notifyDataSetChanged();
    }

    public List<TimeWindow> getTimeWindows() {
        return timeWindows;
    }

    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);

        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }

}
