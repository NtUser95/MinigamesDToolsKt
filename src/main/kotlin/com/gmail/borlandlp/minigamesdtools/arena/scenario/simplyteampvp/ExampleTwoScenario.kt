package com.gmail.borlandlp.minigamesdtools.arena.scenario.simplyteampvp

import com.gmail.borlandlp.minigamesdtools.arena.ArenaEventListener
import com.gmail.borlandlp.minigamesdtools.arena.scenario.Scenario
import com.gmail.borlandlp.minigamesdtools.arena.scenario.ScenarioAbstract

class ExampleTwoScenario : ScenarioAbstract(), Scenario {
    override val isDone = false
    private var listener: ArenaEventListener? = null

    override fun onInit() {}
    override fun beforeGameStarting() {
        listener = ExampleTwoScenarioListener(this)
        try {
            arena!!.eventAnnouncer.register(listener as ExampleTwoScenarioListener)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun gameEnded() {}
    override fun beforeRoundStarting() {}
    override fun onRoundEnd() {}
    override fun update() { /*
        for (ExampleTeam team : this.getArena().getTeamController().getTeams()) {
            for (String nickname : team.getSourcePlayers().keySet()) {
                Player player = team.getSourcePlayers().get(nickname);
                if (player == null || !player.isOnline()) {
                    //timeout
                    if (this.getArena().leaversInFight.containsKey(nickname)) {
                        this.getArena().leaversInFight.put(nickname, this.getArena().leaversInFight.get(nickname) + 1);
                    } else {
                        this.getArena().leaversInFight.put(nickname, 1);
                        this.getArena().broadcastMessage(nickname + " вышел с сервера во время матча, даём ему 30 секунд на возвращение..", false);
                    }

                    if (this.getArena().leaversInFight.containsKey(nickname) && this.getArena().leaversInFight.get(nickname) > 30) {
                        try {
                            this.playerKilled(nickname);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        */
    }

    override fun roundMustBeEnded(): Boolean {
        return false
    }

    override fun gameMustBeEnded(): Boolean {
        return false
    }
}