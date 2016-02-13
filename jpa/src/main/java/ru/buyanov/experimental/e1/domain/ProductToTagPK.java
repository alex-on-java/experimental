package ru.buyanov.experimental.e1.domain;

import java.io.Serializable;

/**
 * @author A.Buyanov 13.02.2016.
 */
public class ProductToTagPK implements Serializable {
    private int productId;
    private int tagId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductToTagPK that = (ProductToTagPK) o;

        if (productId != that.productId) return false;
        return tagId == that.tagId;

    }

    @Override
    public int hashCode() {
        int result = productId;
        result = 31 * result + tagId;
        return result;
    }
}
