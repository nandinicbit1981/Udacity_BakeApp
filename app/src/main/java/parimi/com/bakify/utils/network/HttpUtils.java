package parimi.com.bakify.utils.network;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import static parimi.com.bakify.utils.Constants.BASE_URL;

/**
 * Created by nandpa on 5/9/17.
 */


public class HttpUtils {
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void getReceipes(String relativeUrl, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(relativeUrl), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

}