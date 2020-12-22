package com.tj.cardsagainsthumanity.controllers;

import com.tj.cardsagainsthumanity.models.cards.CardPackage;
import com.tj.cardsagainsthumanity.models.cards.PackageImport;
import com.tj.cardsagainsthumanity.serializer.converter.cardPackage.CreatePackageSerializer;
import com.tj.cardsagainsthumanity.serializer.converter.cardPackage.DetailedPackageResponseConverter;
import com.tj.cardsagainsthumanity.serializer.converter.packageImport.DatabaseImportRequestNormalizer;
import com.tj.cardsagainsthumanity.serializer.converter.packageImport.PackageImportSerializer;
import com.tj.cardsagainsthumanity.serializer.requestModel.cardPackage.CreatePackageRequest;
import com.tj.cardsagainsthumanity.serializer.requestModel.dbImport.DatabaseImportRequest;
import com.tj.cardsagainsthumanity.serializer.requestModel.packageImport.NormalizedPackageImport;
import com.tj.cardsagainsthumanity.serializer.responseModel.cardPackage.PackageResponse;
import com.tj.cardsagainsthumanity.services.CardPackageService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class PackageControllerTest {

    @Mock
    private CreatePackageRequest fakeRequest;
    @Mock
    private CreatePackageSerializer converter;
    @Mock
    private CardPackageService packageService;
    @Mock
    private CardPackage expectedPackage;
    @Mock
    private NormalizedPackageImport normalizedImport;
    @Mock
    private PackageImport importPackage;

    @Mock
    private PackageResponse mockResponse;
    @Mock
    private DatabaseImportRequestNormalizer normalizer;
    @Mock
    private PackageImportSerializer importSerializer;
    @Mock
    private DatabaseImportRequest importRequest;
    @Mock
    private Authentication auth;
    @Mock
    private DetailedPackageResponseConverter detailedPackageResponseConverter;

    private PackageController controller;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new PackageController(packageService, converter, normalizer, importSerializer, detailedPackageResponseConverter);
        when(converter.convertRequestToBusinessObject(fakeRequest)).thenReturn(expectedPackage);
        when(packageService.createCardPackage(expectedPackage)).thenReturn(expectedPackage);
        when(converter.convertBusinessObjectToResponse(expectedPackage)).thenReturn(mockResponse);
        when(normalizer.normalize(importRequest)).thenReturn(Arrays.asList(normalizedImport, normalizedImport));
        when(importSerializer.convertRequestToBusinessObject(normalizedImport)).thenReturn(importPackage);
        when(packageService.importPackages(Arrays.asList(importPackage, importPackage))).thenReturn(Arrays.asList(expectedPackage, expectedPackage));
    }

    @Test
    public void createPackage() {
        ResponseEntity<PackageResponse> result = controller.createPackage(auth, fakeRequest);
        //it converts serializer to business object
        verify(converter, times(1)).convertRequestToBusinessObject(fakeRequest);
        //it calls the service
        verify(packageService, times(1)).createCardPackage(expectedPackage);
        //it converts bo to response
        verify(converter, times(1)).convertBusinessObjectToResponse(expectedPackage);
        //it sets the correct body
        assertSame(result.getBody(), mockResponse);
    }

    @Test
    public void importPackagesFromDatabase() {

        ResponseEntity<Collection<PackageResponse>> result = controller.importPackages(importRequest);
        Collection<PackageResponse> expectedResult = Arrays.asList(mockResponse, mockResponse);
        //it normalizes the request
        verify(normalizer, times(1)).normalize(importRequest);
        //it serializes all of the packages to import
        verify(importSerializer, times(2)).convertRequestToBusinessObject(normalizedImport);
        //it invokes service to import packages
        verify(packageService, times(1)).importPackages(Arrays.asList(importPackage, importPackage));
        //it returns right response
        assertEquals(result.getBody(), expectedResult);
        assertEquals(result.getStatusCode(), HttpStatus.CREATED);
    }
}