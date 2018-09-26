package cn.adair.itooler.roller.wheel;

/**
 * 条目选中监听器
 * com.zyyoona7.wheel
 * Created by Administrator on 2018/9/25/025.
 * slight negligence may lead to great disaster~
 */
public interface OnItemSelectedListener<T> {

    /**
     * 条目选中回调
     *
     * @param wheelView wheelView
     * @param data      选中的数据
     * @param position  选中的下标
     */
    void onItemSelected(iWheelView<T> wheelView, T data, int position);

}
