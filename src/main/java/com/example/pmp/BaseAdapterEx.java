package com.example.pmp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class BaseAdapterEx extends BaseAdapter {
    Context mContext = null;
    ArrayList<Subject> mData = null;
    ArrayList<Subject> mDataShow = null;
    LayoutInflater mLayoutInflater = null;
    private OnClickEvent handler;

    public BaseAdapterEx(Context context, ArrayList<Subject> data) {
        mContext = context;
        handler = (OnClickEvent) context;
        mData = data;
        mDataShow = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Subject getItem(int position) {
        return mData.get(position);
    }

    class ViewHolder {
        TextView nameTv;
        TextView snameTv;
        TextView TimeTv;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View itemLayout = convertView;
        ViewHolder viewHolder = null;


        if (itemLayout == null) {
            itemLayout = mLayoutInflater.inflate(R.layout.list_view_item_layout, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.snameTv = (TextView) itemLayout.findViewById(R.id.sname_text);
            viewHolder.nameTv = (TextView) itemLayout.findViewById(R.id.name_text);
            viewHolder.TimeTv = (TextView) itemLayout.findViewById(R.id.start1_text);

            itemLayout.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) itemLayout.getTag();
        }
        viewHolder.snameTv.setText(mData.get(position).mSname);
        viewHolder.nameTv.setText(mData.get(position).mName);
        viewHolder.TimeTv.setText(mData.get(position).mTime);

        final FrameLayout root = (FrameLayout) parent.findViewById(R.id.Root);
        final String temp = mData.get(position).mTime;
        Button button = (Button)itemLayout.findViewById(R.id.addButton);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                handler.onClick(mData.get(position));
//                Button bt1 = new Button(mContext);
//                bt1.setText("Button 1");
//                FrameLayout.LayoutParams bt1LP = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT);
//
//                root.addView(bt1,bt1LP);
            }
        });




        return itemLayout;

    }

    interface OnClickEvent {
        void onClick(Subject subject);
    }

}