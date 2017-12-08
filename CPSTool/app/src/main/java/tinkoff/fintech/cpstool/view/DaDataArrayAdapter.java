package tinkoff.fintech.cpstool.view;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import java.util.List;

public class DaDataArrayAdapter<String> extends ArrayAdapter<String> {

    private Filter filter = new KNoFilter();
    private List<String> items;

    public DaDataArrayAdapter(Context context, int resource, List<String> items) {
        super(context, resource, items);
        this.items = items;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private class KNoFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence arg0) {
            FilterResults result = new FilterResults();
            result.values = items;
            result.count = items.size();
            return result;
        }

        @Override
        protected void publishResults(CharSequence arg0, FilterResults arg1) {
            notifyDataSetChanged();
        }
    }
}