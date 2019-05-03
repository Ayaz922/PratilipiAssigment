package com.ayazalam.pratlipi.view;

import com.ayazalam.pratlipi.bean.Contact;

/**
 * Created by skylineTan on 2016/6/30.
 */
public interface IAddView extends IView{

    void finishActivity(Contact contact);

    void showError(String msg);

}
