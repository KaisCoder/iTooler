package cn.adair.itooler.roller.wheel;

/**
 * WheelView滚动状态改变监听器
 * com.zyyoona7.wheel
 * Created by Administrator on 2018/9/25/025.
 * slight negligence may lead to great disaster~
 */
public interface OnWheelChangedListener {

    /**
     * WheelView 滚动
     *
     * @param scrollOffsetY 滚动偏移
     */
    void onWheelScroll(int scrollOffsetY);

    /**
     * WheelView 条目变化
     *
     * @param oldPosition 旧的下标
     * @param newPosition 新下标
     */
    void onWheelItemChanged(int oldPosition, int newPosition);

    /**
     * WheelView 选中
     *
     * @param position 选中的下标
     */
    void onWheelSelected(int position);

    /**
     * WheelView 滚动状态
     *
     * @param state 滚动状态
     *              {@link iWheelView#SCROLL_STATE_IDLE}
     *              {@link iWheelView#SCROLL_STATE_DRAGGING}
     *              {@link iWheelView#SCROLL_STATE_SCROLLING}
     */
    void onWheelScrollStateChanged(int state);

}
