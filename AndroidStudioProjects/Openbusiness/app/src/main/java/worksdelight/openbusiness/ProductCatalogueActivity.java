package worksdelight.openbusiness;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by worksdelight on 21/07/17.
 */

public class ProductCatalogueActivity extends Activity {
    RelativeLayout clone_view,success_message_layout;
    ImageView back_img;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_catelogue_layout);
        back_img=(ImageView)findViewById(R.id.back_img);
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        clone_view=(RelativeLayout)findViewById(R.id.clone_view);
        success_message_layout=(RelativeLayout)findViewById(R.id.success_message_layout);

        clone_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                success_message_layout.setVisibility(View.VISIBLE);
            }
        });

    }
}
