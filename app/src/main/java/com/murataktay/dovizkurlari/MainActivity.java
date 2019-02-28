package com.murataktay.dovizkurlari;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends AppCompatActivity {
    TextView cadTextV;
    TextView sterTextV;
    TextView usdTextV;
    TextView chfTextV;
    TextView euroTextV;
    TextView rusTextV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usdTextV = findViewById(R.id.usdText);
        cadTextV = findViewById(R.id.cadText);
        sterTextV = findViewById(R.id.sterText);
        chfTextV = findViewById(R.id.chfText);
        euroTextV = findViewById(R.id.euroText);
        rusTextV = findViewById(R.id.rusText);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menubutton, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.cevir){
            getRates();
        }
        return super.onOptionsItemSelected(item);
    }

    public void getRates(){
        DownloadData downloadData = new DownloadData();
        try{
            String url = "https://www.doviz.com/api/v1/currencies/all/latest";

            downloadData.execute(url);


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private class DownloadData extends AsyncTask<String, Void, String >{
        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            URL url;
            HttpsURLConnection httpsURLConnection;
            try{
                url = new URL(strings[0]);
                httpsURLConnection = (HttpsURLConnection) url.openConnection();
                InputStream inputStream = httpsURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data  = inputStreamReader.read();

                while (data>0){
                    char character = (char) data;
                    result += character;
                    data = inputStreamReader.read();
                }
                return result;
            }catch (Exception e){
                System.out.println(e.getMessage());
                return null;
            }
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray jsonArray = new JSONArray(s);

                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String usd = jsonObject.getString("selling");
                float usdf = Float.parseFloat(usd);
                usdTextV.setText(new DecimalFormat("##.###").format(usdf  + " USD"));
                usdTextV.setFocusable(false);

                jsonObject = jsonArray.getJSONObject(1);
                String euro = jsonObject.getString("selling");
                float euroF = Float.parseFloat(euro);
                euroTextV.setText(new DecimalFormat("##.###").format(euroF )+ " EURO");
                euroTextV.setFocusable(false);

                jsonObject = jsonArray.getJSONObject(2);
                String ster = jsonObject.getString("selling");
                float sterF = Float.parseFloat(ster);
                sterTextV.setText(new DecimalFormat("##.###").format(sterF ) + " GBP");


                jsonObject = jsonArray.getJSONObject(3);
                String chf = jsonObject.getString("selling");
                float chfF = Float.parseFloat(chf);
                chfTextV.setText(new DecimalFormat("##.###").format(chfF ) + " CHF");
                chfTextV.setFocusable(false);


                jsonObject = jsonArray.getJSONObject(4);
                String cad = jsonObject.getString("selling");
                float cadF = Float.parseFloat(cad);
                cadTextV.setText(new DecimalFormat("##.###").format(cadF ) + " CAD");
                cadTextV.setFocusable(false);

                jsonObject = jsonArray.getJSONObject(5);
                String rus = jsonObject.getString("selling");
                float rusF = Float.parseFloat(rus);
                rusTextV.setText(new DecimalFormat("##.###").format(rusF ) + " RUB");
                rusTextV.setFocusable(false);
            }catch (Exception e){
                System.out.println(e.getMessage());

            }


        }
    }
}
