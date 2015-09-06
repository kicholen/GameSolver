package service.mouse;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.Random;

import service.IService;
import service.MainService;

public class MouseClickService implements IService {
	MainService _mainService;
	Robot _robot;
	Point _gamePosition;
	int _offset;
	Random _rand;
	
	public MouseClickService(MainService mainService) {
		_mainService = mainService;
		_offset = 9;
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
		/*--_offset;
		if (_offset > 0) {
			_robot.mouseMove(_gamePosition.x + rand(0, _mainService.getScreenshotService().getGameSize().width), _gamePosition.y + rand(0, _mainService.getScreenshotService().getGameSize().height));
		}
		else
		{
			_offset = 9;*/
			_robot.mouseMove(_gamePosition.x + x, _gamePosition.y + y);
			_robot.mousePress(InputEvent.BUTTON1_MASK);
			_robot.mouseRelease(InputEvent.BUTTON1_MASK);
		//}
	}
	
	int rand(int min, int max) {
		if (_rand == null) {
			_rand = new Random();
		}
	    return _rand.nextInt((max - min) + 1) + min;
	}
	
}
