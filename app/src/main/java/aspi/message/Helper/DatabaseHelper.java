package aspi.message.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import aspi.message.Model.CategoryModel;
import aspi.message.Model.MessageModel;

public class DatabaseHelper extends SQLiteOpenHelper {


    public final String path = "data/data/aspi.message/databases/";
    public final String Name = "database.db";
    public SQLiteDatabase mydb;
    final Context mycontext;
    String TAG = "TAG_DatabaseHelper";


    public DatabaseHelper(Context context) {
        super(context, "database.db", null, 1);
        mycontext = context;

    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
    }

    public void useable() {

        boolean checkdb = checkdb();

        if (checkdb) {

        } else {

            this.getReadableDatabase();

            try {
                copydatabase();
            } catch (IOException e) {

            }

        }
    }

    public void open() {
        mydb = SQLiteDatabase.openDatabase(path + Name, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void close() {
        mydb.close();
    }

    public boolean checkdb() {

        SQLiteDatabase db = null;
        try {
            db = SQLiteDatabase.openDatabase(path + Name, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLException e) {

        }
        //mydb.close();
        return db != null ? true : false;

    }

    //***************************************************
    public void copydatabase() throws IOException {
        OutputStream myOutput = new FileOutputStream(path + Name);
        byte[] buffer = new byte[1024];
        int length;
        InputStream myInput = mycontext.getAssets().open(Name);
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myInput.close();
        myOutput.flush();
        myOutput.close();
    }

    //*****************************************************update_number
    public void update(String fild, String text, String tabel, int id) {
        ContentValues cv = new ContentValues();
        cv.put(fild, text);
        mydb.update(tabel, cv, "ID=" + id, null);
    }

    //*****************************************************
    public List<CategoryModel> GetCategory() {
        List<CategoryModel> categoryModels = new ArrayList<>();
        Cursor cursor = mydb.rawQuery("select * from groups", null);

        if (cursor.moveToFirst()) {
            do {

                CategoryModel cl = new CategoryModel();
                cl.id_group = cursor.getString(0);
                cl.name = cursor.getString(1);
                categoryModels.add(cl);

            } while (cursor.moveToNext());

            return categoryModels;
        } else {
            return categoryModels;
        }

    }

    public List<MessageModel> GetMessageById(String CategoryId) {
        List<MessageModel> messageModels = new ArrayList<>();
        Cursor cursor = mydb.rawQuery("select * from mesage where category ='" + CategoryId + "'", null);


        if (cursor.moveToFirst()) {
            do {

                MessageModel cl = new MessageModel();
                cl.text = cursor.getString(2);
                cl.id = cursor.getString(0);
                cl.love = cursor.getString(3);
                messageModels.add(cl);

            } while (cursor.moveToNext());

            return messageModels;
        } else {
            return messageModels;
        }

    }

    public List<MessageModel> GetWishList() {
        List<MessageModel> messageModels = new ArrayList<>();
        Cursor cursor = mydb.rawQuery("select * from mesage where love ='1'", null);


        if (cursor.moveToFirst()) {
            do {

                MessageModel cl = new MessageModel();
                cl.text = cursor.getString(2);
                cl.id = cursor.getString(0);
                cl.love = cursor.getString(3);
                messageModels.add(cl);

            } while (cursor.moveToNext());

            return messageModels;
        } else {
            return messageModels;
        }

    }


}