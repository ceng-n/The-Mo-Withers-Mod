package net.endermanofdoom.mowithers.registry;

import net.endermanofdoom.mowithers.MoWithers;
import net.endermanofdoom.mowithers.blocks.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.GameData;

public class MBlocks 
{
	public static final Block ATROPHIC_ORE = new BlockWitherOre(MapColor.STONE);
	public static final Block ATROPHIC_ORE_NETHER = new BlockWitherOre(MapColor.NETHERRACK);
	public static final Block ATROPHIC_BLOCK = new BlockWitherBlock();
	public static final Block NETHER_STAR_BLOCK = new BlockStarBlock();
	
	public static void registerBlocks()
	{
		registerBlock(ATROPHIC_ORE, "atrophic_ore");
		registerBlock(ATROPHIC_ORE_NETHER, "atrophic_ore_nether");
		registerBlock(ATROPHIC_BLOCK, "atrophic_block");
		registerBlock(NETHER_STAR_BLOCK, "nether_star_block");
	}
	
	private static void registerBlock(Block block, String registername)
	{
		block.setUnlocalizedName(registername);
		GameData.register_impl(block.setRegistryName(registername));
		GameData.register_impl(new ItemBlock(block).setRegistryName(registername));
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerRenders()
	{
		renderBlock(ATROPHIC_ORE);
		renderBlock(ATROPHIC_ORE_NETHER);
		renderBlock(ATROPHIC_BLOCK);
		renderBlock(NETHER_STAR_BLOCK);
	}
	
	@SideOnly(Side.CLIENT)
	private static void renderBlock(Block block)
	{
		renderBlock(block, 0);
	}
	
	@SideOnly(Side.CLIENT)
	private static void renderBlock(Block block, int meta)
	{
		Item item = Item.getItemFromBlock(block);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, new ModelResourceLocation(MoWithers.MODID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
