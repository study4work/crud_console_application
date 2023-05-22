package com.lysenko.crudapp.controller;

import com.lysenko.crudapp.repository.jdbc.SpecialtyRepositoryImpl;
import com.lysenko.crudapp.model.Specialty;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SpecialtyControllerTest {

   private final SpecialtyRepositoryImpl specialtyRepository = mock(SpecialtyRepositoryImpl.class);
   private final SpecialtyController specialtyController = new SpecialtyController(specialtyRepository);

   @Test
   public void testWhenCreateSpecialty() {
      String description = "description";
      Specialty specialty = new Specialty();
      specialty.setDescriptionSpecialty(description);
      when(specialtyRepository.save(any())).thenReturn(specialty);
      Specialty result = specialtyController.createSpecialty(description);
      verify(specialtyRepository, times(1)).save(any());
      Assert.assertEquals(specialty, result);
   }

   @Test
   public void testWhenFindById() {
      specialtyController.find(any());
      verify(specialtyRepository, times(1)).findById(any());
   }

   @Test
   public void testWhenFindAll() {
      specialtyController.findAll();
      verify(specialtyRepository, times(1)).findAll();
   }

   @Test
   public void testWhenUpdate() {
      specialtyController.update(any());
      verify(specialtyRepository, times(1)).update(any());
   }

   @Test
   public void testWhenDelete() {
      specialtyController.delete(any());
      verify(specialtyRepository, times(1)).delete(any());
   }
}