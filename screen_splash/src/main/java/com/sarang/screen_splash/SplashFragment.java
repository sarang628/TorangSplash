package com.sarang.screen_splash;

import android.os.Bundle;
import android.transition.ChangeBounds;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.sarang.screen_splash.databinding.FragmentSplashBinding;
import com.sarang.torangdi.DependencyInjection;
import com.sarang.torangdi.Logger;
import com.sarang.torangdi.SplashNavigation;


public class SplashFragment extends Fragment {

    private SplashViewModel mViewModel;
    private FragmentSplashBinding mBinding;
    private SplashNavigation splashNavigation = DependencyInjection.splashNavigation;

    public static SplashFragment get(FragmentManager supportFragmentManager) {
        return (SplashFragment) supportFragmentManager.findFragmentByTag(SplashFragment.class.getSimpleName());
    }

    public static void go(AppCompatActivity appCompatActivity, int containerId) {
        SplashFragment splashFragment = new SplashFragment();
        appCompatActivity.getSupportFragmentManager().beginTransaction()
                .replace(containerId, splashFragment, SplashFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSharedElementEnterTransition(new ChangeBounds());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentSplashBinding.bind(view);
        mBinding.setLifecycleOwner(this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(SplashViewModel.class);
        mBinding.setVm(mViewModel);

        mViewModel.registerDevice();

        mViewModel.getIsRegisteredDevice().observe(getActivity(), aBoolean -> {
            if (aBoolean) {
                mViewModel.checkForgery();
            }
        });

        mViewModel.getIsForgery().observe(getActivity(), aBoolean -> {
            if (aBoolean) {
                mViewModel.permissionsCheck();
            }
        });

        mViewModel.getIsReady().observe(getActivity(), aBoolean -> {
            if (splashNavigation != null) {
                splashNavigation.registerDeviceFingerPrint(() -> {
                    if (splashNavigation.isLogin()) {
                        splashNavigation.goMain(getFragmentManager());
                    } else {
                        splashNavigation.goLogin(getFragmentManager());
                    }
                });
            } else {
                Logger.d("splashNavigation is null");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewModel.stopProgress();
    }
}
