
package de.fhtrier.gdw.ss2013.input;

import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.util.Log;

import de.fhtrier.gdw.ss2013.assetloader.AssetLoader;
import de.fhtrier.gdw.ss2013.assetloader.infos.KeyInfo;
import de.fhtrier.gdw.ss2013.input.InputManager.ACTION;

/*
 * Team Input
 * Dennis, Valentin
 * 
 */

public class Keyboard extends InputDevice {

	public Keyboard (GameContainer gc) {
		super(gc);
		loadKeymapping("keyboard");
	}

	@Override
	public void update () {
		super.update();
	}



}
