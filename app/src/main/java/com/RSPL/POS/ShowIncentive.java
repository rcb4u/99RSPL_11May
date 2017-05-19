package com.RSPL.POS;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by rspl-madhavi on 2/3/17.
 */
class ShowIncentive extends Dialog implements View.OnClickListener{

    public Activity c;
    public Dialog d;
    private Button btnCloce;

    public ShowIncentive(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.show_incentive);

        btnCloce=(Button)findViewById(R.id.buttonClose);

        btnCloce.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonClose:
                dismiss();
                return;

        }
    }
}

