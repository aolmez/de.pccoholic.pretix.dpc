package de.pccoholic.pretix.dpc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class BarcodeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        byte[] barocode = intent.getByteArrayExtra("barocode");
        int barocodelen = intent.getIntExtra("length", 0);

        String barcodeStr = new String(barocode, 0, barocodelen);

        try {
            barcodeStr = new String(barocode, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        SharedPreferences prefs = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

        if (barcodeStr.equals(prefs.getString("pref_DPC_unlock_barcode", null))) {
            Intent launchIntent = new Intent(context, DPC.class);
            launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            launchIntent.putExtra("DPC_unlock_barcode", barcodeStr);
            context.startActivity(launchIntent);
        }
    }

}
