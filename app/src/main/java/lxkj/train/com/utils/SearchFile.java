package lxkj.train.com.utils;

import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lxkj.train.com.R;
import lxkj.train.com.entity.DirectoryOrFileEntity;
import lxkj.train.com.overall.OverallData;

/**
 * Created by dell on 2018/6/12.
 */

public class SearchFile {
    public List<DirectoryOrFileEntity> search(File fileold, List<DirectoryOrFileEntity> mdirectoryOrFileEntitys) {
        try {
            File[] files = fileold.listFiles();
            if (files.length>0) {
                for (int i = 0; i < files.length; i++) {
                    DirectoryOrFileEntity directoryOrFileEntity = new DirectoryOrFileEntity();
                    if (files[i].isDirectory()) { //如果是文件夹
                        directoryOrFileEntity.setType("directory");
                        directoryOrFileEntity.setImage(R.mipmap.image_directory);
                        List<DirectoryOrFileEntity> childDirectoryOrFileEntities = new ArrayList<>();
                        search(files[i], childDirectoryOrFileEntities);
                        directoryOrFileEntity.setDirectoryOrFileEntities(childDirectoryOrFileEntities);
                    } else {
                        directoryOrFileEntity.setImage(R.mipmap.image_file);
                        directoryOrFileEntity.setType("file");
                        LogUtils.i("filesLength", fileold.getAbsolutePath() + "---" + files[i].getName());
                    }
                    directoryOrFileEntity.setName(files[i].getName());
                    directoryOrFileEntity.setPath(files[i].getAbsolutePath());
                    directoryOrFileEntity.setReadTag(View.VISIBLE);
                    mdirectoryOrFileEntitys.add(directoryOrFileEntity);
                }
                return mdirectoryOrFileEntitys;
            }
        } catch (Exception e) {
            LogUtils.i("",""+e.getMessage());
            //Toast.makeText(OverallData.app, "查找失败" + "---" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return mdirectoryOrFileEntitys;
    }
    public List<DirectoryOrFileEntity> searchFile(File fileold, List<DirectoryOrFileEntity> mdirectoryOrFileEntitys) {
        try {
            File[] files = fileold.listFiles();
            if (files.length>0) {
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isFile()) {
                        DirectoryOrFileEntity directoryOrFileEntity = new DirectoryOrFileEntity();
                        directoryOrFileEntity.setImage(R.mipmap.image_file);
                        directoryOrFileEntity.setType("file");
                        directoryOrFileEntity.setName(files[i].getName());
                        directoryOrFileEntity.setPath(files[i].getAbsolutePath());
                        directoryOrFileEntity.setReadTag(View.VISIBLE);
                        mdirectoryOrFileEntitys.add(directoryOrFileEntity);
                    }
                }
            }
        } catch (Exception e) {
            //Toast.makeText(OverallData.app, "没有找到文件", Toast.LENGTH_SHORT).show();
        }
        return mdirectoryOrFileEntitys;
    }
}
