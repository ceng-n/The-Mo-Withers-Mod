package net.endermanofdoom.mowithers.entity.wither;

import java.util.List;

import javax.annotation.Nullable;
import com.google.common.base.Predicate;

import net.endermanofdoom.mca.entity.boss.EntityHostileWither;
import net.endermanofdoom.mca.entity.projectile.EntityWitherSkullShared;
import net.endermanofdoom.mac.enums.EnumLevel;
import net.endermanofdoom.mowithers.MoWithers;
import net.endermanofdoom.mowithers.registry.MBlocks;
import net.endermanofdoom.mowithers.registry.MSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityWitherDragon extends EntityHostileWither
{
	public int ghostticks;

	public EntityWitherDragon(World worldIn) 
	{
		super(worldIn);
		this.experienceValue *= 2800;
	}
	
	protected void initEntityAI()
    {
		WITHERTARGETS = new Predicate<Entity>()
	    {
	        public boolean apply(@Nullable Entity p_apply_1_)
	        {
	            return p_apply_1_ instanceof EntityLivingBase && !(p_apply_1_ instanceof EntityWitherDragon) && ((EntityLivingBase)p_apply_1_).attackable();
	        }
	    };
        super.initEntityAI();
        this.targetTasks.addTask(2, new net.endermanofdoom.mca.entity.ai.EntityAINearestAttackableTargetInCube<EntityLivingBase>(this, EntityLivingBase.class, WITHERTARGETS));
    }
    
    public TextFormatting getNameColor()
    {
    	return TextFormatting.DARK_PURPLE;
    }
    
    public void setSkullStats(EntityWitherSkullShared skull, float damage, boolean invul)
    {
        super.setSkullStats(skull, damage, false);
        skull.setRadius(10F);
        skull.setType(17);
        skull.playSound(SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, this.getSoundVolume(), this.getSoundPitch());
        skull.playSound(SoundEvents.ENTITY_ENDERDRAGON_SHOOT, this.getSoundVolume(), 1F);
        skull.setSkullTexture("wither/superboss/charge");
        skull.setMod(MoWithers.MODID);
    }
    
    public boolean isSuperBoss()
    {
        return true;
    }
	
    public float getWitherScale()
    {
    	return super.getWitherScale() * 4F;
    }    
    
    public float getBaseWidth()
    {
    	return 1F;
    }

    public float getBaseHeight()
    {
    	return this.isCrouching() ? 1.25F : 6.25F;
    }
    
    public float getEyeHeight()
    {
    	return height * 0.9625F;
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(24.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(15.0D);
    }

	public double getMobHealth() 
	{
		return super.getMobHealth() * 60000D;
	}

	public double getMobAttack() 
	{
		return super.getMobAttack() * 2000D;
	}

	public double getMobSpeed() 
	{
		return 1D;
	}
    
    protected SoundEvent getAmbientSound()
    {
        return MSounds.ENTITY_WITHER_DRAGON[0];
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return MSounds.ENTITY_WITHER_DRAGON[1];
    }

    protected SoundEvent getDeathSound()
    {
        return MSounds.ENTITY_WITHER_DRAGON[2];
    }
    
    protected double getFlyHeight()
    {
        return 8D;
    }
    
    public void onWitherParticaleUpdate()
    {
        super.onWitherParticaleUpdate();

        for (int i = 0; i < 30; ++i)
        {
            this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
        
            for (int l = 0; l < 3; ++l)
            {
                double d10 = this.getHeadX(l);
                double d2 = this.getHeadY(l);
                double d4 = this.getHeadZ(l);
                this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d10 + this.rand.nextGaussian() * (0.15D * this.getWitherScale()), d2 + this.rand.nextGaussian() * (0.15D * this.getWitherScale()), d4 + this.rand.nextGaussian() * (0.15D * this.getWitherScale()), 0.0D, 0.0D, 0.0D);
            }
        }
    }
    
    protected double getHeadX(int p_82214_1_)
    {
        if (p_82214_1_ <= 0)
        {
            return this.posX;
        }
        else
        {
            float f = (this.renderYawOffset + (float)(180 * (p_82214_1_ - 1))) * 0.017453292F;
            float f1 = MathHelper.cos(f);
            return this.posX + (double)f1 * (1D * this.getWitherScale());
        }
    }

    protected double getHeadY(int p_82208_1_)
    {
        return p_82208_1_ <= 0 ? this.posY + this.getEyeHeight() : this.posY + (this.getEyeHeight() * 0.95D);
    }

    protected double getHeadZ(int p_82213_1_)
    {
        if (p_82213_1_ <= 0)
        {
            return this.posZ;
        }
        else
        {
            float f = (this.renderYawOffset + (float)(180 * (p_82213_1_ - 1))) * 0.017453292F;
            float f1 = MathHelper.sin(f);
            return this.posZ + (double)f1 * (1D * this.getWitherScale());
        }
    }
    
    public void ignite()
    {
        this.setInvulTime(4620);
        this.setHealth(this.getMaxHealth() / 99.0F);
    }
    
    public void onHealUpdate()
    {
        this.heal(20D);
        super.onHealUpdate();
    }
    
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (this.blockBreakCounter <= 0 && getAttackTarget() != null)
            this.blockBreakCounter = 1;
        
        if (!this.world.isRemote && this.getWitherHealth() > 0)
        {
        	if (this.getWatchedTargetId(0) > 0)
        	{
                Entity entity = this.world.getEntityByID(this.getWatchedTargetId(0));
                
              if (entity != null)
              {

              	if (!this.canEntityBeSeen(entity) && this.ticksExisted % 120 == 0)
              		this.attackEntityWithRangedAttack((EntityLivingBase)entity, 0);
              	
              	if (this.world.getEntityByID(this.getWatchedTargetId(1)) == null && rand.nextInt(40) == 0)
              		this.updateWatchedTargetId(1, entity.getEntityId());
              	
              	if (this.world.getEntityByID(this.getWatchedTargetId(2)) == null && rand.nextInt(40) == 0)
              		this.updateWatchedTargetId(2, entity.getEntityId());
              	
            	if (this.isArmored() && this.getRamTime() < -60)
            	{
                    float f = ticksExisted * (float)Math.PI * 0.05F;
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
        	       
        	        if (entitywitherskull.isInvulnerable())
        	    		entitywitherskull.setRadius(entitywitherskull.getRadius() * 6F);
        	        entitywitherskull.posY = entity.posY + (entity.height * 0.5F);
        	        entitywitherskull.posX = entity.posX + (MathHelper.cos(f) * 80F);
        	        entitywitherskull.posZ = entity.posZ + (MathHelper.sin(f) * 80F);
        	        entitywitherskull.accelerationX = entity.posX + entity.motionX - entitywitherskull.posX;
        	        entitywitherskull.accelerationY = 0D;
        	        entitywitherskull.accelerationZ = entity.posZ + entity.motionZ - entitywitherskull.posZ;
        	        entitywitherskull.accelerationX *= 0.0025D;
        	        entitywitherskull.accelerationZ *= 0.0025D;
        	        this.world.spawnEntity(entitywitherskull);
            	}
              }
        	}
        }        
        this.noClip = getRamTime() < -60;
        if (this.getRamTime() < -60)
        {
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(8D * this.world.getDifficulty().getDifficultyId()));
            
            for (Entity entity : list)
            {
                if (!entity.getIsInvulnerable() && !(entity instanceof EntityWitherSkullShared))
                {
                	double force = -30D;
                	entity.extinguish();
                    double d2 = this.posX - entity.posX;
                    double d3 = (this.posY + 2) - entity.posY;
                    double d4 = this.posZ - entity.posZ;
                    double d5 = d2 * d2 + d3 * d3 + d4 * d4;
                    entity.motionX += d2 / d5 * force + (this.motionX * 5D);
                    entity.motionY += d3 / d5 * force + 0.5D + (this.motionY * 5D);
                    entity.motionZ += d4 / d5 * force + (this.motionZ * 5D);
                    entity.isAirBorne = true;
                    entity.hurtResistantTime = 0;                    
                    if (entity instanceof EntityPlayerMP)
                    {
                    	((EntityPlayerMP)entity).connection.sendPacket(new SPacketEntityVelocity(entity.getEntityId(), entity.motionX, entity.motionY, entity.motionZ));
                    }
                    if (entity.isEntityAlive() && !this.isOnSameTeam(entity))
                    {
                    	entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float)(getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue() * 2));
                    }
                }
            }
        }
        
        if (this.getWitherHealth() <= this.getMaxWitherHealth() * 0.001D && world.getEntityByID(getWatchedTargetId(0)) != null)
        {
        	for(int j= 0; j < 6; ++j)
        	for(int i = 0; i < 2; ++i)
        		this.launchWitherSkullToCoords(i, world.getEntityByID(getWatchedTargetId(0)).posX, world.getEntityByID(getWatchedTargetId(0)).posY, world.getEntityByID(getWatchedTargetId(0)).posZ, true);
        }
    }
    
    protected float getSoundPitch()
    {
      return (this.rand.nextFloat() - this.rand.nextFloat()) * 0.3F + 1.0F;
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
            	this.entityDropItem(new ItemStack(Items.NETHER_STAR, 1 + rand.nextInt(4)), this.getEyeHeight());
                
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
            	this.entityDropItem(new ItemStack(MBlocks.NETHER_STAR_BLOCK, 1 + rand.nextInt(4)), this.getEyeHeight());
            	this.entityDropItem(new ItemStack(Blocks.DRAGON_EGG, 1 + rand.nextInt(4)), this.getEyeHeight());
            }

            this.broadcastSound(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, 0.75F);
            this.broadcastSound(SoundEvents.AMBIENT_CAVE, 0.5F);
            this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 10.0F, 0.5F);

            this.isDead = true;
        }
    	}
    }

	public EnumLevel getTier() 
	{
		return EnumLevel.LORD;
	}
}
