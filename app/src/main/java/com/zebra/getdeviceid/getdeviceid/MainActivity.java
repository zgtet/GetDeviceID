package com.zebra.getdeviceid.getdeviceid;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {


    @Override
    protected void onResume() {
        super.onResume();
        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

        String android_id = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
        String device_id = tm.getDeviceId();
        String gsf_id = getGsfAndroidId(this);

        TextView tvDeviceID = (TextView) findViewById(R.id.device_id);
        TextView tvAndroidID = (TextView) findViewById(R.id.android_id);
        TextView tvGSFID = (TextView) findViewById(R.id.gsf_id);

        tvDeviceID.setText("Device ID: "+ device_id);
        tvAndroidID.setText("Android ID: " + android_id);
        tvGSFID.setText("GSF ID: " + gsf_id);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    private static String getGsfAndroidId(Context context)
    {
        Uri URI = Uri.parse("content://com.google.android.gsf.gservices");
        String ID_KEY = "android_id";
        String params[] = {ID_KEY};
        Cursor c = context.getContentResolver().query(URI, null, null, params, null);
        if (!c.moveToFirst() || c.getColumnCount() < 2)
            return null;
        try
        {
            return Long.toHexString(Long.parseLong(c.getString(1)));
        }
        catch (NumberFormatException e)
        {
            return null;
        }
    }
}
