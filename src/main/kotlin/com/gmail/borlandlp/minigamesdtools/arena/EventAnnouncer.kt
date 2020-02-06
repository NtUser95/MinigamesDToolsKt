package com.gmail.borlandlp.minigamesdtools.arena

import com.gmail.borlandlp.minigamesdtools.Debug
import com.gmail.borlandlp.minigamesdtools.Debug.print
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaEvent
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaEventHandler
import com.gmail.borlandlp.minigamesdtools.arena.localevent.ArenaEventPriority
import org.bukkit.event.Cancellable
import java.lang.reflect.Method
import java.util.*

class EventAnnouncer {
    private val listeners: MutableMap<Class<out ArenaEvent>, MutableList<RegisteredArenaListener>> =
        HashMap()

    fun register(listener: ArenaEventListener) {
        for (IMethod in getObjectMethods(listener)) {
            val annClass =
                IMethod.getAnnotation(ArenaEventHandler::class.java)
            if (IMethod.genericParameterTypes.size == 1 && annClass != null) {
                val argEventClazz: Class<out ArenaEvent>? = try {
                     Class.forName(IMethod.genericParameterTypes[0].typeName) as Class<out ArenaEvent>?
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                    null
                }
                if (argEventClazz == null) {
                    val parameterClass =
                        IMethod.genericParameterTypes[0].typeName
                    val listenerClass = listener.javaClass.simpleName
                    val msg =
                        "Internal error. Cant found class $parameterClass for $listenerClass"
                    print(
                        Debug.LEVEL.WARNING,
                        msg
                    )
                    continue
                }
                registerListener(argEventClazz, listener, annClass, IMethod)
            }
        }
    }

    private fun registerListener(
        argEventClazz: Class<out ArenaEvent>,
        listener: ArenaEventListener,
        annClass: ArenaEventHandler,
        method: Method
    ) {
        val handlers = getListenersOf(argEventClazz)
        val rListener =
            RegisteredArenaListener(listener, method, annClass.ignoreCancelled, annClass.priority)
        handlers.add(rListener)
        listeners[argEventClazz] = handlers
    }

    private fun getObjectMethods(obj: Any): Set<Method> {
        val publicMethods = obj.javaClass.methods
        val privateMethods = obj.javaClass.declaredMethods
        val methods: MutableSet<Method> =
            HashSet(publicMethods.size + privateMethods.size, 1.0f)
        methods.addAll(Arrays.asList(*publicMethods))
        methods.addAll(Arrays.asList(*privateMethods))
        return methods
    }

    fun unregister(listenerToRemove: ArenaEventListener) {
        for (list in getListeners().values) {
            list.removeIf { listener ->
                listener.handler === listenerToRemove
            }
        }
    }

    fun getListeners(): Map<Class<out ArenaEvent>, MutableList<RegisteredArenaListener>> {
        return listeners
    }

    fun getListenersOf(clazz: Class<out ArenaEvent>): MutableList<RegisteredArenaListener> {
        return getListeners().getOrDefault(clazz, ArrayList<RegisteredArenaListener>()).toMutableList()
    }

    fun <T : ArenaEvent> announce(event: T) {
        if (!getListeners().containsKey(event.javaClass)) {
            return
        }
        for (priority in ArenaEventPriority.values()) { // highest, high, normal, low, lowest...
            for (registeredArenaListener in getListenersOf(event.javaClass)) {
                val isValidPriority = registeredArenaListener.priority === priority
                val hasCanceled =
                    event is Cancellable && (event as Cancellable).isCancelled
                val isCanceled = hasCanceled && !registeredArenaListener.isIgnoreCanceled
                if (!isValidPriority || isCanceled) {
                    continue
                }
                try {
                    registeredArenaListener.execute(event)
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }
    }
}