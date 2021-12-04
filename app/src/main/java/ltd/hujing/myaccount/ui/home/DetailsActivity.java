package ltd.hujing.myaccount.ui.home;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ltd.hujing.myaccount.R;

public class DetailsActivity extends AppCompatActivity {
    private TextView details_tv_amount, details_tv_typename, details_tv_account_type, details_tv_time,
            details_tv_balance, details_tv_description;
    private ImageView details_iv_type;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ActionBar actionBar = getSupportActionBar();   //隐藏自带标题栏
        if(actionBar != null){
            actionBar.hide();
        }
        BindView();    //绑定按钮，设置数据


    }

    private void BindView(){
        details_tv_amount = findViewById(R.id.details_tv_amount);
        details_tv_typename = findViewById(R.id.details_tv_typename);
        details_tv_account_type = findViewById(R.id.details_tv_account_type);
        details_tv_time = findViewById(R.id.details_tv_time);
        details_tv_balance = findViewById(R.id.details_tv_balance);
        details_tv_description = findViewById(R.id.details_tv_description);
        details_iv_type = findViewById(R.id.details_iv_type);
        intent = getIntent();

        details_tv_amount.setText(intent.getStringExtra("details_tv_amount"));
        details_tv_typename.setText(intent.getStringExtra("details_tv_typename"));
        details_tv_account_type.setText(intent.getStringExtra("details_tv_account_type"));
        details_tv_time.setText(intent.getStringExtra("details_tv_time"));
        details_tv_balance.setText(intent.getStringExtra("details_tv_balance"));
        details_tv_description.setText(intent.getStringExtra("details_tv_description"));
        details_iv_type.setImageResource(intent.getIntExtra("details_iv_type", 0));
    }

    //返回的逻辑实现
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.details_back:
                finish();
                break;
        }
    }
}