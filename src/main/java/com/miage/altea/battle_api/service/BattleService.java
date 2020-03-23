package com.miage.altea.battle_api.service;

import com.miage.altea.battle_api.bo.Battle;
import com.miage.altea.battle_api.exception.BattleNotFoundException;
import com.miage.altea.battle_api.exception.TrainerIsNotTurnException;
import com.miage.altea.battle_api.exception.TrainerNotAlivePokemonException;
import com.miage.altea.battle_api.exception.TrainerNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
public interface BattleService {

    public Collection<Battle> getAllBattles();

    public Battle getStateBattle(UUID uuid) throws BattleNotFoundException;

    public Battle attack(UUID uuid, String trainerName) throws TrainerNotAlivePokemonException, TrainerIsNotTurnException, BattleNotFoundException;

    public UUID createBattle(String trainer, String opponent) throws TrainerNotFoundException, TrainerNotAlivePokemonException;

}
