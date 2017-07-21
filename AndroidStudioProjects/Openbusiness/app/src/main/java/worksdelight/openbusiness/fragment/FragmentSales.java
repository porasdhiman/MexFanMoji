package worksdelight.openbusiness.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import worksdelight.openbusiness.R;

/**
 * Created by worksdelight on 21/07/17.
 */

public class FragmentSales extends Fragment {
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.current_sale_layout, container, false);






        return v;
    }

    public static FragmentSales newInstance(String text) {

        FragmentSales f = new FragmentSales();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}
