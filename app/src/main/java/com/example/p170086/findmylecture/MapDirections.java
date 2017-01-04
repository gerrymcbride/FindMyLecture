package com.example.p170086.findmylecture;

/**
 * Created by gerard on 31/12/16.
 */
import android.app.Dialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

// AIzaSyBZnr2lBPO0dhaIqvGBhgLAEPwlyqN-xlo
public class MapDirections extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap mGoogleMap;
    Button go;


    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);


        if(googleServicesAvailable()){

            Toast.makeText(this, "Perfect!", Toast.LENGTH_LONG).show();
            setContentView(R.layout.mapresult);
            initMap();

        } else {
            setContentView(R.layout.error);
        }
    }

    private void initMap() {
        MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    public boolean googleServicesAvailable(){

        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);

        if(isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Can't connect to google services!", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        MarkerOptions mk = new MarkerOptions()
                  .title("Maynooth University")
                  .position(new LatLng(53.3838854,-6.6022591));
        goToLocationZoom(53.3838854,-6.6022591, 15);

        mGoogleMap.addMarker(mk);


    }

    private void goToLocationZoom(double lat, double lon, float i) {
        LatLng ll = new LatLng(lat,lon);
        CameraUpdate newView = CameraUpdateFactory.newLatLngZoom(ll, i);
        mGoogleMap.moveCamera(newView);

    //    MarkerOptions mk = new MarkerOptions()
      //          .title("Maynooth University")
        //        .position(new LatLng(lat,lon));


    }

    private void goToLocation(double lat, double lon) {
        LatLng ll = new LatLng(lat,lon);
        CameraUpdate newView = CameraUpdateFactory.newLatLng(ll);
        mGoogleMap.moveCamera(newView);

    }

    public void geoLocate(View view) throws IOException {

        EditText et = (EditText)findViewById(R.id.eT);
        String location = et.getText().toString();

        Geocoder gc = new Geocoder(this);
        List<Address> list = gc.getFromLocationName(location,1);
        Address address = list.get(0);
        String locality = address.getLocality();

        Toast.makeText(this, locality, Toast.LENGTH_LONG).show();

        double lat =  address.getLatitude();
        double lon = address.getLongitude();
        goToLocationZoom(lat, lon, 15);

        MarkerOptions mk = new MarkerOptions()
                .title(locality)
                .position(new LatLng(lat, lon));

        mGoogleMap.addMarker(mk);

    }
}