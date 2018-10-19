package br.com.bossini.weatherforecastbycityfatecipitarde;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class WeatherAdapter extends ArrayAdapter <Weather>{

    public WeatherAdapter (Context context, List <Weather> previsoes){
        super (context, -1, previsoes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Context context = getContext();
        Weather caraDaVez = getItem(position);
        LayoutInflater inflater =
                LayoutInflater.from(context);
        View raiz = inflater.
                inflate(R.layout.list_item, parent, false);
        TextView dayTextView = raiz.findViewById(R.id.dayTextView);
        TextView lowTextView = raiz.findViewById(R.id.lowTextView);
        TextView highTextView = raiz.findViewById(R.id.highTextView);
        TextView humidityTextView = raiz.findViewById(R.id.humidityTextView);
        dayTextView.setText(context.getString(R.string.day_description, caraDaVez.dayOfWeek, caraDaVez.description));
        lowTextView.setText(context.getString(R.string.low_temp, caraDaVez.minTemp));
        highTextView.setText(context.getString(R.string.high_temp, caraDaVez.maxTemp));
        humidityTextView.setText(context.getString(R.string.humidity, caraDaVez.humidity));
        return raiz;
    }
}
