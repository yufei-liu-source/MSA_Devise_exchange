package fr.dauphine.miageif.msa.MSA;
import org.springframework.data.jpa.repository.JpaRepository;

// Creation du JPA Repository basé sur Spring Data
//  Permet de "générer" toutes sortes de requêtes JPA, sans implementation
public interface TauxChangeRepository extends  JpaRepository<TauxChange, Long>{
  // Query Method findBy
  TauxChange findBySourceAndDest(String source, String dest);
}