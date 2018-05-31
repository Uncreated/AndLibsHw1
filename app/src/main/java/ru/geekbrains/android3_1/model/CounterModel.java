package ru.geekbrains.android3_1.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import timber.log.Timber;

public class CounterModel {
    private List<Integer> mCounters;

    private static final String JPG_NAME = "photo.jpg";
    private static final String PNG_NAME = "photo.png";

    private String mAppFolder;

    public CounterModel(Context context, int id) {
        mCounters = new ArrayList<>();
        mCounters.add(0);
        mCounters.add(0);
        mCounters.add(0);

        mAppFolder = context.getApplicationContext().getFilesDir().getAbsolutePath();
        //init photo
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id);
        try (FileOutputStream out = new FileOutputStream(mAppFolder + "/" + JPG_NAME)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public Observable<Object> convertPhoto() {
        return Observable.fromCallable(() -> {
            Bitmap bitmap = BitmapFactory.decodeFile(mAppFolder + "/" + JPG_NAME);
            try (FileOutputStream out = new FileOutputStream(mAppFolder + "/" + PNG_NAME)) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
            return new Object();
        });
    }
}
