package net.endermanofdoom.mowithers.entity.wither;

import java.util.List;

import javax.annotation.Nullable;
import com.google.common.base.Predicate;

import net.endermanofdoom.mca.entity.boss.EntityBaseWither;
import net.endermanofdoom.mca.entity.boss.EntityHostileWither;
import net.endermanofdoom.mca.entity.EnumWitherType;
import net.endermanofdoom.mca.entity.projectile.EntityWitherSkullShared;
import net.endermanofdoom.mca.MinecraftAdventures;
import net.endermanofdoom.mowithers.MoWithers;
import net.endermanofdoom.mowithers.registry.MItems;
import net.endermanofdoom.mowithers.registry.MSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWaterFlying;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.BossInfo;
import net.minecraft.world.World;

public class EntityWitherPink extends EntityBaseWither
{
	public EntityWitherPink(World worldIn) 
	{
		super(worldIn);
		this.bossInfo.setColor(BossInfo.Color.PINK);
	}
	
	protected void initEntityAI()
    {
		WITHERTARGETS = new Predicate<Entity>()
	    {
	        public boolean apply(@Nullable Entity p_apply_1_)
	        {
	            return p_apply_1_ instanceof EntityLivingBase && ((EntityLivingBase)p_apply_1_).attackable();
	        }
	    };
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIPanic(this, 1.25D));
        this.tasks.addTask(3, new EntityAIWitherStay(this));
        this.tasks.addTask(4, new EntiyAIWitherFollowOwner(this, 1.0D, 32.0F, 2.0F));
        this.tasks.addTask(5, new EntityAIWanderAvoidWaterFlying(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
    }

    public void setSkullStats(EntityWitherSkullShared skull, float damage, boolean invul)
    {
        super.setSkullStats(skull, damage, invul);
        skull.setMod(MoWithers.MODID);
        skull.setSkullTexture("wither/good/wither_pink");
        skull.setType(16);
        skull.setRadius(0);
    }
    
    public TextFormatting getNameColor()
    {
    	return TextFormatting.LIGHT_PURPLE;
    }
    
    public EnumWitherType getWitherType()
    {
        return EnumWitherType.FRIENDLY;
    }

	public double getMobHealth() 
	{
		return 50;
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

    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        ItemStack itemstack = player.getHeldItem(hand);

        if (!this.isTamed() && itemstack.getItem() == MItems.CHOCOLATE)
        {
            if (!this.world.isRemote && !this.isTamed())
            {
                this.setTamedBy(player);
                this.navigator.clearPath();
                this.setAttackTarget((EntityLivingBase)null);
                this.playTameEffect(true);
                this.world.setEntityState(this, (byte)7);
                this.playSound(SoundEvents.ENTITY_PLAYER_BURP, 1, 1);
            }

            return true;
        }

        return super.processInteract(player, hand);
    }
    
    protected SoundEvent getAmbientSound()
    {
        return MSounds.ENTITY_WITHER_PINK[0];
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return MSounds.ENTITY_WITHER_PINK[1];
    }

    protected SoundEvent getDeathSound()
    {
        return MSounds.ENTITY_WITHER_PINK[2];
    }   
    
    protected float getSoundPitch()
    {
      return super.getSoundPitch() + 1.0F;
    }

    /**
     * Returns true if this entity is undead.
     */
    public boolean isEntityUndead()
    {
        return false;
    }
    
    public void onWitherParticaleUpdate()
    {
        boolean flag = this.isArmored();

        for (int l = 0; l < 3; ++l)
        {
            double d10 = this.getHeadX(l);
            double d2 = this.getHeadY(l);
            double d4 = this.getHeadZ(l);
            this.world.spawnParticle(EnumParticleTypes.HEART, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D);

            for (int i = 0; i < 3; ++i)
            {
                this.world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
            }
            
            if (flag && this.world.rand.nextInt(4) == 0)
            {
                this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 1D, 0.5D, 1D);
            }
        }

        if (this.getInvulTime() > 0)
        {
            for (int i1 = 0; i1 < 3; ++i1)
            {
                this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, this.posX + this.rand.nextGaussian(), this.posY + (double)(this.rand.nextFloat() * 3.3F), this.posZ + this.rand.nextGaussian(), 0.699999988079071D, 0.699999988079071D, 0.8999999761581421D);
            }
        }
    }
    
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        

        if (!this.world.isRemote && this.isEntityAlive())
        {
            if (this.isInWater() && rand.nextInt(1200) == 0)
            {
              for (int i = 0; i < 5; ++i)
              {
            	  EntitySquid squids = new EntitySquid(world);
            	  squids.copyLocationAndAnglesFrom(this);
            	  world.spawnEntity(squids);
              }
            }
        	
            if (rand.nextInt(400) == 0)
            {
    	         MinecraftAdventures.instantGrow(world, this.getPosition(), rand);
    	         MinecraftAdventures.instantGrow(world, this.getPosition().down(), rand);
            }
        	
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(24D));

            for (Entity entity : list)
            {
                if (entity.isEntityAlive() && entity instanceof EntityAnimal && !((EntityAnimal)entity).isInLove() && !((EntityAnimal)entity).isChild() && rand.nextInt(60) == 0)
                {
                	((EntityAnimal)entity).setInLove(null);
                }
                
                if (entity.isEntityAlive() && entity instanceof IMob && entity.ticksExisted % 20 == 0)
                {
                	entity.attackEntityFrom(DamageSource.MAGIC, 4);
                }
            }
        }
    }
    
    public boolean isOnSameTeam(Entity friend)
    {
    	if (friend instanceof EntityHostileWither)
    		return ((EntityHostileWither)friend).isTamed();
    	else
    	return friend instanceof EntityIronGolem || friend instanceof EntitySnowman || (friend instanceof EntityTameable && ((EntityTameable)friend).isTamed()) || (friend instanceof EntityBaseWither && !(friend instanceof EntityHostileWither)) || (friend instanceof EntityPlayer && this.isOwner((EntityPlayer) friend)) || super.isOnSameTeam(friend);
    }

    public boolean isImmuneToExplosions()
    {
        return true;
    }
    
    @Nullable
    protected Item getDropItem()
    {
        return rand.nextBoolean() ? Items.SUGAR : Item.getItemFromBlock(Blocks.RED_FLOWER);
    }
}
