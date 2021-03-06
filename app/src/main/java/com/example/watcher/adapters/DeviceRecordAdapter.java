package com.example.watcher.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watcher.data.device.Device;
import com.example.watcher.data.device.DeviceRepository;
import com.example.watcher.data.passRecord.PassRecord;
import com.example.watcher.data.passRecord.PassRecordRepository;
import com.example.watcher.data.person.Person;
import com.example.watcher.data.person.PersonRepository;
import com.example.watcher.databinding.ListItemRecordBinding;

public class DeviceRecordAdapter extends ListAdapter<PassRecord, RecyclerView.ViewHolder> {
    DeviceRepository deviceRepository;
    PersonRepository personRepository;
    PassRecordRepository passRecordRepository;

    public DeviceRecordAdapter(DeviceRepository deviceRepository, PersonRepository personRepository, PassRecordRepository passRecordRepository) {
        super(new DeviceRecordDiffCallBack());
        this.deviceRepository = deviceRepository;
        this.personRepository = personRepository;
        this.passRecordRepository = passRecordRepository;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DeviceRecordAdapter.DeviceRecordViewHolder(ListItemRecordBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PassRecord passRecord = getItem(position);
        Person person = personRepository.getPersonById(passRecord.personId).blockingFirst();
        Device device = deviceRepository.getDeviceById(passRecord.deviceId).blockingFirst();
        ((DeviceRecordViewHolder) holder).bind(person, device, passRecord);
    }

    static class DeviceRecordViewHolder extends RecyclerView.ViewHolder {
        private final ListItemRecordBinding binding;

        DeviceRecordViewHolder(ListItemRecordBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


        void bind(Person person, Device device, PassRecord record) {
            binding.setPerson(person);
            binding.setDevice(device);
            binding.setRecord(record);
            binding.executePendingBindings();
        }
    }
}

class DeviceRecordDiffCallBack extends DiffUtil.ItemCallback<PassRecord> {

    @Override
    public boolean areItemsTheSame(@NonNull PassRecord oldItem, @NonNull PassRecord newItem) {
        return oldItem.recordId == newItem.recordId;
    }

    @SuppressLint("DiffUtilEquals")
    @Override
    public boolean areContentsTheSame(@NonNull PassRecord oldItem, @NonNull PassRecord newItem) {
        return oldItem == newItem;
    }
}

