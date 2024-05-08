package net.endermanofdoom.mowithers.items;

import com.google.common.collect.Multimap;

import net.endermanofdoom.mowithers.MoWithers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemWitherHoe extends ItemHoe
{
    private final float attackDamage;
    
    public ItemWitherHoe(ToolMaterial material) 
	{
		super(material);
		setCreativeTab(MoWithers.MO_TAB);
        this.attackDamage = material.getAttackDamage() - 4.0F;
	}

	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
	    if (target instanceof EntityLivingBase && attacker instanceof EntityPlayer)
	    {
    		target.addPotionEffect(new PotionEffect(MobEffects.WITHER, 20 * (int)attackDamage, getMaterialName() == "Tier3Wither" ? 4 : getMaterialName() == "Tier2Wither" ? 2 : 1));
	    }

	    return super.hitEntity(stack, target, attacker);
	}
	
	public EnumRarity getRarity(ItemStack stack)
	{
		return getMaterialName() == "Tier3Wither" ? net.endermanofdoom.mca.MCA.SUPER_EPIC : getMaterialName() == "Tier2Wither" ? EnumRarity.EPIC : EnumRarity.RARE;
	}
	
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return true;
    }

    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.remove(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 0.0D, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.attackDamage, 0));
        }

        return multimap;
    }
}
