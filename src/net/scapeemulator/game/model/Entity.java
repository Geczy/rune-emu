package net.scapeemulator.game.model;

public abstract class Entity {

	protected Position position;

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

}
