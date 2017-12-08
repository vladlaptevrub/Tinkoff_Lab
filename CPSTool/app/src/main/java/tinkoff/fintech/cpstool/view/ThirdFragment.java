package tinkoff.fintech.cpstool.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import tinkoff.fintech.cpstool.R;
import tinkoff.fintech.cpstool.model.history.Party;
import tinkoff.fintech.cpstool.presenter.requests.Requests;

public class ThirdFragment extends Fragment{

    private ThirdFragmentListener listener;
    private Requests presenter;

    private final static String FALSE = "false";
    private final static String TRUE = "true";

    private String title = "";
    private String inn = "";
    private String address = "";
    private String favourite = FALSE;

    private MainActivity mainActivity;

    public interface ThirdFragmentListener{
        void thirdCallBack(String value);
        void thirdComeBack();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (ThirdFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + "MainActivity must implement ThirdFragmentListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_third, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final TextView titleView = (TextView)getActivity().findViewById(R.id.party_title);
        TextView innView = (TextView)getActivity().findViewById(R.id.inn_value);
        final TextView addressView = (TextView)getActivity().findViewById(R.id.address_value);
        CheckBox checkFavourite = (CheckBox)getActivity().findViewById(R.id.check_favourite);

        Button toMapButton = (Button)getActivity().findViewById(R.id.to_map_button);
        Button deleteButton = (Button)getActivity().findViewById(R.id.delete_button);
        Button shareButton = (Button)getActivity().findViewById(R.id.shareButton);

        mainActivity = (MainActivity)getActivity();

        presenter = new Requests();

        if (getArguments() != null){
            String value = getArguments().getString("value");
            if (value != null){
                Party party = presenter.findParty(value);
                title = party.getTitle();
                inn = party.getInn();
                address = party.getAddress();
                favourite = party.getFavourite();
            } else {
                mainActivity.toastMessage("null string");
            }
        } else {
            mainActivity.toastMessage("No arguments");
        }

        titleView.setText(title);
        innView.setText(inn);
        addressView.setText(address);
        if (favourite.equals(TRUE)){
            checkFavourite.setChecked(true);
        } else {
            checkFavourite.setChecked(false);
        }

        toMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addressView.getText().length() > 0) {
                    listener.thirdCallBack(titleView.getText().toString());
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDeleteDialog(title);
            }
        });

        checkFavourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                presenter.setFavourite(title, b);
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textToSend = title + "\n"
                        + "ИНН: " + inn + "\n"
                        + "Адрес: " + address + "\n\n"
                        + "Найдено с помощью 'CPS Tool' (Play Market link)";
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, textToSend);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Отправить"));
            }
        });

    }

    private void openDeleteDialog(final String value) {
        final AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                getActivity());
        quitDialog.setTitle("Удалить '" + value + "'?");

        quitDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.deleteItem(value);
                mainActivity.toastMessage("'" + value + "' удален");
                listener.thirdComeBack();
            }
        });

        quitDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        quitDialog.show();
    }
}
