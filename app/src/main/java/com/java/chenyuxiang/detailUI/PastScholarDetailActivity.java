package com.java.chenyuxiang.detailUI;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;

public class PastScholarDetailActivity extends ScholarDetailActivity {
    @Override
    protected void setColor() {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);//饱和度 0灰色 100过度彩色，50正常
        filter = new ColorMatrixColorFilter(matrix);
    }
}
