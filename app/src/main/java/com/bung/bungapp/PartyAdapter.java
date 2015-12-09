package com.bung.bungapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by rok on 2015. 11. 14..
 */
public class PartyAdapter extends RecyclerView.Adapter<PartyAdapter.PartyViewHolder> {

    @Override
    public PartyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(PartyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class PartyViewHolder extends RecyclerView.ViewHolder {

        public PartyViewHolder(View itemView) {
            super(itemView);
        }
    }

}
