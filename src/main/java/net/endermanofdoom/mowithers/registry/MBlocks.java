package net.endermanofdoom.mowithers.registry;

import net.endermanofdoom.mac.registry.AbstractBlockRegistry;
import net.endermanofdoom.mac.registry.AbstractItemRegistry;
import net.endermanofdoom.mowithers.MoWithers;
import net.endermanofdoom.mowithers.blocks.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;

public class MBlocks extends AbstractBlockRegistry
{
	public static final MBlocks INSTANCE = new MBlocks(MoWithers.MODID, MItems.INSTANCE);
	public static final Block ATROPHIC_ORE = new BlockWitherOre(MapColor.STONE);
	public static final Block ATROPHIC_ORE_NETHER = new BlockWitherOre(MapColor.NETHERRACK);
	public static final Block ATROPHIC_BLOCK = new BlockWitherBlock();
	public static final Block NETHER_STAR_BLOCK = new BlockStarBlock(INSTANCE);

	private MBlocks(String modid, AbstractItemRegistry itemRegistry)
	{
		super(modid, itemRegistry);
	}
	
	@Override
	public void init() {}
	
	@Override
	public void register()
	{
		addBlock("atrophic_ore", ATROPHIC_ORE, MoWithers.MO_TAB);
		addBlock("atrophic_ore_nether", ATROPHIC_ORE_NETHER, MoWithers.MO_TAB);
		addBlock("atrophic_block", ATROPHIC_BLOCK, MoWithers.MO_TAB);
	}
}
