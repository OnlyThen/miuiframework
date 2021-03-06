package android.text.style;

import android.os.Parcel;
import android.text.ParcelableSpan;
import android.text.TextPaint;

public class BackgroundColorSpan extends CharacterStyle implements UpdateAppearance, ParcelableSpan {
    private final int mColor;

    public BackgroundColorSpan(int color) {
        this.mColor = color;
    }

    public BackgroundColorSpan(Parcel src) {
        this.mColor = src.readInt();
    }

    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    public int getSpanTypeIdInternal() {
        return 12;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        writeToParcelInternal(dest, flags);
    }

    public void writeToParcelInternal(Parcel dest, int flags) {
        dest.writeInt(this.mColor);
    }

    public int getBackgroundColor() {
        return this.mColor;
    }

    public void updateDrawState(TextPaint textPaint) {
        textPaint.bgColor = this.mColor;
    }
}
