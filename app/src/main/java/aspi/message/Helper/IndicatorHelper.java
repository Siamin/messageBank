package aspi.message.Helper;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import aspi.message.R;

public class IndicatorHelper {

    public static Dialog dialog;

    public static void CreatIndicator(Context context,String Message){
        dialog = new Dialog(context, R.style.NewDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_indicator);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }

    public static void DismissIndicator(){
        dialog.dismiss();
    }
}
