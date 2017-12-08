package tinkoff.fintech.cpstool.view;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import tinkoff.fintech.cpstool.R;
import tinkoff.fintech.cpstool.model.history.Party;
import tinkoff.fintech.cpstool.presenter.requests.Requests;
import tinkoff.fintech.cpstool.view.interfaces.OnSuggestionsListener;
import tinkoff.fintech.cpstool.presenter.utils.ServerUtils;

public class FirstFragment extends Fragment implements
        TextWatcher,
        OnSuggestionsListener {

    private static final List<String> EMPTY = new ArrayList<>();
    private DaDataArrayAdapter<String> adapter;
    private AutoCompleteTextView textView;
    private Toast toast;
    private MainActivity mainActivity;

    private FirstFragmentListener listener;

    public interface FirstFragmentListener{
        void firstCallBack(String value);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (FirstFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + "MainActivity must implement FirstFragmentListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainActivity = (MainActivity)getActivity();
        textView = (AutoCompleteTextView) getActivity().findViewById(R.id.autoCompleteTextView);
        adapter = new DaDataArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, EMPTY);
        final Requests presenter = new Requests();

        textView.setAdapter(adapter);

        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String value = adapter.getItem(i).toString();
                presenter.setData(value);
                //Toast toast = Toast.makeText(getActivity(), adapter.getItem(i).toString() + " SELECTED", Toast.LENGTH_SHORT);
                //toast.show();
                listener.firstCallBack(value);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        textView.addTextChangedListener(this);
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
        // Clear current suggestions
        adapter.clear();

        // If current device is 3.0 and higher
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            adapter.addAll(suggestions);
        } else {
            for (String s : suggestions) {
                adapter.add(s);
            }
        }

        // Notify about the change
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String message) {
        mainActivity.toastMessage(message);
    }
}