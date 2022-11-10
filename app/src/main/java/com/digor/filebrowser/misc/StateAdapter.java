package com.digor.filebrowser.misc;

import android.os.Build;
import android.util.Log;
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
               FolderNavigation(state.getButtonPath());
            }
        });
    }

    public void FolderNavigation(String path){
        File newFile = new File(path);
        try {
            if (newFile != null) {
                states.clear();
                states.add(new State("<=", "", "", R.drawable.code_ic, newFile.getParent()));
                for (String file : newFile.list()) {
                    Path currentFilePath = null;
                    long fileSize = 0;
                    BasicFileAttributes fileAttributes = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                        currentFilePath = Paths.get(path + "/" + file);


                        try {
                            fileSize = Files.size(currentFilePath);
                            fileAttributes = Files.readAttributes(currentFilePath, BasicFileAttributes.class);
                        } catch (Exception e) {
                        }

                        File currentFile = new File(path + "/" + file);
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                        states.add(new State(file,
                                FormatBytes(fileSize),
                                fileAttributes != null ? fileAttributes.lastModifiedTime().toString() : "",
                                getTypeIcon(fileAttributes),
                                currentFile.getPath()));
                    }
                }
            }
        }catch(Exception e){
            Log.i("myLog", e.toString());
        }
        notifyDataSetChanged();
    }

    public String FormatBytes(long size){
        if(size <= 0) return "0 B";
        String[] units = new String[]{"B","KB", "MB", "GB", "TB"};
        int unitGroup = (int) (Math.log10(size)/Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, unitGroup)) + " " + units[unitGroup];
    }

    public int getTypeIcon(BasicFileAttributes attr){
        if(attr == null)
            return R.drawable.code_ic;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return attr.isDirectory() ? R.drawable.ic_launcher_foreground : R.drawable.file_ic;
        }
        return R.drawable.info_ic;
    }

    @Override
    public int getItemCount() {
        return states.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView iconView;
        final AppCompatTextView nameView, dateChangeView, sizeView;
        final String buttonPath;

        ViewHolder(View view) {
            super(view);
            iconView = view.findViewById(R.id.icon_template);
            nameView = view.findViewById(R.id.name_template);
            dateChangeView = view.findViewById(R.id.dateChange_template);
            sizeView = view.findViewById(R.id.size_template);
            buttonPath = "";
        }
    }
}
