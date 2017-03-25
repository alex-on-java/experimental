package ru.buyanov.experimental.jpa.e1.domain;

import javax.persistence.*;

/**
 * @author A.Buyanov 14.02.2016.
 */
@Entity
@IdClass(ProductToTagPK.class)
@Table(name = "product_tag")
public class ProductToTagV2 {
    @Id
    @Column(name = "product_id")
    private int productId;

    @Id
    @Column(name = "tag_id")
    private int tagId;

    @OneToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private ProductV2 product;

    @OneToOne
    @JoinColumn(name = "tag_id", insertable = false, updatable = false)
    private TagV2 tag;


    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public ProductV2 getProduct() {
        return product;
    }

    public void setProduct(ProductV2 product) {
        this.product = product;
    }

    public TagV2 getTag() {
        return tag;
    }

    public void setTag(TagV2 tag) {
        this.tag = tag;
    }
}
