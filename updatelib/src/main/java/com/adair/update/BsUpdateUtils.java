package com.adair.update;

/**
 *
 * 增量更新工具类
 *
 * created at 2019/3/19 16:29
 *
 * @author XuShuai
 * @version v1.0
 */
public class BsUpdateUtils {
    static {
        System.loadLibrary("update-lib");
    }

    /**
     * 合并差分包
     * @param oldFilePath 旧Apk文件地址
     * @param newFilePath 新Apk文件地址
     * @param patchPath 差分包文件地址
     * @return true 合并成功
     */
    public native static int patchApk(String oldFilePath,String newFilePath,String patchPath);

    /**
     * 生成差分包
     * @param oldFilePath 旧Apk文件地址
     * @param newFilePath 新Apk文件地址
     * @param patchPath 差分包文件地址
     * @return true 生成差分包成功
     */
    public native static int diffApk(String oldFilePath,String newFilePath,String patchPath);
}
