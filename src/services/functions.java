/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import inventory.AccountStatement;
import inventory.Expenses;
import inventory.InvoiceItem;
import inventory.Invoices;
import inventory.ReceiptVoucher;
import inventory.Returns;
import inventory.Sales;
import inventory.SettlementBondData;
import inventory.data;
import inventory.database;
import inventory.itemMovementData;
import inventory.productData;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author OSOS
 */
public class functions {

    alert al = new alert();
    purchesServices serv = new purchesServices();

    public void printReceipt(List<InvoiceItem> listData) {
        InputStream input = null;
        try {

            if (data.printType == 0) {
                input = getClass().getResourceAsStream("/reports/report.jrxml");
            } else if (data.printType == 1 && (data.clientName == null || data.clientName.isEmpty())) {
                input = getClass().getResourceAsStream("/reports/receipt_without_clients.jrxml");
            } else {
                input = getClass().getResourceAsStream("/reports/receipt.jrxml");
            }

            if (input == null) {
                al.E_Alert("لم يتم العثور على ملف التصميم report.jrxml", Alert.AlertType.ERROR);
                return;
            }

            JasperReport jr = JasperCompileManager.compileReport(input);

            if (data.invoiceId == 0 || data.Total_invoice_price == 0.0) {
                al.E_Alert("تأكد من وجود بيانات صحيحة للفاتورة قبل الطباعة", Alert.AlertType.WARNING);
                return;
            }

            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("cashier", data.username);

            parameters.put("total", String.format("%.2f $", data.Total_invoice_price));
            parameters.put("RID", data.invoiceId + "");
            parameters.put("totalQuantity", data.totalQty + "");
            parameters.put("discount", String.format("%.2f $", data.discount));
            parameters.put("deffered", String.format("%.2f $", data.deffered));
            double total = data.cashPay + data.vodafonePay + data.instaPay;
            parameters.put("totalPay", String.format("%.2f $", total));

            parameters.put("companyName", data.companyName);
            parameters.put("companyPhone", data.companyPhone);
            parameters.put("companyAddress", data.companyAddress);
            parameters.put("companyWeb", data.companyWeb);

            parameters.put("Cphone", (data.clientphone == null ? "" : data.clientphone));
            parameters.put("Cname", (data.clientName == null ? "" : data.clientName));
            if (data.clientName == null || data.clientphone == null || data.clientName.isEmpty() || data.clientphone.isEmpty()) {
                parameters.put("Cadd", "");
            } else {
                customer cust = serv.getCustomerByNameAndPhone(data.clientName, data.clientphone);
                parameters.put("Cadd", cust.getAdress());
            }

            //Data
            List<InvoiceItem> items = listData;
//            if (!items.isEmpty()) {
//                items.remove(items.size() - 1);
//            }
            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(items);

            JasperPrint print = JasperFillManager.fillReport(jr, parameters, itemsJRBean);

            JasperPrintManager.printReport(print, false);

        } catch (Exception e) {
            al.E_Alert("حدث خطأ أثناء طباعة الفاتورة: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ioEx) {
                al.E_Alert("حدث خطأ أثناء طباعة الفاتورة: " + ioEx.getMessage(), Alert.AlertType.ERROR);
                ioEx.printStackTrace();
            }
        }
    }

    public void printInvoicesByDate(LocalDate fromDate, LocalDate toDate, ObservableList<Invoices> returnItemL, String Report) {
        InputStream input = null;
        try {
            input = getClass().getResourceAsStream("/reports/invoiceReport.jrxml");
            if (input == null) {
                al.E_Alert("لم يتم العثور على ملف التصميم invoiceReport.jrxml", Alert.AlertType.ERROR);
                return;
            }

            JasperReport jr = JasperCompileManager.compileReport(input);

            if (returnItemL == null || returnItemL.isEmpty()) {
                al.E_Alert("تأكد من وجود بيانات للطباعة", Alert.AlertType.WARNING);
                return;
            }

            HashMap<String, Object> parameters = new HashMap<>();
            if (fromDate == null) {
                parameters.put("fromDate", "");
            } else {
                parameters.put("fromDate", fromDate.toString());
            }
            if (toDate == null) {
                parameters.put("toDate", "");
            } else {
                parameters.put("toDate", toDate.toString());
            }

            parameters.put("Report", Report);

            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(returnItemL);

            JasperPrint print = JasperFillManager.fillReport(jr, parameters, itemsJRBean);

            JasperPrintManager.printReport(print, false);

        } catch (Exception e) {
            al.E_Alert("حدث خطأ أثناء طباعة الفاتورة: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ioEx) {
                al.E_Alert("حدث خطأ أثناء طباعة الفاتورة: " + ioEx.getMessage(), Alert.AlertType.ERROR);
                ioEx.printStackTrace();
            }
        }
    }

    public void printSalesByDate(LocalDate fromDate, LocalDate toDate, ObservableList<Sales> returnItemL) {
        InputStream input = null;
        try {
            input = getClass().getResourceAsStream("/reports/salesReport.jrxml");
            if (input == null) {
                al.E_Alert("لم يتم العثور على ملف التصميم salesReport.jrxml", Alert.AlertType.ERROR);
                return;
            }

            JasperReport jr = JasperCompileManager.compileReport(input);

            if (returnItemL == null || returnItemL.isEmpty()) {
                al.E_Alert("تأكد من وجود بيانات للطباعة", Alert.AlertType.WARNING);
                return;
            }

            HashMap<String, Object> parameters = new HashMap<>();
            if (fromDate == null) {
                parameters.put("fromDate", "");
            } else {
                parameters.put("fromDate", fromDate.toString());
            }
            if (toDate == null) {
                parameters.put("toDate", "");
            } else {
                parameters.put("toDate", toDate.toString());
            }

            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(returnItemL);

            JasperPrint print = JasperFillManager.fillReport(jr, parameters, itemsJRBean);

            JasperPrintManager.printReport(print, false);

        } catch (Exception e) {
            al.E_Alert("حدث خطأ أثناء طباعة الفاتورة: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ioEx) {
                al.E_Alert("حدث خطأ أثناء طباعة الفاتورة: " + ioEx.getMessage(), Alert.AlertType.ERROR);
                ioEx.printStackTrace();
            }
        }
    }

    public void printReturnsByDate(LocalDate fromDate, LocalDate toDate, ObservableList<Returns> returnItemL) {
        InputStream input = null;
        try {
            input = getClass().getResourceAsStream("/reports/defferedReport.jrxml");
            if (input == null) {
                al.E_Alert("لم يتم العثور على ملف التصميم defferedReport.jrxml", Alert.AlertType.ERROR);
                return;
            }

            JasperReport jr = JasperCompileManager.compileReport(input);

            if (returnItemL == null || returnItemL.isEmpty()) {
                al.E_Alert("تأكد من وجود بيانات للطباعة", Alert.AlertType.WARNING);
                return;
            }

            HashMap<String, Object> parameters = new HashMap<>();
            if (fromDate == null) {
                parameters.put("fromDate", "");
            } else {
                parameters.put("fromDate", fromDate.toString());
            }
            if (toDate == null) {
                parameters.put("toDate", "");
            } else {
                parameters.put("toDate", toDate.toString());
            }

            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(returnItemL);

            JasperPrint print = JasperFillManager.fillReport(jr, parameters, itemsJRBean);

            JasperPrintManager.printReport(print, false);

        } catch (Exception e) {
            al.E_Alert("حدث خطأ أثناء طباعة الفاتورة: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ioEx) {
                al.E_Alert("حدث خطأ أثناء طباعة الفاتورة: " + ioEx.getMessage(), Alert.AlertType.ERROR);
                ioEx.printStackTrace();
            }
        }
    }

    public void printSalesTotalByDate(printReportsValues reportsValues, ObservableList<Invoices> returnItemL) {
        InputStream input = null;
        try {
            input = getClass().getResourceAsStream("/reports/salesTotalReport.jrxml");
            if (input == null) {
                al.E_Alert("لم يتم العثور على ملف التصميم salesTotalReport.jrxml", Alert.AlertType.ERROR);
                return;
            }

            JasperReport jr = JasperCompileManager.compileReport(input);

            if (returnItemL == null || returnItemL.isEmpty()) {
                al.E_Alert("تأكد من وجود بيانات للطباعة", Alert.AlertType.WARNING);
                return;
            }

            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("fromDate", reportsValues.getFromDate() == null ? "" : reportsValues.getFromDate().toString());
            parameters.put("toDate", reportsValues.getToDate() == null ? "" : reportsValues.getToDate().toString());
            parameters.put("clint", reportsValues.getClintName() == null ? "" : reportsValues.getClintName());
            parameters.put("totalPurchase", reportsValues.getTotalPurchase() == null ? "" : reportsValues.getTotalPurchase());
            parameters.put("totalRuturns", reportsValues.getTotalRuturns() == null ? "" : reportsValues.getTotalRuturns());
            parameters.put("totalDeferred", reportsValues.getTotalDeferred() == null ? "" : reportsValues.getTotalDeferred());
            parameters.put("totalCash", reportsValues.getTotalCash() == null ? "" : reportsValues.getTotalCash());

            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(returnItemL);

            JasperPrint print = JasperFillManager.fillReport(jr, parameters, itemsJRBean);

            JasperPrintManager.printReport(print, false);

        } catch (Exception e) {
            al.E_Alert("حدث خطأ أثناء طباعة الفاتورة: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ioEx) {
                al.E_Alert("حدث خطأ أثناء طباعة الفاتورة: " + ioEx.getMessage(), Alert.AlertType.ERROR);
                ioEx.printStackTrace();
            }
        }
    }

    public void printSalesDetailsByDate(printReportsValues reportsValues, List<productData> returnItemL) {
        InputStream input = null;
        try {
            input = getClass().getResourceAsStream("/reports/salesDetailsReport.jrxml");
            if (input == null) {
                al.E_Alert("لم يتم العثور على ملف التصميم salesDetailsReport.jrxml", Alert.AlertType.ERROR);
                return;
            }

            JasperReport jr = JasperCompileManager.compileReport(input);

            if (returnItemL == null || returnItemL.isEmpty()) {
                al.E_Alert("تأكد من وجود بيانات للطباعة", Alert.AlertType.WARNING);
                return;
            }

            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("fromDate", reportsValues.getFromDate() == null ? "" : reportsValues.getFromDate().toString());
            parameters.put("toDate", reportsValues.getToDate() == null ? "" : reportsValues.getToDate().toString());
            parameters.put("clint", reportsValues.getClintName() == null ? "" : reportsValues.getClintName());
            parameters.put("totalDetails", reportsValues.getTotalDetails() == null ? "" : reportsValues.getTotalDetails());

            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(returnItemL);

            JasperPrint print = JasperFillManager.fillReport(jr, parameters, itemsJRBean);

            JasperPrintManager.printReport(print, false);

        } catch (Exception e) {
            al.E_Alert("حدث خطأ أثناء طباعة الفاتورة: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ioEx) {
                al.E_Alert("حدث خطأ أثناء طباعة الفاتورة: " + ioEx.getMessage(), Alert.AlertType.ERROR);
                ioEx.printStackTrace();
            }
        }
    }

    public void printSuplierByDate(printReportsValues reportsValues, ObservableList<AccountStatement> returnItemL) {
        InputStream input = null;
        try {
            input = getClass().getResourceAsStream("/reports/suplierTotalReport.jrxml");
            if (input == null) {
                al.E_Alert("لم يتم العثور على ملف التصميم suplierTotalReport.jrxml", Alert.AlertType.ERROR);
                return;
            }

            JasperReport jr = JasperCompileManager.compileReport(input);

            if (returnItemL == null || returnItemL.isEmpty()) {
                al.E_Alert("تأكد من وجود بيانات للطباعة", Alert.AlertType.WARNING);
                return;
            }

            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("fromDate", reportsValues.getFromDate() == null ? "" : reportsValues.getFromDate().toString());
            parameters.put("toDate", reportsValues.getToDate() == null ? "" : reportsValues.getToDate().toString());
            parameters.put("clint", reportsValues.getClintName() == null ? "" : reportsValues.getClintName());
            parameters.put("totalPurchase", reportsValues.getTotalPurchaseSup() == null ? "" : reportsValues.getTotalPurchaseSup());
            parameters.put("totalRuturns", reportsValues.getTotalRuturnsSup() == null ? "" : reportsValues.getTotalRuturnsSup());
            parameters.put("totalDeferred", reportsValues.getTotalDeferredSup() == null ? "" : reportsValues.getTotalDeferredSup());
            parameters.put("totalCash", reportsValues.getTotalCashSup() == null ? "" : reportsValues.getTotalCashSup());

            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(returnItemL);

            JasperPrint print = JasperFillManager.fillReport(jr, parameters, itemsJRBean);

            JasperPrintManager.printReport(print, false);

        } catch (Exception e) {
            al.E_Alert("حدث خطأ أثناء طباعة الفاتورة: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ioEx) {
                al.E_Alert("حدث خطأ أثناء طباعة الفاتورة: " + ioEx.getMessage(), Alert.AlertType.ERROR);
                ioEx.printStackTrace();
            }
        }
    }

    public void printExpenses(LocalDate fromDate, LocalDate toDate, List<Expenses> items) {
        InputStream input = null;

        try {
            if (fromDate != null && toDate != null) {
                input = getClass().getResourceAsStream("/reports/ExpensesReport.jrxml");
                if (input == null) {
                    al.E_Alert("لم يتم العثور على ملف التصميم Invoice.jrxml", Alert.AlertType.ERROR);
                    return;
                }

                JasperReport jr = JasperCompileManager.compileReport(input);

                HashMap<String, Object> parameters = new HashMap<>();
                parameters.put("fromDate", fromDate + "");
                parameters.put("toDate", toDate + "");
                parameters.put("name", data.username);
                parameters.put("totalExpenses", data.TotalExpenseforPrint);
                parameters.put("totalOutgoing", data.TotalOutgoingforPrint);

                if (items.isEmpty()) {
                    al.E_Alert("لا توجد مصروفات في هذه الفترة", Alert.AlertType.INFORMATION);
                    return;
                }

                JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(items);

                JasperPrint print = JasperFillManager.fillReport(jr, parameters, itemsJRBean);

                JasperPrintManager.printReport(print, false);
                al.E_Alert("تم الطباعة بنجاح", Alert.AlertType.INFORMATION);
//            JasperViewer.viewReport(print, false);
            } else {
                al.E_Alert("حدد التواريخ من فضلك", Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            e.printStackTrace();
            al.E_Alert("حدث خطأ أثناء طباعة الفاتورة: " + e.getMessage(), Alert.AlertType.ERROR);
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ioEx) {
                ioEx.printStackTrace();
            }
        }
    }

    public void printReceiptReport(LocalDate fromDate, LocalDate toDate, List<ReceiptVoucher> voucherList) {
        try {
            // تحويل List إلى JRBeanCollectionDataSource
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(voucherList, false);

            // تمرير الباراميترات للتقرير
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("fromDate", fromDate != null ? fromDate.toString() : "");
            parameters.put("toDate", toDate != null ? toDate.toString() : "");
            parameters.put("name", data.username != null ? data.username : "");

            // تحميل وتجهيز التقرير
            InputStream reportStream = getClass().getResourceAsStream("/reports/receiptVouchersReport.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // ملء التقرير بالبيانات
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

//            // عرض التقرير
//            JasperViewer.viewReport(jasperPrint, false);
            JasperPrintManager.printReport(jasperPrint, false);
            al.E_Alert("تم الطباعة بنجاح", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            e.printStackTrace();
            al.showAlert("خطأ في الطباعة", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void printCurrentStockReport(String warehouse, String type, ObservableList<productData> currentItems) {
        try {
            // تحويل ObservableList إلى JRBeanCollectionDataSource
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(currentItems, false);

            // تحميل ملف التقرير
            InputStream reportStream = getClass().getResourceAsStream("/reports/CurrentStockReport.jrxml");
            if (reportStream == null) {
                al.showAlert("خطأ", "لم يتم العثور على تقرير المخزون", Alert.AlertType.ERROR);
                return;
            }

            // إعداد الباراميترات للتقرير
            Map<String, Object> params = new HashMap<>();
            params.put("warehouse", warehouse);
            params.put("type", type);
            params.put("name", data.username);

            // لو التقرير بصيغة .jrxml نعمل Compile
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // تعبئة التقرير بالبيانات
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

//            // عرض التقرير
//            JasperViewer viewer = new JasperViewer(jasperPrint, false);
//            viewer.setTitle("تقرير رصيد المخزون الحالي");
//            viewer.setVisible(true);
            JasperPrintManager.printReport(jasperPrint, false);
            al.E_Alert("تم الطباعة بنجاح", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            al.showAlert("خطأ", "حدث خطأ أثناء طباعة التقرير", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void printItemsMovementReport(String warehouse, LocalDate fromDate, LocalDate toDate, ObservableList<itemMovementData> movements) {
        try {
            // تحويل ObservableList إلى JRBeanCollectionDataSource
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(movements, false);

            // تحميل ملف التقرير
            InputStream reportStream = getClass().getResourceAsStream("/reports/ItemsMovementReport.jrxml");
            if (reportStream == null) {
                al.showAlert("خطأ", "لم يتم العثور على تقرير حركة الأصناف", Alert.AlertType.ERROR);
                return;
            }

            // إعداد الباراميترات للتقرير
            Map<String, Object> params = new HashMap<>();
            params.put("name", data.username);
            params.put("fromDate", fromDate != null ? Date.valueOf(fromDate).toString() : null);
            params.put("toDate", toDate != null ? Date.valueOf(toDate).toString() : null);

            // لو التقرير بصيغة .jrxml نعمل Compile
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // تعبئة التقرير بالبيانات
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

//            // عرض التقرير
//            JasperViewer viewer = new JasperViewer(jasperPrint, false);
//            viewer.setTitle("تقرير حركة الأصناف");
//            viewer.setVisible(true);
            JasperPrintManager.printReport(jasperPrint, false);
            al.E_Alert("تم الطباعة بنجاح", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            al.showAlert("خطأ", "حدث خطأ أثناء طباعة التقرير", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void printSettlementBondsReport(String warehouse, LocalDate fromDate, LocalDate toDate, ObservableList<SettlementBondData> settlementList) {
        try {

            if (settlementList.size() == 0) {
                al.showAlert("خطأ", "لا يمكن طباعة تقرير فارغ", Alert.AlertType.ERROR);
                return;
            }

            // تحويل ObservableList إلى JRBeanCollectionDataSource
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(settlementList, false);

            // تحميل ملف التقرير
            InputStream reportStream = getClass().getResourceAsStream("/reports/SettlementBondReport.jrxml");
            if (reportStream == null) {
                al.showAlert("خطأ", "لم يتم العثور على تقرير تسوية العهد", Alert.AlertType.ERROR);
                return;
            }

            // إعداد الباراميترات للتقرير
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("name", data.username);
            parameters.put("warehouse", (warehouse != null && !warehouse.isEmpty()) ? warehouse : "الكل");
            parameters.put("fromDate", fromDate != null ? Date.valueOf(fromDate).toString() : null);
            parameters.put("toDate", toDate != null ? Date.valueOf(toDate).toString() : null);

            // لو التقرير بصيغة .jrxml نعمل Compile
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // تعبئة التقرير بالبيانات
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

//            // عرض التقرير
//            JasperViewer viewer = new JasperViewer(jasperPrint, false);
//            viewer.setTitle("تقرير تسوية العهد");
//            viewer.setVisible(true);
            JasperPrintManager.printReport(jasperPrint, false);
            al.E_Alert("تم الطباعة بنجاح", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            al.showAlert("خطأ", "حدث خطأ أثناء طباعة التقرير", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void finalizeInvoice(List<InvoiceItem> SalesList, TextField disqaunt_textField, Label menu_total) {
        data.totalQty = 0;
        data.beforeDiscount = 0;
        double discount = 0;
        for (InvoiceItem item : SalesList) {
            data.totalQty += item.getQuantity().intValue(); // أو حسب نوع data.totalQty
            data.beforeDiscount += item.getTotalP();
        }

        data.discount = 0;
        try {
            discount = Double.parseDouble(disqaunt_textField.getText()) / data.beforeDiscount;
            BigDecimal bd = new BigDecimal(discount).setScale(3, RoundingMode.HALF_UP);
            discount = bd.doubleValue();
            data.discount_rate = Double.parseDouble(disqaunt_textField.getText());
//            data.discount = discount;

            data.discount = data.Total_invoice_price_peforDis - data.Total_invoice_price;
        } catch (NumberFormatException e) {
            al.E_Alert("الخصم غير صالح", Alert.AlertType.ERROR);
            Platform.runLater(() -> disqaunt_textField.setText("0"));
        }
        if (Double.parseDouble(disqaunt_textField.getText()) < data.beforeDiscount) {
//            data.Total_invoice_price = data.beforeDiscount - (data.discount_rate);
            DecimalFormat df = new DecimalFormat("#,###");
            menu_total.setText(df.format(data.Total_invoice_price) + "");
        } else {
            al.E_Alert("الخصم اكبر من سعر الفاتورة", Alert.AlertType.ERROR);
            Platform.runLater(() -> disqaunt_textField.setText("0"));
        }

    }

    // دالة البحث العامة
    public <T> void setupTableSearchFilter(TextField searchField, TableView<T> tableView, ObservableList<T> originalData, List<String> searchableFields) {
        if (originalData == null) {
            return; // تأمين لو الليست null
        }
        FilteredList<T> filteredData = new FilteredList<>(originalData, b -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(item -> {
                if (item == null) {
                    return false; // لو مافيش بيانات في الجدول
                }
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                for (String field : searchableFields) {
                    try {
                        // تحقق إن الحقل موجود فعلاً
                        Field classField = null;
                        try {
                            classField = item.getClass().getDeclaredField(field);
                        } catch (NoSuchFieldException ignored) {
                            continue; // تجاهل الحقول اللي مش موجودة في الكلاس
                        }

                        classField.setAccessible(true);
                        Object value = classField.get(item);
                        if (value != null && value.toString().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        }
                    } catch (IllegalAccessException ignored) {
                        // تجاهل لو في مشكلة في الوصول
                    }
                }
                return false;
            });
        });

        SortedList<T> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }

    public boolean isntNumeric(String str) {
        try {
            Double.parseDouble(str);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    // Calculate total InstaPay(invoices - expenses) within a date range
    public void totalInstaPay(LocalDate fromDate, LocalDate toDate, TextField total_instaPay_field) {
        String invoicesQuery = "SELECT SUM(instaPay) AS total_invoices FROM invoices WHERE invoice_date BETWEEN ? AND ?";
        String expensesQuery = "SELECT SUM(instaPay) AS total_expenses FROM expenses WHERE date BETWEEN ? AND ?";
        String voucherQuery = "SELECT SUM(instaPay) AS total_voucher FROM receipt_voucher WHERE date BETWEEN ? AND ?";

        if (fromDate == null || toDate == null) {
            al.E_Alert("يرجى تحديد كل من تاريخ البداية والنهاية", Alert.AlertType.ERROR);
            return;
        }

        try (Connection connect = database.getConnection(); PreparedStatement invoicesPS = connect.prepareStatement(invoicesQuery); PreparedStatement expensesPS = connect.prepareStatement(expensesQuery); PreparedStatement voucherPS = connect.prepareStatement(voucherQuery)) {

            // Set date parameters for both queries
            invoicesPS.setString(1, java.sql.Date.valueOf(fromDate).toString());
            invoicesPS.setString(2, java.sql.Date.valueOf(toDate).toString());
            expensesPS.setString(1, java.sql.Date.valueOf(fromDate).toString());
            expensesPS.setString(2, java.sql.Date.valueOf(toDate).toString());
            voucherPS.setString(1, java.sql.Date.valueOf(fromDate).toString());
            voucherPS.setString(2, java.sql.Date.valueOf(toDate).toString());

            ResultSet invoicesRs = invoicesPS.executeQuery();
            ResultSet expensesRs = expensesPS.executeQuery();
            ResultSet voucherRs = voucherPS.executeQuery();

            // Calculate net cash: invoices - expenses
            if (invoicesRs.next() && expensesRs.next() && voucherRs.next()) {
                double total = invoicesRs.getDouble("total_invoices") + voucherRs.getDouble("total_voucher") - expensesRs.getDouble("total_expenses");
                data.TotalInstaPay = total;
                total_instaPay_field.setText(String.format("%.2f", total) + " $");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

// Calculate total VodafonePay (invoices - expenses) within a date range
    public void totalVodafoneCash(LocalDate fromDate, LocalDate toDate, TextField total_vodafoneCash_field) {
        String invoicesQuery = "SELECT SUM(vodafonePay) AS total_invoices FROM invoices WHERE invoice_date BETWEEN ? AND ?";
        String expensesQuery = "SELECT SUM(vodafone) AS total_expenses FROM expenses WHERE date BETWEEN ? AND ?";
        String voucherQuery = "SELECT SUM(vodafone) AS total_voucher FROM receipt_voucher WHERE date BETWEEN ? AND ?";

        if (fromDate == null || toDate == null) {
            al.E_Alert("يرجى تحديد كل من تاريخ البداية والنهاية", Alert.AlertType.ERROR);
            return;
        }

        try (Connection connect = database.getConnection(); PreparedStatement invoicesPS = connect.prepareStatement(invoicesQuery); PreparedStatement expensesPS = connect.prepareStatement(expensesQuery); PreparedStatement voucherPS = connect.prepareStatement(voucherQuery)) {

            // Set date parameters for both queries
            invoicesPS.setString(1, java.sql.Date.valueOf(fromDate).toString());
            invoicesPS.setString(2, java.sql.Date.valueOf(toDate).toString());
            expensesPS.setString(1, java.sql.Date.valueOf(fromDate).toString());
            expensesPS.setString(2, java.sql.Date.valueOf(toDate).toString());
            voucherPS.setString(1, java.sql.Date.valueOf(fromDate).toString());
            voucherPS.setString(2, java.sql.Date.valueOf(toDate).toString());

            ResultSet invoicesRs = invoicesPS.executeQuery();
            ResultSet expensesRs = expensesPS.executeQuery();
            ResultSet voucherRs = voucherPS.executeQuery();

            // Calculate net cash: invoices - expenses
            if (invoicesRs.next() && expensesRs.next() && voucherRs.next()) {
                double total = invoicesRs.getDouble("total_invoices") + voucherRs.getDouble("total_voucher") - expensesRs.getDouble("total_expenses");
                data.TotalVodafoneCash = total;
                total_vodafoneCash_field.setText(String.format("%.2f", total) + " $");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void totalCash(LocalDate fromDate, LocalDate toDate, TextField total_Cash_field) {
        String invoicesQuery = "SELECT SUM(cashPay) AS totalcashPay FROM invoices WHERE invoice_date BETWEEN ? AND ?";
        String expensesQuery = "SELECT SUM(cash) AS totalcash_expenses FROM expenses WHERE date BETWEEN ? AND ?";
        String voucherQuery = "SELECT SUM(cash) AS totalcash_voucher FROM receipt_voucher WHERE date BETWEEN ? AND ?";

        // Check if date range is valid
        if (fromDate == null || toDate == null) {
            al.E_Alert("يرجى تحديد كل من تاريخ البداية والنهاية", Alert.AlertType.ERROR);
            return;
        }

        try (Connection connect = database.getConnection(); PreparedStatement invoicesPS = connect.prepareStatement(invoicesQuery); PreparedStatement expensesPS = connect.prepareStatement(expensesQuery); PreparedStatement voucherPS = connect.prepareStatement(voucherQuery)) {

            // Set date parameters for both queries
            invoicesPS.setString(1, java.sql.Date.valueOf(fromDate).toString());
            invoicesPS.setString(2, java.sql.Date.valueOf(toDate).toString());
            expensesPS.setString(1, java.sql.Date.valueOf(fromDate).toString());
            expensesPS.setString(2, java.sql.Date.valueOf(toDate).toString());
            voucherPS.setString(1, java.sql.Date.valueOf(fromDate).toString());
            voucherPS.setString(2, java.sql.Date.valueOf(toDate).toString());

            ResultSet invoicesRs = invoicesPS.executeQuery();
            ResultSet expensesRs = expensesPS.executeQuery();
            ResultSet voucherRs = voucherPS.executeQuery();

            // Calculate net cash: invoices - expenses
            if (invoicesRs.next() && expensesRs.next() && voucherRs.next()) {
                data.TotalCashPay = invoicesRs.getDouble("totalcashPay") + voucherRs.getDouble("totalcash_voucher") - expensesRs.getDouble("totalcash_expenses");
                total_Cash_field.setText(String.format("%.2f", data.TotalCashPay) + " $");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Calculate total InstaPay(invoices - expenses) within a date range
    public void totalInstaPay(TextField total_instaPay_field) {
        String invoicesQuery = "SELECT SUM(instaPay) AS total_invoices FROM invoices";
        String expensesQuery = "SELECT SUM(instaPay) AS total_expenses FROM expenses";
        String voucherQuery = "SELECT SUM(instaPay) AS total_voucher FROM receipt_voucher";

        try (Connection connect = database.getConnection(); PreparedStatement invoicesPS = connect.prepareStatement(invoicesQuery); PreparedStatement expensesPS = connect.prepareStatement(expensesQuery); PreparedStatement voucherPS = connect.prepareStatement(voucherQuery)) {

            ResultSet invoicesRs = invoicesPS.executeQuery();
            ResultSet expensesRs = expensesPS.executeQuery();
            ResultSet voucherRs = voucherPS.executeQuery();

            // Calculate net cash: invoices - expenses
            if (invoicesRs.next() && expensesRs.next() && voucherRs.next()) {
                double total = invoicesRs.getDouble("total_invoices") + voucherRs.getDouble("total_voucher") - expensesRs.getDouble("total_expenses");
                data.TotalInstaPay = total;
                total_instaPay_field.setText(String.format("%.2f", total) + " $");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

// Calculate total VodafonePay (invoices - expenses) within a date range
    public void totalVodafoneCash(TextField total_vodafoneCash_field) {
        String invoicesQuery = "SELECT SUM(vodafonePay) AS total_invoices FROM invoices";
        String expensesQuery = "SELECT SUM(vodafone) AS total_expenses FROM expenses";
        String voucherQuery = "SELECT SUM(vodafone) AS total_voucher FROM receipt_voucher";

        try (Connection connect = database.getConnection(); PreparedStatement invoicesPS = connect.prepareStatement(invoicesQuery); PreparedStatement expensesPS = connect.prepareStatement(expensesQuery); PreparedStatement voucherPS = connect.prepareStatement(voucherQuery)) {

            ResultSet invoicesRs = invoicesPS.executeQuery();
            ResultSet expensesRs = expensesPS.executeQuery();
            ResultSet voucherRs = voucherPS.executeQuery();

            // Calculate net cash: invoices - expenses
            if (invoicesRs.next() && expensesRs.next() && voucherRs.next()) {
                double total = invoicesRs.getDouble("total_invoices") + voucherRs.getDouble("total_voucher") - expensesRs.getDouble("total_expenses");
                data.TotalVodafoneCash = total;
                total_vodafoneCash_field.setText(String.format("%.2f", total) + " $");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void totalCash(TextField total_Cash_field) {
        String invoicesQuery = "SELECT SUM(cashPay) AS totalcashPay FROM invoices";
        String expensesQuery = "SELECT SUM(cash) AS totalcash_expenses FROM expenses";
        String voucherQuery = "SELECT SUM(cash) AS totalcash_voucher FROM receipt_voucher";

        try (Connection connect = database.getConnection(); PreparedStatement invoicesPS = connect.prepareStatement(invoicesQuery); PreparedStatement expensesPS = connect.prepareStatement(expensesQuery); PreparedStatement voucherPS = connect.prepareStatement(voucherQuery)) {

            ResultSet invoicesRs = invoicesPS.executeQuery();
            ResultSet expensesRs = expensesPS.executeQuery();
            ResultSet voucherRs = voucherPS.executeQuery();

            // Calculate net cash: invoices - expenses
            if (invoicesRs.next() && expensesRs.next() && voucherRs.next()) {
                data.TotalCashPay = invoicesRs.getDouble("totalcashPay") + voucherRs.getDouble("totalcash_voucher") - expensesRs.getDouble("totalcash_expenses");
                total_Cash_field.setText(String.format("%.2f", data.TotalCashPay) + " $");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
