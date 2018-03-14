package edu.wsdl.mobilemink;

/**
 * Created by wes on 12/18/14.
 */

import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.text.format.DateUtils;
import android.util.Log;

import java.util.ArrayList;

/**
 * TODO:Document my code? Why do you think they call it "code"?
 */
public class MementoArrayAdapter extends BaseAdapter {

    private ArrayList<Memento> mementos;
    private ViewArchiveActivity context;

    public MementoArrayAdapter(ViewArchiveActivity context) {
        this.context = context;

        if(context.getTimeMap() != null) {
            mementos = context.getTimeMap().getMementos();
            // TODO: Check for IA+AIT duplicate mementos
        }
        else {
            mementos = new ArrayList<Memento>();
        }
    }

    public void setTimeMap(TimeMap timeMap) {
        mementos = timeMap.getMementos();

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mementos.size();
    }

    @Override
    public Object getItem(int i) {
        return mementos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View oldView, ViewGroup viewGroup) {
        Memento memento = mementos.get(i);

        View v = oldView;
        if(v == null) {
            v = View.inflate(context, R.layout.archive_list_element, null);
        }

        //DateUtils.formatDateTime(context, epochTimeInMs, DateUtils.FORMAT_ABBREV_MONTH | DateUtils.FORMAT_SHOW_DATE);
        ((TextView) v.findViewById(R.id.element_title)).setText(memento.getTime(DateFormat.getDateFormat(context)));
        ((ImageView) v.findViewById(R.id.screenBadge)).setImageResource(memento.getScreenType().getBadgeDrawable());

        return v;
    }
}
