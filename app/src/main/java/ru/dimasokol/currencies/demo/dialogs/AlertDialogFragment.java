package ru.dimasokol.currencies.demo.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * <p></p>
 * <p>Добавлено: 14.06.16</p>
 *
 * @author sokol
 */
public class AlertDialogFragment extends DialogFragment {

    /**
     * Тег для данного диалога, удобный для использования его в качестве единственного фрагмента
     * в менеджере.
     */
    public static final String TAG = "someAlertDialog";

    private static final String ARG_MESSAGE = "message";
    private static final String ARG_TITLE_RES = "title_res";
    private static final String ARG_POSITIVE_RUNNER = "positive";

    private String mMessage;
    private int mTitleRes;
    private DialogAction mPositiveRunner;

    public static AlertDialogFragment newInstance(String message, @StringRes int titleRes, DialogAction positive) {
        AlertDialogFragment fragment = new AlertDialogFragment();

        Bundle args = new Bundle();

        args.putString(ARG_MESSAGE, message);
        args.putInt(ARG_TITLE_RES, titleRes);
        args.putSerializable(ARG_POSITIVE_RUNNER, positive);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMessage = getArguments().getString(ARG_MESSAGE);
        mTitleRes = getArguments().getInt(ARG_TITLE_RES, 0);
        mPositiveRunner = (DialogAction) getArguments().getSerializable(ARG_POSITIVE_RUNNER);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setMessage(mMessage).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                if (mPositiveRunner != null)
                    mPositiveRunner.execute(AlertDialogFragment.this);
            }
        });

        if (mTitleRes != 0)
            builder.setTitle(mTitleRes);

        return builder.create();
    }
}
