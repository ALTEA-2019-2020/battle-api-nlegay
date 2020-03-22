package com.miage.altea.battle_api.bo;

import com.miage.altea.battle_api.utils.StatsCalculator;

public class BattlePokemonFactory {

    public static BattlePokemon createBattlePokemon(PokemonType pokemonType, int level) {
        BattlePokemon battlePokemon = new BattlePokemon(pokemonType, level);
        battlePokemon.setMaxHP( StatsCalculator.calculateHp(pokemonType.getStats().getHp(), level) );
        battlePokemon.setAttack( StatsCalculator.calculateStat(pokemonType.getStats().getAttack(), level) );
        battlePokemon.setDefense( StatsCalculator.calculateStat(pokemonType.getStats().getDefense(), level) );
        battlePokemon.setSpeed( StatsCalculator.calculateStat(pokemonType.getStats().getSpeed(), level) );
        battlePokemon.setHp( StatsCalculator.calculateHp(pokemonType.getStats().getHp(), level) );
        battlePokemon.setKo(false);
        battlePokemon.setAlive(true);
        return battlePokemon;
    }
}
