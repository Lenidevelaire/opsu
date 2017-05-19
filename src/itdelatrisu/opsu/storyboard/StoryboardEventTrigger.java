package itdelatrisu.opsu.storyboard;

import itdelatrisu.opsu.audio.HitSound;
import itdelatrisu.opsu.audio.MultiClip;
import itdelatrisu.opsu.audio.SoundController;

/**
 * Base class for storyboard triggers
 * 
 * @author ExCellRaD (base)
 * @author Lyonlancer5 (C# -> Java)
 */
public abstract class StoryboardEventTrigger {
	
	public abstract String getTriggerType();
	
	public static StoryboardEventTrigger parse(String part){
		
		//discern trigger type
		String f = part.trim();
		if(f.equals(StoryboardEvent.TRIGGER_FAIL)){
			return FAILING;
		}else if(f.equals(StoryboardEvent.TRIGGER_PASS)){
			return PASSING;
		}else if(f.equals(StoryboardEvent.TRIGGER_HITSOUND)){
			//TODO fix the beatmap-loaded hitsound issue first!
		}
		
		//never mind, pass a null first
		return null;
	}
	
	public static final StoryboardEventTrigger FAILING = new StoryboardEventTrigger(){
		public String getTriggerType() {
			return StoryboardEvent.TRIGGER_FAIL;
		}
	};
	
	public static final StoryboardEventTrigger PASSING = new StoryboardEventTrigger() {
		public String getTriggerType() {
			return StoryboardEvent.TRIGGER_PASS;
		}
	};
	
	public static class HitSoundTrigger extends StoryboardEventTrigger implements SoundController.SoundComponent{
		public HitSound.SampleSet sampleSet, additionSampleSet;
		public HitSound soundType;
		//nani kore Custom???
		public boolean triggerOnSampleSet, triggerOnAdditions, triggerOnSoundType, triggerOnCustom;
		
		public String getTriggerType() {
			return StoryboardEvent.TRIGGER_HITSOUND;
		}
		
		private HitSoundTrigger(HitSound sound){
			soundType = sound;
		}

		public MultiClip getClip() {
			//right now...
			return soundType.getClip(sampleSet);
		}
	}
}
