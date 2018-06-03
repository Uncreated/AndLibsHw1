package ru.geekbrains.android3_1.view;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.geekbrains.android3_1.R;
import ru.geekbrains.android3_1.model.CounterModel;
import ru.geekbrains.android3_1.presenter.MainPresenter;
import rx.functions.Action1;

public class MainActivity extends MvpAppCompatActivity implements MainView {
    @BindView(R.id.btn_one)
    Button mButtonOne;

    @BindView(R.id.btn_two)
    Button mButtonTwo;

    @BindView(R.id.btn_three)
    Button mButtonThree;

    @BindView(R.id.btn_photo)
    Button mButtonPhoto;

    @BindView(R.id.et)
    EditText mEditText;

    @BindView(R.id.tv)
    TextView mTextView;

    @InjectPresenter
    MainPresenter mMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @ProvidePresenter
    public MainPresenter provideMainPresenter() {
        return new MainPresenter(new CounterModel(this, R.drawable.wallpaper4k),
                AndroidSchedulers.mainThread());
    }

    public void subscribe(Action1<Void> buttonOneAction,
                          Action1<Void> buttonTwoAction,
                          Action1<Void> buttonThreeAction,
                          Action1<CharSequence> editTextAction,
                          Action1<Void> buttonPhotoAction) {
        RxView.clicks(mButtonOne).subscribe(buttonOneAction);
        RxView.clicks(mButtonTwo).subscribe(buttonTwoAction);
        RxView.clicks(mButtonThree).subscribe(buttonThreeAction);
        RxTextView.textChanges(mEditText).subscribe(editTextAction);
        RxView.clicks(mButtonPhoto).subscribe(buttonPhotoAction);
    }

    @Override
    public void onConverted() {
        Toast.makeText(this, "Photo successfully converted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConvertedError(Throwable throwable) {
        Toast.makeText(this, "Photo convert failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setButtonOneText(String text) {
        mButtonOne.setText(text);
    }

    @Override
    public void setButtonTwoText(String text) {
        mButtonTwo.setText(text);
    }

    @Override
    public void setButtonThreeText(String text) {
        mButtonThree.setText(text);
    }

    @Override
    public void setText(String text) {
        mTextView.setText(text);
    }

    AlertDialog mAlertDialog;

    @Override
    public void showLoading(boolean show) {
        if (mAlertDialog != null) {
            if (mAlertDialog.isShowing()) {
                mAlertDialog.cancel();
            }
            mAlertDialog = null;
        }
        if (show) {
            mAlertDialog = new AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setTitle(R.string.loading_title)
                    .setNegativeButton(R.string.cancel, (dialog, which) -> mMainPresenter.convertCancel())
                    .create();
            mAlertDialog.show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        showLoading(false);
    }
}
