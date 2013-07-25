package de.fhtrier.gdw.commons.tiled.decoders;

import java.io.IOException;

/**
 * Read layer data that has been stored in CSV format.
 *
 * @author Santo Pfingsten
 */
public class CsvDataDecoder implements IDataDecoder {

    private final String[] values;
    private int index;

    public CsvDataDecoder(String cdata) throws IOException, Exception {
        values = cdata.split(",");
    }

    @Override
    public int getNextId() throws Exception {
        if (index == values.length) {
            throw new Exception("No more ids in list");
        }
        return Integer.parseInt(values[index++].trim());
    }
}
