package com.miage.altea.battle_api.bo;

import com.miage.altea.battle_api.exception.TrainerIsNotTurnException;
import com.miage.altea.battle_api.exception.TrainerNotAlivePokemonException;
import com.miage.altea.battle_api.utils.StatsCalculator;

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

    public void setNextTurn() {
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
            BattlePokemon pokemonAttacker = trainerAttacker.getPokemonAlive();
            BattlePokemon pokemonDefender = trainerDefender.getPokemonAlive();
            if ( pokemonAttacker != null && pokemonDefender != null ) {
                pokemonDefender.setHp(pokemonDefender.getHp() - StatsCalculator.calculateDamage(pokemonAttacker.getLevel(), pokemonAttacker.getAttack(), pokemonDefender.getDefense()));
                if (pokemonDefender.getHp() <= 0) {
                    pokemonDefender.setAlive(false);
                    pokemonDefender.setKo(true);
                    pokemonDefender.setHp(0);
                }
                trainerAttacker.setNextTurn(false);
                trainerDefender.setNextTurn(true);
                return this;
            } else {
                throw new TrainerNotAlivePokemonException("A trainer doesn't have any alive pokemons");
            }
        } else {
            throw new TrainerIsNotTurnException("It isn't this trainer's turn");
        }
    }

}
