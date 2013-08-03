package de.fhtrier.gdw.ss2013.settings;

public class DebugModeStatus {
    static protected boolean testModeStatus = false;

    static protected boolean physicTestStatus = false;
    
    static protected boolean winMenuStatus = false;


    static String levelName = "run_or_die";


    static public boolean isTest() {
        return testModeStatus;
    }

    static public void setStatus(boolean status) {
        testModeStatus = status;
    }

    static public void setPhysicTest(boolean status) {
        physicTestStatus = status;
    }
    
    static public boolean isPhysicTest() {
        return physicTestStatus;
    }

    
	public static void setLevel(String levelname) {
		levelName = levelname;
	}

	public static String getLevelName() {
		return levelName;
	}

	public static void setWinMenuStatus(boolean s) {
		winMenuStatus = s;
	}
	
	public static boolean isWinMenuStatus() {
		return winMenuStatus;
	}
}
