package net.endermanofdoom.mowithers.entity.wither;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

import net.endermanofdoom.mca.entity.boss.EntityHostileWither;
import net.endermanofdoom.mca.entity.projectile.EntityWitherSkullShared;
import net.endermanofdoom.mowithers.MoWithers;
import net.endermanofdoom.mowithers.registry.MSounds;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityWitherAir extends EntityHostileWither
{

	public EntityWitherAir(World worldIn) 
	{
		super(worldIn);
		this.experienceValue *= 4;
		this.noClip = true;
        this.moveHelper = new EntityWitherAir.WitherMoveHelper(this);
	}
	
	protected void initEntityAI()
    {
        this.tasks.addTask(0, new AIDoNothing());
        this.tasks.addTask(2, new EntityAIWitherShoot(this));
        this.tasks.addTask(3, new EntityWitherAir.AIRandomFly(this));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(2, new net.endermanofdoom.mca.entity.ai.EntityAINearestAttackableTargetInCube<EntityLivingBase>(this, EntityLivingBase.class, WITHERTARGETS));
        this.targetTasks.addTask(0, new EntityAIWitherOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIWitherOwnerHurtTarget(this));
    }
	
    public boolean canDestroyBlock(Block blockIn)
    {
        return false;
    }
    
    public void setSkullStats(EntityWitherSkullShared skull, float damage, boolean invul)
    {
        super.setSkullStats(skull, damage, invul);
        skull.setMod(MoWithers.MODID);
        skull.setSkullTexture("wither/element/wither_air");
        skull.setRadius(0F);
        skull.setType(4);
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(1.0D);
    }

	public double getMobHealth() 
	{
		return super.getMobHealth() * 1.5D;
	}

	public double getMobAttack() 
	{
		return super.getMobAttack() * 1.5D;
	}

	public double getMobSpeed() 
	{
		return 0.8D;
	}
    
    protected SoundEvent getAmbientSound()
    {
        return MSounds.ENTITY_WITHER_AIR[0];
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return MSounds.ENTITY_WITHER_AIR[1];
    }

    protected SoundEvent getDeathSound()
    {
        return MSounds.ENTITY_WITHER_AIR[2];
    }
    
    protected double getFlyHeight()
    {
        return 1D;
    }
    
    public void onWitherMoveUpdate()
    {
        if (!this.world.isRemote)
        {
        	if (this.getWatchedTargetId(0) > 0)
        	{
                Entity entity = this.world.getEntityByID(this.getWatchedTargetId(0));
                
              if (entity != null)
              {
            	  this.faceEntity(entity, 180F, 180F);
            	  this.getMoveHelper().setMoveTo(entity.posX, entity.posY, entity.posZ, this.getMobSpeed());
                  
            	if (!this.canEntityBeSeen(entity) && this.ticksExisted % 40 == 0)
            		((IRangedAttackMob)this).attackEntityWithRangedAttack((EntityLivingBase)entity, 0);
            	
            	if (this.world.getEntityByID(this.getWatchedTargetId(1)) == null && rand.nextInt(40) == 0)
            		this.updateWatchedTargetId(1, entity.getEntityId());
            	
            	if (this.world.getEntityByID(this.getWatchedTargetId(2)) == null && rand.nextInt(40) == 0)
            		this.updateWatchedTargetId(2, entity.getEntityId());
              }
        	}
        }
        
        if (this.motionX * this.motionX + this.motionZ * this.motionZ != 0.0D) {
            for (int i = 0; i < 2; ++i)
                this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (this.rand.nextDouble() - 0.5D), this.posY + this.getEyeHeight() + (this.rand.nextDouble() - 0.5D), this.posZ + (this.rand.nextDouble() - 0.5D), MathHelper.sin(this.rotationYawHead * 3.1415927F / 180.0F), -this.motionY * 3.1415927F / 180.0F, -MathHelper.cos(this.rotationYawHead * 3.1415927F / 180.0F), new int[0]);
            for (int i = 0; i < 4; ++i)
                this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (this.rand.nextDouble() - 0.5D), this.posY + 2 + (this.rand.nextDouble() * 4D - 2D), this.posZ + (this.rand.nextDouble() - 0.5D), MathHelper.sin(this.renderYawOffset * 3.1415927F / 180.0F), -this.motionY * 3.1415927F / 180.0F, -MathHelper.cos(this.renderYawOffset * 3.1415927F / 180.0F), new int[0]);
          this.renderYawOffset = (this.rotationYaw = (float)Math.atan2(this.motionZ, this.motionX) * 57.295776F - 90.0F);
        }
    }
    
    public void onWitherParticaleUpdate()
    {
        super.onWitherParticaleUpdate();

        for (int i = 0; i < 3; ++i)
        {
            for (int l = 0; l < 3; ++l)
            {
                double d10 = this.getHeadX(l);
                double d2 = this.getHeadY(l);
                double d4 = this.getHeadZ(l);
                this.world.spawnParticle(this.isInWater() ? EnumParticleTypes.WATER_BUBBLE : EnumParticleTypes.EXPLOSION_NORMAL, d10 + this.rand.nextGaussian() * 0.15D, d2 + this.rand.nextGaussian() * 0.15D, d4 + this.rand.nextGaussian() * 0.15D, 0.0D, 0.0D, 0.0D);
            }
        	
            this.world.spawnParticle(this.isInWater() ? EnumParticleTypes.WATER_BUBBLE : EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.1D, 0.0D);
        }
        
        if (this.ticksExisted % 80 == 0 && !this.isSilent())
        {
            this.world.playSound(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, SoundEvents.ITEM_ELYTRA_FLYING, this.getSoundCategory(), 2F, 1F, false);
        }
    }
    
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
    	this.noClip = true;

        if (!this.world.isRemote && this.getWitherHealth() > 0F)
        {
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(24D * this.world.getDifficulty().getDifficultyId()));

            for (Entity entity : list)
            {
                if (entity.isEntityAlive() && !this.isOnSameTeam(entity) && (!entity.getIsInvulnerable() || (entity instanceof EntityPlayer && !((EntityPlayer)entity).capabilities.disableDamage)) && (entity.canBePushed() || !(entity instanceof EntityLivingBase)) && !(entity instanceof EntityWitherSkullShared))
                {
                	double force = 4D;
                	if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isSneaking())
                		force = 0.5D;
                	entity.extinguish();
                    double d2 = this.posX - entity.posX;
                    double d3 = (this.posY + 2) - entity.posY;
                    double d4 = this.posZ - entity.posZ;
                    double d5 = d2 * d2 + d3 * d3 + d4 * d4;
                    entity.addVelocity(d2 / d5 * force, d3 / d5 * force, d4 / d5 * force);
                    if (entity instanceof EntityLivingBase && this.getDistance(entity) < 4)
                	{
                    	entity.attackEntityFrom(new EntityDamageSource("inWall", this).setDamageBypassesArmor(), 1F);
                    	if (entity instanceof EntityCreeper)
                    	{
                    		((EntityCreeper)entity).ignite();
                    		((EntityCreeper)entity).setEntityInvulnerable(true);
                    	}
                	}
                }
            }
            
            if (this.isTamed() && this.getOwner() != null && this.getWatchedTargetId(0) == 0 && this.getDistance(this.getOwner()) > 8D)
            {
            	this.getMoveHelper().setMoveTo(getOwner().posX, getOwner().posY, getOwner().posZ, 1F);
            }
        }
    }

    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    public boolean canBePushed()
    {
        return false;
    }
    
    public void travel(float strafe, float vertical, float forward)
    {
        float f = 0.91F;
        
        if (this.isArmored()) 
        {
            f *= 0.75F;
        }
        
        if (this.world.getBlockState(getPosition()).isFullCube() && this.world.getBlockState(getPosition().up()).isFullCube() && this.world.getBlockState(getPosition().up(2)).isFullCube() && this.world.getBlockState(getPosition().up(3)).isFullCube()) 
        {
            f *= 0.66F;
        }
        
        this.moveRelative(strafe, vertical, forward, 0.02F);
        this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        this.motionX *= (double)f;
        this.motionY *= (double)f;
        this.motionZ *= (double)f;
    }

    /**
     * Returns true if this entity should move as if it were on a ladder (either because it's actually on a ladder, or
     * for AI reasons)
     */
    public boolean isOnLadder()
    {
        return false;
    }
    
    protected void onDeathUpdate()
    {
    	if (this.isTamed())
    		super.onDeathUpdate();
    	else if (!this.isEntityAlive())
    	{
    		this.deathTicks = 2;
            if (!this.world.isRemote && this.canDropLoot() && this.world.getGameRules().getBoolean("doMobLoot"))
            {
            	for (EntityPlayer entityplayer : world.playerEntities)
					entityplayer.addExperience(experienceValue);
                this.dropLoot(true, 0, getLastDamageSource());
            }
            
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(24D * this.world.getDifficulty().getDifficultyId()));

            for (Entity entity : list)
            {
                if (!entity.getIsInvulnerable())
                {
                	double force = -12D;
                	entity.extinguish();
                    double d2 = this.posX - entity.posX;
                    double d3 = (this.posY + 2) - entity.posY;
                    double d4 = this.posZ - entity.posZ;
                    double d5 = d2 * d2 + d3 * d3 + d4 * d4;
                    entity.addVelocity(d2 / d5 * force, d3 / d5 * force, d4 / d5 * force);
                }
            }

            this.setDead();
    	}
    }
    
    @Nullable
    protected Item getDropItem()
    {
        return rand.nextBoolean() ? Items.PAPER : Items.FEATHER;
    }
    
	public int[] getBarColor() 
	{
		return new int[] {255, 255, 255, 0, this.isArmored() || this.isSuperBoss() ? 122 : 0, this.isArmored() || this.isSuperBoss() ? 255 : 0};
	}

    static class AIRandomFly extends EntityAIBase
        {
            private final EntityWitherAir parentEntity;

            public AIRandomFly(EntityWitherAir air)
            {
                this.parentEntity = air;
                this.setMutexBits(1);
            }

            /**
             * Returns whether the EntityAIBase should begin execution.
             */
            public boolean shouldExecute()
            {
                EntityMoveHelper entitymovehelper = this.parentEntity.getMoveHelper();

                if (this.parentEntity.getRNG().nextInt(40) != 0)
                {
                    return false;
                }
                
                if (!entitymovehelper.isUpdating())
                {
                    return true;
                }
                else
                {
                    double d0 = entitymovehelper.getX() - this.parentEntity.posX;
                    double d1 = entitymovehelper.getY() - this.parentEntity.posY;
                    double d2 = entitymovehelper.getZ() - this.parentEntity.posZ;
                    double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                    return d3 < 9D || d3 > 10000D;
                }
            }

            /**
             * Returns whether an in-progress EntityAIBase should continue executing
             */
            public boolean shouldContinueExecuting()
            {
                return false;
            }

            /**
             * Execute a one shot task or start executing a continuous task
             */
            public void startExecuting()
            {
                Random random = this.parentEntity.getRNG();
                
                double d0 = this.parentEntity.posX + (double)((random.nextFloat() * 2.0F - 1.0F) * 48F);
                double d1 = this.parentEntity.posY + (double)((random.nextFloat() * 2.0F - 1.0F) * 24F);
                double d2 = this.parentEntity.posZ + (double)((random.nextFloat() * 2.0F - 1.0F) * 48F);
                
                this.parentEntity.getMoveHelper().setMoveTo(d0, d1, d2, parentEntity.getMobSpeed() * 0.75D);
            }
        }
    
    static class WitherMoveHelper extends EntityMoveHelper
    {
        private final EntityWitherAir parentEntity;
        public WitherMoveHelper(EntityWitherAir air)
        {
            super(air);
            this.parentEntity = air;
        }

        public void onUpdateMoveHelper()
        {
            if (this.action == EntityMoveHelper.Action.MOVE_TO)
            {
                double d0 = this.posX - this.parentEntity.posX;
                double d1 = this.posY - this.parentEntity.posY;
                double d2 = this.posZ - this.parentEntity.posZ;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;

                d3 = (double)MathHelper.sqrt(d3);

                if (d3 > 9D)
                {
                    this.parentEntity.motionX += d0 / d3 * speed;
                    this.parentEntity.motionY += d1 / d3 * speed;
                    this.parentEntity.motionZ += d2 / d3 * speed;
                    parentEntity.setSneaking(true);
                }
                else
                	parentEntity.setSneaking(false);
            }
        }
    }
}
