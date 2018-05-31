package ru.geekbrains.android3_1.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.android3_1.model.CounterModel;
import ru.geekbrains.android3_1.view.MainView;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private CounterModel mCounterModel;
    private Scheduler mMainThreadScheduler;

    public MainPresenter(CounterModel counterModel, Scheduler mainThreadScheduler) {
        mCounterModel = counterModel;
        mMainThreadScheduler = mainThreadScheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    @Override
    public void attachView(MainView view) {
        super.attachView(view);

        view.subscribe(
                aVoid -> mCounterModel.calculate(0)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(mMainThreadScheduler)
                        .subscribe(integer -> getViewState().setButtonOneText(integer + "")),
                aVoid -> mCounterModel.calculate(1)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(mMainThreadScheduler)
                        .subscribe(integer -> getViewState().setButtonTwoText(integer + "")),
                aVoid -> mCounterModel.calculate(2)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(mMainThreadScheduler)
                        .subscribe(integer -> getViewState().setButtonThreeText(integer + "")),
                charSequence -> getViewState().setText(charSequence.toString()),
                aVoid -> mCounterModel.convertPhoto()
                        .subscribeOn(Schedulers.io())
                        .observeOn(mMainThreadScheduler)
                        .subscribe(object -> getViewState().onConverted(),
                                throwable -> getViewState().onConvertedError(throwable))
        );
    }
}
