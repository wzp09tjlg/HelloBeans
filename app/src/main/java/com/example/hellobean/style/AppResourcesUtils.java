package com.example.hellobean.style;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.DimenRes;

import com.example.hellobean.app.ContextHolder;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

//皮肤的扩展修改，将皮肤的命名规则 定义为 类型的结尾，然后判断根据当前的设置的类型获取相应的资源文件
//修改点 就是判断当前类型是不是默认的类型，如果不是默认类型，那么就从当前类型获取 约定的 suffix
public class AppResourcesUtils {
    private static final String TAG = AppResourcesUtils.class.getSimpleName();

//    public static final String SUFFIX_NIGHT_MODEL = "_night";
    public static final String SUFFIX_PRESS_MODEL = "_press";
    private static SkinStyle mStyle = SkinStyle.DAY;
    private static Resources mResources;
    private static String mPackageName;
    private static Context mContext;
    private static SparseIntArray mResIdMap = new SparseIntArray();
    private static SparseIntArray mResIdInPressMap = new SparseIntArray();
    private static LayoutInflater mInflater;

    public static void init(Context context) {
        mContext = context.getApplicationContext();
        mResources = mContext.getResources();
        mPackageName = mContext.getPackageName();
        setStyle(SkinStyleManager.getInstance().getCurrentSkinStyle());
        mInflater = LayoutInflater.from(mContext);
    }

    static void setStyle(SkinStyle style) {
        mResIdMap.clear();
        mResIdInPressMap.clear();
        mStyle = style;
    }

    public static void clearCacheResId() {
        mResIdMap.clear();
        mResIdInPressMap.clear();
    }

    public static String getResourceNameById(int resId) {
        String name = "";
        if (mResources != null) {
            try {
                name = mResources.getResourceEntryName(resId);
            } catch (Resources.NotFoundException e) {
                name = "";
            }
        }
        return name;
    }

    public static Drawable getDrawable(int resId) {
        return getDrawable(resId, mStyle);
    }

    public static int getResId(String resourceName, String defType) {
        if (getResources() != null && resourceName != null) {
            int resId = mResources.getIdentifier(resourceName, defType, mPackageName);
            return resId;
        } else {
            return 0;
        }
    }

    public static int getInteger(int resId) {
        if (mResources == null) {
            return 0;
        }
        return mResources.getInteger(resId);
    }

    public static int getColor(int resId) {
        return getColor(resId, mStyle);
    }

    private static Drawable getDrawable(int resId, SkinStyle style) {
        if (mResources == null || style == null) {
            return null;
        }
        if (style != SkinStyle.DAY) {
            int cacheResId = mResIdMap.get(resId, -1);
            if (cacheResId != -1) {
                resId = cacheResId;
            } else {
                String resName = getResourceNameById(resId);
                resName = resName + style.getSuffix();
                int resIdInNight = mResources.getIdentifier(resName, "drawable", mPackageName);
                if (resIdInNight != 0) {
                    mResIdMap.put(resId, resIdInNight);
                }
                resId = resIdInNight == 0 ? resId : resIdInNight;
            }
        }

        Drawable d;
        try {
            d = mResources.getDrawable(resId);
        } catch (Throwable e) {
            d = null;
        }
        return d;
    }

    private static int getColor(int resId, SkinStyle style) {
        if (mResources == null || resId <= 0) {
            return 0;
        }
        if (style != SkinStyle.DAY) {
            int cacheResId = mResIdMap.get(resId, -1);
            if (cacheResId != -1) {
                resId = cacheResId;
            } else {
                String resName = getResourceNameById(resId);
                resName = resName + style.getSuffix();
                int resIdInNight = mResources.getIdentifier(resName, "color", mPackageName);
                if (resIdInNight != 0) {
                    mResIdMap.put(resId, resIdInNight);
                }
                resId = resIdInNight == 0 ? resId : resIdInNight;
            }
        }
        int color = 0;
        try {
            color = mResources.getColor(resId);
        } catch (Resources.NotFoundException e) {
            color = 0;
        }
        return color;
    }

    public static String getString(int resId) {
        if (mResources == null || resId <= 0) {
            return "";
        }
        String retStr;
        try {
            retStr = mResources.getString(resId);
        } catch (Resources.NotFoundException e) {
            retStr = "";
        }
        return retStr;
    }

    public static String getString(int resId, Object... format) {
        if (mResources == null || resId <= 0) {
            return "";
        }
        String retStr;
        try {
            retStr = mResources.getString(resId, format);
        } catch (Resources.NotFoundException e) {
            retStr = "";
        }
        return retStr;
    }

    public static int getDimension(int resId) {
        if (mResources == null || resId <= 0) {
            return 0;
        }
        int retDim;
        try {
            retDim = (int) mResources.getDimension(resId);
        } catch (Resources.NotFoundException e) {
            retDim = 0;
        }
        return retDim;
    }

    public static String[] getStringArray(int resId) {
        if (mResources == null || resId <= 0) {
            return null;
        }
        String[] retArray;
        try {
            retArray = mResources.getStringArray(resId);
        } catch (Resources.NotFoundException e) {
            retArray = null;
        }
        return retArray;
    }

    public static boolean getBoolean(int resId) {
        if (mResources == null || resId <= 0) {
            return false;
        }
        boolean retBol;
        try {
            retBol = mResources.getBoolean(resId);
        } catch (Resources.NotFoundException e) {
            retBol = false;
        }
        return retBol;
    }

    public static ColorStateList getColorStateList(int resId) {
        return getColorStateList(resId, mStyle);
    }

    private static ColorStateList getColorStateList(int resId, SkinStyle style) {
        if (getResources() == null || resId <= 0) {
            return null;
        }

        if (style != SkinStyle.DAY) {
            int cacheResId = mResIdMap.get(resId, -1);
            if (cacheResId != -1) {
                resId = cacheResId;
            } else {
                String resName = getResourceNameById(resId);
                resName = resName + style.getSuffix();
                int resIdInNight = mResources.getIdentifier(resName, "drawable", mPackageName);
                if (resIdInNight != 0) {
                    mResIdMap.put(resId, resIdInNight);
                }

                resId = resIdInNight == 0 ? resId : resIdInNight;
            }
        }

        ColorStateList colorStateList = null;
        try {
            colorStateList = mResources.getColorStateList(resId);
        } catch (Resources.NotFoundException e) {
            colorStateList = null;
        }

        return colorStateList;
    }

    public static Resources getResources() {
        if (mResources == null) {
            Context context = ContextHolder.getContext();
            if (context != null) {
                mResources = context.getResources();
                mPackageName = context.getPackageName();
            }
        }
        return mResources;
    }

    public static Bitmap getBitmap(int resId) {
        if (mResources == null || resId <= 0) {
            return null;
        }
        Bitmap retBmp;
        try {
            retBmp = BitmapFactory.decodeResource(mResources, resId);
        } catch (Throwable e) {
            retBmp = null;
        }
        return retBmp;
    }

    public static View inflate(int resId, ViewGroup viewGroup) {
        return mInflater.inflate(resId, viewGroup);
    }

    public static View inflate(int resId, ViewGroup viewGroup, boolean isAttachedRoot) {
        return mInflater.inflate(resId, viewGroup, isAttachedRoot);
    }

    public static Animation loadAnimation(int resId) {
        return AnimationUtils.loadAnimation(mContext, resId);
    }

    public static Animation loadAnimation(Context context, int resId) {
        if (getResources() == null) {
            return null;
        }
        XmlResourceParser parser = null;
        try {
            parser = mResources.getAnimation(resId);
            if (parser == null) {
                return null;
            }
            return createAnimationFromXml(context, parser);
        } catch (Throwable ex) {
            return null;
        } finally {
            if (parser != null)
                parser.close();
        }
    }

    public static Interpolator loadInterpolator(int resId) {
        return AnimationUtils.loadInterpolator(mContext, resId);
    }

    public static LayoutAnimationController loadLayoutAnimation(int resId) {
        return AnimationUtils.loadLayoutAnimation(mContext, resId);
    }

    public static void setBackground(View view, int resId) {
        if (view == null || resId <= 0) {
            return;
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(getDrawable(resId));
        } else {
            view.setBackgroundDrawable(getDrawable(resId));
        }
    }

    public static void setImageSrc(ImageView view, int resId) {
        if (view == null || resId <= 0) {
            return;
        }
        view.setImageDrawable(getDrawable(resId));
    }

    public static void setProgressDrawableSrc(ProgressBar progressBar, int resId) {
        if (progressBar == null || resId <= 0) {
            return;
        }
        progressBar.setProgressDrawable(getDrawable(resId));
    }

    public static void setTextColor(TextView view, int resId) {
        if (view == null || resId <= 0) {
            return;
        }
        view.setTextColor(getColor(resId));
    }

    public static void setTextColor(Button view, int resId) {
        if (view == null || resId <= 0) {
            return;
        }
        view.setTextColor(getColor(resId));
    }

    public static void setTextColorSelector(TextView view, int resId) {
        if (view == null || resId <= 0) {
            return;
        }
        view.setTextColor(getColorStateList(resId));
    }

    public static void setBackgroundColor(View view, int resId) {
        if (view == null || resId <= 0) {
            return;
        }
        view.setBackgroundColor(getColor(resId));
    }

    public static void setHintTextColor(TextView view, int resId) {
        if (view == null || resId <= 0) {
            return;
        }
        view.setHintTextColor(getColor(resId));
    }

    public static void setColorFilter(ImageView view, int resId) {
        view.setColorFilter(getColor(resId));
    }

    public static Drawable getDrawable(String resName) {
        if (mResources == null || TextUtils.isEmpty(resName)) {
            return null;
        }
        String srcResName = resName;
        if (mStyle != SkinStyle.DAY) {
            resName += mStyle.getSuffix();
        }
        int resId = mResources.getIdentifier(resName, "drawable", mPackageName);
        if (resId == 0) {
            resId = mResources.getIdentifier(srcResName, "drawable", mPackageName);
        }
        Drawable d;
        try {
            d = mResources.getDrawable(resId);
        } catch (Throwable e) {
            d = null;
        }
        return d;
    }

    public static String getString(String resName) {
        if (mResources == null || TextUtils.isEmpty(resName)) {
            return null;
        }

        int resId = mResources.getIdentifier(resName, "string", mPackageName);
        String str;
        try {
            str = mResources.getString(resId);
        } catch (Throwable e) {
            str = null;
        }
        return str;
    }

    public static int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                AppResourcesUtils.getResources().getDisplayMetrics());
    }

    //设置有点击态的图标，图标命名格式为xxx_press.png,xxx_press_night.png
    public static void setImageSelector(ImageView view, int resId) {
        if (view == null) {
            return;
        }
        view.setImageDrawable(getDrawableSelector(resId, mStyle));
    }

    public static StateListDrawable getDrawableSelector(int resId, SkinStyle style) {
        if (mResources == null || style == null) {
            return null;
        }
        StateListDrawable bg = new StateListDrawable();
        try {
            int resIdInNormal;
            String resName = getResourceNameById(resId);
            if (style == SkinStyle.DAY) {
                resIdInNormal = resId;
                resName = resName + SUFFIX_PRESS_MODEL;
            } else {
                //获取resIdInNight
                int cacheResId = mResIdMap.get(resId, -1);
                if (cacheResId != -1 && cacheResId != 0) {
                    //从缓存中获取
                    resIdInNormal = cacheResId;
                } else {
                    //从资源中获取
                    int resIdInNight = mResources.getIdentifier(resName + style.getSuffix(), "drawable", mPackageName);
                    if (resIdInNight != 0 && resIdInNight != -1) {
                        resIdInNormal = resIdInNight;
                    } else {
                        resIdInNormal = resId;
                    }
                    //存入缓存
                    mResIdMap.put(resId, resIdInNight);
                }
                resName = resName + SUFFIX_PRESS_MODEL + style.getSuffix();
            }
            //下面为获取resIdInPress
            int resIdInPress;
            int cacheResId = mResIdInPressMap.get(resIdInNormal);
            if (cacheResId != -1 && cacheResId != 0) {
                //从缓存中获取
                resIdInPress = cacheResId;
            } else {
                //从资源中获取
                resIdInPress = mResources.getIdentifier(resName, "drawable", mPackageName);
                if (resIdInPress == 0 || resIdInPress == -1) {
                    resIdInPress = resIdInNormal;
                }
                mResIdInPressMap.put(resIdInNormal, resIdInPress);
            }
            Drawable normal = mResources.getDrawable(resIdInNormal);
            Drawable pressed;
            if (resIdInPress == resIdInNormal) {
                //如果没有找到点击态资源，直接设置半透明
                pressed = normal.getConstantState().newDrawable().mutate();
                //setAlpha方法在api21以下无效
                if (Build.VERSION.SDK_INT < 21) {
                    pressed = getDrawableWithSpecAlpha(pressed, 127);
                } else {
                    pressed.setAlpha(127);
                }
            } else {
                pressed = mResources.getDrawable(resIdInPress);
            }
            bg.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, pressed);
            bg.addState(new int[]{android.R.attr.state_enabled}, normal);
            bg.addState(new int[]{}, normal);
        } catch (Throwable e) {
            bg = null;
        }
        return bg;
    }

    private static Drawable getDrawableWithSpecAlpha(Drawable drawable, int alpha) {
        Drawable dstDrawable = drawable;
        if (drawable instanceof BitmapDrawable) {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            paint.setAlpha(alpha);
            canvas.drawBitmap(((BitmapDrawable) drawable).getBitmap(), 0, 0, paint);
            dstDrawable = new BitmapDrawable(getResources(), bitmap);
        }
        return dstDrawable;
    }

    public static void setTextSize(TextView tv, @DimenRes int resId) {
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, getDimension(resId));
    }

    public static Bitmap getBitmap(String resName) {
        return getBitmap(resName, mStyle);
    }

    private static Animation createAnimationFromXml(Context c, XmlPullParser parser) throws XmlPullParserException, IOException {
        return createAnimationFromXml(c, parser, null, Xml.asAttributeSet(parser));
    }

    private static Animation createAnimationFromXml(Context c, XmlPullParser parser, AnimationSet parent, AttributeSet attrs) throws XmlPullParserException, IOException {
        Animation anim = null;

        // Make sure we are on a start tag.
        int type;
        int depth = parser.getDepth();

        while (((type = parser.next()) != XmlPullParser.END_TAG || parser.getDepth() > depth) && type != XmlPullParser.END_DOCUMENT) {

            if (type != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();
            if (name != null) {
                if (name.equals("set")) {
                    anim = new AnimationSet(c, attrs);
                    createAnimationFromXml(c, parser, (AnimationSet) anim, attrs);
                } else if (name.equals("alpha")) {
                    anim = new AlphaAnimation(c, attrs);
                } else if (name.equals("scale")) {
                    anim = new ScaleAnimation(c, attrs);
                } else if (name.equals("rotate")) {
                    anim = new RotateAnimation(c, attrs);
                } else if (name.equals("translate")) {
                    anim = new TranslateAnimation(c, attrs);
                } else {
                    throw new RuntimeException("Unknown animation name: " + parser.getName());
                }
            }

            if (parent != null && anim != null) {
                parent.addAnimation(anim);
            }
        }

        return anim;

    }

    private static Bitmap getBitmap(String resourceName, SkinStyle style) {
        if (getResources() != null && resourceName != null) {
            String resName = resourceName;
            if (style == SkinStyle.NIGHT) {
                resName = resourceName + "_night";
            }

            int resId = mResources.getIdentifier(resName, "drawable", mPackageName);
            if (resId == 0) {
                resId = mResources.getIdentifier(resourceName, "drawable", mPackageName);
            }

            Bitmap bmp;
            try {
                bmp = BitmapFactory.decodeResource(mResources, resId);
            } catch (Throwable var6) {
                bmp = null;
            }

            return bmp;
        } else {
            return null;
        }
    }
}
