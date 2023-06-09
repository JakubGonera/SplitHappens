package com.ogr.splithappens.view;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import javafx.util.StringConverter;

public class Common {
    public static final class SimpleStringConverter extends StringConverter<Number>{
        @Override
        public String toString(Number object) {
            if(object == null)
                return "0";
            DecimalFormatSymbols decimalSymbols = DecimalFormatSymbols.getInstance();
            return new DecimalFormat("0.00", decimalSymbols).format(object);
        }

        @Override
        public Number fromString(String string) {
            if(string.contains(",")){
                string = string.replace(",", ".");
            }
            return Float.parseFloat(string);
        }
    }
    public static String formatAmountWithCurrency(int amount){
        return formatAmount(amount) + " zł";
    }

    public static String formatAmount(int amount){
        String amountString = String.valueOf(amount);
        if(amountString.length() > 2){
            DecimalFormatSymbols decimalSymbols = DecimalFormatSymbols.getInstance();
            char separator = decimalSymbols.getDecimalSeparator();
            amountString = amountString.substring(0, amountString.length() - 2) + separator + amountString.substring(amountString.length() - 2);
        }
        return amountString;
    }

    public static int parseAmount(String amountString){
        DecimalFormatSymbols decimalSymbols = DecimalFormatSymbols.getInstance();
        char separator = decimalSymbols.getDecimalSeparator();
        int digitsAfterSeparator = 0;
        if(amountString.contains(separator+"")){
            // Find how many digits are after separator
            digitsAfterSeparator = amountString.length() - amountString.indexOf(separator) - 1;
            amountString = amountString.replace(separator+"", "");
        }
        int amount = Integer.parseInt(amountString);
        for(int i = digitsAfterSeparator; i < 2; i++){
            amount *= 10;
        }
        return amount;
    }
}
