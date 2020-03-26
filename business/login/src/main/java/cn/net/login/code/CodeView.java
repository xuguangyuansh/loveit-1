package cn.net.login.code;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import cn.net.login.R;
import cn.net.login.count.CountListner;
import cn.net.login.count.Counter;
import cn.net.third.sms.SmsUtil;

public class CodeView extends LinearLayout implements CountListner {

    private TextInputLayout login_first_edit;
    private TextInputLayout login_second_edit;
    private TextView login_other_operation;
    private Counter counter;
    private EditText login_first_edit_text;
    private EditText login_second_edit_text;

    public CodeView(Context context) {
        super(context);
        init();
    }

    public CodeView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CodeView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.login_code_view, this);
        login_first_edit = view.findViewById(R.id.login_first_edit);
        login_first_edit_text = view.findViewById(R.id.login_first_edit_text);
        login_first_edit_text.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        login_second_edit_text = view.findViewById(R.id.login_second_edit_text);
        login_second_edit_text.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        login_second_edit = view.findViewById(R.id.login_second_edit);
        login_other_operation = view.findViewById(R.id.login_other_operation);
        login_first_edit.setHint(getContext().getString(R.string.input_phone));
        login_second_edit.setHint(getContext().getString(R.string.input_code));
        login_other_operation.setText(R.string.get_code);
        if (TextUtils.isEmpty(login_first_edit_text.getText().toString())){
            login_other_operation.setEnabled(false);
        }
        counter = new Counter(60000, 1000);
        counter.setCounterListener(this);
        login_other_operation.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsUtil.getInstance().getSmsCode("86",login_first_edit_text.getText().toString());
                login_other_operation.setEnabled(false);
                counter.start();
            }
        });
        login_first_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String phoneText = s.toString();
                if (!TextUtils.isEmpty(phoneText)){
                    login_other_operation.setEnabled(true);
                }else{
                    login_other_operation.setEnabled(false);
                }
            }
        });
        login_second_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public String getCodeText(){
        if (login_second_edit_text!=null){
            return login_second_edit_text.getText().toString();
        }
        return "";
    }

    public String getPhoneText(){
        if (login_second_edit_text!=null){
            return login_first_edit_text.getText().toString();
        }
        return "";
    }

    public void cancelCounter(){
        if (counter!=null){
            counter.cancel();
        }
        counter=null;
        counter = new Counter(60000, 1000);
        counter.setCounterListener(this);
        login_other_operation.setTextColor(getResources().getColor(R.color.color_text_blue));
        login_other_operation.setText(R.string.get_code_again);
        login_other_operation.setEnabled(true);
    }

    @Override
    public void onCount(int time) {
        login_other_operation.setTextColor(getResources().getColor(R.color.color_text_blue));
        login_other_operation.setText(time+" s");
    }

    @Override
    public void onFinish() {
        counter=null;
        counter = new Counter(60000, 1000);
        counter.setCounterListener(this);
        login_other_operation.setTextColor(getResources().getColor(R.color.color_text_blue));
        login_other_operation.setText(R.string.get_code_again);
        login_other_operation.setEnabled(true);
    }

}
