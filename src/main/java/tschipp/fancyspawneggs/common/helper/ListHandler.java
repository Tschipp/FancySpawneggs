package tschipp.fancyspawneggs.common.helper;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import tschipp.fancyspawneggs.common.config.SpawneggConfig;

public class ListHandler
{
	public static List<String> FORBIDDEN_ENTITIES;
	public static List<String> ALLOWED_ENTITIES;

	public static boolean isForbidden(ResourceLocation entity)
	{
		String name = entity.toString();
		boolean contains = FORBIDDEN_ENTITIES.contains(name);
		return contains;
	}

	public static boolean isAllowed(ResourceLocation entity)
	{
		String name = entity.toString();
		boolean contains = ALLOWED_ENTITIES.contains(name);
		return contains;
	}

	public static void initLists()
	{
		String[] forbiddenEntity = SpawneggConfig.Blacklist.forbiddenEntities;
		FORBIDDEN_ENTITIES = new ArrayList<String>();

		for (int i = 0; i < forbiddenEntity.length; i++)
		{
			if (forbiddenEntity[i].contains("*"))
			{
				String modid = forbiddenEntity[i].replace("*", "");
				for (int k = 0; k < ForgeRegistries.ENTITIES.getKeys().size(); k++)
				{
					if (ForgeRegistries.ENTITIES.getKeys().toArray()[k].toString().contains(modid))
					{
						FORBIDDEN_ENTITIES.add(ForgeRegistries.ENTITIES.getKeys().toArray()[k].toString());
					}
				}
			}
			FORBIDDEN_ENTITIES.add(forbiddenEntity[i]);
		}

		String[] allowedEntities = SpawneggConfig.Whitelist.allowedEntities;
		ALLOWED_ENTITIES = new ArrayList<String>();
		for (int i = 0; i < allowedEntities.length; i++)
		{
			if (allowedEntities[i].contains("*"))
			{
				String modid = allowedEntities[i].replace("*", "");
				for (int k = 0; k < ForgeRegistries.ENTITIES.getKeys().size(); k++)
				{
					if (ForgeRegistries.ENTITIES.getKeys().toArray()[k].toString().contains(modid))
					{
						ALLOWED_ENTITIES.add(ForgeRegistries.ENTITIES.getKeys().toArray()[k].toString());
					}
				}
			}
			ALLOWED_ENTITIES.add(allowedEntities[i]);
		}
	}

}
