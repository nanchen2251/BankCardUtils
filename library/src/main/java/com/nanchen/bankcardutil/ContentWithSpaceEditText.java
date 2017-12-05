package com.nanchen.bankcardutil;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.widget.Toast;


/**
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-11-27  13:34
 */

public class ContentWithSpaceEditText extends AppCompatEditText {

    public static final int TYPE_PHONE = 0;
    public static final int TYPE_BANK_CARD = 1;
    public static final int TYPE_ID_CARD = 2;
    private int start, count,before;
    private int contentType;
    private int maxLength = 50;
    private String digits;

    public ContentWithSpaceEditText(Context context) {
        this(context, null);
    }

    public ContentWithSpaceEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributeSet(context, attrs);
    }

    public ContentWithSpaceEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributeSet(context, attrs);
    }

    private void parseAttributeSet(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ContentWithSpaceEditText, 0, 0);
        contentType = ta.getInt(R.styleable.ContentWithSpaceEditText_input_type, 0);
        // 必须调用recycle
        ta.recycle();
        initType();
        setSingleLine();
        addTextChangedListener(watcher);
    }

    private void initType(){
        if (contentType == TYPE_PHONE) {
            maxLength = 13;
            digits = "0123456789 ";
            setInputType(InputType.TYPE_CLASS_NUMBER);
        } else if (contentType == TYPE_BANK_CARD) {
            maxLength = 31;
            digits = "0123456789 ";
            setInputType(InputType.TYPE_CLASS_NUMBER);
        } else if (contentType == TYPE_ID_CARD) {
            maxLength = 21;
            digits = null;
            setInputType(InputType.TYPE_CLASS_TEXT);
        }
        setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
    }

    @Override
    public void setInputType(int type) {
        if (contentType == TYPE_PHONE || contentType == TYPE_BANK_CARD) {
            type = InputType.TYPE_CLASS_NUMBER;
        }else if(contentType == TYPE_ID_CARD){
            type = InputType.TYPE_CLASS_TEXT;
        }
        super.setInputType(type);
        /* 非常重要:setKeyListener要在setInputType后面调用，否则无效。*/
        if(!TextUtils.isEmpty(digits)) {
            setKeyListener(DigitsKeyListener.getInstance(digits));
        }
    }

    /**
     * 设置内容的类型
     * @param contentType   类型
     */
    public void setContentType(int contentType) {
        this.contentType = contentType;
        initType();
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            ContentWithSpaceEditText.this.start = start;
            ContentWithSpaceEditText.this.before = before;
            ContentWithSpaceEditText.this.count = count;
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s == null) {
                return;
            }
            //判断是否是在中间输入，需要重新计算
            boolean isMiddle = (start + count) < (s.length());
            //在末尾输入时，是否需要加入空格
            boolean isNeedSpace = false;
            if (!isMiddle && isSpace(s.length())) {
                isNeedSpace = true;
            }
            if (isMiddle || isNeedSpace || count > 1) {
                String newStr = s.toString();
                newStr = newStr.replace(" ", "");
                StringBuilder sb = new StringBuilder();
                int spaceCount = 0;
                for (int i = 0; i < newStr.length(); i++) {
                    sb.append(newStr.substring(i, i+1));
                    //如果当前输入的字符下一位为空格(i+1+1+spaceCount)，因为i是从0开始计算的，所以一开始的时候需要先加1
                    if(isSpace(i + 2 + spaceCount)){
                        sb.append(" ");
                        spaceCount += 1;
                    }
                }
                removeTextChangedListener(watcher);
                s.replace(0, s.length(),sb);
                //如果是在末尾的话,或者加入的字符个数大于零的话（输入或者粘贴）
                if (!isMiddle || count > 1) {
                    setSelection(s.length() <= maxLength ? s.length() : maxLength);
                } else {
                    //如果是删除
                    if (count == 0) {
                        //如果删除时，光标停留在空格的前面，光标则要往前移一位
                        if (isSpace(start - before + 1)) {
                            setSelection((start - before) > 0 ? start - before : 0);
                        } else {
                            setSelection((start - before + 1) > s.length() ? s.length() : (start - before + 1));
                        }
                    }
                    //如果是增加
                    else {
                        if (isSpace(start - before + count)) {
                            setSelection((start + count - before + 1) < s.length() ? (start + count - before + 1) : s.length());
                        } else {
                            setSelection(start + count - before);
                        }
                    }
                }
                addTextChangedListener(watcher);
            }
        }
    };

    public String getTextWithoutSpace() {
        return super.getText().toString().replace(" ", "");
    }

    public boolean checkTextRight(){
        String text = getTextWithoutSpace();
        //这里做个简单的内容判断
        if (contentType == TYPE_PHONE) {
            if (TextUtils.isEmpty(text)) {
                showShort(getContext(), "手机号不能为空，请输入正确的手机号");
            } else if (text.length() < 11) {
                showShort(getContext(), "手机号不足11位，请输入正确的手机号");
            } else {
                return true;
            }
        } else if (contentType == TYPE_BANK_CARD) {
            if (TextUtils.isEmpty(text)) {
                showShort(getContext(), "银行卡号不能为空，请输入正确的银行卡号");
            } else if (text.length() < 14) {
                showShort(getContext(), "银行卡号位数不正确，请输入正确的银行卡号");
            } else {
                return true;
            }
        } else if (contentType == TYPE_ID_CARD) {
            if (TextUtils.isEmpty(text)) {
                showShort(getContext(), "身份证号不能为空，请输入正确的身份证号");
            } else if (text.length() < 18) {
                showShort(getContext(), "身份证号不正确，请输入正确的身份证号");
            } else {
                return true;
            }
        }
        return false;
    }

    private void showShort(Context context,String msg){
        Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private boolean isSpace(int length) {
        if (contentType == TYPE_PHONE) {
            return isSpacePhone(length);
        } else if (contentType == TYPE_BANK_CARD) {
            return isSpaceCard(length);
        } else if (contentType == TYPE_ID_CARD) {
            return isSpaceIDCard(length);
        }
        return false;
    }

    private boolean isSpacePhone(int length) {
        return length >= 4 && (length == 4 || (length + 1) % 5 == 0);
    }

    private boolean isSpaceCard(int length) {
        return length % 5 == 0;
    }

    private boolean isSpaceIDCard(int length) {
        return length > 6 && (length == 7 || (length - 2) % 5 == 0);
    }

}
