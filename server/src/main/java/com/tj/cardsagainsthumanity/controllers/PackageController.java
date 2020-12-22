package com.tj.cardsagainsthumanity.controllers;

import com.tj.cardsagainsthumanity.models.cards.CardPackage;
import com.tj.cardsagainsthumanity.models.cards.DetailedCardPackage;
import com.tj.cardsagainsthumanity.models.cards.PackageImport;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.security.auth.PlayerUserDetails;
import com.tj.cardsagainsthumanity.serializer.converter.cardPackage.CreatePackageSerializer;
import com.tj.cardsagainsthumanity.serializer.converter.cardPackage.DetailedPackageResponseConverter;
import com.tj.cardsagainsthumanity.serializer.converter.packageImport.DatabaseImportRequestNormalizer;
import com.tj.cardsagainsthumanity.serializer.converter.packageImport.PackageImportSerializer;
import com.tj.cardsagainsthumanity.serializer.requestModel.cardPackage.CreatePackageRequest;
import com.tj.cardsagainsthumanity.serializer.requestModel.dbImport.DatabaseImportRequest;
import com.tj.cardsagainsthumanity.serializer.requestModel.packageImport.NormalizedPackageImport;
import com.tj.cardsagainsthumanity.serializer.responseModel.cardPackage.DetailedPackageResponse;
import com.tj.cardsagainsthumanity.serializer.responseModel.cardPackage.PackageResponse;
import com.tj.cardsagainsthumanity.services.CardPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PackageController {

    private CardPackageService packageService;
    private CreatePackageSerializer createRequestSerializer;
    private DatabaseImportRequestNormalizer importRequestNormalizer;
    private PackageImportSerializer importSerializer;
    private DetailedPackageResponseConverter detailedPackageResponseConverter;

    public PackageController(@Autowired CardPackageService packageService, @Autowired CreatePackageSerializer createRequestSerializer, @Autowired DatabaseImportRequestNormalizer importRequestNormalizer, @Autowired PackageImportSerializer importSerializer, @Autowired DetailedPackageResponseConverter detailedPackageResponseConverter) {
        this.packageService = packageService;
        this.createRequestSerializer = createRequestSerializer;
        this.importRequestNormalizer = importRequestNormalizer;
        this.importSerializer = importSerializer;
        this.detailedPackageResponseConverter = detailedPackageResponseConverter;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/packages")
    public ResponseEntity<PackageResponse> createPackage(Authentication authentication, @Valid @RequestBody CreatePackageRequest request) {
        PlayerUserDetails player = (PlayerUserDetails) authentication.getPrincipal();
        Player owner = player.getPlayer();
        request.setOwner(owner);
        CardPackage cardPackage = createRequestSerializer.convertRequestToBusinessObject(request);
        CardPackage result = packageService.createCardPackage(cardPackage);
        PackageResponse response = createRequestSerializer.convertBusinessObjectToResponse(result);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/packages/{packageId}")
    public ResponseEntity<?> getPackageInfo(Authentication authentication, @PathVariable("packageId") Integer packageId) {
        PlayerUserDetails player = (PlayerUserDetails) authentication.getPrincipal();
        Player owner = player.getPlayer();
        packageService.assertCanReadPackage(owner, packageId);
        DetailedCardPackage result = packageService.getPackageCardsForPackage(packageId);
        DetailedPackageResponse response = detailedPackageResponseConverter.convertBusinessObjectToResponse(result);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/packages/{packageId}")
    public ResponseEntity<?> deletePackage(Authentication authentication, @PathVariable("packageId") Integer packageId) {
        PlayerUserDetails player = (PlayerUserDetails) authentication.getPrincipal();
        Player owner = player.getPlayer();
        packageService.assertCanEditPackage(owner, packageId);
        CardPackage result = packageService.getCardPackageById(packageId);
        if (result != null) {
            if (result.getOwner().getId() == owner.getId()) {
                packageService.deleteCardPackage(result);
            }
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @RequestMapping(method = RequestMethod.GET, value = "/packages/me")
    public ResponseEntity<?> getMyPackages(Authentication authentication) {
        PlayerUserDetails player = (PlayerUserDetails) authentication.getPrincipal();
        Player owner = player.getPlayer();
        List<CardPackage> results = packageService.getPackagesForPlayer(owner);
        List<PackageResponse> response = results.stream().map(createRequestSerializer::convertBusinessObjectToResponse).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/packages")
    public ResponseEntity<?> getUsablePackages(Authentication authentication) {
        PlayerUserDetails player = (PlayerUserDetails) authentication.getPrincipal();
        Player owner = player.getPlayer();
        List<CardPackage> results = packageService.getUsablePackagesForPlayer(owner);
        List<PackageResponse> response = results.stream().map(createRequestSerializer::convertBusinessObjectToResponse).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK)
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
