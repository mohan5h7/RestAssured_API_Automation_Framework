package base;

import config.ConfigManager;
import context.ApiContext;

public final class BaseTest {

    private BaseTest() {
    }

    public static void initializeFramework() {

        ConfigManager.loadProperties();

    }

    public static void cleanUpFramework() {
    	
    	ApiContext.clear();
       
    }

}