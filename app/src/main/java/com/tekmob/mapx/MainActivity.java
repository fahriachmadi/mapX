package com.tekmob.mapx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.android.PolyUtil;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;
import com.tekmob.mapx.database.AkunDatabaseHandler;
import com.tekmob.mapx.database.MapsDatabaseHandler;
import com.tekmob.mapx.database.PenandaDatabaseHandler;
import com.tekmob.mapx.domain.Akun;
import com.tekmob.mapx.domain.Maps;

import org.joda.time.DateTime;
import org.w3c.dom.Text;

import java.io.IOException;
import java.sql.Types;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.sql.Types.NULL;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,  OnMapClickListener, GoogleMap.OnMapLongClickListener, PlaceSelectionListener,
        LocationListener{


    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    Marker aMaker;
    PopupWindow popup;
    Button clickButton;
    Bundle extras;
    MapsDatabaseHandler dbMap = new MapsDatabaseHandler(this);
    boolean isBukanRetrieveLokasi = true;
    protected LocationManager locationManager;
    MarkerOptions markerOptionsCurr ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        locationManager =   (LocationManager) this
                .getSystemService(LOCATION_SERVICE);
    //get id user
        extras = getIntent().getExtras();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        AkunDatabaseHandler databaseHandler=new AkunDatabaseHandler(this);

        View header = navigationView.getHeaderView(0);

        TextView editTextUsername = (TextView)header.findViewById(R.id.header_username_id);
        TextView editTextEmail = (TextView) header.findViewById(R.id.header_email_id);

        Intent intent = getIntent();
        extras = getIntent().getExtras();
        int value = extras.getInt("id");


        Akun akun = databaseHandler.findOne(value);
        editTextUsername.setText(akun.getUsername());
        editTextEmail.setText(akun.getEmail());

        android.view.View fragment =  findViewById (R.id.place_autocomplete_fragment);

        fragment.setBackgroundColor(Color.WHITE);

        checkLocationPermission();


        //System.out.println(getGeoContext());

    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // TODO Auto-generated method stub

            Intent intent = new Intent(this, PenandaActivity.class);
            intent.putExtra("id", extras.getInt("id"));

            startActivity(intent);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {
            // TODO Auto-generated method stub

            Intent intent = new Intent(this, ProfileDesign.class);
            intent.putExtra("id", extras.getInt("id"));

            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

            if (extras != null) {
                int value = extras.getInt("id");
                Toast.makeText(MainActivity.this,"User id = " + value,
                        Toast.LENGTH_SHORT).show();
                //The key argument here must match that used in the other activity
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
//Google Maps
    @Override
    public void onConnected(@Nullable Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    //method for current position
    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place cur
        // rent location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        markerOptionsCurr = new MarkerOptions();
        markerOptionsCurr.position(latLng);
        markerOptionsCurr.title("Current Position");
        markerOptionsCurr.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptionsCurr);

        //move map camera
        if(isBukanRetrieveLokasi){
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
        }
        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

//        System.out.println("ASD");


    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                        Toast.makeText(this, "permission granted", Toast.LENGTH_LONG).show();

                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(this);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
                //Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
               // onLocationChanged(location);

            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }



        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String title =  marker.getTitle();
                if(title.equalsIgnoreCase("new")){

                    marker.remove();
                    popup.dismiss();
                }
                return false;
            }
        });


////        //show current location
//        if(checkLocationPermission()) {
//            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//
//            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//
//
//            markerOptionsCurr = new MarkerOptions();
//            markerOptionsCurr.position(latLng);
//
//            markerOptionsCurr.title("Current Position");
//            markerOptionsCurr.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
//            mCurrLocationMarker = mMap.addMarker(markerOptionsCurr);
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
//
//
//        }
////        else{
//            System.out.println("lola");
//
//
//        }
        //jika setelah show location
        if(extras.getInt("id_maps") != NULL){
            Maps map = dbMap.findOne(extras.getInt("id_maps"));



            LatLng latLng = new LatLng(Double.parseDouble(map.getKoordinatX()),Double.parseDouble(map.getKoordinatY()));
        //    System.out.println(latLng.toString());
            MarkerOptions saved_marker_option = new MarkerOptions().position(latLng).title("new");

            saved_marker_option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

            Marker saved_marker = mMap.addMarker(saved_marker_option);



            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

            mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(latLng , 17) );
            showPopupNavigasi(this, latLng);
            isBukanRetrieveLokasi =false;

        }





    }
// Old Search Without Helper
// public void onMapSearch(View view) {
//        EditText locationSearch = (EditText) findViewById(R.id.editText);
//        String location = locationSearch.getText().toString();
//        List<Address> addressList = null;
//
//        if (location != null || !location.equals("")) {
//            Geocoder geocoder = new Geocoder(this);
//            try {
//                addressList = geocoder.getFromLocationName(location, 1);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            Address address = addressList.get(0);
//            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
//            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
//            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//        }
//    }


    @Override
    public void onMapClick(LatLng latLng) {
    }

    @Override
    public void onMapLongClick(LatLng latLng) {


        showLocation(latLng);

        showPopup(this , latLng );
    }
    public void showLocation(LatLng latLng){
        //remove if there are some marker before

        if (aMaker != null ) {
            aMaker.remove();

        }


        MarkerOptions marker = new MarkerOptions().position(latLng).title("new");
        aMaker = mMap.addMarker(marker);

        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));



    }

    //jika udah seacrh maka di retrieve kesini
    @Override
    public void onPlaceSelected(Place place) {

        if (aMaker != null ) {
            aMaker.remove();

        }


        MarkerOptions marker = new MarkerOptions().position(place.getLatLng()).title("new");
        aMaker = mMap.addMarker(marker);

        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(place.getLatLng() , 17) );

        showPopup(this , place.getLatLng() );

    }

    @Override
    public void onError(Status status) {

    }

    // The method that displays the popup save location
    private void showPopup(final Activity context, final LatLng latLng) {
        if (popup != null) {
            popup.dismiss();
        }
        int popupWidth = 440;
        int popupHeight = 161;

        // Inflate the popup_layout.xml
        LinearLayout viewGroup = (LinearLayout) context.findViewById(R.id.pop_up);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.pop_up_button, viewGroup);

        // Creating the PopupWindow
          popup = new PopupWindow(context);
        popup.setContentView(layout);

        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.NO_GRAVITY, 900, 1800);

        // Clear the default translucent background
        popup.setBackgroundDrawable(new BitmapDrawable());




        clickButton = (Button)layout.findViewById(R.id.button_for_pop_up);


        clickButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(context, SaveLocation.class);
                intent.putExtra("id", extras.getInt("id"));

                intent.putExtra("koordinat", latLng.toString());
                startActivity(intent);

            }
        });


    }


    // The method that displays the popup save location
    private void showPopupNavigasi(final Activity context, final LatLng latLng) {
        if (popup != null) {
            popup.dismiss();
        }
        int popupWidth = 450;
        int popupHeight = 150;

        // Inflate the popup_layout.xml
        LinearLayout viewGroup = (LinearLayout) context.findViewById(R.id.pop_up_navigasi);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.pop_up_button_navigasi, viewGroup);

        // Creating the PopupWindow
        popup = new PopupWindow(context);
        popup.setContentView(layout);

        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.NO_GRAVITY, 900, 1600);

        // Clear the default translucent background
        popup.setBackgroundDrawable(new BitmapDrawable());




        clickButton = (Button)layout.findViewById(R.id.button_for_pop_up);


        clickButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    getDirection(markerOptionsCurr.getPosition() ,latLng,TravelMode.WALKING);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ApiException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    public void logout(MenuItem item) {
        Toast.makeText(this, "Signout", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    //direction
    private GeoApiContext getGeoContext() {
        GeoApiContext geoApiContext = new GeoApiContext();
        return geoApiContext.setQueryRateLimit(3).setApiKey("AIzaSyDPHgVT_cdIeOAMVc1HIAceOA0gtdkAzJA")
                .setConnectTimeout(1, TimeUnit.SECONDS)
                .setReadTimeout(1, TimeUnit.SECONDS)
                .setWriteTimeout(1, TimeUnit.SECONDS);
    }

    public void getDirection(LatLng origin,LatLng destination,TravelMode mode) throws InterruptedException, ApiException, IOException {
        DateTime now = new DateTime();

        DirectionsResult result = DirectionsApi.newRequest(getGeoContext())
                .mode(mode).origin( new com.google.maps.model.LatLng(origin.latitude,origin
                .longitude))
                .destination( new com.google.maps.model.LatLng(destination.latitude,destination
                        .longitude)).departureTime(now)
                .await();
        addPolyline(result,mMap);

    }
    private void addPolyline(DirectionsResult results, GoogleMap mMap) {
        List<LatLng> decodedPath = PolyUtil.decode(results.routes[0].overviewPolyline.getEncodedPath());
        mMap.addPolyline(new PolylineOptions().addAll(decodedPath));
    }


}
