package de.fhtrier.gdw.commons.tiled.decoders;

/**
 * Interface to read tile id's from the layer data.
 *
 * @author Santo Pfingsten
 */
public interface IDataDecoder {

    int getNextId() throws Exception;
}
