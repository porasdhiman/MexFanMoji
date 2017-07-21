package worksdelight.openbusiness;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import worksdelight.openbusiness.adapter.ProductAdapter;

/**
 * Created by worksdelight on 21/07/17.
 */

public class ProductMenuListActivity extends Activity {
    ListView product_list;
    ImageView menu;
    LinearLayout menu_layout;
    RelativeLayout main_layout;
    TextView all_product_txt, bulk_txt, download_txt, clone_txt, email_txt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_menu_layout);
        all_product_txt = (TextView) findViewById(R.id.all_product_txt);
        bulk_txt = (TextView) findViewById(R.id.bulk_txt);

        download_txt = (TextView) findViewById(R.id.download_txt);

        clone_txt = (TextView) findViewById(R.id.clone_txt);

        email_txt = (TextView) findViewById(R.id.email_txt);

        menu_layout = (LinearLayout) findViewById(R.id.menu_layout);
        main_layout = (RelativeLayout) findViewById(R.id.main_layout);
        menu = (ImageView) findViewById(R.id.menu);
        product_list = (ListView) findViewById(R.id.product_list);
        product_list.setAdapter(new ProductAdapter(this));
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu_layout.setVisibility(View.VISIBLE);
            }
        });
        menu_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                menu_layout.setVisibility(View.GONE);
                return false;
            }
        });

        bulk_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu_layout.setVisibility(View.GONE);
                Intent i = new Intent(ProductMenuListActivity.this, BulkUploadActivity.class);
                startActivity(i);
            }
        });
        download_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu_layout.setVisibility(View.GONE);
                Intent i = new Intent(ProductMenuListActivity.this, BulkUploadActivity.class);
                startActivity(i);
            }
        });
        clone_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu_layout.setVisibility(View.GONE);
                Intent i = new Intent(ProductMenuListActivity.this, ProductCatalogueActivity.class);
                startActivity(i);
            }
        });
        email_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu_layout.setVisibility(View.GONE);
                Intent i = new Intent(ProductMenuListActivity.this, EmailActivity.class);
                startActivity(i);
            }
        });
        all_product_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu_layout.setVisibility(View.GONE);
                Intent i = new Intent(ProductMenuListActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
