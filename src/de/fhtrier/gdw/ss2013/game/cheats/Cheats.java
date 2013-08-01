package de.fhtrier.gdw.ss2013.game.cheats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import de.fhtrier.gdw.ss2013.game.world.World;

public class Cheats {

	private String currentCheatCode = "";
	private World world;
	private HashMap<String, Cheat> cheats = new HashMap<>();
	private ArrayList<Cheat> cheatsThatNeedupdate = new ArrayList<>();
	private Queue<Cheat> newCheats = new LinkedList<>();
	
	public static boolean isGodmode = false;

	public Cheats(World world) {
		this.world = world;
		addStandardCheats();
	}

	private void checkCheats() {
		boolean legalCheat = false;
		for (String code : cheats.keySet()) {
			if (code.startsWith(currentCheatCode)) {
				legalCheat = true;
				if (code.equals(currentCheatCode)) {
					Cheat cheat = cheats.get(currentCheatCode);
					newCheats.add(cheat);
					currentCheatCode = "";
				}
			}
		}
		if (!legalCheat) {
			currentCheatCode = "";
		}
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		while (newCheats.peek() != null) {
			final Cheat cheat = newCheats.poll();
			if (cheatsThatNeedupdate.contains(cheat)) {
				cheatsThatNeedupdate.remove(cheat);
				cheat.end(container, game, delta, world);
			} else {
				cheat.start(container, game, delta, world);
				if (cheat.wantsToBeUpdated) {
					cheatsThatNeedupdate.add(cheat);
				}
			}
		}

		for (Cheat cheat : cheatsThatNeedupdate) {
			cheat.doCheat(container, game, delta, world);
		}
	}

	public void addKey(char c) {
		currentCheatCode += c;
		checkCheats();
	}

	private abstract class Cheat {
		protected boolean wantsToBeUpdated = true;

		void start(GameContainer container, StateBasedGame game, int delta,
				World world) {
		}

		void doCheat(GameContainer container, StateBasedGame game, int delta,
				World world) {
		}

		void end(GameContainer container, StateBasedGame game, int delta,
				World world) {
		}

	}

	private class Invincibility extends Cheat {
		@Override
		public void doCheat(GameContainer container, StateBasedGame game,
				int delta, World world) {
			world.getAstronaut().setOxygen(world.getAstronaut().getMaxOxygen());
			wantsToBeUpdated = true;
			isGodmode = true;
		}
	}

	private class FullPower extends Cheat {
		@Override
		public void start(GameContainer container, StateBasedGame game,
				int delta, World world) {
			world.getAstronaut().setOxygen(world.getAstronaut().getMaxOxygen());
			wantsToBeUpdated = false;
		}
	}

	private class MegaJump extends Cheat {

		private float oldJumpSpeed = 0.0f;

		@Override
		public void start(GameContainer container, StateBasedGame game,
				int delta, World world) {
			oldJumpSpeed = world.getAstronaut().getJumpSpeed();
			world.getAstronaut().setJumpSpeed(600.0f);
			wantsToBeUpdated = true;
		}

		@Override
		public void end(GameContainer container, StateBasedGame game,
				int delta, World world) {
			world.getAstronaut().setJumpSpeed(oldJumpSpeed);
		}
	}

	private void addStandardCheats() {
		cheats.put("iddqd", new Invincibility());
		cheats.put("god", new Invincibility());
		cheats.put("idkfa", new FullPower());
		cheats.put("jumpjump", new MegaJump());
		cheats.put("flash", new SpeedMode());
	}
	
	private class SpeedMode extends Cheat {
		
		private float oldSpeed;
		@Override
		public void start(GameContainer container, StateBasedGame game,
				int delta, World world) {
			oldSpeed = world.getAstronaut().getSpeed();
			world.getAstronaut().setSpeed(500.0f);
			wantsToBeUpdated = true;
		}

		@Override
		public void end(GameContainer container, StateBasedGame game,
				int delta, World world) {
			world.getAstronaut().setSpeed(oldSpeed);
		}
	}
}
