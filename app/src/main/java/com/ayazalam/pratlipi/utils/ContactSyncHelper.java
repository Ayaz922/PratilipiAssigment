package com.ayazalam.pratlipi.utils;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import com.ayazalam.pratlipi.bean.Contact;
import com.ayazalam.pratlipi.support.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Ayaz Alam on 2019/5/03.
 */


public class ContactSyncHelper extends AsyncTask {

    private List<String> list;
    private Context context;
    private ContactSyncCallback callback;
    private Map<String, String> mIdMap;

    public ContactSyncHelper(Context context,ContactSyncCallback contactSyncCallback) {
        this.context = context;
        this.callback =contactSyncCallback;
    }

    private void CheckContactDataBase(){
        list =new ArrayList<>();
        mIdMap =new HashMap<>();
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver()
                    .query(ContactsContract.Data.CONTENT_URI, new String[] {
                                    Constants.Columns.RAW_CONTACT_ID,
                                    Constants.Columns.DATA1,
                                    Constants.Columns.DATA2 },
                            Constants.Columns.MIMETYPE + "='" +
                                    Constants.Columns.MIME_PHONE +
                                    "'", null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String id = cursor.getString(cursor.getColumnIndex(
                            Constants.Columns.RAW_CONTACT_ID));
                    Contact contact = new Contact(id);
                    contact.setPhoneNumber(cursor.getString(
                            cursor.getColumnIndex(
                                    Constants.Columns.DATA1)));
                    if (!list.contains(mIdMap.get(id))&&contact.getPhoneNumber()!=null) {
                        mIdMap.put(id, id);
                        list.add(id);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    @Override
    protected void onPostExecute(Object o) {
        if(list!=null)
        callback.contactUpdated(list.size());
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        CheckContactDataBase();
        return null;
    }
}
