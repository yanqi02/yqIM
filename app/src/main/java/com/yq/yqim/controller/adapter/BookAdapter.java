package com.yq.yqim.controller.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yq.yqim.R;
import com.yq.yqim.controller.activity.webviewActivity;
import com.yq.yqim.model.bean.Gbooks;
import com.yq.yqim.model.bean.etalkBean;
import com.yq.yqim.model.bean.testbean;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder>
{
    private Context context1;
    private ArrayList<Gbooks> mbooks;
    private OnItemClickListener onItemClickListener;

public  static  String bhref;
    public BookAdapter(Context context, ArrayList<Gbooks> paramArrayList)
    {
        this.context1 = context;
        this.mbooks = paramArrayList;
    }

    class BookViewHolder extends RecyclerView.ViewHolder
    {
        private TextView bookname;
        private TextView bookclass;
        private TextView bookimg;

private  TextView bookhref;
        public BookViewHolder(final View view)
        {
            super(view);


            bookname = (TextView)view.findViewById(R.id.bookname);
            bookclass=(TextView)view.findViewById(R.id.bookclass);
            bookimg=(TextView)view.findViewById(R.id.score);
bookhref=(TextView)view.findViewById(R.id.bookhref);

//            setOnItemClickListener(new BookAdapter.OnItemClickListener() {
//                @Override
//                public void OnItemClick(View view, Gbooks gbooks) {
//                    Intent intent1 = new Intent(context1,webviewActivity.class);
//                    intent1.putExtra("href",gbooks.getBookhref());
//                    startActivity(context1,intent1,null);
//                }
//            });


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(bookhref.getText().toString()+"               开赛的"+bookname.getText().toString());
                    bhref=bookhref.getText().toString();
                    Intent intent=new Intent();
                   intent.setClass(context1,webviewActivity.class);
                   intent.putExtra("href",bookhref.getText().toString());

startActivity(context1,intent,null);
                }
            });
        }
    }




    @Override
    public void onBindViewHolder(BookViewHolder viewHolder, int paramInt)
    {
        Gbooks gbooks = (Gbooks) this.mbooks.get(paramInt);
        viewHolder.bookname.setText(gbooks.getBookname());
        viewHolder.bookclass.setText(gbooks.getBookclass());
        viewHolder.bookimg.setText(gbooks.getBookimg());
viewHolder.bookhref.setText(gbooks.getBookhref());


    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int paramInt)
    {
        return new BookAdapter.BookViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false));
    }

    public void setOnItemClickListener(OnItemClickListener paramOnItemClickListener)
    {
        this.onItemClickListener = paramOnItemClickListener;
    }

    @Override
    public int getItemCount()
    {
        return this.mbooks.size();
    }

    public static abstract interface OnItemClickListener
    {
        public abstract void OnItemClick(View paramView, Gbooks gbooks);
    }
}

