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
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.BossInfo;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class EntityWitherMagma extends EntityHostileWither
{

	public EntityWitherMagma(World worldIn) 
	{
		super(worldIn);
		this.experienceValue *= 16;
		this.bossInfo.setColor(BossInfo.Color.RED);
	}
	
	protected void initEntityAI()
    {
        super.initEntityAI();
        this.targetTasks.addTask(2, new EntityAIWitherTargeting<EntityLivingBase>(this, EntityLivingBase.class, WITHERTARGETS));
    }
	
    public boolean canDestroyBlock(BlockPos blockIn)
    {
        return super.canDestroyBlock(blockIn) && world.getBlockState(blockIn).getBlock() != Blocks.FIRE && world.getBlockState(blockIn).getBlock() != Blocks.LAVA && world.getBlockState(blockIn).getBlock() != Blocks.FLOWING_LAVA;
    }
    
    public TextFormatting getNameColor()
    {
    	return TextFormatting.DARK_RED;
    }
    
    public void setSkullStats(EntityWitherSkullShared skull, float damage, boolean invul)
    {
        super.setSkullStats(skull, damage, invul);
        skull.setRadius(2F);
        skull.setType(9);
        skull.setBurn(true);
        skull.setPlacedBlockState(invul ? Blocks.LAVA.getDefaultState() : Blocks.FLOWING_LAVA.getDefaultState());
        skull.setSkullTexture("wither/element/wither_fire");
        skull.setMod(MoWithers.MODID);
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(12.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(2.0D);
    }

	public double getMobHealth() 
	{
		return super.getMobHealth() * 8D;
	}

	public double getMobAttack() 
	{
		return super.getMobAttack() * 2D;
	}

	public double getMobSpeed() 
	{
		return 0.35D;
	}
    
    protected SoundEvent getAmbientSound()
    {
        return MSounds.ENTITY_WITHER_MAGMA[0];
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return MSounds.ENTITY_WITHER_MAGMA[1];
    }

    protected SoundEvent getDeathSound()
    {
        return MSounds.ENTITY_WITHER_MAGMA[2];
    }
    
    public boolean isBurning()
    {
        return this.isArmored() && !this.isWet();
    }
    
    protected double getFlyHeight()
    {
        return 4D;
    }
    
    public void onWitherParticaleUpdate()
    {
        super.onWitherParticaleUpdate();

        for (int l = 0; l < 3; ++l)
        {
            double d10 = this.getHeadX(l);
            double d2 = this.getHeadY(l);
            double d4 = this.getHeadZ(l);
            this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D);
            this.world.spawnParticle(EnumParticleTypes.LAVA, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D);
        }

        for (int i = 0; i < 3; ++i)
        {
            this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
        }
        this.world.spawnParticle(EnumParticleTypes.LAVA, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
        
        if (this.ticksExisted % 100 == 0 && !this.isSilent() && !this.isWet())
        {
            this.world.playSound(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, SoundEvents.BLOCK_LAVA_AMBIENT, this.getSoundCategory(), 1F, this.rand.nextFloat() * 1.0F + 0.3F, false);
        }
        
        if (this.rand.nextInt(24) == 0 && !this.isSilent() && !this.isWet())
        {
            this.world.playSound(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, SoundEvents.BLOCK_LAVA_POP, this.getSoundCategory(), 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, false);
        }
    }
    
    public void onHealUpdate()
    {
        BlockPos blockpos = new BlockPos(MathHelper.floor(this.posX), 0, MathHelper.floor(this.posZ));
        Biome biome = this.world.getBiome(blockpos);
        
        if (this.ticksExisted % 5 == 0 && biome.getTemperature(blockpos) > 1F)
        	this.heal(1D);
        
        if (biome.getTemperature(blockpos) > 0.4F && !this.isWet())
        	super.onHealUpdate();
        
        if (biome.getTemperature(blockpos) <= 0.25F || this.isWet())
        {
        	this.attackEntityFrom(DamageSource.STARVE, 2);
            this.world.playSound(this.posX + 0.5D, this.posY + this.rand.nextFloat() * height, this.posZ + 0.5D, SoundEvents.BLOCK_FIRE_EXTINGUISH, this.getSoundCategory(), 1.0F, this.rand.nextFloat() * 0.7F + 0.3F, false);
        }
    	
        if (this.ticksExisted % 200 == 0 && !this.isWet())
        {
          if (biome == Biomes.FOREST || biome == Biomes.BIRCH_FOREST || biome == Biomes.FOREST_HILLS || biome == Biomes.BIRCH_FOREST_HILLS || biome == Biomes.MUTATED_BIRCH_FOREST || biome == Biomes.MUTATED_BIRCH_FOREST_HILLS || biome == Biomes.MUTATED_FOREST || biome == Biomes.ROOFED_FOREST || biome == Biomes.MUTATED_ROOFED_FOREST || biome == Biomes.TAIGA || biome == Biomes.COLD_TAIGA || biome == Biomes.JUNGLE || biome == Biomes.JUNGLE_HILLS || biome == Biomes.MUTATED_JUNGLE || biome == Biomes.PLAINS)
          {
            float f = 10.0F * width;
            float f1 = 5.0F;
            double d0 = MathHelper.nextDouble(this.rand, this.posX - f, this.posX + f);
            double d1 = MathHelper.nextDouble(this.rand, this.posY - f1, this.posY);
            double d2 = MathHelper.nextDouble(this.rand, this.posZ - f, this.posZ + f);
            if (!this.world.isRemote)
            launchWitherSkullToCoords(0 + this.rand.nextInt(2), d0, d1, d2, false);
          }
        }
    }
    
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        
        if (this.isInWater())
        {
          this.world.playSound(this.posX + 0.5D, this.posY + 2.0D, this.posZ + 0.5D, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, this.getSoundCategory(), 0.5F, 2.6F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.8F, false);
          attackEntityFrom(DamageSource.STARVE, 20F);
        }
        
        if (!this.world.isRemote)
        {
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(8D));

            for (Entity entity : list)
            {
                if (entity.isEntityAlive() && !entity.isImmuneToFire() && !entity.getIsInvulnerable())
                {
                	entity.setFire(5);
                	entity.attackEntityFrom(new EntityDamageSource("inFire", this).setFireDamage(), 1F);
                	
                	if (this.getDistance(entity) < 2)
                	entity.attackEntityFrom(new EntityDamageSource("lava", this).setFireDamage(), 4F);
                }
            }
        }
    }
    

    protected void onDeathUpdate()
    {
    	if (!this.isEntityAlive())
    	{
    		this.deathTicks = 2;
            if (!this.world.isRemote && this.canDropLoot() && this.world.getGameRules().getBoolean("doMobLoot"))
            {
            	this.dropXP(posX, posY + this.getEyeHeight(), posZ, this.getExperiencePoints(this.attackingPlayer));
                this.dropLoot(true, 0, getLastDamageSource());
            }
            
            for (int l1 = 0; l1 < height; l1++) 
            {
                this.world.setBlockState(new BlockPos(this.posX, this.posY + l1, this.posZ), this.isWet() ? Blocks.OBSIDIAN.getDefaultState() : Blocks.LAVA.getDefaultState());
            }
            for (int l = 0; l < 3; ++l)
            {
                double d10 = this.getHeadX(l);
                double d2 = this.getHeadY(l);
                double d4 = this.getHeadZ(l);
                this.world.setBlockState(new BlockPos(d10, d2, d4), this.isWet() ? Blocks.OBSIDIAN.getDefaultState() : Blocks.LAVA.getDefaultState());
            }    

            this.setDead();
    	}
    }
    
    protected float getSoundPitch()
    {
      return super.getSoundPitch() - 0.5F;
    }
    
    @Nullable
    protected Item getDropItem()
    {
        return rand.nextBoolean() ? Items.MAGMA_CREAM : Item.getItemFromBlock(Blocks.MAGMA);
    }
    
    public boolean isOnSameTeam(Entity friend)
    {
    	if (friend instanceof EntityBlaze || friend instanceof EntityGhast || friend instanceof EntityPigZombie || friend instanceof EntityMagmaCube)
    		return true;
    	
    	return super.isOnSameTeam(friend);
    }
}
