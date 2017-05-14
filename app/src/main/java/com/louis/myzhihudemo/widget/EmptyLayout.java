package com.louis.myzhihudemo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.louis.myzhihudemo.ui.R;
import com.louis.myzhihudemo.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by louis on 17-4-12.
 */

public class EmptyLayout extends FrameLayout {
    public static final int STATUS_HIDE = 1001;
    public static final int STATUS_LOADING = 1;
    public static final int STATUS_NO_NET = 2;
    public static final int STATUS_NO_DATA = 3;
    private int mEmptyStatus = STATUS_LOADING;
    private OnRetryListener mOnRetryListener;

    private Context mContext;
    private int mBgColor;

    @BindView(R.id.empty_layout)
    FrameLayout mEmptyLayout;
    @BindView(R.id.tv_net_error)
    TextView mTvEmptyMessage;
    @BindView(R.id.rl_empty_container)
    View mRlEmptyContainer;

    public EmptyLayout(Context context) {
        this(context, null);
    }

    public EmptyLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.EmptyLayout);
        try {
            mBgColor = a.getColor(R.styleable.EmptyLayout_background_color, Color.WHITE);
        } finally {
            a.recycle();
        }

        View.inflate(mContext, R.layout.layout_empty_loading, this);
        ButterKnife.bind(this);
        mEmptyLayout.setBackgroundColor(mBgColor);
        switchEmptyView();

    }

    /**
     * 设置状态
     *
     * @param emptyStatus
     */
    public void setEmptyStatus(int emptyStatus) {
        mEmptyStatus = emptyStatus;
        switchEmptyView();
    }

    public void switchEmptyView() {
        switch (mEmptyStatus) {
            case STATUS_LOADING:
                setVisibility(VISIBLE);
                mRlEmptyContainer.setVisibility(GONE);
                break;
            case STATUS_NO_DATA:
                break;
            case STATUS_NO_NET:
                setVisibility(VISIBLE);
                mRlEmptyContainer.setVisibility(VISIBLE);
                break;
            case STATUS_HIDE:
                setVisibility(GONE);
                break;
        }

    }

    /**
     * 获取状态
     * @return
     */
    public int getEmptyStatus(){
        return mEmptyStatus;
    }

    @OnClick(R.id.tv_net_error)
    public void onClick(){
        if (mOnRetryListener != null){
            mOnRetryListener.onRetry();
        }
    }

    /**
     * 设置重试监听器
     * @param onRetryListener
     */
    public void setRetryListener(OnRetryListener onRetryListener) {
        this.mOnRetryListener = onRetryListener;
    }

    /**
     * 点击重试监听器
     */
    public interface OnRetryListener {
        void onRetry();
    }

}
