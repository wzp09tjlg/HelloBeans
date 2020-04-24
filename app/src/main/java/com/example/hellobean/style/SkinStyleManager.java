package com.example.hellobean.style;

import com.example.hellobean.style.listener.SkinChangeListener;

import java.util.ArrayList;
import java.util.List;

//skinStyle 的控制逻辑及设计思想：
//skinStyleManager 仅仅是保存当前的skinStyle的值，如果去实现整个应用的skin的变化，可以基于
public final class SkinStyleManager {

    private static SkinStyleManager sSkinStyleManager = new SkinStyleManager();

    private SkinStyle mCurrentSkinStyle;
    private List<SkinChangeListener> mChangeListenerList = new ArrayList<>();


    private SkinStyleManager() {
    }

    public static SkinStyleManager getInstance() {
        return sSkinStyleManager;
    }

    //在应用启动的时候 应该来读取一般
    public void readLocalSaveSkinStyle() {
        //read Sp 文件或者是 mmkv的内容
        mCurrentSkinStyle = SkinStyle.DAY;//默认为白天模式的资源
    }

    public SkinStyle getCurrentSkinStyle() {
        return this.mCurrentSkinStyle;
    }

    public void setCurrentSkinStyle(SkinStyle newSkinStyle) {
        this.mCurrentSkinStyle = newSkinStyle;
        AppResourcesUtils.setStyle(this.mCurrentSkinStyle);
    }

    //这个方法的调用 是 那个地方做了设置，那么久应该需要再调用这个方法 来发送消息。将设置和发送消息拆分成两个动作来处理
    public void updateSkinStyle() {
        //这里单独来处理消息总线的东西，让PolicyHandler发送一个消息 ，然后针对监听者做整体的调用处理
        if (mChangeListenerList == null) {
            return;
        }

        for (SkinChangeListener listener : mChangeListenerList) {
            listener.onSkinStyleChange();
        }
    }

    public void addChangeListener(SkinChangeListener listener) {
        if (listener == null) {
            return;
        }
        if (mChangeListenerList == null) {
            mChangeListenerList = new ArrayList<>();
        }
        if (!mChangeListenerList.contains(listener)) {
            mChangeListenerList.add(listener);
        }
    }

    public void removeChangeListener(SkinChangeListener listener) {
        if (listener == null) {
            return;
        }
        if (mChangeListenerList == null) {
            mChangeListenerList = new ArrayList<>();
        }
        if (mChangeListenerList.contains(listener)) {
            mChangeListenerList.remove(listener);
        }
    }
}
