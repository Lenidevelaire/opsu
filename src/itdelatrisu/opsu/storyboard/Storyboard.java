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

import java.util.ArrayList;
import java.util.List;

public class Storyboard {
	
	// Official storyboard specifications
	// https://osu.ppy.sh/forum/viewtopic.php?p=12468
	
	// osu!wiki page regarding storyboards
	// https://osu.ppy.sh/wiki/Storyboard_Scripting
	
	// Codebase
	// https://github.com/ExCellRaD/osuElements
	
	public final List<SpriteEvent> backgroundEvents,
		foregroundEvents, failEvents, passEvents;
	
	public final ArrayMap<String, String> variables;
	
	Storyboard(){
		this.backgroundEvents = new ArrayList<>();
		this.foregroundEvents = new ArrayList<>();
		this.failEvents = new ArrayList<>();
		this.passEvents = new ArrayList<>();
		this.variables = new ArrayMap<>();
	}
}
