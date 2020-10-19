package com.sneydr.roomrv2.App.Validation;

public class InputSanitizer {



    private static InputSanitizer instance = null;

    public static InputSanitizer getInstance() {
        if (instance == null) {
            return new InputSanitizer();
        }
        return instance;
    }

    public String sanitizeString(String s) {
        if (!s.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            s = s.trim();
            for (String line : s.split(" ")) {
                builder.append(capitalize(line));
                builder.append(" ");
            }
            return builder.toString().trim();
        }
        return s;
    }

    public int sanitizeInt(String number) {
        if (!number.equals("0")){
            return tryParse(number) ? Integer.parseInt(number.replaceFirst ("^0", "")) : 0;
        }
        return 0;
    }

    private String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    private boolean tryParse(String i) {
        try {
            Integer.parseInt(i);
            return true;
        }
        catch (NumberFormatException ex) {
            return false;
        }
    }


}
