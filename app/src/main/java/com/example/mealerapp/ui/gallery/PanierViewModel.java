package com.example.mealerapp.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PanierViewModel extends ViewModel {
    private final MutableLiveData<String> mText;
    public PanierViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Panier fragment");
    }
    public LiveData<String> getText() {
        return mText;
    }
}