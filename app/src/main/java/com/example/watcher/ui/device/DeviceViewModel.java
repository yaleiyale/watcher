package com.example.watcher.ui.device;

import androidx.lifecycle.LiveData;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.watcher.data.device.Device;
import com.example.watcher.data.device.DeviceRepository;


import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DeviceViewModel extends ViewModel {
    DeviceRepository deviceRepository;
    LiveData<List<Device>> devices;

    @Inject
    public DeviceViewModel(DeviceRepository repository) {
        this.deviceRepository = repository;
        devices = deviceRepository.getDevices();
    }


}
