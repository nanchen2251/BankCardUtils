package com.nanchen.bankcardutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.TextView;

import com.nanchen.bankcardutil.BankInfoUtil;
import com.nanchen.bankcardutil.ContentWithSpaceEditText;

public class MainActivity extends AppCompatActivity {

    private ContentWithSpaceEditText mTvAccountNo;
    private ContentWithSpaceEditText mTvCardNo;
    private TextView mTvBankName;
    private ContentWithSpaceEditText mTvPhone;
    private TextView mTvCardType;
    private TextView mTvBankId;
    private BankInfoUtil mInfoUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvAccountNo = (ContentWithSpaceEditText) findViewById(R.id.tv_account_no);
        mTvCardNo = (ContentWithSpaceEditText) findViewById(R.id.tv_card_no);
        mTvBankName = (TextView) findViewById(R.id.tv_bank_name);
        mTvPhone = (ContentWithSpaceEditText) findViewById(R.id.tv_phone);
        mTvCardType = (TextView) findViewById(R.id.tv_bank_type);
        mTvBankId = (TextView) findViewById(R.id.tv_bank_id);

        mTvAccountNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                onCheckCard();
            }
        });
    }

    private void onCheckCard() {
        String cardNum = mTvAccountNo.getText().toString().replace(" ", "");
        if (!TextUtils.isEmpty(cardNum) && checkBankCard(cardNum)) {
            mInfoUtil = new BankInfoUtil(cardNum);
            mTvBankName.setText(mInfoUtil.getBankName());
            mTvBankId.setText(mInfoUtil.getBankId());
            mTvCardType.setText(mInfoUtil.getCardType());
        } else {
            mTvCardType.setText("");
            mTvCardType.setHint("输入卡号自动匹配");
            mTvBankName.setText("");
            mTvBankName.setHint("输入卡号自动匹配");
            mTvBankId.setText("");
            mTvBankId.setText("输入卡号自动匹配");
        }
    }

    /**
     * 校验过程：
     * 1、从卡号最后一位数字开始，逆向将奇数位(1、3、5等等)相加。
     * 2、从卡号最后一位数字开始，逆向将偶数位数字，先乘以2（如果乘积为两位数，将个位十位数字相加，即将其减去9），再求和。
     * 3、将奇数位总和加上偶数位总和，结果应该可以被10整除。
     * 校验银行卡卡号
     */
    public static boolean checkBankCard(String bankCard) {
        if (bankCard.length() < 15 || bankCard.length() > 19) {
            return false;
        }
        char bit = getBankCardCheckCode(bankCard.substring(0, bankCard.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return bankCard.charAt(bankCard.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhn 校验算法获得校验位
     */
    public static char getBankCardCheckCode(String nonCheckCodeBankCard) {
        if (nonCheckCodeBankCard == null || nonCheckCodeBankCard.trim().length() == 0
                || !nonCheckCodeBankCard.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeBankCard.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }
}
