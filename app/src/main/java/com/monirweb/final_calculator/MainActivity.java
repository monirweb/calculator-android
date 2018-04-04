package com.monirweb.final_calculator;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    public MainActivity() {

    }

    EditText editText;
    TextView textView;
    Button one, two, three, four, five, six, seven, eight, nine, zero, division, multiply, minus, plus, equals, dot,clear, reset;
    double num1=0,num2=0,result=0;
    int countOpr=0;
    int countEqual=0;
    int dotCount=0;
    String previousOpr="";
    int numOfMinus=0;
    String opr="";
    boolean isOprClick=false;
    boolean isEqualClick=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
        textView=(TextView) findViewById(R.id.textView);
        one = (Button) findViewById(R.id.one);
        two = (Button) findViewById(R.id.two);
        three = (Button) findViewById(R.id.three);
        four = (Button) findViewById(R.id.four);
        five = (Button) findViewById(R.id.five);
        six = (Button) findViewById(R.id.six);
        seven = (Button) findViewById(R.id.seven);
        eight = (Button) findViewById(R.id.eight);
        nine = (Button) findViewById(R.id.nine);
        zero = (Button) findViewById(R.id.zero);
        division = (Button) findViewById(R.id.division);
        multiply = (Button) findViewById(R.id.multiply);
        minus = (Button) findViewById(R.id.minus);
        plus = (Button) findViewById(R.id.plus);
        equals = (Button) findViewById(R.id.equals);
        dot = (Button) findViewById(R.id.dot);
        clear = (Button) findViewById(R.id.clear);
        reset = (Button) findViewById(R.id.reset);

        one.setOnClickListener(action);
        one.setTag(1);
        two.setOnClickListener(action);
        two.setTag(2);
        three.setOnClickListener(action);
        three.setTag(3);
        four.setOnClickListener(action);
        four.setTag(4);
        five.setOnClickListener(action);
        five.setTag(5);
        six.setOnClickListener(action);
        six.setTag(6);
        seven.setOnClickListener(action);
        seven.setTag(7);
        eight.setOnClickListener(action);
        eight.setTag(8);
        nine.setOnClickListener(action);
        nine.setTag(9);
        zero.setOnClickListener(action);
        zero.setTag(0);
        division.setOnClickListener(action);
        division.setTag("÷");
        multiply.setOnClickListener(action);
        multiply.setTag("*");
        plus.setOnClickListener(action);
        plus.setTag("+");
        minus.setOnClickListener(action);
        equals.setOnClickListener(action);
        dot.setOnClickListener(action);
        clear.setOnClickListener(undo);
        reset.setOnClickListener(undo);

        //this code hides the keyboard on click
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            }
        });

    }
    //End of method onCreate



    //calculate method
    public double calculate(String operator,double num1,double num2){
        result=0;
        switch (operator){
            case "+":
                result=num1+num2;
                break;
            case "-":
                result=num1-num2;
                break;
            case "*":
                result=num1*num2;
                break;
            case "÷":
                if (num2==0){
                    return Double.NaN;
                }else {
                    result=num1/num2;
                }
                break;

            default:
                break;
        }
        return result;
    }

    //on click method for numeric and operation buttons
    View.OnClickListener action =new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id=view.getId();
            switch (id) {

                //numeric
                case R.id.one:
                case R.id.two:
                case R.id.three:
                case R.id.four:
                case R.id.five:
                case R.id.six:
                case R.id.seven:
                case R.id.eight:
                case R.id.nine:
                case R.id.zero:

                {
                    String textOfButton = view.getTag().toString();

                    if (isOprClick) {
                        if (!editText.getText().toString().startsWith("-")){
                            num1 = Double.valueOf(editText.getText().toString());
                            editText.setText("");
                        }
                    }
                    if (editText.getText().toString().equals("0")) {
                        editText.setText(textOfButton);
                        isOprClick = false;
                    } else {
                        editText.setText(editText.getText().toString() + textOfButton);
                        isOprClick = false;
                    }
                }
                break;


                //dot button
                case R.id.dot:
                    if (!editText.getText().toString().contains(".")) {
                        if (!editText.getText().toString().equals("")) {
                            editText.setText(editText.getText().toString() + ".");
                        }
                        isOprClick = false;
                    }
                    break;


                //plus,multiply and division button
                case R.id.plus:
                case R.id.multiply:
                case R.id.division: {
                    String textOfOperation = view.getTag().toString();
                    //num1
                    if (countOpr == 0) {
                        if ((editText.getText().toString().equals("") && num1 == 0 && num2 == 0) || editText.getText().toString().equals("-")) {
                            textView.setText(textOfOperation);
                        } else {
                            countOpr++;
                            num1 = Double.parseDouble(editText.getText().toString());
                            opr = textOfOperation;
                            previousOpr = textOfOperation;
                            textView.setText(textOfOperation);
                            isOprClick = true;
                        }
                    }
                    //num2
                    else {
                        if (!isEqualClick) {
                            num2 = Double.parseDouble(editText.getText().toString());
                            editText.setText(Double.toString(calculate(opr, num1, num2)));
                            opr = textOfOperation;
                            previousOpr = textOfOperation;
                            textView.setText(textOfOperation);
                            num1 = Double.parseDouble(editText.getText().toString());
                            isOprClick = true;
                            isEqualClick = false;
                        } else {
                            isEqualClick = false;
                            num1 = Double.parseDouble(editText.getText().toString());
                            opr = textOfOperation;
                            previousOpr = textOfOperation;
                        }
                    }
                }
                break;


                case R.id.minus:
                    if (countOpr == 0) {
                        if (editText.getText().toString().equals("") && !isOprClick) {
                            editText.setText(editText.getText().append("-"));
                        } else if (editText.getText().toString().equals("-")) {
                            editText.setText("-");
                        } else {
                            countOpr++;
                            num1 = Float.parseFloat(editText.getText().toString());
                            opr = "-";
                            previousOpr = "-";
                            textView.setText("-");
                            isOprClick = true;
                        }
                    }
                    //num2
                    else {
                        if (opr.equals("÷")||opr.equals("*")){
                            editText.setText("");
                            editText.setText(editText.getText().append("-"));
                        }else {
                            if (!isEqualClick) {
                                num2 = Double.parseDouble(editText.getText().toString());
                                editText.setText(Double.toString(calculate(opr, num1, num2)));
                                opr = "-";
                                previousOpr = "-";
                                textView.setText("-");
                                num1 = Double.parseDouble(editText.getText().toString());
                                isOprClick = true;
                                isEqualClick = false;
                            }
                            else {
                                isEqualClick = false;
                                num1 = Double.parseDouble(editText.getText().toString());
                                opr = "-";
                                previousOpr = "-";
                            }
                        }
                    }
                break;



                case R.id.equals:

                    if(editText.getText().toString().equals("")&&!isOprClick){
                            textView.setText("=");
                            editText.setText(editText.getText().toString());
                    }else {
                        if (!previousOpr.equals("=")){
                            num2 = Double.valueOf(editText.getText().toString());
                            editText.setText(Double.toString(calculate(opr, num1, num2)));
                            num1 = Double.valueOf(editText.getText().toString());
                            isEqualClick = true;
                            isOprClick = true;
                            previousOpr="=";
                        }else {
                            num1 = result;
                            editText.setText(Double.toString(calculate(opr, num1, num2)));
                            isEqualClick = true;
                            isOprClick = true;
                            previousOpr="=";
                        }
                    }
                break;

            }
        }
    };


    //on click method for reset and clear buttons
    View.OnClickListener undo =new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            int id=view.getId();
            switch (id){
                case R.id.clear:
                    if (editText.getText().toString().length()>0){
                        String text=editText.getText().toString();
                        String clear=text.substring(0,text.length()-1);
                        editText.setText(clear);
                    }
                    break;
                case R.id.reset:
                    editText.setText("");
                    num1=0;num2=0;result=0;
                    countOpr =0;
                    countEqual=0;
                    numOfMinus=0;
                    opr="";
                    isOprClick=false;
                    isEqualClick=false;
                    textView.setText("");
                    break;
                default:
                    break;
            }
        }
    };



}
//End of class MainActivity