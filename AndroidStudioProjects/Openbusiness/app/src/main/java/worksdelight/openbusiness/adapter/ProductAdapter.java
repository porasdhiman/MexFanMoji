package worksdelight.openbusiness.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import worksdelight.openbusiness.R;

/**
 * Created by worksdelight on 21/07/17.
 */

public class ProductAdapter extends BaseAdapter {
    Context c;
    LayoutInflater inflatore;

    public ProductAdapter(Context c) {
        this.c = c;
        inflatore = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflatore.inflate(R.layout.product_list_item, null);
        return view;
    }
}
