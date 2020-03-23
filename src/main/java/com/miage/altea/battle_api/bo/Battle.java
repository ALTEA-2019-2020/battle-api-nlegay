package com.miage.altea.battle_api.bo;

import com.miage.altea.battle_api.exception.TrainerIsNotTurnException;
import com.miage.altea.battle_api.exception.TrainerNotAlivePokemonException;

import java.util.UUID;

public class Battle {

    private UUID uuid = UUID.randomUUID();
    private BattleTrainer trainer;
    private BattleTrainer opponent;

    public Battle(BattleTrainer trainer, BattleTrainer opponent) {
        this.trainer = trainer;
        this.opponent = opponent;
    }

    public UUID getUuid() {
        return uuid;
    }

    public BattleTrainer getTrainer() {
        return trainer;
    }

    public BattleTrainer getOpponent() {
        return opponent;
    }

    public void setNextTurn() throws TrainerNotAlivePokemonException {
        if (trainer.getPokemonAlive().getSpeed() > opponent.getPokemonAlive().getSpeed()) {
            trainer.setNextTurn(true);
            opponent.setNextTurn(false);
        } else {
            trainer.setNextTurn(false);
            opponent.setNextTurn(true);
        }
    }

    public BattleTrainer getTrainer(String trainerName) {
        if (trainer.getName().equals(trainerName)) {
            return trainer;
        } else if (opponent.getName().equals(trainerName)) {
            return opponent;
        }
        return null;
    }

    public Battle attack(String trainerName) throws TrainerNotAlivePokemonException, TrainerIsNotTurnException {
        BattleTrainer trainerAttacker;
        BattleTrainer trainerDefender;
        if ( trainer.getName().equals(trainerName) ) {
            trainerAttacker = trainer;
            trainerDefender = opponent;
        } else {
            trainerAttacker = opponent;
            trainerDefender = trainer;
        }
        // check if it's his turn
        if (trainerAttacker.getNextTurn()) {
            trainerAttacker.getPokemonAlive().attack(trainerDefender.getPokemonAlive());
            trainerDefender.setNextTurn(true);
            trainerAttacker.setNextTurn(false);
            return this;
        } else {
            throw new TrainerIsNotTurnException("It isn't this trainer's turn");
        }
    }

}
