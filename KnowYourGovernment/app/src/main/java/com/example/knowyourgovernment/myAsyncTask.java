package com.example.knowyourgovernment;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class myAsyncTask extends AsyncTask<String, Integer, String> {

    private MainActivity mainActivity;
    private static final String TAG = "MyAsyncTask";
    private String key = "YOUR_API_KEY";
    private String addr;
    private String zip;

    public myAsyncTask(MainActivity ma) {
        mainActivity = ma;
    }

    @Override
    protected void onPostExecute(String s) {
        ArrayList<Official> officialList = parseJsonForFinancialData(s);
        mainActivity.afterAsync(officialList);
    }

    private ArrayList<Official> parseJsonForFinancialData(String s) {

        String mycity="";
        String mystate="";
        String myzip="";
        String officeName="";
        int index;
        String OfficialName="";
        String line1="";
        String line2="";
        String line3="";
        String city="";
        String state="";
        String zip="";
        String compositeAddr="";
        String party="";
        String phone="";
        String url="";
        String email="";
        String photoURL="";
        String GP="GooglePlus";
        String FB="Facebook";
        String TW="Twitter";
        String YT="YouTube";
        String GPid="";
        String FBid="";
        String TWid="";
        String YTid="";

        Official o = null;
        ArrayList<Official> list = new ArrayList<>();
        try{
            JSONObject mainObj = new JSONObject(s);

            if(mainObj.has("error"))
            {

            }
            else
            {
                JSONObject normalizedOBJ = mainObj.getJSONObject("normalizedInput");

                mycity = normalizedOBJ.getString("city");
                mystate = normalizedOBJ.getString("state");
                myzip = normalizedOBJ.getString("zip");


                JSONArray officesArray = new JSONArray();
                officesArray = mainObj.getJSONArray("offices");

                JSONObject objUnderOfficesArray = new JSONObject();
                JSONArray officialIndicesArray = new JSONArray();
                JSONArray officialsArray = new JSONArray();
                JSONObject objUnderOfficials = new JSONObject();
                JSONArray addressArray = new JSONArray();
                JSONObject objUnderAddressArray = new JSONObject();
                JSONArray phoneArray = new JSONArray();
                JSONArray urlArray = new JSONArray();
                JSONArray emailArray = new JSONArray();
                JSONArray channelsArray = new JSONArray();
                JSONObject objUnderChannels = new JSONObject();

                for(int j=0;j<officesArray.length();j++)
                {
                    objUnderOfficesArray = officesArray.getJSONObject(j);  //change index here

                    if(objUnderOfficesArray.has("name"))
                    {
                        officeName = objUnderOfficesArray.getString("name"); // POTUS
                    }


                    officialIndicesArray = objUnderOfficesArray.getJSONArray("officialIndices");

                    index = officialIndicesArray.getInt(0);


                    officialsArray = mainObj.getJSONArray("officials");

                    objUnderOfficials = officialsArray.getJSONObject(index);

                    if(objUnderOfficials.has("name"))
                    {
                        OfficialName = objUnderOfficials.getString("name"); //donald trump
                    }
                    else{
                        OfficialName = "No Data Provided";
                    }

                    if(objUnderOfficials.has("address")) {
                        addressArray = objUnderOfficials.getJSONArray("address");

                        objUnderAddressArray = addressArray.getJSONObject(0);


                        line1 = objUnderAddressArray.getString("line1");     //address
                        city = objUnderAddressArray.getString("city");       //address
                        state = objUnderAddressArray.getString("state");     //address
                        zip = objUnderAddressArray.getString("zip");         //address

                        if (objUnderAddressArray.length() == 5) {
                            line2 = objUnderAddressArray.getString("line2");     //address
                        }
                        if (objUnderAddressArray.length() == 6) {
                            line3 = objUnderAddressArray.getString("line3");    //address
                        }
                        compositeAddr = line1+" "+line2+" "+line3+" "+city+" "+state+" "+zip;
                    }
                    else{
                        compositeAddr="No Data Provided";
                    }



                    if(objUnderOfficials.has("party"))
                    {
                        party = objUnderOfficials.getString("party");        //party
                    }
                    else{
                        party="No Data Provided";
                    }

                    if(objUnderOfficials.has("phones"))
                    {
                        phoneArray = objUnderOfficials.getJSONArray("phones");
                        phone = phoneArray.getString(0);                        //phones
                    }
                    else{
                        phone = "No Data Provided";
                    }

                    if(objUnderOfficials.has("urls"))
                    {
                        urlArray = objUnderOfficials.getJSONArray("urls");
                        url = urlArray.getString(0);                            //url
                    }
                    else{
                        url="No Data Provided";
                    }

                    if(objUnderOfficials.has("emails"))
                    {
                        emailArray = objUnderOfficials.getJSONArray("emails");
                        email = emailArray.getString(0);                        //emails
                    }
                    else{
                        email="No Data Provided";
                    }

                    if(objUnderOfficials.has("photoUrl"))
                    {
                        photoURL = objUnderOfficials.getString("photoUrl");     //photoURL
                    }
                    else{
                        photoURL=null;
                    }

                    if(objUnderOfficials.has("channels"))
                    {
                        channelsArray = objUnderOfficials.getJSONArray("channels");     //channels

                        for(int i=0;i<channelsArray.length();i++)
                        {
                            objUnderChannels = channelsArray.getJSONObject(i);

                            if(objUnderChannels.getString("type").equals(GP)){
                                GPid = objUnderChannels.getString("id");
                            }
                            if(objUnderChannels.getString("type").equals(FB)){
                                FBid = objUnderChannels.getString("id");
                            }
                            if(objUnderChannels.getString("type").equals(TW)){
                                TWid = objUnderChannels.getString("id");
                            }
                            if(objUnderChannels.getString("type").equals(YT)){
                                YTid = objUnderChannels.getString("id");
                            }
                        }

                    }
                    o = new Official(mycity,mystate,myzip,officeName,OfficialName,
                            compositeAddr,party,phone,url,email,photoURL,GPid,FBid,TWid,YTid);

                    list.add(o);

                    officeName="";OfficialName="";line1="";line2="";line3="";
                    city="";state="";zip="";compositeAddr="";party="";phone="";url="";email="";photoURL="";
                    GPid="";FBid="";TWid="";YTid="";
                }
            }

        }
        catch (Exception e){}

        return  list;
    }

    @Override
    protected String doInBackground(String... strings) {
        zip = strings[0];
        addr = strings[1];

        Uri dataUri=null;
        if(addr.equalsIgnoreCase("null")){
            dataUri = Uri.parse("https://www.googleapis.com/civicinfo/v2/representatives?key="+key+"&address="+zip);
        }
        else if(zip.equalsIgnoreCase("null")){
            dataUri = Uri.parse("https://www.googleapis.com/civicinfo/v2/representatives?key="+key+"&address="+addr);
        }
        else{
            dataUri = Uri.parse("https://www.googleapis.com/civicinfo/v2/representatives?key="+key+"&address="+zip);
        }


        String urlToUse = dataUri.toString();
        Log.d(TAG, "doInBackground: " + urlToUse);

        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(urlToUse);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            Log.d(TAG, "doInBackground: ResponseCode: " + conn.getResponseCode());

            conn.setRequestMethod("GET");

            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader((new InputStreamReader(is)));

            String line;
                //sb.append("[");
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }
                //sb.append("]");

            Log.d(TAG, "doInBackground: " + sb.toString());

        } catch (Exception e) {
            Log.e(TAG, "doInBackground: ", e);
            return null;
        }

        Log.d(TAG, "doInBackground: " + sb.toString());

        return sb.toString();
    }

}
