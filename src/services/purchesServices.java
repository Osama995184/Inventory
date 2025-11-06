/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import inventory.InvoiceItem;
import inventory.Invoices;
import inventory.Returns;
import inventory.data;
import inventory.database;
import inventory.productData;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;

/**
 *
 * @author OSOS
 */
public class purchesServices {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    alert al = new alert();

    // دى بتعمل فاتوره جديده وبتضيفها فى الدتا بيز
    public int insertInvoice(double totalQuantity, double totalBeforeDiscount, double discount,
            double totalAfterDiscount, String username,
            Double cashPay, Double instaPay, Double vodafonePay, Double deffered, String clientName, String clientPhone) {

        String sql = "INSERT INTO invoices (total_quantity, total_price_before_discount, discount_percentage, "
                + "total_price_after_discount, username, cashPay, instaPay, vodafonePay, deffered, clientName, clientPhone) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connect = database.getConnection(); PreparedStatement pst = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setDouble(1, totalQuantity);
            pst.setDouble(2, totalBeforeDiscount);
            pst.setDouble(3, discount);
            pst.setDouble(4, totalAfterDiscount);
            pst.setString(5, username);
            pst.setDouble(6, cashPay != null ? cashPay : 0.0);
            pst.setDouble(7, instaPay != null ? instaPay : 0.0);
            pst.setDouble(8, vodafonePay != null ? vodafonePay : 0.0);
            pst.setDouble(9, deffered != null ? deffered : 0.0);
            pst.setString(10, clientName);
            pst.setString(11, clientPhone);
            int affectedRows = pst.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("فشل إنشاء الفاتورة، لم يتم تعديل أي صف.");
            }
            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // ID الناتج
                } else {
                    throw new SQLException("فشل إنشاء الفاتورة، لم يتم الحصول على رقم الفاتورة.");
                }
            }

        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء حفظ الفاتورة في قاعدة البيانات", Alert.AlertType.ERROR);
            e.printStackTrace();
            return -1;
        }
    }

    // دى بتعدل عدد المنتج فى الداتا بيز
    public void updateProductQuantitiesAfterSale(ObservableList<InvoiceItem> SalesList) {
        String updateQuery = "UPDATE product SET mNumberavailable = mNumberavailable - ?, soldNo = soldNo + ? WHERE id = ?";
        String getWholePrice = "SELECT wholesaleprice, realprice FROM product WHERE id = ?";
        String insertQuery = "INSERT INTO sales (em_username, model_id, quantity, total_price, invoice_id, totalWholesalesPrice, model_name, realprice, warehouse) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connect = database.getConnection()) {
            connect.setAutoCommit(false);

            try (
                    PreparedStatement updateStmt = connect.prepareStatement(updateQuery); PreparedStatement selectWholePricePs = connect.prepareStatement(getWholePrice); PreparedStatement insertPs = connect.prepareStatement(insertQuery)) {
                for (InvoiceItem item : SalesList) {

                    // تحديث الكمية
                    updateStmt.setDouble(1, item.getQuantity());
                    updateStmt.setDouble(2, item.getQuantity());
                    updateStmt.setInt(3, item.getModelID());
                    updateStmt.addBatch();

                    // جلب سعر الجملة
                    selectWholePricePs.setInt(1, item.getModelID());
                    try (ResultSet rs = selectWholePricePs.executeQuery()) {
                        if (rs.next()) {
                            double wholePrice = rs.getDouble("wholesaleprice");
                            double realPrice = rs.getDouble("realprice");
                            double totalWholePrice = wholePrice * item.getQuantity();
                            double totalRealPrice = realPrice * item.getQuantity();

//                            double totalPriceAfterDiscount = item.getTotalP() - (item.getTotalP() * data.discount);
                            double totalPriceAfterDiscount = item.getTotalP();
                            BigDecimal bd = new BigDecimal(totalPriceAfterDiscount).setScale(0, RoundingMode.HALF_UP);
                            totalPriceAfterDiscount = bd.doubleValue();

                            // إدخال بيانات البيع
                            insertPs.setString(1, data.username);
                            insertPs.setInt(2, item.getModelID());
                            insertPs.setDouble(3, item.getQuantity());
                            insertPs.setDouble(4, totalPriceAfterDiscount);
                            insertPs.setInt(5, data.invoiceId);
                            insertPs.setDouble(6, totalWholePrice);
                            insertPs.setString(7, item.getModel());
                            insertPs.setDouble(8, totalRealPrice);
                            insertPs.setString(9, item.getWareHouse());

                            insertPs.addBatch();

                        } else {
                            System.err.println("لم يتم العثور على المنتج ID: " + item.getModelID());
                            throw new SQLException("فشل في العثور على سعر الجملة للمنتج");
                        }
                    }
                }

                updateStmt.executeBatch();
                insertPs.executeBatch();
                connect.commit();

            } catch (SQLException e) {
                connect.rollback();
                al.E_Alert("حدث خطأ أثناء تنفيذ عملية البيع. تم التراجع عن العملية.", Alert.AlertType.ERROR);
                e.printStackTrace();
            } finally {
                connect.setAutoCommit(true);
            }

            for (InvoiceItem item : SalesList) {
                recordItemMovement.recordItemMovement("خروج بيع", item.getQuantity(), data.invoiceId, "تم البيع من : " + item.getWareHouse(), item.getModelID(), item.getModel(), item.getWareHouse());
            }

        } catch (SQLException e) {
            al.E_Alert("فشل الاتصال بقاعدة البيانات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
    //دى بتكريت عميل جديد فى حاله فاتوره مبيعات لو العميل جديد

    public void addCustomerIfNotExists(String name, String phone) {
        String checkSql = "SELECT COUNT(*) FROM customers WHERE phone = ?";
        String insertSql = "INSERT INTO customers (name, phone) VALUES (?, ?)";

        try (Connection conn = database.getConnection(); PreparedStatement checkStmt = conn.prepareStatement(checkSql); PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {

            checkStmt.setString(1, phone);
//            checkStmt.setString(1, name);

            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                if (count == 0) {
                    insertStmt.setString(1, name);
                    insertStmt.setString(2, phone);
                    insertStmt.executeUpdate();
                    System.out.println("Customer added successfully.");
                } else {
                    System.out.println("Customer already exists.");
                }
            }

        } catch (SQLException e) {
            al.E_Alert(" حدث خطأ أثناء اضافة بيانات العميل", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    // دى بتجيب المنتجات اللى فى المخزن
    public ObservableList<productData> returnDataList() {
        ObservableList<productData> listData = FXCollections.observableArrayList();
        String sql = "SELECT id, model, mNumberavailable, realprice, type, color, size, unit "
                + "FROM product ORDER BY model COLLATE NOCASE";

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                productData prodData = new productData(
                        rs.getInt("id"),
                        rs.getString("model"),
                        rs.getDouble("mNumberavailable"),
                        rs.getDouble("realprice"),
                        rs.getString("type"),
                        rs.getString("color"),
                        rs.getString("size"),
                        rs.getString("unit")
                );
                listData.add(prodData);
            }

        } catch (SQLException e) {
            al.E_Alert("حدث خطأ اثناء تحميل المرتجعات", Alert.AlertType.ERROR);
            e.printStackTrace(); // يمكنك لاحقاً عرض رسالة باستخدام E_Alert مثلاً
        }

        return listData;
    }

    // دى بتحذف الفاتوره كامله بناء على رقم الفاتورة
    public boolean updateInvoiceById(Integer invoiceId, String Type) {
        StringBuilder sql1 = new StringBuilder();
        Double deffedValue = null;
        String text = null;
        Double returnsValue = null;

        String fetchSql = "SELECT total_price_after_discount, deffered FROM invoices WHERE invoice_id = ?";

        try (Connection connect = database.getConnection(); PreparedStatement fetchPst = connect.prepareStatement(fetchSql)) {

            fetchPst.setInt(1, invoiceId);
            ResultSet rs = fetchPst.executeQuery();

            if (rs.next()) {
                double totalAfterDiscount = rs.getDouble("total_price_after_discount");
                double deffered = rs.getDouble("deffered");
                deffedValue = deffered;  // المبلغ الآجل الذي سيتم حذفه
                returnsValue = totalAfterDiscount - deffered;  // الباقي يخصم من وسيلة الدفع المحددة
            } else {
                al.E_Alert("لم يتم العثور على فاتورة بهذا الرقم", Alert.AlertType.WARNING);
                return false;
            }

            sql1.append("UPDATE invoices SET ");
            if (Type != null) {
                switch (Type) {
                    case "نقدي":
                        text = "النقدي";
                        sql1.append(" cashPay = cashPay - ?, deffered = 0.0, ");
                        break;
                    case "فودافون كاش":
                        text = "فودافون كاش";
                        sql1.append(" vodafonePay = vodafonePay - ?, deffered = 0.0, ");
                        break;
                    case "انستا باي":
                        text = "انستا باي";
                        sql1.append(" instaPay = instaPay - ?, deffered = 0.0, ");
                        break;
                    default:
                        break;
                }
            }

            sql1.append(" total_quantity = 0 , total_price_before_discount = 0.0, discount_percentage = 0.0, total_price_after_discount = 0.0 ");
            sql1.append(" WHERE invoice_id = ?");

            try (PreparedStatement pst = connect.prepareStatement(sql1.toString())) {
                int index = 1;

                if (returnsValue != null && Type != null) {
                    pst.setDouble(index++, returnsValue);
                }

                pst.setInt(index, invoiceId);
                int affectedRows = pst.executeUpdate();

                if (affectedRows > 0) {
                    StringBuilder info = new StringBuilder();
                    info.append("تم خصم مبلغ ");
                    info.append(deffedValue);
                    info.append(" من الآجل وزيادة ");
                    info.append(returnsValue);
                    info.append(" من ");
                    info.append(text);
                    al.information_Alert(info.toString());
                    return true;
                } else {
                    al.E_Alert("لم يتم العثور على فاتورة بهذا الرقم", Alert.AlertType.WARNING);
                    return false;
                }

            }

        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء حذف الفاتورة من قاعدة البيانات", Alert.AlertType.ERROR);
            e.printStackTrace();
            return false;
        }
    }

    //دى بتحذف الفاتوره كامله بناء على رقم الفاتورة بالتفاصيل
    public void deleteSalesById(Integer invoiceId, String type, String returnreason) {
        String selectSql = "SELECT * FROM sales WHERE invoice_id = ?";
        String deleteSql = "DELETE FROM sales WHERE invoice_id = ?";

        try (Connection connect = database.getConnection(); PreparedStatement selectStmt = connect.prepareStatement(selectSql)) {

            selectStmt.setInt(1, invoiceId);
            ResultSet rs = selectStmt.executeQuery();

            boolean hasRows = false;

            while (rs.next()) {
                hasRows = true;

                int productId = rs.getInt("model_id");
                String modelName = rs.getString("model_name");
                String warehouse = rs.getString("warehouse");
                double quantityReturned = rs.getDouble("quantity");
                double priceOfReturn = rs.getDouble("realprice");

                String typeQuery = "SELECT type,color,size,unit FROM product WHERE id = ?";
                try (PreparedStatement typeStmt = connect.prepareStatement(typeQuery)) {
                    typeStmt.setInt(1, productId);
                    ResultSet typeRs = typeStmt.executeQuery();

                    String typeFromProduct = "غير معروف";
                    String colorFromProduct = "غير معروف";
                    String sizeFromProduct = "غير معروف";
                    String unitFromProduct = "غير معروف";
                    if (typeRs.next()) {
                        typeFromProduct = typeRs.getString("type");
                        colorFromProduct = typeRs.getString("color");
                        sizeFromProduct = typeRs.getString("size");
                        unitFromProduct = typeRs.getString("unit");
                    }

                    insertReturn(connect, productId, modelName, typeFromProduct, colorFromProduct,
                            sizeFromProduct, unitFromProduct, warehouse,
                            invoiceId, (int) quantityReturned, returnreason, priceOfReturn);
                }
            }

            if (!hasRows) {
                al.E_Alert("لم يتم العثور على تفاصيل مرتبطة بهذا الرقم", Alert.AlertType.WARNING);
                return;
            }

            try (PreparedStatement deleteStmt = connect.prepareStatement(deleteSql)) {
                deleteStmt.setInt(1, invoiceId);
                int affectedRows = deleteStmt.executeUpdate();

                if (affectedRows > 0) {
                    if (updateInvoiceById(invoiceId, type)) {
                        al.information_Alert("تم حذف الفاتورة وتسجيل المرتجعات بنجاح");
                    } else {
                        al.E_Alert("تم حذف الفاتورة ولكن لم يتم حذف بيانات الفاتورة من جدول الفواتير", Alert.AlertType.ERROR);
                    }
                }
            }

        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء حذف الفاتورة أو تسجيل المرتجعات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    //لارجاع المنتج فى المخزون بعد المرتجع
    public void returnProductsToStockBeforeInvoiceDeletion(Integer invoiceId) {
        String selectSales = "SELECT model_id, quantity FROM sales WHERE invoice_id = ?";
        String updateProduct = "UPDATE product SET mNumberavailable = mNumberavailable + ?, soldNo = soldNo - ? WHERE id = ?";

        try (Connection connect = database.getConnection()) {
            connect.setAutoCommit(false);

            try (
                    PreparedStatement selectStmt = connect.prepareStatement(selectSales); PreparedStatement updateStmt = connect.prepareStatement(updateProduct)) {
                selectStmt.setInt(1, invoiceId);

                try (ResultSet rs = selectStmt.executeQuery()) {
                    boolean hasData = false;

                    while (rs.next()) {
                        hasData = true;
                        String modelId = rs.getString("model_id");
                        int quantity = rs.getInt("quantity");

                        updateStmt.setInt(1, quantity); // زيادة المخزون
                        updateStmt.setInt(2, quantity); // تقليل عدد المبيعات
                        updateStmt.setString(3, modelId);
                        updateStmt.addBatch();
                    }

                    if (hasData) {
                        updateStmt.executeBatch();
                        connect.commit();
                    } else {
                        connect.rollback();
                        al.E_Alert("لم يتم العثور على بيانات المنتجات في الفاتورة.", Alert.AlertType.WARNING);
                    }
                }

            } catch (SQLException e) {
                connect.rollback();
                al.E_Alert("حدث خطأ أثناء استرجاع الكمية للمخزون", Alert.AlertType.ERROR);
                e.printStackTrace();
            } finally {
                connect.setAutoCommit(true);
            }

        } catch (SQLException e) {
            al.E_Alert("فشل الاتصال بقاعدة البيانات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public List<productData> getAllSales(String clint, LocalDate fromDate, LocalDate toDate) {
        List<productData> datas = new ArrayList<>();
        StringBuilder MySql = new StringBuilder();
        List<Object> params = new ArrayList<>();

        MySql.append("SELECT sales.*, product.type , product.color , product.size , product.unit ");
        MySql.append("FROM sales ");
        MySql.append("JOIN product ON sales.model_id = product.id ");
        MySql.append("LEFT JOIN invoices inv ON sales.invoice_id = inv.invoice_id ");
        MySql.append("WHERE 1=1 ");

        if (clint != null && !clint.trim().isEmpty()) {
            MySql.append(" AND inv.clientName = ?");
            params.add(clint);
        }
        if (fromDate != null) {
            MySql.append(" AND DATE(date) >= ?");
            params.add(fromDate.format(formatter));
        }
        if (toDate != null) {
            MySql.append(" AND DATE(date) <= ?");
            params.add(toDate.format(formatter));
        }

        // ترتيب النتائج حسب التاريخ تنازليًا (اختياري)
        MySql.append(" ORDER BY sales.date DESC");

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(MySql.toString())) {

            // تمرير قيم المعايير لـ PreparedStatement
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    productData d = new productData();
                    d.setId(rs.getInt("sales_id"));
                    d.setName(rs.getString("em_username"));
                    d.setmNumberavailable(rs.getDouble("quantity"));
                    d.setInvoicesID(rs.getInt("invoice_id"));
                    d.setModel(rs.getString("model_name"));
                    d.setDate(rs.getString("date"));
                    d.setWholesalePrice(rs.getDouble("totalWholesalesPrice"));
                    d.setRealPrice(rs.getDouble("realprice"));
                    d.setTotal(rs.getDouble("total_price"));
                    d.setModelID(rs.getInt("model_id"));
                    d.setWareHouse(rs.getString("warehouse"));
                    d.setColor(rs.getString("color"));
                    d.setSize(rs.getString("size"));
                    d.setUnit(rs.getString("unit"));
                    d.setType(rs.getString("type")); // من جدول المنتج

                    datas.add(d);
                }
            }

        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء تحميل بيانات المبيعات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

        return datas;
    }

    public List<customer> getAllCustomers() {
        List<customer> customerList = new ArrayList<>();
        String sql = "SELECT * FROM customers";

        try (Connection conn = database.getConnection(); PreparedStatement pst = conn.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {

                int id = rs.getInt("id");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String remark = rs.getString("remark");
                String adress = rs.getString("adress");

                customer customer = new customer();
                customer.setId(id);
                customer.setName(name);
                customer.setPhone(phone);
                customer.setAdress(adress);
                customer.setRemark(remark);
                customerList.add(customer);
            }

        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء تحميل بيانات العملاء", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

        return customerList;
    }

    public customer getCustomerByNameAndPhone(String nameFilter, String phoneFilter) {
        String sql = "SELECT * FROM customers WHERE name = ? AND phone = ?";

        try (Connection conn = database.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, nameFilter);
            pst.setString(2, phoneFilter);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                customer customer = new customer();
                customer.setId(rs.getInt("id"));
                customer.setName(rs.getString("name"));
                customer.setPhone(rs.getString("phone"));
                customer.setAdress(rs.getString("adress"));
                customer.setRemark(rs.getString("remark"));
                return customer;
            }

        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء البحث عن العميل", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

        return null;
    }

    public void deleteCustomer(int id) {
        String deleteSql = "DELETE FROM customers WHERE id = ?";

        try (Connection conn = database.getConnection(); PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {

            deleteStmt.setInt(1, id);
            int rowsAffected = deleteStmt.executeUpdate();
            al.information_Alert("تم حذف العميل بنجاح");

        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء حذف العميل", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

    }

    public void updateCustomer(int id, String name, String phone, String address, String remark) {
        String checkSql = "SELECT COUNT(*) FROM customers WHERE name = ? AND id != ?";
        String updateSql = "UPDATE customers SET name = ?, phone = ?, adress = ?, remark = ? WHERE id = ?";

        try (Connection conn = database.getConnection(); PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setString(1, name);
            checkStmt.setInt(2, id);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                al.E_Alert("يوجد عميل آخر بنفس الاسم!", Alert.AlertType.WARNING);
            }

            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                updateStmt.setString(1, name);
                updateStmt.setString(2, phone);
                updateStmt.setString(3, address);
                updateStmt.setString(4, remark);
                updateStmt.setInt(5, id);

                updateStmt.executeUpdate();
                al.information_Alert("تم تعديل العميل بنجاح");
            }

        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء تعديل بيانات العميل", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

    }

    public void insertCustomer(String name, String phone, String address, String remark) {
        String checkSql = "SELECT COUNT(*) FROM customers WHERE phone = ?";
        String insertSql = "INSERT INTO customers (name, phone, adress, remark) VALUES (?, ?, ?, ?)";

        try (Connection conn = database.getConnection(); PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setString(1, phone);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                al.E_Alert("العميل برقم الهاتف هذا موجود بالفعل", Alert.AlertType.WARNING);
                return;
            }

            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, name);
                insertStmt.setString(2, phone);
                insertStmt.setString(3, address);
                insertStmt.setString(4, remark);

                insertStmt.executeUpdate();
                al.information_Alert("تم اضافة العميل بنجاح");
            }

        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء إضافة العميل", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public List<wareHouse> getAllWareHouse() {
        List<wareHouse> warehouseList = new ArrayList<>();
        String sql = "SELECT * FROM warehouse";

        try (Connection conn = database.getConnection(); PreparedStatement pst = conn.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {

                int id = rs.getInt("id");
                String name = rs.getString("name");
                String remark = rs.getString("remark");
                String dateStr = rs.getString("date");
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // match your string format
//                LocalDate date = LocalDate.parse(dateStr, formatter);

                wareHouse warehouse = new wareHouse();
                warehouse.setId(id);
                warehouse.setName(name);
                warehouse.setDate(dateStr);
                warehouse.setRemark(remark);
                warehouseList.add(warehouse);
            }

        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء تحميل بيانات المخازن", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

        return warehouseList;
    }

    // MERGE ALL DATAS
    public ObservableList<productData> menueDataList(String type, String wareHouse) {
        ObservableList<productData> listData = FXCollections.observableArrayList();

        StringBuilder sql = new StringBuilder("SELECT id, model, mNumberavailable, realprice, type, color, size, unit  FROM product WHERE mNumberavailable > 0 ");
        if (wareHouse != null) {
            sql.append("AND warehouse = '" + wareHouse + "'");
        }
        if (type != null) {
            sql.append("AND type = '" + type + "'");
        }

        sql.append(" ORDER BY model COLLATE NOCASE");
        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql.toString()); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                productData prodData = new productData(
                        rs.getInt("id"),
                        rs.getString("model"),
                        rs.getDouble("mNumberavailable"),
                        rs.getDouble("realprice"),
                        rs.getString("type"),
                        rs.getString("color"),
                        rs.getString("size"),
                        rs.getString("unit")
                );
                listData.add(prodData);
            }

        } catch (SQLException e) {
            al.E_Alert("خطأ في تحميل بيانات القائمة", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

        return listData;
    }

    public ObservableList<Returns> getAllReturns() {
        ObservableList<Returns> returnList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM returns";

        try (Connection conn = database.getConnection(); PreparedStatement pst = conn.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                int returnId = rs.getInt("return_id");
                int productId = rs.getInt("product_id");
                String modelName = rs.getString("model_name");
                String type = rs.getString("type");
                String warehouse = rs.getString("warehouse");
                int invoiceId = rs.getInt("invoice_id");
                int quantityReturned = rs.getInt("quantity_returned");
                String returnReason = rs.getString("return_reason");
                String returnDate = rs.getString("return_date");
                String returnColor = rs.getString("color");
                String returnSize = rs.getString("size");
                String returnUnit = rs.getString("unit");
                double priceOfReturn = rs.getDouble("price_of_return");

                Returns item = new Returns();
                item.setReturn_id(returnId);
                item.setProduct_id(productId);
                item.setModel_name(modelName);
                item.setType(type);
                item.setWarehouse(warehouse);
                item.setInvoice_id(invoiceId);
                item.setQuantity_returned(quantityReturned);
                item.setReturn_reason(returnReason);
                item.setReturn_date(returnDate);
                item.setPrice_of_return(priceOfReturn);
                item.setColor(returnColor);
                item.setSize(returnSize);
                item.setUnit(returnUnit);

                returnList.add(item);
            }

        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء تحميل بيانات المرتجعات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

        return returnList;
    }

    public ObservableList<Returns> getReturnsByDateRange(LocalDate from, LocalDate to) {
        ObservableList<Returns> returnList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM returns WHERE DATE(return_date) BETWEEN ? AND ?";

        try (Connection conn = database.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, from.toString());  // صيغة yyyy-MM-dd
            pst.setString(2, to.toString());

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int returnId = rs.getInt("return_id");
                int productId = rs.getInt("product_id");
                String modelName = rs.getString("model_name");
                String type = rs.getString("type");
                String warehouse = rs.getString("warehouse");
                int invoiceId = rs.getInt("invoice_id");
                int quantityReturned = rs.getInt("quantity_returned");
                String returnReason = rs.getString("return_reason");
                String returnDate = rs.getString("return_date");
                double priceOfReturn = rs.getDouble("price_of_return");

                Returns item = new Returns();
                item.setReturn_id(returnId);
                item.setProduct_id(productId);
                item.setModel_name(modelName);
                item.setType(type);
                item.setWarehouse(warehouse);
                item.setInvoice_id(invoiceId);
                item.setQuantity_returned(quantityReturned);
                item.setReturn_reason(returnReason);
                item.setReturn_date(returnDate);
                item.setPrice_of_return(priceOfReturn);

                returnList.add(item);
            }

        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء البحث بالتاريخ", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

        return returnList;
    }

    public void insertReturn(Connection conn, int productId, String modelName, String type, String color,
            String size, String unit, String warehouse,
            int invoiceId, int quantityReturned, String returnReason, double priceOfReturn) {

        String insertSql = "INSERT INTO returns (product_id, model_name, type,color,size,unit, warehouse, invoice_id, quantity_returned, return_reason, price_of_return) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {

            insertStmt.setInt(1, productId);
            insertStmt.setString(2, modelName);
            insertStmt.setString(3, type);
            insertStmt.setString(4, color);
            insertStmt.setString(5, size);
            insertStmt.setString(6, unit);
            insertStmt.setString(7, warehouse);
            insertStmt.setInt(8, invoiceId);
            insertStmt.setInt(9, quantityReturned);
            insertStmt.setString(10, returnReason);
            insertStmt.setDouble(11, priceOfReturn);

            insertStmt.executeUpdate();

        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء تسجيل المرتجع", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public ObservableList<Invoices> getInvoicesData(Integer type, String clint, LocalDate fromDate, LocalDate toDate) {
        ObservableList<Invoices> list = FXCollections.observableArrayList();
        StringBuilder MySql = new StringBuilder();
        List<Object> params = new ArrayList<>();

        if (type == 0) {
            MySql.append("SELECT invoices.*, COALESCE(SUM(ret.price_of_return), 0) AS total_returns ");
            MySql.append("FROM invoices ");
            MySql.append(" LEFT JOIN returns ret ON  invoices.invoice_id = ret.invoice_id ");
            MySql.append(" WHERE 1=1 ");
        } else {
            MySql.append("SELECT * FROM invoices WHERE total_quantity > 0 AND deffered > 0");
        }

        if (clint != null && !clint.trim().isEmpty()) {
            MySql.append(" AND clientName = ? ");
            params.add(clint);
        }
        if (fromDate != null) {
            MySql.append(" AND DATE(invoice_date) >= ? ");
            params.add(fromDate.format(formatter));
        }
        if (toDate != null) {
            MySql.append(" AND DATE(invoice_date) <= ? ");
            params.add(toDate.format(formatter));
        }

        MySql.append(" GROUP BY invoices.invoice_id ");
        MySql.append(" ORDER BY invoice_id DESC");

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(MySql.toString())) {

            // تعيين قيم المتغيرات في PreparedStatement
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (type == 0) {
                    while (rs.next()) {
                        Invoices inv = new Invoices(
                                rs.getInt("invoice_id"),
                                rs.getInt("total_quantity"),
                                rs.getDouble("total_price_before_discount"),
                                rs.getDouble("discount_percentage"),
                                rs.getDouble("total_price_after_discount"),
                                rs.getDouble("cashPay"),
                                rs.getDouble("instaPay"),
                                rs.getDouble("vodafonePay"),
                                rs.getDouble("deffered"),
                                rs.getString("username"),
                                rs.getString("invoice_date"),
                                rs.getString("clientName"),
                                rs.getString("clientPhone"),
                                rs.getDouble("total_returns")
                        );
                        list.add(inv);
                    }
                } else {
                    while (rs.next()) {
                        Invoices inv = new Invoices(
                                rs.getInt("invoice_id"),
                                rs.getInt("total_quantity"),
                                rs.getDouble("total_price_before_discount"),
                                rs.getDouble("discount_percentage"),
                                rs.getDouble("total_price_after_discount"),
                                rs.getDouble("cashPay"),
                                rs.getDouble("instaPay"),
                                rs.getDouble("vodafonePay"),
                                rs.getDouble("deffered"),
                                rs.getString("username"),
                                rs.getString("invoice_date"),
                                rs.getString("clientName"),
                                rs.getString("clientPhone")
                        );
                        list.add(inv);
                    }
                }
            }

        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء تحميل الفواتير", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

        return list;
    }

    public void searchInvoicesFlexible(LocalDate fromDate, LocalDate toDate, String keyword, TableView<Invoices> invoiceTable) {
        ObservableList<Invoices> searchResult = FXCollections.observableArrayList();

        StringBuilder sql = new StringBuilder("SELECT * FROM invoices WHERE 1=1 ");
        List<Object> params = new ArrayList<>();
        // البحث بالتاريخ
        if (fromDate != null) {
            sql.append(" AND DATE(invoice_date) >= ?");
            params.add(fromDate.format(formatter));
        }
        if (toDate != null) {
            sql.append(" AND DATE(invoice_date) <= ?");
            params.add(toDate.format(formatter));
        }

        // البحث بالكلمة المفتاحية (ID أو الاسم أو الهاتف)
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (CAST(invoice_id AS TEXT) LIKE ? OR clientName LIKE ? OR clientPhone LIKE ?)");
            String likeKeyword = "%" + keyword.trim() + "%";
            params.add(likeKeyword);
            params.add(likeKeyword);
            params.add(likeKeyword);
        }

        sql.append(" ORDER BY invoice_date DESC");

        try (Connection connect = database.getConnection(); PreparedStatement ps = connect.prepareStatement(sql.toString())) {

            // تعيين القيم للـ PreparedStatement
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Invoices inv = new Invoices(
                            rs.getInt("invoice_id"),
                            rs.getInt("total_quantity"),
                            rs.getDouble("total_price_before_discount"),
                            rs.getDouble("discount_percentage"),
                            rs.getDouble("total_price_after_discount"),
                            rs.getDouble("cashPay"),
                            rs.getDouble("instaPay"),
                            rs.getDouble("vodafonePay"),
                            rs.getDouble("deffered"),
                            rs.getString("username"),
                            rs.getString("invoice_date"),
                            rs.getString("clientName"),
                            rs.getString("clientPhone")
                    );
                    searchResult.add(inv);
                }
            }
            invoiceTable.setItems(searchResult);

        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء جلب البيانات من قاعدة البيانات", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void updateInvoiceByIdAndPay(Integer invoiceId, String deferred_cashPay,
            String deferred_instaPay, String deferred_VodafonePay) {

        Double deferredCashPay = Double.valueOf(deferred_cashPay);
        Double deferredInstaPay = Double.valueOf(deferred_instaPay);
        Double deferredVodafonePay = Double.valueOf(deferred_VodafonePay);
        Double deferredTotal = deferredCashPay + deferredInstaPay + deferredVodafonePay;

        StringBuilder sql1 = new StringBuilder("UPDATE invoices SET ");
        List<String> updates = new ArrayList<>();
        List<Double> values = new ArrayList<>();

        if (deferredCashPay > 0) {
            updates.add("cashPay = cashPay + ?");
            values.add(deferredCashPay);
        }
        if (deferredVodafonePay > 0) {
            updates.add("vodafonePay = vodafonePay + ?");
            values.add(deferredVodafonePay);
        }
        if (deferredInstaPay > 0) {
            updates.add("instaPay = instaPay + ?");
            values.add(deferredInstaPay);
        }
        updates.add("deffered = deffered - ?");
        values.add(deferredTotal);
        if (updates.isEmpty()) {
            al.E_Alert("يجب إدخال مبلغ للسداد", Alert.AlertType.WARNING);
            return;
        }

        sql1.append(String.join(", ", updates));
        sql1.append(" WHERE invoice_id = ?");

        try (Connection connect = database.getConnection(); PreparedStatement pst = connect.prepareStatement(sql1.toString())) {

            int index = 1;
            for (Double val : values) {
                pst.setDouble(index++, val);
            }
            pst.setInt(index, invoiceId);

            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                StringBuilder info = new StringBuilder("تم خصم من الاجل ").append(deferredTotal);
                if (deferredCashPay > 0) {
                    info.append(" وزياده فى النقدى ").append(deferredCashPay);
                }
                if (deferredVodafonePay > 0) {
                    info.append(" وزياده فى فودافون كاش ").append(deferredVodafonePay);
                }
                if (deferredInstaPay > 0) {
                    info.append(" وزياده فى انستا باى ").append(deferredInstaPay);
                }
                al.information_Alert(info.toString());
            } else {
                al.E_Alert("لم يتم العثور على فاتورة بهذا الرقم", Alert.AlertType.WARNING);
            }

        } catch (SQLException e) {
            al.E_Alert("حدث خطأ أثناء تحديث الفاتورة", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

}
