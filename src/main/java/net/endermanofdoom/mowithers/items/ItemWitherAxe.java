package net.endermanofdoom.mowithers.items;

import net.endermanofdoom.mowithers.MoWithers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemWitherAxe extends ItemAxe
{
	public ItemWitherAxe(ToolMaterial material, float damage, float speed) 
	{
		super(material, damage, speed);
		setCreativeTab(MoWithers.MO_TAB);
	}

	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
	    if (target instanceof EntityLivingBase && attacker instanceof EntityPlayer)
	    {
    		target.addPotionEffect(new PotionEffect(MobEffects.WITHER, 20 * (int)attackDamage, getToolMaterialName() == "Tier3Wither" ? 4 : getToolMaterialName() == "Tier2Wither" ? 2 : 1));
	    }

	    return super.hitEntity(stack, target, attacker);
	}
	
	public EnumRarity getRarity(ItemStack stack)
	{
		return getToolMaterialName() == "Tier3Wither" ? net.endermanofdoom.mca.MCA.SUPER_EPIC : getToolMaterialName() == "Tier2Wither" ? EnumRarity.EPIC : EnumRarity.RARE;
	}
	
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return true;
    }
}
