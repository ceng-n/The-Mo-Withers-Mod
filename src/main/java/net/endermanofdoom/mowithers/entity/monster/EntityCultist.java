package net.endermanofdoom.mowithers.entity.monster;

import java.util.List;

import javax.annotation.Nullable;
import net.endermanofdoom.mca.entity.projectile.EntityWitherSkullShared;
import net.endermanofdoom.mca.events.MCAWorldData;
import net.endermanofdoom.mca.registrey.MCAItems;
import net.endermanofdoom.mac.enums.EnumGender;
import net.endermanofdoom.mac.interfaces.IBossBar;
import net.endermanofdoom.mac.interfaces.IGendered;
import net.endermanofdoom.mac.interfaces.IVariedMob;
import net.endermanofdoom.mca.MinecraftAdventures;
import net.endermanofdoom.mca.entity.IWitherMob;
import net.endermanofdoom.mca.entity.boss.*;
import net.endermanofdoom.mowithers.MoWithers;
import net.endermanofdoom.mowithers.entity.wither.*;
import net.endermanofdoom.mowithers.registry.MItems;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.AbstractIllager;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntitySpellcasterIllager;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("deprecation")
public class EntityCultist extends EntitySpellcasterIllager implements IVariedMob, IWitherMob, IGendered, IBossBar
{
    private static final DataParameter<Byte> CLIMBING = EntityDataManager.<Byte>createKey(EntityCultist.class, DataSerializers.BYTE);
    private static final DataParameter<Integer> VARIANT = EntityDataManager.<Integer>createKey(EntityCultist.class, DataSerializers.VARINT);
    protected int shouldUseDynamicAI;
    protected int switchAI;
    
    public EntityCultist(World worldIn)
    {
        super(worldIn);
        this.setSize(0.6F, 1.95F);
        ((PathNavigateGround)this.getNavigator()).setBreakDoors(true);
    }

    protected void initEntityAI()
    {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntitySpellcasterIllager.AICastingApell());
        this.tasks.addTask(2, new EntityCultist.AIPhase2Spell());
        this.tasks.addTask(2, new EntityCultist.AISummonSpell());
        this.tasks.addTask(2, new EntityCultist.AISkullSpell());
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.6D));
        this.tasks.addTask(8, new EntityAIWander(this, 0.6D));
        this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] {EntityCultist.class}));
        this.targetTasks.addTask(2, new net.endermanofdoom.mca.entity.ai.EntityAINearestAttackableTargetInCube<EntityLivingBase>(this, EntityLivingBase.class, MoWithers.NON_WITHER_OR_NETHER_MOB));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue((this.getVariant() + 1) * 24D);
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(CLIMBING, Byte.valueOf((byte)0));
        this.dataManager.register(VARIANT, Integer.valueOf(0));
    }

    /**
     * Returns new PathNavigateGround instance
     */
    protected PathNavigate createNavigator(World worldIn)
    {
        return new PathNavigateClimber(this, worldIn);
    }

    /**
     * Dead and sleeping entities cannot move
     */
    protected boolean isMovementBlocked()
    {
        return this.getHealth() <= 0.0F;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();
        
        if (world.getDifficulty() == EnumDifficulty.PEACEFUL && this.isNonBoss() && this.isNoDespawnRequired())
        	isDead = true;
        
        this.isImmuneToFire = this.getVariant() > 1 ? true : false;
    	if (getVariant() > 1)
    	{
            this.motionY *= 0.6D;
            if (!this.onGround)
            {
                this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX + this.rand.nextGaussian() * 0.25D, posY, posZ + this.rand.nextGaussian() * 0.25D, 0.0D, 0.0D, 0.0D);
            	  if (shouldUseDynamicAI < 2)
              	  {
              		  if (this.getAttackTarget() != null)
                  	  this.faceEntity(getAttackTarget(), 180F, this.getVerticalFaceSpeed());
                  	  renderYawOffset = rotationYaw = rotationYawHead;
                  	  limbSwing = limbSwingAmount = 0;
              	  }
            }
            this.idleTime = 0;
    	}
        
        if (!this.world.isRemote)
        {
            this.setBesideClimbableBlock(this.collidedHorizontally);
            
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(24D));

            for (Entity entity : list)
            {
                if (!entity.getIsInvulnerable() && entity instanceof EntityLivingBase && (entity instanceof AbstractIllager || entity instanceof EntityVillager || entity instanceof EntityWitch) && !(entity instanceof EntityCultist) && this.rand.nextInt(1000) == 0)
                {
                	((EntityLivingBase) entity).setHealth(((EntityLivingBase) entity).getHealth() - 1F);
                	((EntityLivingBase) entity).playSound(SoundEvents.ENTITY_ZOMBIE_VILLAGER_CONVERTED, 0.25F, 2F);
                	this.playLivingSound();
                	this.getNavigator().getPathToEntityLiving((EntityLivingBase) entity);
                    if (!entity.isEntityAlive() && !MinecraftAdventures.isWitherMob((EntityLivingBase) entity))
                    	this.onKillEntity((EntityLivingBase) entity);
                }
            }
            
            if (this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue() != this.getMobHealth())
            {
                this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(getMobHealth());
                this.setHealth((float) getMobHealth());
            }
            if (this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue() != this.getMobAttack())
            	this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(getMobAttack());

            if (this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue() != this.getMobSpeed())
            	this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(getMobSpeed());
            
            if (this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getBaseValue() != (this.getVariant() + 1) * 24D)
            	this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue((this.getVariant() + 1) * 24D);
            
        	this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(this.getVariant() > 2 ? 1D : 0D);
        }

        if (this.isOnLadder())
        {
        	this.limbSwingAmount = 1F;
        	if (this.getAttackTarget() != null)
        	this.getLookHelper().setLookPosition(getAttackTarget().posX, getAttackTarget().posY + 20D, getAttackTarget().posZ, 180F, 75F);
        }
        
        if (getVariant() > 2 && this.getHealth() <= 2)
        {
        	this.getNavigator().clearPath();
        	this.setHealth(1);
        	this.motionX = this.motionY = this.motionZ = 0;
        }
        
		switch (this.getVariant())
		{
		case 1:
	        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_HOE));
	        break;
		case 2:
	        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_HOE));
	        break;
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
	        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(MCAItems.NETHERITE_SET[4]));
	        break;
		default:
	        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.STONE_HOE));
		}
    }

    /**
     * Returns true if the WatchableObject (Byte) is 0x01 otherwise returns false. The WatchableObject is updated using
     * setBesideClimableBlock.
     */
    public boolean isBesideClimbableBlock()
    {
        return (((Byte)this.dataManager.get(CLIMBING)).byteValue() & 1) != 0;
    }

    /**
     * Updates the WatchableObject (Byte) created in entityInit(), setting it to 0x01 if par1 is true or 0x00 if it is
     * false.
     */
    public void setBesideClimbableBlock(boolean climbing)
    {
        byte b0 = ((Byte)this.dataManager.get(CLIMBING)).byteValue();

        if (climbing)
        {
            b0 = (byte)(b0 | 1);
        }
        else
        {
            b0 = (byte)(b0 & -2);
        }

        this.dataManager.set(CLIMBING, Byte.valueOf(b0));
    }

    /**
     * Returns true if this entity should move as if it were on a ladder (either because it's actually on a ladder, or
     * for AI reasons)
     */
    public boolean isOnLadder()
    {
        return this.isBesideClimbableBlock();
    }

    protected ResourceLocation getLootTable()
    {
        return LootTableList.ENTITIES_VINDICATION_ILLAGER;
    }

    @SideOnly(Side.CLIENT)
    public boolean isAggressive()
    {
        return this.isAggressive(1);
    }

    public void setAggressive(boolean p_190636_1_)
    {
        this.setAggressive(1, p_190636_1_);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("Variant", this.getVariant());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.setVariant(compound.getInteger("Variant"));
    }

    @SideOnly(Side.CLIENT)
    public AbstractIllager.IllagerArmPose getArmPose()
    {
        return this.isBesideClimbableBlock() || this.isSpellcasting() ? AbstractIllager.IllagerArmPose.SPELLCASTING : this.isAggressive() ? (this.shouldUseDynamicAI >= 2 || this.getVariant() < 2 ? AbstractIllager.IllagerArmPose.ATTACKING : AbstractIllager.IllagerArmPose.BOW_AND_ARROW) : AbstractIllager.IllagerArmPose.CROSSED;
    }

    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        IEntityLivingData ientitylivingdata = super.onInitialSpawn(difficulty, livingdata);
        if (rand.nextInt(20) == 0)
        	this.setVariant(1);
        this.setEquipmentBasedOnDifficulty(difficulty);
        return ientitylivingdata;
    }

    /**
     * Gives armor or weapon for entity based on given DifficultyInstance
     */
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
		switch (this.getVariant())
		{
		case 1:
	        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_HOE));
	        break;
		case 2:
	        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_HOE));
	        break;
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
	        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(MItems.ATROPHIC_HOE));
	        break;
		default:
	        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.STONE_HOE));
		}
    }
    
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
        this.world.playEvent((EntityPlayer)null, 1024, new BlockPos(this), 0);
        double d0 = this.posX;
        double d1 = this.posY + this.getEyeHeight();
        double d2 = this.posZ;
        double d3 = target.posX - d0;
        double d4 = (target.posY + 0.4D) - d1;
        double d5 = target.posZ - d2;
        EntityWitherSkullShared skull = new EntityWitherSkullShared(this.world, this, d3, d4, d5);
        skull.setDamage((float) getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue());
        skull.setRadius(1F);
        skull.posY = d1;
        skull.posX = d0;
        skull.posZ = d2;
        skull.noClip = true;
        skull.setMod(MinecraftAdventures.MODID);
        skull.setSkullTexture("boss/wither");
        skull.setDeflect(true);
        this.world.spawnEntity(skull);
    }

    protected void updateAITasks()
    {
        super.updateAITasks();
        this.setAggressive(this.getAttackTarget() != null);
        
    	if (getVariant() > 1)
    	{
            if (!this.isArmored())
            {
                if (this.getAttackTarget() != null)
                {
            	if (++this.switchAI >= (shouldUseDynamicAI > 2 ? 600 : 400) && !this.isRiding())
            	{
            		this.switchAI = 0;
            		++shouldUseDynamicAI;

        			if (shouldUseDynamicAI >= 3)
            			shouldUseDynamicAI = 0;
            	}
            	
            	if (shouldUseDynamicAI < 2)
        	  for (int i = 0; i < 8; i++)
              {
        		  int in = MathHelper.floor(this.posX);
        		  int j = MathHelper.floor(this.posY + 2.0D - this.rand.nextDouble() * 4D);
        		  int k = MathHelper.floor(this.posZ);
        		  BlockPos blockpos = new BlockPos(in, j, k);
        		  IBlockState iblockstate = this.world.getBlockState(blockpos);
        		  if (!this.world.isRemote && (iblockstate.isFullCube() || blockpos.getY() <= 0))
       	         {
       	          if (this.motionY < 0.0D)
       	            this.motionY = 0.0D;
       	          
       	          this.motionY += (0.6D - this.motionY) * 0.6D;
                }
              }
        	  if (!this.isSpellcasting() && shouldUseDynamicAI < 2)
        	  {
                  double d0 = getAttackTarget().posX - this.posX;
                  double d1 = getAttackTarget().posZ - this.posZ;
                  double d3 = d0 * d0 + d1 * d1;
                  double d5 = (double)MathHelper.sqrt(d3);
                  
        		  if (d3 > 20D)
        		  {
                      this.motionX += (d0 / d5 * 0.6D - this.motionX) * 0.6D;
                      this.motionZ += (d1 / d5 * 0.6D - this.motionZ) * 0.6D;
        		  }
        		  else if (d3 <= 4D && getAttackTarget().onGround)
        		  {
                      this.motionX -= (d0 / d5 * 0.6D + this.motionX) * 0.6D;
                      this.motionZ -= (d1 / d5 * 0.6D + this.motionZ) * 0.6D;
        		  }
        		  else
        			  this.moveStrafing = shouldUseDynamicAI == 1 ? -1F : 1F;
        	  }
                }
			  this.moveStrafing *= 0.95F;
			  
            }
            else
            {
                if (this.getAttackTarget() != null && this.getDistance(getAttackTarget()) <= 2 + getAttackTarget().width && this.ticksExisted % 10 == 0)
                {
                	this.attackEntityAsMob(getAttackTarget());
                	this.playLivingSound();
                }
            }
    	}
    }

    /**
     * Returns whether this Entity is on the same team as the given Entity.
     */
    public boolean isOnSameTeam(Entity entityIn)
    {
        if (super.isOnSameTeam(entityIn))
        {
            return true;
        }
        else if (entityIn instanceof EntityLivingBase && MinecraftAdventures.isWitherMob((EntityLivingBase)entityIn))
        {
            return true;
        }
        else if (entityIn instanceof EntityLivingBase && (((EntityLivingBase)entityIn).getCreatureAttribute() == EnumCreatureAttribute.ILLAGER || entityIn instanceof EntityWitch))
        {
            return this.getTeam() == null && entityIn.getTeam() == null;
        }
        else
        {
            return false;
        }
    }
    
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (source == DamageSource.WITHER)
        {
          this.heal(amount);
          return false;
        }
        else if (getVariant() > 2 && this.getHealth() <= 2)
        {
          return false;
        }
        else
        {
        	if (source.getTrueSource() != null && source.getTrueSource() instanceof EntityLivingBase && MinecraftAdventures.isWitherMob((EntityLivingBase)source.getTrueSource()))
        		return false;
        	
        	return super.attackEntityFrom(source, amount);
        }
    }

    protected SoundEvent getAmbientSound()
    {
        return this.getVariant() > 1 ? (this.isArmored() ? SoundEvents.ENTITY_ILLUSION_ILLAGER_AMBIENT : SoundEvents.ENTITY_EVOCATION_ILLAGER_AMBIENT) : SoundEvents.VINDICATION_ILLAGER_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return this.getVariant() > 1 ? SoundEvents.ENTITY_EVOCATION_ILLAGER_HURT : SoundEvents.ENTITY_VINDICATION_ILLAGER_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        return this.getVariant() > 1 ? SoundEvents.EVOCATION_ILLAGER_DEATH : SoundEvents.VINDICATION_ILLAGER_DEATH;
    }

    public void fall(float distance, float damageMultiplier)
    {
        float[] ret = net.minecraftforge.common.ForgeHooks.onLivingFall(this, distance, damageMultiplier);
        if (ret == null) return;
        distance = ret[0]; damageMultiplier = ret[1];
        if (this.isBeingRidden())
        {
            for (Entity entity : this.getPassengers())
            {
                entity.fall(distance, damageMultiplier);
            }
        }
        PotionEffect potioneffect = this.getActivePotionEffect(MobEffects.JUMP_BOOST);
        float f = potioneffect == null ? 0.0F : (float)(potioneffect.getAmplifier() + 1);
        int i = MathHelper.ceil((distance - 3.0F - f) * damageMultiplier);

        if (i > 0)
        {
            this.playSound(this.getFallSound(i), 1.0F, 1.0F);
            this.getNavigator().clearPath();
            int j = MathHelper.floor(this.posX);
            int k = MathHelper.floor(this.posY - 0.20000000298023224D);
            int l = MathHelper.floor(this.posZ);
            IBlockState iblockstate = this.world.getBlockState(new BlockPos(j, k, l));

            if (iblockstate.getMaterial() != Material.AIR)
            {
                SoundType soundtype = iblockstate.getBlock().getSoundType(iblockstate, world, new BlockPos(j, k, l), this);
                this.playSound(soundtype.getFallSound(), soundtype.getVolume() * 0.5F, soundtype.getPitch() * 0.75F);
            }
        }
    }
    
    /**
     * This method gets called when the entity kills another one.
     */
    public void onKillEntity(EntityLivingBase entityLivingIn)
    {
        super.onKillEntity(entityLivingIn);

    	this.heal(5);
    	
        if (entityLivingIn instanceof EntityVillager)
        {

            EntityVillager killed = (EntityVillager)entityLivingIn;
            EntityCultist ressed = new EntityCultist(this.world);
            ressed.copyLocationAndAnglesFrom(killed);
            this.world.removeEntity(killed);
            ressed.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(ressed)), null);
            ressed.setNoAI(killed.isAIDisabled());
            ressed.setVariant(0);

            if (killed.hasCustomName())
            {
                ressed.setCustomNameTag(killed.getCustomNameTag());
                ressed.setAlwaysRenderNameTag(killed.getAlwaysRenderNameTag());
            }

            this.world.spawnEntity(ressed);
            this.world.playEvent((EntityPlayer)null, 1024, new BlockPos(this), 0);
        }
        
        if (entityLivingIn instanceof EntityWitch)
        {

        	EntityWitch killed = (EntityWitch)entityLivingIn;
            EntityCultist ressed = new EntityCultist(this.world);
            ressed.copyLocationAndAnglesFrom(killed);
            this.world.removeEntity(killed);
            ressed.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(ressed)), null);
            ressed.setNoAI(killed.isAIDisabled());
            ressed.setVariant(1);

            if (killed.hasCustomName())
            {
                ressed.setCustomNameTag(killed.getCustomNameTag());
                ressed.setAlwaysRenderNameTag(killed.getAlwaysRenderNameTag());
            }

            this.world.spawnEntity(ressed);
            this.world.playEvent((EntityPlayer)null, 1024, new BlockPos(this), 0);
        }
        
        if (entityLivingIn instanceof AbstractIllager && !(entityLivingIn instanceof EntityCultist))
        {
        	AbstractIllager killed = (AbstractIllager)entityLivingIn;
            EntityCultist ressed = new EntityCultist(this.world);
            ressed.copyLocationAndAnglesFrom(killed);
            this.world.removeEntity(killed);
            ressed.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(ressed)), null);
            ressed.setNoAI(killed.isAIDisabled());
            ressed.setVariant(0);

            if (killed.hasCustomName())
            {
                ressed.setCustomNameTag(killed.getCustomNameTag());
                ressed.setAlwaysRenderNameTag(killed.getAlwaysRenderNameTag());
            }
            
            if (killed instanceof EntityEvoker)
            {
            	killed.dropItem(Items.TOTEM_OF_UNDYING, 1);
                ressed.setVariant(1);
            }

            this.world.spawnEntity(ressed);
            this.world.playEvent((EntityPlayer)null, 1024, new BlockPos(this), 0);
        }
    }

	public void setVariant(int type) 
	{
        this.dataManager.set(VARIANT, Integer.valueOf(type));		
	}

	public int getVariant() 
	{
        return ((Integer)this.dataManager.get(VARIANT)).intValue();
	}

	public double getMobHealth() 
	{
		EnumDifficulty diff = world.getDifficulty();
		
		double hp = 200D;
		
		if (diff == EnumDifficulty.NORMAL)
			hp *= 1.5D;
		
		if (diff == EnumDifficulty.HARD)
			hp *= 2D;
		
		switch (this.getVariant())
		{
		case 1:
			return 100D;
		case 2:
			return hp;
		case 3:
			return hp * 2;
		case 4:
			return hp * 6;
		case 5:
			return hp * 12;
		case 6:
			return hp * 20;
		case 7:
			return hp * 40;
		default:
			return 30D;
		}
	}
	
    public void setDead() 
    {
    	if (world.getDifficulty() != EnumDifficulty.PEACEFUL || (world.getDifficulty() == EnumDifficulty.PEACEFUL && this.isNonBoss()) || this.getHealth() <= 0)
    		super.setDead();
    }

	public double getMobAttack() 
	{
		switch (this.getVariant())
		{
		case 1:
			return 6D;
		case 2:
			return 8D;
		case 3:
			return 12D;
		case 4:
			return 18D;
		case 5:
			return 40D;
		case 6:
			return 60D;
		case 7:
			return 90D;
		default:
			return 4D;
		}
	}

	public double getMobSpeed() 
	{
		switch (this.getVariant())
		{
		case 1:
			return 0.37D;
		case 2:
			return 0.38D;
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
			return this.isArmored() ? 0.5D : 0.4D;
		default:
			return 0.36D;
		}
	}
    /**
     * Get the name of this object. For players this returns their username
     */
    public String getName()
    {
        if (this.hasCustomName())
        {
            return this.getCustomNameTag();
        }
        else
        {
    		switch (this.getVariant())
    		{
    		case 1:
                return I18n.translateToLocal("entity.cultist_greater.name");
    		case 2:
                return TextFormatting.AQUA + (hasCustomName() ? getCustomNameTag() : I18n.translateToLocal("entity.cultist_leader.name")) + TextFormatting.WHITE + " " + MinecraftAdventures.parseFloat(getHealth() + this.getAbsorptionAmount());
    		case 3:
    		case 4:
    		case 5:
    		case 6:
    		case 7:
                return TextFormatting.LIGHT_PURPLE + (hasCustomName() ? getCustomNameTag() : I18n.translateToLocal("entity.cultist_leader_sec.name")) + TextFormatting.WHITE + " " + MinecraftAdventures.parseFloat(getHealth() + this.getAbsorptionAmount());
    		default:
                return I18n.translateToLocal("entity.cultist_lesser.name");
    		}
        }
    }

    protected SoundEvent getSpellSound()
    {
        return SoundEvents.EVOCATION_ILLAGER_CAST_SPELL;
    }
    
    protected int getSpellTicks()
    {
        return this.getVariant() > 0 ? super.getSpellTicks() : 0;
    }
    
    public void setHealth(float health)
    {
        if (getVariant() > 2 && health <= 1)
        	health = 1;
        super.setHealth(health >= this.getMaxHealth() ? this.getMaxHealth() : (isArmored() && health >= this.getMaxHealth() / 2 ? this.getMaxHealth() / 2 : health));
    }
    
    public void die()
    {
        super.setHealth(0F);
    }
    
    public boolean isArmored()
    {
        return getVariant() > 1 && this.getHealth() <= this.getMaxHealth() * 0.5D;
    }
    
	public boolean isNonBoss()
	{
		return getVariant() <= 1;
	}

	public EnumGender getGender() 
	{
		return EnumGender.MALE;
	}
	
	@Override
	public boolean canRenderBar()
	{
		return !this.isNonBoss();
	}
	
	@Override
	public double getBarHealth()
	{
		return getHealth();
	}

	@Override
	public double getBarMaxHealth()
	{
		return getMaxHealth();
	}

	@Override
	public String getBarName()
	{
		return getDisplayName().getFormattedText();
	}

	@Override
	public boolean isDead()
	{
		return isDead;
	}
    
	public int[] getBarColor() 
	{
		return new int[] {255, 55, 200, 0, 0, 0};
	}
	
    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource cause)
    {
        if (!world.isRemote && getVariant() == 2)
    	MCAWorldData.progress.setBoolean("killedCardinal", true);

        super.onDeath(cause);
    }
    
    class AIPhase2Spell extends EntitySpellcasterIllager.AIUseSpell
    {
        private AIPhase2Spell()
        {
            super();
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
            return EntityCultist.this.getVariant() > 2 && EntityCultist.this.getHealth() <= 1 && EntityCultist.this.isSpellcasting() && EntityCultist.this.ticksExisted >= this.spellCooldown;
        }

        protected int getCastingTime()
        {
            return 100;
        }

        protected int getCastingInterval()
        {
            return 100;
        }

        protected void castSpell()
        {
        	EntityBaseWither better;
        	
        	switch (EntityCultist.this.getVariant())
        	{
        	case 4:
        		better = new EntityWitherLightning(world);
        		break;
        	case 5:
        		better = new EntityWitherStone(world);
        		break;
        	case 6:
        		better = new EntityWitherObsidian(world);
        		break;
        	case 7:
        		better = new EntityWitherAvatar(world);
        		break;
        	default:
        		better = new EntityWitherNormal(world);
        	}
        	
        	better.copyLocationAndAnglesFrom(EntityCultist.this);
        	better.setIsViticus(true);
        	EntityCultist.this.die();
        	EntityCultist.this.playSound(EntityCultist.this.getDeathSound(), EntityCultist.this.getSoundVolume(), EntityCultist.this.getSoundPitch());
        	world.removeEntity(EntityCultist.this);
            world.spawnEntity(better);
            better.ignite();
            world.addWeatherEffect(new EntityLightningBolt(world, better.posX, better.posY + 2D, better.posZ, false));
        }

        protected SoundEvent getSpellPrepareSound()
        {
            return SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE;
        }

        protected EntitySpellcasterIllager.SpellType getSpellType()
        {
            return EntitySpellcasterIllager.SpellType.WOLOLO;
        }
    }
    
    class AISummonSpell extends EntitySpellcasterIllager.AIUseSpell
    {
        private AISummonSpell()
        {
            super();
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
            if (!super.shouldExecute())
            {
                return false;
            }
            else
            {
                return EntityCultist.this.getVariant() > 0;
            }
        }

        protected int getCastingTime()
        {
            return 40;
        }

        protected int getCastingInterval()
        {
            return 800;
        }

        protected void castSpell()
        {
            for (int i = 0; i < EntityCultist.this.getVariant(); ++i)
            {
                BlockPos blockpos = (new BlockPos(EntityCultist.this)).add(-4 + EntityCultist.this.rand.nextInt(8), 1, -4 + EntityCultist.this.rand.nextInt(8));
                EntityCultist backup = new EntityCultist(EntityCultist.this.world);
                backup.moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
                backup.onInitialSpawn(EntityCultist.this.world.getDifficultyForLocation(blockpos), (IEntityLivingData)null);
                backup.setVariant(0);
                EntityCultist.this.world.spawnEntity(backup);
            }
            
            if (EntityCultist.this.getVariant() > 2)
                for (int i = 0; i < 2; ++i)
                {
                    BlockPos blockpos = (new BlockPos(EntityCultist.this)).add(-4 + EntityCultist.this.rand.nextInt(8), 1, -4 + EntityCultist.this.rand.nextInt(8));
                    EntityCultist backup = new EntityCultist(EntityCultist.this.world);
                    backup.moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
                    backup.onInitialSpawn(EntityCultist.this.world.getDifficultyForLocation(blockpos), (IEntityLivingData)null);
                    backup.setVariant(1);
                    EntityCultist.this.world.spawnEntity(backup);
                }
        }

        protected SoundEvent getSpellPrepareSound()
        {
            return SoundEvents.ENTITY_ILLAGER_PREPARE_BLINDNESS;
        }

        protected EntitySpellcasterIllager.SpellType getSpellType()
        {
            return EntitySpellcasterIllager.SpellType.BLINDNESS;
        }
    }
    
    class AISkullSpell extends EntitySpellcasterIllager.AIUseSpell
    {
        private AISkullSpell()
        {
            super();
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
            if (!super.shouldExecute())
            {
                return false;
            }
            else
            {
                return EntityCultist.this.getVariant() > 0 && (EntityCultist.this.shouldUseDynamicAI < 2 || EntityCultist.this.getVariant() == 1);
            }
        }

        protected int getCastingTime()
        {
            return 20;
        }

        protected int getCastingInterval()
        {
            return EntityCultist.this.getVariant() > 1 ? 80 : 160;
        }

        protected void castSpell()
        {
        	for (int i = 0; i < EntityCultist.this.getVariant(); ++i)
        	EntityCultist.this.attackEntityWithRangedAttack(EntityCultist.this.getAttackTarget(), 0);
        }

        protected SoundEvent getSpellPrepareSound()
        {
            return SoundEvents.ENTITY_ILLAGER_PREPARE_BLINDNESS;
        }

        protected EntitySpellcasterIllager.SpellType getSpellType()
        {
            return EntitySpellcasterIllager.SpellType.FANGS;
        }
    }
}