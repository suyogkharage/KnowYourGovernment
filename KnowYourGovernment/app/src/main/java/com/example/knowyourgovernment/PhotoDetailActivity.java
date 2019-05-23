package com.example.knowyourgovernment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PhotoDetailActivity extends AppCompatActivity {

    private TextView locZip;
    private TextView officeName;
    private TextView name;
    private ImageView image;
    private String PhotoUrl="";
    private String party="";
    private ConstraintLayout CL1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        locZip = findViewById(R.id.locAndZip);
        officeName = findViewById(R.id.officeName);
        name = findViewById(R.id.name);
        image = findViewById(R.id.image);
        CL1 = findViewById(R.id.CL1);

        Intent i = getIntent();

        party = i.getStringExtra("party");
        if (party.equalsIgnoreCase("democratic") || party.equalsIgnoreCase("democrat")) {
            CL1.setBackgroundColor(Color.parseColor("#0000FF"));
        } else if (party.equalsIgnoreCase("republican")) {
            CL1.setBackgroundColor(Color.parseColor("#FF0000"));
        } else {
            CL1.setBackgroundColor(Color.parseColor("#000000"));
        }

        locZip.setText(i.getStringExtra("locZip"));
        officeName.setText(i.getStringExtra("OfficeName"));
        name.setText(i.getStringExtra("name"));
        PhotoUrl = i.getStringExtra("PhotoUrl");


        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        Picasso picasso = null;
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            picasso = new Picasso.Builder(this)
                    .listener(new Picasso.Listener() {
                        @Override
                        public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                            final String changedURL = PhotoUrl.replace("http:", "https:");
                            picasso.load(changedURL)
                                    .error(R.drawable.brokenimage)
                                    .placeholder(R.drawable.placeholder)
                                    .into(image);
                        }
                    }).build();

            picasso.load(PhotoUrl)
                    .error(R.drawable.brokenimage)
                    .placeholder(R.drawable.placeholder)
                    .into(image);

        } else {
            Picasso.get().load(R.drawable.placeholder)
                    .error(R.drawable.brokenimage)
                    .placeholder(R.drawable.missingimage)
                    .into(image);

        }


    }
}
