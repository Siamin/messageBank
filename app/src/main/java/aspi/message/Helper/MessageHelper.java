package aspi.message.Helper;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import aspi.message.R;

public class MessageHelper {


    public static void TOAST(Context context, String TEXT) {
        Toast toast = Toast.makeText(context, "" + TEXT, Toast.LENGTH_LONG);
        TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
        textView.setTextColor(context.getResources().getColor(R.color.toast_text));
        textView.setTextSize(14);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.toast);
        toast.show();
    }

}
