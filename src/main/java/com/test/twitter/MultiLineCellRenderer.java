package com.test.twitter;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 *  https://stackoverflow.com/questions/9955595/how-to-display-multiple-lines-in-a-jtable-cell
 *  added class for wrapping text in cells of Jtable
 */

public class MultiLineCellRenderer extends JList<String> implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        //make multi line where the cell value is String[]
        if (value instanceof String[]) {
            setListData((String[]) value);
        }
        setFont(UIManager.getFont("Century Gothic"));
        return this;
    }

}
