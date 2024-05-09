package net.endermanofdoom.mowithers.entity.wither;
import net.endermanofdoom.mca.entity.boss.EntityHostileWither;
import net.endermanofdoom.mca.entity.projectile.EntityWitherSkullShared;
import java.util.List;
import javax.annotation.Nullable;
import com.google.common.base.Predicate;

import net.endermanofdoom.mac.enums.EnumLevel;
import net.endermanofdoom.mowithers.MoWithers;
import net.endermanofdoom.mowithers.registry.MBlocks;
import net.endermanofdoom.mowithers.registry.MItems;
import net.endermanofdoom.mowithers.registry.MSounds;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityFireball;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class EntityWitherVoid extends EntityHostileWither
{

	public EntityWitherVoid(World worldIn) 
	{
		super(worldIn);
		this.experienceValue *= 1200;
        this.tasks.addTask(7, new EntityAIWatchClosest2(this, EntityPlayer.class, 100F, 1));
	}
	
	protected void initEntityAI()
    {
		WITHERTARGETS = new Predicate<Entity>()
	    {
	        public boolean apply(@Nullable Entity p_apply_1_)
	        {
	            return p_apply_1_ instanceof EntityLivingBase && !(p_apply_1_ instanceof EntityWitherVoid) && ((EntityLivingBase)p_apply_1_).attackable();
	        }
	    };
        super.initEntityAI();
        this.targetTasks.addTask(2, new net.endermanofdoom.mca.entity.ai.EntityAINearestAttackableTargetInCube<EntityLivingBase>(this, EntityLivingBase.class, WITHERTARGETS));
    }
    
    public TextFormatting getNameColor()
    {
    	return TextFormatting.BLACK;
    }
	
    public static void consume(EntityWitherVoid eater, Entity eaten)
    {
		float devourment = eaten.height + eaten.width;
		if (eaten instanceof EntityLivingBase)
			devourment += ((EntityLivingBase)eaten).getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue();
		if (eaten instanceof EntityItem)
			devourment += ((EntityItem)eaten).getItem().getCount();
		if (eaten instanceof EntityXPOrb)
			devourment += ((EntityXPOrb)eaten).getXpValue();
		eater.heal(devourment);
		eaten.playSound(SoundEvents.ENTITY_WITHER_SHOOT, 1F, 0.5F);
    }
    
    public static void consumeBlock(EntityWitherVoid eater, BlockPos eaten, World world)
    {
        IBlockState iblockstate = world.getBlockState(eaten);
        Block block = iblockstate.getBlock();
		float devourment = block.getExplosionResistance(world, eaten, null, null);
		eater.heal(devourment);
        world.setBlockState(eaten, Blocks.AIR.getDefaultState(), 3);
    }
    
    public void setSkullStats(EntityWitherSkullShared skull, float damage, boolean invul)
    {
        super.setSkullStats(skull, damage, invul);
        skull.setRadius(15F);
        skull.setType(5);
        skull.setSkullTexture("wither/superboss/wither_void");
        skull.setMod(MoWithers.MODID);
    }

    public float getWitherScale()
    {
    	return super.getWitherScale() * 6F;
    }
    
    public boolean isSuperBoss()
    {
        return true;
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(24.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(10.0D);
    }

	public double getMobHealth() 
	{
		return super.getMobHealth() * 600000D;
	}

	public double getMobAttack() 
	{
		return super.getMobAttack() * 1000D;
	}

	public double getMobSpeed() 
	{
		return 0.3D;
	}
    
    protected SoundEvent getAmbientSound()
    {
        return MSounds.ENTITY_WITHER_VOID[0];
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return MSounds.ENTITY_WITHER_VOID[1];
    }

    protected SoundEvent getDeathSound()
    {
        return MSounds.ENTITY_WITHER_VOID[2];
    }
    
    protected double getFlyHeight()
    {
        return 8D;
    }
    
    public void onWitherParticaleUpdate()
    {
        super.onWitherParticaleUpdate();

        for (int i = 0; i < 15; ++i)
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
    
    public void ignite()
    {
        this.setInvulTime(4620);
        this.setHealth(this.getMaxHealth() / 99.0F);
    }
    
    public void onHealUpdate()
    {
        this.heal(2D);
    }
    
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

    	this.world.provider.setWorldTime(18000);
    	
        if (this.blockBreakCounter <= 0 && getAttackTarget() != null)
            this.blockBreakCounter = 1;
        
        if (!this.world.isRemote && this.getWitherHealth() > 0)
        {
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(2D));

            for (Entity entity : list)
            {
                if (entity.isEntityAlive() && !(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb))
                {
                	entity.attackEntityFrom(DamageSource.OUT_OF_WORLD, 4.0F);
                }
                else
            	{
            		world.removeEntity(entity);
            		consume(this, entity);
            	}
            }
            
            list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(100D));

            for (Entity entity : list)
            {
                if (!(entity instanceof EntityLivingBase) && !(entity instanceof EntityFireball) || this.getRamTime() < -60)
                {
                	double force = 8D;
                	if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isSneaking())
                		force = 2D;
                	if (entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isFlying)
                		force *= 0.5D;
                    double d2 = this.posX - entity.posX;
                    double d3 = (this.posY + this.getWitherScale()) - entity.posY;
                    double d4 = this.posZ - entity.posZ;
                    double d5 = d2 * d2 + d3 * d3 + d4 * d4;
                    entity.addVelocity(d2 / d5 * force, d3 / d5 * force, d4 / d5 * force);
                    if (entity instanceof EntityPlayerMP)
                    {
                    	((EntityPlayerMP)entity).connection.sendPacket(new SPacketEntityVelocity(entity.getEntityId(), entity.motionX, entity.motionY, entity.motionZ));
                    }
                }
            }
            
        	if (this.getWatchedTargetId(0) > 0)
        	{
                Entity entity = this.world.getEntityByID(this.getWatchedTargetId(0));
                
              if (entity != null)
              {

              	if (!this.canEntityBeSeen(entity) && this.ticksExisted % 120 == 0)
              		((IRangedAttackMob)this).attackEntityWithRangedAttack((EntityLivingBase)entity, 0.0F);
              	
              	if (this.world.getEntityByID(this.getWatchedTargetId(1)) == null && rand.nextInt(40) == 0)
              		this.updateWatchedTargetId(1, entity.getEntityId());
              	
              	if (this.world.getEntityByID(this.getWatchedTargetId(2)) == null && rand.nextInt(40) == 0)
              		this.updateWatchedTargetId(2, entity.getEntityId());
              }
        	}
        	else if (this.ticksExisted % 40 == 0)
        	{
        		EntityPlayer screwedman = this.world.getClosestPlayerToEntity(this, 100D);
        		
        		if (screwedman != null && this.posY < -60 && screwedman.posY < -30 && (rand.nextInt(20) == 0 || screwedman.posY < -60))
        		{
        			this.setAttackTarget(screwedman);
        		}
        	}
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
            EntityXPOrb orb = new EntityXPOrb(this.world, this.posX, this.posY + this.getEyeHeight(), this.posZ, i);
            orb.setEntityInvulnerable(true);
            this.world.spawnEntity(orb);
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
    	
        this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.BLOCKS, deathTicks * 0.01F, deathTicks * 0.01F);
        this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.BLOCK_CHORUS_FLOWER_DEATH, SoundCategory.BLOCKS, deathTicks * 0.01F, deathTicks * 0.01F);

    	this.renderYawOffset = this.rotationYaw = this.rotationYawHead;
    	this.rotationPitch = 60F - (deathTicks / 4);
    	rotationPitch += 5F;
    	
        EntityPlayer player = this.world.getClosestPlayer(posX, posY + this.getEyeHeight(), posZ, -1D, EntitySelectors.IS_ALIVE);
        
        if (player != null && this.deathTicks < 500)
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
    	
    	for (int i = 0; i < deathTicks; ++i)
        this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX, this.posY + this.getEyeHeight(), this.posZ, rand.nextGaussian() * 2, rand.nextGaussian() + 1D, rand.nextGaussian() * 2);
        
        if (this.deathTicks >= 380)
        {
            float f = (this.rand.nextFloat() - 0.5F) * width;
            float f1 = (this.rand.nextFloat() - 0.5F) * height;
            float f2 = (this.rand.nextFloat() - 0.5F) * width;
            this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX + (double)f, this.posY + 10D + (double)f1, this.posZ + (double)f2, 0.0D, 0.0D, 0.0D);
        }

        boolean flag = this.world.getGameRules().getBoolean("doMobLoot");
        int i = this.experienceValue;

        if (!this.world.isRemote)
        {
            if (flag)
            {
            	this.entityDropItem(new ItemStack(MItems.NETHER_STAR_SHARD, 1 + rand.nextInt(4)), this.getEyeHeight());
                
                if (this.deathTicks > 450 && flag)
                {
                    this.dropExperience(MathHelper.floor((float)i * 0.016F));
                	this.entityDropItem(new ItemStack(Items.NETHER_STAR, 1 + rand.nextInt(4)), this.getEyeHeight());
                }
            }
            
            if (this.deathTicks == 320)
            {
                this.playHurtSound(null);
                this.world.newExplosion(this, this.getHeadX(2), this.getHeadY(2), this.getHeadZ(2), 14F, false, net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this));
                if (flag)
                {
                    this.dropExperience(MathHelper.floor((float)i * 0.2F));
                }
            }
            
            if (this.deathTicks == 360)
            {
                this.playHurtSound(null);
                this.world.newExplosion(this, this.getHeadX(1), this.getHeadY(1), this.getHeadZ(1), 14F, false, net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this));
                if (flag)
                {
                    this.dropExperience(MathHelper.floor((float)i * 0.2F));
                }
            }

            if (this.deathTicks == 1)
            {
                this.broadcastSound(this.getDeathSound(), this.getSoundPitch());
            }
        }

        if (this.deathTicks == 500 && !this.world.isRemote)
        {
            if (flag)
            {
                this.dropExperience(MathHelper.floor((float)i * 0.2F));
            	this.entityDropItem(new ItemStack(MBlocks.NETHER_STAR_BLOCK, 1 + rand.nextInt(4)), this.getEyeHeight());
            }

            this.world.newExplosion(this, this.getHeadX(0), this.getHeadY(0), this.getHeadZ(0), 14F, false, net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this));
            this.broadcastSound(this.getDeathSound(), this.getSoundPitch());
            this.broadcastSound(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, 0.75F);
            this.broadcastSound(SoundEvents.AMBIENT_CAVE, 0.5F);
            this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 10.0F, 0.5F);

            this.isDead = true;
        }
    	}
    }

	public EnumLevel getTier() 
	{
		return EnumLevel.TEMPLAR;
	}
    
	public int[] getBarColor() 
	{
		return new int[] {0, 0, 0, 0, this.isArmored() || this.isSuperBoss() ? 122 : 0, this.isArmored() || this.isSuperBoss() ? 255 : 0};
	}
}