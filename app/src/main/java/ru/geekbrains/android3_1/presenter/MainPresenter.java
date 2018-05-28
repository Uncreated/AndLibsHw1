package ru.geekbrains.android3_1.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.android3_1.model.CounterModel;
import ru.geekbrains.android3_1.view.MainView;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {


    CounterModel model;

    public MainPresenter(CounterModel model) {
        this.model = model;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    @Override
    public void attachView(MainView view) {
        super.attachView(view);

        view.subscribe(
                aVoid -> model.calculate(0)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(integer -> getViewState().setButtonOneText(integer + "")),
                aVoid -> model.calculate(1)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(integer -> getViewState().setButtonTwoText(integer + "")),
                aVoid -> model.calculate(2)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(integer -> getViewState().setButtonThreeText(integer + "")),
                charSequence -> getViewState().setText(charSequence.toString())
        );
    }
}
