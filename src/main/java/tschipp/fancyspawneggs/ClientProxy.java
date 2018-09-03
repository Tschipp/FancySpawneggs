package tschipp.fancyspawneggs;

import net.minecraft.init.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import tschipp.fancyspawneggs.client.render.event.ClientEvents;

public class ClientProxy extends CommonProxy
{
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);
		MinecraftForge.EVENT_BUS.register(new ClientEvents());
	}

	public void init(FMLInitializationEvent event)
	{
		super.init(event);

	}

	public void postInit(FMLPostInitializationEvent event)
	{
		super.postInit(event);
	}
}
