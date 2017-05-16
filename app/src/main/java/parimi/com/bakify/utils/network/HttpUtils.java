package parimi.com.bakify.utils.network;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import static parimi.com.bakify.utils.Constants.BASE_URL;

/**
 * This is the wrapper class for handling network calls.
 */

public class HttpUtils {
    private static AsyncHttpClient client = new AsyncHttpClient();

    // this method is used to get recipes from the url provided.
    public static void getReceipes(String relativeUrl, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(relativeUrl), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

}