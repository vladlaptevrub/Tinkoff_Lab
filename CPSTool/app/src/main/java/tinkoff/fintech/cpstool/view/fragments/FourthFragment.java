package tinkoff.fintech.cpstool.view.fragments;

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
import tinkoff.fintech.cpstool.view.MainActivity;

public class FourthFragment extends Fragment{

    private final static String DARK_THEME = "DARK";
    private final static String LIGHT_THEME = "LIGHT";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fourth, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CheckBox checkDarkMode = (CheckBox)getActivity().findViewById(R.id.checkDarkMode);
        Button clearHistoryButton = (Button) getActivity().findViewById(R.id.clearHistoryButton);
        final MainActivity mainActivity = (MainActivity) getActivity();
        final Requests presenter = new Requests();

        if (mainActivity.getMapTheme() == null){
            mainActivity.changeMapTheme(DARK_THEME);
            checkDarkMode.setChecked(true);
        } else if (mainActivity.getMapTheme().equals(LIGHT_THEME)){
            checkDarkMode.setChecked(false);
        } else {
            checkDarkMode.setChecked(true);
        }

        checkDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mainActivity.changeMapTheme(DARK_THEME);
                    mainActivity.toastMessage("Выбран темный стиль");
                } else {
                    mainActivity.changeMapTheme(LIGHT_THEME);
                    mainActivity.toastMessage("Выбран светлый стиль");
                }
            }
        });

        clearHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.clearHistoryData();
                mainActivity.toastMessage("История очищена");
            }
        });
    }
}
