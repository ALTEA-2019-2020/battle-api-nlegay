package com.miage.altea.battle_api.controller;

import com.miage.altea.battle_api.exception.BattleNotFoundException;
import com.miage.altea.battle_api.exception.TrainerIsNotTurnException;
import com.miage.altea.battle_api.exception.TrainerNotAlivePokemonException;
import com.miage.altea.battle_api.exception.TrainerNotFoundException;
import com.miage.altea.battle_api.service.BattleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/battles")
public class BattleController {

    private BattleService battleService;

    public BattleController(BattleService battleService) {
        this.battleService = battleService;
    }

    @PostMapping
    ResponseEntity create(@RequestParam String trainer, @RequestParam String opponent) {
        try {
            return ResponseEntity.ok( battleService.createBattle(trainer, opponent) );
        } catch (TrainerNotFoundException | TrainerNotAlivePokemonException e) {
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
        } catch (TrainerNotAlivePokemonException | TrainerIsNotTurnException | BattleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
