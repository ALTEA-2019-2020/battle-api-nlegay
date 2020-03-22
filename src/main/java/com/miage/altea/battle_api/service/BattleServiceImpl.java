package com.miage.altea.battle_api.service;

import com.miage.altea.battle_api.bo.*;
import com.miage.altea.battle_api.repository.BattleMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
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
    public Battle getStateBattle(UUID uuid) {
        return BattleMemory.getBattle(uuid);
    }

    @Override
    public ResponseEntity<Battle> attack(UUID uuid, String trainerName) {
        return BattleMemory.getBattle(uuid).attack(trainerName);
    }

    @Override
    public UUID createBattle(String trainer, String opponent) {
        Battle battle = new Battle( createBattleTrainer(trainer), createBattleTrainer(opponent) );
        battle.setNextTurn();
        BattleMemory.insertBattle(battle);
        return battle.getUuid();
    }

    private BattleTrainer createBattleTrainer(String trainer) {
        Trainer trainerBO = restTemplateTrainer.getForObject(trainerServiceUrl+"trainers/{name}", Trainer.class, trainer);
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
