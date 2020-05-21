package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Product> arrayList;
    ListView lv;
    ProgressBar progressBar;


    String urls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        arrayList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.listview);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadJson().execute("https://guidebook.com/service/v2/upcomingGuides/");
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String[] data = urls.split(",");
                Toast.makeText(getApplicationContext(),"Go to guidebook.com" + data[i] , Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
                intent.putExtra("weburl","https://guidebook.com" + data[i]);
                startActivity(intent);
            }
        });//*/
    }

    class ReadJson extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray = jsonObject.getJSONArray("data");



                for (int i=0; i<jsonArray.length(); i++){
                    JSONObject product = jsonArray.getJSONObject(i);
                    arrayList.add(new Product(product.getString("icon"), product.getString("name"), product.getString("endDate")));
                    urls = urls + product.getString("url") + ",";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            CustomListAdapter adapter = new CustomListAdapter(getApplicationContext(),R.layout.custom_list_layout,arrayList);
            lv.setAdapter(adapter);

            try {
                Thread.sleep(50);
                progressBar.setVisibility(View.GONE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static String readURL(String theUrl){
        StringBuilder content = new StringBuilder();


        try {
            URL url = new URL(theUrl);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null){
                content.append(line + "\n");
            }
            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
