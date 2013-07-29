package de.fhtrier.gdw.commons.tiled.decoders;

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBElement;

import de.fhtrier.gdw.commons.tiled.tmx.TmxTile;

/**
 * Read layer data that has been stored in XML format.
 * 
 * @author Santo Pfingsten
 */
public class XmlDataDecoder implements IDataDecoder {

    private final Iterator<Serializable> iterator;

    public XmlDataDecoder(List<Serializable> content) throws IOException,
            Exception {
        iterator = content.iterator();
    }

    @Override
    public int getNextId() throws Exception {
        while (iterator.hasNext()) {
            Serializable element = iterator.next();
            if (element instanceof JAXBElement) {
                Object value = ((JAXBElement) element).getValue();
                if (value instanceof TmxTile) {
                    return ((TmxTile) value).getGid();
                }
            }
        }
        throw new Exception("No more ids in list");
    }

    @Override
    public void close() throws IOException {
    }
}
