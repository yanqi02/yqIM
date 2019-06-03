package com.yq.yqim.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yq.yqim.R;

import com.yq.yqim.controller.fragment.ChatFragment;
import com.yq.yqim.model.bean.testbean;

import java.util.ArrayList;
import java.util.Iterator;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {
    public static String friendName1;
    private Context context;
    private ArrayList<testbean> mFriendList;
    private OnItemClickListener onItemClickListener;

    public FriendAdapter(Context paramContext, ArrayList<testbean> paramArrayList) {
        this.context = paramContext;
        this.mFriendList = paramArrayList;
    }

    public int getItemCount() {
        return this.mFriendList.size();
    }

    public void onBindViewHolder(FriendViewHolder paramFriendViewHolder, int paramInt) {
        testbean localtestbean = (testbean) this.mFriendList.get(paramInt);
        paramFriendViewHolder.friendName.setText(localtestbean.getUsername());
    }

    public FriendViewHolder onCreateViewHolder(ViewGroup parent, int paramInt) {
        return new FriendViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend, parent, false));
    }

    public void setOnItemClickListener(OnItemClickListener paramOnItemClickListener) {
        this.onItemClickListener = paramOnItemClickListener;
    }

    class FriendViewHolder extends RecyclerView.ViewHolder {
        TextView friendName;

        public FriendViewHolder(View paramView) {
            super(paramView);

            this.friendName = ((TextView) paramView.findViewById(R.id.tv_friend));
            paramView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View paramAnonymousView) {
                    if (FriendAdapter.this.onItemClickListener != null) {
                        FriendAdapter.this.onItemClickListener.OnItemClick(paramAnonymousView, (testbean) FriendAdapter.this.mFriendList.get(FriendAdapter.FriendViewHolder.this.getLayoutPosition()));
                        FriendAdapter.friendName1 = ((testbean) FriendAdapter.this.mFriendList.get(FriendAdapter.FriendViewHolder.this.getLayoutPosition())).getUsername();
                        testbean testbean = new testbean();
                        testbean.setUsername(FriendAdapter.friendName1);
                        if ((ChatFragment.friendList != null) && (ChatFragment.friendList.size() > 0)) {
                            System.out.println("if");
                            System.out.println(ChatFragment.friendList);
                        }
                    }

                    for (; ; ) {
                        try {
                            Object localObject = ChatFragment.friendList.iterator();
                            if (((Iterator) localObject).hasNext()) {
                                testbean localtestbean = (testbean) ((Iterator) localObject).next();
                                if (localtestbean.getUsername().equals(friendName1)) {
                                    System.out.println("ifif");
                                    ChatFragment.friendList.remove(localtestbean);
                                    ChatFragment.friendList.add(localtestbean);
                                    break;
                                }
                                System.out.println("ifelse");
                                ChatFragment.friendList.add(localtestbean);
                                break;
                            }
                            localObject = new StringBuilder();
                            ((StringBuilder) localObject).append(((testbean) FriendAdapter.this.mFriendList.get(FriendAdapter.FriendViewHolder.this.getLayoutPosition())).getUsername());
                            ((StringBuilder) localObject).append("ºÃÓÑ  ");
//                            paramAnonymousView.println(((StringBuilder)localObject).toString());
                        } catch (Exception e) {
                        }
                        return;
//                        System.out.println("else");
//                        ChatFragment.friendList.add(loca);
//                        return;
                    }
                }
            });
        }
    }

    public static abstract interface OnItemClickListener {
        public abstract void OnItemClick(View paramView, testbean paramtestbean);
    }
}

