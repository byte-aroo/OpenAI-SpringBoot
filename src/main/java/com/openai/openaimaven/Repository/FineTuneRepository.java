package com.openai.openaimaven.Repository;

import com.openai.openaimaven.Entity.FineTunes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FineTuneRepository extends JpaRepository<FineTunes,Long> {

    List<FineTunes> findAllBySynced(Boolean synced);

}
