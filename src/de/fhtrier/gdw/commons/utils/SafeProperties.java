package de.fhtrier.gdw.commons.utils;

import java.util.Properties;

/**
 * Extending Properties to get some additional types without the need to
 * manually check for parse exceptions
 * 
 * @author Santo Pfingsten
 */
public class SafeProperties extends Properties {

    /**
     * Creates an empty property list with no default values.
     */
    public SafeProperties() {
    }

    /**
     * Creates an empty property list with the specified defaults.
     * 
     * @param defaults
     *            the defaults.
     */
    public SafeProperties(Properties defaults) {
        super(defaults);
    }

    /**
     * Set the default properties
     * 
     * @param defaults
     *            the defaults.
     */
    public void setDefaults(Properties defaults) {
        this.defaults = defaults;
    }

    /**
     * Get a property as an integer. If the property does not exist or can't be
     * parsed to an integer the default value will be returned. If it is no
     * integer, the NumberFormatException will be printed to stdout.
     * 
     * @param key
     *            the hashtable key.
     * @param defaultValue
     *            a default value.
     * @return the property value or defaultValue
     */
    public int getInt(String key, int defaultValue) {
        String value = getProperty(key, null);
        if (value == null) {
            return defaultValue;
        }

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            System.out.println(key + " is not an integer: " + e.toString());
            return defaultValue;
        }
    }

    /**
     * Get a property as a float. If the property does not exist or can't be
     * parsed to a float the default value will be returned. If it is no float,
     * the NumberFormatException will be printed to stdout.
     * 
     * @param key
     *            the hashtable key.
     * @param defaultValue
     *            a default value.
     * @return the property value or defaultValue
     */
    public float getFloat(String key, float defaultValue) {
        String value = getProperty(key, null);
        if (value == null) {
            return defaultValue;
        }

        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            System.out.println(key + " is not a float: " + e.toString());
            return defaultValue;
        }
    }

    /**
     * Get a property as a double. If the property does not exist or can't be
     * parsed to a double the default value will be returned. If it is no
     * double, the NumberFormatException will be printed to stdout.
     * 
     * @param key
     *            the hashtable key.
     * @param defaultValue
     *            a default value.
     * @return the property value or defaultValue
     */
    public double getDouble(String key, double defaultValue) {
        String value = getProperty(key, null);
        if (value == null) {
            return defaultValue;
        }

        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            System.out.println(key + " is not a double: " + e.toString());
            return defaultValue;
        }
    }

    /**
     * Get a property as a boolean. If the property does not exist the default
     * value will be returned. The value will be matched against "true" or "1".
     * 
     * @param key
     *            the hashtable key.
     * @param defaultValue
     *            a default value.
     * @return the property value or defaultValue
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        String value = getProperty(key, null);
        if (value == null) {
            return defaultValue;
        }
        return value.equals("1") || value.equals("true");
    }
}
