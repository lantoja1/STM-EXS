package cz.cvut.fel.pda.stm_exs.app.view.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import cz.cvut.fel.pda.stm_exs.app.R;
import cz.cvut.fel.pda.stm_exs.app.domain.Sampling;

import java.util.List;

public class SamplingsArrayAdapter extends ArrayAdapter<Sampling> {

    Activity context;
    List<Sampling> sampling;
    private SparseBooleanArray mSelectedItemsIds;

    public SamplingsArrayAdapter(Activity context, int resId, List<Sampling> samplings) {
        super(context, resId, samplings);
        mSelectedItemsIds = new SparseBooleanArray();
        this.context = context;
        this.sampling = samplings;
    }

    private class ViewHolder {
        TextView twTxt;
        TextView dateTxt;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.answers_list_item, null);
            holder = new ViewHolder();

            holder.twTxt = (TextView) convertView.findViewById(R.id.lblListItem);
            holder.dateTxt = (TextView) convertView.findViewById(R.id.date_text);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Sampling tw = getItem(position);

        //@TODO change text
        holder.twTxt.setText(tw.getTitle());
        holder.dateTxt.setText(tw.getDate().toString());

        convertView
                .setBackgroundColor(mSelectedItemsIds.get(position) ? 0x9934B5E4
                        : Color.TRANSPARENT);

        return convertView;
    }

    @Override
    public void add(Sampling tw) {
        sampling.add(tw);
        notifyDataSetChanged();
    }

    @Override
    public void remove(Sampling object) {
        // super.remove(object);
        sampling.remove(object);
        notifyDataSetChanged();
    }

    public List<Sampling> getSampling() {
        return sampling;
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
