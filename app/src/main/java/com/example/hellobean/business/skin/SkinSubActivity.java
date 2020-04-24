package com.example.hellobean.business.skin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.hellobean.R;
import com.example.hellobean.style.AppResourcesUtils;
import com.example.hellobean.style.SkinStyleManager;
import com.example.hellobean.style.listener.SkinChangeListener;

//就是测验skin的框架的测试页面
public class SkinSubActivity extends Activity implements View.OnClickListener, SkinChangeListener {

    private LinearLayout mLayoutSubSkin;

    private Button mGoChangeSkinStyle;
    private ImageView mSubSkinImage;
    private TextView mSubSkinText;
    private ListView mSubListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin_sub);
        initView();
        initData();
    }

    private void initView() {
        mLayoutSubSkin = findViewById(R.id.layout_sub_skin);

        mGoChangeSkinStyle = findViewById(R.id.btn_sub_skin);
        mSubSkinImage = findViewById(R.id.img_sub_skin);
        mSubSkinText = findViewById(R.id.tv_sub_skin);
        mSubListView = findViewById(R.id.list_sub_skin);
    }

    private void initData() {
        SkinStyleManager.getInstance().addChangeListener(this);

        mGoChangeSkinStyle.setOnClickListener(this);

        onSkinStyleChange();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sub_skin:
                goSkinStyleChangeActivity();
                break;
        }
    }

    @Override
    public void onSkinStyleChange() {
        AppResourcesUtils.setBackgroundColor(mLayoutSubSkin, R.color.root_view_background);

        AppResourcesUtils.setBackgroundColor(mGoChangeSkinStyle, R.color.button_background_color);
        AppResourcesUtils.setTextColor(mGoChangeSkinStyle, R.color.button_text_color);

        AppResourcesUtils.setImageSrc(mSubSkinImage, R.drawable.ic_launcher_background);

        AppResourcesUtils.setTextColor(mSubSkinText, R.color.text_view_text_color);

        //mSubListView.setAdapter();
    }

    private void goSkinStyleChangeActivity() {
        Intent goChangeIntent = new Intent(this, SkinTestActivity.class);
        startActivity(goChangeIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinStyleManager.getInstance().removeChangeListener(this);
    }
}
