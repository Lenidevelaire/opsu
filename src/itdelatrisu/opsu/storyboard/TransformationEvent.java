package itdelatrisu.opsu.storyboard;

import static itdelatrisu.opsu.storyboard.StoryboardEvent.*;

/**
 * Represents a transform event in a storyboard
 * 
 * @author ExCellRaD (C-Sharp)
 */
public class TransformationEvent implements Comparable<TransformationEvent> {

	/**
	 * Transformation, can be any of the following:
	 * <ul>
	 * <li>{@link #FADE}</li>
	 * <li>{@link #MOVE}</li>
	 * <li>{@link #MOVE_X}</li>
	 * <li>{@link #MOVE_Y}</li>
	 * <li>{@link #SCALE}</li>
	 * <li>{@link #VECTOR_SCALE}</li>
	 * <li>{@link #ROTATE}</li>
	 * <li>{@link #COLOR}</li>
	 * <li>{@link #PARAMETER}</li>
	 * <li>{@link #LOOP}</li>
	 * <li>{@link #TRIGGER}</li>
	 * </ul>
	 */
	public final String transform;

	/**
	 * Easing, can be any of the following
	 * <ul>
	 * <li>{@link #EASING_NONE}</li>
	 * <li>{@link #EASING_IN}</li>
	 * <li>{@link #EASING_OUT}</li>
	 * </ul>
	 */
	public final int easing;

	/** Timing values */
	public int startTime;

	public int endTime;

	public final int duration;
	/** Object values */
	public final float[] startValues, endValues;

	public float[] valuesAt(float time) {
		int t = (int) (time / duration);
		float[] result = new float[startValues.length];
		for (int i = 0; i < startValues.length; i++) {
			result[i] = lerp(t, startValues[i], endValues[i]);
		}
		return result;
	}

	protected TransformationEvent(String transform, int easing, int start, int end, float[] startTimes) {
		this(transform, easing, start, end, startTimes, startTimes);
	}

	protected TransformationEvent(String transform, int easing, int start, int end, float[] startTimes,
			float[] endTimes) {
		this.transform = transform;
		this.easing = easing;
		this.startTime = start;
		this.endTime = end;
		this.startValues = startTimes;
		this.endValues = endTimes;
		this.duration = end - start;
	}

	@Override
	public int compareTo(TransformationEvent that) {
		return (this.startTime - that.startTime) + (this.endTime - that.endTime);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(transform).append(",");
		sb.append(easing).append(",");
		sb.append(startTime).append(",");
		if (duration > 0)
			sb.append(endTime);

		boolean same = true;

		float[] startVals = startValues, endVals = endValues;

		for (int i = 0; i < startValues.length; i++) {
			if (transform.equals(COLOR)) {
				startVals[i] = Math.max(0, Math.min(255, (int) startVals[i]));
				endVals[i] = Math.max(0, Math.min(255, (int) endVals[i]));
			} else if (transform.equals(FADE)) {
				startVals[i] = Math.max(0, Math.min(1, startVals[i]));
				endVals[i] = Math.max(0, Math.min(1, endVals[i]));
			}

			sb.append(",").append(startVals[i]);
			if (startVals[i] != endVals[i])
				same = false;
		}

		if (same)
			return sb.toString();

		for (float b : endVals) {
			sb.append(",").append(b);
		}

		return sb.toString();
	}

	public static TransformationEvent[] parse(String s) {
		String[] parts = s.trim().split(",");

		String type = parts[0];
		int easing = Integer.parseInt(parts[1]);
		int start = Integer.parseInt(parts[2]);
		int end;
		try {
			end = Integer.parseInt(parts[3]);
		} catch (Exception e) {
			end = start;
		}

		if (type.equals(PARAMETER)) {
			return new TransformationEvent[] { new ParameterEvent(start, end, parts[4]) };
		}

		// Java does not support "array skipping"
		// Do this "the hard way"
		String[] values = new String[parts.length - 4];
		System.arraycopy(parts, 4, values, 0, values.length);

		int valCount = (type == COLOR ? 3 : (type == MOVE || type == VECTOR_SCALE ? 2 : 1));

		// Java 7 does not support lambda functions (although 8 does!)
		// Do this "the hard way"
		float[] sv = new float[valCount];
		for (int i = 0; i < sv.length; i++) {
			sv[i] = Float.parseFloat(values[i]);
		}

		int count = values.length / valCount;

		if (count == 1) {
			return new TransformationEvent[] { new TransformationEvent(type, easing, start, end, sv) };
		}

		TransformationEvent[] res = new TransformationEvent[count--];
		for (int i = 0; i < count; i++) {
			// Java 7 does not support lambda functions (although 8 does!)
			// Do this "the hard way"
			float[] ev = new float[((i + 2) * valCount)];
			for (int j = ((i + 1) * valCount); j < ev.length; j++) {
				ev[j] = Float.parseFloat(values[j]);
			}
			res[i] = new TransformationEvent(type, easing, start, end, sv, ev);
			sv = ev;
		}
		return res;
	}

	/** lerp function, since {@link Math} does not have it */
	private static float lerp(float slide, float low, float high) {
		return low + (high - low) * slide;
	}

	/**
	 * Represents a parameter in a transformation event
	 */
	public static class ParameterEvent extends TransformationEvent {
		public final String parameterType;

		private ParameterEvent(int start, int end, String param) {
			super(PARAMETER, EASING_NONE, start, end, null);
			this.parameterType = param;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(transform + ",");
			sb.append(easing + ",");
			sb.append(startTime + ",");
			if (duration > 0)
				sb.append(endTime);
			sb.append(",");
			sb.append(parameterType);
			return sb.toString();
		}
	}
}
