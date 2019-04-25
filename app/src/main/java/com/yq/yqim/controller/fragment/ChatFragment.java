package com.yq.yqim.controller.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.yq.yqim.controller.activity.ChatActivity;

import java.util.List;

public class ChatFragment extends EaseConversationListFragment {

    protected void initView(){
        super.initView();
        //跳转到消息页面
        setConversationListItemClickListener(new EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                //传递参数
intent.putExtra(EaseConstant.EXTRA_USER_ID,conversation.conversationId());
    //判断一对一还是一对多
                if(conversation.getType()== EMConversation.EMConversationType.GroupChat){
                    intent.putExtra( EaseConstant.EXTRA_CHAT_TYPE,EaseConstant.CHATTYPE_GROUP);
                }

                startActivity(intent);
            }
        });



        //监听会话消息

        EMClient.getInstance().chatManager().addMessageListener(emMessagelistener);
    }
   private EMMessageListener emMessagelistener=new EMMessageListener() {
       @Override
       public void onMessageReceived(List<EMMessage> list) {
          //设置数据
           EaseUI.getInstance().getNotifier().notify(list);
           //刷新页面

           refresh();
       }

       @Override
       public void onCmdMessageReceived(List<EMMessage> list) {

       }

       @Override
       public void onMessageRead(List<EMMessage> list) {

       }

       @Override
       public void onMessageDelivered(List<EMMessage> list) {

       }

       @Override
       public void onMessageRecalled(List<EMMessage> list) {

       }

       @Override
       public void onMessageChanged(EMMessage emMessage, Object o) {

       }
   };
}
