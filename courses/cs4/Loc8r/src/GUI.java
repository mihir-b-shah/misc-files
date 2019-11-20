
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class GUI { 
    private static final Font fontNB;
    private static final Font fontB;
    private static final Border border;
    
    static {
        fontB = new Font("Calibri", Font.BOLD, 36);
        fontNB = new Font("Calibri", Font.PLAIN, 36);
        border = new LineBorder(Color.BLACK);
    }
    
    public static void main(String[] args) {
        partOne();
        partTwo();
    }
    
    public static void partOne() {
        JPanel[] panels =  new JPanel[9];
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
        
        JLabel longit = new JLabel("Longitude: ");
        longit.setFont(fontB);
        panels[2].add(longit);
        panels[2].add(jtf1);
        JLabel lat = new JLabel("     Latitude: ");
        lat.setFont(fontB);
        panels[2].add(lat);
        panels[2].add(jtf2);
        panels[3].add(new JLabel("\n\n"));
        
        JLabel keyword = new JLabel("Keyword: ");
        keyword.setFont(fontB);
        panels[4].add(keyword);
        JTextField kfield = new JTextField();
        kfield.setColumns(40);
        kfield.setFont(fontNB);
        panels[4].add(kfield);
        
        JLabel andorxor = new JLabel("And/Or/Xor: ");
        andorxor.setFont(fontB);
        panels[5].add(andorxor);
        JComboBox andor = new JComboBox();
        andor.addItem("AND"); andor.addItem("OR"); andor.addItem("XOR");
        panels[5].add(andor);
        
        JLabel type = new JLabel("Type: ");
        type.setFont(fontB);
        panels[6].add(type);
        JComboBox tbox = new JComboBox();
        String[] set = Backend.getTypes();
        for(int i = 0; i<set.length; ++i) {
            tbox.addItem(set[i]);
        }
        panels[6].add(tbox);
        
        JLabel address = new JLabel("Address: ");
        address.setFont(fontB);
        panels[7].add(address);
        JTextField afield = new JTextField();
        afield.setColumns(40);
        afield.setFont(fontNB);
        panels[7].add(afield);

        JOptionPane.showMessageDialog(null, panels);
    }
    
    public static void partTwo() {
        JPanel[] panels =  new JPanel[3];
        for(int i = 0; i<panels.length; ++i) {
            panels[i] = new JPanel();
        }
        JLabel img = new JLabel();
        ImageIcon icon;
        img.setIcon(icon = new ImageIcon("Loc8r_Logo_Black.png"));
        icon.setImage(icon.getImage().getScaledInstance(900, 300, 0));
        panels[0].add(img);
        
        String[] results = Backend.genResults();
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
        table.getColumnModel().getColumn(0).setCellRenderer(center);
        table.getColumnModel().getColumn(1).setCellRenderer(center);
        table.getColumnModel().getColumn(2).setCellRenderer(center);
        
        Object[] header = {"Rank", "Description", "Rate!"};
        dtm.addRow(header);
        panels[1].add(table);
        
        for(int i = 0; i<results.length; ++i) {
            Object[] array = new Object[3];
            array[0] = i+1;
            array[1] = results[i];
            array[2] = "";
            dtm.addRow(array);
        }
        
        JOptionPane.showMessageDialog(null, panels);
    }
}