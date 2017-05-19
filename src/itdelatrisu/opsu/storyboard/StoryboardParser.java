package itdelatrisu.opsu.storyboard;

import itdelatrisu.opsu.ErrorHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class StoryboardParser {

	public static Storyboard parse(File file){
		
		//put the readers here
		Storyboard ret = new Storyboard();
		

		try (
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		) {
			
			StoryboardEvent currentEvent = null;
			TransformationEvent currentTransform = null;
			
			String line;
			while((line = in.readLine()) != null){
				if(line.isEmpty() || line.startsWith("//")) continue;
				
				//
				if(line.startsWith("\u0020")){
					
					//Transform event
					if(line.startsWith("\u0020\u0020")){
						TransformationEvent tt;
						
					}
				}
				// 
				else
				{
					if((currentTransform = TransformationEvent.parse(line)[0]) != null) {
						
					}
				}
			}
			
			in.close();
		} catch (IOException e) {
			ErrorHandler.error(String.format("Failed to read file '%s'.", file.getAbsolutePath()), e, false);
		} 
		
		
		return null;
	}
	
}
