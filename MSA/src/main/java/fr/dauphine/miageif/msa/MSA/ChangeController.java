package fr.dauphine.miageif.msa.MSA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

@RestController
public class ChangeController {

  // Spring se charge de la création d'instance
  @Autowired
  private Environment environment;

  // Spring se charge de la création d'instance
  @Autowired
  private TauxChangeRepository repository;

  @GetMapping("/devise-change/source/{source}/dest/{dest}")
  public TauxChange retrouveTauxChange
    (@PathVariable String source, @PathVariable String dest) {

    TauxChange tauxChange =
            repository.findBySourceAndDest(source, dest);
    if(tauxChange == null)
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Change rate not found!");

    return tauxChange;
  }

  @PostMapping("/devise-change/id/{id}/source/{source}/dest/{dest}/taux/{taux}/port/{port}")
  public TauxChange addTauxChange
          (@PathVariable Long id,
           @PathVariable String source,
           @PathVariable String dest,
           @PathVariable BigDecimal taux){

    Optional<TauxChange> tc = repository.findById(id);
    if(tc == null){
      TauxChange tauxChange = new TauxChange(id,source,dest,taux);
      repository.save(tauxChange);

      return tauxChange;
    }else throw new ResponseStatusException(HttpStatus.CONFLICT, "Change rate already existed!");

  }

  @DeleteMapping("/devise-change/del/id/{id}")
  public void delete(@PathVariable long id) {
    Optional<TauxChange> auditAction = repository.findById(id);
    repository.delete(auditAction.orElse(null));
  }

  @PutMapping("/devise-change/upd/id/{id}/taux/{taux}")
  public void updateTaux(@PathVariable long id, @PathVariable BigDecimal taux){
    Optional<TauxChange> tauxChange = repository.findById(id);
    tauxChange.get().setTaux(taux);

    repository.save(tauxChange.orElse(null));
  }

}