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
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class EntityWitherEnder extends EntityHostileWither
{
	public EntityWitherEnder(World worldIn) 
	{
		super(worldIn);
		this.experienceValue *= 12;
		this.isImmuneToFire = false;
        this.setPathPriority(PathNodeType.WATER, -1.0F);
	}
	
	protected void initEntityAI()
    {
		WITHERTARGETS = new Predicate<Entity>()
	    {
	        public boolean apply(@Nullable Entity p_apply_1_)
	        {
	            return p_apply_1_ instanceof EntityLivingBase && ((EntityLivingBase)p_apply_1_).attackable() && !(p_apply_1_ instanceof EntityEnderman) && !(p_apply_1_ instanceof EntityDragon) && !(p_apply_1_ instanceof EntityShulker) && !(p_apply_1_ instanceof EntityWitherEnder);
	        }
	    };
        super.initEntityAI();
        this.targetTasks.addTask(2, new EntityAIWitherTargeting<EntityLivingBase>(this, EntityLivingBase.class, WITHERTARGETS));
    }
    
    public void setSkullStats(EntityWitherSkullShared skull, float damage, boolean invul)
    {
        super.setSkullStats(skull, damage, invul);
        skull.setRadius(1F);
        skull.setType(14);
        EntityEnderman mob = new EntityEnderman(world);
		mob.copyLocationAndAnglesFrom(this);
		mob.onInitialSpawn(world.getDifficultyForLocation(getPosition()), null);
		world.spawnEntity(mob);
		mob.startRiding(skull);
		mob.targetTasks.addTask(2, new EntityAIWitherTargeting<EntityLivingBase>(mob, EntityLivingBase.class, WITHERTARGETS));
        skull.setSkullTexture("wither/mob/wither_ender");
        skull.setMod(MoWithers.MODID);
    }
    
    public TextFormatting getNameColor()
    {
    	return TextFormatting.LIGHT_PURPLE;
    }
    
    public EnumWitherType getWitherType()
    {
        return EnumWitherType.MOB;
    }

	public double getMobHealth() 
	{
		return super.getMobHealth() * 6D;
	}

	public double getMobAttack() 
	{
		return super.getMobAttack() * 2.5D;
	}

	public double getMobSpeed() 
	{
		return 0.7D;
	}
    
    public boolean canAttackClass(Class <? extends EntityLivingBase > cls)
    {
        return super.canAttackClass(cls) && cls != EntityEnderman.class && cls != EntityDragon.class;
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEFINED;
    }
    
    protected SoundEvent getAmbientSound()
    {
    	this.playSound(SoundEvents.ENTITY_WITHER_AMBIENT, getSoundVolume(), getSoundPitch() + 0.4F);
        return SoundEvents.ENTITY_ENDERMEN_SCREAM;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
    	this.playSound(SoundEvents.ENTITY_WITHER_HURT, getSoundVolume(), getSoundPitch() + 0.4F);
        return SoundEvents.ENTITY_ENDERMEN_HURT;
    }

    protected SoundEvent getDeathSound()
    {
    	this.playSound(SoundEvents.ENTITY_WITHER_DEATH, getSoundVolume(), getSoundPitch() + 0.4F);
        return SoundEvents.ENTITY_ENDERMEN_DEATH;
    }
    
    public float getWitherScale()
    {
    	return super.getWitherScale() * 1.5F;
    }

    /**
     * Returns true if this entity is undead.
     */
    public boolean isEntityUndead()
    {
        return false;
    }
    
    public void ignite()
    {
        this.setInvulTime(660);
        this.setHealth(this.getMaxHealth() / 3.0F);
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
        	EntityEnderman mob = new EntityEnderman(world);
    		mob.copyLocationAndAnglesFrom(this);
    		mob.onInitialSpawn(world.getDifficultyForLocation(getPosition()), null);
    		world.spawnEntity(mob);
    		
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(200D));

            for (Entity entity : list)
            {
                if (entity.isEntityAlive() && entity instanceof EntityEnderman)
                {
                	EntityEnderman creepers = (EntityEnderman)entity;
                	
                	EntityAIWitherTargeting<EntityLivingBase> targeting = new EntityAIWitherTargeting<EntityLivingBase>(mob, EntityLivingBase.class, WITHERTARGETS);
                	
                	if (creepers.getAttackTarget() != null && creepers.getAttackTarget() == this)
                		creepers.setAttackTarget(null);
                	
                	if (!creepers.targetTasks.taskEntries.contains(targeting))
                		creepers.targetTasks.addTask(2, targeting);
                }
            }
            
            if (getAttackTarget() != null && this.getDistance(getAttackTarget()) > 24)
            	this.teleportToEntity(getAttackTarget());
        }
        
        if (this.isWet())
        {
            this.attackEntityFrom(DamageSource.DROWN, 4F);
        }
    }
    
    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (this.isEntityInvulnerable(source))
        {
            return false;
        }
        else if (source instanceof EntityDamageSourceIndirect && !this.isArmored())
        {
            for (int i = 0; i < 64; ++i)
            {
                if (this.teleportRandomly())
                {
                    return true;
                }
            }

            return false;
        }
        else
        {
            boolean flag = super.attackEntityFrom(source, amount);

            if (source.isUnblockable() && this.rand.nextInt(10) != 0)
            {
                this.teleportRandomly();
            }

            return flag;
        }
    }
    
    public void onHealUpdate()
    {
        if (!this.isWet())
        	super.onHealUpdate();
    }    
    
    /**
     * Teleport the enderman to a random nearby position
     */
    protected boolean teleportRandomly()
    {
        double d0 = this.posX + (this.rand.nextDouble() - 0.5D) * 64.0D;
        double d1 = this.posY + (double)(this.rand.nextInt(64) - 32);
        double d2 = this.posZ + (this.rand.nextDouble() - 0.5D) * 64.0D;
        return this.teleportTo(d0, d1, d2);
    }

    /**
     * Teleport the enderman to another entity
     */
    protected boolean teleportToEntity(Entity p_70816_1_)
    {
        Vec3d vec3d = new Vec3d(this.posX - p_70816_1_.posX, this.getEntityBoundingBox().minY + (double)(this.height / 2.0F) - p_70816_1_.posY + (double)p_70816_1_.getEyeHeight(), this.posZ - p_70816_1_.posZ);
        vec3d = vec3d.normalize();
        double d0 = 16.0D;
        double d1 = this.posX + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3d.x * d0;
        double d2 = this.posY + (double)(this.rand.nextInt(16) - 8) - vec3d.y * d0;
        double d3 = this.posZ + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3d.z * d0;
        return this.teleportTo(d1, d2, d3);
    }

    /**
     * Teleport the enderman
     */
    private boolean teleportTo(double x, double y, double z)
    {
        net.minecraftforge.event.entity.living.EnderTeleportEvent event = new net.minecraftforge.event.entity.living.EnderTeleportEvent(this, x, y, z, 0);
        if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) return false;
        boolean flag = this.attemptTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ());

        if (flag)
        {
            this.world.playSound((EntityPlayer)null, this.prevPosX, this.prevPosY, this.prevPosZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, this.getSoundCategory(), 1.0F, 1.0F);
            this.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
            this.switchhoverposCounter = 200;
        }

        return flag;
    }
    
    protected boolean hitType(DamageSource source, Entity entity)
    {
    	return super.hitType(source, entity) || entity instanceof EntityEnderman || entity instanceof EntityDragon;
    }
    
    protected float getSoundPitch()
    {
      return super.getSoundPitch() - 0.25F;
    }
    
    @Nullable
    protected Item getDropItem()
    {
        return rand.nextBoolean() ? Items.ENDER_EYE : Items.ENDER_PEARL;
    }
    
    public boolean isOnSameTeam(Entity friend)
    {
    	if (friend instanceof EntityEnderman || friend instanceof EntityDragon)
    		return true;
    	
    	return super.isOnSameTeam(friend);
    }
}
