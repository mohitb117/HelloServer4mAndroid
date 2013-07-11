package pnnl.prototype.HelloServer4mAndroid;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gcm.GCMBaseIntentService;

/**
 * Created by bhal810 on 7/10/13.
 */
public class GCMIntentService extends GCMBaseIntentService
{

    private static final String PROJECT_ID="1065705425945";
    private static final String TAG="PNNL-Android-Team";

    public GCMIntentService()
    {
        super(PROJECT_ID);
        Log.d(TAG,"GCM-INTENT-SERVICE init");
    }

    @Override
    protected void onMessage(Context context, Intent intent)
    {
            String message=intent.getStringExtra("messageServer");
            sendGCMIntent(context,message);
            Log.d(TAG,"Message Received "+message);
    }

    @Override
    protected void onError(Context context, String s)
    {
        Log.d(TAG,"ERROR OCCURRED=>" +s);
    }

    private void sendGCMIntent(Context ctx, String message)
    {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("GCM_RECEIVED_ACTION");
        broadcastIntent.putExtra("gcm", message);
        ctx.sendBroadcast(broadcastIntent);

    }


    @Override
    protected void onRegistered(Context ctx, String regId)
    {
        // TODO Auto-generated method stub
        // send regId to your server

        //Async Communication to the server==> Send the RegID to the Server!!!!!!!!!!!!!!

        Toast.makeText(ctx,regId,Toast.LENGTH_LONG).show();

        Log.d(TAG, regId);

    }

    @Override
    protected void onUnregistered(Context ctx, String regId)
    {
        // TODO Auto-generated method stub
        // send notification to your server to remove that regId

    }


}
