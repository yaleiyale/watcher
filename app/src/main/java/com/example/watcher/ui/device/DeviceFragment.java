package com.example.watcher.ui.device;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.watcher.adapters.DeviceAdapter;
import com.example.watcher.data.device.Device;
import com.example.watcher.databinding.FragmentDeviceBinding;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


@AndroidEntryPoint
public
class DeviceFragment extends Fragment {
    private DeviceViewModel deviceViewModel;
    private FragmentDeviceBinding binding;
    private final CompositeDisposable disposable = new CompositeDisposable();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        deviceViewModel =
                new ViewModelProvider(this).get(DeviceViewModel.class);

        binding = FragmentDeviceBinding.inflate(inflater, container, false);
        DeviceAdapter adapter = new DeviceAdapter();
        binding.deviceList.setAdapter(adapter);
        subscribeUi(adapter);
        binding.button.setOnClickListener(l -> insert());
        setHasOptionsMenu(false);
        return binding.getRoot();

    }

    @Override
    public void onStop() {
        super.onStop();
        disposable.clear();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void insert() {
        binding.button.setEnabled(false);
        disposable.add(deviceViewModel.deviceRepository.insert()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> binding.button.setEnabled(true),
                        throwable -> Log.e("unable", "Unable to add device", throwable)));
    }

    private void subscribeUi(DeviceAdapter adapter) {
        DeviceObserver observer = new DeviceObserver(adapter);
        deviceViewModel.devices.observe(getViewLifecycleOwner(), observer);
    }

}

class DeviceObserver implements Observer<List<Device>> {
    final DeviceAdapter deviceAdapter;

    DeviceObserver(DeviceAdapter deviceAdapter) {
        this.deviceAdapter = deviceAdapter;
    }

    @Override
    public void onChanged(List<Device> devices) {
        this.deviceAdapter.submitList(devices);
    }
}

