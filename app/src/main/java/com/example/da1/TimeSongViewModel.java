package com.example.da1;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TimeSongViewModel extends ViewModel {
    private final MutableLiveData<Integer> selectedItem = new MutableLiveData<Integer>();
    public void selectItem(int index) {
        selectedItem.setValue(index);
    }
    public LiveData<Integer> getSelectedItem() {
        return selectedItem;
    }
}
