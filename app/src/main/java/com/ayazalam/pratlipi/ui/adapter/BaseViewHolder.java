package com.ayazalam.pratlipi.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.ayazalam.pratlipi.ui.widget.CircleImageView;
import com.ayazalam.pratlipi.utils.PhotoHelper;

import java.io.InputStream;


/**
 * Created by Ayaz Alam on 2019/5/03.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder{

    private final SparseArray<View> views;

    private final Context context;

    public View convertView;

    public BaseViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        this.views = new SparseArray<View>();
        convertView = itemView;
    }

    public View getConvertView() {
        return convertView;
    }

    public BaseViewHolder setText(int viewId, CharSequence value) {
        TextView view = getView(viewId);
        view.setText(value);
        return this;
    }

    public BaseViewHolder setImageResource(int viewId, int imageResId) {
        ImageView view = getView(viewId);
        view.setImageResource(imageResId);
        return this;
    }

    public BaseViewHolder setBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public BaseViewHolder setBackgroundRes(int viewId, int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public BaseViewHolder setTextColor(int viewId, int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    public BaseViewHolder setTextColorRes(int viewId, int textColorRes) {
        TextView view = getView(viewId);
        view.setTextColor(context.getResources().getColor(textColorRes));
        return this;
    }

    public BaseViewHolder setImageName(int viewId, String name) {
        CircleImageView view = getView(viewId);
        view.setName(name.toUpperCase());
        return this;
    }

    public BaseViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    public BaseViewHolder setImageUrl(int viewId, String imageUrl) {
        ImageView view = getView(viewId);
        Glide.with(context).load(imageUrl).into(view);
        return this;
    }
    public BaseViewHolder setImageUrl(int viewId, String id,String name) {
        ImageView view = getView(viewId);
        InputStream d = PhotoHelper.openPhoto(Long.parseLong(id),context);
        Bitmap bitmap1 = BitmapFactory.decodeStream(d);
        Glide.with(context)
                .load(bitmap1)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(true)
                .centerCrop()
                .dontAnimate()
                .into(view);
        if(bitmap1==null) setImageName(viewId,name.charAt(0)+"");
        return this;
    }

    public BaseViewHolder setImageUrl(int viewId, String imageUrl, int defResourceId) {
        ImageView view = getView(viewId);
        Glide.with(context).load(imageUrl).placeholder(defResourceId).into(view);
        return this;
    }

    public BaseViewHolder setImageUrl(int viewId, String imageUrl, int defResourceId, BitmapTransformation... transformations) {
        ImageView view = getView(viewId);
        Glide.with(context).load(imageUrl).placeholder(defResourceId).transform(transformations).into(view);
        return this;
    }


    public BaseViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public BaseViewHolder setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public BaseViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public BaseViewHolder setOnClickListener(int viewId, BaseRecyclerAdapter
            .OnClickListenerwithHeader listener) {
        View view = getView(viewId);
        listener.position = getAdapterPosition();
        view.setOnClickListener(listener);
        return this;
    }

    public BaseViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public BaseViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }
}
