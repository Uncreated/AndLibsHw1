package ru.geekbrains.android3_1.model;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import timber.log.Timber;

public class CounterModel {
    private List<Integer> mCounters;

    public CounterModel() {
        mCounters = new ArrayList<>();
        mCounters.add(0);
        mCounters.add(0);
        mCounters.add(0);
    }

    public Observable<Integer> calculate(int index) {
        return Observable.fromCallable(() -> {
            Timber.d("Started");
            Thread.sleep(5000);
            synchronized (this) {
                int value = mCounters.get(index) + 1;
                mCounters.set(index, value);
                return value;
            }
        });
    }
}
