package net.ahammad.showhiddenpassword;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

/**
 * Created by ala on 7/22/14.
 */
public class ShownEdittext extends RelativeLayout {
    private boolean mIsShowingPassword;
    /**
     * EditText component
     */
    private EditText editText;

    /**
     * Button that clears the EditText contents
     */
    private Button showpasswordButton;

    /**
     * Additional listener fired when cleared
     */
    private OnClickListener onClickClearListener;

    public ShownEdittext(Context context) {
        super(context);
        init(null);
    }

    public ShownEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    public ShownEdittext(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    /**
     * Initialize view
     * @param attrs
     */
    private void init(AttributeSet attrs) {

        //inflate layout
        LayoutInflater inflater
                = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_edittext_showpassword, this, true);

        //pass attributes to EditText, make clearable
        editText = (EditText) findViewById(R.id.edittext);
        boolean enabled = true;
        if (attrs != null){
            TypedArray attrsArray =
                    getContext().obtainStyledAttributes(attrs,R.styleable.ShownEdittext);
//            editText.setInputType(
//                    attrsArray.getInt(
//                            R.styleable.ClearableEditText_android_inputType, InputType.TYPE_CLASS_TEXT));
//            editText.setHint(attrsArray.getString(R.styleable.ClearableEditText_android_hint));
            enabled = attrsArray.getBoolean(R.styleable.ShownEdittext_android_enabled , true);
        }
        if (enabled) {
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() > 0)
                        showpasswordButton.setVisibility(RelativeLayout.VISIBLE);
                    else
                        showpasswordButton.setVisibility(RelativeLayout.GONE);
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
        } else { editText.setEnabled(false); }

        //build clear button
        showpasswordButton = (Button) findViewById(R.id.button_clear);
        showpasswordButton.setVisibility(RelativeLayout.INVISIBLE);
        showpasswordButton.setOnTouchListener(mOnTouchListener);
    }

    /**
     * Get value
     * @return text
     */
    public Editable getText() {
        return editText.getText();
    }

    /**
     * Set value
     * @param text
     */
    public void setText(String text) {
        editText.setText(text);
    }

    /**
     * Set OnClickListener, making EditText unfocusable
     * @param listener
     */
    @Override
    public void setOnClickListener(OnClickListener listener) {
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);
        editText.setOnClickListener(listener);
    }

    /**
     * Set listener to be fired after EditText is cleared
     * @param listener
     */
    public void setOnClearListener(OnClickListener listener) {
        onClickClearListener = listener;
    }
    // my part
    OnTouchListener mOnTouchListener = new OnTouchListener() {

        private int mPreviousInputType;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            final int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    if (mIsShowingPassword) {
                        mIsShowingPassword = false;
                        setInputType(mPreviousInputType, true);
                        mPreviousInputType = -1;
                    } else {
                        mPreviousInputType = editText.getInputType();
                        mIsShowingPassword = true;
                        setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD, true);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    break;
            }

            return false;
        }
    };

    private void setInputType(int inputType, boolean keepState) {
        int selectionStart = -1;
        int selectionEnd = -1;
        if (keepState) {
            selectionStart = editText.getSelectionStart();
            selectionEnd = editText.getSelectionEnd();
        }
        editText.setInputType(inputType);
        if (keepState) {
            editText.setSelection(selectionStart, selectionEnd);
        }
    }

}
