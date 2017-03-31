package com.wentongwang.mysports.views.activity.newscomment;

import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;
import android.view.LayoutInflater;
import android.content.Context;


import com.wentongwang.mysports.base.BaseActivity;
import com.wentongwang.mysports.model.module.CommentInfo;
import com.wentongwang.mysports.model.module.NewsInfo;
import com.wentongwang.mysports.R;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.zip.Inflater;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/3/28.
 */

public class NewsCommentActivity extends BaseActivity implements NewsCommentView {
    @BindView(R.id.comment_list)
    protected ListView commentList;

    private NewsCommentAdapter newsCommentAdapter;
    private List<CommentInfo> itemList;
    private NewsInfo news;

    @Override
    protected void initDatesAndViews() {
        news = (NewsInfo) getIntent().getSerializableExtra("item");
        itemList = new ArrayList<>();
        initComment(this.itemList);
        newsCommentAdapter = new NewsCommentAdapter();
        LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        LinearLayout ll = (LinearLayout) layoutInflater.inflate( R.layout.acitivity_news_comment_header_layout, null, false );
        //LinearLayout ll = (LinearLayout) findViewById(R.id.comment_news_header);
        TextView tv = (TextView) ll.findViewById(R.id.comment_news_item_content);
        tv.setText(news.getNews_content());
        commentList.addHeaderView(ll);
        newsCommentAdapter.setItemList(this.itemList);
        commentList.setAdapter(newsCommentAdapter);

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected boolean noTitle() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_comment_layout;
    }

    public void initComment(List<CommentInfo> list){
        for (int i=0;i<10;i++){
            CommentInfo item = new CommentInfo("content" + i, i);
            list.add(item);
        }
    }

}
