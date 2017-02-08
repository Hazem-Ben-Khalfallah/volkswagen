package com.blacknebula.volkswagen;

import java.util.Map;

/**
 * @author hazem
 */
class SystemUtils {
    private final static String BUILD_ID = "BUILD_ID";
    private final static String CI = "CI";
    private final static String CONTINUOUS_INTEGRATION = "CONTINUOUS_INTEGRATION";

    static boolean isCi() {
        final Map<String, String> envParams = System.getenv();
        return envParams.containsKey(BUILD_ID) || envParams.containsKey(CI) || envParams.containsKey(CONTINUOUS_INTEGRATION);
    }
}
