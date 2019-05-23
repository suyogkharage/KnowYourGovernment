package com.example.knowyourgovernment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private List<Official> officialList = new ArrayList<>();

    private RecyclerView recyclerView;

    private OfficialAdapter oAdapter;

    private static final String TAG = "MainActivity";

    private Locator locator;
    private int MY_PERM_REQUEST_CODE = 12345;
    private Location bestLocation;
    private boolean showingInfo = false;
    private TextView cityAndZip;
    Double latitute;
    Double longitude;
    String city;
    String postalcode;
    String netchecker;
    String searchCity="";
    String searchZip="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler);
        oAdapter = new OfficialAdapter(officialList, this);
        recyclerView.setAdapter(oAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if(doNetCheck()==1){
            locator = new Locator(this);

            callAsyncTask(postalcode,city);


        }
        else{
            disappearingDialog("No Network Connection","Data Cannot Be Accessed/loaded Without An Internet Connection");
        }
         cityAndZip = findViewById(R.id.cityAndZip);
    }

    private int doNetCheck() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            netchecker = "yes";
            return 1;
        } else {
            netchecker="no";
            return 0;
        }
    }
    public void disappearingDialog(String title, String message){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setTitle(title).setMessage(message);

        final AlertDialog alert = dialog.create();
        alert.show();
        new CountDownTimer(30000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {}

            @Override
            public void onFinish() { alert.dismiss(); }
        }.start();
    }


    public void setData(double lat, double lon) {


        Log.d(TAG, "setData: Lat: " + lat + ", Lon: " + lon);
        String address = doAddress(41.8313, -87.6272);

    }

    private void callAsyncTask(String postalcode, String city) {
        new myAsyncTask(this).execute(postalcode,city);




    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (requestCode == 5) {
            Log.d(TAG, "onRequestPermissionsResult: permissions.length: " + permissions.length);
            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "onRequestPermissionsResult: HAS PERM");
                        locator.setUpLocationManager();
                        locator.determineLocation();

                    } else {
                        Toast.makeText(this, "Location permission was denied - cannot determine address", Toast.LENGTH_LONG).show();
                        Log.d(TAG, "onRequestPermissionsResult: NO PERM");
                    }
                }
            }
        }
        Log.d(TAG, "onRequestPermissionsResult: Exiting onRequestPermissionsResult");
    }





  private String doAddress(double latitude, double longitude) {

        Log.d(TAG, "doAddress: Lat: " + latitude + ", Lon: " + longitude);
        List<Address> addresses = null;
        for (int times = 0; times < 1; times++) {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            try {
                Log.d(TAG, "doAddress: Getting address now");


                addresses = geocoder.getFromLocation(latitude, longitude, 1);
                Log.d(TAG, "doAddress: Num addresses: " + addresses.size());

                StringBuilder sb = new StringBuilder();

                for (Address ad : addresses) {
                    Log.d(TAG, "doLocation: " + ad);
                    city = ad.getLocality();
                    postalcode = ad.getPostalCode();

                    sb.append(city+","+postalcode);
                }
                return sb.toString();
            } catch (IOException e) {
                Log.d(TAG, "doAddress: " + e.getMessage());

            }
            Toast.makeText(this, "GeoCoder service is slow - please wait", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, "GeoCoder service timed out - please try again", Toast.LENGTH_LONG).show();
        return null;
    }

    public void noLocationAvailable() {
        Toast.makeText(this, "No location providers were available", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v){

        int a =doNetCheck();
        int pos = recyclerView.getChildLayoutPosition(v);
        Official o = officialList.get(pos);

        Intent intent = new Intent(MainActivity.this, OfficialActivity.class);

        intent.putExtra("Name", o.getName());
        intent.putExtra("OfficeName", o.getOfficeName());
        intent.putExtra("PartyName", o.getPartyName());
        intent.putExtra("Address", o.getAddress());
        intent.putExtra("Phone", o.getPhone());
        intent.putExtra("Email", o.getEmail());
        intent.putExtra("Website", o.getWebsite());
        intent.putExtra("PhotoURL",o.getPhotoURL());
        intent.putExtra("City",o.getCity());
        intent.putExtra("State",o.getState());
        intent.putExtra("Zip",o.getZip());
        intent.putExtra("YT", o.getYT());
        intent.putExtra("GP", o.getGP());
        intent.putExtra("T", o.getT());
        intent.putExtra("FB", o.getFB());
        intent.putExtra("netchecker",netchecker);
        startActivity(intent);

    }

    @Override
    public boolean onLongClick(View v) {  // long click listener called by ViewHolder long clicks

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.about:
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                return true;
            case R.id.location:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                final EditText et = new EditText(this);
                et.setInputType(InputType.TYPE_CLASS_TEXT);
                et.setGravity(Gravity.CENTER_HORIZONTAL);

                builder.setView(et);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    //Toast.makeText(getApplicationContext(),"You pressed OK",Toast.LENGTH_SHORT).show();
                        doLocationName(et.getText().toString());
                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

                builder.setTitle("Enter a City, State or a Zip Code:");

                AlertDialog dialog = builder.create();
                dialog.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void doLocationName(String location) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses;

            String loc = location;
            addresses = geocoder.getFromLocationName(loc, 10);

                displayAddresses(addresses);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayAddresses(List<Address> addresses) {
        if (addresses.size() == 0) {
            //((TextView) findViewById(R.id.textView)).setText(R.string.nothing_found);
            return;
        }

        for (Address ad : addresses) {
            searchCity = ad.getLocality();
            searchZip = ad.getPostalCode();
        }

        if(searchZip==null)
        {searchZip="null";}
        if(searchCity==null)
        {searchCity="null";}
        callAsyncTask(searchZip,searchCity);
        //((TextView) findViewById(R.id.textView)).setText(sb.toString());
    }

    @Override
    protected void onDestroy() {
        locator.shutdown();
        super.onDestroy();
    }

    public  void afterAsync(ArrayList<Official> list)
    {
        if(list.size()==0){
           officialList.clear();
           oAdapter.notifyDataSetChanged();
           cityAndZip.setText("");
           disappearingDialog("No data available","Please enter proper input");
        }
        else{
            officialList.clear();
            officialList.addAll(list);
            oAdapter.notifyDataSetChanged();

            cityAndZip.setText(officialList.get(0).getCity()+", "+officialList.get(0).getState()+" "+officialList.get(0).getZip());

        }
    }
}
