package com.ayazalam.pratlipi.model.impl;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Data;
import android.util.Log;
import com.ayazalam.pratlipi.bean.Contact;
import com.ayazalam.pratlipi.model.IContactModel;
import com.ayazalam.pratlipi.support.Constants;
import com.ayazalam.pratlipi.support.callback.DataCallback;
import com.ayazalam.pratlipi.support.callback.StatusCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ayaz Alam on 2019/5/2.
 */
public class ContactModel extends AsyncTask implements IContactModel {

    private static final Object QUERY_PARAMETER_LIMIT = "limit";
    private Map<String, Contact> mIdMap;
    private DataCallback<List<Contact>> callback;
    private Context context;
    private List<Contact> list;


    public ContactModel(Context context,DataCallback<List<Contact>> callback){
        this.callback=callback;
        this.context=context;
    }


    @Override
    public void getContactList(final Context context, final DataCallback<List<Contact>> callback) {
            list =new ArrayList<>();
            mIdMap = new HashMap<>();
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver()
                                .query(Data.CONTENT_URI, new String[] {
                                                Constants.Columns.RAW_CONTACT_ID,
                                                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                                                Constants.Columns.DATA1,
                                                Constants.Columns.DATA2 },
                                        Constants.Columns.MIMETYPE + "='" +
                                                Constants.Columns.MIME_PHONE +
                                                "'", null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+ " ASC");
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        String id = cursor.getString(cursor.getColumnIndex(
                                Constants.Columns.RAW_CONTACT_ID));
                        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        Contact contact = new Contact(id);
                        contact.setName(name);
                        contact.setPhoneNumber(cursor.getString(
                                cursor.getColumnIndex(
                                        Constants.Columns.DATA1)));
                        if (!list.contains(mIdMap.get(id))&&contact.getPhoneNumber()!=null) {
                            mIdMap.put(id, contact);
                            list.add(contact);
                        }
                    }
                    //getName(context,list, callback);
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
    public void addContact(final Context context, final Contact contact, final StatusCallback callback) {
                Uri uri = Uri.parse(Constants.Columns.URI_RAW_CONTACTS);
                ContentResolver resolver = context.getContentResolver();
                ContentValues values = new ContentValues();
                long contactid = ContentUris.parseId(
                        resolver.insert(uri, values));

                uri = Uri.parse(Constants.Columns.URI_DATA);

                //姓名
                values.put(Constants.Columns.RAW_CONTACT_ID, contactid);
                values.put(Data.MIMETYPE, Constants.Columns.MIME_NAME);
                values.put(Constants.Columns.DATA1, contact.getName());
                resolver.insert(uri, values);
                values.clear();

                //电话
                values.put(Constants.Columns.RAW_CONTACT_ID, contactid);
                values.put(Data.MIMETYPE, Constants.Columns.MIME_PHONE);
                values.put(Constants.Columns.DATA1, contact.getPhoneNumber());
                resolver.insert(uri, values);
                values.clear();

                //Email
                values.put(Constants.Columns.RAW_CONTACT_ID, contactid);
                values.put(Data.MIMETYPE, Constants.Columns.MIME_EMAIL);
                values.put(Constants.Columns.DATA1, contact.getEmail());
                resolver.insert(uri, values);
                values.clear();

                //NickName
                values.put(Constants.Columns.RAW_CONTACT_ID, contactid);
                values.put(Data.MIMETYPE, Constants.Columns.MIME_NICKNAME);
                values.put(Constants.Columns.DATA1, contact.getNickName());
                resolver.insert(uri, values);
                values.clear();

                //Address
                values.put(Constants.Columns.RAW_CONTACT_ID, contactid);
                values.put(Data.MIMETYPE, Constants.Columns.MIME_ADDRESS);
                values.put(Constants.Columns.DATA1, contact.getAddress());
                resolver.insert(uri, values);
    }


    @Override
    public void editContact(final Context context, final Contact contact, final StatusCallback callback) {

                ContentValues values = new ContentValues();
                //修改名字
                values.put(Data.MIMETYPE, Constants.Columns.MIME_NAME);
                values.put(Constants.Columns.DATA1, contact.getName());
                context.getContentResolver()
                       .update(Data.CONTENT_URI, values,
                               Constants.Columns.RAW_CONTACT_ID + "= ?",
                               new String[] { contact.getId() });
                values.clear();

                //电话
                values.put(Data.MIMETYPE, Constants.Columns.MIME_PHONE);
                values.put(Constants.Columns.DATA1, contact.getPhoneNumber());
                context.getContentResolver()
                       .update(Data.CONTENT_URI, values,
                               Constants.Columns.RAW_CONTACT_ID + "= ?",
                               new String[] { contact.getId() });
                values.clear();

                //Email
                values.put(Data.MIMETYPE, Constants.Columns.MIME_EMAIL);
                values.put(Constants.Columns.DATA1, contact.getEmail());
                context.getContentResolver()
                       .update(Data.CONTENT_URI, values,
                               Constants.Columns.RAW_CONTACT_ID + "= ?",
                               new String[] { contact.getId() });
                values.clear();

                //NickName
                values.put(Data.MIMETYPE, Constants.Columns.MIME_NICKNAME);
                values.put(Constants.Columns.DATA1, contact.getNickName());
                context.getContentResolver()
                       .update(Data.CONTENT_URI, values,
                               Constants.Columns.RAW_CONTACT_ID + "= ?",
                               new String[] { contact.getId() });
                values.clear();

                //Address
                values.put(Data.MIMETYPE, Constants.Columns.MIME_ADDRESS);
                values.put(Constants.Columns.DATA1, contact.getAddress());
                context.getContentResolver()
                       .update(Data.CONTENT_URI, values,
                               Constants.Columns.RAW_CONTACT_ID + "= ?",
                               new String[] { contact.getId() });
                callback.onSuccess();

    }


    @Override
    public void deleteContact(final Context context, final Contact contact, final StatusCallback callback) {
        new Thread(new Runnable() {
            @Override public void run() {
                Uri uri = Uri.parse(Constants.Columns.URI_DATA);
                context.getContentResolver()
                       .delete(uri, Constants.Columns.RAW_CONTACT_ID + "= ?",
                               new String[] { contact.getId() });
                callback.onSuccess();
            }
        }).start();
    }


    public static String getEmailById(Context context, String id, DataCallback<String> callback) {

        Cursor cursor = null;
            try {

                cursor = context.getContentResolver()
                        .query(Data.CONTENT_URI,
                                new String[]{Constants.Columns.DATA1},
                                Constants.Columns.RAW_CONTACT_ID + "= ?" +
                                        " AND " +
                                        Constants.Columns.MIMETYPE + "='" +
                                        Constants.Columns.MIME_EMAIL +
                                        "'", new String[]{id}, null);
                if(cursor!=null) {
                    cursor.moveToFirst();
                    callback.onSuccess(cursor.getString(cursor.getColumnIndex(Constants.Columns.DATA1)));
                }

            }
            catch (Exception e) {

            }

        if (cursor != null) {
            cursor.close();
        }
        return null;
    }

    @Override
    public void getEmail(Context context, List<Contact> contactList, DataCallback<List<Contact>> callback) {
        String id = "";
        Cursor cursor = null;
        for (Contact contact : contactList) {
            try {


            id = contact.getId();
            cursor = context.getContentResolver()
                    .query(Data.CONTENT_URI,
                            new String[]{Constants.Columns.DATA1},
                            Constants.Columns.RAW_CONTACT_ID + "= ?" +
                                    " AND " +
                                    Constants.Columns.MIMETYPE + "='" +
                                    Constants.Columns.MIME_EMAIL +
                                    "'", new String[]{id}, null);
            if (cursor.moveToNext()) {
                mIdMap.get(id)
                        .setEmail(cursor.getString(
                                cursor.getColumnIndex(Constants.Columns.DATA1)));
            }
        }
            catch (Exception e){
                continue;
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        getNickName(context, contactList, callback);
    }


    public static void getNickNameById(Context context, String id, DataCallback<String> callback) {

        Cursor cursor = null;
            try {
                cursor = context.getContentResolver()
                        .query(Data.CONTENT_URI,
                                new String[]{Constants.Columns.DATA1},
                                Constants.Columns.RAW_CONTACT_ID + "= ?" +
                                        " AND " +
                                        Constants.Columns.MIMETYPE + "='" +
                                        Constants.Columns.MIME_NICKNAME +
                                        "'", new String[]{id}, null);
                if(cursor!=null)
                {
                    cursor.moveToFirst();
                    String nickName = cursor.getColumnName(cursor.getColumnIndex(Constants.Columns.DATA1));
                    callback.onSuccess(nickName);
                }


            }catch (Exception e){}
            if(cursor!=null){
                cursor.close();
            }

    }
    @Override
    public void getNickName(Context context, List<Contact> contactList, DataCallback<List<Contact>> callback) {
        String id = "";
        Cursor cursor = null;
        for (Contact contact : contactList) {
            try {


                id = contact.getId();
                cursor = context.getContentResolver()
                        .query(Data.CONTENT_URI,
                                new String[]{Constants.Columns.DATA1},
                                Constants.Columns.RAW_CONTACT_ID + "= ?" +
                                        " AND " +
                                        Constants.Columns.MIMETYPE + "='" +
                                        Constants.Columns.MIME_NICKNAME +
                                        "'", new String[]{id}, null);
                if (cursor.moveToNext()) {
                    mIdMap.get(id)
                            .setNickName(cursor.getString(
                                    cursor.getColumnIndex(Constants.Columns.DATA1)));
                }
            }catch (Exception e){

            }
        }
        if (cursor != null) {
            cursor.close();
        }
        getAddress(context, contactList, callback);
    }


    public static void getAddressById(Context context, String id, DataCallback<String> callback) {
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver()
                    .query(Data.CONTENT_URI,
                            new String[]{Constants.Columns.DATA1},
                            Constants.Columns.RAW_CONTACT_ID + "= ?" +
                                    " AND " +
                                    Constants.Columns.MIMETYPE + "='" +
                                    Constants.Columns.MIME_ADDRESS +
                                    "'", new String[]{id}, null);
            if(cursor!=null)
            {
                cursor.moveToFirst();
                String nickName = cursor.getColumnName(cursor.getColumnIndex(Constants.Columns.DATA1));
                callback.onSuccess(nickName);
            }


        }catch (Exception e){}
        if(cursor!=null){
            cursor.close();
        }
    }
    @Override
    public void getAddress(Context context, List<Contact> contactList, DataCallback<List<Contact>> callback) {
        String id = "";
        Cursor cursor = null;
        for (Contact contact : contactList) {
            try {


                id = contact.getId();
                cursor = context.getContentResolver()
                        .query(Data.CONTENT_URI,
                                new String[]{Constants.Columns.DATA1},
                                Constants.Columns.RAW_CONTACT_ID + "= ?" +
                                        " AND " +
                                        Constants.Columns.MIMETYPE + "='" +
                                        Constants.Columns.MIME_ADDRESS +
                                        "'", new String[]{id}, null);
                if (cursor.moveToNext()) {
                    mIdMap.get(id)
                            .setAddress(cursor.getString(
                                    cursor.getColumnIndex(Constants.Columns.DATA1)));
                }
            }catch (Exception e){

            }
        }
        if (cursor != null) {
            cursor.close();
        }
        getContactImage();
    }


    @Override
    public void getName(Context context, List<Contact> contactList, DataCallback<List<Contact>> callback) {
        String id = "";
        Cursor cursor = null;
        for (Contact contact : contactList) {
            try {


                id = contact.getId();
                cursor = context.getContentResolver()
                        .query(Data.CONTENT_URI,
                                new String[]{Constants.Columns.DATA1},
                                Constants.Columns.RAW_CONTACT_ID + "= ?" +
                                        " AND " +
                                        Constants.Columns.MIMETYPE + "='" +
                                        Constants.Columns.MIME_NAME +
                                        "'", new String[]{id}, null);
                if (cursor.moveToNext()) {
                    String name= cursor.getString(
                            cursor.getColumnIndex(Constants.Columns.DATA1));
                    mIdMap.get(id)
                            .setName(name);
                    mIdMap.get(id).setFirstChar(name.charAt(0));

                }
            }catch (Exception e){
                continue;
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        getEmail(context, contactList, callback);
    }

    public void getContactImage() {

        for(Contact c:list) {
            try {
                Cursor cur = context.getContentResolver().query(
                        ContactsContract.Data.CONTENT_URI,
                        null,
                        ContactsContract.Data.CONTACT_ID + "=" + c.getId() + " AND "
                                + ContactsContract.Data.MIMETYPE + "='"
                                + ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE + "'", null,
                        null);
                if (cur != null) {
                    if (!cur.moveToFirst()) {
                        return; // no photo
                    }
                } else {
                    return; // error in cursor process
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            Uri person = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long
                    .parseLong(c.getId()));
            Uri photo =Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
            c.setAvatarUrl(photo);
            Log.d("UTI_OUND",photo.toString());
        }
    }


    @Override
    public Object doInBackground(Object[] objects) {
        getContactList(context,callback);
        return list;
    }

    @Override
    protected void onPostExecute(Object o) {
        if(list.size()>0&&list!=null)
            callback.onSuccess(list);

        else callback.onFailure("Failed");
    }
}
