package br.com.senac.atividade2105.controller;

import br.com.senac.atividade2105.dtos.TarefasFiltroDto;
import br.com.senac.atividade2105.dtos.TarefasRequestDto;
import br.com.senac.atividade2105.entidades.Tarefas;
import br.com.senac.atividade2105.services.TarefaService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/tarefas")
public class TarefaController {
    private TarefaService tarefasService;

    public TarefaController(TarefaService tarefasService) {
        this.tarefasService = tarefasService;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Tarefas>> listar(TarefasFiltroDto filtro){
        return ResponseEntity.ok(tarefasService.listar(filtro));
    }

    @PostMapping("/criar")
    public ResponseEntity<Tarefas> criar(@RequestBody TarefasRequestDto tarefa){
        try{
            return ResponseEntity.ok(tarefasService.criar(tarefa));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Tarefas> atualizar(@RequestBody TarefasRequestDto tarefa,
                                             @PathVariable Long id){
        try {
            return ResponseEntity.ok(tarefasService.atualizar(id, tarefa));
        }catch(RuntimeException e){
            return ResponseEntity.badRequest().body(null);
        }catch(Exception e){
            return ResponseEntity.internalServerError().body(null);
        }
    }
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Tarefas> deletar(@PathVariable Long id){
        try {
            tarefasService.deletar(id);
            return ResponseEntity.ok(null);
        }catch(RuntimeException e){
            return ResponseEntity.badRequest().body(null);
        }catch(Exception e){
            return ResponseEntity.internalServerError().body(null);
        }
    }

}
