package com.fui.ui.system;

import com.fui.common.utils.DateUtils;
import com.fui.common.utils.SpringMvcUtils;
import com.fui.model.User;
import com.fui.service.UserService;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.wb.swt.SWTResourceManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserComposite extends Composite {
	private Table table;
	private Text txtUserName;
	private Text text;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public UserComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new FormLayout());

		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION | SWT.CHECK | SWT.MULTI);
		FormData fd_table = new FormData();
		fd_table.bottom = new FormAttachment(100, -10);
		fd_table.right = new FormAttachment(100, -10);
		fd_table.top = new FormAttachment(0, 104);
		fd_table.left = new FormAttachment(0, 10);
		table.setLayoutData(fd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		final TableColumn choice = new TableColumn(table, SWT.BORDER);
		choice.setData(0);
		choice.setImage(SWTResourceManager.getImage(UserComposite.class, "/images/select.png"));
		choice.setWidth(32);
		choice.setAlignment(SWT.CENTER);

		TableColumn ename = new TableColumn(table, SWT.BORDER);
		ename.setText("登录名");
		ename.setWidth(100);
		ename.setAlignment(SWT.CENTER);

		TableColumn cname = new TableColumn(table, SWT.BORDER);
		cname.setText("真实姓名");
		cname.setWidth(130);
		cname.setAlignment(SWT.CENTER);

		TableColumn createTime = new TableColumn(table, SWT.BORDER);
		createTime.setText("创建时间");
		createTime.setWidth(150);
		createTime.setAlignment(SWT.CENTER);

		TableColumn lastLoginTime = new TableColumn(table, SWT.BORDER);
		lastLoginTime.setText("最后一次登录时间");
		lastLoginTime.setWidth(150);
		lastLoginTime.setAlignment(SWT.CENTER);

		final CheckboxTableViewer checkboxTableViewer = new CheckboxTableViewer(table);
		checkboxTableViewer.addCheckStateListener(new ICheckStateListener() {
			public void checkStateChanged(CheckStateChangedEvent event) {
				changeCheckBoxState(checkboxTableViewer, choice);
			}
		});

		ToolBar toolBar = new ToolBar(this, SWT.FLAT | SWT.RIGHT);
		FormData fd_toolBar = new FormData();
		fd_toolBar.bottom = new FormAttachment(table, -6);
		fd_toolBar.left = new FormAttachment(table, 0, SWT.LEFT);
		fd_toolBar.right = new FormAttachment(100, -10);
		toolBar.setLayoutData(fd_toolBar);

		ToolItem tltmQuery = new ToolItem(toolBar, SWT.NONE);
		tltmQuery.setImage(SWTResourceManager.getImage(UserComposite.class, "/images/search.gif"));
		tltmQuery.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		tltmQuery.setText("查询");

		ToolItem tltmInsert = new ToolItem(toolBar, SWT.NONE);
		tltmInsert.setImage(SWTResourceManager.getImage(UserComposite.class, "/images/add.gif"));
		tltmInsert.setText("新增");

		ToolItem tltmUpdate = new ToolItem(toolBar, SWT.NONE);
		tltmUpdate.setImage(SWTResourceManager.getImage(UserComposite.class, "/images/edit.gif"));
		tltmUpdate.setText("修改");

		Label label = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		FormData fd_label = new FormData();
		fd_label.top = new FormAttachment(toolBar, -8, SWT.TOP);
		fd_label.right = new FormAttachment(100, -10);
		fd_label.bottom = new FormAttachment(toolBar, -6);
		fd_label.left = new FormAttachment(table, 0, SWT.LEFT);
		label.setLayoutData(fd_label);

		Label lblUserName = new Label(this, SWT.NONE);
		FormData fd_lblUserName = new FormData();
		fd_lblUserName.bottom = new FormAttachment(label, -23);
		fd_lblUserName.left = new FormAttachment(table, 0, SWT.LEFT);
		lblUserName.setLayoutData(fd_lblUserName);
		lblUserName.setText("登录名：");

		txtUserName = new Text(this, SWT.BORDER);
		FormData fd_txtUserName = new FormData();
		fd_txtUserName.right = new FormAttachment(lblUserName, 185, SWT.RIGHT);
		fd_txtUserName.top = new FormAttachment(0, 21);
		fd_txtUserName.left = new FormAttachment(lblUserName, 5);
		txtUserName.setLayoutData(fd_txtUserName);

		Label lblUserCname = new Label(this, SWT.NONE);
		FormData fd_lblUserCname = new FormData();
		fd_lblUserCname.top = new FormAttachment(lblUserName, 0, SWT.TOP);
		fd_lblUserCname.left = new FormAttachment(txtUserName, 71);
		lblUserCname.setLayoutData(fd_lblUserCname);
		lblUserCname.setText("真实姓名：");

		text = new Text(this, SWT.BORDER);
		FormData fd_text = new FormData();
		fd_text.right = new FormAttachment(lblUserCname, 185, SWT.RIGHT);
		fd_text.top = new FormAttachment(0, 21);
		fd_text.left = new FormAttachment(lblUserCname, 6);
		text.setLayoutData(fd_text);
		// 为表格设置内容提供者
		checkboxTableViewer.setContentProvider(new ArrayContentProvider());
		// 为表格设置标签提供者，最下面给出代码
		checkboxTableViewer.setLabelProvider(new CheckTableLabelProvider());

		UserService userService = SpringMvcUtils.get(UserService.class);
		Map<String, Object> params = new HashMap<String, Object>();
		List<User> userList = userService.getUserList(params);
		checkboxTableViewer.setInput(userList);

		choice.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Integer state = (Integer) choice.getData();
				if (state.intValue() == 0) {
					choice.setImage(
							SWTResourceManager.getImage(UserComposite.class, "/images/selected.png"));
					choice.setData(1);
					checkboxTableViewer.setAllChecked(true);
				} else {
					choice.setImage(
							SWTResourceManager.getImage(UserComposite.class, "/images/select.png"));
					choice.setData(0);
					checkboxTableViewer.setAllChecked(false);
				}
			}
		});
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	/**
	 * 更改复选框状态
	 */
	public void changeCheckBoxState(CheckboxTableViewer checkboxTableViewer, TableColumn choice) {
		if (checkboxTableViewer.getTable().getItemCount() == checkboxTableViewer.getCheckedElements().length) {
			choice.setData(1);
			choice.setImage(SWTResourceManager.getImage(UserComposite.class, "/images/selected.png"));
		} else {
			choice.setData(0);
			choice.setImage(SWTResourceManager.getImage(UserComposite.class, "/images/select.png"));
		}
	}

	// 表格所使用的标签提供者
	class CheckTableLabelProvider extends LabelProvider implements ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof User) {
				User user = (User) element;
				switch (columnIndex) {
				case 0:
					return "";
				case 1:
					return user.getEname();

				case 2:
					return user.getCname();

				case 3:
					return DateUtils.getDateStr19(user.getCreateTime());

				case 4:
					return DateUtils.getDateStr19(user.getLastLoginTime());
				}
			}
			return null;
		}
	}
}
