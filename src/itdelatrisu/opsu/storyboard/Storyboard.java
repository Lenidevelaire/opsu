/*
 * opsu! - an open-source osu! client
 * Copyright (C) 2014-2017 Jeffrey Han
 *
 * opsu! is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * opsu! is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with opsu!.  If not, see <http://www.gnu.org/licenses/>.
 */

package itdelatrisu.opsu.storyboard;

import itdelatrisu.opsu.ErrorHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.util.DefaultLogSystem;
import org.newdawn.slick.util.Log;

public class Storyboard {

	// Official storyboard specifications
	// https://osu.ppy.sh/forum/viewtopic.php?p=12468

	// osu!wiki page regarding storyboards
	// https://osu.ppy.sh/wiki/Storyboard_Scripting

	// Codebase
	// https://github.com/ExCellRaD/osuElements

	public final Map<Integer, List<SpriteEvent>> starts, ends;
	
	/**
	 * Lists of events that form this storyboard
	 */
	public final List<SpriteEvent> backgroundEvents, foregroundEvents, failEvents, passEvents, allEvents;

	/**
	 * Table of variables used by the storyboard
	 */
	//public final Map<String, String> variables;

	public Storyboard() {
		this.backgroundEvents = new ArrayList<>();
		this.foregroundEvents = new ArrayList<>();
		this.failEvents = new ArrayList<>();
		this.passEvents = new ArrayList<>();
		this.allEvents = new ArrayList<>();
		//this.variables = new IdentityHashMap<>();
		this.starts = new HashMap<>();
		this.ends = new HashMap<>();
	}

	public void addSpriteEvent(SpriteEvent sprite) {
		if (sprite.layer.equals(StoryboardEvent.LAYER_BACKGROUND)) {
			this.backgroundEvents.add(sprite);
		} else if (sprite.layer.equals(StoryboardEvent.LAYER_FAIL)) {
			this.failEvents.add(sprite);
		} else if (sprite.layer.equals(StoryboardEvent.LAYER_PASS)) {
			this.passEvents.add(sprite);
		} else if (sprite.layer.equals(StoryboardEvent.LAYER_FOREGROUND)) {
			this.foregroundEvents.add(sprite);
		} else {
			throw new IllegalArgumentException("Sprite event layer is unknown: " + sprite.layer);
		}

		this.allEvents.add(sprite);
		
		int i = sprite.startTime, j = sprite.endTime;
		if(!starts.containsKey(Integer.valueOf(i))) starts.put(i, new ArrayList<SpriteEvent>());
		if(!ends.containsKey(Integer.valueOf(j))) ends.put(j, new ArrayList<SpriteEvent>());
		starts.get(i).add(sprite);
		ends.get(j).add(sprite);
	}

	// TODO
	/**  */
	public static Storyboard parseStoryboard(File file) {
		try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));) {

			// put the readers here
			Storyboard ret = new Storyboard();

			StoryboardEvent currentEvent = null;
			TransformationEvent currentTransform = null;

			String line;
			while ((line = in.readLine()) != null) {
				if (line.isEmpty() || line.startsWith("//"))
					continue;

				//
				if (line.startsWith("\u0020")) {
					// Transform event
					if (line.startsWith("\u0020\u0020")) {
						TransformationEvent tt;
						if ((currentTransform = TransformationEvent.parse(line)[0]) != null) {

						}
					}
					//
					else {
						if (currentEvent instanceof ITransformable) {
							((ITransformable) currentEvent).addTransform(TransformationEvent.parse(line));
						}
					}
				} else {
					currentEvent = StoryboardEvent.parse(line);
					if (currentEvent instanceof SpriteEvent) {
						ret.addSpriteEvent((SpriteEvent) currentEvent);
					} else {
						// TODO parse other events "correctly"
					}
				}

			}

			in.close();
			return ret;
		} catch (Exception e) {
			ErrorHandler.error(String.format("Failed to parse file '%s'.", file.getAbsolutePath()), e, false);
		}

		return null;
	}

	public static void main(String[] args) throws IOException {
		DefaultLogSystem.out = new PrintStream(new File(".opsu.log"));
		Log.setVerbose(true);
		File f = new File("Siestail - Magic Lip Service (Hollow Wings).osb");
		Storyboard sb = parseStoryboard(f);
		if (sb != null) {
			Log.info(sb.toString());
			for (SpriteEvent se : sb.allEvents) {
				Log.info(se.toString());
				for (TransformationEvent te : se.getTransformations()) {
					Log.info("\u0020" + te.toString());
				}
			}
		} else {
			System.err.println("FNF");
		}
	}

}
