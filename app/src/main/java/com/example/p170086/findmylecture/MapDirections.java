package com.example.p170086.findmylecture;



/**
 * Created by gerard on 31/12/16.
 *
 * <p>Main map view interactions, functions and markers are
 * done from this function</p>
 */
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;


public class MapDirections extends FragmentActivity implements OnMapReadyCallback {
    /**
     * The elements declared at this point
     * are a map object from google api, Button object, and two AutoComplete Objects
     */
    GoogleMap mGoogleMap;
    AutoCompleteTextView et;
    MaynoothDB s;
    public static  String[] places ={"John Hume Building","Eolas Building","Iontas Building","Student Union",
            "Logic House","Loftus Hall", "Aula Maxima","Arts Block","John Paul II Library", "Science Building", "Callan Building", "Froebel College of Education"};



    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        s = new MaynoothDB(MapDirections.this);
        s.open();
        s.populateMaynoothTable();



/**
 * On Create begins with checking wheather or not
 * a connection can be made to google maps
 */
        if(googleServicesAvailable()){ // if google services is connected to



            Toast.makeText(this, "Perfect! We're connected!", Toast.LENGTH_LONG).show();// flash message decalaring that the connection has been made
            setContentView(R.layout.mapresult); // layout from R file
            // note the array adapter which is used for the 2 declared autocomplete text views
            ArrayAdapter<String> adapter = new ArrayAdapter<String> (this,android.R.layout.simple_list_item_1, places);
            et = (AutoCompleteTextView)findViewById(R.id.eT); // connect the AutoCompleteTextViews

            // set adapter and Threshold level on AutoCompleteTextViews
            et.setAdapter(adapter);
            et.setThreshold(1);


            // initialize map object
            initMap();


        } else {
            // if error, return error page
            setContentView(R.layout.error);
        }


    }



    @Override
    public View findViewById(int id) {
        return super.findViewById(id);
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
        mGoogleMap.setMapType(mGoogleMap.MAP_TYPE_HYBRID);
        makeMarker("Maynooth University", "Welcome to Maynooth!", 53.382929,-6.603665); // place initial marker
        mGoogleMap.clear();

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


        for(int i = 0; i < places.length;i++){

            if(places[i].equals(location)){
                // clears markers from map on every use
                mGoogleMap.clear();

                // gets the coordinates of both user inputs
                getDest(location);
            } else {
                Toast.makeText(this, "Error in selection! Please try again!", Toast.LENGTH_LONG).show();
            }

        }




    }

    // Function to place markers
    public void makeMarker(String ToastLoc, String message, double lat, double lon){

        MarkerOptions mk = new MarkerOptions() //declare marker object
                .position(new LatLng(lat, lon)); // new latlong declared according to parameters
                mk.icon(BitmapDescriptorFactory.fromResource(R.mipmap.university));
        goToLocationZoom(lat, lon, 17);// goToLocationZoom() called to show location in one function

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

    public String[] returnSQLData(MaynoothDB s, String location){

        String hey = s.getData(location);// creates string of SQL table returns
        String[] newhey = hey.split("/"); // splits string
        return newhey; // returns split array

    }

// method returns venue on map
    public void getDest(String location){

        String[] dataResponse = returnSQLData(s, location); // returns sqldata

        double latitude = Double.valueOf(dataResponse[2]); // set latitude
        double longitude = Double.valueOf(dataResponse[3]); // set longitude
        // calls make marker method
        makeMarker(location, dataResponse[4], longitude, latitude);
        setMapInfoWindow(dataResponse[0], dataResponse[1], dataResponse[4]); // calls smapinfo method

    }


    public void setMapInfoWindow(String building, String venues, String information){
        // declares paramters for inner class as final
        final String buildingText = building;
        final String venuesText = venues;
        final String informationText = information;

        //sets new infowindowadapter
        mGoogleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                View v = getLayoutInflater().inflate(R.layout.infowindow, null); // sets infowindo
                // edits elements of the custom window view XML
                TextView tvloc = (TextView) v.findViewById(R.id.tv_locality);
                TextView tvlat = (TextView) v.findViewById(R.id.tv_lat);
                TextView tvlng = (TextView) v.findViewById(R.id.tv_lng);
                ImageView img = (ImageView) v.findViewById(R.id.imageView);
                tvloc.setText(buildingText);

                tvlat.setText(venuesText);

                tvlng.setText("Classrooms: " + informationText);

                switch (buildingText){

                    case "John Hume Building":
                        img.setImageResource(R.mipmap.hume6);
                        break;
                    case "Aula Maxima":
                        img.setImageResource(R.mipmap.aulamaxima);
                        break;
                    case "Loftus Halls":
                        img.setImageResource(R.mipmap.loftushalls);
                        break;
                    case "Arts Block":
                        img.setImageResource(R.mipmap.artsblock);
                        break;
                    case "Callan Building":
                        img.setImageResource(R.mipmap.callanbuilding);
                        break;
                    case "Eolas Building":
                        img.setImageResource(R.mipmap.eolas);
                        break;
                    case "Froebel College of Education":
                        img.setImageResource(R.mipmap.froebel);
                        break;
                    case "Iontas Building":
                        img.setImageResource(R.mipmap.iontas);
                        break;
                    case "John Paul II Library":
                        img.setImageResource(R.mipmap.library);
                        break;
                    case "Logic House":
                        img.setImageResource(R.mipmap.logichouse);
                        break;
                    case "Student Union":
                        img.setImageResource(R.mipmap.maynooth_students_union);
                        break;
                    case "Science Building":
                        img.setImageResource(R.mipmap.sciencebuilding);
                        break;



                }


                return v;
            }
        });
    }


}
