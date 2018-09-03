package tschipp.fancyspawneggs.client.render.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tschipp.fancyspawneggs.FancySpawneggs;
import tschipp.fancyspawneggs.client.model.FancyEggModel;
import tschipp.fancyspawneggs.client.render.RenderFancyEgg;
import tschipp.fancyspawneggs.common.config.SpawneggConfig;

public class ClientEvents
{
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onModelBake(ModelBakeEvent event)
	{
		IBakedModel model = event.getModelRegistry().getObject(new ModelResourceLocation(FancySpawneggs.MODID + ":fancy_egg", "inventory"));
		if (model != null)
		{
			FancyEggModel newModel = new FancyEggModel(model);
			event.getModelRegistry().putObject(new ModelResourceLocation(FancySpawneggs.MODID + ":fancy_egg", "inventory"), newModel);
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onModelRegistryReady(ModelRegistryEvent event)
	{
		ModelLoader.setCustomModelResourceLocation(Items.SPAWN_EGG, 0, new ModelResourceLocation(FancySpawneggs.MODID + ":fancy_egg", "inventory"));
		Items.SPAWN_EGG.setTileEntityItemStackRenderer(new RenderFancyEgg());
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onTick(TickEvent event)
	{
		if (event.side == Side.CLIENT)
		{
			RenderFancyEgg.renderTimer += 0.0005f;
			if (RenderFancyEgg.renderTimer > 1f)
				RenderFancyEgg.renderTimer = 0;
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onPlayerEnterWorld(PlayerEvent.PlayerLoggedInEvent event)
	{
		EntityPlayer player = event.player;

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
