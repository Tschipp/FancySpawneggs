package tschipp.fancyspawneggs.common.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;

public class Configs {
	
	public static class Settings
	{
		@Comment("If entities rotate in the GUI")
		public boolean rotateInGui = false;
		
		@Comment("If the whitelist should be used instead of the blacklist")
		public boolean useWhitelist = false;
		
	}
	
	public static class Whitelist
	{
		@Comment("Entites that have a fancy Spawnegg (useWhitelist must be enabled!)")
		public String[] allowedEntities=new String[]
				{
				};
	}
	
	public static class Blacklist
	{
		@Comment("Entities that don't have a fancy Spawnegg")
    	public String[] forbiddenEntities = new String[]
    			{
    			};
		
	}

	public static class StaticEntities
	{
		@Comment("Entities that are forced to only render one variant.\nIf an Entity appears to be flickering, add it to this list.")
    	public String[] staticEntities = new String[]
    			{
    					"minecraft:guardian",
    					"minecraft:elder_guardian",
    					"twilightforest:bunny",
    					"twilightforest:tiny_bird"
    			};
	}
	
}
