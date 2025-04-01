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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author John Joseph F. Giron
 */
public class RadioIcon extends JPanel{
    private JLabel icon;
    private JLabel nameLabel;
    private ImageIcon iconImage;
    private JRadioButton radio;
    
    private String name;
    
    public RadioIcon(String name, String icon) {
        iconImage = new ImageIcon(icon);
        this.name = name;
        
        if (GUIControl.debug)
            setBorder(BorderFactory.createLineBorder(Color.blue, 5));
        
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        
        initialize();
    }
    
    private void setupIcon() {
        icon = new JLabel(iconImage);
        icon.setHorizontalAlignment(JLabel.LEFT);
    }

    private void initialize() {
        radio = new JRadioButton();
        nameLabel = new JLabel(name);
        
        setupIcon();
        
        radio.setAlignmentX(Component.LEFT_ALIGNMENT);
        icon.setAlignmentX(Component.LEFT_ALIGNMENT);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        add(radio);
        add(icon);
        add(nameLabel);
    }
    
    public JRadioButton getRadio() {
        return radio;
    }
}
