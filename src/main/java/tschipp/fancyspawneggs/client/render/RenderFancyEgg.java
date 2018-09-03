package tschipp.fancyspawneggs.client.render;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tschipp.fancyspawneggs.common.config.SpawneggConfig;
import tschipp.fancyspawneggs.common.helper.ListHandler;

public class RenderFancyEgg extends TileEntityItemStackRenderer
{

	public static TransformType transformType;
	public static float renderTimer = 0f;

	public static Map<String, Entity> storedEntities = new HashMap<String, Entity>();

	@Override
	public void renderByItem(ItemStack stack, float partialTicks)
	{
		EntityPlayer player = Minecraft.getMinecraft().player;
		Minecraft mc = Minecraft.getMinecraft();
		World world = player.world;

		if (stack.getItem() instanceof ItemMonsterPlacer)
		{

			ResourceLocation loc = ItemMonsterPlacer.getNamedIdFrom(stack);
			if (loc != null && SpawneggConfig.Settings.useWhitelist ? ListHandler.isAllowed(loc) : !ListHandler.isForbidden(loc))
			{
				Entity entity = null;

				if (storedEntities.containsKey(loc.toString()))
					entity = storedEntities.get(loc.toString());
				else
					entity = EntityList.createEntityByIDFromName(loc, world);

				if (entity != null)
				{
					applyItemEntityDataToEntity(world, player, stack, entity);

					entity.setPosition(-1, -1, -1);

					float pbx = OpenGlHelper.lastBrightnessX;
					float pby = OpenGlHelper.lastBrightnessY;
					OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);

					GlStateManager.pushMatrix();
					RenderHelper.enableStandardItemLighting();

					float height = entity.height;
					float width = entity.width;

					if (entity instanceof EntityAgeable)
					{
						try
						{
							Field ageHeight = ReflectionHelper.findField(EntityAgeable.class, "ageHeight");
							Field ageWidth = ReflectionHelper.findField(EntityAgeable.class, "ageWidth");
							ageHeight.setAccessible(true);
							ageWidth.setAccessible(true);

							height = ageHeight.getFloat(entity);
							width = ageWidth.getFloat(entity);

						}
						catch (Exception e)
						{
						}
					}

					float size = width * height;

					GlStateManager.pushMatrix();

					switch (transformType)
					{
					case GROUND:
						GlStateManager.translate(0.5, 0, 0.5);
						if (height > width)
							GlStateManager.scale(1 / height, 1 / height, 1 / height);
						else if (width > height)
							GlStateManager.scale(1 / width, 1 / width, 1 / width);
						else
							GlStateManager.scale(1 / Math.sqrt(size), 1 / Math.sqrt(size), 1 / Math.sqrt(size));

						if (size < 0.25)
							GlStateManager.scale(0.8, 0.8, 0.8);
						break;
					case FIXED:
						GlStateManager.rotate(90, 0.0f, 1.0f, 0f);
						GlStateManager.translate(-0.5, 0, 0.5);

						if (height > width)
							GlStateManager.scale(0.8 / height, 0.8 / height, 0.8 / height);
						else if (width > height)
							GlStateManager.scale(0.8 / width, 0.8 / width, 0.8 / width);
						else
							GlStateManager.scale(0.8 / Math.sqrt(size), 0.8 / Math.sqrt(size), 0.8 / Math.sqrt(size));

						if (size < 0.25)
							GlStateManager.scale(0.6, 0.6, 0.6);

						break;
					case GUI:
						GlStateManager.translate(0.5, 0.1, 0);
						if (height > width)
							GlStateManager.scale(0.8 / height, 0.8 / height, 0.8 / height);
						else if (width > height)
							GlStateManager.scale(0.8 / width, 0.8 / width, 0.8 / width);
						else
							GlStateManager.scale(0.8 / Math.sqrt(size), 0.8 / Math.sqrt(size), 0.8 / Math.sqrt(size));

						if (size < 0.25)
							GlStateManager.scale(0.6, 0.6, 0.6);

						if (SpawneggConfig.Settings.rotateInGui)
							GlStateManager.rotate(360 * renderTimer, 0, 1f, 0);
						else
						{
							GlStateManager.rotate(20, 0.5f, 1f, 0);
						}
						break;
					case THIRD_PERSON_LEFT_HAND:
					case THIRD_PERSON_RIGHT_HAND:

						GlStateManager.rotate(180, 0, 1f, 0);
						GlStateManager.rotate(-90, 1f, 0, 0);
						GlStateManager.translate(-0.5, 0, 0.2);

						if (height > width)
							GlStateManager.scale(0.8 / height, 0.8 / height, 0.8 / height);
						else if (width > height)
							GlStateManager.scale(0.8 / width, 0.8 / width, 0.8 / width);
						else
							GlStateManager.scale(0.8 / Math.sqrt(size), 0.8 / Math.sqrt(size), 0.8 / Math.sqrt(size));

						if (size < 0.25)
							GlStateManager.scale(0.6, 0.6, 0.6);
						break;
					case FIRST_PERSON_RIGHT_HAND:
						GlStateManager.rotate(-90.0f, 0f, 1f, 0f);
						if (height > width)
							GlStateManager.scale(0.8 / height, 0.8 / height, 0.8 / height);
						else if (width > height)
							GlStateManager.scale(0.8 / width, 0.8 / width, 0.8 / width);
						else
							GlStateManager.scale(0.8 / Math.sqrt(size), 0.8 / Math.sqrt(size), 0.8 / Math.sqrt(size));

						if (size < 0.25)
							GlStateManager.scale(0.6, 0.6, 0.6);
						break;
					case FIRST_PERSON_LEFT_HAND:
						GlStateManager.rotate(90.0f, 0f, 1f, 0f);
						GlStateManager.translate(0, 0, 1);
						if (height > width)
							GlStateManager.scale(0.8 / height, 0.8 / height, 0.8 / height);
						else if (width > height)
							GlStateManager.scale(0.8 / width, 0.8 / width, 0.8 / width);
						else
							GlStateManager.scale(0.8 / Math.sqrt(size), 0.8 / Math.sqrt(size), 0.8 / Math.sqrt(size));

						if (size < 0.25)
							GlStateManager.scale(0.6, 0.6, 0.6);
					default:
						break;
					}

					renderEntityStatic(entity);

					GlStateManager.popMatrix();
					GlStateManager.popMatrix();

					OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);

				}
			}
			else
			{
				RenderItem render = mc.getRenderItem();
				IBakedModel model = render.getItemModelWithOverrides(stack, null, null);
				this.renderModel(model, -1, stack);
			}

		}

	}

	@SideOnly(Side.CLIENT)
	private void renderEntityStatic(Entity entity)
	{
		if (entity.ticksExisted == 0)
		{
			entity.lastTickPosX = entity.posX;
			entity.lastTickPosY = entity.posY;
			entity.lastTickPosZ = entity.posZ;
		}

		float f = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw);
		int i = this.getBrightnessForRender(entity, Minecraft.getMinecraft().player);

		if (entity.isBurning())
		{
			i = 15728880;
		}

		int j = i % 65536;
		int k = i / 65536;

		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j, k);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		if (transformType == TransformType.GUI)
			this.setLightmapDisabled(true);
		else
			this.setLightmapDisabled(false);

		Minecraft.getMinecraft().getRenderManager().renderEntity(entity, 0.0D, 0.0D, 0.0D, f, 0.0F, true);

	}

	@SideOnly(Side.CLIENT)
	private int getBrightnessForRender(Entity entity, EntityPlayer player)
	{
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(MathHelper.floor(player.posX), 0, MathHelper.floor(player.posZ));

		if (entity.world.isBlockLoaded(blockpos$mutableblockpos))
		{
			blockpos$mutableblockpos.setY(MathHelper.floor(player.posY + entity.getEyeHeight()));
			return entity.world.getCombinedLight(blockpos$mutableblockpos, 0);
		}
		else
		{
			return 0;
		}
	}

	@SideOnly(Side.CLIENT)
	private void setLightmapDisabled(boolean disabled)
	{
		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);

		if (disabled)
		{
			GlStateManager.disableTexture2D();
		}
		else
		{
			GlStateManager.enableTexture2D();
		}

		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}

	private void renderModel(IBakedModel model, int color, ItemStack stack)
	{
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.ITEM);

		for (EnumFacing enumfacing : EnumFacing.values())
		{
			this.renderQuads(bufferbuilder, model.getQuads((IBlockState) null, enumfacing, 0L), color, stack);
		}

		this.renderQuads(bufferbuilder, model.getQuads((IBlockState) null, (EnumFacing) null, 0L), color, stack);
		tessellator.draw();
	}

	private void renderQuads(BufferBuilder renderer, List<BakedQuad> quads, int color, ItemStack stack)
	{
		boolean flag = color == -1 && !stack.isEmpty();
		int i = 0;

		for (int j = quads.size(); i < j; ++i)
		{
			BakedQuad bakedquad = quads.get(i);
			int k = color;

			if (flag && bakedquad.hasTintIndex())
			{
				k = Minecraft.getMinecraft().getItemColors().colorMultiplier(stack, bakedquad.getTintIndex());

				if (EntityRenderer.anaglyphEnable)
				{
					k = TextureUtil.anaglyphColor(k);
				}

				k = k | -16777216;
			}

			net.minecraftforge.client.model.pipeline.LightUtil.renderQuadColor(renderer, bakedquad, k);
		}
	}

	private void applyItemEntityDataToEntity(World entityWorld, @Nullable EntityPlayer player, ItemStack stack, @Nullable Entity targetEntity)
	{

		if (targetEntity != null)
		{
			NBTTagCompound nbttagcompound = stack.getTagCompound();

			if (nbttagcompound != null && nbttagcompound.hasKey("EntityTag", 10))
			{
				if (!entityWorld.isRemote && targetEntity.ignoreItemEntityData())
				{
					return;
				}

				NBTTagCompound nbttagcompound1 = targetEntity.writeToNBT(new NBTTagCompound());
				UUID uuid = targetEntity.getUniqueID();
				nbttagcompound1.merge(nbttagcompound.getCompoundTag("EntityTag"));
				targetEntity.setUniqueId(uuid);
				targetEntity.readFromNBT(nbttagcompound1);
			}
		}
	}

}
