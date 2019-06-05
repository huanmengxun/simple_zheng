package com.zheng.swing.models;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.border.AbstractBorder;

/**
 * Title: L&F组件<br>
 * Description: 自定义GrayShadowBorder<br>
 * Copyright: Copyright (c) 2016-11-9<br>
 * MSun<br>
 * 
 * @author jiujiya
 * @version 1.0
 */
public class GrayShadowBorder extends AbstractBorder {

    /**
	 * 
	 */
	private static final long serialVersionUID = -9088843717946938542L;
	private boolean bottom = false;

    public GrayShadowBorder() {
    }

    public GrayShadowBorder(boolean bottom) {
        this.bottom = bottom;
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(bottom ? 0 : 3,0,0, bottom ? 3 : 0);
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;

        if (!bottom) {
            g2d.setColor(new Color(0,0,0, 10));
            g2d.drawLine(0, 0, width, 0);
            g2d.setColor(new Color(0, 0, 0, 35));
            g2d.drawLine(0, 1, width, 1);
            g2d.setColor(new Color(0, 0, 0, 55));
            g2d.drawLine(0, 2, width, 2);
        } else {
            g2d.setColor(new Color(0,0,0, 10));
            g2d.drawLine(0, height - 3, width, height - 3);
            g2d.setColor(new Color(0, 0, 0, 35));
            g2d.drawLine(0, height - 2, width, height - 2);
            g2d.setColor(new Color(0, 0, 0, 55));
            g2d.drawLine(0, height - 1, width, height - 1);
        }


    }
}
