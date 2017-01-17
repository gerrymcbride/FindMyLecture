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
        mGoogleMap = googleMap; // declare map object
        makeMarker("Maynooth University", "Maynooth University", "Welcome to Maynooth!", "Main Campus",53.382929,-6.603665); // place initial marker

    }
    // creates a new camera view and goes to zoomed location
    private void goToLocationZoom(double lat, double lon, float i) {
        LatLng ll = new LatLng(lat,lon); // establishes new lat long elements for view
        CameraUpdate newView = CameraUpdateFactory.newLatLngZoom(ll, i); // updates camera view with new parameters
        mGoogleMap.animateCamera(newView); // newview is now implemented via camera move


    }



    // geolocater function
    public void geoLocate(View view)throws IOException{
        // converts entries to strings for processing
        final String location = et.getText().toString();
        final String location2 = tT.getText().toString();
        // clears markers from map on every use
        mGoogleMap.clear();

        // gets the coordinates of both user inputs
        getDest(location2);
        getLoc(location);






    }
    // Function to place markers
    public void makeMarker(String location, String ToastLoc, String message, String snip, double lat, double lon){
        MarkerOptions mk = new MarkerOptions() //declare marker object
                .title(location) //title is given as parameter
                .position(new LatLng(lat, lon)) // new latlong declared according to parameters
                .snippet(snip); // snippet is given as snip parameter
        goToLocationZoom(lat, lon, 14); // goToLocationZoom() called to show location in one function
        mGoogleMap.addMarker(mk); // marker is added to map
        Toast.makeText(this, ToastLoc, Toast.LENGTH_LONG).show(); // info is shown according to location
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

/**
 * getDest & getLoc holds many options which would be typically stored in DB
 * Given the limited scope of this project, DB would add abstraction
 * which is avoidable via below
 *
 * Coordinates are hard-coded and retrieved via onLocationZoom
 */

    public void getDest(String location2){
        double lat, lon;

        if(location2.equals("Eolas Building")){

            lat = 53.384611;
            lon = -6.601662;
            makeMarker("Eolas Building", "Eolas", "Doing some programming?", "Department Of Computer Science", lat, lon);

        } else if (location2.equals("John Hume Building")){

            lat = 53.383891;
            lon = -6.599503;
            makeMarker("John Hume Building", "John Hume Buiding", "There's 7 Lecture Theatres in here!", "JH 1-7", lat, lon);


        } else if (location2.equals("Student Union")){

            lat = 53.382929;
            lon = -6.603665;
            makeMarker("Student Union", "SU", "Have a pint!", "Bar and Student Services", lat, lon);

        } else if (location2.equals("Iontas Building")){

            lat = 53.384716;
            lon = -6.600174;
            makeMarker("Iontas Building", "Iontas", "Home of the Bard!", "English Department", lat, lon);

        } else if (location2.equals("Arts Block")){

            lat = 53.383821;
            lon = -6.601455;
            makeMarker("Arts Block", "Arts Block", "Theatre 1 & 2 in front of you!", "Two Lecture Theatres", lat, lon);

        } else if (location2.equals("John Paul II Library")){

            lat = 53.381178;
            lon = -6.599197;
            makeMarker("John Paul II Library", "Library", "Great place to study!", "Student Card Needed", lat, lon);

        } else if (location2.equals("Science Building")){

            lat = 53.383008;
            lon = -6.600329;
            makeMarker("Science Building", "Science Building", "Don't eat anything!", "Science Department", lat, lon);

        } else if (location2.equals("Callan Building")){

            lat = 53.383091;
            lon = -6.602486;
            makeMarker("Callan Building", "Callan Building", "You can study either Biology or Computers here!","Computer Science and Biology", lat, lon);

        } else if (location2.equals("Froebel College of Education")){

            lat = 53.384970;
            lon = -6.598751;
            makeMarker("Froebel College of Education", "Froebel", "Here is where you teach!", "College of Education", lat, lon);

        }


    }
// methods for location
    public void getLoc(String location2){
        double lat, lon;

        if(location2.equals("Eolas Building")) {

            lat = 53.384611;
            lon = -6.601662;
            makeMarker("Eolas Building","Eolas Building","Please wait","Starting Point", lat, lon);

        } else if (location2.equals("John Hume Building")){

            lat = 53.383891;
            lon = -6.599503;
            makeMarker("John Hume Building","John Hume Building","Please wait","Starting Point", lat, lon);

        } else if (location2.equals("Student Union")){

            lat = 53.382929;
            lon = -6.603665;
            makeMarker("Student Union","Student Union","Please wait","Starting Point", lat, lon);

        } else if (location2.equals("Iontas Building")){

            lat = 53.384716;
            lon = -6.600174;
            makeMarker("Iontas Building","Iontas Building","Please wait","Starting Point", lat, lon);

        } else if (location2.equals("Arts Block")){

            lat = 53.383821;
            lon = -6.601455;
            makeMarker("Arts Block","Arts Block","Please wait","Starting Point", lat, lon);

        } else if (location2.equals("John Paul II Library")){

            lat = 53.381178;
            lon = -6.599197;
            makeMarker("John Paul II Library","Library","Please wait","Starting Point", lat, lon);

        } else if (location2.equals("Science Building")){

            lat = 53.383008;
            lon = -6.600329;
            makeMarker("Science Building","Science Building","Please wait","Starting Point", lat, lon);

        } else if (location2.equals("Callan Building")){

            lat = 53.383091;
            lon = -6.602486;
            makeMarker("Callan Building","Callan Building","Please wait","Starting Point", lat, lon);

        } else if (location2.equals("Froebel College of Education")){

            lat = 53.384970;
            lon = -6.598751;
            makeMarker("Froebel College of Education","Froebel","Please wait","Starting Point", lat, lon);


        }


    }

}
