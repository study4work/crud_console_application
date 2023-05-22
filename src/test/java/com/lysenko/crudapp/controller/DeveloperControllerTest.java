package com.lysenko.crudapp.controller;

import com.lysenko.crudapp.model.Developer;
import com.lysenko.crudapp.repository.jdbc.DeveloperRepositoryImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeveloperControllerTest {

    private final DeveloperRepositoryImpl developerRepository = mock(DeveloperRepositoryImpl.class);
    private final DeveloperController developerController = new DeveloperController(developerRepository);

    @Test
    public void testWhenCreateDeveloper() {
        String firstName = "John";
        String lastName = "Doe";
        Developer developer = new Developer();
        developer.setFirstName(firstName);
        developer.setLastName(lastName);
        when(developerRepository.save(any())).thenReturn(developer);
        Developer result = developerController.createDeveloper("John", "Doe", "spec", any());
        verify(developerRepository, times(1)).save(any());
        Assert.assertEquals(developer, result);
    }

    @Test
    public void testWhenFindDeveloperById() {
        long id = 1;
        Developer developer = new Developer();
        developer.setId(id);
        when(developerRepository.findById(id)).thenReturn(developer);
        Developer result = developerController.findById(id);
        assertEquals(developer, result);
    }

    @Test
    public void testWhenFindAllDevelopers() {
        developerController.findAll();
        verify(developerRepository, times(1)).findAll();
    }

    @Test
    public void testWhenDeleteDeveloper() {
        developerController.delete(any());
        verify(developerRepository, times(1)).delete(any());
    }

    @Test
    public void testWhenUpdateDeveloper() {
        developerController.update(any());
        verify(developerRepository, times(1)).update(any());
    }
}