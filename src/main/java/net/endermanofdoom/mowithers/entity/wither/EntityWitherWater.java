package net.endermanofdoom.mowithers.entity.wither;
import net.endermanofdoom.mca.entity.boss.EntityHostileWither;
import net.endermanofdoom.mca.entity.projectile.EntityWitherSkullShared;
import javax.annotation.Nullable;

import net.endermanofdoom.mowithers.MoWithers;
import net.endermanofdoom.mowithers.registry.MSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class EntityWitherWater extends EntityHostileWither
{
	public EntityWitherWater(World worldIn) 
	{
		super(worldIn);
		this.isImmuneToFire = false;
		this.experienceValue *= 2;
        this.tasks.removeTask(new EntityAISwimming(this));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void initEntityAI()
    {
        this.tasks.addTask(3, new EntityAIWitherShoot(this));
        this.tasks.addTask(4, new EntiyAIWitherFollowOwner(this, 1.0D, 32.0F, 2.0F));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(0, new EntityAIWitherOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIWitherOwnerHurtTarget(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(2, new EntityAIWitherTargeting(this, EntityLivingBase.class, WITHERTARGETS));
    }
	
    public boolean canDestroyBlock(BlockPos blockIn)
    {
        return super.canDestroyBlock(blockIn) && world.getBlockState(blockIn).getBlock() != Blocks.WATER && world.getBlockState(blockIn).getBlock() != Blocks.FLOWING_WATER;
    }
    
    public void setSkullStats(EntityWitherSkullShared skull, float damage, boolean invul)
    {
        super.setSkullStats(skull, damage, invul);
        skull.setRadius(2F);
        skull.setType(3);
        skull.setPlacedBlockState(invul ? Blocks.WATER.getDefaultState() : Blocks.FLOWING_WATER.getDefaultState());
        skull.setSkullTexture("wither/element/wither_water");
        skull.setMod(MoWithers.MODID);
    }
    
    public boolean isPushedByWater()
    {
      return false;
    }    
    
    protected float getWaterSlowDown()
    {
        return 0.98F;
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
    }

	public double getMobHealth() 
	{
		return super.getMobHealth() * 2;
	}
    
    public TextFormatting getNameColor()
    {
    	return TextFormatting.BLUE;
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
        return MSounds.ENTITY_WITHER_WATER[0];
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return MSounds.ENTITY_WITHER_WATER[1];
    }

    protected SoundEvent getDeathSound()
    {
        return MSounds.ENTITY_WITHER_WATER[2];
    }
    
    protected double getFlyHeight()
    {
        return 7D;
    }
    
    public void onWitherParticaleUpdate()
    {
        super.onWitherParticaleUpdate();

        for (int l = 0; l < 3; ++l)
        {
            double d10 = this.getHeadX(l);
            double d2 = this.getHeadY(l);
            double d4 = this.getHeadZ(l);
            this.world.spawnParticle(EnumParticleTypes.WATER_WAKE, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, motionX, 0.0D, motionZ);
            this.world.spawnParticle(EnumParticleTypes.WATER_WAKE, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, motionX, 0.0D, motionZ);
            this.world.spawnParticle(EnumParticleTypes.WATER_WAKE, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, motionX, 0.0D, motionZ);
        }

        for (int i = 0; i < 12; ++i)
        {
            this.world.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
        }
    }
    
    public void onHealUpdate()
    {
        BlockPos blockpos = new BlockPos(MathHelper.floor(this.posX), 0, MathHelper.floor(this.posZ));
        Biome biome = this.world.getBiome(blockpos);
        
        if (this.isInWater())
        {
        	this.heal(2D);
        }
        else
        {
            if (biome.canRain() && this.isWet())
            	this.heal(1D);
            
            if (biome.getTemperature(blockpos) < 1F && biome.canRain())
            	super.onHealUpdate();
            
            if (biome.getTemperature(blockpos) >= 1.25F && !biome.canRain())
            {
            	this.attackEntityFrom(DamageSource.STARVE, 0.25F);

                for (int l = 0; l < 3; ++l)
                {
                    double d10 = this.getHeadX(l);
                    double d2 = this.getHeadY(l);
                    double d4 = this.getHeadZ(l);
                    this.world.spawnParticle(EnumParticleTypes.CLOUD, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D);
                }

                for (int i = 0; i < 6; ++i)
                {
                    this.world.spawnParticle(EnumParticleTypes.CLOUD, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }
    
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        
        if (this.isBurning() || this.isInLava())
        {
          this.world.playSound(this.posX + 0.5D, this.posY + 2.0D, this.posZ + 0.5D, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, this.getSoundCategory(), 0.5F, 2.6F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.8F, false);
          attackEntityFrom(DamageSource.STARVE, 1F);
        }
    }
    
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (source == DamageSource.DROWN)
        {
          this.heal((double)amount);
          return false;
        }
        else
        	return super.attackEntityFrom(source, amount);
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
            	this.dropXP(posX, posY + this.getEyeHeight(), posZ, this.getExperiencePoints(this.attackingPlayer));
                this.dropLoot(true, 0, getLastDamageSource());
            }
            
            for (int l1 = 0; l1 < height; l1++) 
            {
                this.world.setBlockState(new BlockPos(this.posX, this.posY + l1, this.posZ), Blocks.WATER.getDefaultState());
            }
            for (int l = 0; l < 3; ++l)
            {
                double d10 = this.getHeadX(l);
                double d2 = this.getHeadY(l);
                double d4 = this.getHeadZ(l);
                this.world.setBlockState(new BlockPos(d10, d2, d4), Blocks.WATER.getDefaultState());
            }    

            this.setDead();
    	}
    }
    
    @Nullable
    protected Item getDropItem()
    {
        return rand.nextInt(4) == 0 ? Item.getItemFromBlock(Blocks.SPONGE) : Items.FISH;
    }
    
    public boolean isOnSameTeam(Entity friend)
    {
    	if (friend instanceof EntityWaterMob || friend instanceof EntityGuardian)
    		return true;
    	
    	return super.isOnSameTeam(friend);
    }
}
