package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/8.
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {
    private List<T> data;
    //布局导入器
    private LayoutInflater inflater;
    //布局id
    private int layoutRes;
    public MyBaseAdapter(List<T> data, Context context, int layoutRes) {
        this.data=data;
        inflater= LayoutInflater.from(context);
        this.layoutRes=layoutRes;
        if (data==null){
            //如果数据源为空就实例化数据源
            this.data= new ArrayList<>();
        }else{
            this.data=data;
        }
    }

    @Override
    public int getCount() {
        return data!=null?data.size():0;
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView=inflater.inflate(layoutRes,null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder  = (ViewHolder) convertView.getTag();
        }
        //加载数据
        bindData(holder,getItem(position),position);
        return convertView;
    }
    public abstract void bindData(ViewHolder holder,T t,int position);

    /**
     * 创建自定义的ViewHolder
     */
    protected static class ViewHolder{
        private View layout;
        private Map<Integer,View> cacheView;

        public ViewHolder(View convertView) {
            this.layout=convertView;
            cacheView=new HashMap<>();
        }
        public View getView(int resId){
            View view=null;
            //如果map中有我们的资源id，直接根据key获得view
            if (cacheView.containsKey(resId)){
                view=cacheView.get(resId);
            }else {
                //map中不包含resid就实例化一个view，并添加到map缓存中
                view = layout.findViewById(resId);
                cacheView.put(resId,view);
            }
            return view;
        }
    }
}
