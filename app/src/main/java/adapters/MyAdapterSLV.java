package adapters;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.administrator.hotpatch.R;

import java.util.List;
public class MyAdapterSLV extends MyBaseAdapter<JavaB> {
    private List<JavaB> data;
    private String TAG= MyAdapterSLV.class.getSimpleName();
    private CheckBox view;

    public MyAdapterSLV(List<JavaB> data, Context context, int layoutRes) {
        super(data, context, layoutRes);
        this.data=data;
    }

    @Override
    public void bindData(ViewHolder holder, JavaB javaBean, final int position) {
        ((TextView) holder.getView(R.id.list_slv_item_textName)).setText(javaBean.getName());
        ((TextView) holder.getView(R.id.list_slv_item_textAge)).setText(javaBean.getAge()+"");
    }
}
