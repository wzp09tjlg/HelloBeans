package com.example.hellobean.business.skin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.hellobean.R;
import com.example.hellobean.style.AppResourcesUtils;
import com.example.hellobean.style.SkinStyle;
import com.example.hellobean.style.SkinStyleManager;
import com.example.hellobean.style.listener.SkinChangeListener;

//测试皮肤的activity
public class SkinTestActivity extends Activity implements View.OnClickListener, SkinChangeListener {

    private LinearLayout mLayoutTestSkin;

    private Button mChangeSkinStyle;
    private ImageView mTestSkinImage;
    private TextView mTestSkinText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin_test);
        initView();
        initData();
    }

    private void initView() {
        mLayoutTestSkin = findViewById(R.id.layout_test_skin);

        mChangeSkinStyle = findViewById(R.id.btn_test_skin);
        mTestSkinImage = findViewById(R.id.img_test_skin);
        mTestSkinText = findViewById(R.id.tv_test_skin);
    }

    private void initData() {
        SkinStyleManager.getInstance().addChangeListener(this);

        mChangeSkinStyle.setOnClickListener(this);

        onSkinStyleChange();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test_skin:
                changeSkinStyleChange();
                break;
        }
    }

    @Override
    public void onSkinStyleChange() {
        AppResourcesUtils.setBackgroundColor(mLayoutTestSkin, R.color.root_view_background);

        AppResourcesUtils.setBackgroundColor(mChangeSkinStyle, R.color.button_background_color);
        AppResourcesUtils.setTextColor(mChangeSkinStyle, R.color.button_text_color);

        AppResourcesUtils.setImageSrc(mTestSkinImage, R.drawable.ic_launcher_background);

        AppResourcesUtils.setTextColor(mTestSkinText, R.color.text_view_text_color);
    }

    private void changeSkinStyleChange() {
        SkinStyle changeToSkinStyle;
        SkinStyle currentSkinStyle = SkinStyleManager.getInstance().getCurrentSkinStyle();
        if (currentSkinStyle == SkinStyle.DAY) {
            changeToSkinStyle = SkinStyle.NIGHT;
            SkinStyleManager.getInstance().setCurrentSkinStyle(changeToSkinStyle);
            SkinStyleManager.getInstance().updateSkinStyle();
        } else if (currentSkinStyle == SkinStyle.NIGHT) {
            changeToSkinStyle = SkinStyle.RED;
            SkinStyleManager.getInstance().setCurrentSkinStyle(changeToSkinStyle);
            SkinStyleManager.getInstance().updateSkinStyle();
        } else if (currentSkinStyle == SkinStyle.RED) {
            changeToSkinStyle = SkinStyle.DAY;
            SkinStyleManager.getInstance().setCurrentSkinStyle(changeToSkinStyle);
            SkinStyleManager.getInstance().updateSkinStyle();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinStyleManager.getInstance().removeChangeListener(this);
    }
}
