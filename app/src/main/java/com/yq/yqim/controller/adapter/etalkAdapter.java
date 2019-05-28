package com.yq.yqim.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yq.yqim.R;
import com.yq.yqim.model.bean.etalkBean;
import com.yq.yqim.model.bean.testbean;

import java.util.ArrayList;

public class etalkAdapter extends RecyclerView.Adapter<etalkAdapter.etalkViewHolder>
{
    private Context context1;
    private ArrayList<etalkBean> mtalks;
    private OnItemClickListener onItemClickListener;


    class etalkViewHolder extends RecyclerView.ViewHolder
    {
        private TextView talk_user;
        private EditText context;
        private Button sure;


        public etalkViewHolder(View view)
        {
            super(view);


            talk_user = (TextView)view.findViewById(R.id.talk_user);
            context=(EditText)view.findViewById(R.id.context);
            sure=(Button)view.findViewById(R.id.sure);
        }
    }




    @Override
    public void onBindViewHolder(etalkViewHolder viewHolder, int paramInt)
    {
        etalkBean talks = (etalkBean)this.mtalks.get(paramInt);
        viewHolder.talk_user.setText(talks.getUsername());
        viewHolder.context.setText(talks.getContext());

    }

@Override
    public etalkViewHolder onCreateViewHolder(ViewGroup parent, int paramInt)
    {
        return new etalkAdapter.etalkViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_etalk, parent, false));
    }

    public void setOnItemClickListener(OnItemClickListener paramOnItemClickListener)
    {
        this.onItemClickListener = paramOnItemClickListener;
    }

    @Override
    public int getItemCount()
    {
        return this.mtalks.size();
    }

    public static abstract interface OnItemClickListener
    {
        public abstract void OnItemClick(View paramView, testbean paramtestbean);
    }
}

