package com.digor.filebrowser.misc;

import java.util.ArrayList;
import java.util.List;

public class FileExplore_API_21_to_26 implements IFileExplore{
    @Override
    public List<State> FolderNavigation( String path) {
        return null;
    }

    @Override
    public List<State> InitializeData() {
        return null;
    }

    private String parentPath;
    @Override
    public String getParentPath(){
        return parentPath;
    }

    private boolean isInitial;
    @Override
    public boolean getIsInitial() {
        return isInitial;
    }
}
