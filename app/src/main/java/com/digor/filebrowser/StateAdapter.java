package com.digor.filebrowser;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<State> states;

    public StateAdapter(LayoutInflater inflaterParent, List<State> states) {
        this.states = states;
        this.inflater = inflaterParent;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_template, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        State state = states.get(position);
        holder.iconView.setImageResource(state.getImageObject());
        holder.nameView.setText(state.getNameObject());
        holder.dateChangeView.setText(state.getDateChangeObject());
        holder.sizeView.setText(state.getSizeObject());
        holder.iconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                states.remove(0);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView iconView;
        final AppCompatTextView nameView, dateChangeView, sizeView;

        ViewHolder(View view) {
            super(view);
            iconView = view.findViewById(R.id.icon_template);
            nameView = view.findViewById(R.id.name_template);
            dateChangeView = view.findViewById(R.id.dateChange_template);
            sizeView = view.findViewById(R.id.size_template);
        }
    }
}
