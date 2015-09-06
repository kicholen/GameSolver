package view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

public class WindowAdapterEx extends WindowAdapter {
	
	MainFrame _this;

	@Override
    public void windowClosing(WindowEvent windowEvent) {
		_this.destroy();
    	try {
			GlobalScreen.unregisterNativeHook();
		} catch (NativeHookException e) {
			e.printStackTrace();
		}
    	GlobalScreen.removeNativeKeyListener(_this);
    }

	public void setData(MainFrame mainFrame) {
		_this = mainFrame;
	}
}
