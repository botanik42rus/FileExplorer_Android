package com.digor.filebrowser.misc;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.digor.filebrowser.MainActivity;
import com.digor.filebrowser.R;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class FileExploreAfterAPI26 implements IFileExplore {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public List<State> FolderNavigation( String path) {
        List<State> newStateArray = new ArrayList<State>();
        File CurrentItem = new File(path);
        if(CurrentItem == null) return null;

        try{
            newStateArray.add(new State("","","",R.drawable.code_ic, CurrentItem.getParent()));

            for (String file : CurrentItem.list()){
                Path currentItemPath = Paths.get(path + "/"+file);
                BasicFileAttributes currentItemAttr = Files.readAttributes(currentItemPath,BasicFileAttributes.class);;
                long fileSize = Files.size(currentItemPath);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String currentItemLastModifiedDate = sdf.format(currentItemPath.toFile().lastModified());

                newStateArray.add(new State(file,
                        currentItemAttr.isDirectory() ? "" : FormatBytes(fileSize),
                        currentItemLastModifiedDate != null ? currentItemLastModifiedDate : "",
                        currentItemAttr.isDirectory() ? R.drawable.ic_launcher_foreground : R.drawable.file_ic,
                        currentItemPath.toString()));
            }
        }
        catch (Exception e){
            newStateArray = null;
            Log.i("myLog", e.toString());
        }

        return newStateArray;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public List<State> InitializeData() {
        List<State> arrFiles = new ArrayList<State>();
        arrFiles.clear();
        File[] appsDir = ContextCompat.getExternalFilesDirs(MainActivity.mainContext, null);
        ArrayList<File> extRootPath = new ArrayList<File>();
        for (File file1 : appsDir) {
            extRootPath.add(file1.getParentFile().getParentFile().getParentFile().getParentFile());
        }

        for(File filesToStorage : extRootPath){
            arrFiles.add(new State(filesToStorage.getName(),"","", R.drawable.ic_launcher_foreground, filesToStorage.getPath()));
        }
        return arrFiles;
    }


    public String FormatBytes(long size){
        if(size <= 0) return "0 B";
        String[] units = new String[]{"B","KB", "MB", "GB", "TB"};
        int unitGroup = (int) (Math.log10(size)/Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, unitGroup)) + " " + units[unitGroup];
    }

}
