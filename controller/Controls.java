package controller;

import gamebase.GameBase;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

import actors.MainActor;

public class Controls implements KeyListener {

	private static MainActor player1 = GameBase.player1;
	private static MainActor player2 = GameBase.player2;

	Set<Integer> keysPressed = new HashSet<>();

	public Controls() {}

	@Override
	public void keyPressed(KeyEvent event) {
		if (!keysPressed.contains(event.getKeyCode())) {
			keysPressed.add(event.getKeyCode());
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {
		keysPressed.remove(event.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent event) {}

	public void update() {
		if (keysPressed.contains(KeyEvent.VK_UP)) {
			player1.moveForward();
		} else {
			player1.setMovingForward(false);
		}

		if (keysPressed.contains(KeyEvent.VK_DOWN)) {
			player1.moveBackward();
		} else {
			player1.setMovingBackward(false);
		}

		if (keysPressed.contains(KeyEvent.VK_LEFT)) {
			player1.rotateLeft();
		} else {
			player1.setRotatingLeft(false);
		}

		if (keysPressed.contains(KeyEvent.VK_RIGHT)) {
			player1.rotateRight();
		} else {
			player1.setRotatingRight(false);
		}

		if (keysPressed.contains(KeyEvent.VK_SLASH)) {
			if (player1.canFire()) {
				player1.fire();
			}
		} else {
			player1.setCanFire(true);
		}

		if (keysPressed.contains(KeyEvent.VK_W)) {
			player2.moveForward();
		} else {
			player2.setMovingForward(false);
		}

		if (keysPressed.contains(KeyEvent.VK_S)) {
			player2.moveBackward();
		} else {
			player2.setMovingBackward(false);
		}

		if (keysPressed.contains(KeyEvent.VK_A)) {
			player2.rotateLeft();
		} else {
			player2.setRotatingLeft(false);
		}

		if (keysPressed.contains(KeyEvent.VK_D)) {
			player2.rotateRight();
		} else {
			player2.setRotatingRight(false);
		}

		if (keysPressed.contains(KeyEvent.VK_G)) {
			if (player2.canFire()) {
				player2.fire();
			}
		} else {
			player2.setCanFire(true);
		}
	}

}
