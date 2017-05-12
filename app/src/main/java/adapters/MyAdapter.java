package adapters;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import com.example.administrator.hotpatch.R;
import java.util.List;

/**
 * checkBox防错乱
 */
public class MyAdapter extends MyBaseAdapter<JavaB> {
    private List<JavaB> data;
    private String TAG= MyAdapter.class.getSimpleName();
    private CheckBox view;

    public MyAdapter(List<JavaB> data, Context context, int layoutRes) {
        super(data, context, layoutRes);
        this.data=data;
    }

    @Override
    public void bindData(ViewHolder holder, JavaB javaBean, final int position) {
        ((TextView) holder.getView(R.id.list_item_textName)).setText(javaBean.getName());
        ((TextView) holder.getView(R.id.list_item_textAge)).setText(javaBean.getAge()+"");
        view = (CheckBox) holder.getView(R.id.list_item_cb);
        view.setChecked(data.get(position).isCheck());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.get(position).isCheck()){
                    data.get(position).setCheck(false);
                }else {
                    data.get(position).setCheck(true);
                }
            }
        });
    }
}
