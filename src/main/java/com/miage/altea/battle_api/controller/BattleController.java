package com.miage.altea.battle_api.controller;

import com.miage.altea.battle_api.bo.TrainerFigthers;
import com.miage.altea.battle_api.exception.BattleNotFoundException;
import com.miage.altea.battle_api.exception.TrainerIsNotTurnException;
import com.miage.altea.battle_api.exception.TrainerNotAlivePokemonException;
import com.miage.altea.battle_api.exception.TrainerNotFoundException;
import com.miage.altea.battle_api.service.BattleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/battles")
public class BattleController {

    private BattleService battleService;

    public BattleController(BattleService battleService) {
        this.battleService = battleService;
    }

    @PostMapping
    ResponseEntity create(@Valid @RequestBody TrainerFigthers trainerFigthers) {
        try {
            return ResponseEntity.ok( battleService.createBattle(trainerFigthers.getTrainer(), trainerFigthers.getOpponent()) );
        } catch (TrainerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    ResponseEntity getAllBattles() {
        return ResponseEntity.ok( battleService.getAllBattles() );
    }

    @GetMapping("/{uuid}")
    ResponseEntity getStateBattle(@PathVariable UUID uuid) {
        try {
            return ResponseEntity.ok( battleService.getStateBattle(uuid) );
        } catch (BattleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/{uuid}/{trainerName}/attack")
    ResponseEntity attack(@PathVariable UUID uuid, @PathVariable String trainerName) {
        try {
            return ResponseEntity.ok( battleService.attack(uuid, trainerName) );
        } catch (TrainerNotAlivePokemonException | TrainerIsNotTurnException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
