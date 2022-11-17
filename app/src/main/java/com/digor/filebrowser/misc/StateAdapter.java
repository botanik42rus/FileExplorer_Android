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
import com.digor.filebrowser.ui.TabView;

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
    private final boolean IsLayoutManagerLinear;
    private TabView currentTabView;
    public StateAdapter(LayoutInflater inflaterParent, List<State> states, IFileExplore fileExploreClass, boolean isLayoutManagerLinear, TabView tabView) {
        this.states = states;
        this.inflater = inflaterParent;
        FileExploreClass = fileExploreClass;
        IsLayoutManagerLinear = isLayoutManagerLinear;
        currentTabView = tabView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(IsLayoutManagerLinear ? R.layout.list_item_template : R.layout.grid_item_template, parent, false);
        return new ViewHolder(view, IsLayoutManagerLinear);
    }

    @Override
    public void onBindViewHolder(ViewHolder holderList, int position) {
        State state = states.get(position);
        holderList.iconView.setImageDrawable(state.getImageObject());

        holderList.nameView.setText(state.getNameObject());

        holderList.dateChangeView.setText(state.getDateChangeObject());
        holderList.sizeView.setText(state.getSizeObject());
        holderList.getCurrentView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPath(state.getButtonPath());
            }
        });
    }

    public void openPath(String path){
        List<State> newStates = FileExploreClass.FolderNavigation(path);
        if(newStates !=null){
            states.clear();
            states.addAll(newStates);
            notifyDataSetChanged();

            currentTabView.returnButton.setVisibility(FileExploreClass.getIsInitial() ? View.GONE : View.VISIBLE);
        }
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
        ViewHolder(View view, boolean isLinearLayoutManager) {
            super(view);
            iconView = isLinearLayoutManager ? view.findViewById(R.id.icon_template) : view.findViewById(R.id.icon_template_grid);
            nameView = isLinearLayoutManager ? view.findViewById(R.id.name_template) : view.findViewById(R.id.name_template_grid);

            dateChangeView = isLinearLayoutManager ? view.findViewById(R.id.dateChange_template) : view.findViewById(R.id.dateChange_template_grid);
            sizeView = isLinearLayoutManager ? view.findViewById(R.id.size_template) : view.findViewById(R.id.size_template_grid);
            buttonPath = "";
            currentView = view;
        }

        public View getCurrentView(){return currentView;}
    }
}
