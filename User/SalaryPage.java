package User;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class SalaryPage {
   JPanel tablePanel;
   JPanel travelDetailsPanel;
   DecimalFormat df = new DecimalFormat("\u00A4 ###,###");

   public SalaryPage(int emp_id) {
      SalaryController salaryController = new SalaryController();
      SalaryVO salary = salaryController.getSalary(emp_id);
      List<Object[]> travelDetails = salaryController.getTravelDetails(emp_id);

      displaySalary(salary);
      displayTravelDetails(travelDetails, salary);
   }

   public void displaySalary(SalaryVO salary) {
      tablePanel = new JPanel(new BorderLayout());
      tablePanel.setBounds(140, 40, 300, 410);
      tablePanel.setBackground(Color.WHITE);

      double incomeTax = 0.06;
      double healthInsurance = salary.getMonthly_pay() * 0.035;
      double bonus = salary.getBonus() > 1 ? salary.getBonus() : salary.getMonthly_pay() * salary.getBonus();
      int tr_tax = salary.getTravel_allowance() > 200000 ? salary.getTravel_allowance() - 200000 : 0;
      if (salary.getSalary() <= 50000000) {
         incomeTax = (salary.getMonthly_pay() + bonus + tr_tax) * 0.15;
      } else if (salary.getSalary() > 50000000 && salary.getSalary() <= 88000000) {
         incomeTax = (salary.getMonthly_pay() + bonus + tr_tax) * 0.24;
      } else if (salary.getSalary() > 88000000 && salary.getSalary() <= 150000000) {
         incomeTax = (salary.getMonthly_pay() + bonus + tr_tax) * 0.35;
      } else if (salary.getSalary() > 150000000 && salary.getSalary() <= 300000000) {
         incomeTax = (salary.getMonthly_pay() + bonus + tr_tax) * 0.38;
      }
      double netPay = salary.getMonthly_pay() - incomeTax - healthInsurance + bonus
            + salary.getTransport_allowance() + salary.getTravel_allowance();

      String[] columnNames = { "항목", "내용" };
      Object[][] data = { { "사번", salary.getEMP_id() }, { "이름", salary.getEMP_NAME() },
            { "부서", salary.getDEPT_CODE() }, { "직급", salary.getJOB_CODE() },
            { "연봉", df.format(salary.getSalary()) }, { "월 기본급", df.format(salary.getMonthly_pay()) },
            { "보너스", "+ " + (bonus > 1 ? df.format(bonus) : df.format(bonus) + " (" + (int) (bonus * 100) + "%)") },
            { "출장비", "+ " + df.format(salary.getTravel_allowance()) },
            { "교통비", "+ " + df.format(salary.getTransport_allowance()) }, { "소득세", "- " + df.format(incomeTax) },
            { "건강보험료", "- " + df.format(healthInsurance) }, { "실수령액", df.format(netPay) } };

      DefaultTableModel model = new DefaultTableModel(data, columnNames) {
         @Override
         public boolean isCellEditable(int row, int column) {
            return false;
         }
      };

      JTable salaryTable = new JTable(model);
      salaryTable.setRowHeight(30);
      salaryTable.getColumnModel().getColumn(0).setPreferredWidth(100);
      salaryTable.getColumnModel().getColumn(1).setPreferredWidth(200);

      DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
      centerRenderer.setHorizontalAlignment(JLabel.CENTER);
      salaryTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

      DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
      rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
      salaryTable.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);

      JTableHeader header = salaryTable.getTableHeader();
      header.setReorderingAllowed(false);
      header.setBackground(Color.LIGHT_GRAY);

      salaryTable.setBounds(0, 0, 300, 410);
      tablePanel.add(salaryTable.getTableHeader(), BorderLayout.NORTH);
      tablePanel.add(salaryTable, BorderLayout.CENTER);
      tablePanel.setVisible(true);
      JButton savePdfButton = new JButton("PDF로 저장");
      savePdfButton.addActionListener(e -> saveToPdf(data, columnNames));
      tablePanel.add(savePdfButton, BorderLayout.SOUTH);
   }

   public void displayTravelDetails(List<Object[]> travelDetails, SalaryVO salary) {
      String[] columnNames = { "날짜", "출장지", "금액" };
      DefaultTableModel model = new DefaultTableModel(travelDetails.toArray(new Object[0][]), columnNames) {
         @Override
         public boolean isCellEditable(int row, int column) {
            return false;
         }
      };
      travelDetailsPanel = new JPanel(new BorderLayout());
      travelDetailsPanel.setBounds(450, 40, 400, 410); 
      travelDetailsPanel.setBackground(Color.WHITE);

      JTable travelTable = new JTable(model);
      travelTable.setRowHeight(30);
      travelTable.getColumnModel().getColumn(0).setPreferredWidth(100);
      travelTable.getColumnModel().getColumn(1).setPreferredWidth(200);
      travelTable.getColumnModel().getColumn(2).setPreferredWidth(100);

      DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
      centerRenderer.setHorizontalAlignment(JLabel.CENTER);
      travelTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
      travelTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

      DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
      rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
      travelTable.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);

      JScrollPane scrollPane = new JScrollPane(travelTable); 
      travelDetailsPanel.add(scrollPane, BorderLayout.CENTER);

      JTableHeader header = travelTable.getTableHeader();
      header.setReorderingAllowed(false);
      header.setBackground(Color.LIGHT_GRAY);

      travelDetailsPanel.setPreferredSize(new Dimension(400, 410));
      travelDetailsPanel.add(travelTable.getTableHeader(), BorderLayout.NORTH);

      travelDetailsPanel.setVisible(true); 

      
      
      double tr_tax = salary.getTravel_allowance();
      if (tr_tax > 200000) {
         tr_tax-=200000;
         if (salary.getSalary() <= 50000000) {
            tr_tax = tr_tax * 0.15;
         } else if (salary.getSalary() > 50000000 && salary.getSalary() <= 88000000) {
            tr_tax = tr_tax * 0.24;
         } else if (salary.getSalary() > 88000000 && salary.getSalary() <= 150000000) {
            tr_tax = tr_tax * 0.35;
         } else if (salary.getSalary() > 150000000 && salary.getSalary() <= 300000000) {
            tr_tax = tr_tax * 0.38;
         }
      } else {
         tr_tax = 0;
      }
      
      int totalAmount = 0;
      for (Object[] detail : travelDetails) {
         String amountStr = detail[2].toString().replaceAll("[^0-9]", "");
         totalAmount += Integer.parseInt(amountStr);
      }
      totalAmount-=tr_tax;
      
      

      DecimalFormat df = new DecimalFormat("\u00A4 ###,###");
      JLabel totalLabel = new JLabel("총액: " + df.format(totalAmount), JLabel.RIGHT);
      totalLabel.setFont(new java.awt.Font("나눔고딕", java.awt.Font.BOLD, 18));
      travelDetailsPanel.add(totalLabel, BorderLayout.SOUTH);
      
      JPanel totalPanel = new JPanel(new BorderLayout());
       totalPanel.add(totalLabel, BorderLayout.EAST);

      if (tr_tax > 1) {
         JLabel excessTaxLabel = new JLabel("비과세(20만원) 초과분 과세:"+df.format(totalAmount+tr_tax)+ " - " + df.format(tr_tax));
         excessTaxLabel.setFont(new java.awt.Font("나눔고딕", java.awt.Font.BOLD, 8));
         excessTaxLabel.setHorizontalAlignment(JLabel.LEFT);
         travelDetailsPanel.add(excessTaxLabel, BorderLayout.WEST);
         totalPanel.add(excessTaxLabel, BorderLayout.WEST);
      }
      totalPanel.setBackground(Color.white);
      
      travelDetailsPanel.add(totalPanel, BorderLayout.SOUTH);
   }

   private void saveToPdf(Object[][] data, String[] columnNames) {
      String userHome = System.getProperty("user.home");
      String desktopPath =  userHome + "\\salary_statement.pdf";

      //userHome + 
      Document document = new Document();
      try {
         PdfWriter.getInstance(document, new FileOutputStream(desktopPath));
         document.open();

         String fontPath = "C:\\Users\\jokwo\\Searches\\Downloads\\project0606\\Project1_0531\\NanumGothic.ttf"; // 실제 폰트 파일 경로로 변경
         BaseFont baseFont = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
         Font font = new Font(baseFont, 12);

         PdfPTable table = new PdfPTable(columnNames.length);
         table.setWidthPercentage(100);
         table.setWidths(new float[] { 1, 2 });

         for (String columnName : columnNames) {
            PdfPCell headerCell = new PdfPCell(new Paragraph(columnName, font));
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(headerCell);
         }

         for (Object[] row : data) {
            for (Object cellData : row) {
               PdfPCell cell = new PdfPCell(new Paragraph(cellData.toString(), font));
               cell.setHorizontalAlignment(Element.ALIGN_CENTER);
               table.addCell(cell);
            }
         }

         document.add(table);
         document.close();
         JOptionPane.showMessageDialog(null, "이미지 저장이 완료됐습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
      } catch (Exception e) {
         e.printStackTrace();
         JOptionPane.showMessageDialog(null, "이미지 저장에 실패했습니다: " + e.getMessage(), "실패", JOptionPane.ERROR_MESSAGE);
      }
   }

   public JPanel getTablePanel() {
      return tablePanel;
   }

   public JPanel getTravelDetailsPanel() {
      return travelDetailsPanel;
   }
}

