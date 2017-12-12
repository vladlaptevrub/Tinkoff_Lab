package tinkoff.fintech.cpstool.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import tinkoff.fintech.cpstool.R;
import tinkoff.fintech.cpstool.view.fragments.SearchFragment;
import tinkoff.fintech.cpstool.view.fragments.SettingsFragment;
import tinkoff.fintech.cpstool.view.fragments.HistoryFragment;
import tinkoff.fintech.cpstool.view.fragments.InformationFragment;
import tinkoff.fintech.cpstool.view.interfaces.IView;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        SearchFragment.SearchFragmentListener,
        HistoryFragment.HistoryFragmentListener,
        InformationFragment.InformationFragmentListener,
        IView{

    private final static int SEARCH = 1;
    private final static int HISTORY = 2;
    private final static int INFORMATION = 3;
    private final static int SETTINGS = 4;
    private final static int MAP = 5;

    private final static String APP_PREFERENCES = "mysettings";
    private final static String APP_PREFERENCES_MAP_THEME = "MapTheme";

    private int mActiveScreen = SEARCH;
    private Bundle mArgs = new Bundle();
    private FragmentManager mFragmentManager;
    private NavigationView mNavigationView;
    private SharedPreferences mSettings;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        Window window = this.getWindow();
        window.setStatusBarColor(Color.BLACK);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        mFragmentManager = getSupportFragmentManager();
        try {
            Fragment fragment = SearchFragment.class.newInstance();
            mFragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            setTitle("Поиск");
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            switch (mActiveScreen){
                case SEARCH:
                    openQuitDialog();
                    break;
                case HISTORY:
                    setFragment(SEARCH, "Поиск");
                    mNavigationView.getMenu().getItem(0).setChecked(true);
                    break;
                case INFORMATION:
                    setFragment(HISTORY, "История");
                    mNavigationView.getMenu().getItem(1).setChecked(true);
                    break;
                case SETTINGS:
                    setFragment(SEARCH, "Поиск");
                    mNavigationView.getMenu().getItem(0).setChecked(true);
                    break;
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment fragment = null;
        Class fragmentClass = null;

        int id = item.getItemId();

        if (id == R.id.nav_search) {
            // Handle the camera action
            fragmentClass = SearchFragment.class;
            mActiveScreen = SEARCH;
            item.setChecked(true);
            setTitle(item.getTitle());
        } else if (id == R.id.nav_history){
            fragmentClass = HistoryFragment.class;
            mActiveScreen = HISTORY;
            item.setChecked(true);
            setTitle(item.getTitle());
        } else if (id == R.id.nav_manage) {
            fragmentClass = SettingsFragment.class;
            mActiveScreen = SETTINGS;
            item.setChecked(true);
            setTitle(item.getTitle());
        } else if (id == R.id.nav_share) {
            String textToSend = "Приложение 'CPS Tool' для поиска контрагентов. \n" +
                    "Ссылка для скачивая (Play Market): <-ссылка->";
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, textToSend);
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, "Рассказать друзьям"));
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mFragmentManager.beginTransaction().replace(R.id.container, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void toastMessage(String message) {
        if (message.length() > 0) {
            Toast toast = Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void searchFragmentCallBack(String value) {
        mArgs.clear();
        mArgs.putString("value", value);
        boolean success = setFragment(INFORMATION, "Информация");
        if (!success){
            toastMessage("Error");
        } else {
            mNavigationView.getMenu().getItem(0).setChecked(false);
        }
    }

    @Override
    public void historyFragmentCallBack(String value) {
        mArgs.clear();
        mArgs.putString("value", value);
        boolean success = setFragment(INFORMATION, "Информация");
        if (!success){
            toastMessage("Error");
        } else {
            mNavigationView.getMenu().getItem(1).setChecked(false);
        }
    }

    @Override
    public void informationFragmentCallBack(String value) {
        mArgs.clear();
        mArgs.putString("value", value);
        boolean success = setFragment(MAP, "MAP");
        if (!success){
            toastMessage("Error");
        }
    }

    @Override
    public void informationFragmentComeBack() {
        boolean success = setFragment(HISTORY, "История");
        if (!success){
            toastMessage("Error");
        }
    }

    @Override
    public void changeMapTheme(String style) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(APP_PREFERENCES_MAP_THEME, style);
        editor.apply();
    }

    @Override
    public String getMapTheme() {
        if(mSettings.contains(APP_PREFERENCES_MAP_THEME)) {
            return mSettings.getString(APP_PREFERENCES_MAP_THEME, "");
        } else {
            return null;
        }
    }

    public boolean setFragment(int key, String title){
        Fragment fragment = null;
        Class fragmentClass = null;

        switch (key){
            case SEARCH:
                fragmentClass = SearchFragment.class;
                mActiveScreen = SEARCH;
                setTitle(title);
                break;
            case HISTORY:
                fragmentClass = HistoryFragment.class;
                mActiveScreen = HISTORY;
                setTitle(title);
                break;
            case INFORMATION:
                fragmentClass = InformationFragment.class;
                mActiveScreen = INFORMATION;
                setTitle(title);
                break;
            case SETTINGS:
                fragmentClass = SettingsFragment.class;
                mActiveScreen = SETTINGS;
                setTitle(title);
                break;
            case MAP:
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtras(mArgs);
                startActivity(intent);
                return true;
            default:
                return false;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
            fragment.setArguments(mArgs);
        } catch (Exception e) {
            return false;
        }

        mFragmentManager.beginTransaction().replace(R.id.container, fragment).commit();

        return true;
    }

    private void openQuitDialog() {
        final AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                MainActivity.this);
        quitDialog.setTitle("Вы хотите выйти?");
        quitDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
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
