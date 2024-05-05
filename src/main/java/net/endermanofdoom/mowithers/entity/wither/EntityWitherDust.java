package net.endermanofdoom.mowithers.entity.wither;

import net.endermanofdoom.mca.entity.boss.EntityHostileWither;
import net.endermanofdoom.mca.entity.EnumWitherType;
import net.endermanofdoom.mca.entity.projectile.EntityWitherSkullShared;
import java.util.List;

import javax.annotation.Nullable;

import net.endermanofdoom.mowithers.MoWithers;
import net.endermanofdoom.mowithers.registry.MSounds;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class EntityWitherDust extends EntityHostileWither
{

	public EntityWitherDust(World worldIn) 
	{
		super(worldIn);
		this.experienceValue *= 5;
	}
	
	protected void initEntityAI()
    {
        super.initEntityAI();
        this.targetTasks.addTask(2, new net.endermanofdoom.mca.entity.ai.EntityAINearestAttackableTargetInCube<EntityLivingBase>(this, EntityLivingBase.class, WITHERTARGETS));
    }
    
    public void setSkullStats(EntityWitherSkullShared skull, float damage, boolean invul)
    {
        super.setSkullStats(skull, damage, invul);
        skull.setRadius(0F);
        skull.setType(7);
        skull.setSkullTexture("wither/wither_eyes");
        skull.setMod(MoWithers.MODID);
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
    }
    
    public TextFormatting getNameColor()
    {
    	return TextFormatting.YELLOW;
    }
    
    public EnumWitherType getWitherType()
    {
        return EnumWitherType.BLOCK_SOFT;
    }

	public double getMobHealth() 
	{
		return super.getMobHealth() * 2.25D;
	}

	public double getMobAttack() 
	{
		return super.getMobAttack() * 1.25D;
	}

	public double getMobSpeed() 
	{
		return 0.65D;
	}
    
    protected SoundEvent getAmbientSound()
    {
        return MSounds.ENTITY_WITHER_DUST[0];
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return MSounds.ENTITY_WITHER_DUST[1];
    }

    protected SoundEvent getDeathSound()
    {
        return MSounds.ENTITY_WITHER_DUST[2];
    }
    
    protected double getFlyHeight()
    {
        return this.getAttackTarget() == null ? 0D : 5D;
    }
    
    public void onWitherParticaleUpdate()
    {
        super.onWitherParticaleUpdate();

        for (int l = 0; l < 3; ++l)
        {
            double d10 = this.getHeadX(l);
            double d2 = this.getHeadY(l);
            double d4 = this.getHeadZ(l);
            this.world.spawnParticle(EnumParticleTypes.BLOCK_DUST, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.0D, 0.1D, 0.0D, Block.getStateId(Blocks.SAND.getDefaultState()));
        }

        for (int i = 0; i < 6; ++i)
        {
            this.world.spawnParticle(EnumParticleTypes.BLOCK_DUST, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.1D, 0.0D, Block.getStateId(Blocks.SAND.getDefaultState()));
        }
    }
    
    public void onWitherMoveUpdate()
    {
        this.motionY *= 0.6000000238418579D;
        
        if (!this.world.isRemote)
        {
        	if (this.posY < 0)
   	          this.motionY += (0.5D - this.motionY) * getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue();

      	  for (int i = 0; i < 8; i++)
            {
      		  int in = MathHelper.floor(this.posX - 1.5D + this.rand.nextDouble() * 3.0D);
      		  int j = MathHelper.floor(this.posY + height - this.rand.nextDouble() * (this.isArmored() ? height : height + getFlyHeight()));
      		  int k = MathHelper.floor(this.posZ - 1.5D + this.rand.nextDouble() * 3.0D);
      		  BlockPos blockpos = new BlockPos(in, j, k);
      		  IBlockState iblockstate = this.world.getBlockState(blockpos);
      		  if (!this.world.isRemote && iblockstate.isFullCube())
     	         {
     	          if (this.motionY < 0.0D)
     	            this.motionY = 0.0D;
     	          
     	          this.motionY += (0.5D - this.motionY) * getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue();
              }
            }
          
        	if (this.getWatchedTargetId(0) > 0)
        	{
                Entity entity = this.world.getEntityByID(this.getWatchedTargetId(0));
        		
            if (entity != null)
            {
            	if (this.world.getEntityByID(this.getWatchedTargetId(1)) == null && rand.nextInt(40) == 0)
            		this.updateWatchedTargetId(1, entity.getEntityId());
            	
            	if (this.world.getEntityByID(this.getWatchedTargetId(2)) == null && rand.nextInt(40) == 0)
            		this.updateWatchedTargetId(2, entity.getEntityId());
            	
            	if (!this.canEntityBeSeen(entity) && this.ticksExisted % 100 == 0)
            		((IRangedAttackMob)this).attackEntityWithRangedAttack((EntityLivingBase)entity, 0);
            	
                double d0 = entity.posX - this.posX;
                double d1 = entity.posZ - this.posZ;
                double d3 = d0 * d0 + d1 * d1;

                if (d3 > 9.0D)
                {
                    double d5 = (double)MathHelper.sqrt(d3);
                    this.motionX += (d0 / d5 * 0.5D - this.motionX) * getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue();
                    this.motionZ += (d1 / d5 * 0.5D - this.motionZ) * getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue();
                }
            }
        	}
        }

        if (this.motionX * this.motionX + this.motionZ * this.motionZ != 0D)
        {
            this.rotationYaw = (float)MathHelper.atan2(this.motionZ, this.motionX) * (180F / (float)Math.PI) - 90.0F;
        }
    }
    
    public void onHealUpdate()
    {
        if (!this.isWet())
        	super.onHealUpdate();
    }
    
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        
        if (!this.world.isRemote && this.getHealth() > 0F)
        {
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(16D * this.world.getDifficulty().getDifficultyId()));

            for (Entity entity : list)
            {
                if (entity.isEntityAlive() && (!entity.getIsInvulnerable() || (entity instanceof EntityPlayer && !((EntityPlayer)entity).capabilities.disableDamage)) && (entity.canBePushed() || !(entity instanceof EntityLivingBase)) && !(entity instanceof EntityWitherSkullShared))
                {
                	double force = 1D;
                	if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isSneaking())
                		force = 0.25D;
                	entity.extinguish();
                    double d2 = this.posX - entity.posX;
                    double d3 = (this.posY + 2) - entity.posY;
                    double d4 = this.posZ - entity.posZ;
                    double d5 = d2 * d2 + d3 * d3 + d4 * d4;
                    entity.addVelocity(d2 / d5 * force, d3 / d5 * force, d4 / d5 * force);
                    if (entity instanceof EntityLivingBase && this.getDistance(entity) < 4)
                	{
                    	entity.attackEntityFrom(new EntityDamageSource("fallingBlock", this), 1F);
                	}
                }
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
    
    protected float getSoundPitch()
    {
      return super.getSoundPitch() * 0.75F;
    }
    
    @Nullable
    protected Item getDropItem()
    {
        return rand.nextBoolean() ? Items.STRING : Items.BRICK;
    }
    
	public int[] getBarColor() 
	{
		return new int[] {100, 200, 150, 0, this.isArmored() || this.isSuperBoss() ? 122 : 0, this.isArmored() || this.isSuperBoss() ? 255 : 0};
	}
}
