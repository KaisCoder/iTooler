package cn.adair.itooler.roller.picker;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.adair.itooler.R;
import cn.adair.itooler.roller.wheel.iWheelView;

/**
 * 年 WheelView
 *
 * @author zyyoona7
 * @version v1.0.0
 * @since 2018/8/20.
 */
public class iYearWheelView extends iWheelView<Integer> {

    private int mStartYear;
    private int mEndYear;

    public iYearWheelView(Context context) {
        this(context, null);
    }

    public iYearWheelView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public iYearWheelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.iWheel_Year);
        mStartYear = typedArray.getInt(R.styleable.iWheel_Year_iStartYear, 1900);
        mEndYear = typedArray.getInt(R.styleable.iWheel_Year_iEndYear, 2100);
        int selectedYear = typedArray.getInt(R.styleable.iWheel_Year_iSelectYear, Calendar.getInstance().get(Calendar.YEAR));
        typedArray.recycle();
        updateYear();
        setSelectedYear(selectedYear);
    }

    /**
     * 设置年份区间
     *
     * @param start 起始年份
     * @param end   结束年份
     */
    public void setYearRange(int start, int end) {
        mStartYear = start;
        mEndYear = end;
        updateYear();
    }

    /**
     * 更新年份数据
     */
    private void updateYear() {
        List<Integer> list = new ArrayList<>(1);
        for (int i = mStartYear; i < mEndYear; i++) {
            list.add(i);
        }
        super.setData(list);
    }


    /**
     * 获取当前选中的年份
     *
     * @return 当前选中的年份
     */
    public int getSelectedYear() {
        return getSelectedItemData();
    }

    /**
     * 设置当前选中的年份
     *
     * @param selectedYear 选中的年份
     */
    public void setSelectedYear(int selectedYear) {
        setSelectedYear(selectedYear, false);
    }

    /**
     * 设置当前选中的年份
     *
     * @param selectedYear   选中的年份
     * @param isSmoothScroll 是否平滑滚动
     */
    public void setSelectedYear(int selectedYear, boolean isSmoothScroll) {
        setSelectedYear(selectedYear, isSmoothScroll, 0);
    }

    /**
     * 设置当前选中的年份
     *
     * @param selectedYear   选中的年份
     * @param isSmoothScroll 是否平滑滚动
     * @param smoothDuration 平滑滚动持续时间
     */
    public void setSelectedYear(int selectedYear, boolean isSmoothScroll, int smoothDuration) {
        if (selectedYear >= mStartYear && selectedYear <= mEndYear) {
            updateSelectedYear(selectedYear, isSmoothScroll, smoothDuration);
        }
    }

    /**
     * 更新选中的年份
     *
     * @param selectedYear   选中的年
     * @param isSmoothScroll 是否平滑滚动
     * @param smoothDuration 平滑滚动持续时间
     */
    private void updateSelectedYear(int selectedYear, boolean isSmoothScroll, int smoothDuration) {
        setSelectedItemPosition(selectedYear - mStartYear, isSmoothScroll, smoothDuration);
    }

    @Override
    public void setData(List<Integer> dataList) {
        throw new UnsupportedOperationException("You can not invoke setData method in " + iYearWheelView.class.getSimpleName() + ".");
    }
}
