package pnnl.prototype.HelloServer4mAndroid;

import android.app.Activity;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;
import com.koushikdutta.async.http.*;


public class ServerLink extends Activity
{
    /**
     * Called when the activity is first created.
     */

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void pingMe(View v)
    {
        String url="http://130.20.168.67:8080/mohitb117";

        AsyncHttpClient.getDefaultInstance().getString(url, new AsyncHttpClient.StringCallback()
        {
            // Callback is invoked with any exceptions/errors, and the result, if available.

            @Override
            public void onCompleted(Exception e, AsyncHttpResponse response, String result)
            {
                if (e != null)
                {
                    e.printStackTrace();
                    return;
                }

                Toast.makeText(getApplicationContext(),"I got a String==>\n"+response.toString()+"\n"+result ,Toast.LENGTH_LONG).show();

                //System.out.println("I got a string: " + result);

            }
        });

        Toast.makeText(getApplicationContext(),"I got a String==>Check ",Toast.LENGTH_LONG).show();

    }

}
