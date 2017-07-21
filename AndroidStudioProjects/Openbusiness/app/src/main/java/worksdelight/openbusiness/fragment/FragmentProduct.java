package worksdelight.openbusiness.fragment;

import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import worksdelight.openbusiness.R;
import worksdelight.openbusiness.adapter.ProductAdapter;


/**
 * Created by worksdelight on 21/07/17.
 */

public class FragmentProduct extends Fragment {
    ListView product_list_view;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.product_layout, container, false);

        product_list_view=(ListView)v.findViewById(R.id.product_list);


        product_list_view.setAdapter(new ProductAdapter(getActivity()));

        return v;
    }
    public static FragmentProduct newInstance(String text) {

        FragmentProduct f = new FragmentProduct();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

}
