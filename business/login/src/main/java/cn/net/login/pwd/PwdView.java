package cn.net.login.pwd;

import android.content.Context;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.textfield.TextInputLayout;

import cn.net.base.constant.RoutePath;
import cn.net.login.LoginActivity;
import cn.net.login.R;

public class PwdView extends LinearLayout {
    private TextInputLayout login_first_edit;
    private TextInputLayout login_second_edit;
    private TextView login_other_operation;
    private EditText login_second_edit_text;
    private EditText login_first_edit_text;
    public PwdView(Context context) {
        super(context);
        init();
    }

    public PwdView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PwdView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.login_code_view, this);
        login_first_edit = view.findViewById(R.id.login_first_edit);
        login_first_edit_text = view.findViewById(R.id.login_first_edit_text);
        login_first_edit_text.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        login_second_edit = view.findViewById(R.id.login_second_edit);
        login_second_edit_text = view.findViewById(R.id.login_second_edit_text);
        login_second_edit_text.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
        login_other_operation = view.findViewById(R.id.login_other_operation);
        login_first_edit.setHint(getContext().getString(R.string.input_phone));
        login_second_edit.setHint(getContext().getString(R.string.input_pwd));
        login_other_operation.setText(R.string.forget_pwd);
        login_other_operation.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RoutePath.LOGIN_INDEX).withInt("loginType", LoginActivity.FORGET_PWD).navigation();
            }
        });
    }

    public String getPhoneText(){
        if (login_first_edit_text!=null){
            return login_first_edit_text.getText().toString();
        }
        return "";
    }


    public String getPasswordText(){
        if (login_second_edit_text!=null){
            return login_second_edit_text.getText().toString();
        }
        return "";
    }

}
