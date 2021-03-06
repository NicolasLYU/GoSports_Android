package com.wentongwang.mysports.views.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by Wentong WANG on 2017/3/26.
 */
public abstract class ItemViewHolder<Item> extends RecyclerView.ViewHolder{

    protected View rootView;
    protected Context context;
    protected Item item;

    public ItemViewHolder(View view) {
        super(view);
        this.rootView = view;
        this.context = view.getContext();
        ButterKnife.bind(this, view);
    }

    public abstract void setItem(Item item);
}
