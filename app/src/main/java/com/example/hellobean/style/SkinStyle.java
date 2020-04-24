package com.example.hellobean.style;

import androidx.annotation.NonNull;

//这里定义的皮肤的类型，可以做到很好的扩展，
// 如果之后需要再顶一个 red 的类型的皮肤，那么只需要修改 增加一个枚举类型，及对应的图片资源就可以，
//额外需要修改的地方 会非常少。这个皮肤切换的框架就做得很灵活
public enum SkinStyle {
    DAY(""),
    NIGHT("_night"),
    RED("_red");

    String mSuffix = "";

    SkinStyle(String suffix) {
        this.mSuffix = suffix;
    }

    public String getSuffix() {
        return this.mSuffix;
    }

    @NonNull
    @Override
    public String toString() {
        return "";
    }
}
