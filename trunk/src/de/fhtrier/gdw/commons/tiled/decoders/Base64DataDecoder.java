package de.fhtrier.gdw.commons.tiled.decoders;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;
import javax.xml.bind.DatatypeConverter;

/**
 * Read layer data that has been encoded with Base64 and possibly been
 * compressed with gzip or zlib.
 *
 * @author Santo Pfingsten
 */
public class Base64DataDecoder implements IDataDecoder {

    private final InputStream stream;

    public Base64DataDecoder(String cdata, String compression) throws IOException, Exception {
        byte[] dec = DatatypeConverter.parseBase64Binary(cdata.trim());
        ByteArrayInputStream bis = new ByteArrayInputStream(dec);
        if (compression == null || compression.isEmpty()) {
            stream = bis;
        } else if (compression.equals("gzip")) {
            stream = new GZIPInputStream(bis);
        } else if (compression.equals("zlib")) {
            stream = new InflaterInputStream(bis);
        } else {
            throw new IOException("Unsupport compression: " + compression + ". Currently only uncompressed maps and gzip and zlib compressed maps are supported.");
        }
    }

    @Override
    public int getNextId() throws Exception {
        int id = 0;
        id |= stream.read();
        id |= stream.read() << 8;
        id |= stream.read() << 16;
        id |= stream.read() << 24;
        return id;
    }
}
