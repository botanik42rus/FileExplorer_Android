package com.digor.filebrowser.misc;

import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.digor.filebrowser.MainActivity;
import com.digor.filebrowser.R;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<State> states;
    private final IFileExplore FileExploreClass;

    public StateAdapter(LayoutInflater inflaterParent, List<State> states, IFileExplore fileExploreClass) {
        this.states = states;
        this.inflater = inflaterParent;
        FileExploreClass = fileExploreClass;
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

        holder.getCurrentView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<State> newStates = FileExploreClass.FolderNavigation(state.getButtonPath());
                if(newStates !=null){
                    states.clear();
                    states.addAll(newStates);
                    notifyDataSetChanged();
                }
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
        final String buttonPath;
        private final View currentView;
        ViewHolder(View view) {
            super(view);
            iconView = view.findViewById(R.id.icon_template);
            nameView = view.findViewById(R.id.name_template);
            dateChangeView = view.findViewById(R.id.dateChange_template);
            sizeView = view.findViewById(R.id.size_template);
            buttonPath = "";
            currentView = view;
        }

        public View getCurrentView(){return currentView;}
    }
}
