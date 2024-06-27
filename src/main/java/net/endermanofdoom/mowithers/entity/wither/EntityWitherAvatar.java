package net.endermanofdoom.mowithers.entity.wither;

import java.util.List;
import javax.annotation.Nullable;
import com.google.common.base.Predicate;
import net.endermanofdoom.mac.enums.EnumGender;
import net.endermanofdoom.mac.enums.EnumLevel;
import net.endermanofdoom.mca.entity.boss.EntityHostileWither;
import net.endermanofdoom.mca.entity.projectile.EntityWitherSkullShared;
import net.endermanofdoom.mowithers.MoWithers;
import net.endermanofdoom.mowithers.registry.MSounds;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class EntityWitherAvatar extends EntityHostileWither
{
	protected int stilltimer;
	
	public EntityWitherAvatar(World worldIn) 
	{
		super(worldIn);
		this.experienceValue *= 600;
		this.setNoGravity(true);
	}
	
	protected void initEntityAI()
    {
		WITHERTARGETS = new Predicate<Entity>()
	    {
	        public boolean apply(@Nullable Entity p_apply_1_)
	        {
	            return p_apply_1_ instanceof EntityLivingBase && !(p_apply_1_ instanceof EntityWitherAvatar) && ((EntityLivingBase)p_apply_1_).attackable();
	        }
	    };
        super.initEntityAI();
        this.targetTasks.addTask(2, new net.endermanofdoom.mca.entity.ai.EntityAINearestAttackableTargetInCube<EntityLivingBase>(this, EntityLivingBase.class, WITHERTARGETS));
    }
    
    public void setSkullStats(EntityWitherSkullShared skull, float damage, boolean invul)
    {
        super.setSkullStats(skull, damage, invul);
        skull.setRadius(this.getPhasePercent(0.6F) ? 10F : 5F);
        skull.setType(11);
        skull.setDeflect(true);
        skull.setSkullTexture("wither/element/avatar_aura");
        skull.setMod(MoWithers.MODID);
    }
    
    public TextFormatting getNameColor()
    {
    	switch (rand.nextInt(10))
    	{
    	case 1:
        	return TextFormatting.AQUA;
    	case 2:
        	return TextFormatting.BLUE;
    	case 3:
        	return TextFormatting.GOLD;
    	case 4:
        	return TextFormatting.GREEN;
    	case 5:
        	return TextFormatting.RED;
    	case 6:
        	return TextFormatting.YELLOW;
    	case 7:
        	return TextFormatting.DARK_BLUE;
    	case 8:
        	return TextFormatting.DARK_GREEN;
    	case 9:
        	return TextFormatting.DARK_RED;
    	default:
        	return TextFormatting.WHITE;
    	}
    }

    public float getWitherScale()
    {
    	return super.getWitherScale() * (this.getPhasePercent(0.2F) ? 8F : (this.getPhasePercent(0.4F) ? 7F : (this.getPhasePercent(0.6F) ? 6F : (this.getPhasePercent(0.8F) ? 5F : 4F))));
    }
    
    public boolean isFinalBoss()
    {
        return true;
    }
    
    public boolean isSuperBoss()
    {
        return true;
    }

	public EnumLevel getTier() 
	{
		return EnumLevel.TEMPLAR;
	}

	public EnumGender getGender() 
	{
		return EnumGender.MALE;
	}

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(6.0D);
    }

	public double getMobHealth() 
	{
		return super.getMobHealth() * 1800D;
	}

	public double getMobAttack() 
	{
		double attack = 80D;
		
		if (this.getInvulTime() <= 0)
		{
			if (getPhasePercent(0.8F))
				attack = 120D;
			if (getPhasePercent(0.6F))
				attack = 160D;
			if (getPhasePercent(0.4F))
				attack = 200D;
			if (getPhasePercent(0.2F))
				attack = 240D;
		}
		
		return super.getMobAttack() * attack;
	}

	public double getMobSpeed() 
	{
		return 0.35D;
	}
    
    protected SoundEvent getAmbientSound()
    {
        return MSounds.ENTITY_WITHER_AVATAR[0];
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return MSounds.ENTITY_WITHER_AVATAR[1];
    }

    protected SoundEvent getDeathSound()
    {
        return MSounds.ENTITY_WITHER_AVATAR[2];
    }
    
    protected double getFlyHeight()
    {
        return 12D;
    }
    
    public void onWitherParticaleUpdate()
    {
        super.onWitherParticaleUpdate();

        double wid = (this.rand.nextDouble() - 0.5D) * (double)this.width;
        
        for (int l = 0; l < 3; ++l)
        {
            double d10 = this.getHeadX(l);
            double d2 = this.getHeadY(l);
            double d4 = this.getHeadZ(l);
            this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d10 + wid, d2 + wid, d4 + wid, 0.0D, 0.0D, 0.0D);
            this.world.spawnParticle(EnumParticleTypes.WATER_WAKE, d10 + wid, d2 + wid, d4 + wid, motionX, 0.0D, motionZ);
            this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, d10 + wid, d2 + wid, d4 + wid, 0.0D, 0.0D, 0.0D);
            this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, d10 + wid, d2 + wid, d4 + wid, 0.0D, 0.0D, 0.0D, Block.getStateId(Blocks.DIRT.getDefaultState()));
        }

        for (int i = 0; i < 6; ++i)
        {
            this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
            this.world.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
            this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
            this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D, Block.getStateId(Blocks.DIRT.getDefaultState()));
        }
        
        if (this.rand.nextInt(24) == 0 && !this.isSilent() && !this.isWet())
        {
            this.world.playSound(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, SoundEvents.BLOCK_FIRE_AMBIENT, this.getSoundCategory(), 5.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, false);
        }
        if (this.ticksExisted % 10 == 0 && !this.isSilent())
        {
            this.world.playSound(this.posX + 0.5D, this.posY + (height * 0.5D), this.posZ + 0.5D, SoundEvents.ITEM_ELYTRA_FLYING, this.getSoundCategory(), 5F, 2F, false);
            this.world.playSound(this.posX + 0.5D, this.posY + (height * 0.5D), this.posZ + 0.5D, SoundEvents.BLOCK_LAVA_AMBIENT, this.getSoundCategory(), 5F, 2F, false);
            this.world.playSound(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, SoundEvents.BLOCK_WATER_AMBIENT, this.getSoundCategory(), 5F, 2F, false);
        }
        
        this.world.playSound(this.posX + 0.5D, this.posY + (height * 0.5D), this.posZ + 0.5D, SoundEvents.BLOCK_GRAVEL_BREAK, this.getSoundCategory(), 2F, 1F, false);

        for (int i = 0; i < 500; ++i)
        {
            float f1 = (float)i * (float)Math.PI / 250;
            this.world.spawnParticle(EnumParticleTypes.FALLING_DUST, posX + (double)MathHelper.cos(f1) * 15D, this.posY + (height * 0.5D), posZ + (double)MathHelper.sin(f1) * 15D, motionX, motionY, motionZ, Block.getStateId(Blocks.WOOL.getDefaultState()));
        }

        for (int i = 0; i < 400; ++i)
        {
            float f1 = (float)i * (float)Math.PI / 200;
            this.world.spawnParticle(EnumParticleTypes.FALLING_DUST, posX + (double)MathHelper.cos(f1) * 12.5D, this.posY + (height * 0.5D) + ((double)MathHelper.cos(f1) * 5D), posZ + (double)MathHelper.sin(f1) * 12.5D, motionX, motionY + 0.2D, motionZ, Block.getStateId(Blocks.DIRT.getDefaultState()));
        }

        for (int i = 0; i < 400; ++i)
        {
            float f1 = (float)i * (float)Math.PI / 200;
            this.world.spawnParticle(EnumParticleTypes.FALLING_DUST, posX + (double)MathHelper.cos(f1) * 10D, this.posY + (height * 0.5D) - ((double)MathHelper.cos(f1) * 5D), posZ + (double)MathHelper.sin(f1) * 10D, motionX, motionY + 0.2D, motionZ, Block.getStateId(Blocks.LAPIS_BLOCK.getDefaultState()));
        }

        for (int i = 0; i < 200; ++i)
        {
            float f1 = (float)i * (float)Math.PI / 100;
            this.world.spawnParticle(EnumParticleTypes.FALLING_DUST, posX + (double)MathHelper.cos(f1) * 7.5D, this.posY + (height * 0.5D), posZ + (double)MathHelper.sin(f1) * 7.5D, motionX, motionY, motionZ, Block.getStateId(Blocks.FIRE.getDefaultState()));
        }
        
    }
    
    public void ignite()
    {
        this.setInvulTime(4620);
        this.setHealth(this.getMaxHealth() / 99.0F);
    }
    
    public void onHealUpdate()
    {
        this.heal(1D);
    }
    
    public void onWitherMoveUpdate()
    {
    	if (this.stilltimer <= 0)
    	{
    		super.onWitherMoveUpdate();
    	}
    	else
    	{
    		this.setSneaking(false);
    		this.setRamTime(0);
    		this.setSitting(false);
            this.motionY *= 0.1D;
        	flyposX = posX;
        	flyposZ = posZ;
        	  for (int i = 0; i < 64; i++)
              {
        		  int in = MathHelper.floor(this.posX - 1.5D + this.rand.nextDouble() * 3.0D);
        		  int j = MathHelper.floor(this.posY - this.rand.nextDouble() * height + getFlyHeight());
        		  int k = MathHelper.floor(this.posZ - 1.5D + this.rand.nextDouble() * 3.0D);
        		  BlockPos blockpos = new BlockPos(in, j, k);
        		  IBlockState iblockstate = this.world.getBlockState(blockpos);
        		  if (!this.world.isRemote && (iblockstate.isFullCube() || blockpos.getY() <= world.getTopSolidOrLiquidBlock(getPosition()).getY() + getFlyHeight()))
       	         {
       	          if (this.motionY < 0.0D)
       	            this.motionY = 0.0D;
       	          
       	          this.motionY += (0.5D - this.motionY) * getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).getBaseValue();
                }
              }
    	}
    }
    
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (this.blockBreakCounter <= 0 && getAttackTarget() != null)
            this.blockBreakCounter = 1;

        
        if (!this.world.isRemote && this.isEntityAlive())
        {
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(48D * this.world.getDifficulty().getDifficultyId()));

            for (Entity entity : list)
            {
                if (entity.isEntityAlive() && (!entity.getIsInvulnerable() || (entity instanceof EntityPlayer && !((EntityPlayer)entity).capabilities.disableDamage)) && (entity.canBePushed() || !(entity instanceof EntityLivingBase)) && !(entity instanceof EntityWitherAvatar) && !(entity instanceof EntityWitherSkullShared))
                {
                	double force = this.getPhasePercent(0.4F) ? 12 : 4D;
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
                    	entity.attackEntityFrom(new EntityDamageSource("inWall", this).setDamageBypassesArmor(), 0.1F);
                    	entity.hurtResistantTime = 0;
                	}
                }
            }
            
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
              }
              
              if (!this.world.isRemote && !this.isArmored() && ++this.stilltimer > 600)
              {
              	this.stilltimer = this.getPhasePercent(0.8F) ? -400 : -600;
                  this.world.newExplosion(this, this.posX, this.posY + (height * 0.5F), this.posZ, 7.0F * width, false, net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this));
                  this.world.playBroadcastSound(1023, this.getPosition(), 0);
              }
        	}
        	
            if (!this.world.isRemote && this.isArmored() && this.stilltimer > 0)
            {
              	this.stilltimer = 0;
            }
        }
        
        if (!this.world.isRemote && this.isEntityAlive())
        {
            if (this.world.isThundering() && this.world.canBlockSeeSky(new BlockPos(this)) && this.rand.nextInt(this.getPhasePercent(0.2F) ? 20 : (this.getPhasePercent(0.6F) ? 80 : 160)) == 0)
            {
                this.world.addWeatherEffect(new EntityLightningBolt(this.world, this.posX - 0.5D, this.posY + height, this.posZ - 0.5D, true));
                if (this.getWatchedTargetId(0) > 0)
                {
                    Entity entity = this.world.getEntityByID(this.getWatchedTargetId(0));
                    
                    if (entity != null)
                        this.world.addWeatherEffect(new EntityLightningBolt(this.world, entity.posX - 0.5D, entity.posY, entity.posZ - 0.5D, false));
                }
            }
            if (this.getPhasePercent(0.6F))
            {
                this.world.getWorldInfo().setThundering(true);
                this.world.getWorldInfo().setThunderTime(100);
            }
            else
            {
                this.world.getWorldInfo().setRaining(true);
                this.world.getWorldInfo().setRainTime(100);
            }
        }
    }
	
    public boolean canDestroyBlock(BlockPos blockIn)
    {
        return super.canDestroyBlock(blockIn) && world.getBlockState(blockIn).getBlock() != Blocks.GRASS && world.getBlockState(blockIn).getBlock() != Blocks.DIRT && world.getBlockState(blockIn).getBlock() != Blocks.MYCELIUM && world.getBlockState(blockIn).getBlock() != Blocks.GRASS_PATH && world.getBlockState(blockIn).getBlock() != Blocks.GRAVEL && world.getBlockState(blockIn).getBlock() != Blocks.NETHERRACK && world.getBlockState(blockIn).getBlock() != Blocks.STONE && world.getBlockState(blockIn).getBlock() != Blocks.DIRT && world.getBlockState(blockIn).getBlock() != Blocks.COBBLESTONE && world.getBlockState(blockIn).getBlock() != Blocks.LEAVES;
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
    
    protected int minDropNum(int lootingModifier)
    {
    	return 128 + lootingModifier;
    }
    
    protected int maxDropNum(int lootingModifier)
    {
    	return 256 + lootingModifier;
    }
    
    @Nullable
    protected Item getDropItem()
    {
    	switch (rand.nextInt(10))
    	{
    	case 1:
            return Item.getItemFromBlock(Blocks.IRON_BLOCK);
    	case 2:
            return Item.getItemFromBlock(Blocks.REDSTONE_BLOCK);
    	case 3:
            return Item.getItemFromBlock(Blocks.GOLD_BLOCK);
    	case 4:
            return Item.getItemFromBlock(Blocks.LAPIS_BLOCK);
    	case 5:
            return Item.getItemFromBlock(Blocks.DIAMOND_BLOCK);
    	case 6:
            return Item.getItemFromBlock(Blocks.EMERALD_BLOCK);
    	case 7:
            return Items.NETHER_STAR;
    	default:
            return Item.getItemFromBlock(Blocks.COAL_BLOCK);
    	}
    }
    
	public int[] getBarColor() 
	{
		return new int[] {rand.nextInt(255), rand.nextInt(255), rand.nextInt(255), 0, 122, 255};
	}
    
    /**
     * handles entity death timer, experience orb and particle creation
     */
    protected void onDeathUpdate()
    {
    	if (this.getWitherHealth() <= 0)
    	{
        ++this.deathTicks;
        this.setSneaking(false);
        this.setRamTime(0);
        this.setSitting(false);
        this.motionX *= 0;
        this.motionY *= 0;
        this.motionZ *= 0;
    	this.renderYawOffset = this.rotationYaw = this.rotationYawHead;
    	this.rotationPitch = 50F - (deathTicks / 2);
    	rotationPitch -= 5F;
        
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
            this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX + (double)f, this.posY + 6D + (double)f1, this.posZ + (double)f2, 0.0D, 0.0D, 0.0D);
        }

        boolean flag = this.world.getGameRules().getBoolean("doMobLoot");
        int i = this.experienceValue;

        if (!this.world.isRemote)
        {
            if (this.deathTicks > 150 && this.deathTicks % 5 == 0 && flag)
            {
                this.dropExperience(MathHelper.floor((float)i * 0.08F));
            }

            if (this.deathTicks == 1)
            {
                this.broadcastSound(this.getDeathSound(), this.getSoundPitch());
            }
        }

        if (this.deathTicks == 200 && !this.world.isRemote)
        {
            if (flag)
            {
                this.dropExperience(MathHelper.floor((float)i * 0.2F));
                this.dropLoot(true, 0, getLastDamageSource());
            }
            this.world.getWorldInfo().setThundering(false);
            this.world.getWorldInfo().setRaining(false);
            this.broadcastSound(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, 1F);
            this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 10.0F, 0.5F);
            
            EntityWitherAir airpart = new EntityWitherAir(world);
            EntityWitherEarth earthpart = new EntityWitherEarth(world);
            EntityWitherFire firepart = new EntityWitherFire(world);
            EntityWitherWater waterpart = new EntityWitherWater(world);
            
            airpart.copyLocationAndAnglesFrom(this);
            earthpart.copyLocationAndAnglesFrom(this);
            firepart.copyLocationAndAnglesFrom(this);
            waterpart.copyLocationAndAnglesFrom(this);
            
            if (player != null)
            {
                airpart.setTamedBy(player);
                earthpart.setTamedBy(player);
                firepart.setTamedBy(player);
                waterpart.setTamedBy(player);
            }
            
            world.spawnEntity(airpart);
            world.spawnEntity(earthpart);
            world.spawnEntity(firepart);
            world.spawnEntity(waterpart);
            
            this.isDead = true;
        }
    	}
    }
}
