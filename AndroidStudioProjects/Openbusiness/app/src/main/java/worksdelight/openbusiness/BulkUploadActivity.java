package worksdelight.openbusiness;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.wang.avi.AVLoadingIndicatorView;

/**
 * Created by worksdelight on 21/07/17.
 */

public class BulkUploadActivity extends Activity {
    AVLoadingIndicatorView loader;
    ImageView back_img;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bulk_upload_layout);
        back_img=(ImageView)findViewById(R.id.back_img);
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        loader=(AVLoadingIndicatorView)findViewById(R.id.loader_view);
        loader.setIndicatorColor(getResources().getColor(R.color.app_color));
        loader.show();
    }
}
