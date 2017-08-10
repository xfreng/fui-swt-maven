package com.fui.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import com.fui.common.utils.SWTUtils;
import com.fui.ui.system.UserComposite;

public class MainWindow {

	protected Shell shell;

	private Menu menu;
	private TabFolder tabFolder;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainWindow window = new MainWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		SWTUtils.centerPosition(shell);
		shell.setMaximized(true);

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
		shell = new Shell();
		shell.setSize(1024, 768);
		shell.setText("主窗口");
		shell.setLayout(new FormLayout());

		menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);

		MenuItem sysMenu = new MenuItem(menu, SWT.CASCADE);
		sysMenu.setText("系统管理");

		Menu userMenu = new Menu(sysMenu);
		sysMenu.setMenu(userMenu);

		MenuItem userMenuItem = new MenuItem(userMenu, SWT.NONE);
		userMenuItem.setText("用户管理");

		tabFolder = new TabFolder(shell, SWT.NONE);
		FormData fd_tabFolder = new FormData();
		fd_tabFolder.bottom = new FormAttachment(100);
		fd_tabFolder.right = new FormAttachment(100);
		fd_tabFolder.top = new FormAttachment(0);
		fd_tabFolder.left = new FormAttachment(0);
		tabFolder.setLayoutData(fd_tabFolder);

		// 这里继承composite就好了。
		final TabItem comaTabItem = new TabItem(tabFolder, SWT.NONE);
		comaTabItem.setText("用户管理");
		// 实例化一个我们定制的composite
		final Composite composite = new UserComposite(tabFolder, SWT.NONE);
		// 将我们定制的composite附加到tabItem上。
		comaTabItem.setControl(composite);
	}
}
