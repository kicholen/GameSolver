package service;

import service.mouse.MouseClickService;
import service.screenshot.ScreenshotService;
import service.solver.SolverService;
import service.tickable.TickableService;

public class MainService implements IService {
	MouseClickService _mouseClickService;
	ScreenshotService _screenshotService;
	SolverService _solverService;
	TickableService _tickableService;
	
	@Override
	public void init() {
		_mouseClickService = new MouseClickService(this);
		_screenshotService = new ScreenshotService(this);
		_screenshotService.init();
		_solverService = new SolverService(this);
		_solverService.init();
		_tickableService = new TickableService();
		_tickableService.init();
		_mouseClickService.init();
	}
	
	@Override
	public void destroy() {
		_mouseClickService.destroy();
		_screenshotService.destroy();
		_solverService.destroy();
	}
	
	public MouseClickService getMouseClickService() {
		return _mouseClickService;
	}
	
	public ScreenshotService getScreenshotService() {
		return _screenshotService;
	}
	
	public SolverService getSolverService() {
		return _solverService;
	}
	
	public TickableService getTickableService() {
		return _tickableService;
	}
}
