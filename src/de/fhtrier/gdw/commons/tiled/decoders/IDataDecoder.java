package de.fhtrier.gdw.commons.tiled.decoders;

import java.io.Closeable;

/**
 * Interface to read tile id's from the layer data.
 * 
 * @author Santo Pfingsten
 */
public interface IDataDecoder extends Closeable {

	int getNextId() throws Exception;
}
