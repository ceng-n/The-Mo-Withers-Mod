package net.endermanofdoom.mowithers.entity.wither;
import net.endermanofdoom.mca.entity.boss.EntityHostileWither;
import net.endermanofdoom.mca.entity.projectile.EntityWitherSkullShared;
import java.util.List;

import javax.annotation.Nullable;

import net.endermanofdoom.mowithers.MoWithers;
import net.endermanofdoom.mowithers.registry.MSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class EntityWitherSteam extends EntityHostileWither
{

	public EntityWitherSteam(World worldIn) 
	{
		super(worldIn);
		this.experienceValue *= 3;
	}
	
	protected void initEntityAI()
    {
        super.initEntityAI();
        this.targetTasks.addTask(2, new net.endermanofdoom.mca.entity.ai.EntityAINearestAttackableTargetInCube<EntityLivingBase>(this, EntityLivingBase.class, WITHERTARGETS));
    }
    
    public void setSkullStats(EntityWitherSkullShared skull, float damage, boolean invul)
    {
        super.setSkullStats(skull, damage, invul);
        skull.setRadius(1F);
        skull.setType(8);
        skull.setSkullTexture("wither/wither_eyes");
        skull.setMod(MoWithers.MODID);
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
    }

	public double getMobHealth() 
	{
		return super.getMobHealth() * 2.5D;
	}

	public double getMobAttack() 
	{
		return super.getMobAttack() * 2D;
	}

	public double getMobSpeed() 
	{
		return 0.65D;
	}
    
    protected SoundEvent getAmbientSound()
    {
        return MSounds.ENTITY_WITHER_STEAM[0];
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return MSounds.ENTITY_WITHER_STEAM[1];
    }

    protected SoundEvent getDeathSound()
    {
        return MSounds.ENTITY_WITHER_STEAM[2];
    }
    
    protected double getFlyHeight()
    {
        return 8D;
    }
    
    public void onWitherParticaleUpdate()
    {
        super.onWitherParticaleUpdate();
        
        BlockPos blockpos = new BlockPos(MathHelper.floor(this.posX), 0, MathHelper.floor(this.posZ));
        Biome biome = this.world.getBiome(blockpos);

        if (biome.getTemperature(blockpos) > 0.5F && !this.isWet())
        {
            for (int l = 0; l < 3; ++l)
            {
                double d10 = this.getHeadX(l);
                double d2 = this.getHeadY(l);
                double d4 = this.getHeadZ(l);
                this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 1D, 1D, 1D);
                this.world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.0D, 0.25D, 0.0D);
                this.world.spawnParticle(EnumParticleTypes.CLOUD, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.0D, 0.25D, 0.0D);
            }

            for (int i = 0; i < 6; ++i)
            {
                this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 1D, 1D, 1D);
                this.world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.25D, 0.0D);
                this.world.spawnParticle(EnumParticleTypes.CLOUD, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.25D, 0.0D);
            }
            
            if (this.rand.nextInt(16) == 0 && !this.isSilent())
            {
                this.world.playSound(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, SoundEvents.BLOCK_FIRE_EXTINGUISH, this.getSoundCategory(), 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, false);
            }
        }
    }
    
    public void onHealUpdate()
    {
        BlockPos blockpos = new BlockPos(MathHelper.floor(this.posX), 0, MathHelper.floor(this.posZ));
        Biome biome = this.world.getBiome(blockpos);
        
        if (this.ticksExisted % 5 == 0 && biome.getTemperature(blockpos) > 1F)
        	this.heal(1D);
        
        if (biome.getTemperature(blockpos) > 0.75F && !this.isWet())
        	super.onHealUpdate();
        
        if (biome.getTemperature(blockpos) <= 0.5F || this.isWet())
        {
        	this.attackEntityFrom(DamageSource.STARVE, 2);
        	for (int i = 0; i < 6; ++i)
            this.world.spawnParticle(EnumParticleTypes.DRIP_WATER, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.25D, 0.0D);
            this.world.playSound(this.posX + 0.5D, this.posY + this.rand.nextFloat() * height, this.posZ + 0.5D, SoundEvents.WEATHER_RAIN, this.getSoundCategory(), 1.0F, this.rand.nextFloat() * 1.7F + 0.3F, false);
        
            if (this.isArmored() && !this.world.isRemote && !this.isTamed())
            {
            	EntityWitherWater condensation = new EntityWitherWater(world);
            	world.removeEntity(this);
            	condensation.copyLocationAndAnglesFrom(this);
            	world.spawnEntity(condensation);
                this.world.playSound(this.posX + 0.5D, this.posY + 2.0D, this.posZ + 0.5D, SoundEvents.ENTITY_GENERIC_SPLASH, this.getSoundCategory(), 3F, 1F, false);
            }
        }
    }
    
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        
        if (this.isInWater())
        {
          this.world.playSound(this.posX + 0.5D, this.posY + 2.0D, this.posZ + 0.5D, SoundEvents.BLOCK_WATER_AMBIENT, this.getSoundCategory(), 0.5F, 2.6F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.8F, false);
          attackEntityFrom(DamageSource.STARVE, 5.0F);
        }

        if (!this.world.isRemote)
        {
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(8D));

            for (Entity entity : list)
            {
                if (entity.isEntityAlive() && !entity.getIsInvulnerable() && !entity.isImmuneToFire())
                {
                	entity.attackEntityFrom(new EntityDamageSource("onFire", this).setFireDamage().setDamageBypassesArmor(), 1F);
                }
            }
        }
    }
    
    protected float getSoundPitch()
    {
      return super.getSoundPitch() * 0.6F;
    }
    
    @Nullable
    protected Item getDropItem()
    {
        return rand.nextBoolean() ? Items.FEATHER : Items.CLAY_BALL;
    }
}
