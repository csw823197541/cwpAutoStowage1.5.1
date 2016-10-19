package viewFrame;

import javax.swing.table.DefaultTableModel;

/**
 * Created by csw on 2016/1/19.
 */
public class TableModel extends DefaultTableModel{

    public Class getColumnClass(int column) {
        Class returnValue;
        if ((column >= 0) && (column < getColumnCount())) {
            if(getValueAt(0, column) != null) {
                returnValue = getValueAt(0, column).getClass();
            } else {
                returnValue = Object.class;
            }
        } else {
            returnValue = Object.class;
        }
        return returnValue;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
