package com.example.android.ira_1202150251_studycase6;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.CommentHolder> {
    Context con;
    List<DBComm> list;

    public AdapterComment(Context con, List<DBComm> list) {
        this.con = con;
        this.list = list;
    }
    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentHolder(LayoutInflater.from(con).inflate(R.layout.rec_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(CommentHolder holder, int position) {
        DBComm cur = list.get(position);
        holder.t1cmt.setText(cur.getT1cmt());
        holder.thecmt.setText(cur.getThecmt());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    //Constructor adapter


    class CommentHolder extends RecyclerView.ViewHolder{
        TextView t1cmt, thecmt;
        public CommentHolder(View itemView) {
            super(itemView);
            t1cmt = itemView.findViewById(R.id.user);
            thecmt = itemView.findViewById(R.id.comment);
        }
    }
}
