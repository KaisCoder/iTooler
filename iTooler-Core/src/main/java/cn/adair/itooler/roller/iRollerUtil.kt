package cn.adair.itooler.roller

import android.content.res.Resources
import android.support.annotation.IntDef
import android.util.TypedValue

/**
 * cn.adair.itooler.roller
 * Created by Administrator on 2018/9/25/025.
 * slight negligence may lead to great disaster~
 */
object iRollerUtil {

    fun dp2px(dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    fun sp2px(sp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, Resources.getSystem().getDisplayMetrics());
    }


    //文字对齐方式
    const val TEXT_ALIGN_LEFT = 0
    const val TEXT_ALIGN_CENTER = 1
    const val TEXT_ALIGN_RIGHT = 2

    /**
     * 自定义文字对齐方式注解
     */
    @IntDef(TEXT_ALIGN_LEFT, TEXT_ALIGN_CENTER, TEXT_ALIGN_RIGHT)
    @Retention(value = AnnotationRetention.SOURCE)
    annotation class TextAlign

    //弯曲效果对齐方式
    const val CURVED_ARC_DIRECTION_LEFT = 0
    const val CURVED_ARC_DIRECTION_CENTER = 1
    const val CURVED_ARC_DIRECTION_RIGHT = 2

    //分割线填充类型
    const val DIVIDER_TYPE_FILL = 0
    const val DIVIDER_TYPE_WRAP = 1

    /**
     * 自定义左右圆弧效果方向注解
     */
    @IntDef(CURVED_ARC_DIRECTION_LEFT, CURVED_ARC_DIRECTION_CENTER, CURVED_ARC_DIRECTION_RIGHT)
    @Retention(value = AnnotationRetention.SOURCE)
    annotation class CurvedArcDirection

    /**
     * 自定义分割线类型注解
     */
    @IntDef(DIVIDER_TYPE_FILL, DIVIDER_TYPE_WRAP)
    @Retention(value = AnnotationRetention.SOURCE)
    annotation class DividerType


    /**
     * 调整可见条目数为奇数
     * @param visibleItems 可见条目数J
     * @return 调整后的可见条目数
     */
    fun adJustVisibleItems(visibleItems: Int): Int {
        return Math.abs(visibleItems / 2 * 2 + 1) // 当传入的值为偶数时,换算成奇数;
    }


}