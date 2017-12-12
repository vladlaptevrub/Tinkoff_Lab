package tinkoff.fintech.cpstool.view.fragments;

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
import tinkoff.fintech.cpstool.view.MainActivity;

public class InformationFragment extends Fragment{

    private InformationFragmentListener mListener;
    private MainActivity mMainActivity;
    private Requests mPresenter;

    private final static String FALSE = "false";
    private final static String TRUE = "true";

    private String mTitle = "";
    private String mInn = "";
    private String mAddress = "";
    private String mFavourite = FALSE;

    public interface InformationFragmentListener {
        void informationFragmentCallBack(String value);
        void informationFragmentComeBack();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (InformationFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + "MainActivity must implement InformationFragmentListener");
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
        final TextView innView = (TextView)getActivity().findViewById(R.id.inn_value);
        final TextView addressView = (TextView)getActivity().findViewById(R.id.address_value);
        final CheckBox checkFavourite = (CheckBox)getActivity().findViewById(R.id.check_favourite);

        final Button toMapButton = (Button)getActivity().findViewById(R.id.to_map_button);
        final Button deleteButton = (Button)getActivity().findViewById(R.id.delete_button);
        final Button shareButton = (Button)getActivity().findViewById(R.id.shareButton);

        mMainActivity = (MainActivity)getActivity();

        mPresenter = new Requests();

        if (getArguments() != null){
            String value = getArguments().getString("value");
            if (value != null){
                Party party = mPresenter.findParty(value);
                mTitle = party.getTitle();
                mInn = party.getInn();
                mAddress = party.getAddress();
                mFavourite = party.getFavourite();
            } else {
                mMainActivity.toastMessage("null string");
            }
        } else {
            mMainActivity.toastMessage("No arguments");
        }

        titleView.setText(mTitle);
        innView.setText(mInn);
        addressView.setText(mAddress);

        if (mFavourite.equals(TRUE)){
            checkFavourite.setChecked(true);
        } else {
            checkFavourite.setChecked(false);
        }

        toMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addressView.getText().length() > 0) {
                    mListener.informationFragmentCallBack(titleView.getText().toString());
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDeleteDialog(mTitle);
            }
        });

        checkFavourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mPresenter.setFavourite(mTitle, b);
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textToSend = buildSharingMessage(mTitle, mInn, mAddress);
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
                mPresenter.deleteItem(value);
                mMainActivity.toastMessage("'" + value + "' удален");
                mListener.informationFragmentComeBack();
            }
        });

        quitDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        quitDialog.show();
    }

    public String buildSharingMessage(String title, String inn, String address){
        if (title != null && inn != null && address != null) {
            return title + "\n"
                    + "ИНН: " + inn + "\n"
                    + "Адрес: " + address + "\n\n"
                    + "Найдено с помощью 'CPS Tool' (Play Market link)";
        } else {
            return null;
        }
    }
}
