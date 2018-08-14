package com.artifex.mupdflib;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dell on 2018/7/24.
 */

public class SearchTextAdapter extends RecyclerView.Adapter<SearchTextAdapter.MyViewHolder> {
    private Activity activity;
    private List<SearchItemEntity> datas;
    private OnItemClick onItemClick;
    public SearchTextAdapter(Activity activity, List<SearchItemEntity> datas) {
        this.activity = activity;
        this.datas = datas;
    }

    @Override
    public SearchTextAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(activity).inflate(R.layout.search_text_item, null));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String title = "第" + (datas.get(position).getIndex()+1) + "页：";
        String content = datas.get(position).getContent();
        String keyWord = datas.get(position).getKeyWord();
        String[] contents = content.split("。");
        String text = "";
        for (int i = 0; i < contents.length; i++) {
            String[] contentss = contents[i].split(",");
            for (int j = 0; j < contentss.length - 1; j++) {
                text += (contentss[j] + keyWord + contentss[j + 1]+";"+"\r\n");
            }
        }
        holder.tv.setText(title + text);
        holder.my_linearlayout.setTag(position);
        holder.my_linearlayout.setOnItemClick(onItemClick);
    }
    public void setOnItemClick(SearchTextAdapter.OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }
    @Override
    public int getItemCount() {
        return datas.size();
    }

    interface OnItemClick {
        void itemClick(View view,int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        MyLinearLayoutView my_linearlayout;
        MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
            my_linearlayout = (MyLinearLayoutView)itemView.findViewById(R.id.my_linearlayout);
        }
    }
}
