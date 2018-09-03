package tschipp.fancyspawneggs;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import tschipp.fancyspawneggs.common.event.CommonEvents;
import tschipp.fancyspawneggs.common.helper.ListHandler;

public class CommonProxy
{
	public void preInit(FMLPreInitializationEvent event)
	{	
		MinecraftForge.EVENT_BUS.register(new CommonEvents());
	}

	public void init(FMLInitializationEvent event)
	{
		ListHandler.initLists();
	}

	public void postInit(FMLPostInitializationEvent event)
	{

	}
}
