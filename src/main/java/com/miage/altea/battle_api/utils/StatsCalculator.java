package com.miage.altea.battle_api.utils;

public class StatsCalculator {

    public static int calculateHp(int base, int level) {
        return (int)(10+level+base*((double)level/(double)50));
    }

    public static int calculateStat(int base, int level) {
        return (int)(5+base*((double)level/(double)50));
    }

    public static int calculateDamage(int level, int attack, int defenseOpponent) {
        return (int)( (double)(2*level)/(double)5+2*(double)attack/(double)defenseOpponent+2 );
    }
}
