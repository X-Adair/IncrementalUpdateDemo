package com.adair.test;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.adair.update.BsUpdateUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diff();
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patch();
            }
        });
        textView = findViewById(R.id.textView);
    }

    private void patch() {
        textView.setText("");
        textView.setText("开始合并");
        PackageManager packageManager = getPackageManager();
        String parentFile = "";
        if(packageManager!=null){
            try {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo("com.adair.test",0);
                parentFile = applicationInfo.sourceDir;
                textView.append("\nparentFile = "+parentFile);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                textView.append("\n获取ApplicationInfo失败");
                textView.append("\n结束");
                return;
            }
        }

        final File file = new File(parentFile+File.separator+"base.apk");
        if(!file.exists()){
            textView.append("\nbase.apk文件不存在");
            textView.append("\n结束");
            return;
        }
        textView.append("\nbase.apk文件存在,可以复制");
        textView.append("\n开始复制");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String dstParentPath = getExternalFilesDir("test").getAbsolutePath();
                File parentFile = new File(dstParentPath);
                if(!parentFile.exists()){
                    parentFile.mkdirs();
                }

                String dstFilePath = dstParentPath+File.separator+"base.apk";
                File dstFile = new File(dstFilePath);

                FileChannel inputChannel = null;
                FileChannel outputChannel = null;
                try {
                    inputChannel = new FileInputStream(file).getChannel();
                    outputChannel = new FileOutputStream(dstFile).getChannel();
                    outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        inputChannel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        outputChannel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.append("\n复制完成");
                        textView.append("\n开始合并");
                    }
                });


                String patchApkPath = dstParentPath+File.separator+"patch.apk";
                File patchFile = new File(patchApkPath);
                if(!patchFile.exists()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.append("\n差分包不存在");
                            textView.append("\n结束");
                        }
                    });

                }

                String newApkFile = dstParentPath+File.separator+"newApkFile-1.apk";

                if(BsUpdateUtils.patchApk(dstFile.getAbsolutePath(),newApkFile,patchApkPath)==0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.append("\n合并结束");
                        }
                    });
                }else{

                } runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.append("\n合并失败");
                    }
                });
            }
        }).start();
    }

    private void diff() {
        textView.setText("");
        textView.setText("开始合并");
        PackageManager packageManager = getPackageManager();
        String parentFile = "";
        if(packageManager!=null){
            try {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo("com.adair.test",0);
                parentFile = applicationInfo.sourceDir;
                textView.append("\nparentFile = "+parentFile);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                textView.append("\n获取ApplicationInfo失败");
                textView.append("\n结束");
                return;
            }
        }

        final File file = new File(parentFile+File.separator+"base.apk");
        if(!file.exists()){
            textView.append("\nbase.apk文件不存在");
            textView.append("\n结束");
            return;
        }
        textView.append("\nbase.apk文件存在,可以复制");
        textView.append("\n开始复制");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String dstParentPath = getExternalFilesDir("test").getAbsolutePath();
                File parentFile = new File(dstParentPath);
                if(!parentFile.exists()){
                    parentFile.mkdirs();
                }

                String dstFilePath = dstParentPath+File.separator+"base.apk";
                File dstFile = new File(dstFilePath);

                FileChannel inputChannel = null;
                FileChannel outputChannel = null;
                try {
                    inputChannel = new FileInputStream(file).getChannel();
                    outputChannel = new FileOutputStream(dstFile).getChannel();
                    outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        inputChannel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        outputChannel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.append("\n复制完成");
                        textView.append("\n开始复制新的Apk文件");
                    }
                });

                String newApkFilePath = "newFile.Apk";
                String newDstApkFile =  dstParentPath+File.separator+"newFile.Apk";
                FileUtils.copyFileFromAssets2Sd(MainActivity.this,newApkFilePath,newDstApkFile);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.append("\n复制新的Apk文件完成");
                        textView.append("\n开始生成差分包");
                    }
                });
                String patchApkPath = dstParentPath+File.separator+"patch.apk";

                if(BsUpdateUtils.diffApk(dstFile.getAbsolutePath(),newDstApkFile,patchApkPath)==0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.append("\n生成差分包完成");
                        }
                    });
                }else{

                } runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.append("\n生成差分包失败");
                    }
                });
            }
        }).start();
    }
}
