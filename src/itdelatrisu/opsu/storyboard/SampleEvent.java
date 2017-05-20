package itdelatrisu.opsu.storyboard;

public class SampleEvent extends StoryboardEvent {

	public final String filepath;
	public final int volume;
	
	public SampleEvent(String path, int start){
		this(path, start, 100, StoryboardEvent.LAYER_BACKGROUND);
	}
	
	public SampleEvent(String path, int start, int volume, String layer){
		super(layer, StoryboardEvent.TYPE_SAMPLE, start, start);
		this.filepath = path;
		this.volume = volume;
	}
	
	public String toString(){
		return this.type + "," + this.startTime + "," + this.layer + ",\"" + this.filepath + "\"," + this.volume;
	}
}
