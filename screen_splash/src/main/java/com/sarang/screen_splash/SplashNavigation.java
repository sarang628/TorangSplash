package com.sarang.screen_splash;

import androidx.fragment.app.FragmentManager;

public interface SplashNavigation {

    interface OnRegisteredFingerPringListener {
        void onRegistered();
    }

    void registerDeviceFingerPrint(OnRegisteredFingerPringListener onRegisteredFingerPringListener);

    boolean isLogin();

    void goMain(FragmentManager fragmentManager);

    void goLogin(FragmentManager fragmentManager);
}
