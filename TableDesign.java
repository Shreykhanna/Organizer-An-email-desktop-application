/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class TableDesign {
  public static void main(String[] argv) throws Exception {
    TableModel model = new MyTableModel();
    JTable table = new JTable(model);

//    model.addColumn("Select");
//    model.addColumn("Subject");
//    model.insertRow(0, new Object[]{false,"Hello world"});
//    model.insertRow(model.getRowCount(), new Object[]{false,"This is important"});
//    model.insertRow(model.getRowCount(), new Object[]{true,"SnapDeal Offer"});
    
//    model.moveRow(0, 0, model.getRowCount()-1);
//    model.removeRow(1);
    JFrame f = new JFrame();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setSize(300, 300);
    f.add(new JScrollPane(table));
    f.setVisible(true);
  }
}

class MyTableModel extends AbstractTableModel{
    
    Object rowData[][] = { { "1", Boolean.FALSE }, { "2", Boolean.FALSE }, { "3", Boolean.FALSE },
      { "4", Boolean.FALSE }, { "5", Boolean.FALSE }, };

    String columnNames[] = { "English", "Boolean" };
  
    public boolean isCellEditable(int rowIndex, int mColIndex) {
        return true;
    }
    
    public int getColumnCount() {
        return columnNames.length;
      }
    
    public String getColumnName(int column) {
        return columnNames[column];
      }

    public int getRowCount() {
      return rowData.length;
    }

    public Object getValueAt(int row, int column) {
      return rowData[row][column];
    }

    public Class getColumnClass(int column) {
      return (getValueAt(0, column).getClass());
    }

    public void setValueAt(Object value, int row, int column) {
      rowData[row][column] = value;
    }
}
