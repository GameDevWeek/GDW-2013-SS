package de.fhtrier.gdw.commons.jackson;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Reading Json to objects
 * 
 * @author Santo Pfingsten
 */
public class JacksonReader {

    public static <RT> RT read(String filename, Class<RT> clazz)
            throws IOException, UnsupportedEncodingException,
            NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException, InstantiationException, ParseException {
        JsonFactory factory = new JsonFactory();

        try (FileInputStream fileIn = new FileInputStream(filename);
                InputStreamReader inReader = new InputStreamReader(fileIn);
                BufferedReader bufferedReader = new BufferedReader(inReader);
                JsonParser parser = factory.createParser(bufferedReader);) {
            parser.nextToken();
            return readObject(clazz, parser);
        }
    }

    public static <LT> List<LT> readList(String filename, Class<LT> clazz)
            throws IOException, UnsupportedEncodingException,
            NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException, InstantiationException, ParseException {
        JsonFactory factory = new JsonFactory();

        try (FileInputStream fileIn = new FileInputStream(filename);
                InputStreamReader inReader = new InputStreamReader(fileIn);
                BufferedReader bufferedReader = new BufferedReader(inReader);
                JsonParser parser = factory.createParser(bufferedReader);) {
            parser.nextToken();
            return readList(clazz, parser);
        }
    }

    private static <T> List<T> readList(Class<T> clazz, JsonParser parser)
            throws InstantiationException, IllegalAccessException, IOException,
            NoSuchFieldException, ParseException {
        if (clazz == null || parser.getCurrentToken() != JsonToken.START_ARRAY) {
            throw new AssertionError(parser.getCurrentToken().name());
        }

        List<T> list = new ArrayList<>();
        while (parser.nextToken() != JsonToken.END_ARRAY) {
            list.add(readObject(clazz, parser));
        }
        return list;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static <T> T readObject(Class<T> clazz, JsonParser parser)
            throws InstantiationException, IllegalAccessException, IOException,
            NoSuchFieldException, ParseException {
        T object;
        switch (parser.getCurrentToken()) {
        case START_OBJECT:
            object = clazz.newInstance();

            while (parser.nextToken() != JsonToken.END_OBJECT) {
                String headerField = parser.getCurrentName();
                parser.nextToken();

                Field field = clazz.getDeclaredField(headerField);
                JacksonList listAnnotation = (JacksonList) field
                        .getAnnotation(JacksonList.class);
                if (listAnnotation != null) {
                    field.set(object, readList(listAnnotation.value(), parser));
                } else {
                    field.set(object, readObject(field.getType(), parser));
                }
            }
            return object;
        case VALUE_STRING:
            if (clazz.isEnum()) {
                String name = parser.getText().toUpperCase();
                return (T) Enum.valueOf((Class<Enum>) clazz, name);
            } else if (clazz.equals(String.class)) {
                return (T) parser.getText();
            } else if (clazz.equals(Integer.class)) {
                return (T) Integer.valueOf(parser.getText());
            } else if (clazz.equals(Float.class)) {
                return (T) Float.valueOf(parser.getText());
            } else if (clazz.equals(Boolean.class)) {
                String text = parser.getText();
                return (T) ("1".equals(text) ? Boolean.valueOf(true) : Boolean
                        .valueOf(text));
            }
            throw new AssertionError(parser.getCurrentToken().name());
        case VALUE_NUMBER_INT:
            if (clazz.equals(Integer.class)) {
                return (T) Integer.valueOf(parser.getIntValue());
            }
            if (clazz.equals(Float.class)) {
                return (T) Float.valueOf(parser.getIntValue());
            }
            throw new AssertionError(parser.getCurrentToken().name());
        case VALUE_NUMBER_FLOAT:
            if (clazz.equals(Float.class)) {
                return (T) Float.valueOf(parser.getFloatValue());
            }
            throw new AssertionError(parser.getCurrentToken().name());
        case VALUE_TRUE:
            if (clazz.equals(Boolean.class)) {
                return (T) Boolean.TRUE;
            }
            throw new AssertionError(parser.getCurrentToken().name());
        case VALUE_FALSE:
            if (clazz.equals(Boolean.class)) {
                return (T) Boolean.FALSE;
            }
            throw new AssertionError(parser.getCurrentToken().name());
        case VALUE_NULL:
            return null;
        default:
            throw new AssertionError(parser.getCurrentToken().name());
        }
    }
}
