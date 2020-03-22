package com.miage.altea.battle_api.service;

import com.miage.altea.battle_api.bo.Battle;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
public interface BattleService {

    public Collection<Battle> getAllBattles();

    public Battle getStateBattle(UUID uuid);

    public ResponseEntity<Battle> attack(UUID uuid, String trainerName);

    public UUID createBattle(String trainer, String opponent);

}
