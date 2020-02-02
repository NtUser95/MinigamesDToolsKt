package com.gmail.borlandlp.minigamesdtools.nmsentities.entity

import com.gmail.borlandlp.minigamesdtools.Debug
import com.gmail.borlandlp.minigamesdtools.nmsentities.classes.PathProvider
import com.gmail.borlandlp.minigamesdtools.nmsentities.classes.SkyPathEntity
import net.minecraft.server.v1_12_R1.*
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.craftbukkit.v1_12_R1.CraftServer
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftZombie
import org.bukkit.entity.Zombie
import org.bukkit.event.entity.CreatureSpawnEvent

/*
* Spigot: net.minecraft.server.v1_12_R1.EntityZombie
* Vanilla: net.minecraft.entity.monster.EntityZombie
* */
class SkyZombie : EntityZombie, IMoveControllableEntity, IControllable, ISkybattleEntity {
    private var wrappedBukkitEntity: CraftZombie
    var isUsedMinecraftAI = true
        private set

    constructor(world: World) : super((world as CraftWorld).handle as net.minecraft.server.v1_12_R1.World) {
        this.wrappedBukkitEntity = CraftZombie(Bukkit.getServer() as CraftServer, this)
    }

    constructor(
        world: World,
        isUsedMinecraftAI: Boolean
    ) : super((world as CraftWorld).handle as net.minecraft.server.v1_12_R1.World) {
        this.wrappedBukkitEntity = CraftZombie(Bukkit.getServer() as CraftServer, this)
        this.isUsedMinecraftAI = isUsedMinecraftAI
    }

    /*
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIZombieAttack(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }
    */
    override fun r() {
        if (isUsedMinecraftAI) {
            goalSelector.a(0, PathfinderGoalFloat(this))
            goalSelector.a(2, PathfinderGoalZombieAttack(this, 1.0, false))
            goalSelector.a(5, PathfinderGoalMoveTowardsRestriction(this, 1.0))
            goalSelector.a(7, PathfinderGoalRandomStrollLand(this, 1.0))
            goalSelector.a(8, PathfinderGoalLookAtPlayer(this, EntityHuman::class.java, 8.0f))
            goalSelector.a(8, PathfinderGoalRandomLookaround(this))
        }
        do_()
    }

    override fun getBukkitEntity(): CraftZombie {
        return wrappedBukkitEntity
    }

    override fun initAttributes() {
        super.initAttributes()
    }

    override fun spawn(loc: Location): Zombie {
        setLocation(loc.x, loc.y, loc.z, loc.yaw, loc.pitch)
        world.addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM)
        return getBukkitEntity()
    }

    override fun setPath(path: PathProvider) {
        getNavigation().a(path as PathEntity, 2.0)
    }

    override fun vec2Path(vec3D: Vec3D): PathProvider {
        val pathPoint =
            arrayOf<PathPoint?>(PathPoint(vec3D.x.toInt(), vec3D.y.toInt(), vec3D.z.toInt()))
        return SkyPathEntity(pathPoint)
    }

    override fun isFollowsThisPath(pathProvider: PathProvider): Boolean {
        return getNavigation().l() === pathProvider
    }

    override fun teleport(pathProvider: PathProvider) {
        val world = getBukkitEntity().location.world
        val location =
            Location(world, pathProvider.path!!.x, pathProvider.path!!.y, pathProvider.path!!.z)
        getBukkitEntity().teleport(location)
    } /*
    @Override
    protected NavigationAbstract b(World world) {
        return new SkyNavigation(this, world);
    }
    */
}