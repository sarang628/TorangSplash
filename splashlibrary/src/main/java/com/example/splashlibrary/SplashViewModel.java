package com.example.splashlibrary;

import android.app.Application;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

public class SplashViewModel extends AndroidViewModel {

    public MutableLiveData<String> progress = new MutableLiveData<>();
    private MutableLiveData<Boolean> isReady = new MutableLiveData<>();
    private SplashNavigation splashNavigation;
    private MutableLiveData<Boolean> isRegisteredDevice = new MutableLiveData<>();
    private MutableLiveData<Boolean> isForgery = new MutableLiveData<>();

    //private DeviceManager deviceManager;
    private ForgeryManager forgeryManager;
    private PermissionManager permissionManager;
    private Repository repository;

    private Handler handler = new Handler();
    private Runnable runnable;

    public static SplashViewModel get(AppCompatActivity appCompatActivity) {
        return ViewModelProviders.of(appCompatActivity).get(SplashViewModel.class);
    }

    public MutableLiveData<Boolean> getIsRegisteredDevice() {
        return isRegisteredDevice;
    }

    public MutableLiveData<Boolean> getIsReady() {
        return isReady;
    }

    public static SplashViewModel obtainViewModel(AppCompatActivity appCompatActivity) {
        return ViewModelProviders.of(appCompatActivity).get(SplashViewModel.class);
    }

    public void setIsReady(Boolean isReady) {
        this.isReady.setValue(isReady);
    }

    public SplashViewModel(Application context) {
        super(context);
        progress.setValue("");
        startProgress();
    }

    public void startProgress() {
        if (runnable == null) {
            runnable = () -> {
                progress.setValue(progress.getValue().length() > 5 ? "." : progress.getValue() + ".");
                startProgress();
            };
        }

        handler.postDelayed(runnable, 100);
    }

    public void stopProgress() {
        runnable = null;

    }

    public void registerDevice() {
        /*if (deviceManager != null) {
            DeviceInfo deviceInfo = deviceManager.getDeviceInfo();
            if (repository != null) {
                repository.registerDevice(deviceInfo, new OnRegisterDeviceListener() {
                    @Override
                    public void onRegistered() {

                    }

                    @Override
                    public void onFailed(String errMsg) {

                    }
                });
            }
        }*/
    }

    public void checkForgery() {
        if (forgeryManager != null) {
            this.isForgery.setValue(forgeryManager.checkForgery());
        }
    }

    public MutableLiveData<Boolean> getIsForgery() {
        return isForgery;
    }

    public void permissionsCheck() {
        if (permissionManager != null)
            permissionManager.checkPermission();
    }
}
