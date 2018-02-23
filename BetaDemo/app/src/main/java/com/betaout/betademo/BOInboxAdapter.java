package com.betaout.betademo;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.betaout.sdk.push.BOInboxMessage;

import java.util.List;

/**
 * Created by root on 30/5/17.
 */
public class BOInboxAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<BOInboxMessage> boInboxMessageList;


    public BOInboxAdapter(List<BOInboxMessage> boInboxMessageList, Context context) {
        this.boInboxMessageList = boInboxMessageList;
        this.context = context;
    }

    final int EMPTY = 0;
    final int STANDARD = 1;

    @Override
    public int getItemViewType(int position) {

        if (boInboxMessageList.size() == 0) {
            return EMPTY;
        } else  {
            return STANDARD;
        }

    }

    @Override
    public int getItemCount() {

        if(boInboxMessageList.size() > 0)
            return boInboxMessageList.size();
        else
            return 1;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        try {
            if (viewType == EMPTY)
                return new ViewHolderEmpty(LayoutInflater.from(parent.getContext()).inflate(R.layout.not_available, parent, false));
            else if (viewType == STANDARD)
                return new ViewHolderStandard(LayoutInflater.from(parent.getContext()).inflate(R.layout.b_o_sdk_inbox, parent, false));
            else
                return new ViewHolderEmpty(LayoutInflater.from(parent.getContext()).inflate(R.layout.not_available, parent, false));
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        try {
            switch (holder.getItemViewType()) {
                case EMPTY:
                    break;
                case STANDARD:
                    final ViewHolderStandard viewHolderStandard = (ViewHolderStandard) holder;

                    if(boInboxMessageList.get(position).getTitle() != null && boInboxMessageList.get(position).getTitle().length() > 0) {
                        viewHolderStandard.t.setVisibility(View.VISIBLE);
                        if(boInboxMessageList.get(position).isUnread()){
                            viewHolderStandard.t.setTypeface(null, Typeface.BOLD);
                        }
                        viewHolderStandard.t.setText(boInboxMessageList.get(position).getTitle());
                    }else{
                        viewHolderStandard.t.setVisibility(View.GONE);
                    }
                    if(boInboxMessageList.get(position).getMessage() != null && boInboxMessageList.get(position).getMessage().length() > 0) {
                        viewHolderStandard.m.setVisibility(View.VISIBLE);
                        if(boInboxMessageList.get(position).isUnread()){
                            viewHolderStandard.m.setTypeface(null, Typeface.BOLD);
                        }
                        viewHolderStandard.m.setText(boInboxMessageList.get(position).getMessage());
                    }else{
                        viewHolderStandard.m.setVisibility(View.GONE);
                    }

                    break;

            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public class ViewHolderStandard extends RecyclerView.ViewHolder {

        TextView t, m;

        public ViewHolderStandard(View itemView) {
            super(itemView);

            t = (TextView)itemView.findViewById(R.id.t);
            m = (TextView)itemView.findViewById(R.id.m);

        }
    }

    public class ViewHolderEmpty extends RecyclerView.ViewHolder {


        public ViewHolderEmpty(View itemView) {
            super(itemView);

        }

    }

}
