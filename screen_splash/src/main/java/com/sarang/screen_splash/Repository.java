package com.sarang.screen_splash;

import com.example.core.DeviceInfo;

public interface Repository {
    void registerDevice(DeviceInfo deviceInfo, OnRegisterDeviceListener onRegisterDeviceListener);
}
