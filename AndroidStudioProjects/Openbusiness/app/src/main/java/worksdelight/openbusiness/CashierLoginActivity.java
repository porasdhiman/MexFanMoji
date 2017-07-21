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

public class CashierLoginActivity extends Activity {
    Button login_btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_owner_login);
        login_btn=(Button)findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(CashierLoginActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
}
