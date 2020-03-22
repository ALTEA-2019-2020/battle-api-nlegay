package com.miage.altea.battle_api.bo;

import org.springframework.http.ResponseEntity;

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

    public ResponseEntity<Battle> attack(String trainerName) {
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
                pokemonDefender.setHp(pokemonDefender.getHp() - calculateDamage(pokemonAttacker.getLevel(), pokemonAttacker.getAttack(), pokemonDefender.getDefense()));
                if (pokemonDefender.getHp() <= 0) {
                    pokemonDefender.setAlive(false);
                    pokemonDefender.setKo(true);
                    pokemonDefender.setHp(0);
                }
                trainerAttacker.setNextTurn(false);
                trainerDefender.setNextTurn(true);
                return ResponseEntity.ok(this);
            } else {
                //A trainer doesn't have alive pokemons
                return ResponseEntity.badRequest().eTag("A trainer doesn't have alive pokemons").build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    private int calculateDamage(int level, int attack, int defenseOpponent) {
        return (int)( (double)(2*level)/(double)5+2*(double)attack/(double)defenseOpponent+2 );
    }
}
