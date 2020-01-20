package com.credit.app.core.repo;

import com.credit.app.core.common.model.BaseInfo;
import com.credit.app.core.persist.BaseEntity;
import org.springframework.data.repository.CrudRepository;

public interface BaseRepository<E extends BaseEntity<I>, I extends BaseInfo> extends CrudRepository<E, Long> {

}
