package com.RSPL.POS;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;


public class CustomAlphabatKeyboard  {

    /** A link to the KeyboardView that is used to render this CustomKeyboard. */
    private KeyboardView mKeyboardView;

    private Activity mHostActivity;

    Keyboard keyboard;

    Context context;



    /*@Override
    public View onCreateInputView() {
        mKeyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.activity_login, null);
        keyboard = new Keyboard(this, R.xml.upercase);
        mKeyboardView.setKeyboard(keyboard);
        mKeyboardView.setOnKeyboardActionListener(this);
        mKeyboardView.invalidateAllKeys();
        return mKeyboardView;
    }*/

    /** The key (code) handler. */
    private KeyboardView.OnKeyboardActionListener mOnKeyboardActionListener = new KeyboardView.OnKeyboardActionListener() {

        public final static int CodeDelete   = -5; // Keyboard.KEYCODE_DELETE
        public final static int CodeCancel   = -3; // Keyboard.KEYCODE_CANCEL
        public final static int CodePrev     = 55000;
        public final static int CodeAllLeft  = 55001;
        public final static int CodeLeft     = 55002;
        public final static int CodeRight    = 55003;
        public final static int CodeAllRight = 55004;
        public final static int CodeNext     = 55005;
        public final static int CodeClear    = 55006;
        public final static int KEYCODE_SHIFT= -1;

        public final static int KEYCODE_CAPS= 55007;


        private boolean caps = true;

        private boolean isCaps=false;



        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            // NOTE We can say '<Key android:codes="49,50" ... >' in the xml file; all codes come in keyCodes, the first in this list in primaryCode
            // Get the EditText and its Editable
            View focusCurrent = mHostActivity.getWindow().getCurrentFocus();
            if( focusCurrent==null || focusCurrent.getClass()!=EditText.class ) return;
            EditText edittext = (EditText) focusCurrent;
            Editable editable = edittext.getText();
            int start = edittext.getSelectionStart();




            // Apply the key to the edittext
            if( primaryCode==CodeCancel ) {
                hideCustomKeyboard();
            }

            else if (primaryCode == KEYCODE_SHIFT) {

                 caps = !caps;

                Keyboard keyboard= new Keyboard(mHostActivity,R.xml.upercase);

                keyboard.setShifted(caps);
                mKeyboardView.setKeyboard(keyboard);
                mKeyboardView.invalidateAllKeys();


            }


            else if (primaryCode == KEYCODE_CAPS) {

                isCaps = !isCaps;

                Keyboard keyboard= new Keyboard(mHostActivity,R.xml.alphanumerickeyboard);

                keyboard.setShifted(isCaps);
                mKeyboardView.setKeyboard(keyboard);
                mKeyboardView.invalidateAllKeys();

//
            }

            else if( primaryCode==CodeDelete ) {
                if( editable!=null && start>0 ) editable.delete(start - 1, start);
            } else if( primaryCode==CodeClear ) {
                if( editable!=null ) editable.clear();
            } else if( primaryCode==CodeLeft ) {
                if( start>0 ) edittext.setSelection(start - 1);
            } else if( primaryCode==CodeRight ) {
                if (start < edittext.length()) edittext.setSelection(start + 1);
            } else if( primaryCode==CodeAllLeft ) {
                edittext.setSelection(0);
            } else if( primaryCode==CodeAllRight ) {
                edittext.setSelection(edittext.length());
            } else if( primaryCode==CodePrev ) {
                View focusNew= edittext.focusSearch(View.FOCUS_BACKWARD);
                if( focusNew!=null ) focusNew.requestFocus();
            } else if( primaryCode==CodeNext ) {
                View focusNew= edittext.focusSearch(View.FOCUS_FORWARD);
                if( focusNew!=null ) focusNew.requestFocus();
            } else { // insert character
                editable.insert(start, Character.toString((char) primaryCode));
            }


        }

        @Override
        public void onPress(int arg0) {
        }

        @Override
        public void onRelease(int primaryCode) {
        }

        @Override
        public void onText(CharSequence text) {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeRight() {
        }

        @Override
        public void swipeUp() {
        }
    };


    public CustomAlphabatKeyboard(Activity host, int viewid, int layoutid) {
        mHostActivity= host;
//        keyboard = new Keyboard(context,R.xml.upercase);
   //     mKeyboardView.setKeyboard(keyboard);

        mKeyboardView= (KeyboardView)mHostActivity.findViewById(viewid);
        mKeyboardView.setKeyboard(new Keyboard(mHostActivity, layoutid));

        /*keyboard= new (Keyboard)
        mKeyboardView.setKeyboard(keyboard);*/

        /*mKeyboardView.setKeyboard(new Keyboard(mHostActivity,R.xml.upercase));*/

        mKeyboardView.setPreviewEnabled(false); // NOTE Do not show the preview balloons
        mKeyboardView.setOnKeyboardActionListener(mOnKeyboardActionListener);
        // Hide the standard keyboard initially
        mHostActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }






    /** Returns whether the CustomKeyboard is visible. */
    public boolean isCustomKeyboardVisible() {
        return mKeyboardView.getVisibility() == View.VISIBLE;
    }

    /** Make the CustomKeyboard visible, and hide the system keyboard for view v. */
    public void showCustomKeyboard( View v ) {
        mKeyboardView.setVisibility(View.VISIBLE);
        mKeyboardView.setEnabled(true);
        if( v!=null ) ((InputMethodManager)mHostActivity.getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    /** Make the CustomKeyboard invisible. */
    public void hideCustomKeyboard() {
        mKeyboardView.setVisibility(View.GONE);
        mKeyboardView.setEnabled(false);
    }

    /**
     * Register <var>EditText<var> with resource id <var>resid</var> (on the hosting activity) for using this custom keyboard.
     *
     * @param resid The resource id of the EditText that registers to the custom keyboard.
     */
    public void registerEditText(int resid) {
        // Find the EditText 'resid'
        EditText edittext= (EditText) mHostActivity.findViewById(resid);
        // Make the custom keyboard appear
        edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            // NOTE By setting the on focus listener, we can show the custom keyboard when the edit box gets focus, but also hide it when the edit box loses focus
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if( hasFocus ) showCustomKeyboard(v); else hideCustomKeyboard();
            }
        });
        edittext.setOnClickListener(new View.OnClickListener() {
            // NOTE By setting the on click listener, we can show the custom keyboard again, by tapping on an edit box that already had focus (but that had the keyboard hidden).
            @Override
            public void onClick(View v) {
                showCustomKeyboard(v);
            }
        });
        // Disable standard keyboard hard way
        // NOTE There is also an easy way: 'edittext.setInputType(InputType.TYPE_NULL)' (but you will not have a cursor, and no 'edittext.setCursorVisible(true)' doesn't work )
        edittext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                EditText edittext = (EditText) v;
                int inType = edittext.getInputType();       // Backup the input type
                edittext.setInputType(InputType.TYPE_NULL); // Disable standard keyboard
                edittext.onTouchEvent(event);               // Call native handler
                edittext.setInputType(inType);              // Restore input type
                return true; // Consume touch event
            }
        });
        // Disable spell check (hex strings look like words to Android)
        edittext.setInputType(edittext.getInputType() | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
    }




}


/*mCustomKeyboard= new CustomAlphaNumericKeyboard(this, R.id.keyboard_product, R.xml.alphanumerickeyboard );

        mCustomKeyboard.registerEditText(R.id.myautocomplete);



        customFractionalKeyboard= new CustomFractionalKeyboard(this, R.id.keyboard_selling_price, R.xml.fractional_keyboard );

        customFractionalKeyboard.registerEditText(R.id.product_Selling);

        customFractionalKeyboard.registerEditText(R.id.product_Purchase);
*/


// NOTE How can we change the background color of some keys (like the shift/ctrl/alt)?
// NOTE What does android:keyEdgeFlags do/mean
