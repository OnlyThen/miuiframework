package android.hardware;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import java.io.IOException;

public class SerialManager {
    private static final String TAG = "SerialManager";
    private final Context mContext;
    private final ISerialManager mService;

    public SerialManager(Context context, ISerialManager service) {
        this.mContext = context;
        this.mService = service;
    }

    @UnsupportedAppUsage
    public String[] getSerialPorts() {
        try {
            return this.mService.getSerialPorts();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public SerialPort openSerialPort(String name, int speed) throws IOException {
        try {
            ParcelFileDescriptor pfd = this.mService.openSerialPort(name);
            if (pfd != null) {
                SerialPort port = new SerialPort(name);
                port.open(pfd, speed);
                return port;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not open serial port ");
            stringBuilder.append(name);
            throw new IOException(stringBuilder.toString());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
