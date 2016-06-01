package me.kantrael.calcit.util;

import me.kantrael.calcit.BuildConfig;
import okhttp3.HttpUrl;

public class UrlUtils {
    public static HttpUrl buildWolframSearchUrl(String query) {
        return buildWolframSearchUrl(query, 0);
    }

    public static HttpUrl buildWolframSearchUrl(String query, int resultWidth) {
        HttpUrl.Builder builder = new HttpUrl.Builder()
                .scheme(Config.WolframAlpha.REQUEST_SCHEME)
                .host(Config.WolframAlpha.REQUEST_HOST)
                .addPathSegments(Config.WolframAlpha.REQUEST_PATH_SEGMENTS);

        // Wolfram API application id
        builder.addQueryParameter(
                Config.WolframAlpha.REQUEST_PARAM_APP_ID,
                BuildConfig.WOLFRAM_ALPHA_APP_ID
        );

        // Search query
        builder.addQueryParameter(Config.WolframAlpha.REQUEST_PARAM_INPUT, query);

        // Result images maximum width
        if (resultWidth <= 0) {
            resultWidth = Config.WolframAlpha.REQUEST_PARAM_WIDTH_DEFAULT_VALUE;
        }

        builder.addQueryParameter(
                Config.WolframAlpha.REQUEST_PARAM_WIDTH,
                Integer.toString(resultWidth)
        );

        // Result images scale
        builder.addQueryParameter(
                Config.WolframAlpha.REQUEST_PARAM_MAGNIFY,
                Integer.toString(Config.WolframAlpha.REQUEST_PARAM_MAGNIFY_VALUE)
        );

        return builder.build();
    }
}
