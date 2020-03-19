package com.sonatype.repository;

import java.util.Optional;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.sonatype.model.ProductInfo;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface ProductInfoRepository
    extends CrudRepository<ProductInfo, String>
{

}
