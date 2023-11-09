package org.expensetracker.service.test;

import org.expensetracker.database.repository.CategoryRepository;
import org.expensetracker.database.repository.impl.CategoryRepositoryImpl;
import org.expensetracker.service.CategoryService;
import org.expensetracker.service.impl.CategoryServiceImpl;
import org.expensetracker.service.mapper.CategoryMapper;
import org.expensetracker.service.mapper.impl.CategoryMapperImpl;
import org.expensetracker.service.model.CategoryDto;

public class CategoryServiceTest {
    public static void main(String[] args) {
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
        configuration.configure();

        CategoryRepository categoryRepository = new CategoryRepositoryImpl(configuration.buildSessionFactory());

        CategoryMapper categoryMapper = new CategoryMapperImpl();

        CategoryService categoryService = new CategoryServiceImpl(categoryRepository, categoryMapper);


        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Dto name  2");
        categoryDto.setDescription("Dto description 2");


        //getById - works
        //System.out.println(categoryService.getById(1L));
        //getAll - works
        //System.out.println(categoryService.getAll());
        //add - works
        //System.out.println(categoryService.add(categoryDto));
        //updateById - works
        //System.out.println(categoryService.updateById(2L, categoryDto));
        //deleteById - works
        //categoryService.deleteById(3L);
    }
}
