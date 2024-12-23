package net.endermanofdoom.mowithers.entity.wither;
import net.endermanofdoom.mca.entity.boss.EntityHostileWither;
import net.endermanofdoom.mca.entity.projectile.*;
import net.endermanofdoom.mca.registrey.MCASounds;

import javax.annotation.Nullable;

import net.endermanofdoom.mowithers.MoWithers;
import net.endermanofdoom.mowithers.registry.MSounds;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityWitherLightning extends EntityHostileWither
{

	public EntityWitherLightning(World worldIn) 
	{
		super(worldIn);
		this.experienceValue *= 9;
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
        skull.setType(10);
        skull.setBurn(true);
        this.playSound(SoundEvents.AMBIENT_CAVE, 10F, 2F);
        skull.setSkullTexture("wither/element/wither_lightning");
        skull.setMod(MoWithers.MODID);
    }
    
    protected void launchWitherSkullToEntity(int p_82216_1_, EntityLivingBase p_82216_2_)
    {
    	if (p_82216_1_ == 0)
    		super.launchWitherSkullToEntity(p_82216_1_, p_82216_2_);
    	else
		{
	        double d0 = this.getHeadX(p_82216_1_);
	        double d1 = this.getHeadY(p_82216_1_);
	        double d2 = this.getHeadZ(p_82216_1_);
			EntityBeamAttack beam = new EntityBeamAttack(world, p_82216_2_, this, d0, d1, d2);
			beam.posX = d0;
			beam.posY = d1;
			beam.posZ = d2;
			beam.accelerationX = p_82216_2_.posX;
			beam.accelerationY = p_82216_2_.posY;
			beam.accelerationZ = p_82216_2_.posZ;
			beam.targetEntity = p_82216_2_;
			beam.setParticleType(EnumParticleTypes.END_ROD);
			beam.setDamageType(new EntityDamageSource("lightningBolt", this).setDamageBypassesArmor().setDamageIsAbsolute().setDifficultyScaled().setMagicDamage());
			beam.setDamage((float)getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue());
			this.world.spawnEntity(beam);
			playSound(MCASounds.lightningshot, 10F, 1F);
		}
    }
    
    public TextFormatting getNameColor()
    {
    	return TextFormatting.GOLD;
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(6.0D);
    }

	public double getMobHealth() 
	{
		return super.getMobHealth() * 4;
	}

	public double getMobAttack() 
	{
		return super.getMobAttack() * 1.75D;
	}

	public double getMobSpeed() 
	{
		return 0.75D;
	}
	
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        livingdata = super.onInitialSpawn(difficulty, livingdata);

        this.setAbsorptionAmount(this.getMaxHealth() / 3);

        return livingdata;
    }
    
    protected SoundEvent getAmbientSound()
    {
        return MSounds.ENTITY_WITHER_LIGHTNING[0];
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return MSounds.ENTITY_WITHER_LIGHTNING[1];
    }

    protected SoundEvent getDeathSound()
    {
        return MSounds.ENTITY_WITHER_LIGHTNING[2];
    }
    
    protected double getFlyHeight()
    {
        return 8D;
    }
    
    public void onWitherParticaleUpdate()
    {
        super.onWitherParticaleUpdate();

        for (int l = 0; l < 3; ++l)
        {
            double d10 = this.getHeadX(l);
            double d2 = this.getHeadY(l);
            double d4 = this.getHeadZ(l);
            this.world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D);
        }

        for (int i = 0; i < 6; ++i)
        {
            this.world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
        }
    }
    
    public void onHealUpdate()
    {
        if (this.ticksExisted % 20 == 0 && getHealth() >= getMaxHealth() && getAbsorptionAmount() < getMaxHealth() / 3.0F)
            setAbsorptionAmount(getAbsorptionAmount() + 1.0F);

        super.onHealUpdate();
    }
    
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        
        if (this.isInWater())
        {
          this.world.playSound(this.posX + 0.5D, this.posY + 2.0D, this.posZ + 0.5D, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, this.getSoundCategory(), 0.5F, 2.6F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.8F, false);
          attackEntityFrom(DamageSource.STARVE, 5.0F);
        }
        
        if (!this.world.isRemote)
        {
            if (!this.world.isRemote && this.world.isThundering() && this.world.canBlockSeeSky(new BlockPos(this)) && this.rand.nextInt(40) == 0) {
                this.world.addWeatherEffect(new EntityLightningBolt(this.world, this.posX - 0.5D, this.posY + 3.3499999046325684D, this.posZ - 0.5D, true));
              }
        }
    }
    
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (source == DamageSource.LIGHTNING_BOLT)
        {
            if (getAbsorptionAmount() < this.getMaxHealth() / 3)
                setAbsorptionAmount(getAbsorptionAmount() + amount);
            else
            	this.heal((double)amount);
          return false;
        }
        else
        {
        	return super.attackEntityFrom(source, amount);
        }
    }
    
    protected float getSoundPitch()
    {
      return super.getSoundPitch() * 0.75F;
    }
    
    @Nullable
    protected Item getDropItem()
    {
        return rand.nextBoolean() ? Item.getItemFromBlock(Blocks.END_ROD) : Items.BLAZE_ROD;
    }
    
	public int[] getBarColor() 
	{
		return new int[] {233, 233, 0, 0, this.isArmored() || this.isSuperBoss() ? 122 : 0, this.isArmored() || this.isSuperBoss() ? 255 : 0};
	}
}