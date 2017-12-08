package tinkoff.fintech.cpstool.view;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.realm.RealmResults;
import tinkoff.fintech.cpstool.R;
import tinkoff.fintech.cpstool.model.history.Party;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    //private List<String> items;
    private List<Party> items;

    private final static String TRUE = "true";
    private final static String FALSE = "false";

    private OnItemClicked onClick;

    public interface OnItemClicked {
        void onItemClick(String value);
        void onLongItemClick(String value);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemView;
        public ImageView favoriteIcon;
        public LinearLayout partyLayout;

        public ViewHolder(View v) {
            super(v);
            itemView = (TextView)v.findViewById(R.id.elementTitleTV);
            favoriteIcon = (ImageView)v.findViewById(R.id.elementTitleIV);
            partyLayout = (LinearLayout)v.findViewById(R.id.party_layout);
        }
    }

    public RecyclerAdapter(List<Party> items) {
        this.items = items;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.party_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String value = items.get(position).getTitle();
        final String favoutite = items.get(position).getFavourite();
        holder.itemView.setText(value);
        if (!favoutite.equals(TRUE)){
            holder.favoriteIcon.setVisibility(View.INVISIBLE);
        }
        holder.partyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onItemClick(value);
            }
        });

        holder.partyLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onClick.onLongItemClick(value);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick=onClick;
    }
}