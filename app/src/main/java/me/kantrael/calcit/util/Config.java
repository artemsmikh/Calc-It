package me.kantrael.calcit.util;

public class Config {
    public interface WolframAlpha {
        String REQUEST_SCHEME = "https";
        String REQUEST_HOST = "api.wolframalpha.com";
        String REQUEST_PATH_SEGMENTS = "v2/query";

        String REQUEST_PARAM_INPUT = "input";
        String REQUEST_PARAM_APP_ID = "appid";
        String REQUEST_PARAM_WIDTH = "width";
        String REQUEST_PARAM_MAGNIFY = "mag";

        int REQUEST_PARAM_MAGNIFY_VALUE = 2;
        int REQUEST_PARAM_WIDTH_DEFAULT_VALUE = 500;
    }
}
