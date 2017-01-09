package com.example.p170086.findmylecture;



/**
 * Created by gerard on 31/12/16.
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

// AIzaSyBZnr2lBPO0dhaIqvGBhgLAEPwlyqN-xlo
public class MapDirections extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap mGoogleMap;
    Button go;
    AutoCompleteTextView et;
    AutoCompleteTextView tT;


    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);


        if(googleServicesAvailable()){


            Toast.makeText(this, "Perfect!", Toast.LENGTH_LONG).show();
            setContentView(R.layout.mapresult);

            String[] places ={"John Hume Building","Eolas Building","Iontas Building","Student Union","Arts Block","John Paul II Library", "Science Building", "Callan Building", "Froebel College of Education"};
            ArrayAdapter<String> adapter = new ArrayAdapter<String> (this,android.R.layout.simple_list_item_1, places);
            et = (AutoCompleteTextView)findViewById(R.id.eT);
            tT = (AutoCompleteTextView)findViewById(R.id.TT);


            et.setAdapter(adapter);
            et.setThreshold(1);


            tT.setAdapter(adapter);
            tT.setThreshold(1);




            initMap();

            //text=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1);
            //text1=(MultiAutoCompleteTextView)findViewById(R.id.multiAutoCompleteTextView1);

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
        call();


    }

    public void call()throws IOException{

        final String location = et.getText().toString();
        final String location2 = tT.getText().toString();

        Geocoder gc = new Geocoder(this);
      //  List<Address> list = gc.getFromLocationName(location,1);
       // Address address = list.get(0);
        // String locality = address.getLocality();

        //Toast.makeText(this, locality, Toast.LENGTH_LONG).show();

        double lat =  0;
        double lon = 0;




        goToLocationZoom(lat, lon, 17);




        Thread timer = new Thread() {
            public void run() {
                /**
                 * try to sleep() for time
                 */
                try {

                    sleep(3000);
                    /**
                     * if interrupted catch exception as e
                     */
                } catch (InterruptedException e) {

                    e.printStackTrace();
                    /**
                     * upon completion of trial without occurrence of e
                     */
                } finally {
                    /**
                     * @param homePage is Intent leading to Menu page
                     */


                }


            }
        };

        getDest(location2);

        timer.start();

        getLoc(location);

       /** if(location2.length()!= 0) {

            List<Address> list2 = gc.getFromLocationName(location2,1);
            Address address2 = list2.get(0);
            String locality2 = address2.getLocality();

            double lat1 =  address2.getLatitude();
            double lon1 = address2.getLongitude();

            MarkerOptions mk1 = new MarkerOptions()
                    .title(locality2)
                    .position(new LatLng(lat1, lon1));

            mGoogleMap.addMarker(mk1);

        } else {
            Toast.makeText(this, "Hey! Tell me where you want to go!", Toast.LENGTH_LONG).show();
        } **/


    }

    public void getDest(String location2){
        double lat, lon;

        if(location2.equals("Eolas Building")) {

            lat = 53.384656;
            lon = -6.6601530;
            mapPlacements(lat, lon);
            Toast.makeText(this, "Eolas Building", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Doing some programming?", Toast.LENGTH_LONG).show();

        } else if (location2.equals("John Hume Building")){

            lat = 53.383891;
            lon = -6.599503;
            mapPlacements(lat, lon);
            Toast.makeText(this, "John Hume Building", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "There's 7 Lecture Theatres in here!", Toast.LENGTH_LONG).show();

        } else if (location2.equals("Student Union")){

            lat = 53.382929;
            lon = -6.603665;
            mapPlacements(lat, lon);
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

    public void mapPlacements(double lat, double lon){

        goToLocationZoom(lat, lon, 9);
        MarkerOptions mk = new MarkerOptions()
                .position(new LatLng(lat, lon));
        mGoogleMap.addMarker(mk);

    }
}
