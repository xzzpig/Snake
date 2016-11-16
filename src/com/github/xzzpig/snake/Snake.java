package com.github.xzzpig.snake;

import java.util.LinkedList;
import java.util.Random;

public class Snake {
	private static final Random RANDOM = new Random();

	private static Snake instance;

	private int size;

	private Body food;

	class Body {
		public int x, y;

		public Body(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	enum Side {
		UP, LEFT, DOWN, RIGHT
	}

	private LinkedList<Body> bodies;

	public Snake() {
		this(24);
	}

	public Snake(int size) {
		this.size = size;
		bodies = new LinkedList<>();
		instance = this;
		bodies.add(new Body(RANDOM.nextInt(size - 1) + 1, RANDOM.nextInt(size - 1) + 1));
		addNewFood();
	}

	public boolean move(Side side) {
		Body head = bodies.getFirst();
		int x = head.x, y = head.y;
		if (side == Side.UP) {
			y--;
		} else if (side == Side.DOWN) {
			y++;
		} else if (side == Side.LEFT) {
			x--;
		} else if (side == Side.RIGHT) {
			x++;
		}
		Body newbody = new Body(x, y);
		bodies.addFirst(newbody);
		if (isSameLoc(head, food))
			addNewFood();
		else
			bodies.removeLast();
		if (isSide())
			return false;
		if (isCatchSelf())
			return false;
		return true;
	}

	private boolean isSide() {
		Body head = bodies.getFirst();
		int x = head.x, y = head.y;
		if (x < 0 || x > size || y < 0 || y > size)
			return true;
		return false;
	}

	private boolean isCatchSelf() {
		Body head = bodies.getFirst();
		for (Body body : bodies) {
			if (body == head)
				continue;
			if (isSameLoc(head, body))
				return true;
		}
		return false;
	}

	private void addNewFood() {
		do {
			food = new Body(RANDOM.nextInt(size), RANDOM.nextInt(size));
		} while (isSameLoc(food, bodies.toArray(new Body[0])));
	}

	private boolean isSameLoc(Body body1, Body body2) {
		if (body1.x == body2.x&&body1.y == body2.y)
			return true;
		return false;
	}

	private boolean isSameLoc(Body body1, Body[] body2) {
		for (Body body : body2) {
			if (isSameLoc(body1, body))
				return true;
		}
		return false;
	}

	public static Snake getInstance() {
		if (instance == null)
			new Snake();
		return instance;
	}

	public int getSize() {
		return size;
	}

	public int getLength() {
		return bodies.size();
	}

	public int[] getBodyLoc(int index) {
		Body body = bodies.get(index);
		return new int[] { body.x, body.y };
	}

	public int[] getFoodLoc() {
		return new int[] { food.x, food.y };
	}
}
