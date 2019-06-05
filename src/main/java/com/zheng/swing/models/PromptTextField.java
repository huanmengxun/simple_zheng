package com.zheng.swing.models;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JTextField;

import sun.swing.SwingUtilities2;

/**
 * Title: L&F组件<br>
 * Description: To change this template use File | Settings | File Templates.<br>
 * Copyright: Copyright (c) 2016-11-9<br>
 * MSun<br>
 * 
 * @author jiujiya
 * @version 1.0
 */
public class PromptTextField extends JTextField {

    private String promptText;
    private Color promptColor;

    public PromptTextField(String promptText, Color promptColor) {
        this.promptText = promptText;
        this.promptColor = promptColor;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getText().equals("")) {
            int h = getHeight();
            Insets insets = getInsets();
            Font font = getFont();
            FontMetrics fm = g.getFontMetrics(font);
            g.setColor(promptColor);
            g.setFont(font);
            int ascent = fm.getAscent();
            int descent = fm.getDescent();
            int y = (h  - (ascent + descent)) / 2 + ascent;
            SwingUtilities2.drawStringUnderlineCharAt(this, g, promptText, -1,
                    insets.left + 3, y);
        }
    }

}
