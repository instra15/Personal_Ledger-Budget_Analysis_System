package com.plbas.plbas.service.Impl;

import com.plbas.plbas.Response;
import com.plbas.plbas.entity.Category;
import com.plbas.plbas.exception.BusinessException;
import com.plbas.plbas.repository.CategoryRepository;
import com.plbas.plbas.service.DTO.CategoryDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public Response<CategoryDTO> createCategory(CategoryDTO categoryDTO)
    {
        Category category=categoryRepository.findByNameAndDirection(categoryDTO.getName(),categoryDTO.getDirection());
        if (category!=null)
        {
            throw new BusinessException("Category exists.");
        }
        categoryRepository.save(CategoryDTO.converter(categoryDTO));
        return Response.success(categoryDTO);
    }

    public Response<List<CategoryDTO>> getAllCategory()
    {
        List<CategoryDTO> list=new ArrayList<>();
        list=categoryRepository.findAll().stream().map(CategoryDTO::converter).toList();
        return Response.success(list);
    }

}
