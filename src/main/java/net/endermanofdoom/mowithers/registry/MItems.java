package net.endermanofdoom.mowithers.registry;

import net.endermanofdoom.mowithers.MoWithers;
import net.endermanofdoom.mowithers.items.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.GameData;

public class MItems 
{
	public static final ToolMaterial TIER_1_WITHER_TOOLS = EnumHelper.addToolMaterial("Tier1Wither", 4, 4683, 16.0F, 26.0F, 16);
	public static final ToolMaterial TIER_2_WITHER_TOOLS = EnumHelper.addToolMaterial("Tier2Wither", 9, 85350, 32.0F, 196.0F, 22);
	public static final ToolMaterial TIER_3_WITHER_TOOLS = EnumHelper.addToolMaterial("Tier3Wither", 99, 7567500, 120.0F, 24996.0F, 26);
	
	public static final ArmorMaterial TIER_1_WITHER_ARMOR = EnumHelper.addArmorMaterial("Tier1Wither", MoWithers.MODID + ":atrophic", 162, new int[]{3, 7, 8, 4}, 16, SoundEvents.BLOCK_CHORUS_FLOWER_GROW, 4.0F);
	public static final ArmorMaterial TIER_2_WITHER_ARMOR = EnumHelper.addArmorMaterial("Tier2Wither", MoWithers.MODID + ":necrotic", 2468, new int[]{3, 7, 9, 4}, 20, SoundEvents.BLOCK_SHULKER_BOX_OPEN, 10.0F);
	public static final ArmorMaterial TIER_3_WITHER_ARMOR = EnumHelper.addArmorMaterial("Tier3Wither", MoWithers.MODID + ":entropic", 120000, new int[]{4, 7, 10, 4}, 22, SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, 20.0F);
	public static final ArmorMaterial TIER_4_WITHER_ARMOR = EnumHelper.addArmorMaterial("Tier4Wither", MoWithers.MODID + ":reaper", Integer.MAX_VALUE, new int[]{30, 30, 30, 30}, 30, SoundEvents.BLOCK_CLOTH_PLACE, 80.0F);

	public static final Item NETHER_STAR_SHARD = new ItemSimpleFoiled().setCreativeTab(MoWithers.MO_TAB);
	public static final Item NETHER_STAR_DUST = new ItemSimpleFoiled().setCreativeTab(MoWithers.MO_TAB);
	public static final Item ATROPHIC_SHARD = new ItemSimpleFoiled().setCreativeTab(MoWithers.MO_TAB);
	public static final Item ATROPHIC_CRYSTAL = new ItemSimpleFoiled().setCreativeTab(MoWithers.MO_TAB);
	public static final Item ATROPHIC_SWORD = new ItemWitherSword(TIER_1_WITHER_TOOLS);
	public static final Item ATROPHIC_AXE = new ItemWitherAxe(TIER_1_WITHER_TOOLS, 60F, -3F);
	public static final Item ATROPHIC_PICKAXE = new ItemWitherPickaxe(TIER_1_WITHER_TOOLS);
	public static final Item ATROPHIC_SHOVEL = new ItemWitherShovel(TIER_1_WITHER_TOOLS);
	public static final Item ATROPHIC_HOE = new ItemWitherHoe(TIER_1_WITHER_TOOLS);
	public static final Item ATROPHIC_HELMET = new ItemWitherArmor(TIER_1_WITHER_ARMOR, 1, EntityEquipmentSlot.HEAD, 20D, 2D);
	public static final Item ATROPHIC_CHESTPLATE = new ItemWitherArmor(TIER_1_WITHER_ARMOR, 1, EntityEquipmentSlot.CHEST, 40D, 2D);
	public static final Item ATROPHIC_LEGGINGS = new ItemWitherArmor(TIER_1_WITHER_ARMOR, 2, EntityEquipmentSlot.LEGS, 30D, 2D);
	public static final Item ATROPHIC_BOOTS = new ItemWitherArmor(TIER_1_WITHER_ARMOR, 1, EntityEquipmentSlot.FEET, 10D, 2D);
	public static final Item NECROTIC_STAR = new ItemSimpleFoiled().setCreativeTab(MoWithers.MO_TAB);
	public static final Item NECROTIC_SWORD = new ItemWitherSword(TIER_2_WITHER_TOOLS);
	public static final Item NECROTIC_AXE = new ItemWitherAxe(TIER_2_WITHER_TOOLS, 400F, -2.75F);
	public static final Item NECROTIC_PICKAXE = new ItemWitherPickaxe(TIER_2_WITHER_TOOLS);
	public static final Item NECROTIC_SHOVEL = new ItemWitherShovel(TIER_2_WITHER_TOOLS);
	public static final Item NECROTIC_HOE = new ItemWitherHoe(TIER_2_WITHER_TOOLS);
	public static final Item NECROTIC_HELMET = new ItemWitherArmor(TIER_2_WITHER_ARMOR, 1, EntityEquipmentSlot.HEAD, 250D, 8D);
	public static final Item NECROTIC_CHESTPLATE = new ItemWitherArmor(TIER_2_WITHER_ARMOR, 1, EntityEquipmentSlot.CHEST, 400D, 8D);
	public static final Item NECROTIC_LEGGINGS = new ItemWitherArmor(TIER_2_WITHER_ARMOR, 2, EntityEquipmentSlot.LEGS, 300D, 8D);
	public static final Item NECROTIC_BOOTS = new ItemWitherArmor(TIER_2_WITHER_ARMOR, 1, EntityEquipmentSlot.FEET, 150D, 8D);
	public static final Item ENTROPIC_MATTER_UNSTABLE = new ItemSimpleFoiled().setCreativeTab(MoWithers.MO_TAB);
	public static final Item ENTROPIC_MATTER_STABLE = new ItemSimpleFoiled().setCreativeTab(MoWithers.MO_TAB);
	public static final Item ENTROPIC_SWORD = new ItemWitherSword(TIER_3_WITHER_TOOLS);
	public static final Item ENTROPIC_AXE = new ItemWitherAxe(TIER_3_WITHER_TOOLS, 75000F, -2F);
	public static final Item ENTROPIC_PICKAXE = new ItemWitherPickaxe(TIER_3_WITHER_TOOLS);
	public static final Item ENTROPIC_SHOVEL = new ItemWitherShovel(TIER_3_WITHER_TOOLS);
	public static final Item ENTROPIC_HOE = new ItemWitherHoe(TIER_3_WITHER_TOOLS);
	public static final Item ENTROPIC_HELMET = new ItemWitherArmor(TIER_3_WITHER_ARMOR, 1, EntityEquipmentSlot.HEAD, 5000D, 24D);
	public static final Item ENTROPIC_CHESTPLATE = new ItemWitherArmor(TIER_3_WITHER_ARMOR, 1, EntityEquipmentSlot.CHEST, 8000D, 24D);
	public static final Item ENTROPIC_LEGGINGS = new ItemWitherArmor(TIER_3_WITHER_ARMOR, 2, EntityEquipmentSlot.LEGS, 7000D, 24D);
	public static final Item ENTROPIC_BOOTS = new ItemWitherArmor(TIER_3_WITHER_ARMOR, 1, EntityEquipmentSlot.FEET, 4000D, 24D);
	public static final Item REAPER_HOOD = new ItemWitherArmor(TIER_4_WITHER_ARMOR, 1, EntityEquipmentSlot.HEAD, 8000000D, 4000D);
	public static final Item REAPER_CLOAK = new ItemWitherArmor(TIER_4_WITHER_ARMOR, 1, EntityEquipmentSlot.CHEST, 8000000D, 4000D);
	public static final Item REAPER_TROUSERS = new ItemWitherArmor(TIER_4_WITHER_ARMOR, 2, EntityEquipmentSlot.LEGS, 8000000D, 4000D);
	public static final Item REAPER_SHOES = new ItemWitherArmor(TIER_4_WITHER_ARMOR, 1, EntityEquipmentSlot.FEET, 8000000D, 4000D);
	
	public static void registerItems()
	{
		Items.NETHER_STAR.setCreativeTab(MoWithers.MO_TAB);
		registerItem(NETHER_STAR_SHARD, "nether_star_shard");
		registerItem(NETHER_STAR_DUST, "nether_star_dust");
		registerItem(ATROPHIC_SHARD, "atrophic_shard");
		TIER_1_WITHER_TOOLS.setRepairItem(new ItemStack(ATROPHIC_SHARD));
		TIER_1_WITHER_ARMOR.setRepairItem(new ItemStack(ATROPHIC_SHARD));
		registerItem(ATROPHIC_CRYSTAL, "atrophic_crystal");
		registerItem(ATROPHIC_SWORD, "atrophic_sword");
		registerItem(ATROPHIC_AXE, "atrophic_axe");
		registerItem(ATROPHIC_PICKAXE, "atrophic_pickaxe");
		registerItem(ATROPHIC_SHOVEL, "atrophic_shovel");
		registerItem(ATROPHIC_HOE, "atrophic_hoe");
		registerItem(ATROPHIC_HELMET, "atrophic_helmet");
		registerItem(ATROPHIC_CHESTPLATE, "atrophic_chestplate");
		registerItem(ATROPHIC_LEGGINGS, "atrophic_leggings");
		registerItem(ATROPHIC_BOOTS, "atrophic_boots");
		TIER_2_WITHER_TOOLS.setRepairItem(new ItemStack(ATROPHIC_CRYSTAL));
		TIER_2_WITHER_ARMOR.setRepairItem(new ItemStack(ATROPHIC_CRYSTAL));
		registerItem(NECROTIC_STAR, "necrotic_star");
		registerItem(NECROTIC_SWORD, "necrotic_sword");
		registerItem(NECROTIC_AXE, "necrotic_axe");
		registerItem(NECROTIC_PICKAXE, "necrotic_pickaxe");
		registerItem(NECROTIC_SHOVEL, "necrotic_shovel");
		registerItem(NECROTIC_HOE, "necrotic_hoe");
		registerItem(NECROTIC_HELMET, "necrotic_helmet");
		registerItem(NECROTIC_CHESTPLATE, "necrotic_chestplate");
		registerItem(NECROTIC_LEGGINGS, "necrotic_leggings");
		registerItem(NECROTIC_BOOTS, "necrotic_boots");
		TIER_3_WITHER_TOOLS.setRepairItem(new ItemStack(NECROTIC_STAR));
		TIER_3_WITHER_ARMOR.setRepairItem(new ItemStack(NECROTIC_STAR));
		registerItem(ENTROPIC_MATTER_UNSTABLE, "entropic_matter_unstable");
		registerItem(ENTROPIC_MATTER_STABLE, "entropic_matter");
		registerItem(ENTROPIC_SWORD, "entropic_sword");
		registerItem(ENTROPIC_AXE, "entropic_axe");
		registerItem(ENTROPIC_PICKAXE, "entropic_pickaxe");
		registerItem(ENTROPIC_SHOVEL, "entropic_shovel");
		registerItem(ENTROPIC_HOE, "entropic_hoe");
		registerItem(ENTROPIC_HELMET, "entropic_helmet");
		registerItem(ENTROPIC_CHESTPLATE, "entropic_chestplate");
		registerItem(ENTROPIC_LEGGINGS, "entropic_leggings");
		registerItem(ENTROPIC_BOOTS, "entropic_boots");
		registerItem(REAPER_HOOD, "reaper_hood");
		registerItem(REAPER_CLOAK, "reaper_cloak");
		registerItem(REAPER_TROUSERS, "reaper_trousers");
		registerItem(REAPER_SHOES, "reaper_shoes");
	}
	
	private static void registerItem(Item item, String registername)
	{
		GameData.register_impl(item.setUnlocalizedName(registername).setRegistryName(registername));
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerRenders()
	{
		renderItem(NETHER_STAR_SHARD);
		renderItem(NETHER_STAR_DUST);
		renderItem(ATROPHIC_SHARD);
		renderItem(ATROPHIC_CRYSTAL);
		renderItem(ATROPHIC_SWORD);
		renderItem(ATROPHIC_AXE);
		renderItem(ATROPHIC_PICKAXE);
		renderItem(ATROPHIC_SHOVEL);
		renderItem(ATROPHIC_HOE);
		renderItem(ATROPHIC_HELMET);
		renderItem(ATROPHIC_CHESTPLATE);
		renderItem(ATROPHIC_LEGGINGS);
		renderItem(ATROPHIC_BOOTS);
		renderItem(NECROTIC_STAR);
		renderItem(NECROTIC_SWORD);
		renderItem(NECROTIC_AXE);
		renderItem(NECROTIC_PICKAXE);
		renderItem(NECROTIC_SHOVEL);
		renderItem(NECROTIC_HOE);
		renderItem(NECROTIC_HELMET);
		renderItem(NECROTIC_CHESTPLATE);
		renderItem(NECROTIC_LEGGINGS);
		renderItem(NECROTIC_BOOTS);
		renderItem(ENTROPIC_MATTER_UNSTABLE);
		renderItem(ENTROPIC_MATTER_STABLE);
		renderItem(ENTROPIC_SWORD);
		renderItem(ENTROPIC_AXE);
		renderItem(ENTROPIC_PICKAXE);
		renderItem(ENTROPIC_SHOVEL);
		renderItem(ENTROPIC_HOE);
		renderItem(ENTROPIC_HELMET);
		renderItem(ENTROPIC_CHESTPLATE);
		renderItem(ENTROPIC_LEGGINGS);
		renderItem(ENTROPIC_BOOTS);
		renderItem(REAPER_HOOD);
		renderItem(REAPER_CLOAK);
		renderItem(REAPER_TROUSERS);
		renderItem(REAPER_SHOES);
	}
	
	@SideOnly(Side.CLIENT)
	private static void renderItem(Item item)
	{
		renderItem(item, 0);
	}
	
	@SideOnly(Side.CLIENT)
	private static void renderItem(Item item, int meta)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, new ModelResourceLocation(MoWithers.MODID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
