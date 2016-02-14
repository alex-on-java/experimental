package ru.buyanov.experimental.jpa.e1.domain;

import javax.persistence.*;

/**
 * @author A.Buyanov 13.02.2016.
 */
@Entity
@IdClass(ProductToTagPK.class)
@Table(name = "product_tag")
public class ProductToTag {
    @Id
    @Column(name = "product_id", insertable = false, updatable = false)
    private int productId;

    @Id
    @Column(name = "tag_id", insertable = false, updatable = false)
    private int tagId;


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
}
