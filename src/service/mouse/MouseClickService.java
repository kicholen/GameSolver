package service.mouse;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;

import service.IService;
import service.MainService;

public class MouseClickService implements IService {
	MainService _mainService;
	Robot _robot;
	Point _gamePosition;
	
	public MouseClickService(MainService mainService) {
		_mainService = mainService;
	}
	
	@Override
	public void init() {
		try {
			_robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		_gamePosition = new Point(_mainService.getScreenshotService().getScreenSize().width - _mainService.getScreenshotService().getGameSize().width + _mainService.getScreenshotService().getGamePositionOffset().width,
				_mainService.getScreenshotService().getScreenSize().height - _mainService.getScreenshotService().getGameSize().height + _mainService.getScreenshotService().getGamePositionOffset().height);
	}

	@Override
	public void destroy() {
		
	}

	public void click(int x, int y) {
		_robot.mouseMove(_gamePosition.x + x, _gamePosition.y + y);
		_robot.mousePress(InputEvent.BUTTON1_MASK);
		_robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
}
