package de.fhtrier.gdw.ss2013.settings;

public class DebugModeStatus {
    static protected boolean testModeStatus = true;

    static protected boolean physicTestStatus = true;

    static public boolean getStatus() {
        return testModeStatus;
    }

    static public void setStatus(boolean status) {
        testModeStatus = status;
    }

    static public void setPhysicTest(boolean status) {
        physicTestStatus = status;
    }

    static public boolean getPhysicTest() {
        return physicTestStatus;
    }

}
