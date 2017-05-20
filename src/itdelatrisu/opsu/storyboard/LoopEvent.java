package itdelatrisu.opsu.storyboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a storyboard loop event
 * 
 * @author ExCellRaD (base)
 * @author Lyonlancer5 (C# -> Java)
 */
public class LoopEvent implements ITransformable, Comparable<LoopEvent> {

	/** Contains all the transformation events for this object */
	private final List<TransformationEvent> events;

	public final String transform = StoryboardEvent.LOOP;
	public int startTime, loopCount, endTime;

	private int cachedDuration;
	private int[] minmax;
	private boolean resetCache;

	public LoopEvent(int start) {
		this(start, 1);
	}

	public LoopEvent(int start, int loopcount) {
		startTime = start;
		loopCount = loopcount;

		events = new ArrayList<>();
	}

	public int getLoopDuration() {
		if (events.isEmpty())
			return 0;

		if (resetCache) {
			int[] m = getMinMax();
			cachedDuration = m[1] - m[0];
			resetCache = false;
		}

		return cachedDuration;
	}

	public void addTransform(TransformationEvent... transforms) {
		for (TransformationEvent t : transforms) {
			events.add(t);
		}
		Collections.sort(events);
		resetCache = true;
	}

	public void removeTransform(TransformationEvent... transforms) {
		for (TransformationEvent t : transforms) {
			events.remove(t);
		}
		Collections.sort(events);
		resetCache = true;
	}

	public void optimize() {
		int first = getMinMax()[0];
		if (startTime == first)
			return;

		int diff = first - startTime;
		for (TransformationEvent t : events) {
			t.startTime -= diff;
			t.endTime -= diff;
		}
	}

	/**
	 * Gets the earliest start time and the latest end time and puts them in a
	 * two-item array
	 */
	private int[] getMinMax() {
		if (resetCache || minmax == null) {
			int max = Integer.MIN_VALUE;
			int min = Integer.MAX_VALUE;

			for (TransformationEvent te : events) {
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

	public static LoopEvent parse(String line) {
		String[] parts = line.split(",");
		int p1 = -1, p2 = -1;

		for (String p : parts) {
			if (p.isEmpty() || p.trim().equalsIgnoreCase("L"))
				continue;
			if (p1 == -1)
				p1 = Integer.parseInt(p.trim());
			if (p2 == -1)
				p2 = Integer.parseInt(p.trim());
		}

		return new LoopEvent(p1, p2);
	}

	@Override
	public String toString() {
		return transform + "," + startTime + "," + loopCount;
	}

	@Override
	public List<TransformationEvent> getTransformations() {
		return events;
	}

	@Override
	public int compareTo(LoopEvent that) {
		return (this.startTime - that.startTime) + (this.endTime - that.endTime);
	}

}
