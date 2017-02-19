package projekt.artur.projektkalkulator;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    double last, present, result;
    boolean lastNumeric, lastDot, firstAction, isMinus, isEq, lastEq, lastZero;
    int lastBtn;
    int lenN = 0;
    int op = 0;
    String tV;

    @BindView(R.id.textView) TextView textView;
    @BindView(R.id.textViewS) TextView textViewS;
    @BindView(R.id.btnOne) Button btnOne;
    @BindView(R.id.btnTwo) Button btnTwo;
    @BindView(R.id.btnThree) Button btnThree;
    @BindView(R.id.btnFour) Button btnFour;
    @BindView(R.id.btnFive) Button btnFive;
    @BindView(R.id.btnSix) Button btnSix;
    @BindView(R.id.btnSeven) Button btnSeven;
    @BindView(R.id.btnEight) Button btnEight;
    @BindView(R.id.btnNine) Button btnNine;
    @BindView(R.id.btnZero) Button btnZero;
    @BindView(R.id.btnPlus) Button btnPlus;
    @BindView(R.id.btnMinus) Button btnMinus;
    @BindView(R.id.btnMulti) Button btnMulti;
    @BindView(R.id.btnDivide) Button btnDivide;
    @BindView(R.id.btnDot) Button btnDot;
    @BindView(R.id.btnClear) Button btnClear;
    @BindView(R.id.btnEqual) Button btnEqual;

    @OnClick(R.id.btnOne) public void btnOne() { number(btnOne); }
    @OnClick(R.id.btnTwo) public void btnTwo() { number(btnTwo); }
    @OnClick(R.id.btnThree) public void btnThree() { number(btnThree); }
    @OnClick(R.id.btnFour) public void btnFour() { number(btnFour); }
    @OnClick(R.id.btnFive) public void btnFive() { number(btnFive); }
    @OnClick(R.id.btnSix) public void btnSix() { number(btnSix); }
    @OnClick(R.id.btnSeven) public void btnSeven() { number(btnSeven); }
    @OnClick(R.id.btnEight) public void btnEight() { number(btnEight); }
    @OnClick(R.id.btnNine) public void btnNine() { number(btnNine); }

    @OnClick(R.id.btnPlus) public void btnPlus() { body (btnPlus, 1); }
    @OnClick(R.id.btnMinus) public void btnMinus() { body (btnMinus, 2); }
    @OnClick(R.id.btnMulti) public void btnMulti() { body (btnMulti, 3); }
    @OnClick(R.id.btnDivide) public void btnDivide() { body (btnDivide, 4); }

    @OnClick(R.id.btnClear) public void btnClear() { clear(); }

    @OnClick(R.id.btnEqual) public void btnEqual() {

        if (firstAction) {
            if (lastNumeric) eqAct();
            else toast("Najpierw podaj liczbę");
        }

        else {
            if (op == 0) toast("Najpierw uzupełnij działanie");
            else eqAct();
        }

    }

    @OnClick(R.id.btnDot) public void btnDot() {

        if (!lastDot && !lastEq) {

            if (lastNumeric) {
                textView.append(".");
                textViewS.append(".");
                lastNumeric = false;
                lastDot = true;
                lastZero = false;
            }
            else toast("Najpierw podaj liczbę");

        }
    }

    @OnClick(R.id.btnZero) public void btnZero() {

        if (lenN == 0) {

            if (op != 4) {
                number(btnZero);
                lastZero = true;
            }
            else toast("Nieprawidłowe działanie");

        }
        else {

            if (lenN == 1) {
                if (!lastZero) number(btnZero);
                else if (lastDot) number(btnZero);
            }
            else number(btnZero);

        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }



    private void toast(String str) {  Toast.makeText(this, str, Toast.LENGTH_LONG).show();  }


    private void eqAct() {

        action();
        lastEq = true;
    }


    private void targetZero() {

        lastNumeric = false;
        lastDot = false;
        isMinus = false;
        lenN = 0;
    }


    private void clear() {

        textView.setText("");
        textViewS.setText("");
        targetZero();
        firstAction = false;
        isEq = false;
        lastEq = false;
        lastZero = false;
        lastBtn = 0;
        op = 0;
        last = 0;
        present = 0;
        result = 0;
    }


    private void numAction (Button f) {

        textView.append(f.getText());
        textViewS.append(f.getText());
        lastNumeric = true;
        lenN++;
        lastZero = false;
    }


    private void number (Button c) {

        if (!lastEq) {

            if (lenN < 15) {

                if (lenN == 1) {

                    if (lastZero) {

                        textView.setText("");
                        textViewS.setText("");
                        numAction(c);
                    }
                    else numAction(c);
                }
                else numAction(c);
            }
            else toast("Osiągnięto maksymalną liczbę cyfr");
        }
        else toast("Najpierw wybierz działanie");
    }



    private void calculator() {

        switch (op) {

            case 1:
                result = last + present;
                last = result;
                break;

            case 2:
                result = last - present;
                last = result;
                break;

            case 3:
                result = last * present;
                last = result;
                break;

            case 4:
                result = last / present;
                last = result;
                break;
        }
    }


    private void action() {

        present = Double.parseDouble(textViewS.getText().toString());
        calculator();

        if (result == 0) textView.setText("0");

        else {

            BigDecimal bigDec = new BigDecimal(result);
            String bigStr = bigDec.setScale(10, BigDecimal.ROUND_DOWN).toString();
            int ini = 9;

            while (bigStr.endsWith("0") && !bigStr.endsWith(".0") ) {

                bigStr = bigDec.setScale(ini, BigDecimal.ROUND_DOWN).toString();
                ini--;
            }

            if (bigStr.endsWith(".0")) bigStr = bigDec.setScale(ini, BigDecimal.ROUND_DOWN).toString();

            if (bigStr.length() > 15) { textView.setText(String.valueOf(result)); }
            else textView.setText(bigStr);
        }

        targetZero();
        lastNumeric = true;

    }


    private void body(Button a, int b) {

        if (lastNumeric) {

            if (firstAction) {
                if (lastEq) {
                    last = result;
                    lastEq = false;
                }
                else {
                    action();
                    lastEq = false;
                }
            }
            else {
                last = Double.parseDouble(textView.getText().toString());
                lastNumeric = false;
            }

            textViewS.setText("");
            textView.append(a.getText());
            op = b;
            lastBtn = b;
            targetZero();
            firstAction = true;

        }
        else if (!lastDot) {

            if (firstAction) {
                if (b != lastBtn) {
                    tV = textView.getText().toString();
                    textView.setText(tV.substring(0, tV.length() - 1));
                    textView.append(a.getText());
                    lastBtn = b;
                }
            }
            else if (b == 2 && !isMinus) {
                textView.append(btnMinus.getText());
                isMinus = true;
            }

        }
        else toast("Najpierw podaj liczbę");
    }

}