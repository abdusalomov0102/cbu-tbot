package uz.task.util;

import com.google.gson.Gson;
import uz.task.model.Currencies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class CurrencyUtil {

    public static String getCurrency(String Ccy) {
        try {
            URL url = new URL("http://cbu.uz/ru/arkhiv-kursov-valyut/json/");
            URLConnection connection = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            Gson gson = new Gson();
            Currencies[] currencies = gson.fromJson(reader, Currencies[].class);
            for (Currencies currency : currencies) {
                if (currency.getCcy().equals(Ccy.toUpperCase())) {
                    return currency.getNominal() + "  " + currency.getCcyNmUZ() + "  ➡  " + currency.getRate() + " SUM.";
                }
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
