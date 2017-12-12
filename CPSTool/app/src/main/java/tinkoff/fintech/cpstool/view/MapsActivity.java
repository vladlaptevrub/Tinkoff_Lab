package tinkoff.fintech.cpstool.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tinkoff.fintech.cpstool.R;
import tinkoff.fintech.cpstool.presenter.requests.Requests;
import tinkoff.fintech.cpstool.view.interfaces.IView;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        IView{

    private GoogleMap mMap;

    private String title = "";
    private String address = "";

    private static final String APP_PREFERENCES = "mysettings";
    private static final String APP_PREFERENCES_MAP_THEME = "MapTheme";

    private final static String DARK_THEME = "DARK";
    private final static String LIGHT_THEME = "LIGHT";

    private SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        Window window = this.getWindow();
        window.setStatusBarColor(Color.BLACK);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapmap);
        mapFragment.getMapAsync(this);

        final Requests presenter = new Requests();

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            String value = extras.getString("value");
            if (value != null){
                title = presenter.findParty(value).getTitle();
                address = presenter.findParty(value).getAddress();
            } else {
                toastMessage("null string");
            }
        } else {
            toastMessage("No arguments");
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng sydney = new LatLng(-34, 151);
        Geocoder geocoder = new Geocoder(this);
        List<Address> addressList = LatLngFromAddress(address);

        if(addressList.size() > 0) {
            double latitude= addressList.get(0).getLatitude();
            double longitude= addressList.get(0).getLongitude();
            sydney = new LatLng(latitude, longitude);
            if (getMapTheme() == null){
                changeMapTheme(DARK_THEME);
                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.dark_style));
                mMap.addMarker(new MarkerOptions().position(sydney).title(title).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker)));
            } else if (getMapTheme().equals(LIGHT_THEME)){
                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.light_style));
                mMap.addMarker(new MarkerOptions().position(sydney).title(title).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_red)));
            } else {
                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.dark_style));
                mMap.addMarker(new MarkerOptions().position(sydney).title(title).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker)));
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14.0f));

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void toastMessage(String message) {
        Toast toast = Toast.makeText(MapsActivity.this, message, Toast.LENGTH_SHORT);
        toast.show();
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

    public List<Address> LatLngFromAddress(String address){
        Geocoder geocoder = new Geocoder(this);
        List<Address> addressList = new ArrayList<>();
        if (address.length() > 0) {
            try {
                addressList = geocoder.getFromLocationName(address, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return addressList;
    }
}
