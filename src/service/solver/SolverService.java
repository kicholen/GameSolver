package service.solver;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import service.MainService;
import service.tickable.ITickable;

public class SolverService implements ISolverService, ITickable {
	static int WIDTH = 11;
	static int HEIGHT = 9;
	
	MainService _mainService;
	int[][] _blocks;
	Dimension _gameSize;
	Dimension _blockSize;
	Point _currentPoint = new Point();
	Point _previousPoint = new Point();
	
	public SolverService(MainService mainService) {
		_mainService = mainService;
	}
	
	@Override
	public void start() {
		_mainService.getTickableService().add(this);
	}

	@Override
	public void stop() {
		_mainService.getTickableService().remove(this);
	}

	@Override
	public void init() {
		_blocks = new int[11][9];
		fillBlocks();
	}

	@Override
	public void destroy() {
		
	}

	@Override
	public void tick() {
		BufferedImage image = _mainService.getScreenshotService().takeScreenshot();
		readImage(image);
		
		if (checkForMatch()) {
			_mainService.getMouseClickService().click(_previousPoint.x * _blockSize.width + _blockSize.width / 2 - 5, _previousPoint.y * _blockSize.height + _blockSize.height / 2 + 10);
		}

		if (shouldSaveToFile()) {
			String format = "jpg";
	        String fileName = "PartialScreenshot." + format;
			try {
				ImageIO.write(image, format, new File(fileName));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean shouldSaveToFile() {
		return false;
	}

	void readImage(BufferedImage image) {
		if (_gameSize == null) {
			_gameSize = new Dimension(image.getWidth(), image.getHeight());
			_blockSize = new Dimension(_gameSize.width / WIDTH, _gameSize.height / HEIGHT);
		}
		
		for (int i = 0; i < _blocks.length; i++) {
			for (int j = 0; j < _blocks[i].length; j++) {
				Color colorOfBlock = new Color(image.getRGB(_blockSize.width * i + _blockSize.width / 2, _blockSize.height * j + _blockSize.height / 2));
				_blocks[i][j] = getColorEnum(colorOfBlock);
				//System.out.println(colorOfBlock.getRed() + "," + colorOfBlock.getGreen() + "," + colorOfBlock.getBlue() + " color: " + getColorName(_blocks[i][j]));
			}
			i++;
		}
		
		for (int i = 1; i < _blocks.length; i++) {
			for (int j = 0; j < _blocks[i].length; j++) {
				Color colorOfBlock = new Color(image.getRGB(_blockSize.width * i + _blockSize.width / 2, _blockSize.height * j + _blockSize.height));
				_blocks[i][j] = getColorEnum(colorOfBlock);
				//System.out.println(colorOfBlock.getRed() + "," + colorOfBlock.getGreen() + "," + colorOfBlock.getBlue() + " color: " + getColorName(_blocks[i][j]));
			}
			i++;
		}
	}
	
	boolean checkForMatch() {
		boolean found = false;
		
		for (int i = 0; i < _blocks.length && !found; i++) {
			for (int j = 0; j < _blocks[i].length && !found; j++) {
				_currentPoint.setLocation(i, j);
				_previousPoint.setLocation(i, j);
				if (isSpecial(_blocks[i][j])) {
					System.out.println("thunder");
					found = true;
				}
				else if (checkNeighborhood(i, j, true)) {
					if (checkNeighborhood(_currentPoint.x, _currentPoint.y, false)) {
						found = true;
					}
				}
			}
		}
		
		return found;
	}
	
	private boolean isSpecial(int i) {
		return i == 10;
	}

	boolean checkNeighborhood(int i, int j, boolean isNew) {
		boolean result = false;
		if ((i == 0 && j == 0) || _blocks[i][j] == 0) {
			return false;
		}
		if (j > 0 && _blocks[i][j - 1] == _blocks[i][j] && isNotRepeated(isNew, i, j - 1)) {
			_currentPoint.setLocation(i, j - 1);
			result = true;
		}
		else if (j < HEIGHT - 1 && _blocks[i][j + 1] == _blocks[i][j] && isNotRepeated(isNew, i, j + 1)) {
			_currentPoint.setLocation(i, j + 1);
			return true;
		}
		else if (i % 2 == 0) {
			if (i >= 1) {
				if (j > 0 && _blocks[i - 1][j - 1] == _blocks[i][j] && isNotRepeated(isNew, i - 1, j - 1)) {
					_currentPoint.setLocation(i - 1, j - 1);
					result = true;
				}
				else if (_blocks[i - 1][j] == _blocks[i][j] && isNotRepeated(isNew, i - 1, j)) {
					_currentPoint.setLocation(i - 1, j);
					result = true;
				}
			}
			if (!result && i < WIDTH - 1) {
				if (j > 0 && _blocks[i + 1][j - 1] == _blocks[i][j] && isNotRepeated(isNew, i + 1, j - 1)) {
					_currentPoint.setLocation(i + 1, j - 1);
					result = true;
				}
				else if (_blocks[i + 1][j] == _blocks[i][j] && isNotRepeated(isNew, i + 1, j)) {
					_currentPoint.setLocation(i + 1, j);
					result = true;
				}
			}
		}
		else {
			if (i >= 1) {
				if (j < HEIGHT - 1 && _blocks[i - 1][j + 1] == _blocks[i][j] && isNotRepeated(isNew, i - 1, j + 1)) {
					_currentPoint.setLocation(i - 1, j + 1);
					result = true;
				}
				else if (_blocks[i - 1][j] == _blocks[i][j] && isNotRepeated(isNew, i - 1, j)) {
					_currentPoint.setLocation(i - 1, j);
					result = true;
				}
			}
			if (!result && i < WIDTH - 1) {
				if (j < HEIGHT - 1 && _blocks[i + 1][j + 1] == _blocks[i][j] && isNotRepeated(isNew, i + 1, j + 1)) {
					_currentPoint.setLocation(i + 1, j + 1);
					result = true;
				}
				else if (_blocks[i + 1][j] == _blocks[i][j] && isNotRepeated(isNew, i + 1, j)) {
					_currentPoint.setLocation(i + 1, j);
					result = true;
				}
			}
		}
		
		return result;
	}

	boolean isNotRepeated(boolean isNew, int i, int j) {
		if (isNew) {
			return true;
		}
		
		return !(_previousPoint.x == i && _previousPoint.y == j);
	}

	int getColorEnum(Color colorOfBlock) {
		if (colorOfBlock.getGreen() > 190 && colorOfBlock.getRed() < 200 && colorOfBlock.getBlue() < 50) { //green
			return 1;
		}
		else if (colorOfBlock.getRed() == 0 && colorOfBlock.getGreen() == 196 && colorOfBlock.getBlue() == 255) {
			return 10;
		}
		else if (colorOfBlock.getBlue() > 240 && colorOfBlock.getRed() < 150) {//blue
			return 2;
		}
		else if (colorOfBlock.getRed() < 100 && colorOfBlock.getBlue() > 150 && colorOfBlock.getGreen() > 140) {//light blue
			return 3;
		}
		else if (colorOfBlock.getRed() > 200 && colorOfBlock.getGreen() > 180 && colorOfBlock.getBlue() < 130) { //orange
			return 6;
		}
		else if (colorOfBlock.getRed() > 190 && colorOfBlock.getGreen() < 100 && colorOfBlock.getBlue() < 100) {//redBlock
			return 4;
		}
		else if (colorOfBlock.getRed() > 100 && colorOfBlock.getGreen() < 150 && colorOfBlock.getBlue() > 190) { //pink
			return 5;
		}
		else {
			return 0;
		}
	}
	
	String getColorName(int value) {
		String result = "dupa";
		if (value == 1) {
			result = "green";
		}
		else if (value == 2) {
			result = "blue";
		}
		else if (value == 3) {
			result = "light_blue";
		}
		else if (value == 4) {
			result = "red";
		}
		else if (value == 5) {
			result = "pink";
		}
		else if (value == 6) {
			result = "orange";
		}
		else if (value == 10) {
			result = "thunder";
		}
		return result;
	}
	int getPixelPosition(int i, int j) {
		return _blockSize.width * i - _blockSize.width / 2;
	}
	
	void fillBlocks() {
		for (int i = 0; i < _blocks.length; i++) {
			for (int j = 0; j < _blocks[i].length; j++) {
				_blocks[i][j] = -1;
			}
		}
	}
}
