package de.fhtrier.gdw.commons.interfaces;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 *
 * @author Santo Pfingsten
 */
public interface IResourceLocator {

    InputStream locateResource(String filename) throws FileNotFoundException;

    String combinePaths(String parent, String filename);
}
