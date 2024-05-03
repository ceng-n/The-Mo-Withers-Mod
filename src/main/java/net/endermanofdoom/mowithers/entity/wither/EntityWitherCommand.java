package net.endermanofdoom.mowithers.entity.wither;

import javax.annotation.Nullable;

import net.endermanofdoom.mca.entity.boss.EntityHostileWither;
import net.endermanofdoom.mca.entity.EnumWitherType;
import net.endermanofdoom.mca.entity.projectile.EntityWitherSkullShared;
import net.endermanofdoom.mac.enums.EnumLevel;
import net.endermanofdoom.mowithers.MoWithers;
import net.endermanofdoom.mowithers.registry.MSounds;
import net.minecraft.command.*;
import net.minecraft.command.server.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class EntityWitherCommand extends EntityHostileWither
{
	protected int focushover;
    ICommandSender commandWitherPowers = new ICommandSender()
    {
        /**
         * Get the name of this object. For players this returns their username
         */
        public String getName()
        {
            return EntityWitherCommand.this.getName();
        }
        /**
         * Get the formatted ChatComponent that will be used for the sender's username in chat
         */
        public ITextComponent getDisplayName()
        {
            return new TextComponentString(ScorePlayerTeam.formatPlayerName(EntityWitherCommand.this.getTeam(), "Command Wither"));
        }
        /**
         * Send a chat message to the CommandSender
         */
        public void sendMessage(ITextComponent component)
        {
        }
        /**
         * Returns {@code true} if the CommandSender is allowed to execute the command, {@code false} if not
         */
        public boolean canUseCommand(int permLevel, String commandName)
        {
            return true;
        }
        /**
         * Get the position in the world. <b>{@code null} is not allowed!</b> If you are not an entity in the
         * world, return the coordinates 0, 0, 0
         */
        public BlockPos getPosition()
        {
            return EntityWitherCommand.this.getPosition();
        }
        /**
         * Get the position vector. <b>{@code null} is not allowed!</b> If you are not an entity in the world,
         * return 0.0D, 0.0D, 0.0D
         */
        public Vec3d getPositionVector()
        {
            return EntityWitherCommand.this.getPositionVector();
        }
        /**
         * Get the world, if available. <b>{@code null} is not allowed!</b> If you are not an entity in the
         * world, return the overworld
         */
        public World getEntityWorld()
        {
            return EntityWitherCommand.this.getEntityWorld();
        }
        /**
         * Returns the entity associated with the command sender. MAY BE NULL!
         */
        public Entity getCommandSenderEntity()
        {
            return EntityWitherCommand.this;
        }
        /**
         * Returns true if the command sender should be sent feedback about executed commands
         */
        public boolean sendCommandFeedback()
        {
            return EntityWitherCommand.this.getServer().worlds[0].getGameRules().getBoolean("commandBlockOutput");
        }
        public void setCommandStat(CommandResultStats.Type type, int amount)
        {

        }
        /**
         * Get the Minecraft server instance
         */
        public MinecraftServer getServer()
        {
            return EntityWitherCommand.this.getServer();
        }
    };

	public EntityWitherCommand(World worldIn) 
	{
		super(worldIn);
		this.experienceValue *= 4000;
		this.noClip = true;
	}
	
	protected void initEntityAI()
    {
        super.initEntityAI();
        this.targetTasks.addTask(2, new EntityAIWitherTargeting<EntityLivingBase>(this, EntityLivingBase.class, WITHERTARGETS));
    }
	
    public boolean canDestroyBlock(BlockPos blockIn)
    {
        return blockIn.getY() > 0;
    }
    
    public void setSkullStats(EntityWitherSkullShared skull, float damage, boolean invul)
    {
        super.setSkullStats(skull, damage, false);
        skull.setRadius(width);
        skull.setType(rand.nextInt(19));
        skull.setDeflect(true);
        skull.setBurn(true);
        skull.setMod(MoWithers.MODID);
        if (getPhasePercent(0.25F))
            skull.setSkullTexture("wither/superboss/wither_command_3");
        else if (getPhasePercent(0.625F))
            skull.setSkullTexture("wither/superboss/wither_command_2");
        else
            skull.setSkullTexture("wither/superboss/wither_command_1");
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(30D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(20D);
    }
    
    public TextFormatting getNameColor()
    {
    	return this.getPhasePercent(0.25F) ? TextFormatting.DARK_PURPLE : this.getPhasePercent(0.625F) ? TextFormatting.AQUA : TextFormatting.GOLD;
    }
    
    public EnumWitherType getWitherType()
    {
        return EnumWitherType.BLOCK_ROCK;
    }

	public double getMobHealth() 
	{
		return super.getMobHealth() * 120000D;
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
    	return super.getWitherScale() * 8F;
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

    }
    
    public void onWitherMoveUpdate()
    {
        double d0 = this.flyposX - this.posX;
        double d1 = this.flyposZ - this.posZ;
        
        double movereduc = 1D;
        double ramspeedmul = 3D;
		this.setSneaking(true);
		this.setRamTime(1);
		this.setSitting(false);
    	if (!this.world.isRemote)
            if (this.getWatchedTargetId(0) > 0)
            {
                Entity entity = this.world.getEntityByID(this.getWatchedTargetId(0));
                if (entity != null)
                {
                	flyposX = entity.posX + (MathHelper.cos(ticksExisted * (float)Math.PI * 0.01F) * (focushover > -500 ? -64F : 64F));
                	flyposZ = entity.posZ + (MathHelper.sin(ticksExisted * (float)Math.PI * 0.01F) * (focushover > -500 ? -64F : 64F));
                    double d2 = entity.posY - this.posY;
                    double d4 = entity.posX - this.posX;
                    double d5 = entity.posZ - this.posZ;
                    
                    double d3 = d0 * d0 + d2 * d2 + d1 * d1;
                    if (this.getRamTime() <= 55)
            		renderYawOffset = rotationYaw = rotationYawHead = (float)(MathHelper.atan2(d5, d4) * (180D / Math.PI)) - 90.0F;

                    double d6 = (double)MathHelper.sqrt(d3);
                    if (this.getDistance(this.flyposX, entity.posY, this.flyposZ) > 9D)
                    {
                        this.motionX += (d0 / d6 * ramspeedmul - this.motionX) * (getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).getBaseValue() * movereduc);
                        this.motionY += (d2 / d6 * ramspeedmul - this.motionY) * (getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).getBaseValue() * movereduc);
                        this.motionZ += (d1 / d6 * ramspeedmul - this.motionZ) * (getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).getBaseValue() * movereduc);
                    }
                    if (this.ticksExisted % 20 == 0 && entity instanceof EntityLivingBase)
                    	this.attackEntityWithRangedAttack((EntityLivingBase) entity, 0);
                }
            }
    }
    
    public void travel(float strafe, float vertical, float forward)
    {
        float f = 0F;
        
        this.moveRelative(strafe, vertical, forward, 1F);
        this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        this.motionX *= (double)f;
        this.motionY *= (double)f;
        this.motionZ *= (double)f;
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
              		this.attackEntityWithRangedAttack((EntityLivingBase)entity, 0);
              	
              	if (this.world.getEntityByID(this.getWatchedTargetId(1)) == null && rand.nextInt(40) == 0)
              		this.updateWatchedTargetId(1, entity.getEntityId());
              	
              	if (this.world.getEntityByID(this.getWatchedTargetId(2)) == null && rand.nextInt(40) == 0)
              		this.updateWatchedTargetId(2, entity.getEntityId());
              	
              	if (this.getDistance(entity) > 120D)
              		focushover = 1;
              }
              
              if (!this.world.isRemote && ++this.focushover > 0)
              {
              		this.focushover = -1000;
              		this.setRamTime(55);
              }
        	}
        	
        	if (this.ticksExisted % 600 == 0)
        	{
        		CommandSummon fake = new CommandSummon();
        		
                for (int i1 = 0; i1 < world.loadedEntityList.size(); ++i1)
                {
                    Entity entity2 = world.loadedEntityList.get(i1);
                    
                    if (entity2.isEntityAlive() && entity2.ticksExisted > 5)
                    {
                        world.addWeatherEffect(new EntityLightningBolt(world, entity2.posX, entity2.posY, entity2.posZ, false));
                        CommandBase.notifyCommandListener(commandWitherPowers, fake, "commands.summon.success", new Object[0]);
                    }
                }
        	}
        	
        	if (this.ticksExisted % 900 == 0)
        	{
        		CommandSummon fake = new CommandSummon();
        		
                for (int i1 = 0; i1 < world.loadedEntityList.size(); ++i1)
                {
                    Entity entity2 = world.loadedEntityList.get(i1);
                    
                    if (entity2.isEntityAlive() && entity2.ticksExisted > 5)
                    {
                    	EntityTNTPrimed kaboom = new EntityTNTPrimed(world);
                    	kaboom.setFuse((short)(world.rand.nextInt(kaboom.getFuse() / 4) + kaboom.getFuse() / 8));
                    	kaboom.copyLocationAndAnglesFrom(entity2);
                        world.spawnEntity(kaboom);
                        CommandBase.notifyCommandListener(commandWitherPowers, fake, "commands.summon.success", new Object[0]);
                    }
                }
        	}
        	
        	if (this.ticksExisted % 160 == 0)
        	{
        		CommandKill fake = new CommandKill();
        		
                for (int i1 = 0; i1 < world.loadedEntityList.size(); ++i1)
                {
                    Entity entity2 = world.loadedEntityList.get(i1);
                    
                    if (entity2.isEntityAlive() && !(entity2 instanceof EntityLivingBase))
                    {
                    	entity2.onKillCommand();
                        CommandBase.notifyCommandListener(commandWitherPowers, fake, "commands.kill.successful", new Object[] {entity2.getDisplayName()});
                    }
                }
        	}
        }
    }

    /**
     * Returns true if this entity should move as if it were on a ladder (either because it's actually on a ladder, or
     * for AI reasons)
     */
    public boolean isOnLadder()
    {
        return false;
    }
    
    @Nullable
    protected Item getDropItem()
    {
        return Item.getItemFromBlock(Blocks.COMMAND_BLOCK);
    }

	public EnumLevel getTier() 
	{
		return EnumLevel.LORD;
	}  

    public boolean isImmuneToExplosions()
    {
        return true;
    }
    
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        amount -= 500;
        
    	return super.attackEntityFrom(source, amount);
    }
}
