package com.ayazalam.pratlipi.view;

import com.ayazalam.pratlipi.bean.Contact;

public interface IAddView extends IView{

    void finishActivity(Contact contact);

    void showError(String msg);

}
