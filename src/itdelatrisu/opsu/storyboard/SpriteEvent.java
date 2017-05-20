package itdelatrisu.opsu.storyboard;

import itdelatrisu.opsu.objects.curves.Vec2f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpriteEvent extends StoryboardEvent implements ITransformable {

	public static final String DEFAULT_FILE_EXT = "jpg";

	public final String origin;
	public final Vec2f startPos;

	public final List<LoopEvent> loops;
	public final List<TriggerEvent> triggers;
	public final String filepath;

	private final List<TransformationEvent> transforms;
	private int[] minmax;
	private boolean resetCache = false;

	public SpriteEvent(String file, String layer, String origin, float x, float y) {
		super(layer, StoryboardEvent.TYPE_SPRITE, Integer.MIN_VALUE, Integer.MAX_VALUE);

		// x = 320, y = 240, layer= BG, origin = center

		this.origin = origin;
		this.filepath = file;
		this.startPos = new Vec2f(x, y);
		this.loops = new ArrayList<LoopEvent>();
		this.triggers = new ArrayList<TriggerEvent>();
		this.transforms = new ArrayList<TransformationEvent>();
	}

	public void addLoop(LoopEvent l) {
		loops.add(l);
		Collections.sort(loops);
	}

	public void addTrigger(TriggerEvent t) {
		triggers.add(t);
		Collections.sort(triggers);
	}

	/**
	 * Gets the earliest start time and the latest end time and puts them in a
	 * two-item array
	 */
	private int[] getMinMax() {
		if (resetCache || minmax == null) {
			int max = Integer.MIN_VALUE;
			int min = Integer.MAX_VALUE;

			for (TransformationEvent te : transforms) {
				if (te.startTime < min)
					min = te.startTime;
				if (te.endTime > max)
					max = te.endTime;
			}

			minmax = new int[] { min, max };
			resetCache = false;
		}

		return minmax;
	}

	@Override
	public void addTransform(TransformationEvent[] transforms) {
		for (TransformationEvent t : transforms) {
			if (t != null)
				this.transforms.add(t);
		}

		if (!this.transforms.isEmpty()) {
			Collections.sort(this.transforms);
		}

		this.startTime = getMinMax()[0];
		this.endTime = getMinMax()[1];
	}

	@Override
	public void removeTransform(TransformationEvent[] transforms) {
		for (TransformationEvent t : transforms) {
			this.transforms.remove(t);
		}
		Collections.sort(this.transforms);
	}

	@Override
	public List<TransformationEvent> getTransformations() {
		return transforms;
	}

	@Override
	public String toString() {
		String f1 = (filepath.contains(".") ? filepath + "." + DEFAULT_FILE_EXT : filepath);
		StringBuilder sb = new StringBuilder();
		sb.append(type + ",");
		sb.append(layer + ",");
		sb.append(origin + ",");
		sb.append("\"" + f1 + "\",");
		sb.append(startPos.x).append(",");
		sb.append(startPos.y);
		return sb.toString();

	}

}
