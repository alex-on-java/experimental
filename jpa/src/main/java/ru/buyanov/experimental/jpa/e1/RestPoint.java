package ru.buyanov.experimental.jpa.e1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.buyanov.experimental.jpa.e1.domain.*;
import ru.buyanov.experimental.jpa.e1.repository.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author A.Buyanov 13.02.2016.
 */
@RestController
@RequestMapping("/e1")
public class RestPoint {
    @Autowired
    private TagV1Repository tagV1Repository;

    @Autowired
    private ProductV1Repository productV1Repository;

    @Autowired
    private ProductV2Repository productV2Repository;

    @Autowired
    private ProductToTagRepository productToTagRepository;

    @Autowired
    private ProductToTagV2Repository productToTagV2Repository;

    @Autowired
    private TagV2Repository tagV2Repository;

    @PostConstruct
    public void create() {
        List<TagV1> tags = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            tags.add(new TagV1("Tag#" + i));
        }
        tagV1Repository.save(tags);
        for (int i = 1; i <= 5; i++) {
            ProductV1 product = new ProductV1("Product#" + i);
            product.getTags().addAll(tags.subList(i - 1, i + 1));
            productV1Repository.save(product);
        }
    }

    /**
     * Simple as it could be, you just load products, and the rest is Hibernate's work
     * But is suffers from "N+1 selects" issue, where N is the number of products you are loading
     */
    @RequestMapping(value = "/productsV1", produces = "text/plain")
    public String getProductsV1() {
        /**** Loading ****/
        List<ProductV1> products = productV1Repository.findAll();

        /**** Printing ****/
        StringBuilder sb = new StringBuilder();
        for (ProductV1 product : products) {
            sb.append(product.getName()).append("\n");
            for (TagV1 tag : product.getTags()) {
                sb.append("--").append(tag.getName()).append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * This method is much more verbose in loading, and you need extra entity (ProductToTag).
     * Also there could be a problem with WHERE id IN () clause, in case of more than 1000 ids.
     * But the benefit is that you need just 3 selects, no matter how many products you are loading.
     * Also, you could easily cache Tag
     */
    @RequestMapping(value = "/productsV2", produces = "text/plain")
    public String getProductsV2() {
        /**** Loading ****/
        List<ProductV2> products = productV2Repository.findAll();
        List<Integer> productIds = products.stream().map(ProductV2::getId).collect(Collectors.toList());
        List<ProductToTag> productToTagList = productToTagRepository.findAllByProductIdIn(productIds);
        List<Integer> tagIds = productToTagList.stream().map(ProductToTag::getTagId).collect(Collectors.toList());
        List<TagV2> tags = tagV2Repository.findAllByIdIn(tagIds);
        Map<Integer, TagV2> tagMap = tags.stream().collect(Collectors.toMap(TagV2::getId, Function.identity()));
        Map<Integer, ProductV2> productMap = products.stream().collect(Collectors.toMap(ProductV2::getId, Function.identity()));
        for (ProductToTag ptt : productToTagList) {
            productMap.get(ptt.getProductId()).getTags().add(tagMap.get(ptt.getTagId()));
        }

        /**** Printing ****/
        StringBuilder sb = new StringBuilder();
        for (ProductV2 product : products) {
            sb.append(product.getName()).append("\n");
            for (TagV2 tag : product.getTags()) {
                sb.append("--").append(tag.getName()).append("\n");
            }
        }
        return sb.toString();
    }

    /**
     *  Here, like in first case, we need just one line of code and one query to load.
     *  It is possible because of 'join fetch' on Product and Tag
     *  Disadvantage of this method is that we are loading a cartesian product of row
     *  and we need to create as many objects.
     */
    @RequestMapping(value = "/productsV3", produces = "text/plain")
    public String getProductsV3() {
        /**** Loading ****/
        List<ProductToTagV2> productToTagList = productToTagV2Repository.fetchAll();


        /**** Printing ****/
        StringBuilder sb = new StringBuilder();
        ProductV2 lastProduct = null;
        for (ProductToTagV2 ptt : productToTagList) {
            if (lastProduct ==null || lastProduct.getId() != ptt.getProduct().getId()) {
                sb.append(ptt.getProduct().getName()).append("\n");
                lastProduct = ptt.getProduct();
            }
            sb.append("--").append(ptt.getTag().getName()).append("\n");
        }
        return sb.toString();
    }
}
