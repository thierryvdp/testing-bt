package irl.common.tool;

public class Position {
	
	private Coord positionSpatiale;
	private Coord direction;
	private Coord vitesse;
	
	public Position(Coord _positionSpatiale,Coord _direction,Coord _vitesse) {
		positionSpatiale=_positionSpatiale;
		direction=_direction;
		vitesse=_vitesse;
	}

	public Coord getPositionSpatiale() {
		return positionSpatiale;
	}

	public void setPositionSpatiale(Coord positionSpatiale) {
		this.positionSpatiale = positionSpatiale;
	}

	public Coord getDirection() {
		return direction;
	}

	public void setDirection(Coord direction) {
		this.direction = direction;
	}

	public Coord getVitesse() {
		return vitesse;
	}

	public void setVitesse(Coord vitesse) {
		this.vitesse = vitesse;
	}

}
