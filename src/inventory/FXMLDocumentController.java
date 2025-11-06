package inventory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import services.DatabaseCounters;

public class FXMLDocumentController implements Initializable {

    @FXML
    private AnchorPane si_loginForm;

    @FXML
    private TextField si_username;

    @FXML
    private Button si_loginBtn;

    @FXML
    private PasswordField si_password;

    @FXML
    private TextField su_username;

    @FXML
    private PasswordField su_password;

    @FXML
    private ComboBox<?> su_question;

    @FXML
    private TextField su_answer;

    @FXML
    private AnchorPane side_form;

    @FXML
    private Button side_CreatBtn;

    @FXML
    private Button side_alreadyHave;

    @FXML
    private AnchorPane fp_question_form;

    @FXML
    private TextField fp_username;

    @FXML
    private ComboBox<?> fp_question;

    @FXML
    private TextField fp_answer;

    @FXML
    private AnchorPane np_newPasswordFrom;

    @FXML
    private PasswordField np_newPassword;

    @FXML
    private PasswordField np_confirmPassword;

    private Connection connect;
    private PreparedStatement ps;
    private ResultSet rs;

    private Alert alert;

    public void errorAlert(String ms) {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText(ms);
        alert.showAndWait();
    }

    public void infoAlert(String ms) {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Message");
        alert.setHeaderText(null);
        alert.setContentText(ms);
        alert.showAndWait();
    }

    public void trialExpiredAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Trial Period Expired");
        alert.setHeaderText("⚠️ النسخة التجريبية انتهت");
        alert.setContentText(
                "لقد انتهت فترة الاستخدام التجريبية لهذا النظام.\n\n"
                + "الرجاء التواصل مع الدعم الفني للحصول على النسخة المدفوعة:\n"
                + "📩 عبر رسائل الصفحة الرسمية\n"
                + "📞 أو على رقم الهاتف: 01023977965\n\n"
                + "شكرًا لاستخدامك نظامنا، ونتطلع لخدمتك بشكل أفضل في النسخة الكاملة."
        );
        alert.showAndWait();
    }

    public void loginBTN() throws SQLException {
        DatabaseCounters counter = new DatabaseCounters();
        if ((si_username.getText().isEmpty()) || (si_password.getText().isEmpty())) {
            errorAlert("ادخل اسم المستخدم و رقم المرور");
        } else {
            if (counter.isAvailable("invoices") && counter.isAvailable("purchase_invoices") && counter.isAvailable("settlement_bonds")) {
                String checkData = "SELECT username, id FROM employee WHERE username = ? and password = ?";
                connect = database.getConnection();
                try {
                    ps = connect.prepareStatement(checkData);
                    ps.setString(1, si_username.getText());
                    ps.setString(2, si_password.getText());
                    rs = ps.executeQuery();
                    if (rs.next()) {

                        infoAlert("تم تسجيل الدخول بنجاح");

                        // تخزين بيانات المستخدم العامة
                        data.username = rs.getString("username");
                        data.employeeId = rs.getInt("id");

                        // تحميل الفورم الرئيسي وربطه
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainForm.fxml"));
                        Parent root = loader.load();

                        // تمرير البيانات للكلاس الرئيسي
                        mainFormController controller = loader.getController();
                        controller.initUserPermissions(data.employeeId, data.username);

                        Stage stage = new Stage();
                        stage.setTitle("Zentra Inventory System");
                        stage.setMinWidth(1100);
                        stage.setMinHeight(600);
                        stage.setScene(new Scene(root));
                        stage.show();

                        // إغلاق نافذة تسجيل الدخول
                        si_loginBtn.getScene().getWindow().hide();
                    } else {
                        errorAlert("اسم المستخدم او كلمة المرور خاطئة");
                    }

                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                trialExpiredAlert();
            }
        }
    }

    public void regBtn() throws SQLException {
        if ((su_username.getText().isEmpty()) || (su_password.getText().isEmpty())
                || (su_question.getSelectionModel().getSelectedItem() == null) || (su_answer.getText().isEmpty())) {
            errorAlert("رجاء ادخل كل البيانات");
        } else {
            String regData = "INSERT INTO employee (username, password, question, answer, date) VALUES (?, ?, ?, ?, ?)";
            try (Connection connect = database.getConnection()) {

                // Check if username already exists
                String checkUsername = "SELECT username FROM employee WHERE username = ?";
                try (PreparedStatement psCheck = connect.prepareStatement(checkUsername)) {
                    psCheck.setString(1, su_username.getText());
                    try (ResultSet rsCheck = psCheck.executeQuery()) {
                        if (rsCheck.next()) {
                            errorAlert(su_username.getText() + " مستخدم بالفعل");
                            return;
                        }
                    }
                }

                if (su_password.getText().length() < 8) {
                    errorAlert("يجب ألا تقل كلمة المرور عن ٨ أحرف");
                    return;
                }

                Date date = new Date();
                java.sql.Date sqldDate = new java.sql.Date(date.getTime());

                try (PreparedStatement ps = connect.prepareStatement(regData)) {
                    ps.setString(1, su_username.getText());
                    ps.setString(2, su_password.getText());
                    ps.setString(3, su_question.getSelectionModel().getSelectedItem().toString());
                    ps.setString(4, su_answer.getText());
                    ps.setDate(5, sqldDate);
                    ps.executeUpdate();
                }

                infoAlert("تم تسجيل الحساب بنجاح");

                // Clear fields
                su_username.clear();
                su_password.clear();
                su_answer.clear();
                su_question.getSelectionModel().clearSelection();

                // Transition to login
                TranslateTransition slider = new TranslateTransition();
                slider.setNode(side_form);
                slider.setToX(0);
                slider.setDuration(Duration.seconds(.4));
                slider.setOnFinished((ActionEvent e) -> {
                    side_CreatBtn.setVisible(true);
                    side_alreadyHave.setVisible(false);
                });
                slider.play();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void QuuestionList(ComboBox<?> CBox) {
        String[] questionList = {"ما هو لونك المفضل؟", "ما هو رقم هاتف المبرمج؟", "ما هو اسم المبرمج؟"};

        List<String> listQ = new ArrayList<>();
        for (String data : questionList) {
            listQ.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listQ);

        CBox.setItems(listData);
    }

    public void switchForgotPass() {
        fp_question_form.setVisible(true);
        si_loginForm.setVisible(false);
        QuuestionList(fp_question);
        fp_username.clear();
        fp_answer.clear();
        fp_question.getSelectionModel().clearSelection();
    }

    public void proceedBtn() throws SQLException {
        if ((fp_username.getText().isEmpty())
                || (fp_question.getSelectionModel().getSelectedItem() == null)
                || (fp_answer.getText().isEmpty())) {

            errorAlert("ادخل كل البيانات من فضلك");

        } else {
            String checkUsername = "SELECT username FROM employee WHERE username = ? AND question = ? AND answer = ?";
            try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(checkUsername)) {

                ps.setString(1, fp_username.getText());
                ps.setString(2, fp_question.getSelectionModel().getSelectedItem().toString());
                ps.setString(3, fp_answer.getText());

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        np_newPasswordFrom.setVisible(true);
                        fp_question_form.setVisible(false);
                    } else {
                        errorAlert("البيانات خاطئة");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void changePassBtn() throws SQLException {
        if ((np_newPassword.getText().isEmpty()) || (np_confirmPassword.getText().isEmpty())) {
            errorAlert("ادخل كل البيانات من فضلك");
        } else if (np_newPassword.getText().length() < 8) {
            errorAlert("يجب ألا تقل كلمة المرور عن ٨ أحرف");
        } else if (np_newPassword.getText().equals(np_confirmPassword.getText())) {
            String updatePassword = "UPDATE employee SET password = ? WHERE username = ?";
            try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(updatePassword)) {

                ps.setString(1, np_newPassword.getText());
                ps.setString(2, fp_username.getText());
                ps.executeUpdate();

                infoAlert("تم تحديث كلمة المرور بنجاح");

                si_loginForm.setVisible(true);
                np_newPasswordFrom.setVisible(false);
                fp_question_form.setVisible(false);

                np_newPassword.clear();
                np_confirmPassword.clear();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            errorAlert("يجب أن تكون كلمة المرور متطابقة");
        }
    }

    public void backBtn() {
        si_loginForm.setVisible(true);
        np_newPasswordFrom.setVisible(false);
        fp_question_form.setVisible(false);
    }

    public void switchForm(ActionEvent event) {

        TranslateTransition slider = new TranslateTransition();
        if (event.getSource() == side_CreatBtn) {
            slider.setNode(side_form);
            slider.setToX(300);
            slider.setDuration(Duration.seconds(.4));
            slider.setOnFinished((ActionEvent e) -> {
                side_alreadyHave.setVisible(true);
                side_CreatBtn.setVisible(false);

                fp_question_form.setVisible(false);
                si_loginForm.setVisible(true);
                np_newPasswordFrom.setVisible(false);

                QuuestionList(su_question);
            });
            slider.play();
        } else if (event.getSource() == side_alreadyHave) {
            slider.setNode(side_form);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.4));
            slider.setOnFinished((ActionEvent e) -> {
                side_CreatBtn.setVisible(true);
                side_alreadyHave.setVisible(false);

                fp_question_form.setVisible(false);
                si_loginForm.setVisible(true);
                np_newPasswordFrom.setVisible(false);
            });
            slider.play();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
