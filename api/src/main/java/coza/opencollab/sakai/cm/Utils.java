package coza.opencollab.sakai.cm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Utils {

    /**
     * Util class
     */
    private Utils() {
    }
    
	/**
     * A method that tests for Object equality.
     */
    public static boolean isEquals(Object a, Object b) {
        return a == null ? b == null : a.equals(b);
    }

    public static Date getDateFromString(String dateValue) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            return sdf.parse(dateValue);
        }
        catch (ParseException e) {
        	throw new SISException(e);
        }
    }
}
