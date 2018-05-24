package ru.geekbrains.android3_1.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

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

    public void buttonOneClick() {
        int value = model.calculate(0);
        getViewState().setButtonOneText(value + "");
    }

    public void buttonTwoClick() {
        int value = model.calculate(1);
        getViewState().setButtonTwoText(value + "");
    }

    public void buttonThreeClick() {
        int value = model.calculate(2);
        getViewState().setButtonThreeText(value + "");
    }
}
