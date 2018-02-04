package tinkoff.fintech.cpstool.view.fragments;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;

import tinkoff.fintech.cpstool.R;
import tinkoff.fintech.cpstool.presenter.requests.Requests;
import tinkoff.fintech.cpstool.view.adapters.DaDataArrayAdapter;
import tinkoff.fintech.cpstool.view.interfaces.OnSuggestionsListener;
import tinkoff.fintech.cpstool.model.utils.ServerUtils;

public class SearchFragment extends Fragment implements
        TextWatcher,
        OnSuggestionsListener {

    private static final List<String> EMPTY = new ArrayList<>();

    private DaDataArrayAdapter<String> mAdapter;
    private SearchFragmentListener mListener;
    private AutoCompleteTextView mTextView;

    public interface SearchFragmentListener{
        void searchFragmentCallBack(String value);
        void searchFragmentToastCallBack(String value);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (SearchFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + "MainActivity must implement SearchFragmentListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        mTextView = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextView);
        mAdapter = new DaDataArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, EMPTY);
        final Requests presenter = new Requests();

        mTextView.setAdapter(mAdapter);

        mTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String value = mAdapter.getItem(i);
                mListener.searchFragmentToastCallBack("'" + mAdapter.getItem(i) + "' добавлен в историю");
                presenter.setData(value);
                mListener.searchFragmentCallBack(value);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mTextView.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(final Editable s) {
        ServerUtils.query(s.toString(), this);
    }

    @Override
    public synchronized void onSuggestionsReady(List<String> suggestions) {
        mAdapter.clear();

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mAdapter.addAll(suggestions);
        } else {
            for (String s : suggestions) {
                mAdapter.add(s);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String message) {
        mListener.searchFragmentToastCallBack(message);
    }
}