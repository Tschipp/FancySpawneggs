package tschipp.fancyspawneggs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@EventBusSubscriber
@Mod(modid = FancySpawneggs.MODID, name = FancySpawneggs.NAME, version = FancySpawneggs.VERSION, dependencies = FancySpawneggs.DEPENDENCIES, acceptedMinecraftVersions = FancySpawneggs.ACCEPTED_VERSIONS, guiFactory = "tschipp.fancyspawneggs.client.gui.GuiFactorySpawneggs")
public class FancySpawneggs
{

	@SidedProxy(clientSide = "tschipp.fancyspawneggs.ClientProxy", serverSide = "tschipp.fancyspawneggs.CommonProxy")
	public static CommonProxy proxy;

	// Instance
	@Instance(FancySpawneggs.MODID)
	public static FancySpawneggs instance;

	public static final String MODID = "fancyspawneggs";
	public static final String VERSION = "1.0";
	public static final String NAME = "Fancy Spawn Eggs";
	public static final String ACCEPTED_VERSIONS = "[1.12.2,1.13)";
	public static final String DEPENDENCIES = "required-after:forge@[14.23.2.2638,)";
	public static final Logger LOGGER = LogManager.getFormatterLogger(MODID.toUpperCase());

	public static SimpleNetworkWrapper network;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		FancySpawneggs.proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		FancySpawneggs.proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		FancySpawneggs.proxy.postInit(event);
	}
}
