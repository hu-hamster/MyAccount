package ltd.hujing.myaccount.ui.additem;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

import ltd.hujing.myaccount.R;

public class AddItemActivity extends AppCompatActivity implements View.OnClickListener{
    //定义imagebutton列表
    private ImageButton imageButtons[] = new ImageButton[18];
    private final int[] image_button_list = {R.id.home_add_daily,R.id.home_add_food,R.id.home_add_fruit,
            R.id.home_add_fund,R.id.home_add_game,R.id.home_add_gift, R.id.home_add_home,R.id.home_add_garb,
            R.id.home_add_shopping,R.id.home_add_drink,R.id.home_add_telephone,R.id.home_add_sports,
            R.id.home_add_vehicle,R.id.home_add_travel, R.id.home_add_other,R.id.home_add_salary,
            R.id.home_add_pocket,R.id.home_add_transfer};
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        //定义button事件
        for(int i = 0;i<18;i++){
            imageButtons[i] = (ImageButton) findViewById(image_button_list[i]);
            imageButtons[i].setOnClickListener(this);
        }
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        //支出事件
        for(int i = 0; i < 15; i++){
            if(v.getId()==image_button_list[i]){

            }
        }
        //收入事件
        for(int i = 15; i < 18; i++){
            if(v.getId()==image_button_list[i]){

            }
        }
    }
}