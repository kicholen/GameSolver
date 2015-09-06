package main;
import javax.swing.SwingUtilities;

import service.MainService;
import view.MainFrame;


public class Main {
	static MainService _mainService;
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				init();
			}
		});
	}
	
	public static void init() {
		_mainService = new MainService();
		_mainService.init();
		
		MainFrame main = new MainFrame();
		main.init(_mainService);
	}

}
