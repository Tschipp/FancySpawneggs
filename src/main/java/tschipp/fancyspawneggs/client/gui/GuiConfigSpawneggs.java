package tschipp.fancyspawneggs.client.gui;

import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import tschipp.fancyspawneggs.FancySpawneggs;
import tschipp.fancyspawneggs.common.config.SpawneggConfig;

public class GuiConfigSpawneggs extends GuiConfig
{
	private static final String LANG_PREFIX = FancySpawneggs.MODID + ".category.";

	public GuiConfigSpawneggs(GuiScreen parent)
	{
		super(parent, getConfigElements(), FancySpawneggs.MODID, false, false, "Fancy Spawn Egg Configuration");
	}

	private static List<IConfigElement> getConfigElements()
	{

		final Configuration configuration = SpawneggConfig.EventHandler.getConfiguration();

		final ConfigCategory topLevelCategory = configuration.getCategory(Configuration.CATEGORY_GENERAL);
		topLevelCategory.getChildren().forEach(configCategory -> configCategory.setLanguageKey(GuiConfigSpawneggs.LANG_PREFIX + configCategory.getName()));

		return new ConfigElement(topLevelCategory).getChildElements();
	}

	@Override
	public void initGui()
	{
		super.initGui();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	protected void actionPerformed(GuiButton button)
	{
		super.actionPerformed(button);
	}
}
