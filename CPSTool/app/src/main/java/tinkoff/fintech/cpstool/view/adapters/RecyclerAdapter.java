package tinkoff.fintech.cpstool.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import tinkoff.fintech.cpstool.R;
import tinkoff.fintech.cpstool.model.history.Party;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private final static String TRUE = "true";
    private final static String FALSE = "false";

    private List<Party> mItems;
    private OnItemClicked mOnClick;

    public interface OnItemClicked {
        void onItemClick(String value);
        void onLongItemClick(String value);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mItemView;
        private ImageView mFavoriteIcon;
        private LinearLayout mPartyLayout;

        public ViewHolder(View v) {
            super(v);
            mItemView = (TextView)v.findViewById(R.id.elementTitleTV);
            mFavoriteIcon = (ImageView)v.findViewById(R.id.elementTitleIV);
            mPartyLayout = (LinearLayout)v.findViewById(R.id.party_layout);
        }
    }

    public RecyclerAdapter(List<Party> items) {
        this.mItems = items;
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
        final String value = mItems.get(position).getTitle();
        final String favoutite = mItems.get(position).getFavourite();
        holder.mItemView.setText(value);
        if (!favoutite.equals(TRUE)){
            holder.mFavoriteIcon.setVisibility(View.INVISIBLE);
        }
        holder.mPartyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClick.onItemClick(value);
            }
        });

        holder.mPartyLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mOnClick.onLongItemClick(value);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setmOnClick(OnItemClicked mOnClick) {
        this.mOnClick = mOnClick;
    }
}