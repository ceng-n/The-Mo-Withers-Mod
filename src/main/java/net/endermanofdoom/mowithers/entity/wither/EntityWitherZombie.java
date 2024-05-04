package net.endermanofdoom.mowithers.entity.wither;
import net.endermanofdoom.mca.entity.boss.EntityHostileWither;
import net.endermanofdoom.mca.entity.EnumWitherType;
import net.endermanofdoom.mca.entity.projectile.EntityWitherSkullShared;
import net.endermanofdoom.mowithers.MoWithers;

import java.util.List;
import javax.annotation.Nullable;
import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class EntityWitherZombie extends EntityHostileWither
{
	public EntityWitherZombie(World worldIn) 
	{
		super(worldIn);
		this.experienceValue *= 3;
		this.isImmuneToFire = false;
	}
	
	protected void initEntityAI()
    {
		WITHERTARGETS = new Predicate<Entity>()
	    {
	        public boolean apply(@Nullable Entity p_apply_1_)
	        {
	            return p_apply_1_ instanceof EntityLivingBase && ((EntityLivingBase)p_apply_1_).attackable() && !(p_apply_1_ instanceof EntityZombie) && !(p_apply_1_ instanceof EntityWitherZombie);
	        }
	    };
        super.initEntityAI();
        this.targetTasks.addTask(2, new net.endermanofdoom.mca.entity.ai.EntityAINearestAttackableTargetInCube<EntityLivingBase>(this, EntityLivingBase.class, WITHERTARGETS));
    }
    
    public void setSkullStats(EntityWitherSkullShared skull, float damage, boolean invul)
    {
        super.setSkullStats(skull, damage, invul);
        skull.setRadius(1F);
        skull.setType(13);
        skull.setBurn(this.isBurning());
        EntityZombie mob = new EntityZombie(world);
		mob.copyLocationAndAnglesFrom(this);
		mob.onInitialSpawn(world.getDifficultyForLocation(getPosition()), null);
		world.spawnEntity(mob);
		mob.startRiding(skull);
		mob.setChild(true);
		mob.targetTasks.addTask(2, new net.endermanofdoom.mca.entity.ai.EntityAINearestAttackableTargetInCube<EntityLivingBase>(mob, EntityLivingBase.class, WITHERTARGETS));
        skull.setSkullTexture("wither/mob/wither_zombie");
        skull.setMod(MoWithers.MODID);
    }
    
    public TextFormatting getNameColor()
    {
    	return TextFormatting.GRAY;
    }
    
    public EnumWitherType getWitherType()
    {
        return EnumWitherType.MOB;
    }

	public double getMobHealth() 
	{
		return super.getMobHealth() * 2.5D;
	}

	public double getMobAttack() 
	{
		return super.getMobAttack() * 1.5D;
	}

	public double getMobSpeed() 
	{
		return 0.6D;
	}
    
    protected SoundEvent getAmbientSound()
    {
    	this.playSound(SoundEvents.ENTITY_WITHER_AMBIENT, getSoundVolume(), getSoundPitch() + 0.4F);
        return SoundEvents.ENTITY_ZOMBIE_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
    	this.playSound(SoundEvents.ENTITY_WITHER_HURT, getSoundVolume(), getSoundPitch() + 0.4F);
        return SoundEvents.ENTITY_ZOMBIE_HURT;
    }

    protected SoundEvent getDeathSound()
    {
    	this.playSound(SoundEvents.ENTITY_WITHER_DEATH, getSoundVolume(), getSoundPitch() + 0.4F);
        return SoundEvents.ENTITY_ZOMBIE_DEATH;
    }
    
    protected double getFlyHeight()
    {
        return 7D;
    }
    
    @SuppressWarnings("unlikely-arg-type")
	public void onLivingUpdate()
    {
        super.onLivingUpdate();
        
        if (!this.world.isRemote && this.rand.nextInt(80) == 0)
        {
        	EntityZombie mob = new EntityZombie(world);
    		mob.copyLocationAndAnglesFrom(this);
    		mob.onInitialSpawn(world.getDifficultyForLocation(getPosition()), null);
    		world.spawnEntity(mob);
    		
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(200D));

            for (Entity entity : list)
            {
                if (entity.isEntityAlive() && entity instanceof EntityZombie)
                {
                	EntityZombie creepers = (EntityZombie)entity;
                	
                	net.endermanofdoom.mca.entity.ai.EntityAINearestAttackableTargetInCube<EntityLivingBase> targeting = new net.endermanofdoom.mca.entity.ai.EntityAINearestAttackableTargetInCube<EntityLivingBase>(mob, EntityLivingBase.class, WITHERTARGETS);
                	
                	if (creepers.getAttackTarget() != null && creepers.getAttackTarget() == this)
                		creepers.setAttackTarget(null);
                	
                	if (!creepers.targetTasks.taskEntries.contains(targeting))
                		creepers.targetTasks.addTask(2, targeting);
                }
            }
        }
        
        if (this.world.isDaytime() && !this.world.isRemote && !this.isChild())
        {
            float f = this.getBrightness();

            if (f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.world.canSeeSky(new BlockPos(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ)))
            {
                this.setFire(8);
            }
        }
    }
    
    public void onHealUpdate()
    {
        if (!this.isBurning())
        	super.onHealUpdate();
    }
    
    protected boolean hitType(DamageSource source, Entity entity)
    {
    	return super.hitType(source, entity) || entity instanceof EntityZombie;
    }
    
    protected float getSoundPitch()
    {
      return super.getSoundPitch() - 0.4F;
    }
    
    @Nullable
    protected Item getDropItem()
    {
        return rand.nextBoolean() ? Items.IRON_INGOT : Items.ROTTEN_FLESH;
    }
    
    public boolean isOnSameTeam(Entity friend)
    {
    	if (friend instanceof EntityZombie)
    		return true;
    	
    	return super.isOnSameTeam(friend);
    }
}
