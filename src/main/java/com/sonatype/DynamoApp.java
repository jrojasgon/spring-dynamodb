package com.sonatype;

import com.sonatype.model.ProductInfo;
import com.sonatype.repository.ProductInfoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DynamoApp
    implements CommandLineRunner
{
  @Autowired
  ProductInfoRepository repository;

  public static void main(String[] args) {
    SpringApplication.run(DynamoApp.class, args);
  }

  @Override
  public void run(final String... args) throws Exception {
    ProductInfo newProductInfo = new ProductInfo("description", "1500");
    repository.save(newProductInfo);
    Iterable<ProductInfo> productInfos = repository.findAll();
    productInfos.iterator().forEachRemaining(pi -> System.out.println(pi.toString()));
  }
}
