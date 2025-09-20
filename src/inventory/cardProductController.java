package inventory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class cardProductController implements Initializable {

    @FXML
    private AnchorPane card_form;

    @FXML
    private Label prod_size;

    @FXML
    private Label prod_name;

    @FXML
    private Label prod_price;

    @FXML
    private Label prod_color;

    @FXML
    private ImageView prod_imageView;

    @FXML
    private Spinner<Integer> prod_spinner;

    private Image image;
    private SpinnerValueFactory<Integer> spin;
    private Connection connect;
    private ResultSet rs;
    private PreparedStatement ps;
    private Alert alert;

    private double price;

    public void E_Alert(String ms, Alert.AlertType Atype) {
        alert = new Alert(Atype);
        alert.setTitle("Warning Message");
        alert.setHeaderText(null);
        alert.setContentText(ms);
        alert.showAndWait();
    }

    public void Confirmation_Alert(String ms) {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning Message");
        alert.setHeaderText(null);
        alert.setContentText(ms);
    }

    public void setData(productData prodData) {
        prod_name.setText(prodData.getModel());
        prod_price.setText(prodData.getRealPrice().toString());
        price = prodData.getRealPrice();
    }

    public void setQuantity() {
        spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        prod_spinner.setValueFactory(spin);
    }

    public void addtoBillBtn() {
        data.qty = prod_spinner.getValue();
        int check = 0;
        String checkAvailable = "SELECT mNumberavailable FROM product WHERE model = ? ";
        try {
            connect = database.getConnection();
            ps = connect.prepareStatement(checkAvailable);
            ps.setString(1, prod_name.getText());
            rs = ps.executeQuery();
            if (rs.next()) {
                check = rs.getInt("mNumberavailable");
            }
            if (data.qty > check) {
                E_Alert("العدد المتاح لهذا المنتج: " + check, Alert.AlertType.ERROR);
            } else {
                data.totalp = data.qty * price;
                data.Total_invoice_price += data.totalp;
                setQuantity();
                mainFormController mForm = new mainFormController();
//                mForm.Total_invoice();
//                mForm.getMenu_to;

//                Total_invoice();
//                SalesList.add(new InvoiceItem(prod_name.getText(), qty, totalp));
//                salesShowData();
//                String insertSales = "INSERT INTO sales (em_username, quantity, total_price, payment_method, date)"
//                        + "VALUES(?, ?, ?, ?, ?)";
//                ps = connect.prepareStatement(insertSales);
//                ps.setString(1, data.username);
//                ps.setString(2, String.valueOf(qty));
//                totalp = qty * price;
//                ps.setString(3, String.valueOf(totalp));
//                Date date = new Date();
//                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
//                ps.setString(5, String.valueOf(sqlDate));
//
//                ps.executeUpdate();
//
//                SalesList.add(new productData(prod_name.getText(), qty, totalp));
//                menu_tableView.setItems(SalesList);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setQuantity();
//        salesDataList();

        //salesShowData();
        //SalesList.add(new InvoiceItem("Test Model", 2, 300.0));
    }

}
