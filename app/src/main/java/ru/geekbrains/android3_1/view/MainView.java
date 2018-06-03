package ru.geekbrains.android3_1.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import rx.functions.Action1;


@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface MainView extends MvpView {
    void setButtonOneText(String text);

    void setButtonTwoText(String text);

    void setButtonThreeText(String text);

    void subscribe(Action1<Void> buttonOneAction,
                   Action1<Void> buttonTwoAction,
                   Action1<Void> buttonThreeAction,
                   Action1<CharSequence> editTextAction,
                   Action1<Void> buttonPhotoAction);

    void setText(String text);

    @StateStrategyType(value = SkipStrategy.class)
    void onConverted();

    @StateStrategyType(value = SkipStrategy.class)
    void onConvertedError(Throwable throwable);

    @StateStrategyType(value = SingleStateStrategy.class)
    void showLoading(boolean show);
}
