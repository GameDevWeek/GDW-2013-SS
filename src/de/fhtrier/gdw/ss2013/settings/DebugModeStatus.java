package de.fhtrier.gdw.ss2013.settings;

public class DebugModeStatus {
    static protected boolean testModeStatus = true;

    static public boolean getStatus() {
        return testModeStatus;
    }

    static public void setStatus(boolean status) {
        testModeStatus = status;
    }

}
