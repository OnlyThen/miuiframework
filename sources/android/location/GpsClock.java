package android.location;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.Half;

@SystemApi
public class GpsClock implements Parcelable {
    public static final Creator<GpsClock> CREATOR = new Creator<GpsClock>() {
        public GpsClock createFromParcel(Parcel parcel) {
            GpsClock gpsClock = new GpsClock();
            gpsClock.mFlags = (short) parcel.readInt();
            gpsClock.mLeapSecond = (short) parcel.readInt();
            gpsClock.mType = parcel.readByte();
            gpsClock.mTimeInNs = parcel.readLong();
            gpsClock.mTimeUncertaintyInNs = parcel.readDouble();
            gpsClock.mFullBiasInNs = parcel.readLong();
            gpsClock.mBiasInNs = parcel.readDouble();
            gpsClock.mBiasUncertaintyInNs = parcel.readDouble();
            gpsClock.mDriftInNsPerSec = parcel.readDouble();
            gpsClock.mDriftUncertaintyInNsPerSec = parcel.readDouble();
            return gpsClock;
        }

        public GpsClock[] newArray(int size) {
            return new GpsClock[size];
        }
    };
    private static final short HAS_BIAS = (short) 8;
    private static final short HAS_BIAS_UNCERTAINTY = (short) 16;
    private static final short HAS_DRIFT = (short) 32;
    private static final short HAS_DRIFT_UNCERTAINTY = (short) 64;
    private static final short HAS_FULL_BIAS = (short) 4;
    private static final short HAS_LEAP_SECOND = (short) 1;
    private static final short HAS_NO_FLAGS = (short) 0;
    private static final short HAS_TIME_UNCERTAINTY = (short) 2;
    public static final byte TYPE_GPS_TIME = (byte) 2;
    public static final byte TYPE_LOCAL_HW_TIME = (byte) 1;
    public static final byte TYPE_UNKNOWN = (byte) 0;
    private double mBiasInNs;
    private double mBiasUncertaintyInNs;
    private double mDriftInNsPerSec;
    private double mDriftUncertaintyInNsPerSec;
    private short mFlags;
    private long mFullBiasInNs;
    private short mLeapSecond;
    private long mTimeInNs;
    private double mTimeUncertaintyInNs;
    private byte mType;

    GpsClock() {
        initialize();
    }

    public void set(GpsClock clock) {
        this.mFlags = clock.mFlags;
        this.mLeapSecond = clock.mLeapSecond;
        this.mType = clock.mType;
        this.mTimeInNs = clock.mTimeInNs;
        this.mTimeUncertaintyInNs = clock.mTimeUncertaintyInNs;
        this.mFullBiasInNs = clock.mFullBiasInNs;
        this.mBiasInNs = clock.mBiasInNs;
        this.mBiasUncertaintyInNs = clock.mBiasUncertaintyInNs;
        this.mDriftInNsPerSec = clock.mDriftInNsPerSec;
        this.mDriftUncertaintyInNsPerSec = clock.mDriftUncertaintyInNsPerSec;
    }

    public void reset() {
        initialize();
    }

    public byte getType() {
        return this.mType;
    }

    public void setType(byte value) {
        this.mType = value;
    }

    private String getTypeString() {
        byte b = this.mType;
        if (b == (byte) 0) {
            return "Unknown";
        }
        if (b == (byte) 1) {
            return "LocalHwClock";
        }
        if (b == (byte) 2) {
            return "GpsTime";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<Invalid:");
        stringBuilder.append(this.mType);
        stringBuilder.append(">");
        return stringBuilder.toString();
    }

    public boolean hasLeapSecond() {
        return isFlagSet((short) 1);
    }

    public short getLeapSecond() {
        return this.mLeapSecond;
    }

    public void setLeapSecond(short leapSecond) {
        setFlag((short) 1);
        this.mLeapSecond = leapSecond;
    }

    public void resetLeapSecond() {
        resetFlag((short) 1);
        this.mLeapSecond = Half.NEGATIVE_ZERO;
    }

    public long getTimeInNs() {
        return this.mTimeInNs;
    }

    public void setTimeInNs(long timeInNs) {
        this.mTimeInNs = timeInNs;
    }

    public boolean hasTimeUncertaintyInNs() {
        return isFlagSet((short) 2);
    }

    public double getTimeUncertaintyInNs() {
        return this.mTimeUncertaintyInNs;
    }

    public void setTimeUncertaintyInNs(double timeUncertaintyInNs) {
        setFlag((short) 2);
        this.mTimeUncertaintyInNs = timeUncertaintyInNs;
    }

    public void resetTimeUncertaintyInNs() {
        resetFlag((short) 2);
        this.mTimeUncertaintyInNs = Double.NaN;
    }

    public boolean hasFullBiasInNs() {
        return isFlagSet((short) 4);
    }

    public long getFullBiasInNs() {
        return this.mFullBiasInNs;
    }

    public void setFullBiasInNs(long value) {
        setFlag((short) 4);
        this.mFullBiasInNs = value;
    }

    public void resetFullBiasInNs() {
        resetFlag((short) 4);
        this.mFullBiasInNs = Long.MIN_VALUE;
    }

    public boolean hasBiasInNs() {
        return isFlagSet((short) 8);
    }

    public double getBiasInNs() {
        return this.mBiasInNs;
    }

    public void setBiasInNs(double biasInNs) {
        setFlag((short) 8);
        this.mBiasInNs = biasInNs;
    }

    public void resetBiasInNs() {
        resetFlag((short) 8);
        this.mBiasInNs = Double.NaN;
    }

    public boolean hasBiasUncertaintyInNs() {
        return isFlagSet((short) 16);
    }

    public double getBiasUncertaintyInNs() {
        return this.mBiasUncertaintyInNs;
    }

    public void setBiasUncertaintyInNs(double biasUncertaintyInNs) {
        setFlag((short) 16);
        this.mBiasUncertaintyInNs = biasUncertaintyInNs;
    }

    public void resetBiasUncertaintyInNs() {
        resetFlag((short) 16);
        this.mBiasUncertaintyInNs = Double.NaN;
    }

    public boolean hasDriftInNsPerSec() {
        return isFlagSet(HAS_DRIFT);
    }

    public double getDriftInNsPerSec() {
        return this.mDriftInNsPerSec;
    }

    public void setDriftInNsPerSec(double driftInNsPerSec) {
        setFlag(HAS_DRIFT);
        this.mDriftInNsPerSec = driftInNsPerSec;
    }

    public void resetDriftInNsPerSec() {
        resetFlag(HAS_DRIFT);
        this.mDriftInNsPerSec = Double.NaN;
    }

    public boolean hasDriftUncertaintyInNsPerSec() {
        return isFlagSet(HAS_DRIFT_UNCERTAINTY);
    }

    public double getDriftUncertaintyInNsPerSec() {
        return this.mDriftUncertaintyInNsPerSec;
    }

    public void setDriftUncertaintyInNsPerSec(double driftUncertaintyInNsPerSec) {
        setFlag(HAS_DRIFT_UNCERTAINTY);
        this.mDriftUncertaintyInNsPerSec = driftUncertaintyInNsPerSec;
    }

    public void resetDriftUncertaintyInNsPerSec() {
        resetFlag(HAS_DRIFT_UNCERTAINTY);
        this.mDriftUncertaintyInNsPerSec = Double.NaN;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(this.mFlags);
        parcel.writeInt(this.mLeapSecond);
        parcel.writeByte(this.mType);
        parcel.writeLong(this.mTimeInNs);
        parcel.writeDouble(this.mTimeUncertaintyInNs);
        parcel.writeLong(this.mFullBiasInNs);
        parcel.writeDouble(this.mBiasInNs);
        parcel.writeDouble(this.mBiasUncertaintyInNs);
        parcel.writeDouble(this.mDriftInNsPerSec);
        parcel.writeDouble(this.mDriftUncertaintyInNsPerSec);
    }

    public int describeContents() {
        return 0;
    }

    public String toString() {
        String format = "   %-15s = %s\n";
        String formatWithUncertainty = "   %-15s = %-25s   %-26s = %s\n";
        StringBuilder builder = new StringBuilder("GpsClock:\n");
        String str = "   %-15s = %s\n";
        builder.append(String.format(str, new Object[]{"Type", getTypeString()}));
        Object[] objArr = new Object[2];
        objArr[0] = "LeapSecond";
        Double d = null;
        objArr[1] = hasLeapSecond() ? Short.valueOf(this.mLeapSecond) : null;
        builder.append(String.format(str, objArr));
        Object[] objArr2 = new Object[4];
        objArr2[0] = "TimeInNs";
        objArr2[1] = Long.valueOf(this.mTimeInNs);
        objArr2[2] = "TimeUncertaintyInNs";
        objArr2[3] = hasTimeUncertaintyInNs() ? Double.valueOf(this.mTimeUncertaintyInNs) : null;
        String str2 = "   %-15s = %-25s   %-26s = %s\n";
        builder.append(String.format(str2, objArr2));
        objArr2 = new Object[2];
        objArr2[0] = "FullBiasInNs";
        objArr2[1] = hasFullBiasInNs() ? Long.valueOf(this.mFullBiasInNs) : null;
        builder.append(String.format(str, objArr2));
        Object[] objArr3 = new Object[4];
        objArr3[0] = "BiasInNs";
        objArr3[1] = hasBiasInNs() ? Double.valueOf(this.mBiasInNs) : null;
        objArr3[2] = "BiasUncertaintyInNs";
        objArr3[3] = hasBiasUncertaintyInNs() ? Double.valueOf(this.mBiasUncertaintyInNs) : null;
        builder.append(String.format(str2, objArr3));
        objArr = new Object[4];
        objArr[0] = "DriftInNsPerSec";
        objArr[1] = hasDriftInNsPerSec() ? Double.valueOf(this.mDriftInNsPerSec) : null;
        objArr[2] = "DriftUncertaintyInNsPerSec";
        if (hasDriftUncertaintyInNsPerSec()) {
            d = Double.valueOf(this.mDriftUncertaintyInNsPerSec);
        }
        objArr[3] = d;
        builder.append(String.format(str2, objArr));
        return builder.toString();
    }

    private void initialize() {
        this.mFlags = (short) 0;
        resetLeapSecond();
        setType((byte) 0);
        setTimeInNs(Long.MIN_VALUE);
        resetTimeUncertaintyInNs();
        resetFullBiasInNs();
        resetBiasInNs();
        resetBiasUncertaintyInNs();
        resetDriftInNsPerSec();
        resetDriftUncertaintyInNsPerSec();
    }

    private void setFlag(short flag) {
        this.mFlags = (short) (this.mFlags | flag);
    }

    private void resetFlag(short flag) {
        this.mFlags = (short) (this.mFlags & (~flag));
    }

    private boolean isFlagSet(short flag) {
        return (this.mFlags & flag) == flag;
    }
}
