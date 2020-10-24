package com.tj.cardsagainsthumanity.controllers;

import com.tj.cardsagainsthumanity.models.cards.CardPackage;
import com.tj.cardsagainsthumanity.models.cards.PackageImport;
import com.tj.cardsagainsthumanity.serializer.converter.cardPackage.CreatePackageSerializer;
import com.tj.cardsagainsthumanity.serializer.converter.packageImport.DatabaseImportRequestNormalizer;
import com.tj.cardsagainsthumanity.serializer.converter.packageImport.PackageImportSerializer;
import com.tj.cardsagainsthumanity.serializer.requestModel.cardPackage.CreatePackageRequest;
import com.tj.cardsagainsthumanity.serializer.requestModel.dbImport.DatabaseImportRequest;
import com.tj.cardsagainsthumanity.serializer.requestModel.packageImport.NormalizedPackageImport;
import com.tj.cardsagainsthumanity.serializer.responseModel.cardPackage.PackageResponse;
import com.tj.cardsagainsthumanity.services.CardPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;
import java.util.stream.Collectors;

@Controller
public class PackageController {

    private CardPackageService packageService;
    private CreatePackageSerializer createRequestSerializer;
    private DatabaseImportRequestNormalizer importRequestNormalizer;
    private PackageImportSerializer importSerializer;

    public PackageController(@Autowired CardPackageService packageService, @Autowired CreatePackageSerializer createRequestSerializer, @Autowired DatabaseImportRequestNormalizer importRequestNormalizer, @Autowired PackageImportSerializer importSerializer) {
        this.packageService = packageService;
        this.createRequestSerializer = createRequestSerializer;
        this.importRequestNormalizer = importRequestNormalizer;
        this.importSerializer = importSerializer;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/packages")
    public ResponseEntity<PackageResponse> createPackage(@RequestBody CreatePackageRequest request) {
        CardPackage cardPackage = createRequestSerializer.convertRequestToBusinessObject(request);
        CardPackage result = packageService.createCardPackage(cardPackage);
        PackageResponse response = createRequestSerializer.convertBusinessObjectToResponse(result);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/packages/importFromDb")
    public ResponseEntity<Collection<PackageResponse>> importPackages(@RequestBody DatabaseImportRequest request) {
        Collection<NormalizedPackageImport> normalRequest = importRequestNormalizer.normalize(request);
        Collection<PackageResponse> response = importPackages(normalRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    public Collection<PackageResponse> importPackages(Collection<NormalizedPackageImport> packages) {
        Collection<PackageImport> packagesToImport = packages.stream()
                .map(importSerializer::convertRequestToBusinessObject)
                .collect(Collectors.toList());
        Collection<CardPackage> result = packageService.importPackages(packagesToImport);
        Collection<PackageResponse> response = result.stream()
                .map(createRequestSerializer::convertBusinessObjectToResponse)
                .collect(Collectors.toList());

        return response;
    }


}
