package com.android.internal.util;

import android.annotation.UnsupportedAppUsage;
import java.io.File;
import java.io.IOException;

@Deprecated
public class JournaledFile {
    File mReal;
    File mTemp;
    boolean mWriting;

    @UnsupportedAppUsage
    public JournaledFile(File real, File temp) {
        this.mReal = real;
        this.mTemp = temp;
    }

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    public File chooseForRead() {
        File result;
        if (this.mReal.exists()) {
            result = this.mReal;
            if (this.mTemp.exists()) {
                this.mTemp.delete();
            }
        } else if (!this.mTemp.exists()) {
            return this.mReal;
        } else {
            result = this.mTemp;
            this.mTemp.renameTo(this.mReal);
        }
        return result;
    }

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    public File chooseForWrite() {
        if (this.mWriting) {
            throw new IllegalStateException("uncommitted write already in progress");
        }
        if (!this.mReal.exists()) {
            try {
                this.mReal.createNewFile();
            } catch (IOException e) {
            }
        }
        if (this.mTemp.exists()) {
            this.mTemp.delete();
        }
        this.mWriting = true;
        return this.mTemp;
    }

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    public void commit() {
        if (this.mWriting) {
            this.mWriting = false;
            this.mTemp.renameTo(this.mReal);
            return;
        }
        throw new IllegalStateException("no file to commit");
    }

    @UnsupportedAppUsage(maxTargetSdk = 28, trackingBug = 115609023)
    public void rollback() {
        if (this.mWriting) {
            this.mWriting = false;
            this.mTemp.delete();
            return;
        }
        throw new IllegalStateException("no file to roll back");
    }
}
