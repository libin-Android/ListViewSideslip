package com.example.administrator.hotpatch;

import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yydcdut.sdlv.Menu;
import com.yydcdut.sdlv.MenuItem;
import com.yydcdut.sdlv.SlideAndDragListView;

import java.util.List;

import sdlv.SLVUtils;

public class ListViewSlideslip extends AppCompatActivity implements SlideAndDragListView.OnSlideListener, SlideAndDragListView.OnMenuItemClickListener, SlideAndDragListView.OnItemDeleteListener {

    private List<ApplicationInfo> data;//数据源
    private Menu mMenu;
    private SlideAndDragListView mListView;//侧滑的listView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_slideslip);
        initData();
        initMenu();
        initUiAndListener();
    }

    /**
     * 初始化数据
     */
    public void initData() {
        data = getPackageManager().getInstalledApplications(0);
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
        mMenu.addItem(new MenuItem.Builder().setWidth((int) getResources().getDimension(R.dimen.slv_item_bg_btn_width) *2)
                .setBackground(SLVUtils.getDrawable(this, R.drawable.btn_left_delete))
                .setText("不做处理")
                .setTextColor(Color.BLACK)
                .setTextSize(14)
                .build());
        mMenu.addItem(new MenuItem.Builder().setWidth((int) getResources().getDimension(R.dimen.slv_item_bg_btn_width)*2)
                .setBackground(SLVUtils.getDrawable(this, R.drawable.btn_left_cancel))
                .setIcon(getResources().getDrawable(R.mipmap.ic_launcher))
                .build());
    }

    /**
     * 初始化监听
     */
    public void initUiAndListener() {
        mListView = (SlideAndDragListView) findViewById(R.id.lv_edit);
        mListView.setMenu(mMenu);
        mListView.setAdapter(mAdapter);
//        mListView.setOnListItemLongClickListener(this);
//        mListView.setOnDragListener(this, mAppList);
//        mListView.setOnListItemClickListener(this);
        mListView.setOnSlideListener(this);
        mListView.setOnMenuItemClickListener(this);
        mListView.setOnItemDeleteListener(this);
    }

    /**
     * 自定义的Adapter
     */
    private BaseAdapter mAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CustomViewHolder cvh;
            if (convertView == null) {
                cvh = new CustomViewHolder();
                convertView = LayoutInflater.from(ListViewSlideslip.this).inflate(R.layout.item_custom, null);
                cvh.imgLogo = (ImageView) convertView.findViewById(R.id.img_item_edit);
                cvh.txtName = (TextView) convertView.findViewById(R.id.txt_item_edit);
                cvh.imgLogo2 = (ImageView) convertView.findViewById(R.id.img_item_edit2);
                convertView.setTag(cvh);
            } else {
                cvh = (CustomViewHolder) convertView.getTag();
            }
            ApplicationInfo item = (ApplicationInfo) this.getItem(position);
            cvh.txtName.setText(item.loadLabel(getPackageManager()));
            cvh.imgLogo.setImageDrawable(item.loadIcon(getPackageManager()));
            cvh.imgLogo2.setImageDrawable(item.loadIcon(getPackageManager()));
            return convertView;
        }

        class CustomViewHolder {
            public ImageView imgLogo;
            public TextView txtName;
            public ImageView imgLogo2;
        }
    };

    /**
     * mListView.setOnSlideListener(this);侧滑的回调
     * @param view
     * @param view1
     * @param i
     * @param i1
     */
    @Override
    public void onSlideOpen(View view, View view1, int i, int i1) {
        //当开始侧滑时的回调
    }
    @Override
    public void onSlideClose(View view, View view1, int i, int i1) {
        //当侧滑关闭时的回调
    }

    /**
     *   mListView.setOnMenuItemClickListener(this);的回调
     *   方向右边为1，左边为-1
     * @return
     */
    @Override
    public int onMenuItemClick(View v, int itemPosition, int buttonPosition, int direction) {
        switch (direction) {
            case MenuItem.DIRECTION_LEFT:
                switch (buttonPosition) {
                    case 0:
                        return Menu.ITEM_NOTHING;//不做处理
                    case 1:
                        return Menu.ITEM_NOTHING;//关闭侧滑
                }
                break;
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
     * mListView.setOnItemDeleteListener(this);的回调
     * @param view
     */
    @Override
    public void onItemDelete(View view, int position) {
        data.remove(position - mListView.getHeaderViewsCount());
        mAdapter.notifyDataSetChanged();
    }
}
