package tinkoff.fintech.cpstool.view;

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

import java.util.ArrayList;
import java.util.List;

import tinkoff.fintech.cpstool.R;
import tinkoff.fintech.cpstool.presenter.requests.Requests;

public class SecondFragment extends Fragment implements RecyclerAdapter.OnItemClicked {

    private SecondFragmentListener listener;
    private Requests presenter;
    private RecyclerAdapter recyclerAdapter;
    private RecyclerView recyclerView;
    private MainActivity mainActivity;

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

        presenter = new Requests();

        recyclerView = (RecyclerView)getActivity().findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager recyclerLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(recyclerLayoutManager);
        mainActivity = (MainActivity)getActivity();
        updateRecyclerView();
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
    }
}