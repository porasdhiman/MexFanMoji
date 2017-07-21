package worksdelight.openbusiness;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

/**
 * Created by worksdelight on 21/07/17.
 */

public class LandingActivity extends Activity {
    Button register_btn,login_btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_layout);
        register_btn=(Button)findViewById(R.id.button);
        login_btn=(Button)findViewById(R.id.button2);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LandingActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LandingActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
