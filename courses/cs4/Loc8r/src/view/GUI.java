
package view;

import java.awt.Color;
import java.awt.Font;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import application.*;
import java.awt.Dimension;

public class GUI { 
    private static final Font fontNB;
    private static final Font fontB;
    private static final Border border;
    private static String errors;
    private static List<String> results;
    private static int retTwo;
    
    static {
        fontB = new Font("Calibri", Font.BOLD, 36);
        fontNB = new Font("Calibri", Font.PLAIN, 36);
        border = new LineBorder(Color.BLACK);
        errors = "";
        UIManager.put("OptionPane.maximumSize", new Dimension(600,400));
    }
    
    public static void main(String[] args) {
        Input input = null;
        int ctr = 0;
        int ret = 0;
        do {
            if(ctr != 0) {
                ret = JOptionPane.showConfirmDialog(null, "Errors detected:"
                        + "\n\n".concat(input.getErrors()), "Error",
                        JOptionPane.OK_CANCEL_OPTION);
                if(ret == JOptionPane.CANCEL_OPTION) {
                    break;
                }
            }
            input = partOne();
            ++ctr;
        } while(input != null && input.getErrors().length() > 0 && ret == 0);
        if(ret != JOptionPane.CANCEL_OPTION) {
            Backend.setInput(input);
            results = Backend.genResults();
            do {
                partTwo(input);
            } while(errors.length()>0&&retTwo!=JOptionPane.CANCEL_OPTION);
        }
    }
    
    public static Input partOne() {
        JPanel[] panels =  new JPanel[7];
        for(int i = 0; i<panels.length; ++i) {
            panels[i] = new JPanel();
        }
        JLabel img = new JLabel();
        ImageIcon icon;
        img.setIcon(icon = new ImageIcon("Loc8r_Logo_Black.png"));
        icon.setImage(icon.getImage().getScaledInstance(900, 300, 0));
        panels[0].add(img);
        
        JTextField jtf1 = new JTextField();
        jtf1.setColumns(17);
        jtf1.setFont(fontNB);
        JTextField jtf2 = new JTextField();
        jtf2.setColumns(17);
        jtf2.setFont(fontNB);
        panels[1].add(new JLabel("\n\n"));
        
        JLabel lat = new JLabel("Latitude: ");
        lat.setFont(fontB);
        panels[2].add(lat);
        panels[2].add(jtf1);
        JLabel longit = new JLabel("     Longitude: ");
        longit.setFont(fontB);
        panels[2].add(longit);
        panels[2].add(jtf2);
        panels[3].add(new JLabel("\n\n"));
        
        JRadioButton rb1 = new JRadioButton();
        rb1.setText("Low priority");
        rb1.setFont(fontNB);
        panels[4].add(rb1);
        JLabel keyword = new JLabel("Keyword: ");
        keyword.setFont(fontB);
        panels[4].add(keyword);
        JTextField kfield = new JTextField();
        kfield.setColumns(40);
        kfield.setFont(fontNB);
        panels[4].add(kfield);
        
        JLabel type = new JLabel("Type: ");
        type.setFont(fontB);
        panels[5].add(type);
        String[] set = Backend.getTypes();
        JList tbox = new JList(set);
        panels[5].add(new JScrollPane(tbox));
        
        JRadioButton rb2 = new JRadioButton();
        rb2.setText("Low priority");
        rb2.setFont(fontNB);
        panels[6].add(rb2);
        JLabel address = new JLabel("Address: ");
        address.setFont(fontB);
        panels[6].add(address);
        JTextField afield = new JTextField();
        afield.setColumns(40);
        afield.setFont(fontNB);
        panels[6].add(afield);

        JOptionPane.showMessageDialog(null, panels, 
                "The coolest application ever", JOptionPane.INFORMATION_MESSAGE);
        Input input = new Input(jtf1.getText(), jtf2.getText(), kfield.getText(),
                tbox.getSelectedIndices(), afield.getText(), rb1.isSelected(),
                rb2.isSelected());
        return input;
    }
    
    public static void partTwo(Input in) {
        JPanel[] panels =  new JPanel[3];
        for(int i = 0; i<panels.length; ++i) {
            panels[i] = new JPanel();
        }
        JLabel img = new JLabel();
        ImageIcon icon;
        img.setIcon(icon = new ImageIcon("/Loc8r_Logo_Black.png"));
        icon.setImage(icon.getImage().getScaledInstance(900, 300, 0));
        panels[0].add(img);
        
        DefaultTableModel dtm;
        JTable table = new JTable(
                dtm = new DefaultTableModel(0,3){
                    @Override
                    public boolean isCellEditable(int r, int c) {
                        return r>0&c==2;
                    }
                });
        table.setBorder(border);
        
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(800);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.setRowHeight(60);
        
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i = 0; i<3; ++i) {
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }
        
        Object[] header = {"Rank", "Description", "Rate (0-4)"};
        dtm.addRow(header);
        panels[1].add(table);
        
        for(int i = 0; i<results.size(); ++i) {
            Object[] array = new Object[3];
            array[0] = i+1;
            array[1] = results.get(i);
            array[2] = "";
            dtm.addRow(array);
        }
        
        JOptionPane.showMessageDialog(null, panels, 
                "The coolest application ever", JOptionPane.INFORMATION_MESSAGE);
        int[] res = new int[results.size()];
        int i = -1;
        try {
            String s;
            for(i = 1; i<=results.size(); ++i) {
                s = (String) dtm.getValueAt(i, 2);
                if(s.length()>0) {
                    res[i-1] = Integer.parseInt((String) dtm.getValueAt(i, 2));
                } else {
                    res[i-1] = 2;
                }
            }
            in.setRatings(res);
            errors = "";
            Backend.dump();
        } catch (NumberFormatException nfe) {
            errors = String.format("Error in parsing rating #%d", i);
            retTwo = JOptionPane.showConfirmDialog(null, errors, "Error", 
                    JOptionPane.OK_CANCEL_OPTION);
        }
    }
}