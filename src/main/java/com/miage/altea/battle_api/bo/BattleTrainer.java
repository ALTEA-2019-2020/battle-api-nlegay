package com.miage.altea.battle_api.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.miage.altea.battle_api.exception.TrainerNotAlivePokemonException;

import java.util.List;

public class BattleTrainer {

    private String name;
    private Boolean nextTurn;
    private List<BattlePokemon> team;

    public BattleTrainer(String name, List<BattlePokemon> team) {
        this.name = name;
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public Boolean getNextTurn() {
        return nextTurn;
    }

    public void setNextTurn(Boolean nextTurn) {
        this.nextTurn = nextTurn;
    }

    public List<BattlePokemon> getTeam() {
        return team;
    }

    public void setTeam(List<BattlePokemon> team) {
        this.team = team;
    }

    @JsonIgnore
    public BattlePokemon getPokemonAlive() throws TrainerNotAlivePokemonException {
        BattlePokemon battlePokemon = team.stream().filter(e -> e.isAlive()).findFirst().orElseGet(null);
        if ( battlePokemon != null ) {
            return battlePokemon;
        }
        throw new TrainerNotAlivePokemonException("A trainer doesn't have any alive pokemons");
    }
}
