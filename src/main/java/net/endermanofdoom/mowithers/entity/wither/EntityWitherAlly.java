package net.endermanofdoom.mowithers.entity.wither;

import net.endermanofdoom.mca.entity.boss.EntityBaseWither;
import net.endermanofdoom.mca.entity.EnumWitherType;
import net.endermanofdoom.mca.entity.projectile.EntityWitherSkullShared;
import javax.annotation.Nullable;
import com.google.common.base.Predicate;

import net.endermanofdoom.mowithers.MoWithers;
import net.endermanofdoom.mowithers.registry.MSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class EntityWitherAlly extends EntityBaseWither
{
	public EntityWitherAlly(World worldIn) 
	{
		super(worldIn);
	}
	
	protected void initEntityAI()
    {
		WITHERTARGETS = new Predicate<Entity>()
	    {
	        public boolean apply(@Nullable Entity p_apply_1_)
	        {
	            return p_apply_1_ instanceof EntityLivingBase && ((EntityLivingBase)p_apply_1_).attackable() && p_apply_1_ instanceof IMob;
	        }
	    };
		super.initEntityAI();
        this.tasks.addTask(1, new EntityAIWitherStay(this));
        this.targetTasks.addTask(3, new EntityAIWitherTargeting<EntityLivingBase>(this, EntityLivingBase.class, WITHERTARGETS));
    }
    
    public void setSkullStats(EntityWitherSkullShared skull, float damage, boolean invul)
    {
        super.setSkullStats(skull, damage, invul);
        skull.setMod(MoWithers.MODID);
        skull.setSkullTexture("wither/good/wither_ally");
        skull.setType(15);
    }
    
    public TextFormatting getNameColor()
    {
    	return TextFormatting.AQUA;
    }
	
    /**
     * Add the given player to the list of players tracking this entity. For instance, a player may track a boss in
     * order to view its associated boss bar.
     */
    public void addTrackingPlayer(EntityPlayerMP player)
    {
    }

    /**
     * Removes the given player from the list of players tracking this entity. See {@link Entity#addTrackingPlayer} for
     * more information on tracking.
     */
    public void removeTrackingPlayer(EntityPlayerMP player)
    {
    }
    
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (source == DamageSource.WITHER)
        {
          return false;
        }
        else
        {
        	if (source.getTrueSource() != null && !(source.getTrueSource() instanceof IMob))
        		return false;
        	
        	return super.attackEntityFrom(source, amount);
        }
    }

    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        ItemStack itemstack = player.getHeldItem(hand);

        if (!this.isTamed() && itemstack.getItem() == Items.NETHER_STAR)
        {
            if (!this.world.isRemote && !this.isTamed())
            {
                this.setTamedBy(player);
                this.navigator.clearPath();
                this.setAttackTarget((EntityLivingBase)null);
                this.playTameEffect(true);
                this.world.setEntityState(this, (byte)7);
                this.playSound(SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, 1, 1);
            }

            return true;
        }

        return super.processInteract(player, hand);
    }

    /**
     * Returns true if this entity is undead.
     */
    public boolean isEntityUndead()
    {
        return false;
    }
    
    protected SoundEvent getAmbientSound()
    {
        return MSounds.ENTITY_WITHER_ALLY[0];
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return MSounds.ENTITY_WITHER_ALLY[1];
    }

    protected SoundEvent getDeathSound()
    {
        return MSounds.ENTITY_WITHER_ALLY[2];
    }   
    
    protected float getSoundPitch()
    {
      return super.getSoundPitch() + 0.5F;
    }
    
    public EnumWitherType getWitherType()
    {
        return EnumWitherType.FRIENDLY;
    }
}
