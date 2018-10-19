package br.com.bossini.weatherforecastbycityfatecipitarde;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText locationEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        locationEditText = findViewById(R.id.locationEditText);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cidade =
                        locationEditText.getEditableText().toString();
                String endereco =
                        getString(R.string.web_service_url);
                endereco = endereco + cidade;
                endereco += "&APPID=";
                endereco += getString(R.string.api_key);
                endereco += "&units=metric";
                new ObtemTemperaturas().execute(endereco);
            }
        });
    }

    private class ObtemTemperaturas extends
            AsyncTask <String, Void, String>{

        @Override
        protected String doInBackground(String... enderecos) {
            try{
                URL url = new URL (enderecos[0]);
                HttpURLConnection connection =
                        (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(inputStream));
                String linha = null;
                String json = "";
                while ((linha = reader.readLine()) != null){
                    json += linha;
                }
                return json;
            }
            catch (MalformedURLException e){
                e.printStackTrace();

            }
            catch (IOException e){
               e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String jsonS) {
            try{
                JSONObject json = new JSONObject(jsonS);
                JSONArray list = json.getJSONArray("list");
                for (int i = 0; i < list.length(); i++){
                   JSONObject previsao = list.getJSONObject(i);
                   long dt = previsao.getLong("dt");
                   JSONObject main = previsao.getJSONObject("main");
                   double temp_min = main.getDouble("temp_min");
                   double temp_max = main.getDouble("temp_max");
                   int humidity = main.getInt("humidity");
                   JSONArray weather = previsao.getJSONArray("weather");
                   JSONObject unicoNoWeather = weather.getJSONObject(0);
                   String description = unicoNoWeather.getString("description");
                   String icon = unicoNoWeather.getString("icon");
                }
            }
            catch (JSONException e){
               e.printStackTrace();
            }


        }
    }
}
