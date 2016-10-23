package edu.p.lodz.pl.studentshenchman.settings.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.p.lodz.pl.studentshenchman.database.models.Type;

/**
 * @author Michal Warcholinski
 */
public class TypeAdapter extends BaseAdapter {
    private static final String TAG = TypeAdapter.class.getName();

    private Context mContext;
    private List<Type> mValues;

    public TypeAdapter(Context context, List<Type> values) {
        mContext = context;
        init(values);
    }

    private void init(List<Type> values) {
        mValues = new ArrayList<>(values);
        Type type = new Type();
        type.setId(Long.MIN_VALUE);
        type.setExternalId(Long.MIN_VALUE);
        type.setName("choose");
        mValues.add(0, type);
    }

    @Override
    public int getCount() {
        return mValues.size();
    }

    @Override
    public Type getItem(int position) {
        return mValues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(android.R.layout.simple_list_item_single_choice, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Type type = mValues.get(position);

        viewHolder.text.setText(type.getName());

        return convertView;
    }


    public void setValues(List<Type> newValues) {
        init(newValues);
        notifyDataSetChanged();
    }

    public int getPosForId(long id) {
        int i = 0;
        Iterator<Type> iterator = mValues.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getExternalId() == id) {
                break;
            }
            i++;
        }
        return i;
    }

    private class ViewHolder {

        public TextView text;

        public ViewHolder(View convertView) {
            text = (TextView) convertView.findViewById(android.R.id.text1);
        }
    }
}
