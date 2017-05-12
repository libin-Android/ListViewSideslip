package com.example.administrator.hotpatch;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import com.yydcdut.sdlv.Menu;
import com.yydcdut.sdlv.MenuItem;
import com.yydcdut.sdlv.SlideAndDragListView;

import java.util.ArrayList;
import java.util.List;

import adapters.JavaB;
import adapters.MyAdapter;
import adapters.MyAdapterSLV;
import sdlv.SLVUtils;

public class LVSlideslipAndCheckBox extends AppCompatActivity implements SlideAndDragListView.OnMenuItemClickListener, SlideAndDragListView.OnItemDeleteListener, View.OnClickListener {

    private ArrayList<JavaB> data;
    private SlideAndDragListView slv;
    private CheckBox cb;
    private Menu mMenu;
    private ListView lv;
    private MyAdapterSLV adapterSLV;
    private Button deleteData;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lvslideslip_and_check_box);
        initData();
        initMenu();
        initView();
    }

    /**
     * 必须先设置 lv.setMenu(mMenu);然后在设置lv.setAdapter(myAdapter);
     */
    private void initView() {
        //----
        ((Button) findViewById(R.id.bj)).setOnClickListener(this);
        deleteData = ((Button) findViewById(R.id.delete));
        deleteData.setOnClickListener(this);
        lv = ((ListView) findViewById(R.id.lv));
        myAdapter = new MyAdapter(data, this, R.layout.item);
        lv.setAdapter(myAdapter);
        //---
        slv = ((SlideAndDragListView) findViewById(R.id.lv_edit_cb));
        adapterSLV=new MyAdapterSLV(data,this,R.layout.item_slv);
        slv.setMenu(mMenu);
        slv.setAdapter(adapterSLV);
        slv.setOnMenuItemClickListener(this);
        slv.setOnItemDeleteListener(this);
        cb = ((CheckBox) findViewById(R.id.list_item_cb));
    }

    private void initData() {
        data=new ArrayList<>();
        for (int i = 0; i < 80; i++) {
            JavaB javaBean=new JavaB(i,i+"网球");
            data.add(javaBean);
        }
    }
    /**
     * 初始化侧滑按钮
     */
    public void initMenu() {
        mMenu = new Menu(true, true);
        mMenu.addItem(new MenuItem.Builder().setWidth((int) getResources().getDimension(R.dimen.slv_item_bg_btn_width) * 2)
                .setBackground(SLVUtils.getDrawable(this, R.drawable.btn_left_delete))//设置背景色
                .setText("删除")
                .setTextColor(Color.WHITE)
                .setDirection(MenuItem.DIRECTION_RIGHT)//设置方向，默认为左边
                .setTextSize(14)
                .build());
        mMenu.addItem(new MenuItem.Builder().setWidth((int) getResources().getDimension(R.dimen.slv_item_bg_btn_width)*2)
                .setBackground(SLVUtils.getDrawable(this, R.drawable.btn_left_cancel))
                .setText("取消")
                .setTextColor(Color.WHITE)
                .setDirection(MenuItem.DIRECTION_RIGHT)//设置方向，默认为左边
                .setTextSize(14)
                .build());
    }

    /**
     * 侧滑按钮的监听
     * @param v
     * @param itemPosition
     * @param buttonPosition
     * @param direction
     * @return
     */
    @Override
    public int onMenuItemClick(View v, int itemPosition, int buttonPosition, int direction) {
        switch (direction) {
            case MenuItem.DIRECTION_RIGHT:
                switch (buttonPosition) {
                    case 0:
                        return Menu.ITEM_DELETE_FROM_BOTTOM_TO_TOP;//调用删除
                    case 1:
                        return Menu.ITEM_SCROLL_BACK;//关闭侧滑
                }
        }
        return Menu.ITEM_NOTHING;
    }

    /**
     * 删除的监听
     * @param view
     * @param position
     */
    @Override
    public void onItemDelete(View view, int position) {
        data.remove(position - slv.getHeaderViewsCount());
        adapterSLV.notifyDataSetChanged();
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bj:
                /**
                 * 设置listView和slv的反状态
                 */
                if (lv.isShown()){
                    lv.setVisibility(View.GONE);
                    deleteData.setVisibility(View.GONE);
                    slv.setVisibility(View.VISIBLE);
                    return;
                }
                slv.setVisibility(View.GONE);
                lv.setVisibility(View.VISIBLE);
                lv.setSelection(0);
                deleteData.setVisibility(View.VISIBLE);
                break;
            case R.id.delete:
                /**
                 * 点击删除按钮，删除数据
                 */
                List<JavaB> list=new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    JavaB javaB = data.get(i);
                    if (javaB.isCheck()){
                        list.add(javaB);
                    }
                }
                data.removeAll(list);
                myAdapter.notifyDataSetChanged();
                adapterSLV.notifyDataSetChanged();
                break;
        }
    }
}
