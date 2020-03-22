package com.miage.altea.battle_api.controller;

import com.miage.altea.battle_api.bo.Battle;
import com.miage.altea.battle_api.bo.TrainerFigthers;
import com.miage.altea.battle_api.service.BattleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/battles")
public class BattleController {

    private BattleService battleService;

    public BattleController(BattleService battleService) {
        this.battleService = battleService;
    }

    @PostMapping
    UUID create(@Valid @RequestBody TrainerFigthers trainerFigthers) {
        return battleService.createBattle(trainerFigthers.getTrainer(), trainerFigthers.getOpponent());
    }

    @GetMapping
    Collection<Battle> getAllBattles() {
        return battleService.getAllBattles();
    }

    @GetMapping("/{uuid}")
    Battle getStateBattle(@PathVariable UUID uuid) {
        return battleService.getStateBattle(uuid);
    }

    @PostMapping("/{uuid}/{trainerName}/attack")
    ResponseEntity<Battle> attack(@PathVariable UUID uuid, @PathVariable String trainerName) {
        return battleService.attack(uuid, trainerName);
    }

}
