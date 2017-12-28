package com.time_bt.administrator.bt_timing;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author iimT
 * @date 2017/12/21
 * 工具类
 */

public class Helper {
    public static void makeToast(Context ctx, String msg, Boolean isShow){
        Toast t = Toast.makeText(ctx,msg,Toast.LENGTH_SHORT);
        if(isShow)
            t.show();
    }

    //禁用输入框
    public static void banInput(EditText editText) {
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);
    }

    //激活输入框
    public static void activeInput(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
    }

    /*
    * 置空输入框
    * */
    public static void emptyInput(EditText editText) {
        editText.setText(("").toCharArray(), 0, ("").length());
    }
}
