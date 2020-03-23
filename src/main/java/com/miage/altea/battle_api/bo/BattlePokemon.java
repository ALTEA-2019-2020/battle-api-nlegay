package com.miage.altea.battle_api.bo;

import com.miage.altea.battle_api.utils.StatsCalculator;

import java.util.concurrent.atomic.AtomicInteger;

public class BattlePokemon {

    private static final AtomicInteger count = new AtomicInteger(0);

    private int id;
    private PokemonType type;
    private int maxHP;
    private int attack;
    private int defense;
    private int speed;
    private int level;
    private int hp;
    private boolean ko;
    private boolean alive;

    public BattlePokemon(PokemonType type, int level) {
        this.id = count.incrementAndGet();
        this.type = type;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PokemonType getType() {
        return type;
    }

    public void setType(PokemonType type) {
        this.type = type;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public boolean isKo() {
        return ko;
    }

    public void setKo(boolean ko) {
        this.ko = ko;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * Pokemon attack pokemonDefender
     */
    public void attack(BattlePokemon pokemonDefender) {
        pokemonDefender.setHp(pokemonDefender.getHp() - StatsCalculator.calculateDamage( level, attack, pokemonDefender.getDefense() ));
        if (pokemonDefender.getHp() <= 0) {
            pokemonDefender.setAlive(false);
            pokemonDefender.setKo(true);
            pokemonDefender.setHp(0);
        }
    }
}
