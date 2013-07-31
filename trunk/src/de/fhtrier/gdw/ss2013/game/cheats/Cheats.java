package de.fhtrier.gdw.ss2013.game.cheats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.ss2013.game.world.World;

public class Cheats {

	private String currentCheatCode = "";
	private World world;
	private HashMap<String, Cheat> cheats = new HashMap<>();
	private ArrayList<Cheat> enabledCheats = new ArrayList<>();

	public Cheats(World world) {
		this.world = world;
		addStandardCheats();
	}

	private void addStandardCheats() {
		cheats.put("iddqd", new Invincibility());
		cheats.put("idkfa", new FullPower());
	}

	private void checkCheats() {
		boolean legalCheat = false;
		for (String code : cheats.keySet()) {
			if (code.startsWith(currentCheatCode)) {
				legalCheat = true;
				if (code.equals(currentCheatCode)) {
					Cheat cheat = cheats.get(currentCheatCode);
					if (enabledCheats.contains(cheat)) {
						enabledCheats.remove(cheat);
					} else {
						enabledCheats.add(cheat);
					}
					currentCheatCode = "";
				}
			}
		}
		if (!legalCheat) {
			currentCheatCode = "";
		}
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		Iterator<Cheat> iterator = enabledCheats.iterator();
		while (iterator.hasNext()) {
			Cheat cheat = iterator.next();
			boolean isFinished = cheat.doCheat(container, game, delta, world);
			if (isFinished) {
				iterator.remove();
			}
		}
	}

	public void addKey(char c) {
		currentCheatCode += c;
		checkCheats();
	}

	private interface Cheat {
		boolean doCheat(GameContainer container, StateBasedGame game, int delta,
				World world);
	};

	private class Invincibility implements Cheat {
		@Override
		public boolean doCheat(GameContainer container, StateBasedGame game,
				int delta, World world) {
			world.getAstronaut().setOxygen(world.getAstronaut().getMaxOxygen());
			return false;
		}
	}

	private class FullPower implements Cheat {
		@Override
		public boolean doCheat(GameContainer container, StateBasedGame game,
				int delta, World world) {
			world.getAstronaut().setOxygen(world.getAstronaut().getMaxOxygen());
			return true;
		}
	}
}
