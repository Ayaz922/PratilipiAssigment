package com.ayazalam.pratlipi.ui.adapter;

import android.content.Context;

import com.ayazalam.pratlipi.R;
import com.ayazalam.pratlipi.bean.Contact;

import java.util.List;

/**
 * Created by Ayaz Alam on 2019/5/03.
 */
public class ContactAdapter extends BaseRecyclerAdapter<Contact>{

    Context context;
    public ContactAdapter(Context context, int layoutResId, List<Contact> data) {
        super(context, layoutResId, data);
        this.context =context;
    }


    @Override protected void bindData(BaseViewHolder viewHolder, Contact
            item,int position) {
        viewHolder.setImageUrl(R.id.main_avatar,item.getId(),item.getName());
        viewHolder.setText(R.id.main_name,item.getName());
        viewHolder.setVisible(R.id.main_letter,false);
        if(position != 0) {
            Contact prevContact = mData.get(position - 1);
            if (item.getFirstChar() != prevContact.getFirstChar()) {
                viewHolder.setVisible(R.id.main_letter, false);
                viewHolder.setText(R.id.main_letter, item.getFirstChar() + "");
            }
            else {
                viewHolder.setVisible(R.id.main_letter, false);
            }
        }else {
            viewHolder.setVisible(R.id.main_letter, false);
            viewHolder.setText(R.id.main_letter, item.getFirstChar() + "");
        }
    }
}
