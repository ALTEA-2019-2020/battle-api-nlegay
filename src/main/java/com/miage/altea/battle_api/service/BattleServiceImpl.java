package com.miage.altea.battle_api.service;

import com.miage.altea.battle_api.bo.*;
import com.miage.altea.battle_api.exception.BattleNotFoundException;
import com.miage.altea.battle_api.exception.TrainerIsNotTurnException;
import com.miage.altea.battle_api.exception.TrainerNotAlivePokemonException;
import com.miage.altea.battle_api.exception.TrainerNotFoundException;
import com.miage.altea.battle_api.repository.BattleMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class BattleServiceImpl implements BattleService {

    private RestTemplate restTemplate;
    private RestTemplate restTemplateTrainer;
    private String pokemonServiceUrl;
    private String trainerServiceUrl;

    @Autowired
    public BattleServiceImpl() {
    }

    @Override
    public Collection<Battle> getAllBattles() {
        return BattleMemory.getAllBattles();
    }

    @Override
    public Battle getStateBattle(UUID uuid) throws BattleNotFoundException {
        Battle battle = BattleMemory.getBattle(uuid);
        if (battle != null) {
            return battle;
        }
        throw new BattleNotFoundException("Battle not found");
    }

    @Override
    public Battle attack(UUID uuid, String trainerName) throws TrainerNotAlivePokemonException, TrainerIsNotTurnException, BattleNotFoundException {
        Battle battle = BattleMemory.getBattle(uuid);
        if (battle != null) {
            return battle.attack(trainerName);
        }
        throw new BattleNotFoundException("Battle not found");
    }

    @Override
    public UUID createBattle(String trainer, String opponent) throws TrainerNotFoundException, TrainerNotAlivePokemonException {
        Battle battle = new Battle( createBattleTrainer(trainer), createBattleTrainer(opponent) );
        battle.setNextTurn();
        BattleMemory.insertBattle(battle);
        return battle.getUuid();
    }

    private BattleTrainer createBattleTrainer(String trainer) throws TrainerNotFoundException {
        Trainer trainerBO = restTemplateTrainer.getForObject(trainerServiceUrl+"trainers/{name}", Trainer.class, trainer);
        if (trainerBO == null || trainerBO.getName() == null) {
            throw new TrainerNotFoundException("Trainer not found");
        }
        List<BattlePokemon> team = new ArrayList<>();
        for ( Pokemon pokemon : trainerBO.getTeam() ) {
            PokemonType pokemonType = restTemplate
                    .getForObject(pokemonServiceUrl+"pokemon-types/{id}", PokemonType.class, pokemon.getPokemonTypeId());
            team.add( BattlePokemonFactory.createBattlePokemon(pokemonType, pokemon.getLevel()) );
        }
        return new BattleTrainer( trainer, team );
    }

    @Autowired
    @Qualifier("restTemplate")
    void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    @Qualifier("trainerApiRestTemplate")
    void setRestTemplateTrainer(RestTemplate restTemplateTrainer) {
        this.restTemplateTrainer = restTemplateTrainer;
    }

    @Value("${pokemonType.service.url}")
    void setPokemonTypeServiceUrl(String pokemonServiceUrl) {
        this.pokemonServiceUrl = pokemonServiceUrl;
    }

    @Value("${trainer.service.url}")
    void setTrainerServiceUrl(String trainerServiceUrl) {
        this.trainerServiceUrl = trainerServiceUrl;
    }

}
