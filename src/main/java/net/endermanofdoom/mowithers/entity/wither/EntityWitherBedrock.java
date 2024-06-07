package net.endermanofdoom.mowithers.entity.wither;

import java.util.List;

import javax.annotation.Nullable;

import net.endermanofdoom.mca.entity.boss.EntityHostileWither;
import net.endermanofdoom.mca.entity.EnumWitherType;
import net.endermanofdoom.mca.entity.projectile.EntityWitherSkullShared;
import net.endermanofdoom.mac.enums.EnumLevel;
import net.endermanofdoom.mowithers.MoWithers;
import net.endermanofdoom.mowithers.registry.MBlocks;
import net.endermanofdoom.mowithers.registry.MItems;
import net.endermanofdoom.mowithers.registry.MSounds;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityWitherBedrock extends EntityHostileWither
{
	protected int focushover;

	public EntityWitherBedrock(World worldIn) 
	{
		super(worldIn);
		this.experienceValue *= 2400;
	}
    
    protected Block getShotBlock() 
    {
		return Blocks.BEDROCK;
	}
	
	protected void initEntityAI()
    {
        super.initEntityAI();
        this.targetTasks.addTask(2, new net.endermanofdoom.mca.entity.ai.EntityAINearestAttackableTargetInCube<EntityLivingBase>(this, EntityLivingBase.class, WITHERTARGETS));
    }
	
    public boolean canDestroyBlock(BlockPos blockIn)
    {
        return blockIn.getY() > 0;
    }
    
    public void setSkullStats(EntityWitherSkullShared skull, float damage, boolean invul)
    {
        super.setSkullStats(skull, damage, invul);
        skull.setRadius(8F);
        skull.setType(20);
        skull.setDeflect(true);
        skull.setMod(MoWithers.MODID);
        skull.setSkullTexture("wither/superboss/wither_bedrock");
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(24D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(20D);
    }
    
    public TextFormatting getNameColor()
    {
    	return TextFormatting.DARK_GRAY;
    }
    
    public EnumWitherType getWitherType()
    {
        return EnumWitherType.BLOCK_ROCK;
    }

	public double getMobHealth() 
	{
		return super.getMobHealth() * 80000D;
	}
    
    public boolean isSuperBoss()
    {
        return true;
    }

	public double getMobAttack() 
	{
		return super.getMobAttack() * 1000D;
	}

	public double getMobSpeed() 
	{
		return this.focushover >= 1 ? 6D : 1D;
	}
	
    public float getWitherScale()
    {
    	return super.getWitherScale() * 4F;
    }
    
    public void onHealUpdate()
    {
    	if (this.collidedHorizontally)
    		this.motionY += 0.2D - this.motionY;
    }
    
    protected SoundEvent getAmbientSound()
    {
        return MSounds.ENTITY_WITHER_BEDROCK[0];
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return MSounds.ENTITY_WITHER_BEDROCK[1];
    }

    protected SoundEvent getDeathSound()
    {
        return MSounds.ENTITY_WITHER_BEDROCK[2];
    }
    
    protected double getFlyHeight()
    {
        return 0D;
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
            this.world.spawnParticle(EnumParticleTypes.BLOCK_DUST, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D, Block.getStateId(Blocks.BEDROCK.getDefaultState()));
        }

        for (int i = 0; i < 12; ++i)
        {
            this.world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
            this.world.spawnParticle(EnumParticleTypes.BLOCK_DUST, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D, Block.getStateId(Blocks.BEDROCK.getDefaultState()));
        }
    }
    
    public void onWitherMoveUpdate()
    {
    	if (this.focushover <= 0)
    	{
    		super.onWitherMoveUpdate();
    	}
    	else
    	{
            double d0 = this.flyposX - this.posX;
            double d1 = this.flyposZ - this.posZ;
            
            double movereduc = 0.33D;
            double ramspeedmul = 2D;
            
    		this.setSneaking(true);
    		this.setRamTime(1);
    		this.setSitting(false);
        	if (!this.world.isRemote)
                if (this.getWatchedTargetId(0) > 0)
                {
                    Entity entity = this.world.getEntityByID(this.getWatchedTargetId(0));
                    if (entity != null)
                    {
                    	flyposX = entity.posX + (focushover > 500 ? -32D : 32D);
                    	flyposZ = entity.posZ;
                        double d2 = (entity.posY + entity.getEyeHeight()) - (this.posY - height);
                        double d4 = entity.posX - this.posX;
                        double d5 = entity.posZ - this.posZ;
                        
                        double d3 = d0 * d0 + d2 * d2 + d1 * d1;
                        if (this.getRamTime() >= -55)
                		renderYawOffset = rotationYaw = rotationYawHead = (float)(MathHelper.atan2(d5, d4) * (180D / Math.PI)) - 90.0F;

                        double d6 = (double)MathHelper.sqrt(d3);
                        this.motionX += (d0 / d6 * ramspeedmul - this.motionX) * (getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).getBaseValue() * movereduc);
                        this.motionY += (d2 / d6 * ramspeedmul - this.motionY) * (getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).getBaseValue() * movereduc);
                        this.motionZ += (d1 / d6 * ramspeedmul - this.motionZ) * (getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).getBaseValue() * movereduc);
                        
                        if (this.ticksExisted % 20 == 0 && entity instanceof EntityLivingBase)
                        	((IRangedAttackMob)this).attackEntityWithRangedAttack((EntityLivingBase) entity, 0);
                        
                        if (this.ticksExisted % 40 == 0 && entity instanceof EntityLivingBase)
                        	((IRangedAttackMob)this).attackEntityWithRangedAttack((EntityLivingBase) entity, 0);
                        
                        if (this.ticksExisted % 60 == 0 && entity instanceof EntityLivingBase)
                        	((IRangedAttackMob)this).attackEntityWithRangedAttack((EntityLivingBase) entity, 0);
                        
                        if (this.ticksExisted % 100 == 0 && entity instanceof EntityLivingBase)
                        {
                        	((IRangedAttackMob)this).attackEntityWithRangedAttack((EntityLivingBase) entity, 0);
                        	this.launchWitherSkullToCoords(1, entity.posX, entity.posY, entity.posZ, false);
                        	this.launchWitherSkullToCoords(2, entity.posX, entity.posY, entity.posZ, false);
                        }
                        

                    	if (this.focushover > 300)
                    	{
                            float f = focushover * (float)Math.PI * 0.01F;
                	        EntityWitherSkullShared entitywitherskull = new EntityWitherSkullShared(this.world, this, 0, 0, 0);
                	        this.setSkullStats(entitywitherskull, (float)getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue(), false);
                	    	if (isRaidBoss())
                	    		entitywitherskull.setRadius(entitywitherskull.getRadius() * 6F);
                	    	
                	        if (world.getDifficulty() == EnumDifficulty.PEACEFUL)
                	    		entitywitherskull.setRadius(entitywitherskull.getRadius() * 0.25F);
                	    	
                	        if (world.getDifficulty() == EnumDifficulty.EASY)
                	    		entitywitherskull.setRadius(entitywitherskull.getRadius() * 0.75F);
                	        
                	        if (world.getDifficulty() == EnumDifficulty.HARD)
                	    		entitywitherskull.setRadius(entitywitherskull.getRadius() * 1.5F);
                	        entitywitherskull.posY = entity.posY + (entity.height * 0.5F);
                	        entitywitherskull.posX = entity.posX + (MathHelper.cos(f) * 120F);
                	        entitywitherskull.posZ = entity.posZ + (MathHelper.sin(f) * 120F);
                	        entitywitherskull.accelerationX = entity.posX - entitywitherskull.posX;
                	        entitywitherskull.accelerationY = 0D;
                	        entitywitherskull.accelerationZ = entity.posZ - entitywitherskull.posZ;
                	        entitywitherskull.accelerationX *= 0.001D;
                	        entitywitherskull.accelerationZ *= 0.001D;
                	        this.world.spawnEntity(entitywitherskull);
                    	}
                        
                    	if (this.focushover > 80)
                    	{
                        	if (this.focushover % 40 == 0)
                        	{
                        		for (int t = 0; t < 10; ++t)
                        		{
                        	        EntityWitherSkullShared entitywitherskull = new EntityWitherSkullShared(this.world, this, 0, 0, 0);
                        	        entitywitherskull.accelerationX = 0D;
                        	        entitywitherskull.accelerationY = 0D;
                        	        entitywitherskull.accelerationZ = -0.25D;
                        	        this.setSkullStats(entitywitherskull, (float)getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue(), false);
                        	    	if (isRaidBoss())
                        	    		entitywitherskull.setRadius(entitywitherskull.getRadius() * 6F);
                        	    	
                        	        if (world.getDifficulty() == EnumDifficulty.PEACEFUL)
                        	    		entitywitherskull.setRadius(entitywitherskull.getRadius() * 0.25F);
                        	    	
                        	        if (world.getDifficulty() == EnumDifficulty.EASY)
                        	    		entitywitherskull.setRadius(entitywitherskull.getRadius() * 0.75F);
                        	        
                        	        if (world.getDifficulty() == EnumDifficulty.HARD)
                        	    		entitywitherskull.setRadius(entitywitherskull.getRadius() * 1.5F);
                        	       
                        	        if (entitywitherskull.isInvulnerable())
                        	    		entitywitherskull.setRadius(entitywitherskull.getRadius() * 6F);
                        	        entitywitherskull.posY = entity.posY;
                        	        entitywitherskull.posX = entity.posX - 100D + (20D * t);
                        	        entitywitherskull.posZ = entity.posZ + 60D;
                        	        this.world.spawnEntity(entitywitherskull);
                        		}
                        		
                        		for (int t = 0; t < 10; ++t)
                        		{
                        	        EntityWitherSkullShared entitywitherskull = new EntityWitherSkullShared(this.world, this, 0, 0, 0);
                        	        entitywitherskull.accelerationX = 0D;
                        	        entitywitherskull.accelerationY = 0D;
                        	        entitywitherskull.accelerationZ = 0.25D;
                        	        this.setSkullStats(entitywitherskull, (float)getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue(), false);
                        	    	if (isRaidBoss())
                        	    		entitywitherskull.setRadius(entitywitherskull.getRadius() * 6F);
                        	    	
                        	        if (world.getDifficulty() == EnumDifficulty.PEACEFUL)
                        	    		entitywitherskull.setRadius(entitywitherskull.getRadius() * 0.25F);
                        	    	
                        	        if (world.getDifficulty() == EnumDifficulty.EASY)
                        	    		entitywitherskull.setRadius(entitywitherskull.getRadius() * 0.75F);
                        	        
                        	        if (world.getDifficulty() == EnumDifficulty.HARD)
                        	    		entitywitherskull.setRadius(entitywitherskull.getRadius() * 1.5F);
                        	       
                        	        if (entitywitherskull.isInvulnerable())
                        	    		entitywitherskull.setRadius(entitywitherskull.getRadius() * 6F);
                        	        entitywitherskull.posY = entity.posY;
                        	        entitywitherskull.posX = entity.posX - 90D + (20D * t);
                        	        entitywitherskull.posZ = entity.posZ - 60D;
                        	        this.world.spawnEntity(entitywitherskull);
                        		}
                        	}
                    	}
                    }
                }
        	
    	}
    }
    
    public boolean getPhasePercent(float per)
    {
		return this.getWitherHealth() <= this.getMaxWitherHealth() * per;
    }
    
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (this.blockBreakCounter <= 0 && getAttackTarget() != null)
            this.blockBreakCounter = 1;

        if (!this.world.isRemote && this.isEntityAlive())
        {
        	if (this.getWatchedTargetId(0) > 0)
        	{
                Entity entity = this.world.getEntityByID(this.getWatchedTargetId(0));
                
              if (entity != null)
              {
              	if (!this.canEntityBeSeen(entity) && this.ticksExisted % 120 == 0)
              		((IRangedAttackMob)this).attackEntityWithRangedAttack((EntityLivingBase)entity, 0);
              	
              	if (this.world.getEntityByID(this.getWatchedTargetId(1)) == null && rand.nextInt(40) == 0)
              		this.updateWatchedTargetId(1, entity.getEntityId());
              	
              	if (this.world.getEntityByID(this.getWatchedTargetId(2)) == null && rand.nextInt(40) == 0)
              		this.updateWatchedTargetId(2, entity.getEntityId());
              	
              	if (this.getDistance(entity) > 120D)
              		focushover = 1;
              }
              
              if (!this.world.isRemote && ++this.focushover > (this.getPhasePercent(0.8F) ? 1000 : 500))
              {
              		this.focushover = this.getPhasePercent(0.8F) ? -400 : -600;
              		this.setRamTime(55);
              }
        	}
        	
            if (this.world.isThundering() && this.rand.nextInt(20) == 0)
            {
                double d6 = posX + (rand.nextGaussian() * 100D);
                double d7 = posZ + (rand.nextGaussian() * 100D);
                
                if (this.world.canBlockSeeSky(new BlockPos(d6, posY, d7)))
                    this.world.addWeatherEffect(new EntityLightningBolt(this.world, d6, world.getTopSolidOrLiquidBlock(new BlockPos(d6, posY, d7)).getY(), d7, false));
            }

            
            this.world.getWorldInfo().setRaining(true);
            this.world.getWorldInfo().setRainTime(100);
            this.world.getWorldInfo().setThundering(true);
            this.world.getWorldInfo().setThunderTime(100);
        }
    }
    
    @Nullable
    protected Item getDropItem()
    {
        return Item.getItemFromBlock(Blocks.BEDROCK);
    }

	public EnumLevel getTier() 
	{
		return EnumLevel.TEMPLAR;
	}
    
    protected float getSoundPitch()
    {
      return super.getSoundPitch() - 0.25F;
    }    

    public boolean isImmuneToExplosions()
    {
        return true;
    }
    

    
    /**
     * handles entity death timer, experience orb and particle creation
     */
    protected void onDeathUpdate()
    {
    	if (this.getWitherHealth() <= 0)
    	{
        ++this.deathTicks;

        this.motionX *= 0;
        this.motionY *= 0;
        this.motionZ *= 0;
        this.setSneaking(false);
        this.setRamTime(0);
        this.setSitting(false);
    	this.renderYawOffset = this.rotationYaw = this.rotationYawHead;
        this.move(MoverType.SELF, 0.0D, 0.05D, 0.0D);
        
        EntityPlayer player = this.world.getClosestPlayer(posX, posY + this.getEyeHeight(), posZ, -1D, EntitySelectors.IS_ALIVE);
        
        if (player != null && this.deathTicks < 200)
        {
        	player.closeScreen();
        }
        else
        {
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
                    entity.addVelocity(d2 / d5 * force, d3 / d5 * force + 0.1D, d4 / d5 * force);
                }
            }
        }
        
        if (this.deathTicks >= 180)
        {
            float f = (this.rand.nextFloat() - 0.5F) * width;
            float f1 = (this.rand.nextFloat() - 0.5F) * height;
            float f2 = (this.rand.nextFloat() - 0.5F) * width;
            this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX + (double)f, this.posY + 16D + (double)f1, this.posZ + (double)f2, 0.0D, 0.0D, 0.0D);
        }

        boolean flag = this.world.getGameRules().getBoolean("doMobLoot");
        int i = this.experienceValue;

        if (!this.world.isRemote)
        {
            if (flag)
            {
            	this.entityDropItem(new ItemStack(MItems.ENTROPIC_MATTER_UNSTABLE, 1), this.getEyeHeight());
                
                if (this.deathTicks > 150 && flag)
                {
                    this.dropExperience(MathHelper.floor((float)i * 0.016F));
                }
            }

            if (this.deathTicks == 1)
            {
                this.broadcastSound(this.getDeathSound(), 1F);
            }
        }

        if (this.deathTicks == 200 && !this.world.isRemote)
        {
            if (flag)
            {
                this.dropExperience(MathHelper.floor((float)i * 0.2F));
            	this.entityDropItem(new ItemStack(MBlocks.ATROPHIC_BLOCK, 16 + rand.nextInt(16)), this.getEyeHeight());
            }

            this.broadcastSound(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, 0.75F);
            this.broadcastSound(SoundEvents.AMBIENT_CAVE, 0.5F);
            this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 10.0F, 0.5F);

            this.isDead = true;
        }
    	}
    }
    
    private void dropExperience(int p_184668_1_)
    {
        while (p_184668_1_ > 0)
        {
            int i = EntityXPOrb.getXPSplit(p_184668_1_);
            p_184668_1_ -= i;
            this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY + this.getEyeHeight(), this.posZ, i));
        }
    }
    
	public int[] getBarColor() 
	{
		return new int[] {55, 55, 55, 0, this.isArmored() || this.isSuperBoss() ? 122 : 0, this.isArmored() || this.isSuperBoss() ? 255 : 0};
	}
    
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        amount -= 500;
        
    	return super.attackEntityFrom(source, amount);
    }
}
