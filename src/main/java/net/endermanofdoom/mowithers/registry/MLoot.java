package net.endermanofdoom.mowithers.registry;

import net.endermanofdoom.mowithers.MoWithers;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class MLoot
{
	public static final ResourceLocation ENTITIES_WANE = new ResourceLocation(MoWithers.MODID, "wane");

	public static void registerLoot()
	{
		LootTableList.register(ENTITIES_WANE);
	}
}
