package android.content.pm.dex;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IArtManager extends IInterface {

    public static class Default implements IArtManager {
        public void snapshotRuntimeProfile(int profileType, String packageName, String codePath, ISnapshotRuntimeProfileCallback callback, String callingPackage) throws RemoteException {
        }

        public boolean isRuntimeProfilingEnabled(int profileType, String callingPackage) throws RemoteException {
            return false;
        }

        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IArtManager {
        private static final String DESCRIPTOR = "android.content.pm.dex.IArtManager";
        static final int TRANSACTION_isRuntimeProfilingEnabled = 2;
        static final int TRANSACTION_snapshotRuntimeProfile = 1;

        private static class Proxy implements IArtManager {
            public static IArtManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            public void snapshotRuntimeProfile(int profileType, String packageName, String codePath, ISnapshotRuntimeProfileCallback callback, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(profileType);
                    _data.writeString(packageName);
                    _data.writeString(codePath);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    _data.writeString(callingPackage);
                    if (this.mRemote.transact(1, _data, _reply, 0) || Stub.getDefaultImpl() == null) {
                        _reply.readException();
                        _reply.recycle();
                        _data.recycle();
                        return;
                    }
                    Stub.getDefaultImpl().snapshotRuntimeProfile(profileType, packageName, codePath, callback, callingPackage);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isRuntimeProfilingEnabled(int profileType, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(profileType);
                    _data.writeString(callingPackage);
                    boolean z = true;
                    boolean z2 = false;
                    if (!this.mRemote.transact(2, _data, _reply, 0)) {
                        z = Stub.getDefaultImpl();
                        if (z) {
                            z = Stub.getDefaultImpl().isRuntimeProfilingEnabled(profileType, callingPackage);
                            return z;
                        }
                    }
                    _reply.readException();
                    z = _reply.readInt();
                    if (z) {
                        z2 = true;
                    }
                    boolean _result = z2;
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IArtManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IArtManager)) {
                return new Proxy(obj);
            }
            return (IArtManager) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            if (transactionCode == 1) {
                return "snapshotRuntimeProfile";
            }
            if (transactionCode != 2) {
                return null;
            }
            return "isRuntimeProfilingEnabled";
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            int i = code;
            Parcel parcel = data;
            Parcel parcel2 = reply;
            String descriptor = DESCRIPTOR;
            if (i == 1) {
                parcel.enforceInterface(descriptor);
                snapshotRuntimeProfile(data.readInt(), data.readString(), data.readString(), android.content.pm.dex.ISnapshotRuntimeProfileCallback.Stub.asInterface(data.readStrongBinder()), data.readString());
                reply.writeNoException();
                return true;
            } else if (i == 2) {
                parcel.enforceInterface(descriptor);
                boolean _result = isRuntimeProfilingEnabled(data.readInt(), data.readString());
                reply.writeNoException();
                parcel2.writeInt(_result);
                return true;
            } else if (i != 1598968902) {
                return super.onTransact(code, data, reply, flags);
            } else {
                parcel2.writeString(descriptor);
                return true;
            }
        }

        public static boolean setDefaultImpl(IArtManager impl) {
            if (Proxy.sDefaultImpl != null || impl == null) {
                return false;
            }
            Proxy.sDefaultImpl = impl;
            return true;
        }

        public static IArtManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }

    boolean isRuntimeProfilingEnabled(int i, String str) throws RemoteException;

    void snapshotRuntimeProfile(int i, String str, String str2, ISnapshotRuntimeProfileCallback iSnapshotRuntimeProfileCallback, String str3) throws RemoteException;
}
