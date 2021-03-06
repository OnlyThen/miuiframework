package android.graphics;

import android.annotation.UnsupportedAppUsage;
import android.graphics.ColorSpace.Named;
import android.graphics.Shader.TileMode;

public class RadialGradient extends Shader {
    @UnsupportedAppUsage
    private int mCenterColor;
    private final long[] mColorLongs;
    @UnsupportedAppUsage
    private int[] mColors;
    @UnsupportedAppUsage
    private int mEdgeColor;
    @UnsupportedAppUsage
    private float[] mPositions;
    @UnsupportedAppUsage
    private float mRadius;
    @UnsupportedAppUsage
    private TileMode mTileMode;
    @UnsupportedAppUsage
    private float mX;
    @UnsupportedAppUsage
    private float mY;

    private static native long nativeCreate(long j, float f, float f2, float f3, long[] jArr, float[] fArr, int i, long j2);

    public RadialGradient(float centerX, float centerY, float radius, int[] colors, float[] stops, TileMode tileMode) {
        this(centerX, centerY, radius, Shader.convertColors(colors), stops, tileMode, ColorSpace.get(Named.SRGB));
    }

    public RadialGradient(float centerX, float centerY, float radius, long[] colors, float[] stops, TileMode tileMode) {
        this(centerX, centerY, radius, (long[]) colors.clone(), stops, tileMode, Shader.detectColorSpace(colors));
    }

    private RadialGradient(float centerX, float centerY, float radius, long[] colors, float[] stops, TileMode tileMode, ColorSpace colorSpace) {
        super(colorSpace);
        if (radius <= 0.0f) {
            throw new IllegalArgumentException("radius must be > 0");
        } else if (stops == null || colors.length == stops.length) {
            this.mX = centerX;
            this.mY = centerY;
            this.mRadius = radius;
            this.mColorLongs = colors;
            this.mPositions = stops != null ? (float[]) stops.clone() : null;
            this.mTileMode = tileMode;
        } else {
            throw new IllegalArgumentException("color and position arrays must be of equal length");
        }
    }

    public RadialGradient(float centerX, float centerY, float radius, int centerColor, int edgeColor, TileMode tileMode) {
        this(centerX, centerY, radius, Color.pack(centerColor), Color.pack(edgeColor), tileMode);
    }

    public RadialGradient(float centerX, float centerY, float radius, long centerColor, long edgeColor, TileMode tileMode) {
        this(centerX, centerY, radius, new long[]{centerColor, edgeColor}, null, tileMode);
    }

    /* Access modifiers changed, original: 0000 */
    public long createNativeInstance(long nativeMatrix) {
        return nativeCreate(nativeMatrix, this.mX, this.mY, this.mRadius, this.mColorLongs, this.mPositions, this.mTileMode.nativeInt, colorSpace().getNativeInstance());
    }
}
