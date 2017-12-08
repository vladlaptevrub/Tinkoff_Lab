package tinkoff.fintech.cpstool.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import tinkoff.fintech.cpstool.R;
import tinkoff.fintech.cpstool.view.interfaces.IView;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        FirstFragment.FirstFragmentListener,
        SecondFragment.SecondFragmentListener,
        ThirdFragment.ThirdFragmentListener,
        IView{

    private NavigationView navigationView;
    private FragmentManager fragmentManager;
    private Toolbar toolbar;
    private Bundle args = new Bundle();

    private final static int FIRST = 1;
    private final static int SECOND = 2;
    private final static int THIRD = 3;
    private final static int FOURTH = 4;
    private final static int MAP = 5;

    private int activeScreen = FIRST;

    private static final String APP_PREFERENCES = "mysettings";
    private static final String APP_PREFERENCES_MAP_THEME = "MapTheme";

    private SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        Window window = this.getWindow();
        window.setStatusBarColor(Color.BLACK);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();
        try {
            Fragment fragment = FirstFragment.class.newInstance();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
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
            switch (activeScreen){
                case FIRST:
                    openQuitDialog();
                    break;
                case SECOND:
                    setFragment(FIRST, "Поиск");
                    navigationView.getMenu().getItem(0).setChecked(true);
                    break;
                case THIRD:
                    setFragment(SECOND, "История");
                    navigationView.getMenu().getItem(1).setChecked(true);
                    break;
                case FOURTH:
                    setFragment(FIRST, "Поиск");
                    navigationView.getMenu().getItem(0).setChecked(true);
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
            fragmentClass = FirstFragment.class;
            activeScreen = FIRST;
            item.setChecked(true);
            setTitle(item.getTitle());
        } else if (id == R.id.nav_history){
            fragmentClass = SecondFragment.class;
            activeScreen = SECOND;
            item.setChecked(true);
            setTitle(item.getTitle());
        } else if (id == R.id.nav_manage) {
            fragmentClass = FourthFragment.class;
            activeScreen = FOURTH;
            item.setChecked(true);
            setTitle(item.getTitle());
        } else if (id == R.id.nav_share) {
            fragmentClass = FirstFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();

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
    public void firstCallBack(String value) {
        args.clear();
        args.putString("value", value);
        boolean success = setFragment(THIRD, "Информация");
        if (!success){
            toastMessage("Error");
        } else {
            navigationView.getMenu().getItem(0).setChecked(false);
        }
    }

    @Override
    public void secondCallBack(String value) {
        args.clear();
        args.putString("value", value);
        boolean success = setFragment(THIRD, "Информация");
        if (!success){
            toastMessage("Error");
        } else {
            navigationView.getMenu().getItem(1).setChecked(false);
        }
    }

    @Override
    public void thirdCallBack(String value) {
        args.clear();
        args.putString("value", value);
        boolean success = setFragment(MAP, "MAP");
        if (!success){
            toastMessage("Error");
        }
    }

    @Override
    public void thirdComeBack() {
        boolean success = setFragment(SECOND, "История");
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

    private boolean setFragment(int key, String title){
        Fragment fragment = null;
        Class fragmentClass = null;

        switch (key){
            case FIRST:
                fragmentClass = FirstFragment.class;
                activeScreen = FIRST;
                setTitle(title);
                break;
            case SECOND:
                fragmentClass = SecondFragment.class;
                activeScreen = SECOND;
                setTitle(title);
                break;
            case THIRD:
                fragmentClass = ThirdFragment.class;
                activeScreen = THIRD;
                setTitle(title);
                break;
            case FOURTH:
                fragmentClass = FourthFragment.class;
                activeScreen = FOURTH;
                setTitle(title);
                break;
            case MAP:
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtras(args);
                startActivity(intent);
                return true;
            default:
                return false;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
            fragment.setArguments(args);
        } catch (Exception e) {
            return false;
        }

        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();

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
