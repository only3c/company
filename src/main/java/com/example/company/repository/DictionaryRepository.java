package com.example.company.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.company.model.Dictionary;

public interface DictionaryRepository extends JpaRepository<Dictionary, Long> {

	List<Dictionary> findListByDatatypeAndParentcode(String datatype, String parentcode);
	// List<Dictionary> findListByDatatype(String datatype);

	Dictionary findOneByDatatypeAndDatacode(String datatype, String datacode);

	Dictionary findOneByDatatypeAndDatacodeAndParentcode(String datatype, String datacode, String parentcode);

	Page<Dictionary> findAllByDatatypeLikeAndDatatypenameLikeAndDatanameLike(String datatype, String datatypename,
			String dataname, Pageable pageable);
	@Query(value="select Max(id) from dictionary", nativeQuery = true)
	Integer findMaxId();

	void deleteAllByIdIn(Long[] num);
}
