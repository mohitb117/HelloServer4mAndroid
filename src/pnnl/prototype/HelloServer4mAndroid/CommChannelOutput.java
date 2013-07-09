package pnnl.prototype.HelloServer4mAndroid;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.security.KeyStore;

/**
 * Created by bhal810 on 7/8/13.
 */

public class CommChannelOutput
{
    private static String BASE_URL;
    private static AsyncHttpClient client = new AsyncHttpClient();


    public static String getBASE_URL()
    {
        return BASE_URL;
    }

    public static void setBASE_URL(String BASE_URL)
    {
        CommChannelOutput.BASE_URL = BASE_URL;
    }


    public static void genericFunc()
    {
        try
        {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
            sf.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            client.setSSLSocketFactory(sf);
        }

        catch (Exception e)
        {

        }
    }
    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler)
    {
        CommChannelOutput.genericFunc();
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler)
    {
        CommChannelOutput.genericFunc();
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl)
    {
        return BASE_URL + relativeUrl;
    }
}