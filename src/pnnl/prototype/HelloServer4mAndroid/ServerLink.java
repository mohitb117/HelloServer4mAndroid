package pnnl.prototype.HelloServer4mAndroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


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

    public void pingServer(View v)
    {
        try
        {

            Gson g1=new GsonBuilder().setPrettyPrinting().create();

            RecallPreferences r1=new RecallPreferences();
            r1.setLocationName("London,UK");

            r1.setD1(RecallPreferences.DietTypes.LACTOSE);

            RequestParams params=new RequestParams();

            params.put("Object1",g1.toJson(r1));

            String url=getResources().getString(R.string.serverAddress)+getResources().getString(R.string.resourceName);


            CommChannelOutput.setBASE_URL(getResources().getString(R.string.serverAddress));

            Toast.makeText(getApplicationContext(),url,Toast.LENGTH_LONG).show();

            CommChannelOutput.get(getResources().getString(R.string.resourceName),
                    params,
                    new AsyncHttpResponseHandler()
                    {
                        @Override
                        public void onSuccess(String s)
                        {
                            Toast.makeText(getApplicationContext(),"Message Successfully Received!=> "+s ,Toast.LENGTH_LONG).show();
                        }

                    }
                );


             Toast.makeText(getApplicationContext(),"Message Sent!=> "+g1.toJson(r1) ,Toast.LENGTH_LONG).show();


        }

        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"Unable to send message !=> "+ e ,Toast.LENGTH_LONG).show();
        }


    }

}


//        class TwitterRestClientUsage
//        {
//            public void getPublicTimeline() throws JSONException
//            {
//                TwitterRestClient.get("statuses/public_timeline.json", null, new JsonHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(JSONArray timeline) {
//                        // Pull out the first event on the public timeline
//                        JSONObject firstEvent = timeline.get(0);
//                        String tweetText = firstEvent.getString("text");
//
//                        // Do something with the response
//                        System.out.println(tweetText);
//                    }
//                });
//            }
//        }




//        AsyncHttpClient.getDefaultInstance().getString(getResources().getString(R.string.serverAddress),
//
//        new AsyncHttpClient.StringCallback()
//        {
//            // Callback is invoked with any exceptions/errors, and the result, if available.
//
//            @Override
//            public void onCompleted(Exception e, AsyncHttpResponse response, String result)
//            {
//                if (e != null)
//                {
//                    e.printStackTrace();
//                    return;
//                }
//
//                Toast.makeText(getApplicationContext(),"I got a String==>\n"+response.toString()+"\n"+result ,Toast.LENGTH_LONG).show();
//
//
//            }
//        });
//
//        Toast.makeText(getApplicationContext(),"I got a String==>Check ",Toast.LENGTH_LONG).show();
