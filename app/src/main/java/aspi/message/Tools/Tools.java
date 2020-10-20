package aspi.message.Tools;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

import aspi.message.Helper.MessageHelper;
import aspi.message.R;

public class Tools {

    public static void Bazar(Context context) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("bazaar://details?id=aspi.message"));
            intent.setPackage("com.farsitel.bazaar");
            ((Activity)context).startActivity(intent);
        } catch (Exception e) {
            MessageHelper.TOAST(context, "لطفا برنامه بازار را نصب کنید...!");
        }
    }

    public static void Share(Context context) {
        try {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "صندق پیامک");
            sendIntent.setType("text/plain");
            ((Activity)context).startActivity(sendIntent);
        } catch (Exception e) {
        }

    }

    public static void Abute(final Context context) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String version = pInfo.versionName;
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setIcon(R.drawable.sends);

            builder.setTitle("درباره ما").setMessage("این برنامه توسط گروه نرم افزاری ASPI طراحی شده برای حمایت از ما به نرم افزار در کافه بازار رای بدهید"
                    + "\n" + "منابع :" + "\n" +
                    "۱-www.4jok.com\n" +
                    "2-www.beytoote.com\n" +
                    "3-www.bisms.ir" + "\n" + "\n" + "نرم افزار بانک پیامک نسخه ی " + version);
            builder.setPositiveButton("امتیاز دادن به برنامه", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    Bazar(context);
                }
            });
            AlertDialog aler = builder.create();
            aler.show();
        } catch (Exception e) {
        }
    }

    public static void Developer_app(Context context) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("bazaar://collection?slug=by_author&aid=aminsyahi1369"));
            intent.setPackage("com.farsitel.bazaar");
            ((Activity)context).startActivity(intent);
        } catch (Exception e) {
            MessageHelper.TOAST(context, "لطفا برنامه بازار را نصب کنید...!");
        }

    }
}
