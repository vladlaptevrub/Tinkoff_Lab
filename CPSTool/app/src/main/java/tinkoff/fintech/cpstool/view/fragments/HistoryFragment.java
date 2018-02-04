package tinkoff.fintech.cpstool.view.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;

import tinkoff.fintech.cpstool.R;
import tinkoff.fintech.cpstool.model.history.Party;
import tinkoff.fintech.cpstool.presenter.requests.Requests;
import tinkoff.fintech.cpstool.view.MainActivity;
import tinkoff.fintech.cpstool.view.adapters.RecyclerAdapter;
import tinkoff.fintech.cpstool.view.helpers.RecyclerItemTouchHelper;

public class HistoryFragment extends Fragment implements
        RecyclerAdapter.OnItemClicked,
        RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{

    private HistoryFragmentListener mListener;
    //private MainActivity mMainActivity;
    private Requests mPresenter;
    private RecyclerAdapter mRecyclerAdapter;
    private RecyclerView mRecyclerView;
    private ArrayAdapter mSearchAdapter;
    private AutoCompleteTextView mSearchInCacheEditText;

    public interface HistoryFragmentListener {
        void historyFragmentCallBack(String value);
        void historyFragmentToastCallBack(String value);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (HistoryFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + "MainActivity must implement HistoryFragmentListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_second, container, false);
        mPresenter = new Requests();

        mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager recyclerLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(recyclerLayoutManager);

        mSearchInCacheEditText = (AutoCompleteTextView) view
                .findViewById(R.id.searchInCacheEditText);

        updateRecyclerView();

        mSearchInCacheEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mListener.historyFragmentCallBack(mSearchAdapter.getItem(i).toString());
            }
        });

        return view;
    }

    @Override
    public void onItemClick(String value) {
        mListener.historyFragmentCallBack(value);
    }

    /*@Override
    public void onLongItemClick(String value) {
        openDeleteDialog(value);
    }*/

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        //openDeleteDialog(mSearchAdapter.getItem(position).toString());
        String value = mSearchAdapter.getItem(position).toString();
        mPresenter.deleteItem(value);
        updateRecyclerView();
        mListener.historyFragmentToastCallBack("'" + value + "' удален");
    }

    /*private void openDeleteDialog(final String value) {
        final AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                getActivity());
        quitDialog.setTitle("Удалить '" + value + "'?");

        quitDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mPresenter.deleteItem(value);
                updateRecyclerView();
                mListener.historyFragmentToastCallBack("'" + value + "' удален");
            }
        });

        quitDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        quitDialog.show();
    }*/

    private void updateRecyclerView(){
        mRecyclerAdapter = new RecyclerAdapter(mPresenter.getData());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mRecyclerAdapter);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);

        mRecyclerAdapter.setmOnClick(this);

        mSearchAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,
                getListOfTitles());
        mSearchInCacheEditText.setAdapter(mSearchAdapter);
        mSearchInCacheEditText.setThreshold(1);
    }

    private List<String> getListOfTitles(){
        List<Party> parties = mPresenter.getData();
        List<String> titles = new ArrayList<>();
        for (int i = 0; i < parties.size(); i++){
            titles.add(parties.get(i).getTitle());
        }
        return titles;
    }
}