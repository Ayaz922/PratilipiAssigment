package com.ayazalam.pratlipi.model;

import android.content.Context;
import com.ayazalam.pratlipi.bean.Contact;
import com.ayazalam.pratlipi.support.callback.DataCallback;
import com.ayazalam.pratlipi.support.callback.StatusCallback;
import java.util.List;


/**
 * Created by Ayaz Alam on 2019/5/03.
 */

public interface IContactModel {

    void getContactList(Context context, DataCallback<List<Contact>> callback);

    void addContact(Context context,Contact contact,StatusCallback callback);

    void editContact(Context context,Contact contact,StatusCallback callback);

    void deleteContact(Context context,Contact contact,StatusCallback callback);

    void getEmail(Context context,List<Contact> contactList,
                     DataCallback<List<Contact>> callback);

    void getNickName(Context context,List<Contact> contactList,
                        DataCallback<List<Contact>> callback);

    void getAddress(Context context,List<Contact> contactList,
                       DataCallback<List<Contact>> callback);

    void getName(Context context,List<Contact> contactList,
                    DataCallback<List<Contact>> callback);
}
