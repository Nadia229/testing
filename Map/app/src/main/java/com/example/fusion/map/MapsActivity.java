package com.example.fusion.map;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fusion.map.Model.Ambulance;
import com.example.fusion.map.Model.AmbulanceSpeciality;
import com.example.fusion.map.Model.BlodBank;
import com.example.fusion.map.Model.Blood;
import com.example.fusion.map.Model.Hospital;
import com.example.fusion.map.Model.HospitalSpeciality;
import com.example.fusion.map.Model.Test;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.fusion.map.R.id.map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    MarkerOptions markerOptions;



    //<====for gpsTracker
    double latitude; // latitude
    double longitude; // longitude
    GPSTracker gps;
    //===================>
    String[] hospitalName = new String[20];
    double[] latitudeN = new double[20];
    double[] longitudeN = new double[20];
    double[] result = new double[20];
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    FirebaseDatabase hospitaldb;
    DatabaseReference hospitalref;
    FirebaseDatabase ambulancedb;
    DatabaseReference ambulanceref;
    FirebaseDatabase blooddb;
    DatabaseReference bloodref;
    ListView listView;
    ArrayAdapter<String> itemsAdapter;
    List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);
        listView= (ListView) findViewById(R.id.listview);
        listView.setVisibility(View.GONE);
        list=new ArrayList<String>();
        itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(itemsAdapter);


        hospitaldb = FirebaseDatabase.getInstance();
        hospitalref = hospitaldb.getReference("Hospital");

        ambulancedb = FirebaseDatabase.getInstance();
        ambulanceref = ambulancedb.getReference("Ambulance");

        blooddb = FirebaseDatabase.getInstance();
        bloodref = blooddb.getReference("Blood");
        listView.setBackgroundColor(Color.WHITE);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        //loadAmbulanceData();
       // loadHospitalData();
     // loadBloodData();




    }

    private void loadBloodData() {
       Blood blood=new Blood();



        bloodref.push().setValue(blood);



    }


    private void loadHospitalData() {



        Hospital hospital=new Hospital();
        hospital.blodbank=new BlodBank();
        hospital.speciality=new HospitalSpeciality();






        hospitalref.push().setValue(hospital);






    }

    private void loadAmbulanceData() {





        Ambulance ambulance=new Ambulance();




        ambulanceref.push().setValue(ambulance);



    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    public boolean isMoveCameraActivated=false;
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {


              //  navigate(new LatLng(marker.getPosition().latitude,marker.getPosition().longitude));
                marker.showInfoWindow();

                return true;
            }
        });


        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {

                return null;
            }

            @Override
            public View getInfoContents(final Marker marker) {

                View v = getLayoutInflater().inflate(R.layout.info_window_layout, null);
                Button button = (Button) v.findViewById(R.id.button2);
                TextView textView = (TextView) v.findViewById(R.id.textView3);
                TextView textView1 = (TextView) v.findViewById(R.id.textView26);
                LatLng ll = marker.getPosition();
                textView1.setText(marker.getTitle());
                textView.setText(marker.getSnippet());
                //textView.setText(marker.getSnippet());
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });



                return v;
            }
        });

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {

                @Override
                public void onLocationChanged(Location location)
                {
                    final double latitude = location.getLatitude();
                    final double longitude = location.getLongitude();
                    LatLng latLng = new LatLng(latitude, longitude);

                    Geocoder geocoder = new Geocoder(getApplicationContext());


                    try {

                        List<android.location.Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                        String str = addressList.get(0).getLocality() + ",";
                        str += addressList.get(0).getCountryName();
                        markerOptions=new MarkerOptions().position(latLng).title(str);

                        Marker  marker=mMap.addMarker(markerOptions);

                        if(!isMoveCameraActivated)
                        {
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
                            isMoveCameraActivated=true;
                        }
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                    }






                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }
        else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location)
                {

                        final double latitude = location.getLatitude();
                        final double longitude = location.getLongitude();
                        LatLng latLng = new LatLng(latitude, longitude);

                        Geocoder geocoder = new Geocoder(getApplicationContext());
                        try {


                            List<android.location.Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                            String str = addressList.get(0).getLocality() + ",";
                            str += addressList.get(0).getCountryName();
                           mMap.clear();
                            mMap.addMarker(new MarkerOptions().position(latLng).title(str));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });

        }



        mMap.setMyLocationEnabled(true);
        final Button Hospital = (Button) findViewById(R.id.B_hopistals);
        Hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Log.d("onClick", "Button is Clicked");
                mMap.clear();
                if(markerOptions!=null)
                mMap.addMarker(markerOptions);
                          /*  String url = getUrl(latitude, longitude, bhospital);
                            Object[] DataTransfer = new Object[2];
                            DataTransfer[0] = mMap;
                            DataTransfer[1] = url;
                            Log.d("onClick", url);
                            GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
                            getNearbyPlacesData.execute(DataTransfer);*/

                hospitalref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        list.clear();
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            try{
                                Hospital hospital= child.getValue(Hospital.class);
                               // Log.d("Hospital Data",hospital.name);
                                Marker temp=mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(hospital.latituade,hospital.longitude))
                                        .snippet(hospital.name+""+ hospital.catagory)
                                        .title(hospital.contactNumber).icon(BitmapDescriptorFactory.fromResource(R.drawable.dw)));
                                temp.showInfoWindow();
                                list.add(hospital.name);



                            }
                            catch (Exception ex)
                            {
                                ex.printStackTrace();
                            }
                        }
                        listView.setVisibility(View.VISIBLE);
                        itemsAdapter.notifyDataSetChanged();


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
        Button blood = (Button) findViewById(R.id.B_blood);
        blood.setOnClickListener(new View.OnClickListener()

        {
            public void onClick(View v) {
                Log.d("onClick", "Button is Clicked");
                mMap.clear();
                mMap.addMarker(markerOptions);
                //ambulance
                bloodref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        list.clear();
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            Blood blood1 = child.getValue(Blood.class);
                            Marker temp = mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(blood1.latitude,blood1.longitude))
                                    .snippet(blood1.orgName + "" + blood1.adress)
                                    .title(blood1.contactnumber)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.bld)));
                            temp.showInfoWindow();
                            list.add(blood1.orgName);

                        }
                        itemsAdapter.notifyDataSetChanged();


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }


        });




        Button Ambulance = (Button) findViewById(R.id.B_Ambulance);
        Ambulance.setOnClickListener(new View.OnClickListener()

        {
            public void onClick(View v) {
                Log.d("onClick", "Button is Clicked");
                mMap.clear();
                mMap.addMarker(markerOptions);
                //ambulance
                ambulanceref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        list.clear();
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            Ambulance ambulance = child.getValue(Ambulance.class);
                            Marker temp = mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(ambulance.latitude, ambulance.longitude))
                                    .snippet(ambulance.name + "" + ambulance.price)
                                    .title(ambulance.contactNumber)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ambu)));
                            temp.showInfoWindow();
                            list.add(ambulance.name);

                        }
                        itemsAdapter.notifyDataSetChanged();


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }


        });

       mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            public void onInfoWindowClick(Marker arg0) {
                if (arg0 != null ) {

                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+arg0.getTitle()));
                    startActivity(intent);
                }




            }
        });
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                listView.setVisibility(View.GONE);
            }
        });





        //GPS Tracker
      /* gps = new GPSTracker(MapsActivity.this);

        // check if GPS enabled
       if (gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            // \n is for new line
            //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
           // gps.showSettingsAlert();
        }
        //==========>
        for (int i = 0; i < 20; i++) {
            result[i] = distance(latitude, longitude, latitudeN[i], longitudeN[i]);
        }
        Arrays.sort(result);


        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(latitude, longitude);
         mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

    }


}
