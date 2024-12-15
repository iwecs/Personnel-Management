package Admin;

import javax.swing.*;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class SalaryInfoManage extends JPanel implements ActionListener, MouseListener {
   private JTable table;
   private DefaultTableModel model;
   private ArrayList<SalaryVO> list;
   DecimalFormat df = new DecimalFormat("\u00A4 ###,###");
   private JTextField searchField;
   private JComboBox<String> searchCriteria;
   private JComboBox<String> salaryCondition;
   private JComboBox<String> bonusTypeCondition; 
   private JPanel conditionPanel;
   private JPanel detailPanel;
   private JLabel detailLabel;
   private AdminPage admin;

   public SalaryInfoManage(JPanel panel) {
      list = SalaryDAO.select();

      setPreferredSize(panel.getSize());
      setBackground(AdminPage.Backgroundcolor);
      setLayout(null);

      JPanel tablePanel = new JPanel();
      tablePanel.setBounds(450, 45, 500, 400);
      tablePanel.setLayout(new BorderLayout());

      model = new DefaultTableModel() {

         @Override
         public boolean isCellEditable(int row, int column) {
            return false; 
         }
      };

      detailPanel = new JPanel();
      detailPanel.setBounds(140, 40, 300, 410);

      detailPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
      detailPanel.setVisible(false);
      add(detailPanel);

      detailLabel = new JLabel();
      detailLabel.setVerticalAlignment(SwingConstants.TOP);
      detailPanel.add(detailLabel); 

      model.addColumn("���");
      model.addColumn("�̸�");
      model.addColumn("����");
      model.addColumn("�����");
      model.addColumn("�����");
      model.addColumn("���ʽ�");
      model.addColumn("�Ǽ���");
      for (SalaryVO temp : list) {
         int bonus1 = temp.getBonus() <= 1 ? (int) (temp.getBonus() * temp.getMonthly_pay()) : (int) temp.getBonus();
         double bonus = temp.getBonus() > 1 ? temp.getBonus() : temp.getMonthly_pay() * temp.getBonus();
         int tr_tax = temp.getTravel_allowance() > 200000 ? temp.getTravel_allowance() - 200000 : 0;
         double incomeTax = 0;
         if (temp.getSalary() <= 50000000) {
            incomeTax = (temp.getMonthly_pay() + bonus + tr_tax) * 0.15;
         } else if (temp.getSalary() > 50000000 && temp.getSalary() <= 88000000) {
            incomeTax = (temp.getMonthly_pay() + bonus + tr_tax) * 0.24;
         } else if (temp.getSalary() > 88000000 && temp.getSalary() <= 150000000) {
            incomeTax = (temp.getMonthly_pay() + bonus + tr_tax) * 0.35;
         } else if (temp.getSalary() > 150000000 && temp.getSalary() <= 300000000) {
            incomeTax = (temp.getMonthly_pay() + bonus + tr_tax) * 0.38;
         }

         double healthInsurance = temp.getMonthly_pay() * 0.035; // 3.5% �ǰ������
         double netPay = temp.getMonthly_pay() + bonus + temp.getTransport_allowance() + temp.getTravel_allowance()
               - incomeTax - healthInsurance;

         String bonusStr = temp.getBonus() <= 1 ? temp.getBonus() * 100 + " %" : df.format(temp.getBonus());

         model.addRow(new Object[] { temp.getEmp_id(), temp.getEmp_name(), df.format(temp.getMonthly_pay()),
               df.format(temp.getTransport_allowance()), df.format(temp.getTravel_allowance()), bonusStr,
               df.format(netPay) });
      }

      table = new JTable(model);
      // table.setFont(new Font("���� ���",Font.BOLD,15));
      // table.setRowHeight(30);
      JScrollPane sp = new JScrollPane(table);
      tablePanel.add(sp, BorderLayout.CENTER);

      
      table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
         @Override
         public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
               int selectedRow = table.getSelectedRow();
               if (selectedRow != -1) {
                  int empId = (int) table.getValueAt(selectedRow, 0);
                  showEmployeeDetails(empId);

               }
            }
         }
      });
      
      add(tablePanel);

      JPanel searchPanel = new JPanel();
      searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

      searchPanel.setBounds(450, 0, 500, 30);
      searchPanel.setBackground(AdminPage.Backgroundcolor);

      String[] searchOptions = { "���", "�̸�", "����", "���ʽ�" }; 
      String[] columnNames = { "EMP_ID", "EMP_NAME", "MONTHLY_PAY", "BONUS" }; 
      searchCriteria = new JComboBox<>(searchOptions);
      searchCriteria.setBackground(AdminPage.Backgroundcolor);
      searchPanel.add(searchCriteria);

      searchField = new JTextField(20);
      searchPanel.add(searchField);

      // ���� ���� üũ�ڽ�
      salaryCondition = new JComboBox<>(new String[] { "�̻�", "����" });
      salaryCondition.setVisible(false);

      bonusTypeCondition = new JComboBox<>(new String[] { "%", "������" });
      bonusTypeCondition.setVisible(false); 

      // ���� �г�
      conditionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
      conditionPanel.add(salaryCondition);
      conditionPanel.add(bonusTypeCondition);
      conditionPanel.setVisible(false);

      // ���ʽ� ���� üũ�ڽ�
      searchPanel.add(conditionPanel);

      JTableHeader header = table.getTableHeader();
      header.setReorderingAllowed(false);
      header.setBackground(Color.LIGHT_GRAY);

      JButton searchButton = new JButton("�˻�");
      searchButton.setBackground(AdminPage.color);
      searchPanel.add(searchButton);

      add(searchPanel);

      searchCriteria.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            updateConditionPanel();
         }
      });

      JPanel tablepanel = new JPanel();
      tablepanel.setBounds(150, 30, 800, 400);

      searchButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            String keyword = searchField.getText();
            int selectedIndex = searchCriteria.getSelectedIndex();
            String criteria = columnNames[selectedIndex]; 
            String salaryCond = salaryCondition.isVisible() ? (String) salaryCondition.getSelectedItem() : null;

            String bonusTypeCond = bonusTypeCondition.isVisible() ? (String) bonusTypeCondition.getSelectedItem()
                  : "";

            
            admin = new AdminPage();
            if (criteria.equals("EMP_ID") || criteria.equals("MONTHLY_PAY") || criteria.equals("BONUS")) {
               if (!isNum(keyword)) {
                  admin.showErrorDialog("���ڸ� �Է����ּ���.");
               } else if (Integer.parseInt(keyword) < 0) {
                  admin.showErrorDialog("������ �Է��� �� �����ϴ�.");
               } else if (bonusTypeCond.equals("%") && Integer.parseInt(keyword) > 100) {
                  admin.showErrorDialog("1~100%������ �Է��� �����մϴ�.");
               } else {
                  filterTable(criteria, keyword, salaryCond, bonusTypeCond);
               }

            } 
            else if(criteria.equals("EMP_NAME")&&isNum(keyword)) {
               admin.showErrorDialog("�̸� �˻��� ���ڰ˻��� �Ұ����մϴ�.");
            }else {
               filterTable(criteria, keyword, salaryCond, bonusTypeCond);
            }
         }
      });

   }

   private void updateConditionPanel() {
      String selectedCriteria = (String) searchCriteria.getSelectedItem();
      conditionPanel.setVisible(true);
      salaryCondition.setVisible(false);
      bonusTypeCondition.setVisible(false);

      conditionPanel.setBackground(AdminPage.Backgroundcolor);
      salaryCondition.setBackground(AdminPage.Backgroundcolor);
      bonusTypeCondition.setBackground(AdminPage.Backgroundcolor);

      if ("����".equals(selectedCriteria)) {
         salaryCondition.setVisible(true);
      } else if ("���ʽ�".equals(selectedCriteria)) {
         salaryCondition.setVisible(true);
         bonusTypeCondition.setVisible(true);
      } else {
         conditionPanel.setVisible(false);
      }

      conditionPanel.revalidate();
      conditionPanel.repaint();
   }

   private void filterTable(String criteria, String keyword, String salaryCond, String bonusTypeCond) {
      list = SalaryDAO.search(criteria, keyword, salaryCond, bonusTypeCond); 
      DefaultTableModel filteredModel = new DefaultTableModel();
      filteredModel.addColumn("���");
      filteredModel.addColumn("�̸�");
      filteredModel.addColumn("����");
      filteredModel.addColumn("�����");
      filteredModel.addColumn("�����");
      filteredModel.addColumn("���ʽ�");
      filteredModel.addColumn("�Ǽ���");
      
      for (SalaryVO temp : list) {
         int bonus1 = temp.getBonus() <= 1 ? (int) (temp.getBonus() * temp.getMonthly_pay()) : (int) temp.getBonus();
         String bonus = temp.getBonus() <= 1 ? temp.getBonus() * 100 + " %" : df.format(temp.getBonus());
         filteredModel.addRow(new Object[] { temp.getEmp_id(), temp.getEmp_name(), df.format(temp.getMonthly_pay()),
               df.format(temp.getTransport_allowance()), df.format(temp.getTravel_allowance()), bonus,
               df.format(temp.getMonthly_pay() + bonus1 + temp.getTransport_allowance()
                     + temp.getTravel_allowance()) });
      }

      table.setModel(filteredModel);
   }

   private void showEmployeeDetails(int empId) {
      DetailVO employee = SalaryDAO.detailSelect(empId);
      if (employee != null) {
         detailPanel.removeAll();
         detailPanel.setLayout(new BorderLayout());
         detailPanel.setVisible(true);

         // �ҵ漼, �ǰ������ ���� ���
         double bonus = employee.getBonus() > 1 ? employee.getBonus()
               : employee.getMonthly_pay() * employee.getBonus();
         double incomeTax = 0;
         int tr_tax = employee.getTravel_allowance() > 200000 ? employee.getTravel_allowance() - 200000 : 0;

         if (employee.getSalary() <= 50000000) {
            incomeTax = (employee.getMonthly_pay() + bonus + tr_tax) * 0.15;
         } else if (employee.getSalary() > 50000000 && employee.getSalary() <= 88000000) {
            incomeTax = (employee.getMonthly_pay() + bonus + tr_tax) * 0.24;
         } else if (employee.getSalary() > 88000000 && employee.getSalary() <= 150000000) {
            incomeTax = (employee.getMonthly_pay() + bonus + tr_tax) * 0.35;
         } else if (employee.getSalary() > 150000000 && employee.getSalary() <= 300000000) {
            incomeTax = (employee.getMonthly_pay() + bonus + tr_tax) * 0.38;
         }

         double healthInsurance = employee.getMonthly_pay() * 0.035; 

         // �Ǽ��ɾ� ���
         int netPay = (int) (employee.getMonthly_pay() + bonus + employee.getTransport_allowance()
               + employee.getTravel_allowance() - incomeTax - healthInsurance);
         String[] columnNames = { "�׸�", "����" };
         Object[][] data = { { "���", employee.getEMP_id() }, { "�̸�", employee.getEMP_NAME() },
               { "�μ�", employee.getDEPT_CODE() }, { "����", employee.getJOB_CODE() },
               { "����", df.format(employee.getSalary()) }, { "�� �⺻��", df.format(employee.getMonthly_pay()) },
               { "���ʽ�", employee.getBonus() > 1 ? df.format(bonus)
                     : df.format(bonus) + " (" + (int) (employee.getBonus() * 100) + "%)" },
               { "�����", "+ " + df.format(employee.getTravel_allowance()) },
               { "�����", "+ " + df.format(employee.getTransport_allowance()) },
               { "�ҵ漼", "- " + df.format(incomeTax) }, { "�ǰ������", "- " + df.format(healthInsurance) },
               { "�Ǽ��ɾ�", df.format(netPay) } };

         DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
         };

         JTable detailTable = new JTable(model);
         detailTable.setRowHeight(30);
         detailTable.getColumnModel().getColumn(0).setPreferredWidth(100);
         detailTable.getColumnModel().getColumn(1).setPreferredWidth(200);

         // �߾� ����
         DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
         centerRenderer.setHorizontalAlignment(JLabel.CENTER);
         detailTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

         // ���� ����
         DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
         rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
         detailTable.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);

         detailPanel
               .setPreferredSize(new Dimension(detailPanel.getWidth(), data.length * detailTable.getRowHeight()));
         detailPanel.add(detailTable.getTableHeader(), BorderLayout.NORTH);
         detailPanel.add(detailTable, BorderLayout.CENTER);

         JButton updateButton = new JButton("����");
         updateButton.setBackground(AdminPage.color);
         detailPanel.add(updateButton, BorderLayout.SOUTH);

         JTableHeader header = detailTable.getTableHeader();
         header.setReorderingAllowed(false);
         header.setBackground(Color.LIGHT_GRAY);

         updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               showUpdateDialog(employee);
            }
         });

         detailPanel.revalidate();
         detailPanel.repaint();
      }
   }

   private void showUpdateDialog(DetailVO employee) {
      JDialog dialog = new JDialog((Frame) null, "���� ����", true);
      dialog.setLayout(null); 
      dialog.getContentPane().setBackground(Color.white);

      // ���
      JLabel label1 = new JLabel("���: ");
      label1.setBounds(15, 20, 80, 25);
      dialog.add(label1);

      JLabel empIdLabel = new JLabel(String.valueOf(employee.getEMP_id()));
      empIdLabel.setBounds(60, 20, 150, 25);
      dialog.add(empIdLabel);

      // �̸�
      JLabel label2 = new JLabel("�̸�: ");
      label2.setBounds(15, 55, 80, 25);
      dialog.add(label2);

      JLabel empNameLabel = new JLabel(employee.getEMP_NAME());
      empNameLabel.setBounds(60, 55, 120, 25);
      dialog.add(empNameLabel);

      // �μ�
      JLabel label3 = new JLabel("�μ�: ");
      label3.setBounds(15, 90, 80, 25);
      dialog.add(label3);

      JLabel deptCodeLabel = new JLabel(employee.getDEPT_CODE());
      deptCodeLabel.setBounds(60, 90, 120, 25);
      dialog.add(deptCodeLabel);

      // ����
      JLabel label4 = new JLabel("����: ");
      label4.setBounds(15, 125, 80, 25);
      dialog.add(label4);

      JLabel jobCodeLabel = new JLabel(employee.getJOB_CODE());
      jobCodeLabel.setBounds(60, 125, 150, 25);
      dialog.add(jobCodeLabel);

      // ����
      JLabel label5 = new JLabel("����: ");
      label5.setBounds(15, 160, 80, 25);
      dialog.add(label5);

      JTextField salaryField = new JTextField(String.valueOf(employee.getSalary()));
      salaryField.setBounds(60, 160, 100, 25);
      dialog.add(salaryField);

      // ���ʽ�
      JLabel label6 = new JLabel("���ʽ�: ");
      label6.setBounds(15, 195, 100, 25);
      dialog.add(label6);

      JTextField bonusField = new JTextField(employee.getBonus() >= 1 ? String.valueOf((int) employee.getBonus())
            : String.valueOf((int) (employee.getBonus() * 100)));
      bonusField.setBounds(60, 195, 100, 25);
      dialog.add(bonusField);

      JComboBox<String> bonusTypeComboBox = new JComboBox<>(new String[] { "%", "������" });
      bonusTypeComboBox.setBounds(160, 195, 70, 25);
      if (employee.getBonus() >= 1) {
         bonusTypeComboBox.setSelectedItem("������");
      } else {
         bonusTypeComboBox.setSelectedItem("%");
      }
      dialog.add(bonusTypeComboBox);

   
      JLabel label8 = new JLabel("�����: ");
      label8.setBounds(15, 230, 80, 25);
      dialog.add(label8);

      JTextField transportAllowanceField = new JTextField(String.valueOf(employee.getTransport_allowance()));
      transportAllowanceField.setBounds(60, 230, 100, 25);
      dialog.add(transportAllowanceField);

      // ��ư �г�
      JButton saveButton = new JButton("����");
      saveButton.setBounds(45, 265, 70, 30);
      dialog.add(saveButton);

      JButton cancelButton = new JButton("���");
      cancelButton.setBounds(135, 265, 70, 30);
      dialog.add(cancelButton);
      saveButton.setBackground(AdminPage.color);
      cancelButton.setBackground(AdminPage.color);

      saveButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            try {
               int salary = Integer.parseInt(salaryField.getText());
               if (salary < 26400000) {
                  JOptionPane.showMessageDialog(dialog, "������ 2640���� �̻��̾�� �մϴ�.", "�Է� ����", JOptionPane.ERROR_MESSAGE);
                  
               }

               double bonus;
               if (bonusTypeComboBox.getSelectedItem().equals("%")) {
                  int bonusPercent = Integer.parseInt(bonusField.getText());
                  if (bonusPercent < 1 || bonusPercent > 100) {
                     JOptionPane.showMessageDialog(dialog, "���ʽ� ������ 1%���� 100% ���̿��� �մϴ�.", "�Է� ����", JOptionPane.ERROR_MESSAGE);
                     
                  }
                  bonus = bonusPercent / 100.0;
               } else {
                  bonus = Double.parseDouble(bonusField.getText());
                  int monthlyPay = salary / 12;
                  if (bonus < 300000 || bonus > monthlyPay) {
                     JOptionPane.showMessageDialog(dialog, "������ ���ʽ��� 30�������� �⺻ ������ 100% ���̿��� �մϴ�.", "�Է� ����", JOptionPane.ERROR_MESSAGE);
                     
                  }
               }

               int transportAllowance = Integer.parseInt(transportAllowanceField.getText());

               if (bonus < 0 || salary < 0 || transportAllowance < 0) {
                  JOptionPane.showMessageDialog(dialog, "������ �Է� �Ұ����մϴ�.", "�Է� ����", JOptionPane.ERROR_MESSAGE);
               }
               int monthlyPay = salary / 12; 

               SalaryDAO.updateSalary(employee.getEMP_id(), salary, monthlyPay, bonus, transportAllowance);
               dialog.dispose();
               refreshTable();
               showEmployeeDetails(employee.getEMP_id());
            } catch (NumberFormatException ne) {
               JOptionPane.showMessageDialog(dialog, "�߸��� ���� �����Դϴ�.", "�Է� ����", JOptionPane.ERROR_MESSAGE);
            }
         }
      });

      cancelButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            dialog.dispose();
         }
      });
      dialog.setResizable(false);
      dialog.setSize(265, 350);
      dialog.setLocationRelativeTo(null);
      dialog.setVisible(true);

   }

   private void refreshTable() {
      list = SalaryDAO.select(); 
      model.setRowCount(0);
      for (SalaryVO temp : list) {
         int bonus1 = temp.getBonus() <= 1 ? (int) (temp.getBonus() * temp.getMonthly_pay()) : (int) temp.getBonus();
         String bonus = temp.getBonus() <= 1 ? temp.getBonus() * 100 + " %" : df.format(temp.getBonus());
         model.addRow(new Object[] { temp.getEmp_id(), temp.getEmp_name(), df.format(temp.getMonthly_pay()),
               df.format(temp.getTransport_allowance()), df.format(temp.getTravel_allowance()), bonus,
               df.format(temp.getMonthly_pay() + bonus1 + temp.getTransport_allowance()
                     + temp.getTravel_allowance()) });
      } 
   }

   

   @Override
   public void actionPerformed(ActionEvent e) {
      // TODO Auto-generated method stub

   }

   @Override
   public void mouseClicked(MouseEvent e) {
      // TODO Auto-generated method stub

   }

   @Override
   public void mousePressed(MouseEvent e) {
      // TODO Auto-generated method stub

   }

   @Override
   public void mouseReleased(MouseEvent e) {
      // TODO Auto-generated method stub

   }

   @Override
   public void mouseEntered(MouseEvent e) {
      // TODO Auto-generated method stub

   }

   @Override
   public void mouseExited(MouseEvent e) {
      // TODO Auto-generated method stub

   }

   private static boolean isNum(String str) {
      if (str == null || str.isEmpty()) {
         return false;
      }
      try {
         Integer.parseInt(str);
         return true;
      } catch (NumberFormatException e) {
         return false;
      }
   }
}
