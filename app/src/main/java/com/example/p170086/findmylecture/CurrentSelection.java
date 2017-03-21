package com.example.p170086.findmylecture;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * Created by gerard on 29/12/16.
 */

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Gerard McBride on 28/11/16.
 */


public class CurrentSelection extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mGoogleMap;
    private Button b;
    private TextView t;
    private LocationManager locationManager;
    private LocationListener listener;
    private RequestQueue reuqestQueue;
    private String lat;
    private String lon;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// checks if connections to google services is available
        if(googleServicesAvailable()) {

            setContentView(R.layout.currentselection);
            // intialize buttons and text view
            t = (TextView) findViewById(R.id.displayCoordinates);
            b = (Button) findViewById(R.id.requestBtn);
            initMap(); // intialize map
            Toast.makeText(this, "We're connected!", Toast.LENGTH_LONG).show();
            // create volley queue and initialize locationmanager
            reuqestQueue = Volley.newRequestQueue(this);
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        }
        //set location lisitener
        listener = new LocationListener() {
            @Override
            // if location changes
            public void onLocationChanged(Location location) {
                lon = Double.toString(location.getLongitude());
                lat = Double.toString(location.getLatitude());

                getAddress(lat, lon);
            }

        @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }
// not used
            @Override
            public void onProviderEnabled(String s) {

            }

// not used
            @Override
            public void onProviderDisabled(String s) {
                // if provider is disabled, bring to hardware settings
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };
        //configure button method
        configure_button();
    }
    // intitalize mapfragment
    private void initMap() {
        // mapfragment is linked directly to the xml map fragment
        MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.mapFragment2);
        mapFragment.getMapAsync(this);
    }

    public boolean googleServicesAvailable(){
        // initializes google api availibility
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);

        // if isAvailable, true is returned
        if(isAvailable == ConnectionResult.SUCCESS) {
            return true;
            // if user resolvable, show dialog to user
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();

            // if untraceable error
        } else {
            Toast.makeText(this, "Can't connect to google services!", Toast.LENGTH_LONG).show();
        }
        return false;
    }


        @Override
        // permission request
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }

    void configure_button() {
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET} , 10);
            }
            return;
        }
        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //noinspection MissingPermission
                locationManager.requestLocationUpdates("gps", 5000, 0, listener);
            }
        });
    }

    public void getAddress(final String lat, final String lon){
        final double lat1 = Double.parseDouble(lat); // parse double to lat
        final double lon1 = Double.parseDouble(lon); // parse double to lon
        // request json object with coordinates lat and lon acting as latitude and longtitude for the google servers
        JsonObjectRequest request = new JsonObjectRequest("https://maps.googleapis.com/maps/api/geocode/json?latlng="+lat+","+lon+
                "&key=AIzaSyC4usvTEkMSFAOiRA-Xv1fOUf_OJ8Gp268", new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {                                                                                  // AIzaSyBaOKywPpyNvaeWiFZKi2yzdq4eCIhKi-E

                try {
                    // returns and sets the address from retured json object
                    String address = response.getJSONArray("results").getJSONObject(0).getString("formatted_address");
                    t.setText(address);
                    makeMarker(address, lat1, lon1, "You are here!"); // sets marker on map


                // if exception
                } catch (JSONException e) {
                    e.printStackTrace();

                } finally {
                    // goes to marker
                    goToLocationZoom(lat1, lon1, 14);

                }
        // if volley returns an error
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        // adds request to network volley queue
        reuqestQueue.add(request);
    }

// once map is ready, goes to maynooth
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(mGoogleMap.MAP_TYPE_HYBRID);
        makeMarker("Maynooth University", 53.382929, -6.603665, null);

    }
    // method to change location
    private void goToLocationZoom(double lat, double lon, int i) {
        LatLng ll = new LatLng(lat,lon); // get new lat long coordinates
        CameraUpdate newView = CameraUpdateFactory.newLatLngZoom(ll, i); // updates camera
        mGoogleMap.animateCamera(newView); // camera animated move
    }
// method makes markers
    public void makeMarker(String x, double lat, double lon, String snip){
        MarkerOptions mk = new MarkerOptions()
                .title(x)
                .position(new LatLng(lat, lon))
                .snippet(snip);

        goToLocationZoom(lat, lon, 15);
        mGoogleMap.addMarker(mk);

    }
}