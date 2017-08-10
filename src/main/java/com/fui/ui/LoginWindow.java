package com.fui.ui;

import com.alibaba.fastjson.JSONObject;
import com.fui.common.utils.KeyCode;
import com.fui.common.utils.SWTUtils;
import com.fui.common.utils.SpringMvcUtils;
import com.fui.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.wb.swt.SWTResourceManager;

public class LoginWindow {

    protected Shell shell;
    protected Display display = Display.getDefault();
    private Text txtUserName;
    private Text txtUserPwd;

    /**
     * Launch the application.
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            LoginWindow window = new LoginWindow();
            window.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Open the window.
     */
    public void open() {
        createContents();
        shell.open();
        shell.layout();
        SWTUtils.centerPosition(shell);

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    /**
     * Create contents of the window.
     */
    protected void createContents() {
        shell = new Shell(display, SWT.MIN | SWT.CLOSE);
        shell.setSize(425, 370);
        shell.setText("登录");
        shell.setLayout(new FormLayout());

        Label lblBanner = new Label(shell, SWT.NONE);
        lblBanner.setAlignment(SWT.CENTER);
        lblBanner.setImage(SWTResourceManager.getImage(LoginWindow.class, "/images/banner.jpg"));
        FormData fd_lblBanner = new FormData();
        fd_lblBanner.top = new FormAttachment(0);
        fd_lblBanner.left = new FormAttachment(0);
        fd_lblBanner.bottom = new FormAttachment(0, 66);
        fd_lblBanner.right = new FormAttachment(100);
        lblBanner.setLayoutData(fd_lblBanner);

        Label lblUserName = new Label(shell, SWT.NONE);
        FormData fd_lblUserName = new FormData();
        fd_lblUserName.top = new FormAttachment(lblBanner, 56);
        lblUserName.setLayoutData(fd_lblUserName);
        lblUserName.setText("用户名：");

        txtUserName = new Text(shell, SWT.BORDER);
        txtUserName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.keyCode;
                if (keyCode == KeyCode.ENTER || keyCode == KeyCode.SMALL_KEY_BOARD_ENTER) {
                    login();
                }
            }
        });
        FormData fd_txtUserName = new FormData();
        fd_txtUserName.right = new FormAttachment(100, -77);
        fd_txtUserName.left = new FormAttachment(lblUserName, 55);
        fd_txtUserName.top = new FormAttachment(lblUserName, -3, SWT.TOP);
        txtUserName.setLayoutData(fd_txtUserName);

        Label lblUserPwd = new Label(shell, SWT.NONE);
        fd_lblUserName.left = new FormAttachment(lblUserPwd, 0, SWT.LEFT);
        FormData fd_lblUserPwd = new FormData();
        fd_lblUserPwd.top = new FormAttachment(lblUserName, 32);
        fd_lblUserPwd.left = new FormAttachment(0, 69);
        lblUserPwd.setLayoutData(fd_lblUserPwd);
        lblUserPwd.setText("密   码：");

        txtUserPwd = new Text(shell, SWT.BORDER | SWT.PASSWORD);
        txtUserPwd.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.keyCode;
                if (keyCode == KeyCode.ENTER || keyCode == KeyCode.SMALL_KEY_BOARD_ENTER) {
                    login();
                }
            }
        });
        FormData fd_txtUserPwd = new FormData();
        fd_txtUserPwd.top = new FormAttachment(lblUserPwd, -3, SWT.TOP);
        fd_txtUserPwd.right = new FormAttachment(txtUserName, 0, SWT.RIGHT);
        fd_txtUserPwd.left = new FormAttachment(0, 172);
        txtUserPwd.setLayoutData(fd_txtUserPwd);

        Button btnLogin = new Button(shell, SWT.NONE);
        btnLogin.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.keyCode;
                if (keyCode == KeyCode.ENTER || keyCode == KeyCode.SMALL_KEY_BOARD_ENTER) {
                    login();
                }
            }
        });
        btnLogin.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                login();
            }
        });
        FormData fd_btnLogin = new FormData();
        fd_btnLogin.right = new FormAttachment(txtUserName, -61, SWT.RIGHT);
        fd_btnLogin.left = new FormAttachment(lblUserName, 85, SWT.LEFT);
        fd_btnLogin.top = new FormAttachment(txtUserPwd, 60);
        btnLogin.setLayoutData(fd_btnLogin);
        btnLogin.setText("登录");
    }

    private void login() {
        String userName = txtUserName.getText().trim();
        String userPwd = txtUserPwd.getText().trim();
        if (StringUtils.isBlank(userName)) {
            MessageBox infoBox = new MessageBox(shell, SWT.ICON_INFORMATION);
            infoBox.setText("提示信息");
            infoBox.setMessage("用户名不能为空！");
            infoBox.open();
            txtUserName.setFocus();
            return;
        }
        if (StringUtils.isBlank(userPwd)) {
            MessageBox infoBox = new MessageBox(shell, SWT.ICON_INFORMATION);
            infoBox.setText("提示信息");
            infoBox.setMessage("用户密码不能为空！");
            infoBox.open();
            txtUserPwd.setFocus();
            return;
        }
        UserService userService = SpringMvcUtils.get(UserService.class);
        JSONObject json = userService.login(userName, userPwd);
        int state = json.getIntValue("state");
        if (state == 1) {
            shell.setVisible(false);
            shell.dispose();
            new MainWindow().open();
        } else {
            MessageBox infoBox = new MessageBox(shell, SWT.ICON_WARNING);
            infoBox.setText("提示信息");
            infoBox.setMessage(json.getString("message"));
            infoBox.open();
        }
    }
}
