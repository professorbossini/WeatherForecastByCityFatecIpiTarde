package br.com.bossini.weatherforecastbycityfatecipitarde;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class Weather {
    public final String dayOfWeek;
    public final String minTemp;
    public final String maxTemp;
    public final String humidity;
    public final String description;
    public final String iconName;

    public Weather (long timeStamp, double minTemp,
                        double maxTemp, double humidity,
                            String description, String iconName){
        dayOfWeek = converterNumeroParaDiaDaSemana(timeStamp);
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(0);
        this.minTemp = numberFormat.format(minTemp);
        this.maxTemp = numberFormat.format(maxTemp);
        NumberFormat percentFormat =
                NumberFormat.getPercentInstance();
        this.humidity = percentFormat.format(humidity);
        this.description = description;
        this.iconName = iconName;
    }

    private static String converterNumeroParaDiaDaSemana (long timeStamp){
        Calendar agora = Calendar.getInstance();
        agora.setTimeInMillis(timeStamp * 1000);
        TimeZone timeZone = TimeZone.getDefault();
        agora.add(Calendar.MILLISECOND,
                timeZone.getOffset(agora.getTimeInMillis()));
        //SimpleDateFormat simpleDateFormat =
                return new SimpleDateFormat("EEEE", Locale.getDefault())
                        .format(agora.getTime());
        //return simpleDateFormat.format(agora.getTime());
    }
}
