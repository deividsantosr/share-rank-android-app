package com.example.sharerank.ui.top10;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Top10ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Top10ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Top 10 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}