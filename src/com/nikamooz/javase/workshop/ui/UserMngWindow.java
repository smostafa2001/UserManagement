package com.nikamooz.javase.workshop.ui;

import com.nikamooz.javase.workshop.data.entity.User;
import com.nikamooz.javase.workshop.service.ServiceException;
import com.nikamooz.javase.workshop.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.util.logging.Logger;

public class UserMngWindow extends JFrame implements ActionListener {

    Logger logger = Logger.getLogger(UserMngWindow.class.getName());

    UserTableModel model = new UserTableModel();
    JTable table = UIUtility.createTable(model);

    JButton addBtn = new JButton(new ImageIcon("icon/add.png"));
    JButton editBtn = new JButton(new ImageIcon("icon/edit.png"));
    JButton deleteBtn = new JButton(new ImageIcon("icon/delete.png"));
    JButton excelBtn = new JButton(new ImageIcon("icon/excel.png"));

    JMenuItem changePasswordItem = UIUtility.createMenuItem("main.menu.changePassword");
    JRadioButtonMenuItem farsiItem = UIUtility.createRadioButtonMenuItem("main.menu.farsi", !UIUtility.isLTR());
    JRadioButtonMenuItem englishItem = UIUtility.createRadioButtonMenuItem("main.menu.english", UIUtility.isLTR());

    UserService userService = new UserService();

    public UserMngWindow() {
        setJMenuBar(createMenuBar());
        add(createToolBar(), BorderLayout.PAGE_START);
        model.setUsers(userService.findAll());
        add(new JScrollPane(table));
        UIUtility.setLocation(this, 500, 250);
        setVisible(true);
    }

    private JMenuBar createMenuBar(){
        JMenuBar bar = UIUtility.createMenuBar();
        JMenu fileMenu = UIUtility.createMenu("main.menu.file");
        ButtonGroup group = new ButtonGroup();
        group.add(farsiItem);
        group.add(englishItem);
        bar.add(fileMenu);
        farsiItem.addActionListener(this);
        englishItem.addActionListener(this);
        changePasswordItem.addActionListener(this);
        fileMenu.add(changePasswordItem);
        fileMenu.add(farsiItem);
        fileMenu.add(englishItem);
        return bar;
    }

    private JToolBar createToolBar(){
        JToolBar bar = new JToolBar();
        addBtn.addActionListener(this);
        editBtn.addActionListener(this);
        deleteBtn.addActionListener(this);
        excelBtn.addActionListener(this);
        bar.add(addBtn);
        bar.add(editBtn);
        bar.add(deleteBtn);
        bar.addSeparator();
        bar.add(excelBtn);
        return bar;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == addBtn) {
                UserDialog dialog = new UserDialog();
                if (dialog.isOk()) {
                    User user = dialog.getUser();
                    dialog.dispose();
                    userService.save(user);
                    model.setUsers(userService.findAll());
                }
            }else if(e.getSource()==editBtn){
                int row = table.getSelectedRow();
                if(row!=-1) {
                    User user = model.getSelectedUser(row);
                    UserDialog dialog = new UserDialog(user);
                    if(dialog.isOk()){
                        userService.update(user);
                        dialog.dispose();
                        model.setUsers(userService.findAll());
                    }
                }
            }else if(e.getSource()==deleteBtn){
                int row = table.getSelectedRow();
                if(row!=-1) {
                    User user = model.getSelectedUser(row);
                    int result = JOptionPane.showConfirmDialog(this, "Are you sure?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if(result==JOptionPane.YES_OPTION){
                        userService.delete(user);
                        model.setUsers(userService.findAll());
                    }
                }
            }else if(e.getSource()==farsiItem){
                if(!I18NUtility.getLanguage().equals("fa")) {
                    I18NUtility.setLanguage("fa");
                    this.setVisible(true);
                    new UserMngWindow();
                    this.dispose();
                }
            }else if(e.getSource()==englishItem){
                if(!I18NUtility.getLanguage().equals("en")) {
                    I18NUtility.setLanguage("en");
                    this.setVisible(true);
                    new UserMngWindow();
                    this.dispose();
                }
            }else if(e.getSource()==excelBtn){
                JFileChooser chooser = new JFileChooser();
                int result = chooser.showSaveDialog(this);
                if(result==JFileChooser.APPROVE_OPTION){
                   File file=  chooser.getSelectedFile();
                   ExcelExporter.export(model.getUsers(), file);
                }
            }
        }catch (Exception ex){
            logger.severe("Error: "+ex.getMessage());
            JOptionPane.showMessageDialog(this, "Error: "+ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }
    }
}
