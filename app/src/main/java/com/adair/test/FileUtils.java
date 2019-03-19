package com.adair.test;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * created at 2019/3/19 17:19
 *
 * @author XuShuai
 * @version v1.0
 */
public class FileUtils {
    /**
     * 从从Assets中复制文件目标文件夹
     *
     * @param context 上下文对象
     * @param srcPath 需要复制的文件路径
     * @param dstPath 新的文件路径
     * @return
     */
    public static boolean copyFileFromAssets2Sd(Context context, String srcPath, String dstPath) {
        AssetManager manager = context.getAssets();
        try {
            String[] fileNames = manager.list(srcPath);
            if (fileNames == null) {//正常情况,fileNames不会为空,当为文件时,fileNames.length==0
                return false;
            }

            if (fileNames.length > 0) {//文件夹
                File file = new File(dstPath);
                if (!file.mkdirs()) {
                   Log.e("copy","创建文件夹失败。。。");
                    return false;
                }
                for (String fileName : fileNames) {
                    srcPath = TextUtils.isEmpty(srcPath) ? fileName : (srcPath + File.separator + fileName);
                    copyFileFromAssets2Sd(context, srcPath, dstPath + File.separator + fileName);
                }
            } else {//文件
                InputStream is = manager.open(srcPath);
                FileOutputStream fos = new FileOutputStream(new File(dstPath));
                byte[] buffer = new byte[2048];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
                is.close();
                fos.close();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
