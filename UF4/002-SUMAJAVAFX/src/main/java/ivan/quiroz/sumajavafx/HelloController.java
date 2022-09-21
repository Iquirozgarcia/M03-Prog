package ivan.quiroz.sumajavafx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {
    //<editor-fold desc="FXML">
    @FXML
    private TextField TFNum1;
    @FXML
    private TextField TFNum2;
    @FXML
    private Button BTSuma;
    @FXML
    private Button BTResta;
    @FXML
    private Label LBTexto;
    //</editor-fold>
    private Integer num1, num2, res;
    public void setBTSuma() {
        num1 = Integer.parseInt(TFNum1.getText());
        num2 = Integer.parseInt(TFNum2.getText());
        res = Integer.parseInt(String.valueOf((num1 + num2)));
        LBTexto.setText(num1+"+"+num2+"="+ res);
    }
    public void setBTResta() {
        num1 = Integer.parseInt(TFNum1.getText());
        num2 = Integer.parseInt(TFNum2.getText());
        res = Integer.parseInt(String.valueOf((num1 - num2)));
        LBTexto.setText(num1+"-"+num2+"="+ res);
    }
}
