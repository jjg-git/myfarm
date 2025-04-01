/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ccprog3.myfarm.guinterface;

import com.ccprog3.myfarm.GUIControl;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author John Joseph F. Giron
 */
public class CheckBoxIcon extends JPanel{
    private JLabel icon;
    private JLabel nameLabel;
    private ImageIcon iconImage;
    private JCheckBox checkbox;
    
    private String name;
    
    public CheckBoxIcon(String name, String icon) {
        iconImage = new ImageIcon(icon);
        this.name = name;
        
        if (GUIControl.debug)
            setBorder(BorderFactory.createLineBorder(Color.red, 5));
        
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        
        initialize();
    }
    
    private void setupIcon() {
        icon = new JLabel(iconImage);
        icon.setHorizontalAlignment(JLabel.LEFT);
    }

    private void initialize() {
        checkbox = new JCheckBox();
        nameLabel = new JLabel(name);
        
        setupIcon();
        
        checkbox.setAlignmentX(Component.LEFT_ALIGNMENT);
        icon.setAlignmentX(Component.LEFT_ALIGNMENT);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        add(checkbox);
        add(icon);
        add(nameLabel);
    }
    
    public JCheckBox getCheckBox() {
        return checkbox;
    }
}
