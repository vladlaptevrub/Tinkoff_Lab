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
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import tinkoff.fintech.cpstool.R;
import tinkoff.fintech.cpstool.model.history.Party;
import tinkoff.fintech.cpstool.presenter.requests.Requests;
import tinkoff.fintech.cpstool.view.MainActivity;
import tinkoff.fintech.cpstool.view.adapters.RecyclerAdapter;

public class SecondFragment extends Fragment implements RecyclerAdapter.OnItemClicked {

    private SecondFragmentListener listener;
    private Requests presenter;
    private RecyclerAdapter recyclerAdapter;
    private RecyclerView recyclerView;
    private AutoCompleteTextView searchInCacheEditText;
    private MainActivity mainActivity;
    private ArrayAdapter searchAdapter;

    public interface SecondFragmentListener{
        void secondCallBack(String value);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (SecondFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + "MainActivity must implement SecondFragmentListener");
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

        String[] languages={"Android ","java","IOS","SQL","JDBC","Web services", "Anton", "Anna"};

        presenter = new Requests();

        recyclerView = (RecyclerView)getActivity().findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager recyclerLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(recyclerLayoutManager);
        mainActivity = (MainActivity)getActivity();
        searchInCacheEditText = (AutoCompleteTextView) getActivity().findViewById(R.id.searchInCacheEditText);
        updateRecyclerView();

        searchInCacheEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listener.secondCallBack(searchAdapter.getItem(i).toString());
            }
        });
    }

    @Override
    public void onItemClick(String value) {
        listener.secondCallBack(value);
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
                presenter.deleteItem(value);
                updateRecyclerView();
                mainActivity.toastMessage("'" + value + "' удален");
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
        recyclerAdapter = new RecyclerAdapter(presenter.getData());
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnClick(this);

        searchAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, getListOfTitles());
        searchInCacheEditText.setAdapter(searchAdapter);
        searchInCacheEditText.setThreshold(1);
    }

    private List<String> getListOfTitles(){
        List<Party> parties = presenter.getData();
        List<String> titles = new ArrayList<>();
        for (int i = 0; i < parties.size(); i++){
            titles.add(parties.get(i).getTitle());
        }
        return titles;
    }
}