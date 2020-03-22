package com.miage.altea.battle_api.repository;

import com.miage.altea.battle_api.bo.Battle;

import java.util.*;

public class BattleMemory {

    private static Map<UUID, Battle> allBattles = new HashMap<>();

    public static void insertBattle(Battle battle) {
        allBattles.put(battle.getUuid(), battle);
    }

    public static Collection<Battle> getAllBattles() {
        return allBattles.values();
    }

    public static Battle getBattle(UUID uuid) {
        return allBattles.get(uuid);
    }
}
