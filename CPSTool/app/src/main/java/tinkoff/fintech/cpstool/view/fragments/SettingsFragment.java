package tinkoff.fintech.cpstool.view.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import tinkoff.fintech.cpstool.R;
import tinkoff.fintech.cpstool.presenter.requests.Requests;

public class SettingsFragment extends Fragment{

    private SettingsFragment.SettingsFragmentListener mListener;

    private final static String DARK_THEME = "DARK";
    private final static String LIGHT_THEME = "LIGHT";

    public interface SettingsFragmentListener {
        void settingsFragmentStyleCallBack(String style);
        void settingsFragmentToastCallBack(String text);
        String settingsFragmentGetStyleCallBack();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (SettingsFragment.SettingsFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + "MainActivity must implement SettingsFragmentListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fourth, container, false);
        final CheckBox checkDarkMode = (CheckBox)view.findViewById(R.id.checkDarkMode);
        final Button clearHistoryButton = (Button) view.findViewById(R.id.clearHistoryButton);
        final Requests presenter = new Requests();
        if (mListener.settingsFragmentGetStyleCallBack() == null){
            mListener.settingsFragmentStyleCallBack(DARK_THEME);
            checkDarkMode.setChecked(true);
        } else if (mListener.settingsFragmentGetStyleCallBack().equals(LIGHT_THEME)){
            checkDarkMode.setChecked(false);
        } else {
            checkDarkMode.setChecked(true);
        }

        checkDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mListener.settingsFragmentStyleCallBack(DARK_THEME);
                    mListener.settingsFragmentToastCallBack("Выбран темный стиль");
                } else {
                    mListener.settingsFragmentStyleCallBack(LIGHT_THEME);
                    mListener.settingsFragmentToastCallBack("Выбран светлый стиль");
                }
            }
        });

        clearHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.clearHistoryData();
                mListener.settingsFragmentToastCallBack("История очищена");
            }
        });
        return view;
    }
}
