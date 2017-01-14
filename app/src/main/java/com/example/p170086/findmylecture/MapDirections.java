package com.example.p170086.findmylecture;



/**
 * Created by gerard on 31/12/16.
 *
 * <p>Main map view interactions, functions and markers are
 * done from this function</p>
 */
import android.app.Dialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
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


public class MapDirections extends FragmentActivity implements OnMapReadyCallback {
    /**
     * The elements declared at this point
     * are a map object from google api, Button object, and two AutoComplete Objects
     */
    GoogleMap mGoogleMap;
    Button go;
    AutoCompleteTextView et;
    AutoCompleteTextView tT;


    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

/**
 * On Create begins with checking wheather or not
 * a connection can be made to google maps
 */
        if(googleServicesAvailable()){ // if google services is connected to


            Toast.makeText(this, "Perfect! We're connected!", Toast.LENGTH_LONG).show(); // flash message decalaring that the connection has been made
            setContentView(R.layout.mapresult); // layout from R file
            // note the array adapter which is used for the 2 declared autocomplete text views
            String[] places ={"John Hume Building","Eolas Building","Iontas Building","Student Union","Arts Block","John Paul II Library", "Science Building", "Callan Building", "Froebel College of Education"};
            ArrayAdapter<String> adapter = new ArrayAdapter<String> (this,android.R.layout.simple_list_item_1, places);

            et = (AutoCompleteTextView)findViewById(R.id.eT); // connect the AutoCompleteTextViews
            tT = (AutoCompleteTextView)findViewById(R.id.TT);

            // set adapter and Threshold level on AutoCompleteTextViews
            et.setAdapter(adapter);
            et.setThreshold(1);


            tT.setAdapter(adapter);
            tT.setThreshold(1);

            // initialize map object
            initMap();


        } else {
            // if error, return error page
            setContentView(R.layout.error);
        }


    }
    // map function
    private void initMap() {
        // mapfragment is linked directly to the xml map fragment
        MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }
    //funciton checks if google services is available
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

    // decalres map object and brings marker and zoom to maynooth
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        MarkerOptions mk = new MarkerOptions()
                  .title("Maynooth University")
                  .position(new LatLng(53.382929,-6.603665));
        goToLocationZoom(53.382929,-6.603665, 15);

        //lat = 53.382929;
        //lon = -6.603665;

        mGoogleMap.addMarker(mk);


    }
    // creates a new camera view and goes to zoomed location
    private void goToLocationZoom(double lat, double lon, float i) {
        LatLng ll = new LatLng(lat,lon);
        CameraUpdate newView = CameraUpdateFactory.newLatLngZoom(ll, i);
        mGoogleMap.moveCamera(newView);


    }




    public void geoLocate(View view)throws IOException{

        final String location = et.getText().toString();
        final String location2 = tT.getText().toString();

        // Thread Timer  for marker placement
        Thread timer = new Thread() {
            public void run() {

                try {

                    sleep(3000);

                } catch (InterruptedException e) {

                    e.printStackTrace();

                } finally {

                }
            }
        };

        Geocoder gc = new Geocoder(this);
      //  List<Address> list = gc.getFromLocationName(location,1);
       // Address address = list.get(0);
        // String locality = address.getLocality();

        //Toast.makeText(this, locality, Toast.LENGTH_LONG).show();


        getDest(location2);
        getLoc(location);

        timer.start();





    }


    public void mapPlacements(double lat, double lon){

        goToLocationZoom(lat, lon, 14);


    }

    public void getDest(String location2){
        double lat, lon;

        if(location2.equals("Eolas Building")) {
            lat = 53.384611;
            lon = -6.601662;

            goToLocationZoom(lat, lon,9);
            makeMarker("Eolas Building", "Eolas", "Doing some programming?", lat, lon);

        } else if (location2.equals("John Hume Building")){

            lat = 5;
            lon = -6.59950;
            mapPlacements(lat, lon);
            Toast.makeText(this, "John Hume Building", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "There's 7 Lecture Theatres in here!", Toast.LENGTH_LONG).show();

        } else if (location2.equals("Student Union")){

            lat = 53.382929;
            lon = -6.603665;
            mapPlacements(lat, lon);
            //makeMarker("Student Union",53.382929,-6.603665);
            Toast.makeText(this, "Student Union", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Have a pint!", Toast.LENGTH_LONG).show();

        } else if (location2.equals("Iontas Building")){

            lat = 53.384716;
            lon = -6.600174;
            mapPlacements(lat, lon);
            Toast.makeText(this, "Iontas Building", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Home of the Bard!", Toast.LENGTH_LONG).show();

        } else if (location2.equals("Arts Block")){

            lat = 53.383821;
            lon = -6.601455;
            mapPlacements(lat, lon);
            Toast.makeText(this, "Arts Block", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Theatre 1 & 2 in front of you!", Toast.LENGTH_LONG).show();

        } else if (location2.equals("John Paul II Library")){

            lat = 53.381178;
            lon = -6.599197;
            mapPlacements(lat, lon);
            Toast.makeText(this, "John Paul II Library", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Great place to study!", Toast.LENGTH_LONG).show();

        } else if (location2.equals("Science Building")){

            lat = 53.383008;
            lon = -6.600329;
            mapPlacements(lat, lon);
            Toast.makeText(this, "Science Building", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Don't eat anything!", Toast.LENGTH_LONG).show();

        } else if (location2.equals("Callan Building")){

            lat = 53.383091;
            lon = -6.602486;
            mapPlacements(lat, lon);
            Toast.makeText(this, "Callan Building", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "You can study either Biology or Computers here!", Toast.LENGTH_LONG).show();

        } else if (location2.equals("Froebel College of Education")){

            lat = 53.384970;
            lon = -6.598751;
            mapPlacements(lat, lon);
            Toast.makeText(this, "Froebel College of Education", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Here is where you teach!", Toast.LENGTH_LONG).show();

        }


    }

    public void getLoc(String location2){
        double lat, lon;

        if(location2.equals("Eolas Building")) {

            lat = 53.384656;
            lon = -6.6601530;
            mapPlacements(lat, lon);
            Toast.makeText(this, "Eolas Building", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Please Wait!", Toast.LENGTH_LONG).show();

        } else if (location2.equals("John Hume Building")){

            lat = 53.383891;
            lon = -6.599503;
            mapPlacements(lat, lon);
            Toast.makeText(this, "John Hume Building", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Please Wait!", Toast.LENGTH_LONG).show();

        } else if (location2.equals("Student Union")){

            lat = 53.382929;
            lon = -6.603665;
            mapPlacements(lat, lon);
           // makeMarker("Student Union",53.382929,-6.603665);
            Toast.makeText(this, "Student Union", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Please Wait!", Toast.LENGTH_LONG).show();

        } else if (location2.equals("Iontas Building")){

            lat = 53.384716;
            lon = -6.600174;
            mapPlacements(lat, lon);
            Toast.makeText(this, "Iontas Building", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Please Wait!", Toast.LENGTH_LONG).show();

        } else if (location2.equals("Arts Block")){

            lat = 53.383821;
            lon = -6.601455;
            mapPlacements(lat, lon);
            Toast.makeText(this, "Arts Block", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Please Wait!", Toast.LENGTH_LONG).show();

        } else if (location2.equals("John Paul II Library")){

            lat = 53.381178;
            lon = -6.599197;
            mapPlacements(lat, lon);
            Toast.makeText(this, "John Paul II Library", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Please Wait!", Toast.LENGTH_LONG).show();

        } else if (location2.equals("Science Building")){

            lat = 53.383008;
            lon = -6.600329;
            mapPlacements(lat, lon);
            Toast.makeText(this, "Science Building", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Please Wait!", Toast.LENGTH_LONG).show();

        } else if (location2.equals("Callan Building")){

            lat = 53.383091;
            lon = -6.602486;
            mapPlacements(lat, lon);
            Toast.makeText(this, "Callan Building", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Please Wait!", Toast.LENGTH_LONG).show();

        } else if (location2.equals("Froebel College of Education")){

            lat = 53.384970;
            lon = -6.598751;
            mapPlacements(lat, lon);
            Toast.makeText(this, "Froebel College of Education", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Please Wait!", Toast.LENGTH_LONG).show();

        }


    }

    public void makeMarker(String location, String ToastLoc, String message, double lat, double lon){
        MarkerOptions mk = new MarkerOptions()
                .title(location)
                .position(new LatLng(lat, lon));
        mGoogleMap.addMarker(mk);
        Toast.makeText(this, ToastLoc, Toast.LENGTH_LONG).show();
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }



}
