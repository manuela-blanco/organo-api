package bsi.pcs.organo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bsi.pcs.organo.entity.CompradorEntity;

@Repository
public interface CompradorRepository extends JpaRepository<CompradorEntity, Long> {

}