package org.ssa.ironyard.liquorstore.dao;

import java.util.List;

import org.ssa.ironyard.liquorstore.model.CoreProduct.Tag;
import org.ssa.ironyard.liquorstore.model.CoreProduct.Type;
import org.ssa.ironyard.liquorstore.model.Product;

public interface DAOProduct extends DAO<Product>
{
    List<Product> searchProducts(List<Tag> tags, List<Type> types);


}
