package itdelatrisu.opsu.storyboard;

import java.util.List;

/**
 * A storyboard event or object that has transform-related actions
 * 
 * @author ExCellRaD (base)
 * @author Lyonlancer5 (changes to impl)
 */
public interface ITransformable {
	
	/** 
	 * Adds all the given transformation events to this object
	 * @param transforms The transformations to apply to this object
	 */
	void addTransform(TransformationEvent... transforms);
	
	/** 
	 * Removes all the given transfomation events from this object
	 * @param transforms The transformations to remove from this object
	 */
	void removeTransform(TransformationEvent... transforms);
	
	/**
	 * Gets the list of all transformation events for this object
	 * @return A <i>modifiable</i> list of all transforms
	 */
	List<TransformationEvent> getTransformations();
	
}
