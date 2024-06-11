package com.sds.animalapp.model.animal;

import org.apache.ibatis.annotations.Mapper;

import com.sds.animalapp.domain.InterestAnimal;

@Mapper
public interface InterestAnimalDAO {

	// 관심 동물 등록
	public void addInterestAnimal(InterestAnimal interestAnimal);

	// 관심 동물 삭제
	public void deleteInterestAnimal(int interest_animal_idx);

	// 관심 동물 중복 확인
	public int checkDuplicateInterestAnimal(InterestAnimal interestAnimal);
}
