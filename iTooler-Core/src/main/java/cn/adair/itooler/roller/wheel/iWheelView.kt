package cn.adair.itooler.roller.wheel

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.media.AudioManager
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.view.ViewConfiguration
import android.widget.OverScroller
import cn.adair.itooler.R
import cn.adair.itooler.roller.iRollerUtil
import java.util.*

/**
 * cn.adair.itooler.picker
 * Created by Administrator on 2018/9/25/025.
 * slight negligence may lead to great disaster~
 */
class iWheelView<T> : View {

    constructor(context: Context) : this(context, null) {}

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttrsAndDefault(context, attrs)
        initValue(context)
    }

    private val DEFAULT_TEXT_SIZE = iRollerUtil.sp2px(15f) // 字体大小
    private val DEFAULT_TEXT_MARGIN = iRollerUtil.dp2px(2f) // margin
    private val DEFAULT_LINE_SPACING = iRollerUtil.dp2px(2f) // 行间距
    private val DEFAULT_DIVIDER_HEIGHT = iRollerUtil.dp2px(1f) // 分割线高度
    private val DEFAULT_DIVIDER_MARGIN = iRollerUtil.dp2px(2f) // Wrap类型下分割线内边距

    val DEFAULT_INTEGER_FORMAT = "%02d" // 默认格式化为两位数
    val DEFAULT_VISIBLE_ITEM = 5 // 可见item 数量
    val DEFAULT_CURVED_FACTOR = 0.75f // 弯曲（3D）效果左右圆弧偏移效果方向系数 偏移系数
    val DEFAULT_REFRACT_RATIO = 0.9f // 默认折射比值，通过字体大小来实现折射视觉差

    val DEFAULT_TEXT_COLOR_NORMAL = Color.DKGRAY    // 未选中字体颜色
    val DEFAULT_TEXT_COLOR_SELECT = Color.BLACK     // 选中时字体颜色

    var mTextSize: Float = 0.0f    //字体大小
    var isAutoFitTextSize: Boolean = false  //是否自动调整字体大小以显示完全
    @iRollerUtil.TextAlign
    var mTextAlign: Int = 0  //文字对齐方式
    var mTextMargin: Float = 0.0f //字体外边距，目的是留有边距
    var mTextColor: Int = 0   //未选中文字颜色
    var mTextColorSelect: Int = 0 // 选中时字体颜色
    var mLineSpacing: Float = 0.0f // 行间距

    var isIntNeedFormat: Boolean = false  //数据为Integer类型时，是否需要格式转换
    lateinit var mIntFormat: String  //数据为Integer类型时，转换格式，默认转换为两位数

    var mVisibleItems: Int = 0 // 可见的item条数
    var mSelectItemPosition: Int = 0    //当前选中的下标
    var mCurrentScrollPosition: Int = 0  //当前滚动经过的下标
    var isCyclic: Boolean = false  //是否循环滚动

    var isShowDivider: Boolean = false //是否显示分割线
    @iRollerUtil.DividerType
    var mDividerType: Int = 0    //分割线填充类型
    var mDividerSize: Float = 0.0f  //分割线高度
    var mDividerColor: Int = 0   //分割线的颜色
    var mDividerPaddingForWrap: Float = 0.0f //分割线类型为DIVIDER_TYPE_WRAP时 分割线左右两端距离文字的间距

    var isDrawSelectRect: Boolean = false  //是否绘制选中区域
    var mSelectRectColor: Int = 0 //选中区域颜色

    //3D效果
    var isCurved: Boolean = false //是否是弯曲（3D）效果
    @iRollerUtil.CurvedArcDirection
    var mCurvedArcDirection: Int = 0 //弯曲（3D）效果左右圆弧偏移效果方向 center 不偏移
    var mCurvedArcDirectionFactor: Float = 0.0f  //弯曲（3D）效果左右圆弧偏移效果系数 0-1之间 越大越明显
    var mCurvedRefractRatio: Float = 0.0f   //弯曲（3D）效果选中后折射的偏移 与字体大小的比值，1为不偏移 越小偏移越明显


    /**
     * 初始化自定义属性及默认值
     */
    @SuppressLint("Recycle")
    private fun initAttrsAndDefault(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.iWheelView)
        mTextSize = typedArray.getDimension(R.styleable.iWheelView_iTextSize, DEFAULT_TEXT_SIZE)
        isAutoFitTextSize = typedArray.getBoolean(R.styleable.iWheelView_isAutoFitTextSize, false)
        mTextAlign = typedArray.getInt(R.styleable.iWheelView_iTextAlign, iRollerUtil.TEXT_ALIGN_CENTER)
        mTextMargin = typedArray.getDimension(R.styleable.iWheelView_iTextMargin, DEFAULT_TEXT_MARGIN)
        mTextColor = typedArray.getColor(R.styleable.iWheelView_iTextColorNormal, DEFAULT_TEXT_COLOR_NORMAL)
        mTextColorSelect = typedArray.getColor(R.styleable.iWheelView_iTextColorSelect, DEFAULT_TEXT_COLOR_SELECT)
        mLineSpacing = typedArray.getDimension(R.styleable.iWheelView_iLineSpacing, DEFAULT_LINE_SPACING)
        isIntNeedFormat = typedArray.getBoolean(R.styleable.iWheelView_isIntNeedFormat, false)
        mIntFormat = typedArray.getString(R.styleable.iWheelView_iIntFormat)
        if (TextUtils.isEmpty(mIntFormat)) {
            mIntFormat = DEFAULT_INTEGER_FORMAT
        }
        // 可见item 数量
        mVisibleItems = typedArray.getInt(R.styleable.iWheelView_iVisibleItems, DEFAULT_VISIBLE_ITEM)
        mVisibleItems = iRollerUtil.adJustVisibleItems(mVisibleItems)
        // 初始化滚动下标
        mSelectItemPosition = typedArray.getInt(R.styleable.iWheelView_iSelectItemPosition, 0)
        mCurrentScrollPosition = mSelectItemPosition
        isCyclic = typedArray.getBoolean(R.styleable.iWheelView_isCyclic, false)
        isShowDivider = typedArray.getBoolean(R.styleable.iWheelView_isShowDivider, false)
        mDividerType = typedArray.getInt(R.styleable.iWheelView_iDividerType, iRollerUtil.DIVIDER_TYPE_FILL)
        mDividerSize = typedArray.getDimension(R.styleable.iWheelView_iDividerHeight, DEFAULT_DIVIDER_HEIGHT)
        mDividerColor = typedArray.getColor(R.styleable.iWheelView_iDividerColor, DEFAULT_TEXT_COLOR_SELECT)
        mDividerPaddingForWrap = typedArray.getDimension(R.styleable.iWheelView_iDividerPaddingForWrap, DEFAULT_DIVIDER_MARGIN)

        isDrawSelectRect = typedArray.getBoolean(R.styleable.iWheelView_isDrawSelectRect, false)
        mSelectRectColor = typedArray.getColor(R.styleable.iWheelView_iSelectRectColor, Color.TRANSPARENT)

        isCurved = typedArray.getBoolean(R.styleable.iWheelView_isCurved, true)
        mCurvedArcDirection = typedArray.getInt(R.styleable.iWheelView_iCurvedArcDirection, iRollerUtil.CURVED_ARC_DIRECTION_CENTER)
        mCurvedArcDirectionFactor = typedArray.getFloat(R.styleable.iWheelView_iCurvedArcDirectionFactor, DEFAULT_CURVED_FACTOR)

        //折射偏移默认值
        mCurvedRefractRatio = typedArray.getFloat(R.styleable.iWheelView_iCurvedRefractRatio, DEFAULT_REFRACT_RATIO)
        if (mCurvedRefractRatio > 1f) {
            mCurvedRefractRatio = 1.0f
        } else if (mCurvedRefractRatio < 0f) {
            mCurvedRefractRatio = DEFAULT_REFRACT_RATIO
        }
        typedArray.recycle()
    }

    var mMaxFlingVelocity: Int = 0
    var mMinFlingVelocity: Int = 0
    lateinit var mOverScroller: OverScroller
    //绘制区域
    lateinit var mDrawRect: Rect
    //3D效果
    lateinit var mCamera: Camera
    lateinit var mMatrix: Matrix
    //音频
    lateinit var mSoundHelper: SoundHelper


    /**
     * 初始化并设置默认值
     */
    private fun initValue(context: Context) {
        val viewConfiguration = ViewConfiguration.get(context)
        mMaxFlingVelocity = viewConfiguration.scaledMaximumFlingVelocity
        mMinFlingVelocity = viewConfiguration.scaledMinimumFlingVelocity
        mOverScroller = OverScroller(context)
        mDrawRect = Rect()
        mCamera = Camera()
        mMatrix = Matrix()
        mSoundHelper = SoundHelper.obtain()
        initDefaultVolume(context)
        calculateTextSize()
        updateTextAlign()
    }

    /**
     * 初始化默认音量
     * @param context 上下文
     */
    private fun initDefaultVolume(context: Context) {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        //获取系统媒体当前音量
        val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        //获取系统媒体最大音量
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        //设置播放音量
        mSoundHelper.setPlayVolume(currentVolume * 1.0f / maxVolume)
    }

    val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    var mDataList: List<T> = ArrayList(1)

    var mMaxTextWidth: Int = 0  //文字的最大宽度
    lateinit var mFontMetrics: Paint.FontMetrics
    var mItemHeight: Int = 0 //每个item的高度


    /**
     * 测量文字最大所占空间
     */
    private fun calculateTextSize() {
        mPaint.setTextSize(mTextSize)
        for (i in mDataList.indices) {
            val textWidth = mPaint.measureText(getDataText(mDataList.get(i))).toInt()
            mMaxTextWidth = Math.max(textWidth, mMaxTextWidth)
        }
        mFontMetrics = mPaint.getFontMetrics()
        //itemHeight实际等于字体高度+一个行间距
        mItemHeight = (mFontMetrics.bottom - mFontMetrics.top + mLineSpacing).toInt()
    }

    /**
     * 获取item text
     * @param item item数据
     * @return 文本内容
     */
    protected fun getDataText(item: T): String {
        if (item == null) {
            return ""
        } else if (item is iWheelEntity) {
            return (item as iWheelEntity)._GetWheelText()
        } else if (item is Int) {
            //如果为整形则最少保留两位数.
            return if (isIntNeedFormat) String.format(Locale.getDefault(), mIntFormat, item) else item.toString()
        } else if (item is String) {
            return item
        }
        return item.toString()
    }

    /**
     * 更新textAlign
     */
    private fun updateTextAlign() {
        when (mTextAlign) {
            iRollerUtil.TEXT_ALIGN_LEFT -> mPaint.textAlign = Paint.Align.LEFT
            iRollerUtil.TEXT_ALIGN_RIGHT -> mPaint.textAlign = Paint.Align.RIGHT
            else -> mPaint.textAlign = Paint.Align.CENTER
        }
    }


}