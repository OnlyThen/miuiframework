package com.android.internal.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.NotificationHeaderView;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RemoteViews.RemoteView;
import com.android.internal.R;

@RemoteView
public class MediaNotificationView extends FrameLayout {
    private View mActions;
    private NotificationHeaderView mHeader;
    private int mImagePushIn;
    private View mMainColumn;
    private View mMediaContent;
    private final int mNotificationContentImageMarginEnd;
    private final int mNotificationContentMarginEnd;
    private ImageView mRightIcon;

    public MediaNotificationView(Context context) {
        this(context, null);
    }

    public MediaNotificationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MediaNotificationView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    /* Access modifiers changed, original: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        boolean hasIcon = this.mRightIcon.getVisibility() != 8;
        if (!hasIcon) {
            resetHeaderIndention();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        boolean reMeasure = false;
        this.mImagePushIn = 0;
        if (!hasIcon || mode == 0) {
        } else {
            int size = MeasureSpec.getSize(widthMeasureSpec) - this.mActions.getMeasuredWidth();
            MarginLayoutParams layoutParams = (MarginLayoutParams) this.mRightIcon.getLayoutParams();
            int imageEndMargin = layoutParams.getMarginEnd();
            size -= imageEndMargin;
            int fullHeight = this.mMediaContent.getMeasuredHeight();
            if (size > fullHeight) {
                size = fullHeight;
            } else if (size < fullHeight) {
                size = Math.max(0, size);
                this.mImagePushIn = fullHeight - size;
            }
            if (!(layoutParams.width == fullHeight && layoutParams.height == fullHeight)) {
                layoutParams.width = fullHeight;
                layoutParams.height = fullHeight;
                this.mRightIcon.setLayoutParams(layoutParams);
                reMeasure = true;
            }
            MarginLayoutParams params = (MarginLayoutParams) this.mMainColumn.getLayoutParams();
            int marginEnd = (size + imageEndMargin) + this.mNotificationContentMarginEnd;
            if (marginEnd != params.getMarginEnd()) {
                params.setMarginEnd(marginEnd);
                this.mMainColumn.setLayoutParams(params);
                reMeasure = true;
            }
            int headerMarginEnd = imageEndMargin;
            int headerTextMarginEnd = size + imageEndMargin;
            if (headerTextMarginEnd != this.mHeader.getHeaderTextMarginEnd()) {
                this.mHeader.setHeaderTextMarginEnd(headerTextMarginEnd);
                reMeasure = true;
            }
            params = (MarginLayoutParams) this.mHeader.getLayoutParams();
            if (params.getMarginEnd() != headerMarginEnd) {
                params.setMarginEnd(headerMarginEnd);
                this.mHeader.setLayoutParams(params);
                reMeasure = true;
            }
            if (this.mHeader.getPaddingEnd() != this.mNotificationContentImageMarginEnd) {
                NotificationHeaderView notificationHeaderView = this.mHeader;
                notificationHeaderView.setPaddingRelative(notificationHeaderView.getPaddingStart(), this.mHeader.getPaddingTop(), this.mNotificationContentImageMarginEnd, this.mHeader.getPaddingBottom());
                reMeasure = true;
            }
        }
        if (reMeasure) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    /* Access modifiers changed, original: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (this.mImagePushIn > 0) {
            if (getLayoutDirection() == 1) {
                this.mImagePushIn *= -1;
            }
            ImageView imageView = this.mRightIcon;
            imageView.layout(imageView.getLeft() + this.mImagePushIn, this.mRightIcon.getTop(), this.mRightIcon.getRight() + this.mImagePushIn, this.mRightIcon.getBottom());
        }
    }

    private void resetHeaderIndention() {
        if (this.mHeader.getPaddingEnd() != this.mNotificationContentMarginEnd) {
            NotificationHeaderView notificationHeaderView = this.mHeader;
            notificationHeaderView.setPaddingRelative(notificationHeaderView.getPaddingStart(), this.mHeader.getPaddingTop(), this.mNotificationContentMarginEnd, this.mHeader.getPaddingBottom());
        }
        MarginLayoutParams headerParams = (MarginLayoutParams) this.mHeader.getLayoutParams();
        headerParams.setMarginEnd(0);
        if (headerParams.getMarginEnd() != 0) {
            headerParams.setMarginEnd(0);
            this.mHeader.setLayoutParams(headerParams);
        }
    }

    public MediaNotificationView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mNotificationContentMarginEnd = context.getResources().getDimensionPixelSize(R.dimen.notification_content_margin_end);
        this.mNotificationContentImageMarginEnd = context.getResources().getDimensionPixelSize(R.dimen.notification_content_image_margin_end);
    }

    /* Access modifiers changed, original: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mRightIcon = (ImageView) findViewById(R.id.right_icon);
        this.mActions = findViewById(R.id.media_actions);
        this.mHeader = (NotificationHeaderView) findViewById(R.id.notification_header);
        this.mMainColumn = findViewById(R.id.notification_main_column);
        this.mMediaContent = findViewById(R.id.notification_media_content);
    }
}
