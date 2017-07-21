package worksdelight.openbusiness;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * Created by worksdelight on 21/07/17.
 */

public class LoginActivity extends Activity {
    RelativeLayout cashier_layout,business_layout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        business_layout=(RelativeLayout) findViewById(R.id.business_layout);
        cashier_layout=(RelativeLayout) findViewById(R.id.cashier_layout);
        business_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,BusinessLoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        cashier_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,CashierLoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
