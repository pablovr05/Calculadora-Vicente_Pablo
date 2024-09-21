package com.project;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Controller {

    private BigDecimal ans = BigDecimal.ZERO;
    private BigDecimal val1 = BigDecimal.ZERO;
    private BigDecimal val2 = BigDecimal.ZERO;
    private String action = "";
    
    @FXML
    private Text textCamp;

    @FXML
    private void porcentageAction(ActionEvent event) {
        textCamp.setText("/");
    }

    @FXML
    private void CEAction(ActionEvent event) {
        textCamp.setText("0");
    }

    @FXML
    private void CAction(ActionEvent event) {
        resetCalculator();
    }

    @FXML
    private void retroAction(ActionEvent event) {
        retro();
    }

    @FXML
    private void numberAction(ActionEvent event) {
        Button source = (Button) event.getSource();
        updateCalcNum(source.getText());
    }

    @FXML
    private void operatorAction(ActionEvent event) {
        Button source = (Button) event.getSource();
        String operator = source.getText();

        if (operator.equals("-") && textCamp.getText().isEmpty()) {
            updateCalcNum("-");
        } else if (!textCamp.getText().isEmpty()) {
            val1 = stringToBigDecimal(textCamp.getText());
            action = operator.equals("x") ? "*" : operator;
            textCamp.setText("");
        }
    }

    @FXML
    private void igualAction(ActionEvent event) {
        calcularIgual();
    }

    @FXML
    private void ansAction(ActionEvent event) {
        setDisplayText(ans);
        val1 = ans;
    }

    @FXML
    private void comaAction(ActionEvent event) {
        String currentText = textCamp.getText();
        if (!currentText.contains(".")) {
            updateCalcNum(".");
        }
    }

    private void updateCalcNum(String character) {
        String currentText = textCamp.getText();

        if (character.matches("[0-9\\.]")) {
            if (currentText.equals("Ans") || (currentText.equals("0") && !character.equals("."))) {
                textCamp.setText(character);
            } else {
                textCamp.setText(currentText + character);
            }
        } else if (character.equals("-") && currentText.isEmpty()) {
            textCamp.setText("-");
        }
    }

    private void retro() {
        String currentText = textCamp.getText();
        if (currentText.length() > 0) {
            textCamp.setText(currentText.substring(0, currentText.length() - 1));
        }
    }

    private BigDecimal stringToBigDecimal(String str) {
        if (str.equals("Ans")) {
            return ans;
        }
        try {
            return new BigDecimal(str);
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }

    private void calcularIgual() {
        if (!textCamp.getText().isEmpty()) {
            val2 = stringToBigDecimal(textCamp.getText());
            ans = calcularOperacion(val1, val2, action);
            setDisplayText(ans);
            val1 = ans;
        }
    }

    private BigDecimal calcularOperacion(BigDecimal val1, BigDecimal val2, String action) {
        BigDecimal result;
        switch (action) {
            case "+":
                result = val1.add(val2);
                break;
            case "-":
                result = val1.subtract(val2);
                break;
            case "*":
                result = val1.multiply(val2);
                break;
            case "/":
                if (val2.compareTo(BigDecimal.ZERO) != 0) {
                    result = val1.divide(val2, 10, RoundingMode.HALF_UP);
                } else {
                    textCamp.setText("Error");
                    return BigDecimal.ZERO;
                }
                break;
            default:
                result = BigDecimal.ZERO;
        }
        return result.setScale(10, RoundingMode.HALF_UP);
    }
    

    private void setDisplayText(BigDecimal number) {
        // Si es un número entero
        if (number.stripTrailingZeros().scale() <= 0) {
            textCamp.setText(String.valueOf(number.toBigInteger()));
        } else {
            textCamp.setText(number.setScale(10, RoundingMode.HALF_UP).toPlainString());
        }
    }

    private void resetCalculator() {
        textCamp.setText("0");
        val1 = BigDecimal.ZERO;
        val2 = BigDecimal.ZERO;
        ans = BigDecimal.ZERO;
        action = "";
    }
}
