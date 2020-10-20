package aspi.message;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.farsitel.bazaar.IUpdateCheckService;

import java.util.ArrayList;
import java.util.List;

import aspi.message.Adapter.CategoryAdapter;
import aspi.message.Adapter.MessageAdapter;
import aspi.message.Adapter.WishListAdapter;
import aspi.message.Helper.DatabaseHelper;
import aspi.message.Helper.DialogHelper;
import aspi.message.Helper.IndicatorHelper;
import aspi.message.Helper.MessageHelper;
import aspi.message.Model.CategoryModel;
import aspi.message.Model.MessageModel;
import aspi.message.Tools.Tools;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    static RecyclerView recyclerView;
    public static String potion = "null";
    static List<CategoryModel> ListContent = new ArrayList<>();
    static List<MessageModel> showContent = new ArrayList<>();
    static DatabaseHelper Data;
    IUpdateCheckService service;
    UpdateServiceConnection connection;
    static final String TAG = "TAG_MainActivity";
    public static int chke = 0, po = 0;
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    static LinearLayoutManager LLM;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //******************************************************************************************
        if (potion == "null") {
            getCategory(MainActivity.this);
        } else {
            getMessageByCategoryId();
        }
        //******************************************************************************************
        try {
            if (chke == 0) initService();
        } catch (Exception e) {

        }
    }

    //********************************************************************* Updata App
    class UpdateServiceConnection implements ServiceConnection {
        public void onServiceConnected(ComponentName name, IBinder boundService) {
            service = IUpdateCheckService.Stub
                    .asInterface((IBinder) boundService);
            try {
                long vCode = service.getVersionCode("aspi.message");
                if (vCode != -1) {

                    DialogHelper.UpdateApplication(MainActivity.this, String.valueOf(vCode));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void onServiceDisconnected(ComponentName name) {
            service = null;
        }
    }

    void initService() {
        chke = 1;
        connection = new UpdateServiceConnection();
        Intent i = new Intent("com.farsitel.bazaar.service.UpdateCheckService.BIND");
        i.setPackage("com.farsitel.bazaar");
        boolean ret = bindService(i, connection, Context.BIND_AUTO_CREATE);
    }

    void releaseService() {
        unbindService(connection);
        connection = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseService();
    }
    //**************************************************************************

    void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerviw);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        LLM = new LinearLayoutManager(MainActivity.this);

        Data = new DatabaseHelper(this);
        Data.useable();

    }

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.love) {
            po = 1;
            getWishList(MainActivity.this, false);
        } else if (id == R.id.list) {
            po = 0;
            getCategory(MainActivity.this);
        } else if (id == R.id.share) {
            Tools.Share(MainActivity.this);
        } else if (id == R.id.bazar) {
            Tools.Bazar(MainActivity.this);
        } else if (id == R.id.abut) {
            Tools.Abute(MainActivity.this);
        } else if (id == R.id.developer) {
            Tools.Developer_app(MainActivity.this);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    static void getCategory(Context context) {
        IndicatorHelper.CreatIndicator(context, "لطفا صبر کنید ...!");

        Data.open();
        ListContent.clear();
        ListContent = Data.GetCategory();
        Data.close();

        recyclerView.setLayoutManager(LLM);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new CategoryAdapter(ListContent, context));

        IndicatorHelper.DismissIndicator();

    }

    void getMessageByCategoryId() {
        IndicatorHelper.CreatIndicator(MainActivity.this, "لطفا صبر کنید ...!");
        try {
            Data.open();
            showContent.clear();
            showContent = Data.GetMessageById(potion);
            Data.close();

            if (showContent.size() > 0) {
                recyclerView.setLayoutManager(LLM);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(new MessageAdapter(showContent, MainActivity.this));
            } else {
                getCategory(MainActivity.this);
            }

        } catch (final Exception e) {
            runOnUiThread(new Runnable() {
                public void run() {
                    Log.i(TAG, e.toString());
                }
            });
        }
        IndicatorHelper.DismissIndicator();

    }

    public static void getWishList(Context context, boolean status) {
        IndicatorHelper.CreatIndicator(context, "لطفا صبر کنید ...!");

        try {
            Data.open();
            showContent.clear();
            showContent = Data.GetWishList();
            Data.close();

            if (showContent.size() > 0) {

                recyclerView.setLayoutManager(LLM);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(new WishListAdapter(showContent, context));
            } else {
                IndicatorHelper.DismissIndicator();
                MessageHelper.TOAST(context, "لیست علاقه مندی شما خالی است ...!");
                if (status) getCategory(context);
            }

        } catch (final Exception e) {

        }
        IndicatorHelper.DismissIndicator();


    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (po == 1) {
                po = 0;
                getCategory(MainActivity.this);
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}
