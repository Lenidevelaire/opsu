package itdelatrisu.opsu.storyboard;

public class ParameterEvent extends TransformationEvent {
	
	public final String parameter;
	
	public ParameterEvent(int start, int end, String param){
		super(StoryboardEvent.PARAMETER, StoryboardEvent.EASING_NONE, start, end, null);
		this.parameter = param;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(transform + ",");
		sb.append(easing + ",");
		sb.append(startTime + ",");
		if(duration > 0) sb.append(endTime);
		sb.append(",");
		sb.append(parameter);
		return sb.toString();
	}

}
