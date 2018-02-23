package com.betaout.betademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.betaout.sdk.app.BetaOut;
import com.betaout.sdk.push.BOInboxMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 13/1/18.
 */
public class InboxActivity extends AppCompatActivity {

    BOInboxAdapter boInboxAdapter;
    List<BOInboxMessage> inboxMessages = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_o_sdk_inbox_activity);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        findViewById(R.id.inboxFab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BetaOut.getInstance().clearAllInboxMessages();
                inboxMessages.clear();
                if(boInboxAdapter != null)
                    boInboxAdapter.notifyDataSetChanged();
            }
        });


        if(BetaOut.getInstance() != null) {
            inboxMessages = BetaOut.getInstance().getAllInboxMessages(true, true, false);
            if(inboxMessages == null){
                inboxMessages = new ArrayList<>();
            }
            Log.i("AppInfo", inboxMessages.size() + " size of inbox");
            Log.i("AppInfo", BetaOut.getInstance().getInboxUnreadCount() + " unread size of inbox");
            Log.i("AppInfo", BetaOut.getInstance().getInboxReadCount() + " read size of inbox");
            Log.i("AppInfo", BetaOut.getInstance().getInboxAllMessagesCount() + " all size of inbox");
            boInboxAdapter = new BOInboxAdapter(inboxMessages, this);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(boInboxAdapter);
        boInboxAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(inboxMessages != null) {
            for (int i = 0; i < inboxMessages.size(); i++){
                if(inboxMessages.get(i).isUnread())
                    inboxMessages.get(i).markRead();
            }
        }
    }
}
