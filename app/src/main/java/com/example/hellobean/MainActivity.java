package com.example.hellobean;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hellobean.business.skin.SkinSubActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mHelloTv;
    private Button mHelloBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        mHelloTv = findViewById(R.id.tv_hello);
        mHelloBtn = findViewById(R.id.btn_hello);
    }

    private void initData() {
        mHelloTv.setText("hello Bean!");
        mHelloBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_hello:
                //testHelloBtn();
                goSubSkinActivity();
                break;
        }
    }

    private void testHelloBtn() {
        Toast.makeText(this, "Hello Bean,I am here", Toast.LENGTH_SHORT).show();
    }

    private void goSubSkinActivity() {
        Intent goIntent = new Intent(this, SkinSubActivity.class);
        startActivity(goIntent);
    }

}
