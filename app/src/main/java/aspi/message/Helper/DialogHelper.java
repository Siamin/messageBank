package aspi.message.Helper;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import aspi.message.R;
import aspi.message.Tools.Tools;

public class DialogHelper {

    public static void UpdateApplication(final Context context,String VersionCode){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.sends);

        builder.setTitle("بروزرسانی").setMessage("آیا میخواهید نسخه " + VersionCode + " بانک پیامک را دریافت کنید");
        builder.setPositiveButton("بله", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Tools.Bazar(context);
            }
        });
        builder.setNegativeButton("خیر", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        AlertDialog aler = builder.create();
        aler.show();
    }

}
