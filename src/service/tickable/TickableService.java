package service.tickable;

import java.util.ArrayList;
import java.util.List;

import service.IService;

public class TickableService extends Thread implements IService {
	int _updateInterval;
	List<ITickable> _tickables = new ArrayList<ITickable>();
	Lock _lock = new Lock();
	
	public void run() {
		while (true) {
			_lock.lock();
			for (int i = 0; i < _tickables.size(); i++) {
				_tickables.get(i).tick();
			}
			
			try {
				_lock.unlock();
				sleep(_updateInterval);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void remove(ITickable tickable) {
		_lock.lock();
		_tickables.remove(tickable);
		if (_tickables.size() == 0) {
			yield();
		}
		_lock.unlock();
	}
	
	public void add(ITickable tickable) {
		_lock.lock();
		_tickables.add(tickable);
		if (!isAlive()) {
			start();
		}
		_lock.unlock();
	}

	@Override
	public void init() {
		_updateInterval = 17;
	}
}
