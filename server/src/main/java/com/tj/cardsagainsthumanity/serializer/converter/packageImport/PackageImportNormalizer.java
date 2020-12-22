package com.tj.cardsagainsthumanity.serializer.converter.packageImport;

import com.tj.cardsagainsthumanity.serializer.requestModel.packageImport.NormalizedPackageImport;

import java.util.Collection;

public interface PackageImportNormalizer<T> {
    Collection<NormalizedPackageImport> normalize(T requestObject);
}
