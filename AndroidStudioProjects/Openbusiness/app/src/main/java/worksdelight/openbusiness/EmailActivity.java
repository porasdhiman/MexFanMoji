package worksdelight.openbusiness;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by worksdelight on 21/07/17.
 */

public class EmailActivity extends Activity {
    RelativeLayout success_message_layout, send_layout;
    EditText email_ed;
ImageView back_img;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_send_mail);
        email_ed = (EditText) findViewById(R.id.email_ed);
        back_img=(ImageView)findViewById(R.id.back_img);
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        success_message_layout = (RelativeLayout) findViewById(R.id.success_message_layout);
        send_layout = (RelativeLayout) findViewById(R.id.send_layout);
        email_ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (email_ed.getText().length() == 0) {

                    send_layout.setAlpha(0.5f);
                    success_message_layout.setVisibility(View.GONE);
                } else {
                    send_layout.setAlpha(1.0f);
                    send_layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            success_message_layout.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }
}
