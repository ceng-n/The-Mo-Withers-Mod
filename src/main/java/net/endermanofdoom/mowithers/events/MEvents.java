package net.endermanofdoom.mowithers.events;

import net.endermanofdoom.mac.util.math.Maths;
import net.endermanofdoom.mca.entity.boss.EntityHostileWither;
import net.endermanofdoom.mowithers.items.*;
import net.endermanofdoom.mowithers.registry.MBlocks;
import net.endermanofdoom.mowithers.registry.MItems;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MEvents 
{
	@SubscribeEvent
	public void onLivingEvent(LivingUpdateEvent event)
	{
		Entity entity = event.getEntity();
		
        if (!entity.world.isRemote && entity.posY < -45D)
        {
        	entity.hurtResistantTime = 0;
        }
	}
	
	@SubscribeEvent
	public void onHurtEvent(LivingAttackEvent event)
	{
		Entity entity = event.getEntity();
		
        if (entity instanceof EntityPlayer && ItemWitherArmor.isWearingFullSet((EntityPlayer)entity))
        {
        	if (event.getSource().isExplosion() || event.getSource().getDamageType() == "drown" || event.getSource().getDamageType() == "cramming" || event.getSource().getDamageType() == "wither")
        		event.setCanceled(true);
        }
        
        if (entity instanceof EntityPlayer && event.getSource().getDamageType() == "outOfWorld" && ((EntityPlayer)entity).inventory.armorItemInSlot(3) != null && ((EntityPlayer)entity).inventory.armorItemInSlot(3).getItem() instanceof ItemWitherArmor && ((EntityPlayer)entity).inventory.armorItemInSlot(2) != null && ((EntityPlayer)entity).inventory.armorItemInSlot(2).getItem() instanceof ItemWitherArmor&& ((EntityPlayer)entity).inventory.armorItemInSlot(1) != null && ((EntityPlayer)entity).inventory.armorItemInSlot(1).getItem() instanceof ItemWitherArmor&& ((EntityPlayer)entity).inventory.armorItemInSlot(0) != null && ((EntityPlayer)entity).inventory.armorItemInSlot(0).getItem() instanceof ItemWitherArmor && ((ItemArmor) ((EntityPlayer)entity).inventory.armorItemInSlot(3).getItem()).getArmorMaterial() == MItems.TIER_4_WITHER_ARMOR && ((ItemArmor) ((EntityPlayer)entity).inventory.armorItemInSlot(2).getItem()).getArmorMaterial() == MItems.TIER_4_WITHER_ARMOR && ((ItemArmor) ((EntityPlayer)entity).inventory.armorItemInSlot(1).getItem()).getArmorMaterial() == MItems.TIER_4_WITHER_ARMOR && ((ItemArmor) ((EntityPlayer)entity).inventory.armorItemInSlot(0).getItem()).getArmorMaterial() == MItems.TIER_4_WITHER_ARMOR)
        {
        	event.setCanceled(true);
        }
        
        if (event.getSource().getDamageType() == "outOfWorld" && !event.isCanceled())
        {
        	Random rand = new Random();
            for (int i1 = 0; i1 < 20; ++i1)
            {
            	entity.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, entity.posX + (rand.nextDouble() - 0.5D) * entity.width * 1.25D, entity.posY +  (rand.nextDouble() * entity.height) * 1.25D, entity.posZ + (rand.nextDouble() - 0.5D) * entity.width * 1.25D, 0.0D, 0.0D, 0.0D);
            }
        }
	}
	
	@SubscribeEvent
	public void onMobHitEvent(LivingHurtEvent event)
	{

	}
	
	@SubscribeEvent
	public void onMobDeathEvent(LivingDeathEvent event)
	{
		Entity entity = event.getEntity();
		
        if (!entity.world.isRemote && entity instanceof EntityLivingBase)
        {
            List<Entity> list = entity.world.loadedEntityList;

            for (Entity entities : list)
            {
                if (entities instanceof EntityPlayer && ((EntityPlayer)entities).inventory.armorItemInSlot(3) != null && ((EntityPlayer)entities).inventory.armorItemInSlot(3).getItem() instanceof ItemWitherArmor && ((EntityPlayer)entities).inventory.armorItemInSlot(2) != null && ((EntityPlayer)entities).inventory.armorItemInSlot(2).getItem() instanceof ItemWitherArmor&& ((EntityPlayer)entities).inventory.armorItemInSlot(1) != null && ((EntityPlayer)entities).inventory.armorItemInSlot(1).getItem() instanceof ItemWitherArmor&& ((EntityPlayer)entities).inventory.armorItemInSlot(0) != null && ((EntityPlayer)entities).inventory.armorItemInSlot(0).getItem() instanceof ItemWitherArmor && ((ItemArmor) ((EntityPlayer)entities).inventory.armorItemInSlot(3).getItem()).getArmorMaterial() == MItems.TIER_4_WITHER_ARMOR && ((ItemArmor) ((EntityPlayer)entities).inventory.armorItemInSlot(2).getItem()).getArmorMaterial() == MItems.TIER_4_WITHER_ARMOR && ((ItemArmor) ((EntityPlayer)entities).inventory.armorItemInSlot(1).getItem()).getArmorMaterial() == MItems.TIER_4_WITHER_ARMOR && ((ItemArmor) ((EntityPlayer)entities).inventory.armorItemInSlot(0).getItem()).getArmorMaterial() == MItems.TIER_4_WITHER_ARMOR)
                {
                	((EntityPlayer)entities).heal(((EntityLivingBase)entity).getMaxHealth() * 2);
                	((EntityLivingBase)entity).deathTime = 19;
                }
            }
        }
        
        if (!entity.world.isRemote && entity instanceof EntityHostileWither)
        {
        	EntityHostileWither wither = (EntityHostileWither)entity;
        	
        	if (entity.world.getGameRules().getBoolean("doMobLoot"))
            for (int k = 0; k < Maths.random(8 * wither.getWitherScale(), 16 * wither.getWitherScale()); ++k)
            {
            	wither.dropItem(wither.isRaidBoss() ? MItems.ENTROPIC_MATTER_UNSTABLE : MItems.ATROPHIC_SHARD, 1);
            }
        }
	}
	
	@SubscribeEvent
	public void onMobSpawnEvent(EntityJoinWorldEvent event)
	{
		Entity entity = event.getEntity();
		
        if (!entity.world.isRemote && entity instanceof EntityLivingBase)
        {
            List<Entity> list = entity.world.loadedEntityList;

            for (Entity entities : list)
            {
                if (entities instanceof EntityPlayer && ((EntityPlayer)entities).inventory.armorItemInSlot(3) != null && ((EntityPlayer)entities).inventory.armorItemInSlot(3).getItem() instanceof ItemWitherArmor && ((EntityPlayer)entities).inventory.armorItemInSlot(2) != null && ((EntityPlayer)entities).inventory.armorItemInSlot(2).getItem() instanceof ItemWitherArmor&& ((EntityPlayer)entities).inventory.armorItemInSlot(1) != null && ((EntityPlayer)entities).inventory.armorItemInSlot(1).getItem() instanceof ItemWitherArmor&& ((EntityPlayer)entities).inventory.armorItemInSlot(0) != null && ((EntityPlayer)entities).inventory.armorItemInSlot(0).getItem() instanceof ItemWitherArmor && ((ItemArmor) ((EntityPlayer)entities).inventory.armorItemInSlot(3).getItem()).getArmorMaterial() == MItems.TIER_4_WITHER_ARMOR && ((ItemArmor) ((EntityPlayer)entities).inventory.armorItemInSlot(2).getItem()).getArmorMaterial() == MItems.TIER_4_WITHER_ARMOR && ((ItemArmor) ((EntityPlayer)entities).inventory.armorItemInSlot(1).getItem()).getArmorMaterial() == MItems.TIER_4_WITHER_ARMOR && ((ItemArmor) ((EntityPlayer)entities).inventory.armorItemInSlot(0).getItem()).getArmorMaterial() == MItems.TIER_4_WITHER_ARMOR)
                {
                	((EntityPlayer)entities).heal(((EntityLivingBase)entity).getMaxHealth());
                }
            }
        }
        
		if (entity instanceof EntityItem)
		{
			EntityItem item = (EntityItem)entity;
			
			if (
					item.getItem().getItem() == Item.getItemFromBlock(MBlocks.NETHER_STAR_BLOCK) ||
					item.getItem().getItem() == MItems.NETHER_STAR_SHARD ||
					item.getItem().getItem() == MItems.NETHER_STAR_DUST ||
					item.getItem().getItem() == MItems.ATROPHIC_SHARD ||
					item.getItem().getItem() == MItems.ATROPHIC_CRYSTAL ||
					item.getItem().getItem() == MItems.NECROTIC_STAR ||
					item.getItem().getItem() == MItems.ENTROPIC_MATTER_UNSTABLE ||
					item.getItem().getItem() == MItems.ENTROPIC_MATTER_STABLE ||
					item.getItem().getItem() instanceof ItemWitherArmor ||
					item.getItem().getItem() instanceof ItemWitherAxe ||
					item.getItem().getItem() instanceof ItemWitherHoe ||
					item.getItem().getItem() instanceof ItemWitherPickaxe ||
					item.getItem().getItem() instanceof ItemWitherShovel ||
					item.getItem().getItem() instanceof ItemWitherSword
				)
			{
			item.setEntityInvulnerable(true);
			item.setNoDespawn();
			}
		}
	}
}
