package com.gmail.borlandlp.minigamesdtools.nmsentities.entity

import com.gmail.borlandlp.minigamesdtools.nmsentities.classes.PathProvider
import com.gmail.borlandlp.minigamesdtools.nmsentities.classes.SkyDragonControllerCharge
import net.minecraft.server.v1_12_R1.*
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.craftbukkit.v1_12_R1.CraftServer
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEnderDragon
import org.bukkit.entity.EnderDragon
import org.bukkit.event.entity.CreatureSpawnEvent
import java.lang.reflect.Field

class SkyDragon(world: World) :
    EntityEnderDragon((world as CraftWorld).handle as net.minecraft.server.v1_12_R1.World),
    IMoveControllableEntity, IControllable, ISkybattleEntity {

    private val wrappedBukkitEntity: CraftEnderDragon
    private var dragonControllerManager: DragonControllerManager? = null
    override fun getDragonControllerManager(): DragonControllerManager {
        return dragonControllerManager!!
    }

    override fun setPath(path: PathProvider) {
        val dragoncontrollerphase =
            CraftEnderDragon.getMinecraftPhase(CraftEnderDragon.getBukkitPhase(DragonControllerPhase.i))
        //IDragonController iDragonController = dragoncontrollerphase.a(this);
        var curDragContr: Field? = null
        try {
            curDragContr = getDragonControllerManager().javaClass.getDeclaredField("currentDragonController")
        } catch (e1: NoSuchFieldException) {
            e1.printStackTrace()
        }
        curDragContr!!.isAccessible = true
        try {
            curDragContr[getDragonControllerManager()] = path as SkyDragonControllerCharge
        } catch (e1: IllegalAccessException) {
            e1.printStackTrace()
        }
        this.dataWatcher.set(PHASE, dragoncontrollerphase.b())
    }

    override fun vec2Path(vec3D: Vec3D): PathProvider {
        val iDragonController = SkyDragonControllerCharge(this)
        iDragonController.a(vec3D)
        return iDragonController
    }

    override fun isFollowsThisPath(pathProvider: PathProvider): Boolean {
        return getDragonControllerManager().a() === pathProvider
    }

    override fun getBukkitEntity(): CraftEnderDragon {
        return wrappedBukkitEntity
    }

    override fun initAttributes() {
        super.initAttributes()
    }

    override fun spawn(loc: Location): EnderDragon {
        setLocation(loc.x, loc.y, loc.z, loc.yaw, loc.pitch)
        world.addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM)
        return getBukkitEntity()
    }

    override fun teleport(pathProvider: PathProvider) {
        val world = getBukkitEntity().location.world
        val location =
            Location(world, pathProvider.path!!.x, pathProvider.path!!.y, pathProvider.path!!.z)
        getBukkitEntity().teleport(location)
    } //onLivingUpdate();

    /*public void n() {
        // dragon living
    }*/
    init {
        children =
            arrayOf(bw, bx, by, bz, bA, bB, bC, bD)
        this.health = this.maxHealth
        setSize(16.0f, 8.0f)
        noclip = true
        fireProof = true
        this.isNoAI = false
        try {
            val fbM = EntityEnderDragon::class.java.getDeclaredField("bM")
            fbM.isAccessible = true
            fbM.setInt(this, 100)
            val fbK = EntityEnderDragon::class.java.getDeclaredField("bK")
            fbK.isAccessible = true
            if (!(world as CraftWorld).handle.isClientSide && world.handle.worldProvider is WorldProviderTheEnd) {
                fbK[this] = (world.handle.worldProvider as WorldProviderTheEnd).t()
            } else {
                fbK[this] = null
            }
            dragonControllerManager = DragonControllerManager(this)
            val fbL = EntityEnderDragon::class.java.getDeclaredField("bL")
            fbL.isAccessible = true
            fbL[this] = getDragonControllerManager()
        } catch (e1: NoSuchFieldException) {
            e1.printStackTrace()
        } catch (e1: IllegalAccessException) {
            e1.printStackTrace()
        }
        ah = true
        this.wrappedBukkitEntity = CraftEnderDragon(Bukkit.getServer() as CraftServer, this)
    }
}