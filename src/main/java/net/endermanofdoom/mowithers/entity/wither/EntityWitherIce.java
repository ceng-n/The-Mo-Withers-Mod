package net.endermanofdoom.mowithers.entity.wither;
import net.endermanofdoom.mca.entity.boss.EntityHostileWither;
import net.endermanofdoom.mca.entity.EnumWitherType;
import net.endermanofdoom.mca.entity.projectile.EntityWitherSkullShared;
import java.util.List;
import javax.annotation.Nullable;
import com.google.common.base.Predicate;

import net.endermanofdoom.mowithers.MoWithers;
import net.endermanofdoom.mowithers.registry.MSounds;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.init.Blocks;
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

public class EntityWitherIce extends EntityHostileWither
{
	public EntityWitherIce(World worldIn) 
	{
		super(worldIn);
		this.isImmuneToFire = false;
		this.experienceValue *= 8;
        this.tasks.removeTask(new EntityAISwimming(this));
	}
	
	protected void initEntityAI()
    {
		WITHERTARGETS = new Predicate<Entity>()
	    {
	        public boolean apply(@Nullable Entity p_apply_1_)
	        {
	            return p_apply_1_ instanceof EntityLivingBase && ((EntityLivingBase)p_apply_1_).getCreatureAttribute() != EnumCreatureAttribute.UNDEAD && ((EntityLivingBase)p_apply_1_).attackable() && !(p_apply_1_ instanceof EntityPolarBear);
	        }
	    };
        super.initEntityAI();
        this.targetTasks.addTask(2, new EntityAIWitherTargeting<EntityLivingBase>(this, EntityLivingBase.class, WITHERTARGETS));
    }
    
    public TextFormatting getNameColor()
    {
    	return TextFormatting.AQUA;
    }
    
    public EnumWitherType getWitherType()
    {
        return EnumWitherType.BLOCK_ROCK;
    }
	
    public boolean canDestroyBlock(BlockPos blockIn)
    {
        return super.canDestroyBlock(blockIn) && world.getBlockState(blockIn).getBlock() != Blocks.WATER && world.getBlockState(blockIn).getBlock() != Blocks.FLOWING_WATER;
    }
    
    public void setSkullStats(EntityWitherSkullShared skull, float damage, boolean invul)
    {
        super.setSkullStats(skull, damage, invul);
        skull.setRadius(2F);
        skull.setType(6);
        skull.setPlacedBlockState(invul ? Blocks.PACKED_ICE.getDefaultState() : Blocks.ICE.getDefaultState());
        skull.setSkullTexture("wither/element/wither_ice");
        skull.setMod(MoWithers.MODID);
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(8.0D);
    }

	public double getMobHealth() 
	{
		return super.getMobHealth() * 3D;
	}

	public double getMobAttack() 
	{
		return super.getMobAttack() * 2D;
	}

	public double getMobSpeed() 
	{
		return 0.45D;
	}
    
    protected SoundEvent getAmbientSound()
    {
        return MSounds.ENTITY_WITHER_ICE[0];
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        world.playEvent(2001, this.getPosition().up(rand.nextInt(3)), Block.getStateId(Blocks.ICE.getDefaultState()));
        return MSounds.ENTITY_WITHER_ICE[1];
    }

    protected SoundEvent getDeathSound()
    {
        return MSounds.ENTITY_WITHER_ICE[2];
    }
    
    protected double getFlyHeight()
    {
        return 6D;
    }
    
    public void onWitherParticaleUpdate()
    {
        super.onWitherParticaleUpdate();

        for (int l = 0; l < 3; ++l)
        {
            double d10 = this.getHeadX(l);
            double d2 = this.getHeadY(l);
            double d4 = this.getHeadZ(l);
            this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D, Block.getStateId(Blocks.ICE.getDefaultState()));
        }

        for (int i = 0; i < 6; ++i)
        {
            this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D, Block.getStateId(Blocks.ICE.getDefaultState()));
        }
    }
    
    public void onHealUpdate()
    {
        BlockPos blockpos = new BlockPos(MathHelper.floor(this.posX), 0, MathHelper.floor(this.posZ));
        Biome biome = this.world.getBiome(blockpos);
        
        if (this.isInWater())
        {
        	this.attackEntityFrom(DamageSource.STARVE, 1F);
        }
        else
        {
            if (biome.canRain() && this.isWet())
            	this.attackEntityFrom(DamageSource.STARVE, 0.25F);
            
            if (biome.getTemperature(blockpos) <= 0F)
            	this.heal(1D);
            
            if (biome.getTemperature(blockpos) < 1F)
            	super.onHealUpdate();
            
            if (biome.getTemperature(blockpos) >= 1F)
            {
            	this.attackEntityFrom(DamageSource.STARVE, 0.5F);

                for (int l = 0; l < 3; ++l)
                {
                    double d10 = this.getHeadX(l);
                    double d2 = this.getHeadY(l);
                    double d4 = this.getHeadZ(l);
                    this.world.spawnParticle(EnumParticleTypes.WATER_SPLASH, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D);
                }

                for (int i = 0; i < 6; ++i)
                {
                    this.world.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }
    
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        
        if (this.isBurning() || this.isInLava() && !this.isTamed())
        {
          this.world.playSound(this.posX + 0.5D, this.posY + 2.0D, this.posZ + 0.5D, SoundEvents.BLOCK_LAVA_EXTINGUISH, this.getSoundCategory(), 4F, 1.6F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.8F, false);
          attackEntityFrom(DamageSource.STARVE, 5F);
          
          if (this.getHealth() < 50)
          {
              this.world.newExplosion(this, this.posX, this.posY + (double)this.getEyeHeight(), this.posZ, 15, true, net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this));
              this.playSound(getDeathSound(), getSoundVolume(), getSoundPitch());
              this.setDead();
          }
        }
        
        if (!this.world.isRemote)
        {
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(8D));

            for (Entity entity : list)
            {
                if (entity.isEntityAlive() && !entity.getIsInvulnerable() && !(entity instanceof EntityWitherIce))
                {
                	entity.attackEntityFrom(new DamageSource("freeze").setDamageBypassesArmor(), 1F);
                }
            }
        }
    }
    
    protected float getSoundPitch()
    {
      return super.getSoundPitch() - 0.25F;
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
            
            if (!this.isBurning() && !this.isInLava())
            {
                for (int l1 = 0; l1 < height; l1++) 
                {
                    this.world.setBlockState(new BlockPos(this.posX, this.posY + l1, this.posZ), Blocks.ICE.getDefaultState());
                }
                for (int l = 0; l < 3; ++l)
                {
                    double d10 = this.getHeadX(l);
                    double d2 = this.getHeadY(l);
                    double d4 = this.getHeadZ(l);
                    this.world.setBlockState(new BlockPos(d10, d2, d4), Blocks.ICE.getDefaultState());
                }    
            }

            this.setDead();
    	}
    }
    
    @Nullable
    protected Item getDropItem()
    {
        return rand.nextBoolean() ? Item.getItemFromBlock(Blocks.PACKED_ICE) : Item.getItemFromBlock(Blocks.ICE);
    }
}
