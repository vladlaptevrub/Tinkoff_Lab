package tinkoff.fintech.cpstool.view.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import java.util.List;

public class DaDataArrayAdapter<String> extends ArrayAdapter<String> {

    private Filter mFilter = new KNoFilter();
    private List<String> mItems;

    public DaDataArrayAdapter(Context context, int resource, List<String> items) {
        super(context, resource, items);
        this.mItems = items;
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private class KNoFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence arg0) {
            FilterResults result = new FilterResults();
            result.values = mItems;
            result.count = mItems.size();
            return result;
        }

        @Override
        protected void publishResults(CharSequence arg0, FilterResults arg1) {
            notifyDataSetChanged();
        }
    }
}