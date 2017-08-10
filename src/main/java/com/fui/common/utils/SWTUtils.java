package com.fui.common.utils;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class SWTUtils {
	
	/**
	 * 居中控件
	 * @param composite
	 */
	public static void centerPosition(Composite composite) {
		Rectangle bounds = Display.getDefault().getPrimaryMonitor().getBounds();
		Rectangle rect = composite.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		composite.setLocation(x, y);
	}
}
