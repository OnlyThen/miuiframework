package android.telephony;

import android.telephony.TelephonyManager.CellInfoCallback;
import java.util.List;

/* compiled from: lambda */
public final /* synthetic */ class -$$Lambda$TelephonyManager$1$scMPky6lOZrCjFC3d4STbtLfpHE implements Runnable {
    private final /* synthetic */ CellInfoCallback f$0;
    private final /* synthetic */ List f$1;

    public /* synthetic */ -$$Lambda$TelephonyManager$1$scMPky6lOZrCjFC3d4STbtLfpHE(CellInfoCallback cellInfoCallback, List list) {
        this.f$0 = cellInfoCallback;
        this.f$1 = list;
    }

    public final void run() {
        this.f$0.onCellInfo(this.f$1);
    }
}
