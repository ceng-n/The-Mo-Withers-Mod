package net.endermanofdoom.mowithers.registry;

import net.endermanofdoom.mac.registry.AbstractRecipeRegistry;
import net.endermanofdoom.mca.registrey.MCAItems;
import net.endermanofdoom.mowithers.MoWithers;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class MRecipes extends AbstractRecipeRegistry
{
	public static final MRecipes INSTANCE = new MRecipes(MoWithers.MODID);
	
	private MRecipes(String modid)
	{
		super(modid);
	}

	@Override
	public void init() {}

	@Override
	public void register()
	{
		addShapedRecipe(MItems.ATROPHIC_CRYSTAL, "shards_to_crystal", " 0 ,010, 0 ", MItems.ATROPHIC_SHARD, MCAItems.getNugget("dragon_scale"));
		addShapedRecipe(MItems.NECROTIC_STAR, "crystals_to_rottenstar", "202,010,202", MCAItems.getNugget("dragon_scale"), MBlocks.ATROPHIC_BLOCK, Items.NETHER_STAR);
		addShapedRecipe(MItems.ENTROPIC_MATTER_STABLE, "stabilize_matter", "000,010,000", MItems.NECROTIC_STAR, MItems.ENTROPIC_MATTER_UNSTABLE);
		addShapedRecipe(MBlocks.ATROPHIC_BLOCK, "crystal_to_block", "000,000,000", MItems.ATROPHIC_CRYSTAL);
		addShapelessRecipe(MItems.ATROPHIC_CRYSTAL, 9, "block_to_crystal", MBlocks.ATROPHIC_BLOCK);
		addShapedRecipe(MBlocks.NETHER_STAR_BLOCK, "star_to_block", "000,000,000", Items.NETHER_STAR);
		addShapelessRecipe(Items.NETHER_STAR, 9, "block_to_star", MBlocks.NETHER_STAR_BLOCK);
		addShapedRecipe(Items.NETHER_STAR, "shards_to_star", " 0 ,000, 0 ", MItems.NETHER_STAR_SHARD);
		addShapelessRecipe(MItems.NETHER_STAR_SHARD, 5, "star_to_shards", Items.NETHER_STAR);
		addShapedRecipe(MItems.NETHER_STAR_SHARD, "dust_to_shard", "000,000,000", MItems.NETHER_STAR_DUST);
		addShapelessRecipe(MItems.NETHER_STAR_DUST, 9, "shard_to_dust", MItems.NETHER_STAR_SHARD);
		addSmeltingRecipe(MItems.ATROPHIC_SHARD, 0, 2F, MBlocks.ATROPHIC_ORE);
		addSmeltingRecipe(MItems.ATROPHIC_SHARD, 0, 2F, MBlocks.ATROPHIC_ORE_NETHER);

		String material = "atrophic";
		Item item = MItems.ATROPHIC_CRYSTAL;
		
		addShapedRecipe(MItems.ATROPHIC_PICKAXE, material + "_pickaxe", "000, 1 , 1 ", item, Items.BLAZE_ROD);
		addShapedRecipe(MItems.ATROPHIC_AXE, material + "_axe", "00,01, 1", item, Items.BLAZE_ROD);
		addShapedRecipe(MItems.ATROPHIC_SHOVEL, material + "_shovel", "0,1,1", item, Items.BLAZE_ROD);
		addShapedRecipe(MItems.ATROPHIC_HOE, material + "_hoe", "00, 1, 1", item, Items.BLAZE_ROD);
		addShapedRecipe(MItems.ATROPHIC_SWORD, material + "_sword", "0,0,1", item, Items.BLAZE_ROD);
		addShapedRecipe(MItems.ATROPHIC_HELMET, material + "_helmet", "000,0 0", item);
		addShapedRecipe(MItems.ATROPHIC_CHESTPLATE, material + "_chestplate", "0 0,000,000", item);
		addShapedRecipe(MItems.ATROPHIC_LEGGINGS, material + "_leggings", "000,0 0,0 0", item);
		addShapedRecipe(MItems.ATROPHIC_BOOTS, material + "_boots", "0 0,0 0", item);

		material = "necrotic";
		item = MItems.NECROTIC_STAR;
		
		addShapedRecipe(MItems.NECROTIC_PICKAXE, material + "_pickaxe", "000, 1 , 1 ", item, Items.BLAZE_ROD);
		addShapedRecipe(MItems.NECROTIC_AXE, material + "_axe", "00,01, 1", item, Items.BLAZE_ROD);
		addShapedRecipe(MItems.NECROTIC_SHOVEL, material + "_shovel", "0,1,1", item, Items.BLAZE_ROD);
		addShapedRecipe(MItems.NECROTIC_HOE, material + "_hoe", "00, 1, 1", item, Items.BLAZE_ROD);
		addShapedRecipe(MItems.NECROTIC_SWORD, material + "_sword", "0,0,1", item, Items.BLAZE_ROD);
		addShapedRecipe(MItems.NECROTIC_HELMET, material + "_helmet", "000,0 0", item);
		addShapedRecipe(MItems.NECROTIC_CHESTPLATE, material + "_chestplate", "0 0,000,000", item);
		addShapedRecipe(MItems.NECROTIC_LEGGINGS, material + "_leggings", "000,0 0,0 0", item);
		addShapedRecipe(MItems.NECROTIC_BOOTS, material + "_boots", "0 0,0 0", item);

		material = "entropic";
		item = MItems.ENTROPIC_MATTER_STABLE;
		
		addShapedRecipe(MItems.ENTROPIC_PICKAXE, material + "_pickaxe", "000, 1 , 1 ", item, Items.BLAZE_ROD);
		addShapedRecipe(MItems.ENTROPIC_AXE, material + "_axe", "00,01, 1", item, Items.BLAZE_ROD);
		addShapedRecipe(MItems.ENTROPIC_SHOVEL, material + "_shovel", "0,1,1", item, Items.BLAZE_ROD);
		addShapedRecipe(MItems.ENTROPIC_HOE, material + "_hoe", "00, 1, 1", item, Items.BLAZE_ROD);
		addShapedRecipe(MItems.ENTROPIC_SWORD, material + "_sword", "0,0,1", item, Items.BLAZE_ROD);
		addShapedRecipe(MItems.ENTROPIC_HELMET, material + "_helmet", "000,0 0", item);
		addShapedRecipe(MItems.ENTROPIC_CHESTPLATE, material + "_chestplate", "0 0,000,000", item);
		addShapedRecipe(MItems.ENTROPIC_LEGGINGS, material + "_leggings", "000,0 0,0 0", item);
		addShapedRecipe(MItems.ENTROPIC_BOOTS, material + "_boots", "0 0,0 0", item);
	}
}
