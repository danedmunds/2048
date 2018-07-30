package ca.danedmunds.twentyfortyeight.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class TwentyFortyEight {

	private Random rand = new Random();

	private static final int NO_VALUE = -1;

	private static class PointIterator implements Iterator<Point> {

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Point next() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	private enum Direction {
		Up(0, -1) {
			@Override
			protected Iterator<Point> traversalIterator(int width, int height) {
				return null;
			}
		},
		Down(0, 1) {
			@Override
			protected Iterator<Point> traversalIterator(int width, int height) {
				return null;
			}
		},
		Right(1, 0) {
			@Override
			protected Iterator<Point> traversalIterator(int width, int height) {
				return null;
			}
		},
		Left(-1, 0) {
			@Override
			protected Iterator<Point> traversalIterator(int width, int height) {
				return null;
			}
		};

		private Point directionVector;

		private Direction(int xMod, int yMod) {
			directionVector = new Point(xMod, yMod);
		}

		private Point advance(Point point) {
			return new Point(point.x + directionVector.x,
					point.y + directionVector.y);
		}

		private Point backout(Point point) {
			return new Point(point.x - directionVector.x,
					point.y - directionVector.y);
		}

		protected abstract Iterator<Point> traversalIterator(int width, int height);
	}

	private final int width;
	private final int height;

	private final int[][] cells;

	public TwentyFortyEight(int width, int height) {
		this.width = width;
		this.height = height;

		cells = new int[width][height];
		for (int x = 0; x < getWidth(); ++x) {
			for (int y = 0; y < getHeight(); ++y) {
				setValue(new Point(x, y), NO_VALUE);
			}
		}
	}

	public void up() {
		boolean[][] combinedThisRound = new boolean[getHeight()][getWidth()];
		boolean somethingMoved = false;

		for (int x = 0; x < getWidth(); ++x) {
			for (int y = 0; y < getHeight(); ++y) {
				somethingMoved |= moveSingle(new Point(x, y), Direction.Up, combinedThisRound);
			}
		}

		if (somethingMoved) {
			newValue();
		}
	}

	public void down() {
		boolean[][] combinedThisRound = new boolean[getHeight()][getWidth()];
		boolean somethingMoved = false;

		for (int x = 0; x < getWidth(); ++x) {
			for (int y = getHeight() - 1; y >= 0; --y) {
				somethingMoved |= moveSingle(new Point(x, y), Direction.Down, combinedThisRound);
			}
		}

		if (somethingMoved) {
			newValue();
		}
	}

	public void right() {
		boolean[][] combinedThisRound = new boolean[getHeight()][getWidth()];
		boolean somethingMoved = false;

		for (int y = 0; y < getHeight(); ++y) {
			for (int x = getWidth() - 1; x >= 0; --x) {
				somethingMoved |= moveSingle(new Point(x, y), Direction.Right, combinedThisRound);
			}
		}

		if (somethingMoved) {
			newValue();
		}
	}

	public void left() {
		left(true);
	}

	public void left(boolean newValue) {
		boolean[][] combinedThisRound = new boolean[getHeight()][getWidth()];
		boolean somethingMoved = false;

		for (int x = 0; x < getWidth(); ++x) {
			for (int y = 0; y < getHeight(); ++y) {
				somethingMoved |= moveSingle(new Point(x, y), Direction.Left, combinedThisRound);
			}
		}

		if (newValue && somethingMoved) {
			newValue();
		}
	}

	private boolean moveSingle(Point point, Direction direction, boolean[][] combinedThisMove) {
		Point next = new Point(point);

		// don't move empties
		if (isEmpty(next)) {
			return false;
		}

		// 'pick up' the value
		int value = getValue(point);
		setValue(point, NO_VALUE);

		while (isInRange(next) && isEmpty(next)) {
			next = direction.advance(next);
		}

		Point newLocation;
		if (isInRange(next) && !combinedThisMove(next, combinedThisMove) && getValue(next.x, next.y) == value) {
			newLocation = next;
			setValue(next, 2 * value);
			flagCombinedThisRount(next, combinedThisMove);
		} else {
			newLocation = direction.backout(next);
			setValue(newLocation, value);
		}

		return !newLocation.equals(point);
	}

	private int getValue(Point point) {
		return getValue(point.x, point.y);
	}

	private void flagCombinedThisRount(Point point, boolean[][] combinedThisMove) {
		combinedThisMove[point.x][point.y] = true;
	}

	protected void setValue(Point point, int value) {
		cells[point.x][point.y] = value;
	}

	private boolean isInRange(Point coords) {
		return coords.x < getWidth() && coords.x >= 0 &&
				coords.y < getHeight() && coords.y >= 0;
	}

	private boolean isEmpty(Point point) {
		return cells[point.x][point.y] == NO_VALUE;
	}

	private boolean combinedThisMove(Point point, boolean[][] combinedThisMove) {
		return combinedThisMove[point.x][point.y];
	}

	public int getValue(int x, int y) {
		assert(x < width - 1);
		assert(y < height - 1);

		return cells[x][y];
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void start() {
		newValue();
		newValue();
	}

	private void newValue() {
		List<Point> emptyCells = new ArrayList<Point>();
		for (int y = 0; y < getHeight(); ++y) {
			for (int x = 0; x < getWidth(); ++x) {
				Point toConsider = new Point(x, y);
				if (isEmpty(toConsider)) {
					emptyCells.add(toConsider);
				}
			}
		}

		if (!emptyCells.isEmpty()) {
			Point toGenerate = emptyCells.get(rand.nextInt(emptyCells.size()));
			setValue(toGenerate, 2);
		}
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int y = 0; y < getHeight(); ++y) {
			for (int x = 0; x < getWidth(); ++x) {
				builder.append(getValue(x, y)).append(",");
			}
			builder.replace(builder.length() - 1, builder.length(), "\n");
		}

		return builder.toString();
	}
}
