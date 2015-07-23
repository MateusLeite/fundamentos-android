package com.example.administrador.myapplication.util;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;

import com.example.administrador.myapplication.R;

/**
 * Created by Administrador on 22/07/2015.
 */
public final class FormHelper {

    private FormHelper(){
        super();
    }

    public static boolean requireValidate(Context context, EditText...editTexts){
        boolean valid = true;
        for (EditText editText : editTexts){
            if(editText.getText() == null || editText.getText().toString().trim().isEmpty()){
                String message = context.getString(R.string.required_field);
                editText.setError(message);
                valid = false;
            }
        }
        return valid;
    }
}
