package com.example.sharerank.ui.help;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HelpViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HelpViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Entre em contato com nosso time: <a href=\\\"mailto:ask@me.it\\\">Send Feedback</a>\"");
    }

    public LiveData<String> getText() {
        return mText;
    }
}