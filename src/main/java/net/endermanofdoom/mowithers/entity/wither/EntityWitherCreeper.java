package net.endermanofdoom.mowithers.entity.wither;

import java.util.Collection;
import java.util.List;
import javax.annotation.Nullable;
import com.google.common.base.Predicate;

import net.endermanofdoom.mca.entity.boss.EntityHostileWither;
import net.endermanofdoom.mca.entity.EnumWitherType;
import net.endermanofdoom.mca.entity.projectile.EntityWitherSkullShared;
import net.endermanofdoom.mowithers.MoWithers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.BossInfo;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityWitherCreeper extends EntityHostileWither
{
    private static final DataParameter<Integer> STATE = EntityDataManager.<Integer>createKey(EntityWitherCreeper.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> POWERED = EntityDataManager.<Boolean>createKey(EntityWitherCreeper.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IGNITED = EntityDataManager.<Boolean>createKey(EntityWitherCreeper.class, DataSerializers.BOOLEAN);
    /**
     * Time when this creeper was last in an active state (Messed up code here, probably causes creeper animation to go
     * weird)
     */
    private int lastActiveTime;
    /** The amount of time since the creeper was close enough to the player to ignite */
    private int timeSinceIgnited;
    private int fuseTime = 100;
    
	public EntityWitherCreeper(World worldIn) 
	{
		super(worldIn);
		this.experienceValue *= 6;
	}
    
    public TextFormatting getNameColor()
    {
    	return this.getPowered() ? TextFormatting.AQUA : TextFormatting.GREEN;
    }
    
    public EnumWitherType getWitherType()
    {
        return EnumWitherType.MOB;
    }
	
	protected void initEntityAI()
    {
		WITHERTARGETS = new Predicate<Entity>()
	    {
	        public boolean apply(@Nullable Entity p_apply_1_)
	        {
	            return p_apply_1_ instanceof EntityLivingBase && ((EntityLivingBase)p_apply_1_).attackable() && p_apply_1_.getClass() != EntityCreeper.class && !(p_apply_1_ instanceof EntityWitherCreeper);
	        }
	    };
        super.initEntityAI();
        this.targetTasks.addTask(2, new EntityAIWitherTargeting<EntityLivingBase>(this, EntityLivingBase.class, WITHERTARGETS));
    }
	
    /**
     * Params: (Float)Render tick. Returns the intensity of the creeper's flash when it is ignited.
     */
    @SideOnly(Side.CLIENT)
    public float getCreeperFlashIntensity(float p_70831_1_)
    {
        return ((float)this.lastActiveTime + (float)(this.timeSinceIgnited - this.lastActiveTime) * p_70831_1_) / (float)(this.fuseTime - 2);
    }
    
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(STATE, Integer.valueOf(-1));
        this.dataManager.register(POWERED, Boolean.valueOf(false));
        this.dataManager.register(IGNITED, Boolean.valueOf(false));
    }    

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);

        if (((Boolean)this.dataManager.get(POWERED)).booleanValue())
        {
            compound.setBoolean("powered", true);
        }

        compound.setShort("Fuse", (short)this.fuseTime);
        compound.setBoolean("ignited", this.hasIgnited());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.dataManager.set(POWERED, Boolean.valueOf(compound.getBoolean("powered")));

        if (compound.hasKey("Fuse", 99))
        {
            this.fuseTime = compound.getShort("Fuse");
        }

        if (compound.getBoolean("ignited"))
        {
            this.ignite();
        }
    }
    
    public boolean canAttackClass(Class <? extends EntityLivingBase > cls)
    {
        return super.canAttackClass(cls) && cls != EntityCreeper.class;
    }

    /**
     * Returns true if the creeper is powered by a lightning bolt.
     */
    public boolean getPowered()
    {
        return ((Boolean)this.dataManager.get(POWERED)).booleanValue();
    }

    public boolean hasIgnited()
    {
        return ((Boolean)this.dataManager.get(IGNITED)).booleanValue();
    }

    public void ignite()
    {
        this.dataManager.set(IGNITED, Boolean.valueOf(true));
    }

    /**
     * Called when a lightning bolt hits the entity.
     */
    public void onStruckByLightning(EntityLightningBolt lightningBolt)
    {
        super.onStruckByLightning(lightningBolt);
        this.dataManager.set(POWERED, Boolean.valueOf(true));
    }
    
    /**
     * This method gets called when the entity kills another one.
     */
    public void onKillEntity(EntityLivingBase entityLivingIn)
    {
        super.onKillEntity(entityLivingIn);

        if (this.getPowered())
        {
        	if (entityLivingIn instanceof EntitySkeleton)
        		entityLivingIn.entityDropItem(new ItemStack(Items.SKULL, 1, 0), 0);
        	if (entityLivingIn instanceof EntityWitherSkeleton)
        		entityLivingIn.entityDropItem(new ItemStack(Items.SKULL, 1, 1), 0);
        	if (entityLivingIn instanceof EntityZombie)
        		entityLivingIn.entityDropItem(new ItemStack(Items.SKULL, 1, 2), 0);
        	if (entityLivingIn instanceof EntityPlayer)
        		entityLivingIn.entityDropItem(new ItemStack(Items.SKULL, 1, 3), 0);
        	if (entityLivingIn instanceof EntityCreeper)
        		entityLivingIn.entityDropItem(new ItemStack(Items.SKULL, 1, 4), 0);
        	if (entityLivingIn instanceof EntityDragon)
        		entityLivingIn.entityDropItem(new ItemStack(Items.SKULL, 1, 5), 0);
        }
    }
    
    public void setSkullStats(EntityWitherSkullShared skull, float damage, boolean invul)
    {
        super.setSkullStats(skull, damage, invul);
        skull.setRadius(this.getPowered() ? 6F : 3F);
        skull.setType(12);
		EntityCreeper mob = new EntityCreeper(world);
		mob.copyLocationAndAnglesFrom(this);
		mob.onInitialSpawn(world.getDifficultyForLocation(getPosition()), null);
		world.spawnEntity(mob);
		mob.startRiding(skull);
		mob.targetTasks.addTask(2, new EntityAIWitherTargeting<EntityLivingBase>(mob, EntityLivingBase.class, WITHERTARGETS));
		if (this.getPowered())
			mob.onStruckByLightning(null);
        skull.setSkullTexture("wither/mob/wither_creeper");
        skull.setMod(MoWithers.MODID);
    }

	public double getMobHealth() 
	{
		return super.getMobHealth() * 2.5D;
	}

	public double getMobAttack() 
	{
		return this.getPowered() ? super.getMobAttack() * 6D : super.getMobAttack() * 3D;
	}

	public double getMobSpeed() 
	{
		return 0.45D;
	}
    
    protected SoundEvent getAmbientSound()
    {
    	this.playSound(SoundEvents.ENTITY_WITHER_AMBIENT, getSoundVolume(), getSoundPitch() + 0.4F);
        return SoundEvents.ENTITY_CREEPER_HURT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
    	this.playSound(SoundEvents.ENTITY_WITHER_HURT, getSoundVolume(), getSoundPitch() + 0.4F);
        return SoundEvents.ENTITY_CREEPER_HURT;
    }

    protected SoundEvent getDeathSound()
    {
    	this.playSound(SoundEvents.ENTITY_WITHER_DEATH, getSoundVolume(), getSoundPitch() + 0.4F);
        return SoundEvents.ENTITY_CREEPER_DEATH;
    }

    /**
     * Returns true if this entity is undead.
     */
    public boolean isEntityUndead()
    {
        return false;
    }
    
    protected double getFlyHeight()
    {
        return 9D;
    }
    
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (source.isExplosion())
        {
          return false;
        }
        else
        {
        	return super.attackEntityFrom(source, amount);
        }
    }
    
    protected void onDeathUpdate()
    {
    	if (this.getWitherHealth() <= 0)
    	{
    	this.ignite();
    	
        this.lastActiveTime = this.timeSinceIgnited;

        if (this.hasIgnited())
        {
            this.setCreeperState(1);
        }

        int i = this.getCreeperState();

        if (i > 0 && this.timeSinceIgnited == 0)
        {
            this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 5F, 0.5F);
            this.playSound(SoundEvents.ENTITY_CREEPER_DEATH, 5F, 0.5F);
        }

        this.timeSinceIgnited += i;

        if (this.timeSinceIgnited < 0)
        {
            this.timeSinceIgnited = 0;
        }

        if (this.timeSinceIgnited >= this.fuseTime)
        {
            this.timeSinceIgnited = this.fuseTime;
            this.explode();
        }
    	}
    }
    
    @Nullable
    protected Item getDropItem()
    {
        return rand.nextInt(4) == 0 ? Item.getItemFromBlock(Blocks.TNT) : Items.GUNPOWDER;
    }
    
    /**
     * Creates an explosion as determined by this creeper's power and explosion radius.
     */
    private void explode()
    {
        if (!this.world.isRemote)
        {
            boolean flag = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this);
            float f = this.getPowered() ? 2.0F : 1.0F;
            this.dead = true;
            this.world.createExplosion(this, this.posX, this.posY, this.posZ, (float)21F * f, flag);
            this.setDead();
            this.spawnLingeringCloud();
            if (this.canDropLoot() && this.world.getGameRules().getBoolean("doMobLoot"))
            {
            	this.deathTicks = 2; 
            	this.dropXP(posX, posY + this.getEyeHeight(), posZ, this.getExperiencePoints(this.attackingPlayer));
                this.dropLoot(true, 0, getLastDamageSource());
            }
        }
    }

    private void spawnLingeringCloud()
    {
        Collection<PotionEffect> collection = this.getActivePotionEffects();

        if (!collection.isEmpty())
        {
            EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(this.world, this.posX, this.posY, this.posZ);
            entityareaeffectcloud.setRadius(10F);
            entityareaeffectcloud.setRadiusOnUse(-0.5F);
            entityareaeffectcloud.setWaitTime(10);
            entityareaeffectcloud.setDuration(entityareaeffectcloud.getDuration() / 20);
            entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / (float)entityareaeffectcloud.getDuration());

            for (PotionEffect potioneffect : collection)
            {
                entityareaeffectcloud.addEffect(new PotionEffect(potioneffect));
            }

            this.world.spawnEntity(entityareaeffectcloud);
        }
    }
    
    @SuppressWarnings({ "unlikely-arg-type" })
	public void onLivingUpdate()
    {
        super.onLivingUpdate();

		this.isImmuneToFire = this.getPowered();

        if (!this.world.isRemote && this.rand.nextInt(80) == 0)
        {
    		EntityCreeper mob = new EntityCreeper(world);
    		mob.copyLocationAndAnglesFrom(this);
    		mob.onInitialSpawn(world.getDifficultyForLocation(getPosition()), null);
    		world.spawnEntity(mob);
    		
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(200D));

            for (Entity entity : list)
            {
                if (entity.isEntityAlive() && entity instanceof EntityCreeper)
                {
                	EntityCreeper creepers = (EntityCreeper)entity;
                	
                	EntityAIWitherTargeting<EntityLivingBase> targeting = new EntityAIWitherTargeting<EntityLivingBase>(mob, EntityLivingBase.class, WITHERTARGETS);
                	
                	if (creepers.getAttackTarget() != null && creepers.getAttackTarget() == this)
                		creepers.setAttackTarget(null);
                	
                	if (!creepers.targetTasks.taskEntries.contains(targeting))
                		creepers.targetTasks.addTask(2, targeting);
                }
            }
        }
    }

    /**
     * Returns the current state of creeper, -1 is idle, 1 is 'in fuse'
     */
    public int getCreeperState()
    {
        return ((Integer)this.dataManager.get(STATE)).intValue();
    }

    /**
     * Sets the state of creeper, -1 to idle and 1 to be 'in fuse'
     */
    public void setCreeperState(int state)
    {
        this.dataManager.set(STATE, Integer.valueOf(state));
    }
    
    protected boolean hitType(DamageSource source, Entity entity)
    {
    	return super.hitType(source, entity) || entity.getClass() == EntityCreeper.class;
    }
    
    public boolean isOnSameTeam(Entity friend)
    {
    	if (friend instanceof EntityCreeper)
    		return true;
    	
    	return super.isOnSameTeam(friend);
    }
    
    protected float getSoundPitch()
    {
      return super.getSoundPitch() - 0.4F;
    }
}
