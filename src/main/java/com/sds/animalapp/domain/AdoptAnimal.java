package com.sds.animalapp.domain;

import lombok.Data;

@Data
public class AdoptAnimal {
	private int adopt_list_idx;
	private int member_idx;
	private int animal_idx;

	// 추가.
	private int adopt_animal_idx;
	private String kindCd;
	private String popfile;

}
