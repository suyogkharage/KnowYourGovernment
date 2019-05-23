package com.example.knowyourgovernment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class OfficialActivity extends AppCompatActivity {

    private TextView Office;
    private TextView Name;
    private TextView Party;
    private TextView Address;
    private TextView Phone;
    private TextView Email;
    private TextView Website;
    private TextView locationAndZip;
    private ImageView imageView;

    private ConstraintLayout CL;
    String YT="";
    String GP="";
    String T="";
    String FB="";

    private ImageView YouTube;
    private ImageView GooglPlus;
    private ImageView Twitter;
    private ImageView Facebook;

    private String samplePhotoURL = "https://images-assets.nasa.gov/image/6900952/does_not_exist.jpg";
    private String locationZip="";
    private String office="";
    private String name="";
    private String photoUrl="";
    private String party="";
    String tagName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official);

        Office = findViewById(R.id.Office);
        Name = findViewById(R.id.Name);
        Party = findViewById(R.id.Party);
        Address = findViewById(R.id.Address);
        Phone = findViewById(R.id.Phone);
        Email = findViewById(R.id.Email);
        Website = findViewById(R.id.Website);
        YouTube = findViewById(R.id.YouTube);
        GooglPlus = findViewById(R.id.GooglePlus);
        Twitter = findViewById(R.id.Twiter);
        Facebook = findViewById(R.id.Facebook);
        locationAndZip = findViewById(R.id.locationAndZip);
        imageView = findViewById(R.id.mainImage);
        CL = findViewById(R.id.CL);

        Intent intent = getIntent();

        int blue = Color.parseColor("#0000FF");
        int brown = Color.parseColor("#8B0000");
        int red = Color.parseColor("#FF0000");
        int black = Color.parseColor("#000000");

        party = intent.getStringExtra("PartyName");
        if(intent.getStringExtra("PartyName").equalsIgnoreCase("democratic") ||
                intent.getStringExtra("PartyName").equalsIgnoreCase("democrat"))
        {
            CL.setBackgroundColor(blue);
        }
        else if(intent.getStringExtra("PartyName").equalsIgnoreCase("republican"))
        {
            CL.setBackgroundColor(red);
        }
        else{
            CL.setBackgroundColor(black);
        }
        //locationAndZip.setBackgroundColor(brown);

        locationZip = intent.getStringExtra("City") +", "+ intent.getStringExtra("State") +" "+ intent.getStringExtra("Zip");
        locationAndZip.setText(locationZip);

        name = intent.getStringExtra("Name");
        Name.setText(intent.getStringExtra("Name"));

        office = intent.getStringExtra("OfficeName");
        Office.setText(intent.getStringExtra("OfficeName"));

        Party.setText("("+intent.getStringExtra("PartyName")+")");


        Address.setText(intent.getStringExtra("Address"));
        Address.setLinkTextColor(Color.parseColor("#FFFFFF"));
        Linkify.addLinks(Address, Linkify.ALL);


        if(intent.getStringExtra("Phone").equals(""))
        {
            Phone.setText("No Data Provided");
        }
        else{
            Phone.setText(intent.getStringExtra("Phone"));
            Phone.setLinkTextColor(Color.parseColor("#FFFFFF"));
            Linkify.addLinks(Phone, Linkify.ALL);

        }

        if(intent.getStringExtra("Email").equals(""))
        {
            Email.setText("No Data Provided");
        }
        else
        {
            Email.setText(intent.getStringExtra("Email"));
            Email.setLinkTextColor(Color.parseColor("#FFFFFF"));
            Linkify.addLinks(Email, Linkify.ALL);

        }

        if(intent.getStringExtra("Website").equals(""))
        {
            Website.setText("No Data Provided");
        }
        else
        {
            Website.setText(intent.getStringExtra("Website"));
            Website.setLinkTextColor(Color.parseColor("#FFFFFF"));
            Linkify.addLinks(Website, Linkify.ALL);
        }


        YT = intent.getStringExtra("YT");
        if(YT.length()>0)
            YouTube.setVisibility(View.VISIBLE);
        else
            YouTube.setVisibility(View.INVISIBLE);

        GP = intent.getStringExtra("GP");
        if(GP.length()>0){
            GooglPlus.setVisibility(View.VISIBLE);}
        else{
            GooglPlus.setVisibility(View.INVISIBLE);}

        T = intent.getStringExtra("T");
        if(T.length()>0){
            Twitter.setVisibility(View.VISIBLE);}
        else{
            Twitter.setVisibility(View.INVISIBLE);}

        FB = intent.getStringExtra("FB");
        if(FB.length()>0){
            Facebook.setVisibility(View.VISIBLE);}
        else{
            Facebook.setVisibility(View.INVISIBLE);}



        photoUrl = intent.getStringExtra("PhotoURL");
        final String imageURL = intent.getStringExtra("PhotoURL");
        String netchecker = intent.getStringExtra("netchecker");

        if(netchecker.equalsIgnoreCase("yes"))
        {
            if(imageURL!=null){
                Picasso picasso = new Picasso.Builder(this)
                        .listener(new Picasso.Listener() {
                            @Override
                            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                                final String changedURL = imageURL.replace("http:","https:");
                                picasso.load(changedURL)
                                        .error(R.drawable.brokenimage)
                                        .placeholder(R.drawable.placeholder)
                                        .into(imageView);
                            }
                        }).build();

                picasso.load(imageURL)
                        .error(R.drawable.brokenimage)
                        .placeholder(R.drawable.placeholder)
                        .into(imageView);
                imageView.setTag("clickable");

            }
            else{
                Picasso.get().load(imageURL)
                        .error(R.drawable.brokenimage)
                        .placeholder(R.drawable.missingimage)
                        .into(imageView);
                imageView.setTag("nonClickable");
            }

        }
        else
        {
            Picasso.get().load(R.drawable.placeholder)
                    .error(R.drawable.brokenimage)
                    .placeholder(R.drawable.missingimage)
                    .into(imageView);
            imageView.setTag("nonClickable");

        }

        tagName = (String) imageView.getTag();

        if(tagName.equalsIgnoreCase("s"))
        {

        }
    }


    public void newAct(View v)
    {
        String tag = (String) imageView.getTag();
        if(tag.equalsIgnoreCase("clickable")){

            Intent i = new Intent(OfficialActivity.this, PhotoDetailActivity.class);
            i.putExtra("locZip",locationZip);
            i.putExtra("OfficeName",office);
            i.putExtra("name",name);
            i.putExtra("PhotoUrl",photoUrl);
            i.putExtra("party",party);

            startActivity(i);
        }
    }

    public void youTubeClicked(View v)
    {
        String name = YT;
        Intent intent1 = null;
        try
        {
            intent1 = new Intent(Intent.ACTION_VIEW);
            intent1.setPackage("com.google.android.youtube");
            intent1.setData(Uri.parse("https://www.youtube.com/" + name));
            startActivity(intent1);
        }
        catch (ActivityNotFoundException e)
        {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/" + name)));
        }
    }

    public void googlePlusClicked(View v) {
        String name = GP;
        Intent intent = null;
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setClassName("com.google.android.apps.plus", "com.google.android.apps.plus.phone.UrlGatewayActivity");
            intent.putExtra("customAppUri", name);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/" + name)));
        }
    }

    public void twitterClicked(View v)
    {
        Intent intent = null;
        String name = T;
        try
        {
            getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + name));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        catch (Exception e)
        {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + name));
        }
        startActivity(intent);
    }

    public void facebookClicked(View v)
    {
        String FACEBOOK_URL = "https://www.facebook.com/" + FB;
        String urlToUse;
        PackageManager packageManager = getPackageManager();
        try
        {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850)
            {
                //newer versions of fb app
                urlToUse = "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            }
            else
            {
                //older versions of fb app
                urlToUse = "fb://page/" + FB;
            }
        }
        catch (PackageManager.NameNotFoundException e)
        {
            urlToUse = FACEBOOK_URL;
            //normal web url
        }
        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        facebookIntent.setData(Uri.parse(urlToUse));
        startActivity(facebookIntent);
    }

}
