/*********************************************************
 * opsu!variant -- Copyright 2019 Lenidevelaire team     *
 * Licensed under GPL, see LICENSE file for more details *
 *********************************************************/
package lc.lenidevelaire.aria.math.curves;

import itdelatrisu.opsu.beatmap.HitObject;
import itdelatrisu.opsu.objects.curves.Curve;
import itdelatrisu.opsu.objects.curves.Vec2f;

/**
 * A curve that represents only the slider's starting point
 */
public class NoCurve extends Curve {

	public NoCurve(HitObject hitObject, boolean scaled) {
		super(hitObject, scaled);
		this.curve = new Vec2f[] { new Vec2f(x, y) };
	}

	@Override
	public Vec2f pointAt(float t) {
		return curve[0].cpy();
	}

	@Override
	public float getEndAngle() {
		return 0;
	}

	@Override
	public float getStartAngle() {
		return 0;
	}

}
