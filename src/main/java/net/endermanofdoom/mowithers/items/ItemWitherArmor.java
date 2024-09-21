package net.endermanofdoom.mowithers.items;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.collect.Multimap;

import net.endermanofdoom.mca.registrey.MCAPotions;
import net.endermanofdoom.mowithers.MoWithers;
import net.endermanofdoom.mowithers.registry.MItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemWitherArmor extends ItemArmor
{	
    private static final UUID[] ARMOR_MODIFIERS = new UUID[] {UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};
    public final double hpboost;
    public final double attackboost;
    
	public ItemWitherArmor(ArmorMaterial material, int renderIndex, EntityEquipmentSlot equipmentSlot, double health, double attack) 
	{
		super(material, renderIndex, equipmentSlot);
		hpboost = health;
		attackboost = attack;
		setCreativeTab(MoWithers.MO_TAB);
	}
	
	public EnumRarity getRarity(ItemStack stack)
	{
		switch (this.getArmorInt())
		{
		case 2:
			return EnumRarity.EPIC;
		case 5:
			return net.endermanofdoom.mca.MCA.SUPER_EPIC;
		case 10:
			return net.endermanofdoom.mca.MCA.LEGENDARY;
		default:
			return EnumRarity.RARE;
		}
	}
	
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced)
    {
    	if (this.getArmorInt() >= 10)
    	{
            tooltip.add(TextFormatting.GOLD + "'The robes of a Reaper. You've earned these." + TextFormatting.WHITE);
            tooltip.add(TextFormatting.GOLD + "You know the job. Go and complete your duty.'" + TextFormatting.WHITE);
            tooltip.add(TextFormatting.AQUA + "- Made in Between" + TextFormatting.WHITE);
    	}
    }
	
    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == this.armorType)
        {
            multimap.put(SharedMonsterAttributes.MAX_HEALTH.getName(), new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Health modifier", hpboost, 0));
            multimap.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "MS modifier", 0.01D * getArmorInt(), 0));
            multimap.put(SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(), new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "KB modifier", 0.25D, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Strength modifier", attackboost, 0));
        }

        return multimap;
    }
    
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemstack) 
	{
		world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, player.posX + (player.getRNG().nextDouble() - 0.5D) * (double)player.width, player.posY + player.getRNG().nextDouble() * (double)player.height, player.posZ + (player.getRNG().nextDouble() - 0.5D) * (double)player.width, 0.0D, 0.0D, 0.0D);
		
		if (player.ticksExisted % (20 / this.getArmorInt()) == 0)
		{
			player.heal(this.getArmorInt() * (player.capabilities.disableDamage ? 10000 : 1));
			player.getFoodStats().addStats(1, 1);
			if (player.getAbsorptionAmount() < player.getMaxHealth())
				player.setAbsorptionAmount(player.getAbsorptionAmount() + this.getArmorInt());
		}
		
		if (this.getArmorInt() > 2 && this.getDamage(itemstack) > 0)
			itemstack.setItemDamage(this.getDamage(itemstack) - 100);
		
		if (this.getDamage(itemstack) < 0)
			itemstack.setItemDamage(0);
		
		if (itemstack.getItemDamage() >= getMaxDamage() - 1)
			itemstack.setItemDamage(getMaxDamage() - 1);
		
        EntityEquipmentSlot slot = EntityLiving.getSlotForItemStack(itemstack);
		
		player.removePotionEffect(MobEffects.WITHER);
		player.removePotionEffect(MobEffects.POISON);
		player.removePotionEffect(MCAPotions.BLEEDING);
		player.removePotionEffect(MCAPotions.VENOM);
		player.extinguish();
		
		if (this.getArmorInt() >= 10 && !itemstack.isItemEnchanted())
		{
			itemstack.addEnchantment(Enchantments.BINDING_CURSE, 1);
			itemstack.addEnchantment(Enchantments.PROTECTION, 10);
			itemstack.addEnchantment(Enchantments.PROJECTILE_PROTECTION, 10);
			itemstack.addEnchantment(Enchantments.BLAST_PROTECTION, 10);
			itemstack.addEnchantment(Enchantments.FIRE_PROTECTION, 10);
			itemstack.addEnchantment(Enchantments.RESPIRATION, 10);
			itemstack.addEnchantment(Enchantments.THORNS, 10);
			itemstack.addEnchantment(Enchantments.UNBREAKING, 10);
			itemstack.addEnchantment(Enchantments.AQUA_AFFINITY, 1);
			itemstack.addEnchantment(Enchantments.MENDING, 1);
		}
		
		if (slot == EntityEquipmentSlot.HEAD)
		{
			this.effectPlayer(player, MobEffects.WATER_BREATHING, 0);
			this.effectPlayer(player, MobEffects.NIGHT_VISION, 0);
			player.removePotionEffect(MobEffects.BLINDNESS);
			player.removePotionEffect(MobEffects.NAUSEA);
			player.removePotionEffect(MobEffects.HUNGER);
			
			if (this.getArmorInt() >= 10 && player.noClip && player.ticksExisted % 1000 == 0)
			{
				world.playSound(null, player.posX, player.posY + player.getEyeHeight(), player.posZ, SoundEvents.AMBIENT_CAVE, SoundCategory.MASTER, 20F, 0.5F);
			}
		}
		
		if (slot == EntityEquipmentSlot.CHEST)
		{
			this.effectPlayer(player, MobEffects.RESISTANCE, this.getArmorInt() > 4 ? 3 : this.getArmorInt() - 1);
			this.effectPlayer(player, MobEffects.HASTE, this.getArmorInt() - 1);
			this.effectPlayer(player, MobEffects.FIRE_RESISTANCE, 0);
			player.removePotionEffect(MobEffects.MINING_FATIGUE);
		}
		
		if (slot == EntityEquipmentSlot.LEGS)
		{
			this.effectPlayer(player, MobEffects.JUMP_BOOST, this.getArmorInt() - 1);
			player.removePotionEffect(MobEffects.WEAKNESS);
		}
		
		if (slot == EntityEquipmentSlot.FEET)
		{
			player.removePotionEffect(MobEffects.SLOWNESS);
		}
		
		if (isWearingFullSet(player)) 
		{
			player.capabilities.setFlySpeed(0.05F * this.getArmorInt());
			player.capabilities.allowFlying = player.getHealth() > player.getMaxHealth() / 2 || player.capabilities.disableDamage;
			player.fallDistance = 0F;
			if (!player.capabilities.isFlying && player.motionY < 0 && player.isSneaking())
				player.motionY *= 0.95F;
			player.jumpMovementFactor = player.moveStrafing != 0F ? 0.06F : 0.02F;
			player.stepHeight = 1.5F;
			if (player.capabilities.isFlying && player.getHealth() <= player.getMaxHealth() / 2 && !player.capabilities.disableDamage)
				player.capabilities.isFlying = false;
			if (player.capabilities.isFlying && player.moveForward == 0F && this.getArmorInt() >= 10)
			{
				player.motionX *= 0.75D;
				player.motionY *= 0.75D;
				player.motionZ *= 0.75D;
			}			
			if (player.capabilities.isFlying && !player.getHeldItemMainhand().isEmpty() && player.isSneaking() && player.moveForward == 0F && player.moveStrafing == 0F && this.getArmorInt() >= 10)
			{
				player.getHeldItemMainhand().getItem().onItemRightClick(world, player, EnumHand.MAIN_HAND);
				player.getHeldItemMainhand().getItem().onItemUse(player, world, player.getPosition(), EnumHand.MAIN_HAND, EnumFacing.UP, (float)player.posX, (float)player.posY, (float)player.posZ);
			}
			player.noClip = player.capabilities.isFlying && this.getArmorInt() >= 10;
			if (this.getArmorInt() >= 10)
				this.effectPlayer(player, MobEffects.INVISIBILITY, 0);
		}
		else
		{
			player.capabilities.setFlySpeed(0.05F);
			player.stepHeight = 0.5F;
			player.jumpMovementFactor = 0.02F;
			player.capabilities.allowFlying = player.capabilities.disableDamage;
		}
	}
	
	public static boolean isWearingFullSet(EntityPlayer player) 
	{
		return player.inventory.armorItemInSlot(3) != null && player.inventory.armorItemInSlot(3).getItem() instanceof ItemWitherArmor
			&& player.inventory.armorItemInSlot(2) != null && player.inventory.armorItemInSlot(2).getItem() instanceof ItemWitherArmor
			&& player.inventory.armorItemInSlot(1) != null && player.inventory.armorItemInSlot(1).getItem() instanceof ItemWitherArmor
			&& player.inventory.armorItemInSlot(0) != null && player.inventory.armorItemInSlot(0).getItem() instanceof ItemWitherArmor;
	}
	
	public int getArmorInt() 
	{
		if (getArmorMaterial() == MItems.TIER_4_WITHER_ARMOR)
			return 10;
		else if (getArmorMaterial() == MItems.TIER_3_WITHER_ARMOR)
			return 4;
		else if (getArmorMaterial() == MItems.TIER_2_WITHER_ARMOR)
			return 2;
		else
			return 1;
	}

	private void effectPlayer(EntityPlayer player, Potion potion, int amplifier) 
	{
		player.addPotionEffect(new PotionEffect(potion, potion.equals(MobEffects.NIGHT_VISION) ? 500 : 2, amplifier, true, false));
	}
	
    @SuppressWarnings("deprecation")
	public int getMaxDamage()
    {
        return this.getArmorInt() >= 10 ? 0 : super.getMaxDamage();
    }
    
    public void setDamage(ItemStack stack, int damage)
    {
    	super.setDamage(stack, this.getArmorInt() >= 10 ? 0 : damage);
    }
}