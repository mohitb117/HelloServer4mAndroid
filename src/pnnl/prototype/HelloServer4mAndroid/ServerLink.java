package pnnl.prototype.HelloServer4mAndroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


public class ServerLink extends Activity
{
    /**
     * Called when the activity is first created.
     */


    public static final String Project_ID="1065705425945";

    public static final String TAG="Server-Link-Activity";

    private static String regID="";

    private String registrationStatus ="Not Yet Registered";

    private String broadcastMessage="No Broadcast Message";

    IntentFilter gcmFilter;



    private BroadcastReceiver gcmReceiver=new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
                broadcastMessage=intent.getExtras().getString("gcm");

                if(broadcastMessage!=null)
                {

                // createAlert(broadcastMessage);

                createNotifications(broadcastMessage,"Broadcast Message from Server");

                }
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

            gcmFilter=new IntentFilter();
            gcmFilter.addAction("GCM_RECEIVED_ACTION");

          registerClient();

    }



    private void createAlert(String message)
    {
        try
        {

            AlertDialog.Builder alertbox = new AlertDialog.Builder(this);

            alertbox.setMessage(message);

            alertbox.setTitle("Registration ID");

            alertbox.show();


        }

        catch (Exception e)
        {
            Log.d(TAG,e.getMessage());
        }
    }


    public void createNotifications(String  message,String source)
    {
         NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(this)
                                .setSmallIcon(R.drawable.ic_launcher)
                                .setContentTitle(source)
                                .setContentText(message);

        // Creates an explicit intent for an Activity in your app
                Intent resultIntent = new Intent(this, ServerLink.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
                stackBuilder.addParentStack(ServerLink.class);
        // Adds the Intent that starts the Activity to the top of the stack
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(
                                0,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );
                mBuilder.setContentIntent(resultPendingIntent);

                NotificationManager mNotificationManager =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // mId allows you to update the notification later on.

                mNotificationManager.notify(0, mBuilder.build());
    }
    private void registerClient()
    {

            try
            {
                GCMRegistrar.checkDevice(this);
                GCMRegistrar.checkManifest(this);

                regID=GCMRegistrar.getRegistrationId(this);

                if(regID.equals(""))
                {
                    registrationStatus="Registering.....";

                    GCMRegistrar.register(this,Project_ID);


                    regID=GCMRegistrar.getRegistrationId(this);

                    registrationStatus="Registration Acquired!";

                    Log.d(TAG,registrationStatus+" "+regID);

                    createAlert(registrationStatus+" "+regID);
                    //Send RegID to the Server
                }

                else
                {
                    registrationStatus="Already Registered!";

                    Log.d(TAG,registrationStatus+" "+regID);

                    createAlert(registrationStatus + "\n" + regID);
                }

            }

            catch (Exception e)
            {
                e.printStackTrace();

                registrationStatus =e.getMessage();
            }

            Log.d(TAG,registrationStatus);


    }



    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {

        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString("BroadcastMessage", broadcastMessage);

    }

    // When an activity is re-created, the os generates an onRestoreInstanceState()
    // event, passing it a bundle that contains any values that you may have put
    // in during onSaveInstanceState()
    // We can use this mechanism to re-display our last broadcast message.

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState)
    {

        super.onRestoreInstanceState(savedInstanceState);

        broadcastMessage = savedInstanceState.getString("BroadcastMessage");

        createAlert(broadcastMessage);
    }

    // If our activity is paused, it is important to UN-register any
    // broadcast receivers.
    @Override
    protected void onPause()
    {
        super.onPause();
        unregisterReceiver(gcmReceiver);
    }

    // When an activity is resumed, be sure to register any
    // broadcast receivers with the appropriate intent

    @Override
    protected void onResume()
    {
        super.onResume();
        registerReceiver(gcmReceiver, gcmFilter);

    }

    // NOTE the call to GCMRegistrar.onDestroy()
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        GCMRegistrar.onDestroy(this);
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
                            Toast.makeText(getApplicationContext(),"Message Successfully Received!=> \n"+s ,Toast.LENGTH_LONG).show();
                        }

                    }
                );


             Toast.makeText(getApplicationContext(),"Message Sent!=> \n"+g1.toJson(r1) ,Toast.LENGTH_LONG).show();


        }

        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"Unable to send message !=> "+ e ,Toast.LENGTH_LONG).show();
        }


    }

}
