package tschipp.fancyspawneggs.common.event;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import tschipp.fancyspawneggs.FancySpawneggs;
import tschipp.fancyspawneggs.client.render.RenderFancyEgg;
import tschipp.fancyspawneggs.common.config.SpawneggConfig;
import tschipp.fancyspawneggs.common.helper.ListHandler;

public class CommonEvents
{

	@SubscribeEvent
	public void onPlayerEnterWorld(EntityJoinWorldEvent event)
	{
		if (event.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.getEntity();

			if (player.world.isRemote)
			{
				EntityPlayer sp = Minecraft.getMinecraft().player;
				World world = player.world;
				if (world != null && player != null && sp != null && player.getUUID(player.getGameProfile()).equals(sp.getUUID(sp.getGameProfile())))
				{
					RenderFancyEgg.storedEntities.clear();
					for (String s : SpawneggConfig.StaticEntities.staticEntities)
					{
						Entity entity = EntityList.createEntityByIDFromName(new ResourceLocation(s), world);
						if (entity != null)
						{
							RenderFancyEgg.storedEntities.put(s, entity);
						}
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onConfigChange(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if(event.getModID().equals(FancySpawneggs.MODID))
		{
			ListHandler.initLists();
		}
	}

}
