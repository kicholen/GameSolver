package view;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import service.solver.ISolverService;

public class MainFrame extends JFrame implements ActionListener, KeyListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ISolverService _service;
	Dimension _dim;
	JButton _button;
	boolean _working;
	
	public void init(ISolverService service) {
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
        addKeyListener(this);
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
			_service.stop();
			_working = false;
		}
		else {
			_button.setText("stop");
			_service.start();
			_working = true;
		}
	}

	public boolean isWorking() {
		return _working;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
			_service.destroy();
			System.exit(ABORT);
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}
}
