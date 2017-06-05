/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Main {
  public static void main(String[] argv) throws Exception {
    DefaultTableModel model = new DefaultTableModel();
    JTable table = new JTable(model);

    model.addColumn("Col1");
    model.addColumn("Col2");

    model.addRow(new Object[] { "v1", "v2" });

    model.addRow(new Object[] { "v1" });

    model.addRow(new Object[] { "v1", "v2", "v3" });

    JFrame f = new JFrame();
    f.setSize(300, 300);
    f.add(new JScrollPane(table));
    f.setVisible(true);

  }
}