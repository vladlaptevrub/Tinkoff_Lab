package tinkoff.fintech.cpstool.view.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class HistoryFragment extends Fragment implements RecyclerAdapter.OnItemClicked {

    private HistoryFragmentListener mListener;
    private MainActivity mMainActivity;
    private Requests mPresenter;
    private RecyclerAdapter mRecyclerAdapter;
    private RecyclerView mRecyclerView;
    private ArrayAdapter mSearchAdapter;
    private AutoCompleteTextView mSearchInCacheEditText;

    public interface HistoryFragmentListener {
        void historyFragmentCallBack(String value);
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
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter = new Requests();

        mRecyclerView = (RecyclerView)getActivity().findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager recyclerLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(recyclerLayoutManager);

        mMainActivity = (MainActivity)getActivity();

        mSearchInCacheEditText = (AutoCompleteTextView) getActivity()
                .findViewById(R.id.searchInCacheEditText);

        updateRecyclerView();

        mSearchInCacheEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mListener.historyFragmentCallBack(mSearchAdapter.getItem(i).toString());
            }
        });
    }

    @Override
    public void onItemClick(String value) {
        mListener.historyFragmentCallBack(value);
    }

    @Override
    public void onLongItemClick(String value) {
        openDeleteDialog(value);
    }

    private void openDeleteDialog(final String value) {
        final AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                getActivity());
        quitDialog.setTitle("Удалить '" + value + "'?");

        quitDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mPresenter.deleteItem(value);
                updateRecyclerView();
                mMainActivity.toastMessage("'" + value + "' удален");
            }
        });

        quitDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        quitDialog.show();
    }

    private void updateRecyclerView(){
        mRecyclerAdapter = new RecyclerAdapter(mPresenter.getData());
        mRecyclerView.setAdapter(mRecyclerAdapter);
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