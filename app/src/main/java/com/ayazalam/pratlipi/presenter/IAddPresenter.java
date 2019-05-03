package com.ayazalam.pratlipi.presenter;

import com.ayazalam.pratlipi.bean.Contact;
import com.ayazalam.pratlipi.view.IAddView;


/**
 * Created by Ayaz Alam on 2019/5/03.
 */

public interface IAddPresenter extends IPresenter<IAddView>{

    void addContacts(Contact contact);

    void deleteContacts(Contact contact);

    void editContacts(Contact contact);
}
