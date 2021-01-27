package id.kopas.berkarya.zakatku.utils;

import android.text.TextWatcher;
import android.widget.EditText;

import java.text.NumberFormat;
import java.util.Locale;

public class Fungsi {
    public TextWatcher formatRupiahEditText(EditText nilai){
        Locale locale = new Locale("id", "ID"); // For example Argentina
        int numDecs = 2; // Let's use 2 decimals
        TextWatcher tw = new NumberTextWatcher(nilai, locale, numDecs);
        return tw;
    }

    public String formatRupiah(int text){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        return String.valueOf( formatRupiah.format(text) );
    }

    public String formatInt(String ss){
        ss = ss.replace(".", "");
        //ss = ss.replace(",", "");
        return ss;
    }
}
