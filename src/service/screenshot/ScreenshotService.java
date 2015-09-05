package service.screenshot;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import service.IService;
import service.MainService;

public class ScreenshotService implements IService {
	MainService _mainService;
	BufferedImage _image;
	Robot _robot;
	Dimension _screenSize;
	Dimension _gameSize;
	Dimension _gamePositionOffset;
	Rectangle _captureRect;
	
	public ScreenshotService(MainService mainService) {
		_mainService = mainService;
	}
	
	@Override
	public void init() {
        try {
			_robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
        _gameSize = new Dimension(405, 375);
        _gamePositionOffset = new Dimension(-50, -95);
        _screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        _captureRect = new Rectangle(_screenSize.width - _gameSize.width + _gamePositionOffset.width,
        		_screenSize.height - _gameSize.height + _gamePositionOffset.height,
        		_gameSize.width,
        		_gameSize.height);
	}

	@Override
	public void destroy() {
		
	}

	public BufferedImage takeScreenshot() {
        _image = _robot.createScreenCapture(_captureRect);
        return _image;
	}

	public Dimension getGamePositionOffset() {
		return _gamePositionOffset;
	}

	public Dimension getScreenSize() {
		return _screenSize;
	}
	
	public Dimension getGameSize() {
		return _gameSize;
	}
}
