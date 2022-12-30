package com.openai.openaimaven.Repository;

import com.openai.openaimaven.Common.Enums.ActionsEnum;
import com.openai.openaimaven.Entity.Actions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionsRepository extends JpaRepository<Actions,Long> {

    Actions findByActionAndState(ActionsEnum action, Boolean state);

}
