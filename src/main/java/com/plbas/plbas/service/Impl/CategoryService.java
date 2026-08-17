package com.plbas.plbas.service.Impl;

import com.plbas.plbas.Response;
import com.plbas.plbas.service.DTO.CategoryDTO;

import java.util.List;

public interface CategoryService {

    Response<Void> createCategory(CategoryDTO categoryDTO);

    Response<List<CategoryDTO>> getAllCategory();

    Response<CategoryDTO> getCategoryById(Long id);

    Response<Void> deleteCategoryById(Long id);

}
