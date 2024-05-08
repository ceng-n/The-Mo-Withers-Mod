package net.endermanofdoom.mowithers.items;

import net.endermanofdoom.mca.MCA;
import net.endermanofdoom.mca.entity.projectile.EntityWitherSkullShared;
import net.endermanofdoom.mowithers.MoWithers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemWitherSword extends ItemSword
{
	public ItemWitherSword(ToolMaterial material) 
	{
		super(material);
		setCreativeTab(MoWithers.MO_TAB);
	}
	
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
	    if (target instanceof EntityLivingBase && attacker instanceof EntityPlayer)
	    {
    		target.addPotionEffect(new PotionEffect(MobEffects.WITHER, 20 * (int)this.getAttackDamage(), getToolMaterialName() == "Tier3Wither" ? 4 : getToolMaterialName() == "Tier2Wither" ? 2 : 1));
	    }

	    return super.hitEntity(stack, target, attacker);
	}
	
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
		  playerIn.swingArm(handIn);
    	if (worldIn.isRemote)
    	{
            return new ActionResult<ItemStack>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
    	}
    	else
    	{
	      double d1 = 1.5D;
	      double d22 = 64D;
	      Vec3d vec3 = playerIn.getLook(1.0F);
	      double d2 = (playerIn.posX + vec3.x * d22) - (playerIn.posX + vec3.x * d1);
	      double d3 = (playerIn.posY + vec3.y * d22) - (playerIn.posY + vec3.x * d1);
	      double d4 = (playerIn.posZ + vec3.z * d22) - (playerIn.posZ + vec3.z * d1);
	      EntityWitherSkullShared entitywitherskull = new EntityWitherSkullShared(worldIn, playerIn, d2, d3, d4);
	      if (playerIn.getRNG().nextFloat() < 0.01F || playerIn.isSneaking())
	    	  entitywitherskull.setInvulnerable(true);
	      entitywitherskull.copyLocationAndAnglesFrom(playerIn);
	      entitywitherskull.posX = playerIn.posX + vec3.x;
	      entitywitherskull.posY = playerIn.posY + vec3.y + 1.5D;
	      entitywitherskull.posZ = playerIn.posZ + vec3.z;
	      entitywitherskull.setDamage(this.getAttackDamage() + 4);
	      entitywitherskull.setRadius(getToolMaterialName() == "Tier3Wither" ? 7F : getToolMaterialName() == "Tier2Wither" ? 3F : 1F);
	      entitywitherskull.setSkullSize(0.5F);
	      entitywitherskull.playSound(SoundEvents.ENTITY_WITHER_SHOOT, 1F, 1F);
	      entitywitherskull.setMod(MCA.MODID);
	      entitywitherskull.setSkullTexture("boss/wither");
	      if (!worldIn.isRemote)
	      {
	          worldIn.spawnEntity(entitywitherskull);
	      }
    	}
    	
	    return super.onItemRightClick(worldIn, playerIn, handIn);
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
