package de.fhtrier.gdw.ss2013.debug;

public class TestModeStatus {
    static protected boolean testModeStatus;

    static public boolean getStatus() {
        return testModeStatus;
    }

    static public void setStatus(boolean status) {
        testModeStatus = status;
    }
}
