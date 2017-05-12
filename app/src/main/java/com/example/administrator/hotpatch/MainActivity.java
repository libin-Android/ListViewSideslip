package com.example.administrator.hotpatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;


/**
 *                                listView的侧滑删除
 * Android线上Bug热修复分析
 * Android经常使用的是PathClassLoader和DexClassLoader
 * Android中使用PathClassLoader类作为Android的默认的类加载器，PathClassLoade本身继承自BaseDexClassLoader，BaseDexClassLoader重写了findClass方法，该方法是ClassLoader的核心。
 * 1.PathClassLoader
 *      官方注释：一个简单的ClassLoader的实现，工作在本地文件系统中的文件和目录的列表上，但不尝试从网络加载类。
 *      Android使用这个类为它的系统类加载器和应用类加载器。
 *      Android是使用这个类作为其系统类和应用类的加载器。并且对于这个类呢，只能去加载已经安装到Android系统中的apk文件。
 * 2.DexClassLoader
 *      官方注释：一个ClassLoader的实现，从.jar和.apk文件内部加载classes.dex。这可以用于执行非安装程序作为已安装应用程序的一部分的代码。
 *      也就是说可以加载比如sd目录下的dex文件，获取到不是已安装app里面的类。
 *
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        Button button = (Button) findViewById(R.id.bt);
        Button button2 = (Button) findViewById(R.id.bt2);
        button.setOnClickListener(this);
        button2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent=null;
        switch (v.getId()){
            case R.id.bt:
                intent=new Intent(this,ListViewSlideslip.class);
                break;
            case R.id.bt2:
                intent=new Intent(this,LVSlideslipAndCheckBox.class);
                break;
        }
        startActivity(intent);
    }
}
