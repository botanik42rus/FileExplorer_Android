package com.digor.filebrowser.misc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.digor.filebrowser.BuildConfig;
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
import java.util.Locale;

public class FileExploreAfterAPI26 implements IFileExplore {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public List<State> FolderNavigation( String path) {
        List<State> newStateArray = new ArrayList<State>();
        File CurrentItem = new File(path);
        if(CurrentItem == null) return null;

        if(CurrentItem.isFile()){
            try {

                MimeTypeMap myMime = MimeTypeMap.getSingleton();
                String mimeType = myMime.getMimeTypeFromExtension(FileExtensions(CurrentItem.getPath()));

                Intent newIntent = new Intent(Intent.ACTION_VIEW);
                newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                newIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                Uri myUri = FileProvider.getUriForFile(MainActivity.mainContext, BuildConfig.APPLICATION_ID, CurrentItem);
                newIntent.setDataAndType(myUri,mimeType);

                MainActivity.mainContext.startActivity(newIntent);
            }
            catch (Exception e){
                Log.i("myLog", e.toString());
            }

            return null;
        }

        try{
            newStateArray.add(new State("","","",MainActivity.mainContext.getDrawable(R.drawable.return_ic), CurrentItem.getParent()));

            if(CurrentItem.list() == null){
                Log.i("myLog", "list == null");
                return InitializeData();
            }

            for (String file : CurrentItem.list()){
                Path currentItemPath = Paths.get(path + "/"+file);
                MimeTypeMap myMime = MimeTypeMap.getSingleton();
                String fileMimeType = myMime.getMimeTypeFromExtension(FileExtensions(currentItemPath.toString()));
                BasicFileAttributes currentItemAttr = Files.readAttributes(currentItemPath,BasicFileAttributes.class);;
                long fileSize = Files.size(currentItemPath);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String currentItemLastModifiedDate = sdf.format(currentItemPath.toFile().lastModified());

                newStateArray.add(new State(file,
                        currentItemAttr.isDirectory() ? "" : FormatBytes(fileSize),
                        currentItemLastModifiedDate != null ? currentItemLastModifiedDate : "",
                        getImageIcon(currentItemAttr,currentItemPath.toFile(),fileMimeType),
                        currentItemPath.toString()));
            }
        }
        catch (Exception e){
            newStateArray = null;
            Log.i("myLog", e.toString() + "!!!");
        }

        return newStateArray;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Drawable getImageIcon(BasicFileAttributes attr, File currentFile, String mimeType){
        Drawable newDrawable = MainActivity.mainContext.getDrawable(R.drawable.empty_file_ic);;
        if (attr.isDirectory())
            newDrawable = MainActivity.mainContext.getDrawable(R.drawable.folder_ic);
        else if(mimeType == null){}
        else if(mimeType.contains("image")){
            Bitmap bmp = BitmapFactory.decodeFile(currentFile.getAbsolutePath());
            newDrawable = new BitmapDrawable(MainActivity.mainContext.getResources(), bmp);
        }
        else if(mimeType.contains("pdf")){
            newDrawable = MainActivity.mainContext.getDrawable(R.drawable.pdf_ic);
        }
        else if(mimeType.contains("package-archive")){
            newDrawable = MainActivity.mainContext.getDrawable(R.drawable.apk_ic);
        }
        else if(mimeType.contains("text")){
            newDrawable = MainActivity.mainContext.getDrawable(R.drawable.file_ic);
        }
        return newDrawable;
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
            arrFiles.add(new State(filesToStorage.getName(),"","", MainActivity.mainContext.getDrawable(R.drawable.sd_card_ic), filesToStorage.getPath()));
        }
        return arrFiles;
    }


    public String FormatBytes(long size){
        if(size <= 0) return "0 B";
        String[] units = new String[]{"B","KB", "MB", "GB", "TB"};
        int unitGroup = (int) (Math.log10(size)/Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, unitGroup)) + " " + units[unitGroup];
    }

    private  String FileExtensions(String url){
        if(url.indexOf("?") > -1){
            url = url.substring(0,url.indexOf("?"));
        }
        if(url.lastIndexOf(".") == -1){
            return null;
        }
        else {
            String ext = url.substring(url.lastIndexOf(".") + 1);
            if(ext.indexOf("%")>-1){
                ext = ext.substring(0,ext.indexOf("%"));
            }
            if(ext.indexOf("/")>-1){
                ext = ext.substring(0,ext.indexOf("/"));
            }
            return ext.toLowerCase();
        }
    }
}
