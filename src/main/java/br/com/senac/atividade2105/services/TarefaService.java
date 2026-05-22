package br.com.senac.atividade2105.services;

import br.com.senac.atividade2105.dtos.TarefasFiltroDto;
import br.com.senac.atividade2105.dtos.TarefasRequestDto;
import br.com.senac.atividade2105.entidades.Tarefas;
import br.com.senac.atividade2105.repositorio.TarefasRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {
    private TarefasRepositorio tarefasRepositorio;

    public TarefaService(TarefasRepositorio tarefasRepositorio) {
        this.tarefasRepositorio = tarefasRepositorio;
    }

    @GetMapping("/listar")
    public List<Tarefas> listar(TarefasFiltroDto filtro){
        if(filtro.getNome() != null){
            return tarefasRepositorio.findByNomeContaining(filtro.getNome());
        }
        if(filtro.getDataInicio() != null){
            return tarefasRepositorio.findBydataInicioGreaterThan(filtro.getDataInicio());
        }
        return tarefasRepositorio.findAll();
    }

    public Tarefas atualizar(Long id,
                             TarefasRequestDto tarefa){
        if(tarefasRepositorio.existsById(id)){
            Tarefas tarefaPersist = this.tarefaRequestDtoParaTarefas(tarefa);
            tarefaPersist.setId(id);
            return tarefasRepositorio.save(tarefaPersist);
        }
        throw new RuntimeException("Tarefa não encontrada");
    }

    public Tarefas criar(TarefasRequestDto tarefa){
        Tarefas tarefaPersist = this.tarefaRequestDtoParaTarefas(tarefa);

        return tarefasRepositorio.save(tarefaPersist);
    }

    public void deletar(Long id){
        if(tarefasRepositorio.existsById(id)){
            tarefasRepositorio.deleteById(id);
        }
        throw new RuntimeException("Tarefa não encontrada");
    }

    public Tarefas listarPorId(Long id){
        Optional<Tarefas> retorno = tarefasRepositorio.findById(id);
        if(retorno.isPresent()){
            return retorno.get();
        }
        throw new RuntimeException("Tarefa não encontrada");
    }

    private Tarefas tarefaRequestDtoParaTarefas(TarefasRequestDto entrada){
        Tarefas saida = new Tarefas();
        saida.setNome(entrada.getNome());
        saida.setDescricao(entrada.getDescricao());
        saida.setDataInicio(entrada.getDataInicio());
        saida.setDataFim(entrada.getDataFim());
        saida.setAutor(entrada.getAutor());

        return saida;
    }
}
