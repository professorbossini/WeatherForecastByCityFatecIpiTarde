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
import android.widget.ListView;
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
import java.security.interfaces.RSAKey;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText locationEditText;
    private List<Weather> previsoes;
    private WeatherAdapter adapter;
    private ListView weatherListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        previsoes = new ArrayList<>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        locationEditText = findViewById(R.id.locationEditText);
        adapter = new WeatherAdapter(this, previsoes);
        weatherListView = findViewById(R.id.weatherListView);
        weatherListView.setAdapter(adapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cidade =
                        locationEditText.getEditableText().toString();
                String endereco =
                        getString(R.string.web_service_url,
                                cidade, getString(R.string.api_key),
                                getString(R.string.units),
                                getString(R.string.lang));
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
                previsoes.clear();
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
                   Weather w = new Weather (dt, temp_min, temp_max, humidity,
                                                            description, icon);
                   previsoes.add(w);
                }
                adapter.notifyDataSetChanged();

            }
            catch (JSONException e){
               e.printStackTrace();
            }


        }
    }
}
