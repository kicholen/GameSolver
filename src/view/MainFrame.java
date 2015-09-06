package view;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import service.MainService;

public class MainFrame extends JFrame implements ActionListener, NativeKeyListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MainService _service;
	Dimension _dim;
	JButton _button;
	boolean _working;
	
	public void init(MainService service) {
		_service = service;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        _dim = _dim == null ? new Dimension(200, 80) : _dim;
        setSize(_dim);
        setVisible(true);
        setState(Frame.NORMAL);
        _button = new JButton("Start");
        _button.setVerticalTextPosition(AbstractButton.CENTER);
        _button.setHorizontalTextPosition(AbstractButton.LEADING);
        _button.setActionCommand("start");
        _button.addActionListener(this);
        add(_button);
        setFocusable(true);
        setVisible(true);
        
        addGlobalListener();
        WindowAdapterEx adapter = new WindowAdapterEx();
        adapter.setData(this);
        addWindowListener(adapter);
        
        LogManager.getLogManager().reset();
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
	}
    
    private void addGlobalListener() {
    	try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
        	ex.printStackTrace();
        }
    	GlobalScreen.addNativeKeyListener(this);
	}

	@Override
    public void setSize(Dimension dim) {
    	_dim = dim;
    	super.setSize(dim);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if (_working) {
			_button.setText("start");
			_service.getSolverService().stop();
			_working = false;
		}
		else {
			_button.setText("stop");
			_service.getSolverService().start();
			_working = true;
		}
	}

	public boolean isWorking() {
		return _working;
	}
	
	public void destroy() {
		_service.destroy();
	}
	
	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            try {
				GlobalScreen.unregisterNativeHook();
			} catch (NativeHookException e1) {
				e1.printStackTrace();
			}
            _service.destroy();
			System.exit(ABORT);
        }
		
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0) {
		
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {
		
	}
}
