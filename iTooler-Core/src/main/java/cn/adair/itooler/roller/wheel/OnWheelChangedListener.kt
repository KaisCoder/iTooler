package cn.adair.itooler.roller.wheel

/**
 * WheelView滚动状态改变监听器
 * cn.adair.itooler.roller.wheel
 * Created by Administrator on 2018/9/25/025.
 * slight negligence may lead to great disaster~
 */
interface OnWheelChangedListener {

    /**
     * WheelView 滚动
     * @param scrollOffsetY 滚动偏移
     */
    fun onWheelScroll(scrollOffsetY: Int);

    /**
     * WheelView 条目变化
     * @param oldPosition 旧的下标
     * @param newPosition 新下标
     */
    fun onWheelItemChanged(oldPosition: Int, newPosition: Int)

    /**
     * WheelView 选中
     * @param position 选中的下标
     */
    fun onWheelSelected(position: Int)

    /**
     * WheelView 滚动状态
     * @param state 滚动状态
     * [iWheelView.SCROLL_STATE_IDLE]
     * [iWheelView.SCROLL_STATE_DRAGGING]
     * [iWheelView.SCROLL_STATE_SCROLLING]
     */
    fun onWheelScrollStateChanged(state: Int)

}